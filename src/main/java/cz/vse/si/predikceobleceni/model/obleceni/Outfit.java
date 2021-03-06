package cz.vse.si.predikceobleceni.model.obleceni;

import java.util.ArrayList;
import java.util.List;

public class Outfit {
    private final Cepice cepice;
    private final List<Vrsek> obleceniNaTele;
    private final List<Spodek> obleceniNaNohach;
    private final Boty boty;
    private final boolean destnik;

    /**
     * Alternativní oblečení obsahuje všechno oblečení, které je možné si obléknout v daném počasí
     */
    private AlternativniObleceni alternativniObleceni;

    public Outfit(Cepice cepice, List<Vrsek> obleceniNaTele, List<Spodek> obleceniNaNohach, Boty boty, boolean destnik) {
        this.cepice = cepice;
        this.obleceniNaTele = obleceniNaTele;
        this.obleceniNaNohach = obleceniNaNohach;
        this.boty = boty;
        this.destnik = destnik;
    }

    public AlternativniObleceni getAlternativniObleceni() {
        return alternativniObleceni;
    }

    public void setAlternativniObleceni(AlternativniObleceni alternativniObleceni) {
        this.alternativniObleceni = alternativniObleceni;
    }

    public Cepice getCepice() {
        return cepice;
    }

    public List<Vrsek> getObleceniNaTele() {
        return obleceniNaTele;
    }

    public List<Spodek> getObleceniNaNohach() {
        return obleceniNaNohach;
    }

    public Boty getBoty() {
        return boty;
    }

    public boolean isDestnik() {
        return destnik;
    }

    public List<Obleceni> vratVsechnoZakladniObleceni() {
        List<Obleceni> vsechnoObleceni = new ArrayList<>();

        if (!(cepice == null)) {
            vsechnoObleceni.add(cepice);
        }
        vsechnoObleceni.addAll(obleceniNaTele);
        vsechnoObleceni.addAll(obleceniNaNohach);
        if (!(boty == null)) {
            vsechnoObleceni.add(boty);
        }

        return vsechnoObleceni;
    }

    public List<Obleceni> vratVsechnoAlternativniObleceni() {
        return alternativniObleceni.getVsechnoObleceni();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Outfit\n");
        stringBuilder.append("-------------------");
        stringBuilder.append("\nČepice:\n");
        stringBuilder.append(zkontrolujNull(cepice));
        stringBuilder.append("\nOblečení na těle:\n");
        stringBuilder.append(zkontrolujNull(obleceniNaTele));
        stringBuilder.append("\nOblečení na nohách:\n");
        stringBuilder.append(zkontrolujNull(obleceniNaNohach));
        stringBuilder.append("\nBoty:\n");
        stringBuilder.append(zkontrolujNull(boty));
        stringBuilder.append("\nDeštník:\n\t");
        stringBuilder.append(destnik ? "ano" : "ne");
        stringBuilder.append("\n\n");
        stringBuilder.append(alternativniObleceni.toString());
        return stringBuilder.toString();
    }

    private <T extends Obleceni> String zkontrolujNull(T obleceni) {
        if (obleceni == null) {
            return "\tneedefinováno\n";
        } else {
            return "\t" + obleceni + "\n";
        }
    }

    private <T extends Obleceni> String zkontrolujNull(List<T> obleceni) {
        if (obleceni == null || obleceni.isEmpty()) {
            return "\tneedefinováno\n";
        } else {
            return listNaString(obleceni);
        }
    }

    private <T extends Obleceni> String listNaString(List<T> obleceni) {
        StringBuilder stringBuilder = new StringBuilder();
        obleceni.forEach(o -> stringBuilder.append("\t" + o.toString() + "\n"));
        return stringBuilder.toString();
    }
}
