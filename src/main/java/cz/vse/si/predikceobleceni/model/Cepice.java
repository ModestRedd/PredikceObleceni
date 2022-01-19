package cz.vse.si.predikceobleceni.model;

public class Cepice extends Obleceni {
    public Cepice(Obleceni obleceni) {
        super(obleceni.getId(),
                obleceni.getNazev(),
                obleceni.getVrstva(),
                obleceni.getCastTela(),
                obleceni.getMinimalniTeplota(),
                obleceni.getMaximalniTeplota(),
                obleceni.getFormalni());
    }

    public Cepice(String nazev, int minimalniTeplota, int maximalniTeplota, Formalni formalni) {
        super(nazev, Vrstva.PRVNI, CastTela.HLAVA, minimalniTeplota, maximalniTeplota, formalni);
    }

}
