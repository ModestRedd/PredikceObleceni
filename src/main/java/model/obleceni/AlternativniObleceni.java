package model.obleceni;

import java.util.List;

public class AlternativniObleceni {
    private List<Cepice> cepice;
    private List<Vrsek> vrsky;
    private List<Spodek> spodky;
    private List<Boty> boty;

    public AlternativniObleceni(List<Cepice> cepice, List<Vrsek> vrsky, List<Spodek> spodky, List<Boty> boty) {
        this.cepice = cepice;
        this.vrsky = vrsky;
        this.spodky = spodky;
        this.boty = boty;
    }

    public List<Cepice> getCepice() {
        return cepice;
    }

    public List<Vrsek> getVrsky() {
        return vrsky;
    }

    public List<Spodek> getSpodky() {
        return spodky;
    }

    public List<Boty> getBoty() {
        return boty;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Alternativní oblečení\n");
        stringBuilder.append("-------------------");
        stringBuilder.append("\nČepice:\n");
        stringBuilder.append(zkontrolujNull(cepice));
        stringBuilder.append("\nOblečení na těle:\n");
        stringBuilder.append(zkontrolujNull(vrsky));
        stringBuilder.append("\nOblečení na nohách:\n");
        stringBuilder.append(zkontrolujNull(spodky));
        stringBuilder.append("\nBoty:\n");
        stringBuilder.append(zkontrolujNull(boty));
        return stringBuilder.toString();
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
