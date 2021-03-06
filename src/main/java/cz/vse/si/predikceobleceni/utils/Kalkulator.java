package cz.vse.si.predikceobleceni.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cz.vse.si.predikceobleceni.model.obleceni.*;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;
import cz.vse.si.predikceobleceni.model.svet.Pocasi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Kalkulator {
    private static final Kalkulator kalkulator = new Kalkulator();

    private Kalkulator() {
    }

    public static Kalkulator getInstance() {
        return kalkulator;
    }

    /**
     * @param casoprostor - časoprostor obsahující pouze souřadnice a čas (bez deště a nechladnějšího počasí)
     *                    Nejprve dojde k zjištění nejchladnějšího počasí a zjištění, jestli bude v tomto rozmezí času pršet.
     *                    Dochází k nastavení těchto hodnot v časoprostoru.
     *                    Tyto 2 hodnoty jsou poté použity ke generaci outfitu.
     */
    public Outfit predpovedObleceni(Casoprostor casoprostor) {
        //Persistence persistence = new Persistence();
        Persistence.getInstance().pridejLokalitu(casoprostor);

        if (!priradNejchladnejsiPocasiADest(casoprostor)) {
            return null;
        }
        return vygenerujOutfit(casoprostor);
    }


    private boolean priradNejchladnejsiPocasiADest(Casoprostor casoprostor) {
        try {
            List<Pocasi> pocasi = zjistiPocasiZApi(casoprostor);
            casoprostor.setNejchladnejsiPocasi(vyberNejchladnejsi(pocasi));
            casoprostor.setNejteplejsiPocasi(vyberNejteplejsi(pocasi));
            casoprostor.setDest(zjistiJestliBudePrset(pocasi));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * V této metodě dochází ke scrapenutí dat z API
     */
    private List<Pocasi> zjistiPocasiZApi(Casoprostor casoprostor) {
        String jsonOdpoved = VolacApi.getInstance().zavolejApi(casoprostor.getZemepisnaSirka(), casoprostor.getZemepisnaDelka());
        return konvertujJsonNaListPocasi(jsonOdpoved, casoprostor);
    }

    private List<Pocasi> konvertujJsonNaListPocasi(String json, Casoprostor casoprostor) {
        //System.out.println(json);
        JsonElement jelement = null;
        try {
            jelement = new JsonParser().parse(json);
        } catch (NullPointerException e) {
            return null;
        }
        JsonObject jobject = jelement.getAsJsonObject();

        String nazev = jobject.getAsJsonObject("city").get("name").toString();

        List<Pocasi> pocasi = new ArrayList<>();

        JsonArray weatherArray = jobject.getAsJsonArray("list");
        weatherArray.forEach(element -> {
            boolean dest = Objects.equals(element.getAsJsonObject().getAsJsonArray("weather").get(0).getAsJsonObject().get("main").toString(), "Rain")
                    || Objects.equals(element.getAsJsonObject().getAsJsonArray("weather").get(0).getAsJsonObject().get("main").toString(), "Thunderstorm")
                    || Objects.equals(element.getAsJsonObject().getAsJsonArray("weather").get(0).getAsJsonObject().get("main").toString(), "Drizzle");
            int cas = Integer.parseInt(element.getAsJsonObject().get("dt").toString());
            double teplota = Double.parseDouble(element.getAsJsonObject().getAsJsonObject("main").get("temp").toString());

            LocalDateTime objektCasu = LocalDateTime.ofInstant(Instant.ofEpochSecond(cas), ZoneId.systemDefault());

            pocasi.add(new Pocasi(dest, teplota, objektCasu));
        });
        return filtrujPocasi(pocasi, casoprostor);
    }


    //tady dojde k vyfiltrování všech počasí na ty které jsou v časoprostoru (podle počátečního a konečného času)
    private List<Pocasi> filtrujPocasi(List<Pocasi> vsechnaPocasi, Casoprostor casoprostor) {
        return vsechnaPocasi.stream().filter(pocasi -> pocasi.getLocalDateTime().isAfter(casoprostor.getPocatecniCas()) && pocasi.getLocalDateTime().isBefore(casoprostor.getKonecnyCas())).collect(Collectors.toList());
    }

    private Pocasi vyberNejchladnejsi(List<Pocasi> mnozinaPocasi) {
        return mnozinaPocasi
                .stream()
                .min(Comparator.comparing(Pocasi::getTeplota))
                .orElse(null);
    }

    private Pocasi vyberNejteplejsi(List<Pocasi> mnozinaPocasi) {
        return mnozinaPocasi
                .stream()
                .max(Comparator.comparing(Pocasi::getTeplota))
                .orElse(null);
    }

    private boolean zjistiJestliBudePrset(List<Pocasi> mnozinaPocasi) {
        return mnozinaPocasi
                .stream()
                .anyMatch(Pocasi::isDest);
    }

    /**
     * @param casoprostor - časoprostor s nastavenými hodnotami pro déšť a nejchladnější počasí.
     *                    Při existenci více uživatelem definovaných kusů oblečení (např. uživatel zadal vícero bot)
     *                    se vyberou ty s nejvyšší nejnižší přípustnou hodnotou pro daný kus oblečení. (protože v časoprostoru
     *                    je jinak tepleji, takže pokud je za den v Praze nejnižší teplota za den 20°C a existenci bot pro -10°C až 22°C
     *                    a dalších bot pro 20°C až 40°C má samozřejmě smysl si dát boty pro 20-40)
     */

    private Outfit vygenerujOutfit(Casoprostor casoprostor) {

        if (casoprostor.getNejchladnejsiPocasi() == null) {
            return new Outfit(new Cepice("", Integer.MIN_VALUE, Integer.MAX_VALUE, null), null, null, null, false);
        }

        //Persistence persistence = new Persistence();

        ArrayList<Cepice> cepice = Persistence.getInstance().getHlava(); //array cepic z databaze
        ArrayList<Vrsek> vrsky = Persistence.getInstance().getVrsek(); //array vrsku z databaze
        ArrayList<Spodek> spodky = Persistence.getInstance().getSpodek(); //array spodku z databaze
        ArrayList<Boty> boty = Persistence.getInstance().getBoty(); //array bot z databaze

        List<Vrsek> prvniVrstvaTelo = vratVrstvu(vrsky, Vrstva.PRVNI, casoprostor);
        List<Vrsek> druhaVrstvaTelo = vratVrstvu(vrsky, Vrstva.DRUHA, casoprostor);
        List<Vrsek> tretiVrstvaTelo = vratVrstvu(vrsky, Vrstva.TRETI, casoprostor);

        List<Spodek> prvniVrstvaSpodek = vratVrstvu(spodky, Vrstva.PRVNI, casoprostor);
        List<Spodek> druhaVrstvaSpodek = vratVrstvu(spodky, Vrstva.DRUHA, casoprostor);
        List<Spodek> tretiVrstvaSpodek = vratVrstvu(spodky, Vrstva.TRETI, casoprostor);

        Cepice finalniCepice = vratFinalniKus(cepice, casoprostor);
        Boty finalniBoty = vratFinalniKus(boty, casoprostor);

        List<Vrsek> finalniVrsky = new ArrayList<>();
        List<Spodek> finalniSpodky = new ArrayList<>();

        boolean destnik = casoprostor.isDest();

        Vrsek prvniVrsek = vratFinalniKus(prvniVrstvaTelo, casoprostor);
        Vrsek druhyVrsek = vratFinalniKus(druhaVrstvaTelo, casoprostor);
        Vrsek tretiVrsek = vratFinalniKus(tretiVrstvaTelo, casoprostor);

        Spodek prvniSpodek = vratFinalniKus(prvniVrstvaSpodek, casoprostor);
        Spodek druhySpodek = vratFinalniKus(druhaVrstvaSpodek, casoprostor);
        Spodek tretiSpodek = vratFinalniKus(tretiVrstvaSpodek, casoprostor);

        if (prvniVrsek != null) {
            finalniVrsky.add(prvniVrsek);
        }
        if (druhyVrsek != null) {
            finalniVrsky.add(druhyVrsek);
        }
        if (tretiVrsek != null) {
            finalniVrsky.add(tretiVrsek);
        }
        if (prvniSpodek != null) {
            finalniSpodky.add(prvniSpodek);
        }
        if (druhySpodek != null) {
            finalniSpodky.add(druhySpodek);
        }
        if (tretiSpodek != null) {
            finalniSpodky.add(tretiSpodek);
        }
        Outfit finalniOutfit = new Outfit(finalniCepice, finalniVrsky, finalniSpodky, finalniBoty, destnik);
        finalniOutfit.setAlternativniObleceni(
                vygenerujAlternativniObleceni(cepice, vrsky, spodky, boty, casoprostor)
        );
        return finalniOutfit;
    }

    private <T extends Obleceni> List<T> vratVrstvu(List<T> obleceni, Vrstva vrstva, Casoprostor casoprostor) {
        return obleceni
                .stream()
                .filter(o -> o.getVrstva() == vrstva)
                .filter(o -> casoprostor.getFormalnostObleceni().contains(o.getFormalni()))
                .collect(Collectors.toList());
    }

    private <T extends Obleceni> T vratFinalniKus(List<T> obleceni, Casoprostor casoprostor) {
        return obleceni
                .stream()
                .filter(o -> o.getMinimalniTeplota() < casoprostor.getNejchladnejsiPocasi().getTeplota())
                .filter(o -> o.getMaximalniTeplota() > casoprostor.getNejchladnejsiPocasi().getTeplota())
                .filter(o -> casoprostor.getFormalnostObleceni().contains(o.getFormalni()))
                .max(Comparator.comparing(T::getMinimalniTeplota))
                .orElse(null);
    }

    private AlternativniObleceni vygenerujAlternativniObleceni(List<Cepice> cepice, List<Vrsek> vrsky, List<Spodek> spodky, List<Boty> boty, Casoprostor casoprostor) {
        List<Cepice> vsechnyCepice = zkontrolujVhodnostDoPocasiAFormalnost(cepice, casoprostor);
        List<Vrsek> vsechnyVrsky = zkontrolujVhodnostDoPocasiAFormalnost(vrsky, casoprostor);
        List<Spodek> vsechnySpodky = zkontrolujVhodnostDoPocasiAFormalnost(spodky, casoprostor);
        List<Boty> vsechnyBoty = zkontrolujVhodnostDoPocasiAFormalnost(boty, casoprostor);
        return new AlternativniObleceni(vsechnyCepice, vsechnyVrsky, vsechnySpodky, vsechnyBoty);
    }

    private <T extends Obleceni> List<T> zkontrolujVhodnostDoPocasiAFormalnost(List<T> obleceni, Casoprostor casoprostor) {
        return obleceni
                .stream()
                .filter(o -> o.getMinimalniTeplota() < casoprostor.getNejchladnejsiPocasi().getTeplota())
                .filter(o -> o.getMaximalniTeplota() > casoprostor.getNejchladnejsiPocasi().getTeplota())
                .filter(o -> casoprostor.getFormalnostObleceni().contains(o.getFormalni()))
                .sorted(Comparator.comparingInt(Obleceni::getMinimalniTeplota))
                .collect(Collectors.toList());
    }

}
