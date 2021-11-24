package model.obleceni;

public class Spodek extends Obleceni{
    public Spodek(String nazev, Vrstva vrstva, int minimalniTeplota, int maximalniTeplota) {
        super(nazev, vrstva, CastTela.NOHY, minimalniTeplota, maximalniTeplota);
    }
}
