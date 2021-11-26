package model.utils;

import model.svet.Pocasi;
import model.obleceni.*;
import model.svet.Casoprostor;
import java.util.ArrayList;
import java.util.Arrays;
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
        casoprostor.setNejteplejsiPocasi(vyberNejteplejsi(pocasi));
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

    private Pocasi vyberNejteplejsi(List<Pocasi> mnozinaPocasi) {
        return mnozinaPocasi.stream().max(Comparator.comparing(Pocasi::getTeplota)).orElse(null);
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

        //scrape dat z databáze a mapování na oblečení - Jirka doplní metody
        List<Cepice> cepice = Arrays.asList(new Cepice("Čepice", 5, 99, Formalni.MALO));
        List<Vrsek> vrsky = Arrays.asList(new Vrsek("Mikina", Vrstva.DRUHA, -99, 11, Formalni.MALO), new Vrsek("Tričko", Vrstva.PRVNI, -99, 99, Formalni.MALO), new Vrsek("Kabát", Vrstva.TRETI, -99, 18, Formalni.STREDNE),new Vrsek("Bunda",Vrstva.TRETI,-20,15,Formalni.MALO));

        List<Spodek> spodky = Arrays.asList(new Spodek("Chinos",Vrstva.DRUHA,-20,20,Formalni.STREDNE),new Spodek("Rifle",Vrstva.DRUHA,-21,20,Formalni.STREDNE));
        List<Boty> boty = Arrays.asList(new Boty("Koženky",-20,15,Formalni.MALO));

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
        finalniOutfit.setAlternativniObleceni(vygenerujAlternativniObleceni(cepice, vrsky, spodky, boty, casoprostor));
        return finalniOutfit;
    }

    private <T extends Obleceni> List<T> vratVrstvu(List<T> obleceni, Vrstva vrstva, Casoprostor casoprostor) {
        return obleceni.stream().filter(o -> o.getVrstva() == vrstva).filter(o -> casoprostor.getFormalnostObleceni().contains(o.getFormalni())).collect(Collectors.toList());
    }

    private <T extends Obleceni> T vratFinalniKus(List<T> obleceni, Casoprostor casoprostor) {
        return obleceni.stream().filter(o -> o.getMinimalniTeplota() < casoprostor.getNejchladnejsiPocasi().getTeplota()).filter(o -> o.getMaximalniTeplota() > casoprostor.getNejchladnejsiPocasi().getTeplota()).filter(o -> casoprostor.getFormalnostObleceni().contains(o.getFormalni())).max(Comparator.comparing(T::getMinimalniTeplota)).orElse(null);
    }

    private AlternativniObleceni vygenerujAlternativniObleceni(List<Cepice> cepice, List<Vrsek> vrsky, List<Spodek> spodky, List<Boty> boty, Casoprostor casoprostor) {
        List<Cepice> vsechnyCepice = zkontrolujVhodnostDoPocasiAFormalnost(cepice, casoprostor);
        List<Vrsek> vsechnyVrsky = zkontrolujVhodnostDoPocasiAFormalnost(vrsky, casoprostor);
        List<Spodek> vsechnySpodky = zkontrolujVhodnostDoPocasiAFormalnost(spodky, casoprostor);
        List<Boty> vsechnyBoty = zkontrolujVhodnostDoPocasiAFormalnost(boty, casoprostor);
        return new AlternativniObleceni(vsechnyCepice, vsechnyVrsky, vsechnySpodky, vsechnyBoty);
    }

    private <T extends Obleceni> List<T> zkontrolujVhodnostDoPocasiAFormalnost(List<T> obleceni, Casoprostor casoprostor) {
        return obleceni.stream().filter(o -> o.getMinimalniTeplota() < casoprostor.getNejchladnejsiPocasi().getTeplota()).filter(o -> o.getMaximalniTeplota() > casoprostor.getNejchladnejsiPocasi().getTeplota()).filter(o -> casoprostor.getFormalnostObleceni().contains(o.getFormalni())).sorted(Comparator.comparingInt(Obleceni::getMinimalniTeplota)).collect(Collectors.toList());
    }

}


