package cz.vse.si.predikceobleceni.model.obleceni;

public class Boty extends Obleceni{
    public Boty(Obleceni obleceni) {
        super(obleceni.getId(),
                obleceni.getNazev(),
                obleceni.getVrstva(),
                obleceni.getCastTela(),
                obleceni.getMinimalniTeplota(),
                obleceni.getMaximalniTeplota(),
                obleceni.getFormalni());
    }
    public Boty(String nazev, int minimalniTeplota, int maximalniTeplota, Formalni formalni) {
        super(nazev, Vrstva.PRVNI, CastTela.BOTY, minimalniTeplota, maximalniTeplota, formalni);
    }
}
