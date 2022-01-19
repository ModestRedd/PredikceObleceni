package cz.vse.si.predikceobleceni.model.svet;

import cz.vse.si.predikceobleceni.model.obleceni.Formalni;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Casoprostor {
    private int id;

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

    /*
    public Casoprostor(String nazev, double zemepisnaSirka, double zemepisnaDelka, LocalDateTime pocatecniCas, LocalDateTime konecnyCas, List<Formalni> formalni) {
        this.nazev = nazev;
        this.zemepisnaSirka = zemepisnaSirka;
        this.zemepisnaDelka = zemepisnaDelka;
        this.pocatecniCas = pocatecniCas;
        this.konecnyCas = konecnyCas;
        this.formalnostObleceni = formalni;
    }
     */

    public Casoprostor(double zemepisnaSirka, double zemepisnaDelka, LocalDateTime pocatecniCas, LocalDateTime konecnyCas, List<Formalni> formalni) {
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

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Zem. sirka: " + String.format("%.6g", zemepisnaSirka) +
                ", zem. delka: " + String.format("%.6g", zemepisnaDelka) +
                ",\npočáteční čas: " + pocatecniCas +
                ", konečný čas: " + konecnyCas +
                ",\nformálnost: " + formalnostObleceni;

        /*
        return "Casoprostor{" +
                "id=" + id +
                ", zemepisnaSirka=" + zemepisnaSirka +
                ", zemepisnaDelka=" + zemepisnaDelka +
                ", pocatecniCas=" + pocatecniCas +
                ", konecnyCas=" + konecnyCas +
                ", nejchladnejsiPocasi=" + nejchladnejsiPocasi +
                ", nejteplejsiPocasi=" + nejteplejsiPocasi +
                ", dest=" + dest +
                ", formalnostObleceni=" + formalnostObleceni +
                '}';
         */
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Casoprostor that = (Casoprostor) o;
        return Double.compare(that.zemepisnaSirka, zemepisnaSirka) == 0 && Double.compare(that.zemepisnaDelka, zemepisnaDelka) == 0 && Objects.equals(pocatecniCas, that.pocatecniCas) && Objects.equals(konecnyCas, that.konecnyCas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zemepisnaSirka, zemepisnaDelka, pocatecniCas, konecnyCas);
    }
}
