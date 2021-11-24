package model.obleceni;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Outfit {
    private Cepice cepice;
    private List<Vrsek> obleceniNaTele;
    private List<Spodek> obleceniNaNohach;
    private Boty boty;
    private boolean destnik;

    public Outfit(Cepice cepice, List<Vrsek> obleceniNaTele, List<Spodek> obleceniNaNohach, Boty boty, boolean destnik) {
        this.cepice = cepice;
        this.obleceniNaTele = obleceniNaTele;
        this.obleceniNaNohach = obleceniNaNohach;
        this.boty = boty;
        this.destnik = destnik;
    }

    public Cepice getCepice() {
        return cepice;
    }

    public List<Vrsek> getObleceniNaTele() {
        return obleceniNaTele;
    }

    public List<Spodek> getObleceniNaNohach() {
        return obleceniNaNohach;
    }

    public Boty getBoty() {
        return boty;
    }

    public boolean isDestnik() {
        return destnik;
    }
}
