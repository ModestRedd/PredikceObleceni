package cz.vse.si.predikceobleceni.model.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import cz.vse.si.predikceobleceni.model.obleceni.*;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class Persistence {
    private static cz.vse.si.predikceobleceni.model.utils.Persistence persistence;

    static {
        try {
            persistence = new Persistence();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static cz.vse.si.predikceobleceni.model.utils.Persistence getInstance() {
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

    public Persistence() throws IOException {
        /*
        String obleceniJson = Files.readString(Path.of(pathToObleceni), StandardCharsets.UTF_8);
        String lokalityJson = Files.readString(Path.of(pathToLokality), StandardCharsets.UTF_8);
         */
        String obleceniJson = new String(Files.readAllBytes(Paths.get(pathToObleceni)));
        String lokalityJson = new String(Files.readAllBytes(Paths.get(pathToLokality)));

        pridejObleceni(obleceniJson);
        pridejLokality(lokalityJson);
    }

    public void pridejObleceni(Obleceni kusObleceni) throws IOException {
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

    protected void pridejObleceni(String obleceniJson) throws IOException {
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
        }
    }

    public void pridejLokalitu(Casoprostor lokalita) throws IOException {
        int id = lokality.size();

        for (Casoprostor element :
                lokality) {
            if (element.getId() == lokalita.getId()) {
                id = element.getId();
                lokality.remove(element);
                break;
            }
        }

        lokalita.setId(id);

        lokality.add(lokalita);

        dumpLokalityJson();
    }


    protected void dumpObleceniJson() throws IOException {
        Gson gson = new Gson();

        String json = gson.toJson(obleceni);

        Files.write(Paths.get(pathToObleceni), json.getBytes());
        //Files.write(Path.of(pathToObleceni), List.of(json), StandardCharsets.UTF_8);
    }

    protected void dumpLokalityJson() throws IOException {
        Gson gson = new Gson();

        String json = gson.toJson(lokality);

        Files.write(Paths.get(pathToLokality), json.getBytes());
        //Files.write(Path.of(pathToLokality), List.of(json), StandardCharsets.UTF_8);
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
}
