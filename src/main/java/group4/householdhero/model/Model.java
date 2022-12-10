package group4.householdhero.model;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import group4.householdhero.controller.IController;

public class Model implements IModel {
	private IController controller;
	private DataAccessObject dao;
	private ProductFactory productFactory;
	private BudgetFactory budgetFactory;
	private CategoryLocalizer categoryLocalizer;
	
	public Model() {
		productFactory = new ProductFactory();
		budgetFactory = new BudgetFactory();
	}
	
	public void setController(IController controller) {
		this.controller = controller;
		dao = new DataAccessObject(this, "HouseholdHero");
	}
	
	public void createLocalizer() throws SQLException {
		categoryLocalizer = new CategoryLocalizer(this);
	}
	
	

	public Budget createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) {
		Budget budget = budgetFactory.createBudget(id, plannedBudget, spentBudget, startDate, endDate);
		return budget;
	}
	
	public void addBudgetToDatabase(Budget budget) throws SQLException {
		dao.addBudget(budget);
	}
	
	public void updateBudget(Budget budget) throws SQLException {
		dao.updateBudget(budget);
	}
	
	public Budget getBudget(LocalDate date) throws SQLException {
    	return dao.getBudgetByDate(date);
    }
	
	public List<Budget> getAllBudgets() throws SQLException {
		return dao.getAllBudgets();
	}
	
	public boolean checkBudgets(LocalDate startDate, LocalDate endDate) throws SQLException {
		return dao.checkBudgets(startDate, endDate);
	}
	
	public boolean checkBudgets(LocalDate startDate, LocalDate endDate, Budget budget) throws SQLException {
		return dao.checkBudgets(startDate, endDate, budget);
	}
	
	public boolean deleteBudget(Budget budget) throws SQLException {
		return dao.deleteBudget(budget);
	}
	
	

	public Product createProduct(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId) {
    	Product product = productFactory.createProduct(id, name, price, bestBefore, category, budgetId, statusId, this);
    	return product;
    }
	
	public void addProductToDatabase(Product product) throws SQLException {
		dao.addProduct(product);
	}
	
	public List<Product> getProducts(String status) throws SQLException {
		return dao.getProducts(status);
	}
	
	public List<Product> getProductsByBudget(int budgetId) throws SQLException {
		return dao.getProductsByBudget(budgetId);
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
	
	public void putToUsed(Product product) throws IOException, SQLException {
		controller.putToUsed(product);
	}
	
	public void putToWaste(Product product) throws IOException, SQLException {
		controller.putToWaste(product);
	}

	public List<String> getCategories() throws SQLException {
		return dao.getCategories();
	}
	
	public List<String> getLocalizedCategories() {
		return categoryLocalizer.getLocalizedCategories();
	}

	public String unlocalizeCategory(String localizedCategory) {
		return categoryLocalizer.unlocalizeCategory(localizedCategory);
	}
	
	public List<Product> localize(List<Product> products) {
		return categoryLocalizer.localize(products);
	}

	public void localizeCategories() {
		categoryLocalizer.localizeCategories();
	}
	
	
}