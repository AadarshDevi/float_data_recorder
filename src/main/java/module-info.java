module com.miramar_water_jets {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;

    opens com.miramar_water_jets to javafx.fxml;

    exports com.miramar_water_jets;
}
