package group4.householdhero.view;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import group4.householdhero.controller.Controller;
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
	private static View view;
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

    /*static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }*/
    
    private static FXMLLoader setRoot(String fxml) throws IOException {
    	FXMLLoader loader = getFXMLLoader(fxml);
    	scene.setRoot(loader.load());
    	return loader;
    }
    
    private static FXMLLoader getFXMLLoader(String fxml) throws IOException {
    	FXMLLoader loader = new FXMLLoader(View.class.getResource(fxml + ".fxml"));
    	return loader;
    }
    
    protected static void showFridge() throws IOException {
    	FXMLLoader loader = setRoot("StartingGUI");
    	fridgeController = loader.getController();
    }
    
    protected static void showBudget() throws IOException {
    	FXMLLoader loader = setRoot("BudgetGUI");
    	budgetController = loader.getController();
    }
    
    protected static void showProductWindow(boolean editing, Product product) throws IOException, SQLException {
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
    
    protected static void showBudgetWindow(boolean editing, Budget budget) throws IOException {
    	Pair<FXMLLoader, Stage> pair = setupWindow("AddEditBudgetGUI");
    	FXMLLoader loader = pair.getKey();
    	Stage stage = pair.getValue();
    	
		loader.<AddEditBudgetController>getController().initialize(editing, budget);
		stage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
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