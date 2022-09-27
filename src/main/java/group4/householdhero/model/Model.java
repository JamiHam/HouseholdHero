package group4.householdhero.model;

import java.time.LocalDate;
import java.util.List;

import group4.householdhero.controller.*;

public class Model {
	
	private Controller controller;
	private String testString = "Test"; //MVC testing
	
	public Model(Controller controller) {
		this.controller = controller;
	}
	
	//MVC testing
	public String getTestString() {
		return testString;
	}
	
	public Budget createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) {
		Budget budget = new Budget(id, plannedBudget, spentBudget, startDate, endDate);
		return budget;
	}
	
	public Product createProduct(int id, String name, double price, LocalDate bestBefore, int categoryId, int budgetId, int statusId) {
    	Product product = new Product(id, name, price, bestBefore, categoryId, budgetId, statusId);
    	return product;
    }
	
	public void addBudgetToDatabase(Budget budget) {
		//dataAccessObject.addBudget(budget);
	}
	
	public void addProductToDatabase(Product product) {
		//dataAcccessObject.addProduct(product);
	}
	
	public List getProductsInFridge() {
		//return dataAccessObject.getProductsInFridge();
		return null;
	}
}
