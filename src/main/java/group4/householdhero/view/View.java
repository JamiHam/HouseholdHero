package group4.householdhero.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import group4.householdhero.controller.*;
import group4.householdhero.model.Product;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * JavaFX App
 */
public class View extends Application {

    private static Scene scene;
    private static Controller controller;
    
    public void init() {
    	View.controller = new Controller(this);
    }

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

    public static void main(String[] args) {
        launch();
    }
    
    //MVC testing
    static String getTestString() throws IOException{
    	return controller.getTestString();
    }
    
    static void createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) {
    	 controller.createBudget(id, plannedBudget, spentBudget, startDate, endDate);
    }
    
    static void createProduct(int id, String name, double price, LocalDate bestBefore, int categoryId, int budgetId, int statusId) {
    	controller.createProduct(id, name, price, bestBefore, categoryId, budgetId, statusId);
    }
    
    static List<Product> getProductsInFridge() {
    	return controller.getProductsInFridge();
    }

}