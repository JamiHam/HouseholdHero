package group4.householdhero.view;

import java.io.File;
import java.io.IOException;

import group4.householdhero.controller.Controller;
import group4.householdhero.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        stage.setTitle("HouseholdHero");

    	Image icon = new Image(String.valueOf(new File("householdhero-icon.png")));
    	stage.getIcons().add(icon);

        scene = new Scene(loadFXML("StartingGUI"), 1000, 600);
        //scene = new Scene(loadFXML("AddEditProductGUI"), 600, 500);

        // Tällä CSS-lisäys ei onnistu, CSS lisätty Scene Builderin kautta AnchorPaneen
        // scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
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