package group4.householdhero.view;

import group4.householdhero.controller.*;
import group4.householdhero.model.Product;

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