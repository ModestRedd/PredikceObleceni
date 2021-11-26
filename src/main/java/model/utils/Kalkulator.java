package model.utils;

import model.svet.Pocasi;
import model.obleceni.*;
import model.svet.Casoprostor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Kalkulator {
    private static Kalkulator kalkulator = new Kalkulator();

    public static Kalkulator getInstance() {
        return kalkulator;
    }

    private Kalkulator() {
    }

    /**
     * @param casoprostor - časoprostor obsahující pouze souřadnice a čas (bez deště a nechladnějšího počasí)
     *                    Nejprve dojde k zjištění nejchladnějšího počasí a zjištění, jestli bude v tomto rozmezí času pršet.
     *                    Dochází k nastavení těchto hodnot v časoprostoru.
     *                    Tyto 2 hodnoty jsou poté použity ke generaci outfitu.
     */
    //Viktor z UI v kontrolleru vygeneruje časoprostor, a zavolá tuto metodu. Vrátí mu hotový outfit.
    public Outfit predpovedObleceni(Casoprostor casoprostor) {
        priradNejchladnejsiPocasiADest(casoprostor);
        return vygenerujOutfit(casoprostor);
    }


    private void priradNejchladnejsiPocasiADest(Casoprostor casoprostor) {
        List<Pocasi> pocasi = zjistiPocasiZApi(casoprostor);
        casoprostor.setNejchladnejsiPocasi(vyberNejchladnejsi(pocasi));
        casoprostor.setDest(zjistiJestliBudePrset(pocasi));
    }

    /**
     * V této metodě dochází ke scrapenutí dat z API
     */
    //doplní Dimitrii z údajů zemepisnaSirka, zemepisnaDelka, pocatecniCas a konecnyCas v časoprostoru
    // a getterů na tyto proměnné, a vrati list pocasi
    private List<Pocasi> zjistiPocasiZApi(Casoprostor casoprostor) {
        return null;
    }

    private Pocasi vyberNejchladnejsi(List<Pocasi> mnozinaPocasi) {
        return mnozinaPocasi.stream().min(Comparator.comparing(Pocasi::getTeplota)).orElse(null);
    }

    private boolean zjistiJestliBudePrset(List<Pocasi> mnozinaPocasi) {
        return mnozinaPocasi.stream().anyMatch(Pocasi::isDest);
    }

    /**
     * @param casoprostor - časoprostor s nastavenými hodnotami pro déšť a nejchladnější počasí.
     *                    Při existenci více uživatelem definovaných kusů oblečení (např. uživatel zadal vícero bot)
     *                    se vyberou ty s nejvyšší nejnižší přípustnou hodnotou pro daný kus oblečení. (protože v časoprostoru
     *                    je jinak tepleji, takže pokud je za den v Praze nejnižší teplota za den 20°C a existenci bot pro -10°C až 22°C
     *                    a dalších bot pro 20°C až 40°C má samozřejmě smysl si dát boty pro 20-40)
     */

    private Outfit vygenerujOutfit(Casoprostor casoprostor) {

        int teplota = casoprostor.getNejchladnejsiPocasi().getTeplota();

        //scrape dat z databáze a mapování na oblečení - Jirka doplní metody
        List<Cepice> cepice = null;
        List<Vrsek> vrsky = null;
        List<Spodek> spodky = null;
        List<Boty> boty = null;

        List<Vrsek> prvniVrstvaTelo = vratVrstvu(vrsky, Vrstva.PRVNI, casoprostor.getOchotnyOblect());
        List<Vrsek> druhaVrstvaTelo = vratVrstvu(vrsky, Vrstva.DRUHA, casoprostor.getOchotnyOblect());
        List<Vrsek> tretiVrstvaTelo = vratVrstvu(vrsky, Vrstva.TRETI, casoprostor.getOchotnyOblect());

        List<Spodek> prvniVrstvaSpodek = vratVrstvu(spodky, Vrstva.PRVNI, casoprostor.getOchotnyOblect());
        List<Spodek> druhaVrstvaSpodek = vratVrstvu(spodky, Vrstva.DRUHA, casoprostor.getOchotnyOblect());
        List<Spodek> tretiVrstvaSpodek = vratVrstvu(spodky, Vrstva.TRETI, casoprostor.getOchotnyOblect());

        Cepice finalniCepice = vratFinalniKus(cepice, teplota);
        Boty finalniBoty = vratFinalniKus(boty, teplota);

        List<Vrsek> finalniVrsky = new ArrayList<>();
        List<Spodek> finalniSpodky = new ArrayList<>();

        boolean destnik = casoprostor.isDest();

        Vrsek prvniVrsek = vratFinalniKus(prvniVrstvaTelo, teplota);
        Vrsek druhyVrsek = vratFinalniKus(druhaVrstvaTelo, teplota);
        Vrsek tretiVrsek = vratFinalniKus(tretiVrstvaTelo, teplota);

        Spodek prvniSpodek = vratFinalniKus(prvniVrstvaSpodek, teplota);
        Spodek druhySpodek = vratFinalniKus(druhaVrstvaSpodek, teplota);
        Spodek tretiSpodek = vratFinalniKus(tretiVrstvaSpodek, teplota);

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
        return new Outfit(finalniCepice, finalniVrsky, finalniSpodky, finalniBoty, destnik);
    }

    private <T extends Obleceni> List<T> vratVrstvu(List<T> obleceni, Vrstva vrstva, List<Formalni> formalni) {
        return obleceni.stream().filter(o -> o.getVrstva() == vrstva).filter(o -> formalni.contains(o.getFormalni())).collect(Collectors.toList());
    }

    private <T extends Obleceni> T vratFinalniKus(List<T> obleceni, int teplota) {
        return obleceni.stream().filter(o -> o.getMinimalniTeplota() < teplota && o.getMaximalniTeplota() > teplota).max(Comparator.comparing(T::getMinimalniTeplota)).orElse(null);
    }

}


