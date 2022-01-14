package cz.vse.si.predikceobleceni.model.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public final class VolacApi {
    private static final VolacApi instance = new VolacApi();
    private static final String KLIC = "417282c5e6578006d33fca782cb9e0d5";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&units=metric&lang=cz&appid=%s";

    private VolacApi(){};

    public static VolacApi getInstance(){
        return instance;
    }

    public String zavolejApi(double zemepisnaSirka, double zemepisnaDelka) {
        HttpURLConnection spojeniSApi = null;
        try {
            URL konecneUrl = new URL(String.format(API_URL, zemepisnaSirka, zemepisnaDelka, KLIC));

            spojeniSApi = (HttpURLConnection) konecneUrl.openConnection();
            pripravSpojeni(spojeniSApi);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(spojeniSApi.getInputStream()));
            StringBuilder odpoved = new StringBuilder();
            String radek;
            while ((radek = bufferedReader.readLine()) != null) {
                odpoved.append(radek);
            }
            bufferedReader.close();
            return odpoved.toString();
        } catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        } finally {
            if (spojeniSApi != null){
                spojeniSApi.disconnect();
            }
        }
    }

    private void pripravSpojeni(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
    }

}