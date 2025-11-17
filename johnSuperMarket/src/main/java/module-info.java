module com.example.johnsupermarket {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.johnsupermarket.controller to javafx.fxml;
    opens com.example.johnsupermarket.app to javafx.fxml;
    opens com.example.johnsupermarket.loader to javafx.fxml;
    opens com.example.johnsupermarket.model to javafx.fxml;

    exports com.example.johnsupermarket.app;
    exports com.example.johnsupermarket.controller;
    exports com.example.johnsupermarket.loader;
    exports com.example.johnsupermarket.model;
}
