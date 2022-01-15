package cz.vse.si.predikceobleceni.main;

import cz.vse.si.predikceobleceni.model.svet.Casoprostor;
import cz.vse.si.predikceobleceni.model.utils.Kalkulator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Start {

    public static void main(String[] args) throws IOException {
        //Persistence persistence = new Persistence();

        /*
        persistence.pridejObleceni(Files.readString(Path.of("example_data/ObleceniJedno.json"), StandardCharsets.UTF_8));
        persistence.pridejLokality(Files.readString(Path.of("example_data/LokalitaJedna.json"), StandardCharsets.UTF_8));

        persistence.pridejObleceni(Files.readString(Path.of("example_data/ObleceniPole.json"), StandardCharsets.UTF_8));
        persistence.pridejLokality(Files.readString(Path.of("example_data/LokalityPole.json"), StandardCharsets.UTF_8));
        */

        /*
        persistence.dumpObleceniJson("data/Obleceni.json");
        persistence.dumpLokalityJson("data/Lokality.json");
        */

        //persistence.dumpLokalityJson();
        //persistence.dumpObleceniJson();

        //Kalkulator.getInstance().zjistiPocasiZApi(persistence.getLokality().get(0));
    }
}
