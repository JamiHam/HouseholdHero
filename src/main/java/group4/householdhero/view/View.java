package group4.householdhero.view;

import group4.householdhero.controller.*;
import group4.householdhero.model.Budget;
import group4.householdhero.model.Product;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class View implements IView {
    private IControllerVtoM controller;
    
    public void setController(IControllerVtoM controller) {
    	this.controller = controller;
    }
    
    public void createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) throws SQLException {
    	 controller.createBudget(id, plannedBudget, spentBudget, startDate, endDate);
    }
    
    public void updateBudget(Budget budget) throws SQLException {
    	controller.updateBudget(budget);
    }
    
    public Budget getBudget(LocalDate date) throws SQLException {
    	return controller.getBudget(date);
    }
    
    public List<Budget> getAllBudgets() {
    	return null;
    }
    
    public void createProduct(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId) throws SQLException {
    	controller.createProduct(id, name, price, bestBefore, category, budgetId, statusId);
    }
    
    public void updateProduct(Product product) throws SQLException {
    	controller.updateProduct(product);
    }
    
    public List<Product> getProducts(String status) throws SQLException {
    	return controller.getProducts(status);
    }
    
    public void changeProductStatus(Product product, String status) throws SQLException {
		controller.changeProductStatus(product, status);
	}
    
    public void deleteProduct(Product product) throws SQLException {
		controller.deleteProduct(product);
	}
    
    public void checkBestBefore() throws SQLException {
    	controller.checkBestBefore();
    }
    
    public void editProduct(Product product) throws IOException, SQLException {
    	FridgeController.editProduct(product);
    }

	public List<String> getCategories() throws SQLException {
		return controller.getCategories();
	}
}