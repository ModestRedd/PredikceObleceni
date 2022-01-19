package cz.vse.si.predikceobleceni.utils;

import javafx.scene.control.Alert;

public class InternetAlert {

    public static void zobrazNoInternetAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Internetové připojení");
        alert.setHeaderText("Žádné internetové připojení. Zkuste to prosím znovu.");

        alert.showAndWait();
    }

    public static void zobrazMalyRozsahAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Malý rozsah");
        alert.setHeaderText("Pro tak malý časový rozsah neznáme předpověď. Zkuste to prosím znovu.");

        alert.showAndWait();
    }
}
