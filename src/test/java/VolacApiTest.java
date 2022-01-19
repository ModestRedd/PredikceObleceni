import cz.vse.si.predikceobleceni.utils.VolacApi;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class VolacApiTest {


    @Test
    public void zavolejApiTest(){
        String odpoved = VolacApi.getInstance().zavolejApi(10,10);
        assertNotNull(odpoved);

        odpoved = VolacApi.getInstance().zavolejApi(20,20);
        assertNotNull(odpoved);

        odpoved = VolacApi.getInstance().zavolejApi(10000,10000);
        assertNull(odpoved);

        odpoved = VolacApi.getInstance().zavolejApi(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertNull(odpoved);

    }

}