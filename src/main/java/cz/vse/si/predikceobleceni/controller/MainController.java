package cz.vse.si.predikceobleceni.controller;

import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.javascript.event.GMapMouseEvent;
import com.dlsc.gmapsfx.javascript.event.UIEventType;
import com.dlsc.gmapsfx.javascript.object.GoogleMap;
import com.dlsc.gmapsfx.javascript.object.LatLong;
import com.dlsc.gmapsfx.javascript.object.MapOptions;
import com.dlsc.gmapsfx.javascript.object.MapTypeIdEnum;
import cz.vse.si.predikceobleceni.model.Formalni;
import cz.vse.si.predikceobleceni.model.Obleceni;
import cz.vse.si.predikceobleceni.model.Outfit;
import cz.vse.si.predikceobleceni.svet.Casoprostor;
import cz.vse.si.predikceobleceni.utils.InternetAlert;
import cz.vse.si.predikceobleceni.utils.Kalkulator;
import cz.vse.si.predikceobleceni.utils.Persistence;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private double latitude;
    private double longtitude;
    @FXML
    private Label latitudeLabel;
    @FXML
    private Label longitudeLabel;
    @FXML
    private GoogleMapView googleMapView;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Slider startTime;
    @FXML
    private Slider endTime;
    @FXML
    private Label appendLabel;
    @FXML
    private CheckBox formalni;
    @FXML
    private CheckBox stredne;
    @FXML
    private CheckBox neformalni;
    @FXML
    private GridPane mainGridPane;
    @FXML
    private Button okButton;

    @FXML
    private void zkontrolujVsechnyUdaje() {
        appendLabel.setText("");
        okButton.setDisable(false);

        if (startDate.getValue() == null || endDate.getValue() == null || latitude == 0 || longtitude == 0 || (!formalni.isSelected() && !neformalni.isSelected() && !stredne.isSelected())) {
            okButton.setDisable(true);
        }
    }

    @FXML
    public void handleOk() {
        List<Formalni> formalniList = new ArrayList<>();

        if (formalni.isSelected()) {
            formalniList.add(Formalni.HODNE);
        }
        if (stredne.isSelected()) {
            formalniList.add(Formalni.STREDNE);
        }
        if (neformalni.isSelected()) {
            formalniList.add(Formalni.MALO);
        }

        appendLabel.setText("");
        LocalDateTime convertedStartDate = LocalDateTime.of(startDate.getValue(), LocalTime.of((int) startTime.getValue(), 0));
        LocalDateTime convertedEndDate = LocalDateTime.of(endDate.getValue(), LocalTime.of((int) endTime.getValue(), 0));
        LocalDateTime check = LocalDateTime.now();


        if (convertedEndDate.isBefore(convertedStartDate) || convertedStartDate.isBefore(LocalDateTime.of(check.getYear(), check.getMonth(), check.getDayOfMonth(), check.getHour(), 0)) || convertedEndDate.isAfter(LocalDateTime.of(check.getYear(), check.getMonth(), check.getDayOfMonth() + 5, check.getHour(), check.getMinute()))) {
            appendLabel.setText("Chyba v datumech");
        } else {

           this.zobrazZFXThread("Načítání...");

            Casoprostor casoprostor = new Casoprostor(latitude, longtitude, convertedStartDate, convertedEndDate, formalniList);

            Persistence persistence = new Persistence();
            persistence.pridejLokalitu(casoprostor);

            Outfit outfit = Kalkulator.getInstance().predpovedObleceni(casoprostor);

            this.zobrazZFXThread("");

            if (outfit == null) {
                InternetAlert.zobrazNoInternetAlert();
                appendLabel.setText("");
                return;
            }
            if (outfit.getCepice() != null) {
                if (outfit.getCepice().getMinimalniTeplota() == Integer.MIN_VALUE) {
                    InternetAlert.zobrazMalyRozsahAlert();
                    return;
                }
            }
            zobrazOknoOutfitu(outfit);
        }
    }

    private void zobrazZFXThread(String text){
        Runnable task = () -> Platform.runLater(() -> appendLabel.setText(text));
        new Thread(task).start();
    }

    private GoogleMap map;

    private final DecimalFormat formatter = new DecimalFormat("###.00000");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        okButton.setDisable(true);
        googleMapView.addMapInitializedListener(this::configureMap);

    }

    protected void configureMap() {
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(50.0832, 14.4353))
                .mapType(MapTypeIdEnum.ROADMAP)
                .zoom(10);
        map = googleMapView.createMap(mapOptions, false);

        map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
            LatLong latLong = event.getLatLong();
            latitudeLabel.setText(formatter.format(latLong.getLatitude()));
            longitudeLabel.setText(formatter.format(latLong.getLongitude()));
            latitude = latLong.getLatitude();
            longtitude = latLong.getLongitude();
            zkontrolujVsechnyUdaje();
        });


    }

    public void otevriPridavaciOkno() {
        Dialog<ButtonType> dialog = new Dialog<>();

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.initOwner(mainGridPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/resources/pridatobleceni.fxml");

        try {
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.setTitle("Přidání oblečení");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        dialog.showAndWait();

    }

    public void otevriUpravovaciOkno() {
        Dialog<ButtonType> dialog = new Dialog<>();

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.initOwner(mainGridPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/resources/upravitobleceni.fxml");

        try {
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));

            Node content = fxmlLoader.load();

            ListView<Obleceni> obleceniListView = (ListView<Obleceni>) content.lookup("#obleceniListView");

            Persistence persistence = new Persistence();
            ArrayList<Obleceni> obleceni = persistence.getAllObleceni();

            ObservableList<Obleceni> obleceniObservableList = FXCollections.observableArrayList(obleceni);
            obleceniListView.setItems(obleceniObservableList);

            dialog.getDialogPane().setContent(content);
            dialog.setTitle("Úprava oblečení");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.showAndWait();
    }

    public void otevriOknoLokalit() {
        Dialog<ButtonType> dialog = new Dialog<>();

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.initOwner(mainGridPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/resources/nacistlokalitu.fxml");

        try {
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.setTitle("Načti lokalitu");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        dialog.showAndWait();

    }

    public void otevriMazaciOkno() {
        Dialog<ButtonType> dialog = new Dialog<>();

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.initOwner(mainGridPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/resources/smazatobleceni.fxml");

        try {
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));

            Node content = fxmlLoader.load();

            ListView<Obleceni> obleceniListView = (ListView<Obleceni>) content.lookup("#obleceniListView");

            Persistence persistence = new Persistence();
            ArrayList<Obleceni> obleceni = persistence.getAllObleceni();

            ObservableList<Obleceni> obleceniObservableList = FXCollections.observableArrayList(obleceni);
            obleceniListView.setItems(obleceniObservableList);

            dialog.getDialogPane().setContent(content);
            dialog.setTitle("Smazat oblečení");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.showAndWait();
    }

    public void otevriMazaciOknoLokalit() {
        Dialog<ButtonType> dialog = new Dialog<>();

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.initOwner(mainGridPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/resources/smazatlokalitu.fxml");

        try {
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.setTitle("Odeber lokalitu");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        dialog.showAndWait();
    }

    public void zobrazOknoOutfitu(Outfit vygenerovanyOutfit) {

        List<Obleceni> zakladniObleceni = vygenerovanyOutfit.vratVsechnoZakladniObleceni();
        List<Obleceni> alternativniObleceni = vygenerovanyOutfit.vratVsechnoAlternativniObleceni();
        boolean vzitSiDestnik = vygenerovanyOutfit.isDestnik();

        Dialog<ButtonType> dialog = new Dialog<>();

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        //dialog.initOwner(mainGridPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/resources/predpovedbleceni.fxml");

        try {
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));

            Node content = fxmlLoader.load();

            ListView<Obleceni> zakladniObleceniListView = (ListView<Obleceni>) content.lookup("#zakladniObleceniListView");
            ObservableList<Obleceni> zakladniObleceniObservableList = FXCollections.observableArrayList(zakladniObleceni);
            zakladniObleceniListView.setItems(zakladniObleceniObservableList);

            ListView<Obleceni> alternativniObleceniListView = (ListView<Obleceni>) content.lookup("#alternativniObleceniListView");
            ObservableList<Obleceni> alternativniObleceniObservableList = FXCollections.observableArrayList(alternativniObleceni);
            alternativniObleceniListView.setItems(alternativniObleceniObservableList);

            Label destnikLabel = (Label) content.lookup("#destnikLabel");
            if (vzitSiDestnik) {
                destnikLabel.setText("Pravděpodobnost deště. Vezmi si deštník.");
            } else {
                destnikLabel.setText("Zřejmě nebude pršet. Nemusíš si brát deštník.");
            }

            dialog.getDialogPane().setContent(content);
            dialog.setTitle("Doporučené oblečení");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.showAndWait();
    }

    public void otevriInformaceOAplikaci() {
        Dialog<ButtonType> dialog = new Dialog<>();

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.initOwner(mainGridPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/resources/oaplikaci.fxml");

        try {
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.setTitle("O aplikaci");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        dialog.showAndWait();
    }
}
