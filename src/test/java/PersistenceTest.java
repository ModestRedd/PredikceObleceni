import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import cz.vse.si.predikceobleceni.model.obleceni.CastTela;
import cz.vse.si.predikceobleceni.model.obleceni.Formalni;
import cz.vse.si.predikceobleceni.model.obleceni.Obleceni;
import cz.vse.si.predikceobleceni.model.obleceni.Vrstva;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;
import cz.vse.si.predikceobleceni.utils.Persistence;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersistenceTest {
    public List<Formalni> formalnostObleceni;


    @Test
    public void pridejLokalituTest() throws IOException {

        Persistence db = Persistence.getInstance();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime1 = now.plusDays(3);
        Casoprostor newyork;
        newyork = new Casoprostor(50.067776416264024, -73.90930203421330, now, localDateTime1, formalnostObleceni);
        db.pridejLokalitu(newyork);

        System.out.println(db.getLokality());

        // ------------

        FileReader json = new FileReader("data/Lokality.json");
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(json);
        String zemepisnaSirka = jsonArray.get(0).getAsJsonObject().get("zemepisnaSirka").toString();
        String rain = jsonArray.get(0).getAsJsonObject().get("dest").toString();

        assertEquals("50.067776416264024", zemepisnaSirka);
        assertEquals("false", rain);
    }

    @Test
    public void pridejObleceniTest() throws IOException {
        Persistence db = Persistence.getInstance();

        Obleceni mikina = new Obleceni("svetr", Vrstva.PRVNI, CastTela.TELO, 0, 12, Formalni.STREDNE);
        db.pridejObleceni(mikina);

        FileReader json = new FileReader("data/Obleceni.json");
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(json);
        String minimalniTeplota = jsonArray.get(5).getAsJsonObject().get("minimalniTeplota").toString();
        String maximalniTeplota = jsonArray.get(5).getAsJsonObject().get("maximalniTeplota").toString();

        assertEquals("0", minimalniTeplota);
        assertEquals("12", maximalniTeplota);

    }





}
