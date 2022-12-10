package group4.householdhero.model;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import group4.householdhero.controller.IController;

public interface IModel {
	public void setController(IController controller);
	
	public Budget createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) throws SQLException;
	public void updateBudget(Budget budget) throws SQLException;
	public void addBudgetToDatabase(Budget budget) throws SQLException;
	public Budget getBudget(LocalDate date) throws SQLException;
	public List<Budget> getAllBudgets() throws SQLException;
	public boolean checkBudgets(LocalDate startDate, LocalDate endDate) throws SQLException;
	public boolean checkBudgets(LocalDate startDate, LocalDate endDate, Budget budget) throws SQLException;
	public boolean deleteBudget(Budget budget) throws SQLException;
	
	public Product createProduct(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId) throws SQLException;
	public void addProductToDatabase(Product product) throws SQLException;
	public List<Product> getProducts(String status) throws SQLException;
	public List<Product> getProductsByBudget(int budgetId) throws SQLException;
	public void changeProductStatus(Product product, String status) throws SQLException;
	public void updateProduct(Product product) throws SQLException;
	public void deleteProduct(Product product) throws SQLException;
	public void checkBestBefore() throws SQLException;
	public void editProduct(Product product) throws IOException, SQLException;
	
	public List<String> getCategories() throws SQLException;
	public List<String> getLocalisedCategories();
	public String unlocaliseCategory(String localizedCategory);
	public List<Product> localise(List<Product> products);
	public void localiseCategories();
	
	public Locale setLocale(String language);
	public String getLocalisedString(String string);
	public Locale getLocale();
	public ResourceBundle getBundle();
}
