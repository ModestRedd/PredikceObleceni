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
     * Nejprve dojde k zjištění nejchladnějšího počasí a zjištění, jestli bude v tomto rozmezí času pršet.
     * Dochází k nastavení těchto hodnot v časoprostoru.
     * Tyto 2 hodnoty jsou poté použity ke generaci outfitu.
     */
    public Outfit predpovedObleceni(Casoprostor casoprostor) {
        priradNejchladnejsiPocasiADest(casoprostor);
        return vygenerujOutfit(casoprostor);
    }


    private void priradNejchladnejsiPocasiADest(Casoprostor casoprostor) {
        List<Pocasi> pocasi = zjistiPocasiZApi(casoprostor);
        casoprostor.setNejchladnejsi(vyberNejchladnejsi(pocasi));
        casoprostor.setDest(zjistiJestliBudePrset(pocasi));
    }
    /**
     * V této metodě dochází ke scrapenutí dat z API
     */
    //
    //doplní Dimitrii
    private List<Pocasi> zjistiPocasiZApi(Casoprostor casoprostor){
        return null;
    }
    private Pocasi vyberNejchladnejsi(List<Pocasi> mnozinaPocasi) {
        return mnozinaPocasi.stream().min(Comparator.comparing(Pocasi::getTeplota)).orElse(null);
    }
    private boolean zjistiJestliBudePrset(List<Pocasi> mnozinaPocasi){
        return mnozinaPocasi.stream().anyMatch(Pocasi::isDest);
    }

    /**
     * @param casoprostor - časoprostor s nastavenými hodnotami pro déšť a nejchladnější počasí.
     * Při existenci více uživatelem definovaných kusů oblečení (např. uživatel zadal vícero bot)
     * se vyberou ty s nejvyšší nejnižší přípustnou hodnotou pro daný kus oblečení. (protože v časoprostoru
     * je jinak tepleji, takže pokud je za den v Praze nejnižší teplota za den 20°C a existenci bot pro -10°C až 22°C
     * a dalších bot pro 20°C až 40°C má samozřejmě smysl si dát boty pro 20-40)
     *
     */

    private Outfit vygenerujOutfit(Casoprostor casoprostor) {
        int teplota = casoprostor.getNejchladnejsi().getTeplota();

        //scrape dat z databáze a mapování na oblečení - Jirka doplní metody
        List<Cepice> cepice = null;
        List<Vrsek> vrsky = null;
        List<Spodek> spodky = null;
        List<Boty> boty = null;

        List<Vrsek> prvniVrstvaTelo = vrsky.stream().filter(vrsek -> vrsek.getVrstva() == Vrstva.PRVNI).collect(Collectors.toList());
        List<Vrsek> druhaVrstvaTelo = vrsky.stream().filter(vrsek -> vrsek.getVrstva() == Vrstva.DRUHA).collect(Collectors.toList());
        List<Vrsek> tretiVrstvaTelo = vrsky.stream().filter(vrsek -> vrsek.getVrstva() == Vrstva.TRETI).collect(Collectors.toList());

        List<Spodek> prvniVrstvaSpodek = spodky.stream().filter(spodek -> spodek.getVrstva() == Vrstva.PRVNI).collect(Collectors.toList());
        List<Spodek> druhaVrstvaSpodek = spodky.stream().filter(spodek -> spodek.getVrstva() == Vrstva.DRUHA).collect(Collectors.toList());
        List<Spodek> tretiVrstvaSpodek = spodky.stream().filter(spodek -> spodek.getVrstva() == Vrstva.TRETI).collect(Collectors.toList());

        //vybíráme oblečení, nejprve všechno, co matchuje s danou teplotou, a pak vybíráme podle nejvyšší minimální teploty (protože v časoprostoru je jinak tepleji)
        Cepice finalniCepice = cepice.stream().filter(c -> c.getMinimalniTeplota() > teplota && c.getMaximalniTeplota() < teplota).max(Comparator.comparing(Cepice::getMinimalniTeplota)).orElse(null);
        Boty finalniBoty = boty.stream().filter(b -> b.getMinimalniTeplota() > teplota && b.getMaximalniTeplota() < teplota).max(Comparator.comparing(Boty::getMinimalniTeplota)).orElse(null);
        List<Vrsek> finalniVrsky = new ArrayList<>();
        List<Spodek> finalniSpodky = new ArrayList<>();
        boolean destnik = casoprostor.isDest();

        Vrsek prvniVrsek = prvniVrstvaTelo.stream().filter(v -> v.getMinimalniTeplota() > teplota && v.getMaximalniTeplota() < teplota).max(Comparator.comparing(Vrsek::getMinimalniTeplota)).orElse(null);
        Vrsek druhyVrsek = druhaVrstvaTelo.stream().filter(v -> v.getMinimalniTeplota() > teplota && v.getMaximalniTeplota() < teplota).max(Comparator.comparing(Vrsek::getMinimalniTeplota)).orElse(null);
        Vrsek tretiVrsek = tretiVrstvaTelo.stream().filter(v -> v.getMinimalniTeplota() > teplota && v.getMaximalniTeplota() < teplota).max(Comparator.comparing(Vrsek::getMinimalniTeplota)).orElse(null);

        Spodek prvniSpodek = prvniVrstvaSpodek.stream().filter(s -> s.getMinimalniTeplota() > teplota && s.getMaximalniTeplota() < teplota).max(Comparator.comparing(Spodek::getMinimalniTeplota)).orElse(null);
        Spodek druhySpodek = druhaVrstvaSpodek.stream().filter(s -> s.getMinimalniTeplota() > teplota && s.getMaximalniTeplota() < teplota).max(Comparator.comparing(Spodek::getMinimalniTeplota)).orElse(null);
        Spodek tretiSpodek = tretiVrstvaSpodek.stream().filter(s -> s.getMinimalniTeplota() > teplota && s.getMaximalniTeplota() < teplota).max(Comparator.comparing(Spodek::getMinimalniTeplota)).orElse(null);

        finalniVrsky.add(prvniVrsek);
        if (prvniVrsek != null){
            finalniVrsky.add(prvniVrsek);
        }
        if (druhyVrsek != null) {
            finalniVrsky.add(druhyVrsek);
        }
        if (tretiVrsek != null){
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

}


