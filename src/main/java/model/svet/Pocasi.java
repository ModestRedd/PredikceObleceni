package model.svet;

public class Pocasi {
    private boolean dest;
    private int pravdepodobnostDeste;
    private int teplota;

    public Pocasi(boolean dest, int pravdepodobnostDeste, int teplota) {
        this.dest = dest;
        this.pravdepodobnostDeste = pravdepodobnostDeste;
        this.teplota = teplota;
    }

    public boolean isDest() {
        return dest;
    }

    public int getPravdepodobnostDeste() {
        return pravdepodobnostDeste;
    }

    public int getTeplota() {
        return teplota;
    }
}
