package group4.householdhero.model;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import group4.householdhero.controller.Controller;

public class Model {
	private Controller controller;
	private DataAccessObject dao;
	private Testmain main;
	
	public void setController(Controller controller) {
		this.controller = controller;
		dao = new DataAccessObject(this);
		main = new Testmain(this);
	}

	public Budget createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) {
		Budget budget = new Budget(id, plannedBudget, spentBudget, startDate, endDate);
		return budget;
	}

	public Product createProduct(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId) {
    	Product product = new Product(id, name, price, bestBefore, category, budgetId, statusId, this);
    	return product;
    }
	
	public void updateProduct(Product product) {
    	//dao.updateProduct(product);
    }
	
	public void deleteProduct(Product product) {
		//dao.deleteProduct(product);
	}
	
	public void changeProductStatus(Product product, String status) {
		//dao.updateStatus(product, status);
	}
	
	public void editProduct(Product product) throws IOException {
		controller.editProduct(product);
	}

	public void addBudgetToDatabase(Budget budget) {
		//dao.addBudget(budget);
	}

	public void addProductToDatabase(Product product) {
		//dao.addProduct(product);
	}
	
	

	public List<Product> getProductsInFridge() throws SQLException {
		//return dao.getProductsInFridge();
		Product product = createProduct(1, "test", 2, LocalDate.parse("2022-01-01"), "test", 1, 1);
		Product product2 = createProduct(2, "test2", 1, LocalDate.parse("2022-09-01"), "test", 1, 1);
		List<Product> list = new ArrayList<Product>();
		list.add(product);
		list.add(product2);
		return list;
	}
}
