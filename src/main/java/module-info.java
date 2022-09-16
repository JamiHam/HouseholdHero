module group4.householdhero {
    requires javafx.controls;
    requires javafx.fxml;

    opens group4.householdhero.view to javafx.fxml;
    exports group4.householdhero.view;
}
