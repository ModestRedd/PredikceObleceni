package cz.vse.si.predikceobleceni.main;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import cz.vse.si.predikceobleceni.model.obleceni.*;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Persistence {
    private ArrayList<Obleceni> obleceni = new ArrayList<>();
    private ArrayList<Casoprostor> lokality = new ArrayList<>();

    boolean jsouSeznamyObleceniAktualni = true;
    private ArrayList<Cepice> hlava = new ArrayList<Cepice>();
    private ArrayList<Vrsek> vrsek = new ArrayList<Vrsek>();
    private ArrayList<Spodek> spodek = new ArrayList<Spodek>();
    private ArrayList<Boty> boty = new ArrayList<Boty>();

    Persistence(String pathObleceniJson, String pathLokalityJson) throws IOException {
        String obleceniJson = Files.readString(Path.of(pathObleceniJson), StandardCharsets.UTF_8);
        String lokalityJson = Files.readString(Path.of(pathLokalityJson), StandardCharsets.UTF_8);

        pridejObleceni(obleceniJson);
        pridejLokality(lokalityJson);
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
            case HLAVA -> {
                this.hlava.add((Cepice) obleceniKZarazeni);
            }
            case TELO -> {
                this.vrsek.add((Vrsek) obleceniKZarazeni);
            }
            case NOHY -> {
                this.spodek.add((Spodek) obleceniKZarazeni);
            }
            case BOTY -> {
                this.boty.add((Boty) obleceniKZarazeni);
            }
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

    protected String dumpObleceniJson() {
        Gson gson = new Gson();

        return gson.toJson(obleceni);
    }

    protected void dumpObleceniJson(String pathToFile) throws IOException {
        String json = dumpObleceniJson();

        Files.write(Path.of(pathToFile), List.of(json), StandardCharsets.UTF_8);
    }

    protected String dumpLokalityJson() {
        Gson gson = new Gson();

        return gson.toJson(lokality);
    }

    protected void dumpLokalityJson(String pathToFile) throws IOException {
        String json = dumpLokalityJson();

        Files.write(Path.of(pathToFile), List.of(json), StandardCharsets.UTF_8);
    }

    public ArrayList<Casoprostor> getLokality() {
        return lokality;
    }

    private void zaradVeskereObleceni(){
        hlava = new ArrayList<Cepice>();
        vrsek = new ArrayList<Vrsek>();
        spodek = new ArrayList<Spodek>();
        boty = new ArrayList<Boty>();
        zaradObleceni(obleceni);
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
