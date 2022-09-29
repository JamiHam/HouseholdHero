module group4.householdhero {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;

    opens group4.householdhero.view to javafx.fxml;
    opens group4.householdhero.model to javafx.fxml;
    exports group4.householdhero.view;
    exports group4.householdhero.model;
}
