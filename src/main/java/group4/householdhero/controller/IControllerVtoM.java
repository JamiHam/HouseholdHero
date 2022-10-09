package group4.householdhero.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import group4.householdhero.model.Budget;
import group4.householdhero.model.Product;

public interface IControllerVtoM {
	public void createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) throws SQLException;
	public void updateBudget(Budget budget) throws SQLException;
	public Budget getBudget(LocalDate date) throws SQLException;
	public List<Budget> getAllBudgets() throws SQLException;
	
	public void createProduct(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId) throws SQLException;
	public void updateProduct(Product product) throws SQLException;
	public List<Product> getProducts(String status) throws SQLException;
	public void changeProductStatus(Product product, String status) throws SQLException;
	public void deleteProduct(Product product) throws SQLException;
	public void checkBestBefore() throws SQLException;
	public void editProduct(Product product) throws IOException, SQLException;
	
	public List<String> getCategories() throws SQLException;
}