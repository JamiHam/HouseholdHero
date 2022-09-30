module group4.householdhero {
    requires javafx.controls;
    requires javafx.fxml;
<<<<<<< HEAD
	requires javafx.graphics;
=======
	requires javafx.base;
>>>>>>> main

    opens group4.householdhero.view to javafx.fxml;
    opens group4.householdhero.model to javafx.fxml;
    exports group4.householdhero.view;
    exports group4.householdhero.model;
}
