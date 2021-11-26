package model.obleceni;

public class Vrsek extends Obleceni{
    public Vrsek(String nazev, Vrstva vrstva, int minimalniTeplota, int maximalniTeplota, Formalni formalni) {
        super(nazev, vrstva, CastTela.TELO, minimalniTeplota, maximalniTeplota, formalni);
    }
}
