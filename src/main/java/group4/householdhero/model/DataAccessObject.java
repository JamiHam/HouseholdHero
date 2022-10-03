package group4.householdhero.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataAccessObject {
	
	DBInfo db = new DBInfo();
	private Connection conn;
	private Model model = new Model();
	
	
	public DataAccessObject(Model model) {
		connect();
		this.model = model;
	}
	
	public void connect() {
		final String URL = "jdbc:mariadb://localhost/" + db.getDatabase();
		final String USERNAME = db.getUsername();
		final String PWD = db.getPassword();
		try {
		conn = DriverManager.getConnection(
		URL + "?user=" + USERNAME + "&password=" + PWD);
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
	protected void finalize() { // destruktori
		try { // oli sama yhteys koko sovelluksen ajan
			conn.close(); // vapauttaa kaikki muutkin resurssit
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Product> getProductsInFridge() throws SQLException {
		//connection();
		ArrayList<Product> products = new ArrayList<Product>();
 		try (PreparedStatement stmt = conn.prepareStatement("select * from product where status_ID = " + getStatusIdByName("fridge"))) {
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Product product = model.createProduct(rs.getInt("product_ID"), rs.getString("name"),
						rs.getDouble("price"), LocalDate.parse(rs.getDate("best_before").toString()),
						getCategoryByName(rs.getInt("product_ID")), rs.getInt("status_ID"), rs.getInt("budget_ID"));
				products.add(product);
				System.out.println("Found: " + product.getBestBefore());
			}
			return products;
		}
	}
	
	public int getCategoryIdByName(Product product) throws SQLException {
		int id = 0;
		try(PreparedStatement stms = conn.prepareStatement("select category_ID from "
				+ "category where type = '" + product.getCategory() + "';")) {
			ResultSet rs = stms.executeQuery();
			if (rs.next()) {
				id = rs.getInt("category_ID");
			}
		}
		return id;
	}
	
	public String getCategoryByName(int productId) throws SQLException {
		String type = null;
		try (PreparedStatement stmt = conn.prepareStatement("select type from category "
				+ "inner join product on category.category_ID = product.category_ID where product.product_ID = '" + productId + "';")) {
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				type = rs.getString("type");
			}
		}
		return type;
	}
	
	public void updateProduct(Product product) throws SQLException {
		//Päivittää kaikki productin fieldit (paitsi budjetin ja statuksen) id:n perusteella.
		Statement stmt = conn.createStatement();
		String updateString = "update product set name='" + product.getName() + 
				"', price=" + product.getPrice() + ", best_before='" + product.getBestBefore() + 
				"', budget_ID=" + product.getBudgetId() + " where product_ID=" + product.getId() + ";";
		System.out.println(updateString);
		int updates = stmt.executeUpdate(updateString);
		System.out.println("Amount of updated products: " + updates);
	}
	
	public void deleteProduct(Product product) throws SQLException {
		Statement stmt = conn.createStatement();
		String deleteProduct = "delete from product where product_ID = " + product.getId();
		System.out.println("Deleting product: " + product.getName());
		int deletes = stmt.executeUpdate(deleteProduct);
		System.out.println("Deleted products: " + deletes);
	}
	
	public void addProduct(Product product) throws SQLException {
		Statement stmt = conn.createStatement();
		String addProduct = "insert into product (name, price, best_before, category_ID, status_ID, budget_ID) "
				+ "values ('"+ product.getName() + "', " + product.getPrice() + ", '" + product.getBestBefore() +
				"', " + getCategoryIdByName(product) + ", " + product.getStatusId() + ", " + product.getBudgetId() + ")";
		System.out.println(addProduct);
		int added = stmt.executeUpdate(addProduct);
		System.out.println("Added: " + added);
	}
	
	public int getStatusIdByName(String statusName) throws SQLException {
		String getIdByName = "select product.status_ID from product inner join status "
				+ "on product.status_ID = status.status_ID where status.status = '" + statusName + "';";
		int id = 0;
		try (PreparedStatement stmt = conn.prepareStatement(getIdByName)) {
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				id=rs.getInt("status_ID");
			}
		}
		return id;
	}
	
	public void updateStatus(Product product, String status) throws SQLException {
		Statement stmt = conn.createStatement();
		String updateStatus = "update product set status_ID = "+getStatusIdByName(status) + " where product_ID = " + product.getId();
		int update = stmt.executeUpdate(updateStatus);
		System.out.println("Updated: " + update);
	}

	public Budget getCurrentBudget() throws SQLException {
		//Palauttaa aktiivisen budjetin. Palauttaa null jos budjettia ei ole.
		//(LocalDate.now() = nykyinen päivämäärä)
		Budget budget = null;
		
		
		return budget;
	}
	
	public void addBudget(Budget budget) throws SQLException {
		String addBudgetString = "insert into budget (planned_budget, spent_budget, start_date, end_date)"
				+ " values (" + budget.getPlannedBudget() + ", " + budget.getSpentBudget() + ", " + budget.getStartDate() 
				+ ", " + budget.getEndDate() + ")";
		Statement stmt = conn.createStatement();
		int added = stmt.executeUpdate(addBudgetString);
		System.out.println("Added: " + added);	
	}
	
	public List<Product> getExpiredProducts() throws SQLException {
		ArrayList<Product> expiredProducts = new ArrayList<Product>();
		String getExpiredString = "select * from product where best_before > " + LocalDate.now();
		try (PreparedStatement stmt = conn.prepareStatement(getExpiredString)) {
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Product product = model.createProduct(rs.getInt("product_ID"), rs.getString("name"),
						rs.getDouble("price"), LocalDate.parse(rs.getDate("best_before").toString()),
						getCategoryByName(rs.getInt("product_ID")), rs.getInt("status_ID"), rs.getInt("budget_ID"));
				expiredProducts.add(product);
				System.out.println("Found: " + product.getBestBefore());
			}
		}
		return expiredProducts;
		
	}
	
	public List<Product> getProductsInWaste() throws SQLException {
		ArrayList<Product> products = new ArrayList<Product>();
 		try (PreparedStatement stmt = conn.prepareStatement("select * from product where status_ID = " + getStatusIdByName("waste"))) {
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Product product = model.createProduct(rs.getInt("product_ID"), rs.getString("name"),
						rs.getDouble("price"), LocalDate.parse(rs.getDate("best_before").toString()),
						getCategoryByName(rs.getInt("product_ID")), rs.getInt("status_ID"), rs.getInt("budget_ID"));
				products.add(product);
				System.out.println("Found: " + product.getBestBefore());
			}
			return products;
		}
	}
	
	
}
