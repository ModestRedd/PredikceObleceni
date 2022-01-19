package cz.vse.si.predikceobleceni.model.svet;

import java.time.LocalDateTime;

public class Pocasi {
    private final boolean dest;
    private final double teplota;
    private final LocalDateTime localDateTime;
    private String nazev;

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
