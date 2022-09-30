package group4.householdhero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import group4.householdhero.controller.Controller;

public class Model {
<<<<<<< HEAD
	private Controller controller;
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
=======

	private Controller controller;
	private String testString = "Test"; //MVC testing

	public Model(Controller controller) {
		this.controller = controller;
	}

	//MVC testing
	public String getTestString() {
		return testString;
	}

>>>>>>> ba021822eb352c5560fbc531ad3178cd9c5ad4fb
	public Budget createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) {
		Budget budget = new Budget(id, plannedBudget, spentBudget, startDate, endDate);
		return budget;
	}

	public Product createProduct(int id, String name, double price, LocalDate bestBefore, int categoryId, int budgetId, int statusId) {
    	Product product = new Product(id, name, price, bestBefore, categoryId, budgetId, statusId, this);
    	return product;
    }
<<<<<<< HEAD
	
	public void editProduct(int id) {
		controller.editProduct(id);
	}
	
=======

>>>>>>> ba021822eb352c5560fbc531ad3178cd9c5ad4fb
	public void addBudgetToDatabase(Budget budget) {
		//dataAccessObject.addBudget(budget);
	}

	public void addProductToDatabase(Product product) {
		//dataAcccessObject.addProduct(product);
	}
<<<<<<< HEAD
	
	public List<Product> getProductsInFridge() {
=======

	public List getProductsInFridge() {
>>>>>>> ba021822eb352c5560fbc531ad3178cd9c5ad4fb
		//return dataAccessObject.getProductsInFridge();
		Product product = createProduct(1, "test", 2, LocalDate.parse("2022-01-01"), 1, 1, 1);
		Product product2 = createProduct(2, "test2", 1, LocalDate.parse("2022-09-01"), 1, 1, 1);
		List<Product> list = new ArrayList<Product>();
		list.add(product);
		list.add(product2);
		return list;
	}
}
