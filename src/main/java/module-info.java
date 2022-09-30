module group4.householdhero {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens group4.householdhero.view to javafx.fxml;
    exports group4.householdhero.view;
}
