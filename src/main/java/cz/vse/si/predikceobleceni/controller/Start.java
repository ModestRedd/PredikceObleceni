package cz.vse.si.predikceobleceni.controller;

import cz.vse.si.predikceobleceni.utils.InternetAlert;
import cz.vse.si.predikceobleceni.utils.VolacApi;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public class Start extends Application {

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        try {
            String json = VolacApi.getInstance().zavolejApi(10, 10);
            if (json == null) {
                throw new Exception("Bez internetového připojení");
            }
            URL url = new File("src/main/java/cz/vse/si/predikceobleceni/view/gui.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene scene = new Scene(root);
            Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/view/clouds.png");

            stage.getIcons().add(new Image("file:" + path.toAbsolutePath()));
            stage.setTitle("Předpověď oblečení");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            InternetAlert.zobrazNoInternetAlert();
            System.exit(0);
        }
    }

}
