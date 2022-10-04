package group4.householdhero.view;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import group4.householdhero.controller.Controller;
import group4.householdhero.model.Budget;
import group4.householdhero.model.Model;
import group4.householdhero.model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
	private static Scene scene;
	private static View view;
	private static Controller controller;
	private static Model model;
	private static FridgeController fridgeController;
	
	@Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("HouseholdHero");

    	Image icon = new Image(String.valueOf(new File("householdhero-icon-white-fridge.png")));
    	stage.getIcons().add(icon);

    	FXMLLoader loader = new FXMLLoader(View.class.getResource("StartingGUI.fxml"));
        scene = new Scene(loader.load(), 1000, 600);
        fridgeController = loader.getController();

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
    
    protected static void openProductWindow(boolean editing, Product product) throws IOException {
    	FXMLLoader loader = new FXMLLoader(View.class.getResource("AddEditProductGUI.fxml"));
    	Parent root = loader.load();
    	Stage stage = setupWindow(root);
    	
        stage.setTitle("HouseholdHero");

    	Image icon = new Image(String.valueOf(new File("householdhero-icon-white-fridge.png")));
    	stage.getIcons().add(icon);
    	
    	loader.<AddEditProductController>getController().initialize(editing, product);
    	stage.setOnHidden(e -> {
    		try {
    			fridgeController.updateTables();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
    	});
    	stage.show();
    }
    
    protected static void openBudgetWindow(boolean editing, Budget budget) throws IOException {
    	FXMLLoader loader = new FXMLLoader(View.class.getResource("AddEditBudgetGUI.fxml"));
    	Parent root = loader.load();
    	Stage stage = setupWindow(root);
    	
        stage.setTitle("HouseholdHero");

    	Image icon = new Image(String.valueOf(new File("householdhero-icon-white-fridge.png")));
    	stage.getIcons().add(icon);
    	
		loader.<AddEditBudgetController>getController().initialize(editing, budget);
    	stage.showAndWait();
    }
    
    static Stage setupWindow(Parent root) throws IOException {
    	Stage stage = new Stage();
    	stage.setScene(new Scene(root));
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.setAlwaysOnTop(true);
    	return stage;
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