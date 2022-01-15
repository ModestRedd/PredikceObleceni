package cz.vse.si.predikceobleceni.model.gui;

import cz.vse.si.predikceobleceni.model.utils.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Objects;


public class GUIMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        URL url = new File("src/main/java/cz/vse/si/predikceobleceni/model/resources/gui.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        //Parent root = loader.load(getClass().getClassLoader().getResource("gui.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles.css");

        stage.setTitle("GUI test");
        stage.setScene(scene);
        stage.show();
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
