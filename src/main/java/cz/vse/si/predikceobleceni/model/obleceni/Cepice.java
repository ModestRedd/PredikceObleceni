package cz.vse.si.predikceobleceni.model.obleceni;

public class Cepice extends Obleceni {
    public Cepice(String nazev, int minimalniTeplota, int maximalniTeplota, Formalni formalni) {
        super(nazev, Vrstva.PRVNI, CastTela.HLAVA, minimalniTeplota, maximalniTeplota, formalni);
    }

}
