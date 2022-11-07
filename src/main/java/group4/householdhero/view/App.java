package group4.householdhero.view;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import group4.householdhero.controller.AddEditBudgetController;
import group4.householdhero.controller.AddEditProductController;
import group4.householdhero.controller.BudgetController;
import group4.householdhero.controller.Controller;
import group4.householdhero.controller.FridgeController;
import group4.householdhero.model.Budget;
import group4.householdhero.model.Model;
import group4.householdhero.model.Product;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * JavaFX App
 */
public class App extends Application {
	private static Scene scene;
	private static Controller controller;
	private static Model model;
	private static FridgeController fridgeController;
	private static BudgetController budgetController;
	
	@Override
    public void start(Stage stage) throws IOException {
        setTitleAndIcon(stage);
    	
    	FXMLLoader loader = getFXMLLoader("StartingGUI");
        scene = new Scene(loader.load(), 1000, 600);
        fridgeController = loader.getController();
        
        stage.setScene(scene);
        stage.show();
    }
    
    private static FXMLLoader setRoot(String fxml) throws IOException {
    	FXMLLoader loader = getFXMLLoader(fxml);
    	scene.setRoot(loader.load());
    	return loader;
    }
    
    private static FXMLLoader getFXMLLoader(String fxml) throws IOException {
    	FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    	return loader;
    }
    
    public static void showFridge() throws IOException {
    	FXMLLoader loader = setRoot("StartingGUI");
    	fridgeController = loader.getController();
    }
    
    public static void showBudget() throws IOException {
    	FXMLLoader loader = setRoot("BudgetGUI");
    	budgetController = loader.getController();
    }
    
    public static void showProductWindow(boolean editing, Product product) throws IOException, SQLException {
    	Pair<FXMLLoader, Stage> pair = setupWindow("AddEditProductGUI");
    	FXMLLoader loader = pair.getKey();
    	Stage stage = pair.getValue();
    	
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
    
    public static void showBudgetWindow(boolean editing, Budget budget) throws IOException {
    	Pair<FXMLLoader, Stage> pair = setupWindow("AddEditBudgetGUI");
    	FXMLLoader loader = pair.getKey();
    	Stage stage = pair.getValue();
    	
		loader.<AddEditBudgetController>getController().initialize(editing, budget);
		if (!editing) {
			stage.setOnCloseRequest(e -> {
				Platform.exit();
				System.exit(0);
			});
		}
    	stage.showAndWait();
    }
    
    private static Pair<FXMLLoader, Stage> setupWindow(String fxml) throws IOException {
    	FXMLLoader loader = getFXMLLoader(fxml);
    	Parent root = loader.load();
    	Stage stage = new Stage();
    	
    	setTitleAndIcon(stage);
    	stage.setScene(new Scene(root));
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.setAlwaysOnTop(true);
    	
    	return new Pair<FXMLLoader, Stage> (loader, stage);
    }
    
    private static void setTitleAndIcon(Stage stage) {
		stage.setTitle("HouseholdHero");

    	Image icon = new Image(String.valueOf(new File("householdhero-icon-white-fridge.png")));
    	stage.getIcons().add(icon);
	}
    
    public static Controller getController() {
    	return controller;
    }

    public static void main(String[] args) {
    	model = new Model();
    	controller = new Controller();
    	
    	model.setController(controller);
    	controller.setModel(model);
    	
        launch();
    }
}