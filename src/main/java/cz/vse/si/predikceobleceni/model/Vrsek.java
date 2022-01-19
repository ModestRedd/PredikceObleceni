package cz.vse.si.predikceobleceni.model;

public class Vrsek extends Obleceni {
    public Vrsek(Obleceni obleceni) {
        super(obleceni.getId(),
                obleceni.getNazev(),
                obleceni.getVrstva(),
                obleceni.getCastTela(),
                obleceni.getMinimalniTeplota(),
                obleceni.getMaximalniTeplota(),
                obleceni.getFormalni());
    }

    public Vrsek(String nazev, Vrstva vrstva, int minimalniTeplota, int maximalniTeplota, Formalni formalni) {
        super(nazev, vrstva, CastTela.TELO, minimalniTeplota, maximalniTeplota, formalni);
    }
}
