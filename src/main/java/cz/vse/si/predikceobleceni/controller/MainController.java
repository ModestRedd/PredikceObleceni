package cz.vse.si.predikceobleceni.controller;

import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.javascript.event.GMapMouseEvent;
import com.dlsc.gmapsfx.javascript.event.UIEventType;
import com.dlsc.gmapsfx.javascript.object.GoogleMap;
import com.dlsc.gmapsfx.javascript.object.LatLong;
import com.dlsc.gmapsfx.javascript.object.MapOptions;
import com.dlsc.gmapsfx.javascript.object.MapTypeIdEnum;
import cz.vse.si.predikceobleceni.model.obleceni.Formalni;
import cz.vse.si.predikceobleceni.model.obleceni.Obleceni;
import cz.vse.si.predikceobleceni.model.obleceni.Outfit;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;
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
    private final DecimalFormat formatter = new DecimalFormat("###.00000");
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
    private GoogleMap map;

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


        if (chybaVDatumech(convertedStartDate, convertedEndDate, check)) {
            appendLabel.setText("Chyba v datumech");
        } else {

            this.zobrazZFXThread("Na????t??n??...");

            Casoprostor casoprostor = new Casoprostor(latitude, longtitude, convertedStartDate, convertedEndDate, formalniList);

            Persistence.getInstance().pridejLokalitu(casoprostor);

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

    public void otevriMazaciOkno() {
        Dialog<ButtonType> dialog = new Dialog<>();

        setupParent(dialog);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/view/smazatobleceni.fxml");

        try {
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));

            Node content = fxmlLoader.load();

            ListView<Obleceni> obleceniListView = (ListView<Obleceni>) content.lookup("#obleceniListView");

            ArrayList<Obleceni> obleceni = Persistence.getInstance().getAllObleceni();

            ObservableList<Obleceni> obleceniObservableList = FXCollections.observableArrayList(obleceni);
            obleceniListView.setItems(obleceniObservableList);

            dialog.getDialogPane().setContent(content);
            dialog.setTitle("Smazat oble??en??");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.showAndWait();
    }

    public void otevriMazaciOknoLokalit() {
        otevriOknoPodleJmena("smazatlokalitu.fxml", "Odeber lokalitu");
    }

    public void zobrazOknoOutfitu(Outfit vygenerovanyOutfit) {

        List<Obleceni> zakladniObleceni = vygenerovanyOutfit.vratVsechnoZakladniObleceni();
        List<Obleceni> alternativniObleceni = vygenerovanyOutfit.vratVsechnoAlternativniObleceni();
        boolean vzitSiDestnik = vygenerovanyOutfit.isDestnik();

        Dialog<ButtonType> dialog = new Dialog<>();
        setupParent(dialog);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/view/predpovedbleceni.fxml");

        try {
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));
            Node content = fxmlLoader.load();
            zaradObleceniDoGUI(zakladniObleceni, alternativniObleceni, content);
            Label destnikLabel = (Label) content.lookup("#destnikLabel");
            if (vzitSiDestnik) {
                destnikLabel.setText("Pravd??podobnost de??t??. Vezmi si de??tn??k.");
            } else {
                destnikLabel.setText("Z??ejm?? nebude pr??et. Nemus???? si br??t de??tn??k.");
            }

            dialog.getDialogPane().setContent(content);
            dialog.setTitle("Doporu??en?? oble??en??");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.showAndWait();
    }

    public void otevriInformaceOAplikaci() {
        otevriOknoPodleJmena("oaplikaci.fxml", "O aplikaci");
    }

    public void otevriPridavaciOkno() {
        otevriOknoPodleJmena("pridatobleceni.fxml", "P??id??n?? oble??en??");
    }

    public void otevriUpravovaciOkno() {
        Dialog<ButtonType> dialog = new Dialog<>();

        setupParent(dialog);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/view/upravitobleceni.fxml");

        try {
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));

            Node content = fxmlLoader.load();

            ListView<Obleceni> obleceniListView = (ListView<Obleceni>) content.lookup("#obleceniListView");

            ArrayList<Obleceni> obleceni = Persistence.getInstance().getAllObleceni();

            ObservableList<Obleceni> obleceniObservableList = FXCollections.observableArrayList(obleceni);
            obleceniListView.setItems(obleceniObservableList);

            dialog.getDialogPane().setContent(content);
            dialog.setTitle("??prava oble??en??");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.showAndWait();
    }

    public void otevriOknoLokalit() {
        otevriOknoPodleJmena("nacistlokalitu.fxml", "Na??ti lokalitu");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        okButton.setDisable(true);
        googleMapView.addMapInitializedListener(this::configureMap);

    }

    @FXML
    private void zkontrolujVsechnyUdaje() {
        appendLabel.setText("");
        okButton.setDisable(false);

        if (startDate.getValue() == null || endDate.getValue() == null || latitude == 0 || longtitude == 0 || (!formalni.isSelected() && !neformalni.isSelected() && !stredne.isSelected())) {
            okButton.setDisable(true);
        }
    }


    private boolean chybaVDatumech(LocalDateTime convertedStartDate, LocalDateTime convertedEndDate, LocalDateTime check) {
        return convertedEndDate.isBefore(convertedStartDate) || convertedStartDate.isBefore(LocalDateTime.of(check.getYear(), check.getMonth(), check.getDayOfMonth(), check.getHour(), 0)) || convertedEndDate.isAfter(LocalDateTime.of(check.getYear(), check.getMonth(), check.getDayOfMonth() + 5, check.getHour(), check.getMinute()));
    }

    private void zobrazZFXThread(String text) {
        Runnable task = () -> Platform.runLater(() -> appendLabel.setText(text));
        new Thread(task).start();
    }


    private void configureMap() {
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


    private void otevriOknoPodleJmena(String s, String s2) {
        Dialog<ButtonType> dialog = new Dialog<>();

        setupParent(dialog);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("src/main/java/cz/vse/si/predikceobleceni/view/" + s);

        try {
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.setTitle(s2);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        dialog.showAndWait();
    }

    private void setupParent(Dialog<ButtonType> dialog) {
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.initOwner(mainGridPane.getScene().getWindow());
    }


    private void zaradObleceniDoGUI(List<Obleceni> zakladniObleceni, List<Obleceni> alternativniObleceni, Node content) {
        ListView<Obleceni> zakladniObleceniListView = (ListView<Obleceni>) content.lookup("#zakladniObleceniListView");
        ObservableList<Obleceni> zakladniObleceniObservableList = FXCollections.observableArrayList(zakladniObleceni);
        zakladniObleceniListView.setItems(zakladniObleceniObservableList);

        ListView<Obleceni> alternativniObleceniListView = (ListView<Obleceni>) content.lookup("#alternativniObleceniListView");
        ObservableList<Obleceni> alternativniObleceniObservableList = FXCollections.observableArrayList(alternativniObleceni);
        alternativniObleceniListView.setItems(alternativniObleceniObservableList);
    }
}
