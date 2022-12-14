package group4.householdhero.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
	
	public boolean checkBudgets(LocalDate startDate, LocalDate endDate, Budget budget) throws SQLException {
		return model.checkBudgets(startDate, endDate, budget);
	}
	
	public boolean deleteBudget(Budget budget) throws SQLException {
		 return model.deleteBudget(budget);
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
	
	public void putToUsed(Product product) throws IOException, SQLException {
		FridgeController.putToUsed(product);
	}
	
	@Override
	public void putToWaste(Product product) throws IOException, SQLException {
		FridgeController.putToWaste(product);
	}

	public List<String> getCategories() throws SQLException {
		return model.getCategories();
	}
	
	public List<String> getLocalisedCategories() {
		return model.getLocalisedCategories();
	}
	
	public String unlocaliseCategory(String localizedCategory) {
		return model.unlocaliseCategory(localizedCategory);
	}
	
	public List<Product> localise(List<Product> products) {
		return model.localise(products);
	}
	
	public void localiseCategories() {
		model.localiseCategories();
	}
	
	
	
	public Locale setLocale(String language) {
		return model.setLocale(language);
	}

	public String getLocalisedString(String string) {
		return model.getLocalisedString(string);
	}

	public Locale getLocale() {
		return model.getLocale();
	}

	public ResourceBundle getBundle() {
		return model.getBundle();
	}
	
	
	
	public boolean showBudget() throws IOException {
		return App.showBudget();
	}
	
	public boolean showFridge() throws IOException {
		return App.showFridge();
	}
	
	public boolean showProductWindow(boolean editing, Product product) throws IOException, SQLException {
		return App.showProductWindow(editing, product);
	}
	
	public boolean showBudgetWindow(boolean editing, Budget budget) throws IOException, SQLException {
		return App.showBudgetWindow(editing, budget);
	}



	



	
}
