package cz.vse.si.predikceobleceni.svet;

import java.time.LocalDateTime;

public class Pocasi {
    private String nazev;
    private final boolean dest;
    private final double teplota;
    private final LocalDateTime localDateTime;

    public Pocasi(boolean dest, double teplota, LocalDateTime localDateTime) {
        this.dest = dest;
        this.teplota = teplota;
        this.localDateTime = localDateTime;
    }

    public boolean isDest() {
        return dest;
    }

    public double getTeplota() {
        return teplota;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
