module group4.householdhero {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	
	opens group4.householdhero.model to javafx.fxml;
    opens group4.householdhero.view to javafx.fxml;
    opens group4.householdhero.controller to javafx.fxml;
    
    exports group4.householdhero.model;
    exports group4.householdhero.view;
    exports group4.householdhero.controller;
}
