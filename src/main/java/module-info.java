module group4.householdhero {
    requires javafx.controls;
    requires javafx.fxml;
<<<<<<< HEAD
<<<<<<< HEAD
	requires javafx.graphics;
=======
	requires javafx.base;
>>>>>>> main
=======
	requires java.sql;
>>>>>>> ba021822eb352c5560fbc531ad3178cd9c5ad4fb

    opens group4.householdhero.view to javafx.fxml;
    opens group4.householdhero.model to javafx.fxml;
    exports group4.householdhero.view;
    exports group4.householdhero.model;
}
