import cz.vse.si.predikceobleceni.model.obleceni.Formalni;
import cz.vse.si.predikceobleceni.model.obleceni.Outfit;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;
import cz.vse.si.predikceobleceni.utils.Kalkulator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class KalkulatorTest {

    @Test
    void predpovedObleceni() {
        LocalDateTime aktualniCas = LocalDateTime.now();
        Casoprostor casoprostor = new Casoprostor(10,10,aktualniCas,aktualniCas.plusDays(2), Arrays.asList(Formalni.HODNE));
        Outfit outfit = Kalkulator.getInstance().predpovedObleceni(casoprostor);
        assertNotNull(outfit);

        casoprostor = new Casoprostor(10,10,aktualniCas,aktualniCas.plusDays(5), Arrays.asList(Formalni.HODNE));
        outfit = Kalkulator.getInstance().predpovedObleceni(casoprostor);
        assertNotNull(outfit);

        casoprostor = new Casoprostor(10,10,aktualniCas,aktualniCas.minusDays(50), Arrays.asList(Formalni.HODNE));
        outfit = Kalkulator.getInstance().predpovedObleceni(casoprostor);
        assertNull(outfit.getAlternativniObleceni());

        //pokud se stala chyba při volání api, vrátí se null
        casoprostor = new Casoprostor(-1000,-1000,aktualniCas,aktualniCas.plusDays(2),Arrays.asList(Formalni.HODNE));
        outfit = Kalkulator.getInstance().predpovedObleceni(casoprostor);
        assertNull(outfit);

        casoprostor = new Casoprostor(1245,1245,aktualniCas,aktualniCas.minusDays(20),Arrays.asList(Formalni.HODNE));
        outfit = Kalkulator.getInstance().predpovedObleceni(casoprostor);
        assertNull(outfit);

        //pokud se povedlo zavolat api, ale nepovedlo přiřadit nejchladnější počasí - špatné datumy či nedostatečnou prodlevou
        //mezi nimi - nepovedlo se vyfiltrovat počasí, je vygenerován dummy outfit o jedné čepici
        casoprostor = new Casoprostor(10,10,aktualniCas,aktualniCas.minusDays(40),Arrays.asList(Formalni.HODNE));
        outfit = Kalkulator.getInstance().predpovedObleceni(casoprostor);
        assertEquals(Integer.MIN_VALUE,outfit.getCepice().getMinimalniTeplota());

        casoprostor = new Casoprostor(10,10,aktualniCas,aktualniCas, Arrays.asList(Formalni.HODNE));
        outfit = Kalkulator.getInstance().predpovedObleceni(casoprostor);
        assertNotNull(outfit);
        assertEquals(Integer.MIN_VALUE,outfit.getCepice().getMinimalniTeplota());
    }


}