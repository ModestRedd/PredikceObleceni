package cz.vse.si.predikceobleceni.model.svet;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pocasi {
    private String nazev;
    private boolean dest;
    private double teplota;
    private LocalDateTime localDateTime;

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
