package cz.vse.si.predikceobleceni.main;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import cz.vse.si.predikceobleceni.model.obleceni.Obleceni;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;

import java.util.ArrayList;

public class Persistence {

    private ArrayList<Obleceni> obleceni = new ArrayList<>();
    private ArrayList<Casoprostor> lokality = new ArrayList<>();

    Persistence(String obleceniJson, String lokalityJson) {
        pridejObleceni(obleceniJson);
        pridejLokality(lokalityJson);
    }

    protected void pridejObleceni(String obleceniJson) {
        Gson gson = new Gson();

        try {
            if (obleceniJson.charAt(0) == '[') {
                this.obleceni = gson.fromJson(obleceniJson, new TypeToken<ArrayList<Obleceni>>() {
                }.getType());
            } else {
                obleceni.add(gson.fromJson(obleceniJson, Obleceni.class));
            }
        } catch (JsonParseException exception) {
            System.out.println("ERROR: Zadany JSON s daty obleceni je nevalidni! Exception: " + exception);
        }
    }

    protected void pridejLokality(String lokalityJson) {
        Gson gson = new Gson();

        try {
            if (lokalityJson.charAt(0) == '[') {
                this.lokality = gson.fromJson(lokalityJson, new TypeToken<ArrayList<Casoprostor>>() {
                }.getType());
            } else {
                lokality.add(gson.fromJson(lokalityJson, Casoprostor.class));
            }
        } catch (JsonParseException exception) {
            System.out.println("ERROR: Zadany JSON s daty lokalit je nevalidni! Exception: " + exception);
        }
    }

    private String dumpObleceniJson() {
        Gson gson = new Gson();

        return gson.toJson(obleceni);
    }

    private String dumpLokalityJson() {
        Gson gson = new Gson();

        return gson.toJson(lokality);
    }
}
