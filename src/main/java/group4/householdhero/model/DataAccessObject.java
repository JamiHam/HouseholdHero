package group4.householdhero.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject {

	protected Connection conn;
	private Model model = new Model();
	
	public DataAccessObject(Model model, String database) {
		connect(database);
		this.model = model;
	}

	public void connect(String database) {
		final String URL = "jdbc:mariadb://10.114.32.4:3306/" + database;
		final String USERNAME = "user";
		final String PWD = "password";
		try {
			conn = DriverManager.getConnection(URL + "?user=" + USERNAME + "&password=" + PWD);
			System.out.println("Database connection created");
		} catch (SQLException e) {
			do {
				System.err.println("Viesti: " + e.getMessage());
				System.err.println("Virhekoodi: " + e.getErrorCode());
				System.err.println("SQL-tilakoodi: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1); // lopetus heti virheen vuoksi
		}
	}
	


	@Override
	public void finalize() { // destruktori
		try { // oli sama yhteys koko sovelluksen ajan
			conn.close(); // vapauttaa kaikki muutkin resurssit
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getCategoryIdByName(Product product) throws SQLException {
		int id = 0;
		String getCategoryIdByNameQuery = "select category_ID from Category where type = ?";

		PreparedStatement stmt = conn.prepareStatement(getCategoryIdByNameQuery);
		stmt.setString(1, product.getCategory());

		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			id = rs.getInt("category_ID");
		}
		return id;
	}

	private String getCategoryByName(int productID) throws SQLException {
		String type = null;
		String getCategoryByNameQuery = "select type from Category "
				+ "inner join Product on Category.category_ID = Product.category_ID where Product.product_ID = ?";

		PreparedStatement stmt = conn.prepareStatement(getCategoryByNameQuery);
		stmt.setInt(1, productID);

		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			type = rs.getString("type");
		}

		return type;
	}

	public void updateProduct(Product product) throws SQLException {
		// Päivittää kaikki productin fieldit (paitsi budjetin ja statuksen) id:n
		// perusteella.

		String updateProductQuery = "update Product set name=?, price=?, best_before=?, budget_ID=?, category_ID=? where product_ID=?";

		PreparedStatement stmt = conn.prepareStatement(updateProductQuery);
		stmt.setString(1, product.getName());
		stmt.setDouble(2, product.getPrice());
		stmt.setDate(3, Date.valueOf(product.getBestBefore()));
		stmt.setInt(4, product.getBudgetId());
		stmt.setInt(5, getCategoryIdByName(product));
		stmt.setInt(6, product.getId());

		System.out.println(updateProductQuery);
		int updates = stmt.executeUpdate();
		System.out.println("Amount of updated products: " + updates);
	}

	
	public void deleteProduct(Product product) throws SQLException {
		String deleteProductQuery = "delete from Product where product_ID=?";

		PreparedStatement stmt = conn.prepareStatement(deleteProductQuery);
		stmt.setInt(1, product.getId());

		System.out.println("Deleting product: " + product.getName());
		int deletes = stmt.executeUpdate();
		System.out.println("Deleted products: " + deletes);
	}

	
	public boolean addProduct(Product product) {
		String addProductQuery = "insert into Product (Product_ID, name, price, best_before, category_ID, status_ID, budget_ID) "
				+ "values (?, ?, ?, ?, ?, ?, ?)";
		System.out.println("Adding new product");

		try {
			PreparedStatement stmt = conn.prepareStatement(addProductQuery);
			stmt.setInt(1, product.getId());
			stmt.setString(2, product.getName());
			stmt.setDouble(3, product.getPrice());
			stmt.setDate(4, Date.valueOf(product.getBestBefore()));
			stmt.setInt(5, getCategoryIdByName(product));
			stmt.setInt(6, product.getStatusId());
			stmt.setInt(7, product.getBudgetId());

			System.out.println(addProductQuery);
			int added = stmt.executeUpdate();
			System.out.println("Added: " + added);
			System.out.println(product.getName());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	private int getStatusIdByName(String statusName) throws SQLException {
		int id = 0;
		String getIdByNameQuery = "select status_ID from Status where status = ?";

		PreparedStatement stmt = conn.prepareStatement(getIdByNameQuery);
		stmt.setString(1, statusName);

		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			id = rs.getInt("status_ID");
		}
		return id;
	}

	public void updateStatus(Product product, String status) throws SQLException {
		String updateStatusQuery = "update Product set status_ID=? where product_ID=?";

		PreparedStatement stmt = conn.prepareStatement(updateStatusQuery);
		stmt.setInt(1, getStatusIdByName(status));
		stmt.setInt(2, product.getId());

		System.out.println(updateStatusQuery);
		int update = stmt.executeUpdate();
		System.out.println("Updated: " + update);
	}

	public Budget getBudgetByDate(LocalDate date) throws SQLException {
		// Palauttaa aktiivisen budjetin. Palauttaa null jos budjettia ei ole.
		// (LocalDate.now() = nykyinen päivämäärä)
		Budget budget = null;
		String getCurrentBudgetQuery = "select * from Budget where start_date<=? and end_date>=?";
		PreparedStatement stmt = conn.prepareStatement(getCurrentBudgetQuery);

		stmt.setDate(1, Date.valueOf(date));
		stmt.setDate(2, Date.valueOf(date));

		System.out.println(stmt);

		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			budget = new Budget(rs.getInt("budget_ID"), rs.getDouble("planned_budget"), rs.getDouble("spent_budget"),
					LocalDate.parse(rs.getDate("start_date").toString()),
					LocalDate.parse(rs.getDate("end_date").toString()));
		}
		return budget;
	}
	

	public void addBudget(Budget budget) throws SQLException {
		String addBudgetQuery = "insert into Budget (budget_ID, planned_budget, spent_budget, start_date, end_date) values (?, ?, ?, ?, ?)";

		PreparedStatement stmt = conn.prepareStatement(addBudgetQuery);
		stmt.setInt(1, budget.getId());
		stmt.setDouble(2, budget.getPlannedBudget());
		stmt.setDouble(3, budget.getSpentBudget());
		stmt.setDate(4, Date.valueOf(budget.getStartDate()));
		stmt.setDate(5, Date.valueOf(budget.getEndDate()));

		int added = stmt.executeUpdate();
		System.out.println("Added: " + added);
	}

	public List<Product> getProducts(String status) throws SQLException {
		ArrayList<Product> products = new ArrayList<Product>();
		String getProductsQuery = "select * from Product where status_ID=?";

		PreparedStatement stmt = conn.prepareStatement(getProductsQuery);
		stmt.setInt(1, getStatusIdByName(status));

		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Product product = model.createProduct(rs.getInt("product_ID"), rs.getString("name"), rs.getDouble("price"),
					LocalDate.parse(rs.getDate("best_before").toString()), getCategoryByName(rs.getInt("product_ID")),
					rs.getInt("budget_ID"), rs.getInt("status_ID"));
			products.add(product);
		}
		return products;
	}
	
	public List<Product> getProductsByBudget(int budgetID) throws SQLException {
		ArrayList<Product> products = new ArrayList<Product>();
		String getProductsQuery = "select * from Product where budget_ID=?";

		PreparedStatement stmt = conn.prepareStatement(getProductsQuery);
		stmt.setInt(1, budgetID);

		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Product product = model.createProduct(rs.getInt("product_ID"), rs.getString("name"), rs.getDouble("price"),
					LocalDate.parse(rs.getDate("best_before").toString()), getCategoryByName(rs.getInt("product_ID")),
					rs.getInt("budget_ID"), rs.getInt("status_ID"));
			products.add(product);
			System.out.println("Found: " + product.getBestBefore());
		}
		return products;
	}

	public Product getProduct(int id) throws SQLException {
		String getProductsQuery = "select * from Product where product_ID=?";
		Product product = null;

		PreparedStatement stmt = conn.prepareStatement(getProductsQuery);
		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			product = model.createProduct(rs.getInt("product_ID"), rs.getString("name"), rs.getDouble("price"),
					LocalDate.parse(rs.getDate("best_before").toString()), getCategoryByName(rs.getInt("product_ID")),
					rs.getInt("budget_ID"), rs.getInt("status_ID"));
		}
		System.out.println("\nTuotenimi\n"+product.getName());
		return product;
	}

	public List<String> getCategories() throws SQLException {
		ArrayList<String> categories = new ArrayList<String>();
		String getCategoriesQuery = "select type from Category";

		PreparedStatement stmt = conn.prepareStatement(getCategoriesQuery);

		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			categories.add(rs.getString("type"));
		}
		return categories;
	}

	public void checkBestBefore() throws SQLException {
		String checkBestBeforeQuery = "update Product set status_ID=? where status_ID=? and best_before<?";

		PreparedStatement stmt = conn.prepareStatement(checkBestBeforeQuery);
		stmt.setInt(1, getStatusIdByName("expired"));
		stmt.setInt(2, getStatusIdByName("fridge"));
		stmt.setDate(3, Date.valueOf(LocalDate.now()));

		int updates = stmt.executeUpdate();
		System.out.println("updated: " + updates);
	}

	public void updateBudget(Budget newBudget) throws SQLException {
		String updateBudgetQuery = "update Budget set planned_budget=?, spent_budget=?, start_date=?, end_date=? where budget_ID=?";

		PreparedStatement stmt = conn.prepareStatement(updateBudgetQuery);
		stmt.setDouble(1, newBudget.getPlannedBudget());
		stmt.setDouble(2, newBudget.getSpentBudget());
		stmt.setDate(3, Date.valueOf(newBudget.getStartDate()));
		stmt.setDate(4, Date.valueOf(newBudget.getEndDate()));
		stmt.setInt(5, newBudget.getId());

		stmt.executeUpdate();
	}

	public boolean checkBudgets(LocalDate startDate, LocalDate endDate) throws SQLException {
		String checkBudgetString = "select * from Budget where (start_date <= ?) and (? <= end_date) and "
				+ "(start_date <= end_date) and (? <= ?);";

		PreparedStatement stmt = conn.prepareStatement(checkBudgetString);
		stmt.setDate(1, Date.valueOf(endDate));
		stmt.setDate(2, Date.valueOf(startDate));
		stmt.setDate(3, Date.valueOf(startDate));
		stmt.setDate(4, Date.valueOf(endDate));

		System.out.println("Starting date: " + Date.valueOf(startDate) + "\nEndDate: " + Date.valueOf(endDate));
		ResultSet rs = stmt.executeQuery();
		int found = 0;
		while(rs.next()) {
			found++;
		}
		System.out.println("CheckBudgets found count: " + found);
		if (found != 0) {
			return false;
		} else {
			return true;
		}

	}
	
	public List<Budget> getAllBudgets() throws SQLException {
		ArrayList<Budget> budgets = new ArrayList<>();
		String getBudgetsString = "select * from Budget";

		PreparedStatement stmt = conn.prepareStatement(getBudgetsString);

		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Budget budget = model.createBudget(rs.getInt("budget_ID"), rs.getDouble("planned_budget"), rs.getDouble("spent_budget"), 
					LocalDate.parse(rs.getDate("start_date").toString()), LocalDate.parse(rs.getDate("end_date").toString()));
			budgets.add(budget);
		}
		return budgets;
	}
	
	public void deleteProductsFromBudget(Budget budget) throws SQLException {
		String deleteProductsFromBudgetString = "delete from Product where Budget_ID=?";
		
		PreparedStatement stmt = conn.prepareStatement(deleteProductsFromBudgetString);
		stmt.setInt(1, budget.getId());
		
		System.out.println("Deleted products with budgetID: " + budget.getId());
		int deletes = stmt.executeUpdate();
		System.out.println("Deleted item count: " + deletes);
	}
	
	public boolean deleteBudget(Budget budget) throws SQLException {
		deleteProductsFromBudget(budget);
		String deleteBudgetString = "delete from Budget where budget_ID=?";
		
		PreparedStatement stmt = conn.prepareStatement(deleteBudgetString);
		stmt.setInt(1, budget.getId());
		
		System.out.println("Deleting budget (start-end): " + budget.getStartDate() + " - " + budget.getEndDate());
		int deletes = stmt.executeUpdate();
		System.out.println("Deleted budget count: " + deletes);
		if(deletes != 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public Connection getConnection() {
		return conn;
	}
}
