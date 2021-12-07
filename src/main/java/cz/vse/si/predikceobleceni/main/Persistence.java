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
                ArrayList<Obleceni> obleceniProPridani = new ArrayList<Obleceni>(
                        gson.fromJson(obleceniJson,
                                new TypeToken<ArrayList<Obleceni>>() {
                                }.getType()));

                int obleceniSize = this.obleceni.size();
                for (int i = 0; i < obleceniProPridani.size(); i++) {
                    obleceniProPridani.get(i).setId(obleceniSize + i);
                }

                obleceni.addAll(obleceniProPridani);

            } else {
                Obleceni obleceniProPridani = gson.fromJson(obleceniJson, Obleceni.class);
                obleceniProPridani.setId(obleceni.size());
                obleceni.add(obleceniProPridani);
            }
        } catch (JsonParseException exception) {
            System.out.println("ERROR: Zadany JSON s daty obleceni je nevalidni! Exception: " + exception);
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

    protected String dumpLokalityJson() {
        Gson gson = new Gson();

        return gson.toJson(lokality);
    }
}
