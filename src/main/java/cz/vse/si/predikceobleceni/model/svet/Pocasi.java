package cz.vse.si.predikceobleceni.model.svet;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pocasi {
    private boolean dest;
    private int teplota;
    private LocalDateTime cas;

    public Pocasi(boolean dest, int teplota, LocalDateTime cas) {
        this.dest = dest;
        this.teplota = teplota;
        this.cas = cas;
    }

    public boolean isDest() {
        return dest;
    }

    public int getTeplota() {
        return teplota;
    }

    public LocalDateTime getCas(){
        return cas;

    }
}
