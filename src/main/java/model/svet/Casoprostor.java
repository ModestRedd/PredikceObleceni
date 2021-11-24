package model.svet;

import java.time.LocalDateTime;

public class Casoprostor {
    private double zemepisnaSirka;
    private double zemepisnaDelka;
    private LocalDateTime pocatecniCas;
    private LocalDateTime konecnyCas;
    private Pocasi nejchladnejsi;
    private boolean dest;

    public Casoprostor(double zemepisnaSirka, double zemepisnaDelka, LocalDateTime pocatecniCas, LocalDateTime konecnyCas) {
        this.zemepisnaSirka = zemepisnaSirka;
        this.zemepisnaDelka = zemepisnaDelka;
        this.pocatecniCas = pocatecniCas;
        this.konecnyCas = konecnyCas;
    }

    public Pocasi getNejchladnejsi() {
        return nejchladnejsi;
    }

    public void setNejchladnejsi(Pocasi nejchladnejsi) {
        this.nejchladnejsi = nejchladnejsi;
    }

    public boolean isDest() {
        return dest;
    }

    public void setDest(boolean dest) {
        this.dest = dest;
    }

    public double getZemepisnaSirka() {
        return zemepisnaSirka;
    }

    public double getZemepisnaDelka() {
        return zemepisnaDelka;
    }

    public LocalDateTime getPocatecniCas() {
        return pocatecniCas;
    }

    public LocalDateTime getKonecnyCas() {
        return konecnyCas;
    }
}
