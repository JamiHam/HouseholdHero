package group4.householdhero.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import group4.householdhero.controller.*;
import group4.householdhero.model.Product;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class View {
    private Controller controller;
    
<<<<<<< HEAD
    public void init() {
    	View.controller = new Controller(this);
    }

    @Override
    public void start(Stage stage) throws IOException {
    	stage.setTitle("HouseholdHero");
    	
    	Image icon = new Image(String.valueOf(new File("householdhero-icon.png")));
    	stage.getIcons().add(icon);
    	
        scene = new Scene(loadFXML("StartingGUI"), 1000, 600);
        
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

    public static void main(String[] args) {
        launch();
=======
    public void setController(Controller controller) {
    	this.controller = controller;
>>>>>>> main
    }
    
    public void createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) {
    	 controller.createBudget(id, plannedBudget, spentBudget, startDate, endDate);
    }
    
    public void createProduct(int id, String name, double price, LocalDate bestBefore, int categoryId, int budgetId, int statusId) {
    	controller.createProduct(id, name, price, bestBefore, categoryId, budgetId, statusId);
    }
    
    public void editProduct(int id) {
    	FridgeController.editProduct(id);
    }
    
    public List<Product> getProductsInFridge() {
    	return controller.getProductsInFridge();
    }

}