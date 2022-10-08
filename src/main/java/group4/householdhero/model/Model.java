package group4.householdhero.model;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import group4.householdhero.controller.IControllerMtoV;

public class Model implements IModel {
	private IControllerMtoV controller;
	private DataAccessObject dao;
	
	public void setController(IControllerMtoV controller) {
		this.controller = controller;
		dao = new DataAccessObject(this);
	}

	public Budget createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) {
		Budget budget = new Budget(id, plannedBudget, spentBudget, startDate, endDate);
		return budget;
	}
	
	public void updateBudget(Budget budget) throws SQLException {
		dao.updateBudget(budget);
	}
	
	public void addBudgetToDatabase(Budget budget) throws SQLException {
		dao.addBudget(budget);
	}
	
	public Budget getBudget(LocalDate date) throws SQLException {
    	return dao.getBudgetByDate(date);
    }
	
	public List<Budget> getAllBudgets() throws SQLException {
		return null;
	}

	public Product createProduct(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId) {
    	Product product = new Product(id, name, price, bestBefore, category, budgetId, statusId, this);
    	return product;
    }
	
	public void addProductToDatabase(Product product) throws SQLException {
		dao.addProduct(product);
	}
	
	public List<Product> getProducts(String status) throws SQLException {
		return dao.getProducts(status);
	}
	
	public void changeProductStatus(Product product, String status) throws SQLException {
		dao.updateStatus(product, status);
	}
	
	public void updateProduct(Product product) throws SQLException {
    	dao.updateProduct(product);
    }
	
	public void deleteProduct(Product product) throws SQLException {
		dao.deleteProduct(product);
	}
	
	public void checkBestBefore() throws SQLException {
    	dao.checkBestBefore();
    }
	
	public void editProduct(Product product) throws IOException, SQLException {
		controller.editProduct(product);
	}

	public List<String> getCategories() throws SQLException {
		return dao.getCategories();
	}
}