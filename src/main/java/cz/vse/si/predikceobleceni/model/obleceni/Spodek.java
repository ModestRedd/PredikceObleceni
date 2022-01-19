package cz.vse.si.predikceobleceni.model.obleceni;

public class Spodek extends Obleceni {
    public Spodek(Obleceni obleceni) {
        super(obleceni.getId(),
                obleceni.getNazev(),
                obleceni.getVrstva(),
                obleceni.getCastTela(),
                obleceni.getMinimalniTeplota(),
                obleceni.getMaximalniTeplota(),
                obleceni.getFormalni());
    }

    public Spodek(String nazev, Vrstva vrstva, int minimalniTeplota, int maximalniTeplota, Formalni formalni) {
        super(nazev, vrstva, CastTela.NOHY, minimalniTeplota, maximalniTeplota, formalni);
    }
}
