package model.svet;

import model.obleceni.Formalni;

import java.time.LocalDateTime;
import java.util.List;

public class Casoprostor {
    private double zemepisnaSirka;
    private double zemepisnaDelka;
    private LocalDateTime pocatecniCas;
    private LocalDateTime konecnyCas;
    private Pocasi nejchladnejsi;
    private boolean dest;
    private List<Formalni> formalni;

    public Casoprostor(double zemepisnaSirka, double zemepisnaDelka, LocalDateTime pocatecniCas, LocalDateTime konecnyCas, List<Formalni> formalni) {
        this.zemepisnaSirka = zemepisnaSirka;
        this.zemepisnaDelka = zemepisnaDelka;
        this.pocatecniCas = pocatecniCas;
        this.konecnyCas = konecnyCas;
        this.formalni = formalni;
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

    public List<Formalni> getFormalni() {
        return formalni;
    }
}
