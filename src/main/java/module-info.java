module com.example.cinema_projet {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires themoviedbapi;
    requires java.sql;
    requires mail;
    requires activation;
    requires java.desktop;

    opens com.example.cinema_projet to javafx.fxml;
    exports com.example.cinema_projet;
    exports model;
    opens model to javafx.fxml;
}