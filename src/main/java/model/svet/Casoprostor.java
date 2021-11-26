package model.svet;

import model.obleceni.Formalni;

import java.time.LocalDateTime;
import java.util.List;

public class Casoprostor {
    private double zemepisnaSirka;
    private double zemepisnaDelka;
    private LocalDateTime pocatecniCas;
    private LocalDateTime konecnyCas;
    private Pocasi nejchladnejsiPocasi;
    private boolean dest;
    /**
     * možné formální oblečení, které je uživatel ochoten nosit,
     * například je ochoten si vzít někam málo/středně formální oblečení, tak kalkulátor
     * bere v potaz obě možnosti při výběru outfitu.
     */
    private List<Formalni> ochotnyOblect;

    public Casoprostor(double zemepisnaSirka, double zemepisnaDelka, LocalDateTime pocatecniCas, LocalDateTime konecnyCas, List<Formalni> formalni) {
        this.zemepisnaSirka = zemepisnaSirka;
        this.zemepisnaDelka = zemepisnaDelka;
        this.pocatecniCas = pocatecniCas;
        this.konecnyCas = konecnyCas;
        this.ochotnyOblect = formalni;
    }

    public Pocasi getNejchladnejsiPocasi() {
        return nejchladnejsiPocasi;
    }

    public void setNejchladnejsiPocasi(Pocasi nejchladnejsiPocasi) {
        this.nejchladnejsiPocasi = nejchladnejsiPocasi;
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

    public List<Formalni> getOchotnyOblect() {
        return ochotnyOblect;
    }

}
