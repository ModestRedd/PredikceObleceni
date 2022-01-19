package cz.vse.si.predikceobleceni.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import cz.vse.si.predikceobleceni.model.obleceni.*;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Persistence {
    private static final Persistence persistence = new Persistence();

    public static Persistence getInstance() {
        return persistence;
    }

    private final String pathToObleceni = "data/Obleceni.json";
    private final String pathToLokality = "data/Lokality.json";

    private ArrayList<Obleceni> obleceni = new ArrayList<>();
    private ArrayList<Casoprostor> lokality = new ArrayList<>();

    boolean jsouSeznamyObleceniAktualni = true;
    private ArrayList<Cepice> hlava = new ArrayList<Cepice>();
    private ArrayList<Vrsek> vrsek = new ArrayList<Vrsek>();
    private ArrayList<Spodek> spodek = new ArrayList<Spodek>();
    private ArrayList<Boty> boty = new ArrayList<Boty>();

    private Persistence() {
        /*
        String obleceniJson = Files.readString(Path.of(pathToObleceni), StandardCharsets.UTF_8);
        String lokalityJson = Files.readString(Path.of(pathToLokality), StandardCharsets.UTF_8);
         */

        String obleceniJson = "";
        String lokalityJson = "";

        try {
            obleceniJson = new String(Files.readAllBytes(Paths.get(pathToObleceni)));
        } catch (Exception e) {
            //System.out.println("[ERROR] Doslo k chybe pri nacitani dat. Jsou dostupne datove soubory?" + e);
            obleceniJson = "[]";
        }

        try {
            lokalityJson = new String(Files.readAllBytes(Paths.get(pathToLokality)));
        } catch (Exception e) {
            //System.out.println("[ERROR] Doslo k chybe pri nacitani dat. Jsou dostupne datove soubory?" + e);
            lokalityJson = "[]";
        }

        pridejObleceni(obleceniJson);
        pridejLokality(lokalityJson);
    }

    private void znovuNactiObleceni() {
        jsouSeznamyObleceniAktualni = false;
        obleceni = new ArrayList<>();
        hlava = new ArrayList<>();
        vrsek = new ArrayList<>();
        spodek = new ArrayList<>();
        boty = new ArrayList<>();

        String obleceniJson = "";

        try {
            obleceniJson = new String(Files.readAllBytes(Paths.get(pathToObleceni)));
        } catch (Exception e) {
            obleceniJson = "[]";
        }

        pridejObleceni(obleceniJson);
    }

    private void znovuNactiLokality() {
        lokality = new ArrayList<>();

        String lokalityJson = "";

        try {
            lokalityJson = new String(Files.readAllBytes(Paths.get(pathToLokality)));
        } catch (Exception e) {
            //System.out.println("[ERROR] Doslo k chybe pri nacitani dat. Jsou dostupne datove soubory?" + e);
            lokalityJson = "[]";
        }

        pridejLokality(lokalityJson);
    }

    public void pridejObleceni(Obleceni kusObleceni) {
        jsouSeznamyObleceniAktualni = false;

        int id = obleceni.size();

        for (Obleceni element :
                obleceni) {
            if (element.getId() == kusObleceni.getId()) {
                id = element.getId();
                obleceni.remove(element);
                break;
            }
        }

        kusObleceni.setId(id);

        zaradObleceni(kusObleceni);
        jsouSeznamyObleceniAktualni = true;

        obleceni.add(kusObleceni);

        dumpObleceniJson();
    }

    protected void pridejObleceni(String obleceniJson) {
        jsouSeznamyObleceniAktualni = false;

        Gson gson = new Gson();

        try {
            if (obleceniJson.charAt(0) == '[') {
                ArrayList<Obleceni> obleceniProPridani = new ArrayList<Obleceni>(
                        gson.fromJson(obleceniJson,
                                new TypeToken<ArrayList<Obleceni>>() {
                                }.getType()));

                int obleceniSize = this.obleceni.size();
                for (int i = 0; i < obleceniProPridani.size(); i++) {
                    obleceniProPridani.get(i).setId(obleceniSize + i);
                }

                zaradObleceni(obleceniProPridani);
                jsouSeznamyObleceniAktualni = true;

                obleceni.addAll(obleceniProPridani);

            } else {
                Obleceni obleceniProPridani = gson.fromJson(obleceniJson, Obleceni.class);
                obleceniProPridani.setId(obleceni.size());

                zaradObleceni(obleceniProPridani);
                jsouSeznamyObleceniAktualni = true;

                obleceni.add(obleceniProPridani);
            }
        } catch (JsonParseException exception) {
            System.out.println("ERROR: Zadany JSON s daty obleceni je nevalidni! Exception: " + exception);
        }
    }

    private void zaradObleceni(ArrayList<Obleceni> obleceniKZarazeni) {
        obleceniKZarazeni.forEach(this::zaradObleceni);
    }

    private void zaradObleceni(Obleceni obleceniKZarazeni) {
        switch (obleceniKZarazeni.getCastTela()) {
            case HLAVA:
                Cepice cepice = new Cepice(obleceniKZarazeni);
                this.hlava.add(cepice);
                break;

            case TELO:
                Vrsek vrsek = new Vrsek(obleceniKZarazeni);
                this.vrsek.add(vrsek);
                break;

            case NOHY:
                Spodek spodek = new Spodek(obleceniKZarazeni);
                this.spodek.add(spodek);
                break;

            case BOTY:
                Boty boty = new Boty(obleceniKZarazeni);
                this.boty.add(boty);
                break;
        }
    }

    public void odeberObleceniPodleId(int id) {
        for (Obleceni element :
                obleceni) {
            if (element.getId() == id) {
                jsouSeznamyObleceniAktualni = false;
                obleceni.remove(element);
                break;
            }
        }

        dumpObleceniJson();
        zaradVeskereObleceni();
    }

    public void odeberLokalituPodleId(int id) {
        for (Casoprostor element :
                lokality) {
            if (element.getId() == id) {
                lokality.remove(element);
                break;
            }
        }

        dumpLokalityJson();
    }

    protected void pridejLokality(String lokalityJson) {
        Gson gson = new Gson();

        try {
            if (lokalityJson.charAt(0) == '[') {
                ArrayList<Casoprostor> lokalityProPridani = new ArrayList<Casoprostor>(
                        gson.fromJson(lokalityJson,
                                new TypeToken<ArrayList<Casoprostor>>() {
                                }.getType()));

                int lokalitySize = this.lokality.size();
                for (int i = 0; i < lokalityProPridani.size(); i++) {
                    lokalityProPridani.get(i).setId(lokalitySize + i);
                }

                lokality.addAll(lokalityProPridani);

            } else {
                Casoprostor lokalityProPridani = gson.fromJson(lokalityJson, Casoprostor.class);
                lokalityProPridani.setId(lokality.size());
                lokality.add(lokalityProPridani);
            }
        } catch (JsonParseException exception) {
            System.out.println("ERROR: Zadany JSON s daty lokalit je nevalidni! Exception: " + exception);
        } finally {
            List<Casoprostor> kOdstraneni = new ArrayList<>();
            for (Casoprostor lokalita : lokality) {
                if (lokalita.getPocatecniCas().isBefore(LocalDateTime.now())) {
                    kOdstraneni.add(lokalita);
                }
            }
            lokality.removeAll(kOdstraneni);
            kOdstraneni.forEach(lokalita -> this.odeberLokalituPodleId(lokalita.getId()));
        }
    }

    public void pridejLokalitu(Casoprostor lokalita) {
        int id = lokality.size();
        lokalita.setId(id);

        for (Casoprostor element :
                lokality) {
            if (lokalita.equals(element)) {

                return;
            }
        }


        lokality.add(lokalita);

        dumpLokalityJson();
    }


    private void dumpObleceniJson() {
        Gson gson = new Gson();

        String json = gson.toJson(obleceni);

        try {
            Files.write(Paths.get(pathToObleceni), json.getBytes());
        } catch (Exception exception) {
            System.out.println("[ERROR] Nelze zapisovat do souboru obleceni!" + exception);
        }
        //Files.write(Path.of(pathToObleceni), List.of(json), StandardCharsets.UTF_8);

        znovuNactiObleceni();
    }

    private void dumpLokalityJson() {
        Gson gson = new Gson();

        String json = gson.toJson(lokality);

        try {
            Files.write(Paths.get(pathToLokality), json.getBytes());
        } catch (Exception exception) {
            System.out.println("[ERROR] Nelze zapisovat do souboru lokalit!" + exception);
        }
        //Files.write(Path.of(pathToLokality), List.of(json), StandardCharsets.UTF_8);

        znovuNactiLokality();
    }

    public ArrayList<Casoprostor> getLokality() {
        return lokality;
    }

    private void zaradVeskereObleceni() {
        hlava = new ArrayList<>();
        vrsek = new ArrayList<>();
        spodek = new ArrayList<>();
        boty = new ArrayList<>();
        zaradObleceni(this.obleceni);
        jsouSeznamyObleceniAktualni = true;
    }

    public ArrayList<Cepice> getHlava() {
        if (!jsouSeznamyObleceniAktualni) {
            zaradVeskereObleceni();
        }

        return hlava;
    }

    public ArrayList<Vrsek> getVrsek() {
        if (!jsouSeznamyObleceniAktualni) {
            zaradVeskereObleceni();
        }

        return vrsek;
    }

    public ArrayList<Spodek> getSpodek() {
        if (!jsouSeznamyObleceniAktualni) {
            zaradVeskereObleceni();
        }

        return spodek;
    }

    public ArrayList<Boty> getBoty() {
        if (!jsouSeznamyObleceniAktualni) {
            zaradVeskereObleceni();
        }

        return boty;
    }

    public ArrayList<Obleceni> getAllObleceni() {
        return obleceni;
    }
}
