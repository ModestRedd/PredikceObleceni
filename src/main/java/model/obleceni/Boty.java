package model.obleceni;

public class Boty extends Obleceni{
    public Boty(String nazev, int minimalniTeplota, int maximalniTeplota) {
        super(nazev, Vrstva.PRVNI, CastTela.BOTY, minimalniTeplota, maximalniTeplota);
    }
}
