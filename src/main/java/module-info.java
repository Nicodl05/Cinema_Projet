module com.example.cinema_projet {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires themoviedbapi;

    opens com.example.cinema_projet to javafx.fxml;
    exports com.example.cinema_projet;
}