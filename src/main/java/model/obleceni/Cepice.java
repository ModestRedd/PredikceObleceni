package model.obleceni;

public class Cepice extends Obleceni {
    public Cepice(String nazev, int minimalniTeplota, int maximalniTeplota) {
        super(nazev, Vrstva.PRVNI, CastTela.HLAVA, minimalniTeplota, maximalniTeplota);
    }

}
