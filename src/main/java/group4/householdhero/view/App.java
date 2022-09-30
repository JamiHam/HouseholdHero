package group4.householdhero.view;

import java.io.IOException;

import group4.householdhero.controller.Controller;
import group4.householdhero.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
	private static Scene scene;
	private static View view;
	private static Controller controller;
	private static Model model;
	
	@Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    static View getView() {
    	return view;
    }

    public static void main(String[] args) {
    	model = new Model();
    	view = new View();
    	controller = new Controller();
    	
    	model.setController(controller);
    	view.setController(controller);
    	controller.setView(view);
    	controller.setModel(model);
    	
        launch();
    }
}