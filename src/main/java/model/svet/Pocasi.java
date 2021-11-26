package model.svet;

public class Pocasi {
    private boolean dest;
    private int teplota;

    public Pocasi(boolean dest, int teplota) {
        this.dest = dest;
        this.teplota = teplota;
    }

    public boolean isDest() {
        return dest;
    }

    public int getTeplota() {
        return teplota;
    }
}
