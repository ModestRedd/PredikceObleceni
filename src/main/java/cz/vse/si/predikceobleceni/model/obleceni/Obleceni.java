package cz.vse.si.predikceobleceni.model.obleceni;

public class Obleceni {
    private int idObleceni = 0;

    protected String nazev;
    protected Vrstva vrstva;
    protected CastTela castTela;
    protected int minimalniTeplota;
    protected int maximalniTeplota;
    protected Formalni formalni;

    public Obleceni(String nazev, Vrstva vrstva, CastTela castTela, int minimalniTeplota, int maximalniTeplota, Formalni formalni) {
        this.nazev = nazev;
        this.vrstva = vrstva;
        this.castTela = castTela;
        this.minimalniTeplota = minimalniTeplota;
        this.maximalniTeplota = maximalniTeplota;
        this.formalni = formalni;
    }

    public String getNazev() {
        return nazev;
    }

    public Vrstva getVrstva() {
        return vrstva;
    }

    public CastTela getCastTela() {
        return castTela;
    }

    public int getMinimalniTeplota() {
        return minimalniTeplota;
    }

    public int getMaximalniTeplota() {
        return maximalniTeplota;
    }

    public Formalni getFormalni() {
        return formalni;
    }

    @Override
    public String toString() {
        return "\"" + idObleceni + "\":{" +
                "\"castTela\":\"" + castTela + "\"," +
                "\"nazev\":\"" + nazev + "\"," +
                "\"minimalniTeplota\":\"" + minimalniTeplota + "\"," +
                "\"maximalniTeplota\":\"" + maximalniTeplota + "\"," +
                "\"vrstva\":\"" + vrstva + "\"," +
                "\"formalni\":\"" + formalni + "\"}";

    }
}
