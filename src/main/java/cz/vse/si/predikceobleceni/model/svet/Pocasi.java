package cz.vse.si.predikceobleceni.model.svet;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pocasi {
    private String nazev;
    private boolean dest;
    private double teplota;
    private int cas; //unixtime

    public Pocasi(boolean dest, double teplota, int cas) {
        this.dest = dest;
        this.teplota = teplota;
        this.cas = cas;
    }

    public boolean isDest() {
        return dest;
    }

    public double getTeplota() {
        return teplota;
    }

    public int getCas(){
        return cas;
    }
}
