package cz.vse.si.predikceobleceni.model.svet;

import cz.vse.si.predikceobleceni.model.obleceni.Formalni;

import java.util.List;

public class Casoprostor {
    private int idLokality = 0;

    private String nazev;
    private double zemepisnaSirka;
    private double zemepisnaDelka;
    private int pocatecniCas;
    private int konecnyCas;
    private Pocasi nejchladnejsiPocasi;
    private Pocasi nejteplejsiPocasi;
    private boolean dest;
    /**
     * možné formální oblečení, které je uživatel ochoten nosit,
     * například je ochoten si vzít někam málo/středně formální oblečení, tak kalkulátor
     * bere v potaz obě možnosti při výběru outfitu.
     */
    private List<Formalni> formalnostObleceni;

    public Casoprostor(String nazev, double zemepisnaSirka, double zemepisnaDelka, int pocatecniCas, int konecnyCas, List<Formalni> formalni) {
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

    public int getPocatecniCas() {
        return pocatecniCas;
    }

    public int getKonecnyCas() {
        return konecnyCas;
    }

    public List<Formalni> getFormalnostObleceni() {
        return formalnostObleceni;
    }

    @Override
    public String toString() {
        return "\"" + idLokality + "\":{" +
                "\"nazev\":\"" + nazev + "\"," +
                "\"zemepisnaSirka\":\"" + zemepisnaSirka + "\"," +
                "\"zemepisnaDelka\":\"" + zemepisnaDelka + "\"," +
                "\"pocatecniCas\":\"" + pocatecniCas + "\"," +
                "\"konecnyCas\":\"" + konecnyCas + "\"," +
                "\"formalnostObleceni\":\"" + formalnostObleceni + "\"}";
    }
}
