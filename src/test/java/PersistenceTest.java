import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import cz.vse.si.predikceobleceni.model.obleceni.*;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;
import cz.vse.si.predikceobleceni.utils.Persistence;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersistenceTest {


    @Test
    public void pridejLokalituTest() throws IOException {

        Persistence db = Persistence.getInstance();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime1 = now.plusDays(3);
        Casoprostor newyork;
        newyork = new Casoprostor(50.067776416264024, -73.90930203421330, now.plusSeconds(10), localDateTime1, Arrays.asList(Formalni.HODNE));
        db.pridejLokalitu(newyork);

        FileReader json = new FileReader("data/Lokality.json");

        Casoprostor newYorkZDb = db.getLokality().get(db.getLokality().size() - 1);
        assertEquals(50.067776416264024, newYorkZDb.getZemepisnaSirka());
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(json);
        String zemepisnaSirka = jsonArray.get(jsonArray.size() - 1).getAsJsonObject().get("zemepisnaSirka").toString();

        assertEquals(50.067776416264024, Double.parseDouble(zemepisnaSirka));
    }

    @Test
    public void pridejObleceniTest() throws IOException {
        Persistence db = Persistence.getInstance();

        Obleceni mikina = new Obleceni("svetr", Vrstva.PRVNI, CastTela.TELO, 0, 12, Formalni.STREDNE);
        db.pridejObleceni(mikina);

        FileReader json = new FileReader("data/Obleceni.json");
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(json);
        String minimalniTeplota = jsonArray.get(jsonArray.size() - 1).getAsJsonObject().get("minimalniTeplota").toString();
        String maximalniTeplota = jsonArray.get(jsonArray.size() - 1).getAsJsonObject().get("maximalniTeplota").toString();

        assertEquals("0", minimalniTeplota);
        assertEquals("12", maximalniTeplota);

    }


    @Test
    public void zkontrolujJsonSoubor() throws IOException {

        Files.delete(FileSystems.getDefault().getPath("data/Obleceni.json"));
        Persistence.getInstance().pridejObleceni(new Boty("Mokasíny",-10,20, Formalni.MALO));
        Files.exists(FileSystems.getDefault().getPath("data/Obleceni.json"));

        Files.delete(FileSystems.getDefault().getPath("data/Lokality.json"));
        Persistence.getInstance().pridejLokalitu(new Casoprostor(10,10, LocalDateTime.now(), LocalDateTime.now().plusDays(2), Arrays.asList(Formalni.HODNE)));
        Files.exists(FileSystems.getDefault().getPath("data/Lokality.json"));

    }



}