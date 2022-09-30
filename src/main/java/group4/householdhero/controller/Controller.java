package group4.householdhero.controller;

import group4.householdhero.view.*;

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
	
	public void createProduct(int id, String name, double price, LocalDate bestBefore, int categoryId, int budgetId, int statusId) {
    	Product product = model.createProduct(id, name, price, bestBefore, categoryId, budgetId, statusId);
    	model.addProductToDatabase(product);
    }
	
	public void editProduct(int id) {
		view.editProduct(id);
	}
	
	public List<Product> getProductsInFridge() throws SQLException {
		return model.getProductsInFridge();
	}
}