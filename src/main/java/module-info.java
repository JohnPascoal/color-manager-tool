module ao.jedp.colormanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens ao.jedp.colormanager to javafx.fxml;
    exports ao.jedp.colormanager;
}