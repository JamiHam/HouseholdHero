package group4.householdhero.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import group4.householdhero.model.*;
import group4.householdhero.view.App;

public class Controller implements IController {
	private IModel model;
	
	public void setModel(IModel model) {
		this.model = model;
	}
	
	
	
	public void createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) throws SQLException {
		Budget budget = model.createBudget(id, plannedBudget, spentBudget, startDate, endDate);
		model.addBudgetToDatabase(budget);
	}
	
	public void updateBudget(Budget budget) throws SQLException {
		model.updateBudget(budget);
	}
	
	public Budget getBudget(LocalDate date) throws SQLException {
    	return model.getBudget(date);
    }
	
	public List<Budget> getAllBudgets() throws SQLException {
		return model.getAllBudgets();
	}
	
	public boolean checkBudgets(LocalDate startDate, LocalDate endDate) throws SQLException {
		return model.checkBudgets(startDate, endDate);
	}
	
	
	
	public void createProduct(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId) throws SQLException {
    	Product product = model.createProduct(id, name, price, bestBefore, category, budgetId, statusId);
    	model.addProductToDatabase(product);
    }
	
	public void updateProduct(Product product) throws SQLException {
    	model.updateProduct(product);
    }
	
	public List<Product> getProducts(String status) throws SQLException {
		return model.getProducts(status);
	}
	
	public List<Product> getProductsByBudget(int budgetId) throws SQLException {
		return model.getProductsByBudget(budgetId);
	}
	
	public void changeProductStatus(Product product, String status) throws SQLException {
		model.changeProductStatus(product, status);
	}
	
	public void deleteProduct(Product product) throws SQLException {
		model.deleteProduct(product);
	}
	
	public void checkBestBefore() throws SQLException {
    	model.checkBestBefore();
    }

	public void editProduct(Product product) throws IOException, SQLException {
		FridgeController.editProduct(product);
	}
	
	

	public List<String> getCategories() throws SQLException {
		return model.getCategories();
	}
	
	
	
	public void showBudget() throws IOException {
		App.showBudget();
	}
	
	public void showFridge() throws IOException {
		App.showFridge();
	}
	
	public void showProductWindow(boolean editing, Product product) throws IOException, SQLException {
		App.showProductWindow(editing, product);
	}
	
	public void showBudgetWindow(boolean editing, Budget budget) throws IOException, SQLException {
		App.showBudgetWindow(editing, budget);
	}
}
