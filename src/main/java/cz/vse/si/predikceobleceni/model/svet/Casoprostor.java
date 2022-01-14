package cz.vse.si.predikceobleceni.model.svet;

import cz.vse.si.predikceobleceni.model.obleceni.Formalni;

import java.time.LocalDateTime;
import java.util.List;

public class Casoprostor {
    private int id;

    private String nazev;
    private double zemepisnaSirka;
    private double zemepisnaDelka;
    private LocalDateTime pocatecniCas;
    private LocalDateTime konecnyCas;
    private Pocasi nejchladnejsiPocasi;
    private Pocasi nejteplejsiPocasi;
    private boolean dest;
    /**
     * možné formální oblečení, které je uživatel ochoten nosit,
     * například je ochoten si vzít někam málo/středně formální oblečení, tak kalkulátor
     * bere v potaz obě možnosti při výběru outfitu.
     */
    private List<Formalni> formalnostObleceni;

    public Casoprostor(String nazev, double zemepisnaSirka, double zemepisnaDelka, LocalDateTime pocatecniCas, LocalDateTime konecnyCas, List<Formalni> formalni) {
        this.nazev = nazev;
        this.zemepisnaSirka = zemepisnaSirka;
        this.zemepisnaDelka = zemepisnaDelka;
        this.pocatecniCas = pocatecniCas;
        this.konecnyCas = konecnyCas;
        this.formalnostObleceni = formalni;
    }

    public Pocasi getNejchladnejsiPocasi() {
        return nejchladnejsiPocasi;
    }

    public void setNejchladnejsiPocasi(Pocasi nejchladnejsiPocasi) {
        this.nejchladnejsiPocasi = nejchladnejsiPocasi;
    }

    public Pocasi getNejteplejsiPocasi() {
        return nejteplejsiPocasi;
    }

    public void setNejteplejsiPocasi(Pocasi nejteplejsiPocasi) {
        this.nejteplejsiPocasi = nejteplejsiPocasi;
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

    public List<Formalni> getFormalnostObleceni() {
        return formalnostObleceni;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    @Override
    public String toString() {
        return "\"" + id + "\":{" +
                "\"nazev\":\"" + nazev + "\"," +
                "\"zemepisnaSirka\":\"" + zemepisnaSirka + "\"," +
                "\"zemepisnaDelka\":\"" + zemepisnaDelka + "\"," +
                "\"pocatecniCas\":\"" + pocatecniCas + "\"," +
                "\"konecnyCas\":\"" + konecnyCas + "\"," +
                "\"formalnostObleceni\":\"" + formalnostObleceni + "\"}";
    }
}
