package cz.vse.si.predikceobleceni.model.obleceni;

public class Obleceni {
    protected String nazev;
    protected Vrstva vrstva;
    protected CastTela castTela;
    protected int minimalniTeplota;
    protected int maximalniTeplota;
    protected Formalni formalni;
    private int id = -1;

    public Obleceni(int id, String nazev, Vrstva vrstva, CastTela castTela, int minimalniTeplota, int maximalniTeplota, Formalni formalni) {
        this.id = id;
        this.nazev = nazev;
        this.vrstva = vrstva;
        this.castTela = castTela;
        this.minimalniTeplota = minimalniTeplota;
        this.maximalniTeplota = maximalniTeplota;
        this.formalni = formalni;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    @Override
    public String toString() {
        return "Název: " + nazev + ", umístění: " + castTela + ", vrstva: " + vrstva;
    }
}
