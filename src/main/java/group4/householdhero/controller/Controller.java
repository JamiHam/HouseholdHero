package group4.householdhero.controller;

import group4.householdhero.view.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import group4.householdhero.model.*;

public class Controller {
	private Model model;
	private View view;
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public void createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) {
		Budget budget = model.createBudget(id, plannedBudget, spentBudget, startDate, endDate);
		model.addBudgetToDatabase(budget);
	}
	
	public Budget getCurrentBudget() {
    	return model.getCurrentBudget();
    }
	
	public void createProduct(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId) {
    	Product product = model.createProduct(id, name, price, bestBefore, category, budgetId, statusId);
    	model.addProductToDatabase(product);
    }
	
	public void updateProduct(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId) {
    	Product product = model.createProduct(id, name, price, bestBefore, category, budgetId, statusId);
    	model.updateProduct(product);
    }
	
	public void deleteProduct(Product product) {
		model.deleteProduct(product);
	}
	
	public void changeProductStatus(Product product, String status) {
		model.changeProductStatus(product, status);
	}

	public void editProduct(Product product) throws IOException {
		view.editProduct(product);
	}
	
	public List<Product> getProductsInFridge() throws SQLException {
		return model.getProductsInFridge();
	}
}
