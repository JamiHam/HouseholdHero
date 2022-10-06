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
	//private Testmain main;
	
	public void setController(Controller controller) {
		this.controller = controller;
		dao = new DataAccessObject(this);
		//main = new Testmain();
	}

	public Budget createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) {
		Budget budget = new Budget(id, plannedBudget, spentBudget, startDate, endDate);
		return budget;
	}
	
	public Budget getCurrentBudget() throws SQLException {
    	return dao.getCurrentBudget();
    }

	public Product createProduct(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId) {
    	Product product = new Product(id, name, price, bestBefore, category, budgetId, statusId, this);
    	return product;
    }
	
	public void updateProduct(Product product) throws SQLException {
    	dao.updateProduct(product);
    }
	
	public void deleteProduct(Product product) throws SQLException {
		dao.deleteProduct(product);
	}
	
	public void changeProductStatus(Product product, String status) throws SQLException {
		dao.updateStatus(product, status);
	}
	
	public void editProduct(Product product) throws IOException {
		controller.editProduct(product);
	}

	public void addBudgetToDatabase(Budget budget) throws SQLException {
		//dao.addBudget(budget);
	}

	public void addProductToDatabase(Product product) throws SQLException {
		dao.addProduct(product);
	}
	
	public List<Product> getProductsInFridge() throws SQLException {
		return dao.getProductsInFridge();
	}

	public List<String> getCategories() {
		//return dao.getCategories();
		List<String> list = new ArrayList<String>();
		list.add("category 1");
		list.add("category 2");
		return list;
	}
	
	public List<Product> getExpiredProducts() throws SQLException {
		return dao.getExpiredProducts();
	}
}
