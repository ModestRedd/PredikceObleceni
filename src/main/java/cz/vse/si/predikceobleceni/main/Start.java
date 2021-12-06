package cz.vse.si.predikceobleceni.main;

import com.google.gson.Gson;
import cz.vse.si.predikceobleceni.model.obleceni.*;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;

import java.util.List;

public class Start {

    public static void main(String[] args) {
        Obleceni mikina = new Vrsek("Mikina", Vrstva.DRUHA, 10, 20, Formalni.MALO);
        System.out.println("Pouze toString(): " + mikina);
        Obleceni kalhoty = new Spodek("Kalhoty", Vrstva.PRVNI, -10, 40, Formalni.HODNE);
        System.out.println("Pouze toString(): " + kalhoty);
        Casoprostor les = new Casoprostor("Les", 50.123, 13.240, 1638822222, 1638832222, List.of(Formalni.MALO));
        System.out.println("Pouze toString(): " + les);
        Casoprostor skola = new Casoprostor("Skola", 50.456, 14.987, 1531234567, 1636665557, List.of(Formalni.MALO, Formalni.STREDNE));
        System.out.println("Pouze toString(): " + skola);

        System.out.println("--------------------------------------------------");

        Gson g = new Gson();

        String mikinaJson = g.toJson(mikina);
        System.out.println("JSON: " + mikinaJson);
        String kalhotyJson = g.toJson(kalhoty);
        System.out.println("JSON: " + kalhotyJson);
        String lesJson = g.toJson(les);
        System.out.println("JSON: " + lesJson);
        String skolaJson = g.toJson(skola);
        System.out.println("JSON: " + skolaJson);

        System.out.println("--------------------------------------------------");

        Obleceni mikinaGson = g.fromJson(mikinaJson, Obleceni.class);
        System.out.println("Imported from JSON: " + mikinaGson);
        Obleceni kalhotyGson = g.fromJson(kalhotyJson, Obleceni.class);
        System.out.println("Imported from JSON: " + kalhotyGson);
        Casoprostor lesGson = g.fromJson(lesJson, Casoprostor.class);
        System.out.println("Imported from JSON: " + lesGson);
        Casoprostor skolaGson = g.fromJson(skolaJson, Casoprostor.class);
        System.out.println("Imported from JSON: " + skolaGson);


        /*
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String jsonString = gson.toJson(mikina);
        System.out.println(jsonString);
         */
    }
}
