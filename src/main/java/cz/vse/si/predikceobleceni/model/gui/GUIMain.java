package cz.vse.si.predikceobleceni.model.gui;

import cz.vse.si.predikceobleceni.model.utils.InternetAlert;
import cz.vse.si.predikceobleceni.model.utils.Kalkulator;
import cz.vse.si.predikceobleceni.model.utils.Persistence;
import cz.vse.si.predikceobleceni.model.utils.VolacApi;
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
import java.util.Objects;


public class GUIMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        try {

            String json = VolacApi.getInstance().zavolejApi(10,10);
            if (json == null){
                throw new Exception("Bez internetového připojení");
            }
            FXMLLoader loader = new FXMLLoader();
            URL url = new File("src/main/java/cz/vse/si/predikceobleceni/model/resources/gui.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            //Parent root = loader.load(getClass().getClassLoader().getResource("gui.fxml"));

            Scene scene = new Scene(root);
            Path path = FileSystems.getDefault().getPath("./src/main/java/cz/vse/si/predikceobleceni/model/resources/clouds.png");

            stage.getIcons().add(new Image("file:" + path.toAbsolutePath()));
            stage.setTitle("Předpověď oblečení");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            InternetAlert.generujAlert();
            System.exit(0);
        }
    }

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

}
