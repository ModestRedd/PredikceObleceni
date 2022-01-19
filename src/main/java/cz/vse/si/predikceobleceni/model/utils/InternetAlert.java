package cz.vse.si.predikceobleceni.model.utils;

import javafx.scene.control.Alert;

public class InternetAlert {

    public static void generujAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Internetové připojení");
        alert.setHeaderText("Žádné internetové připojení. Zkuste to prosím znovu.");

        alert.showAndWait();
    }
}
