package model.obleceni;

public class Obleceni {
    protected final String nazev;
    protected Vrstva vrstva;
    protected  CastTela castTela;
    protected int minimalniTeplota;
    protected int maximalniTeplota;


    public Obleceni(String nazev, Vrstva vrstva, CastTela castTela, int minimalniTeplota, int maximalniTeplota) {
        this.nazev = nazev;
        this.vrstva = vrstva;
        this.castTela = castTela;
        this.minimalniTeplota = minimalniTeplota;
        this.maximalniTeplota = maximalniTeplota;
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
}
