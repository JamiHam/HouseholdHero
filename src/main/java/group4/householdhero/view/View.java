package group4.householdhero.view;

import group4.householdhero.controller.*;
import group4.householdhero.model.Budget;
import group4.householdhero.model.Product;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class View {
    private Controller controller;
    
    public void setController(Controller controller) {
    	this.controller = controller;
    }
    
    public void createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) {
    	 controller.createBudget(id, plannedBudget, spentBudget, startDate, endDate);
    }
    
    public Budget getCurrentBudget() {
    	return controller.getCurrentBudget();
    }
    
    public void createProduct(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId) {
    	controller.createProduct(id, name, price, bestBefore, category, budgetId, statusId);
    }
    
    public void updateProduct(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId) {
    	controller.updateProduct(id, name, price, bestBefore, category, budgetId, statusId);
    }
    
    public void deleteProduct(Product product) {
		//dao.deleteProduct(product);
	}
    
    public void changeProductStatus(Product product, String status) {
		controller.changeProductStatus(product, status);
	}
    
    public void editProduct(Product product) throws IOException {
    	FridgeController.editProduct(product);
    }
    
    public List<Product> getProductsInFridge() throws SQLException {
    	return controller.getProductsInFridge();
    }

	public List<String> getCategories() {
		return controller.getCategories();
	}

	public List<Product> getExpiredProducts() {
		return controller.getExpiredProducts();
	}
    
    /*public List<Product> getExpiredProducts() throws SQLException {
    	//return controller.getExpiredProducts();
    }*/
}