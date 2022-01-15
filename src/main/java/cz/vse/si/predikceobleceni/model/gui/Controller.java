package cz.vse.si.predikceobleceni.model.gui;

import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.javascript.event.GMapMouseEvent;
import com.dlsc.gmapsfx.javascript.event.UIEventType;
import com.dlsc.gmapsfx.javascript.object.GoogleMap;
import com.dlsc.gmapsfx.javascript.object.LatLong;
import com.dlsc.gmapsfx.javascript.object.MapOptions;
import com.dlsc.gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    static double latitude;
    static double longtitude;
    static LocalDateTime from;
    static LocalDateTime to;
    @FXML
    private Label latitudeLabel;
    @FXML
    private Label longitudeLabel;
    @FXML
    private GoogleMapView googleMapView;
    @FXML
    private TextField fromTime;
    @FXML
    private TextField toTime;

    @FXML
    public void save() {
        System.out.println(LocalDateTime.parse(fromTime.getText()));
        System.out.println(LocalDateTime.parse(toTime.getText()));

        from = (LocalDateTime.parse(fromTime.getText()));
        to = (LocalDateTime.parse(toTime.getText()));

        System.out.println(latitude);
        System.out.println(longtitude);
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
}
