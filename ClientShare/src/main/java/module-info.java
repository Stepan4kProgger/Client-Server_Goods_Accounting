module res.cwclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;


    opens res.cwclient to javafx.fxml;
    exports res.cwclient;

    opens res.common to javafx.fxml;
    exports res.common;
}