package cz.vse.si.predikceobleceni.model.gui;

import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.javascript.event.GMapMouseEvent;
import com.dlsc.gmapsfx.javascript.event.UIEventType;
import com.dlsc.gmapsfx.javascript.object.GoogleMap;
import com.dlsc.gmapsfx.javascript.object.LatLong;
import com.dlsc.gmapsfx.javascript.object.MapOptions;
import com.dlsc.gmapsfx.javascript.object.MapTypeIdEnum;
import cz.vse.si.predikceobleceni.model.obleceni.Formalni;
import cz.vse.si.predikceobleceni.model.svet.Casoprostor;
import cz.vse.si.predikceobleceni.model.utils.Kalkulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
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
    public void handleOk() {
        if (startDate.getValue() == null || endDate.getValue() == null || latitude == 0 || longtitude == 0 || (!formalni.isSelected() && !neformalni.isSelected() && !stredne.isSelected())){
            appendLabel.setText("Musíš zadat všechy údaje");
            return;
        }

        List<Formalni> formalniList = new ArrayList<>();

        if (formalni.isSelected()){
            formalniList.add(Formalni.HODNE);
        }
        if (stredne.isSelected()){
            formalniList.add(Formalni.STREDNE);
        }
        if (neformalni.isSelected()){
            formalniList.add(Formalni.MALO);
        }

        appendLabel.setText("");
        LocalDateTime convertedStartDate = LocalDateTime.of(startDate.getValue(), LocalTime.of((int) startTime.getValue(), 0));
        LocalDateTime convertedEndDate = LocalDateTime.of(endDate.getValue(), LocalTime.of((int) endTime.getValue(), 0));
        LocalDateTime check = LocalDateTime.now();


        if ( convertedEndDate.isBefore(convertedStartDate) || convertedStartDate.isBefore(LocalDateTime.of(check.getYear(), check.getMonth(), check.getDayOfMonth(), check.getHour(), 0)) || convertedEndDate.isAfter(LocalDateTime.of(check.getYear(), check.getMonth(), check.getDayOfMonth() + 5, check.getHour(), check.getMinute()))) {
            appendLabel.setText("Chyba v datumech");
        } else {
            Kalkulator.getInstance().zjistiPocasiZApi(new Casoprostor(latitude, longtitude, convertedStartDate, convertedEndDate, formalniList));
        }
    }

    private GoogleMap map;

    private DecimalFormat formatter = new DecimalFormat("###.00000");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        googleMapView.addMapInitializedListener(() -> configureMap());
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
        });

    }

    public void otevriPridavaciOkno() {
        Dialog<ButtonType> dialog = new Dialog<>();

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        dialog.initOwner(mainGridPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        Path path = FileSystems.getDefault().getPath("./src/main/java/cz/vse/si/predikceobleceni/model/resources/pridatobleceni.fxml");

        try{
            fxmlLoader.setLocation(new URL("file:" + path.toAbsolutePath()));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.setTitle("Úprava oblečení");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        dialog.showAndWait();

    }
}
