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
	
	public HashMap<Integer, String> getCategoryNameById() throws SQLException {
		HashMap<Integer, String> categories = new HashMap<Integer, String>();
		try(PreparedStatement stms = conn.prepareStatement("select * from category")) {
			ResultSet rs = stms.executeQuery();
			while(rs.next()) {
				categories.put(rs.getInt("category_ID"), rs.getString("type"));
				System.out.println("Found categories: " + categories);
			}
		}
		return categories;
	}
	
	public String getCategoryByName(String productName) throws SQLException {
		String type = null;
		try (PreparedStatement stmt = conn.prepareStatement("select type from category "
				+ "inner join product on category.category_ID = product.category_ID where product.name = '" + productName + "';")) {
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				type = rs.getString("type");
			}
		}
		return type;
	}
	
	public List<Product> getProductsInFridge() throws SQLException {
		//connection();
		ArrayList<Product> products = new ArrayList<Product>();
 		try (PreparedStatement stmt = conn.prepareStatement("select * from product")) {
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Product product = model.createProduct(rs.getInt("product_ID"), rs.getString("name"),
						rs.getDouble("price"), LocalDate.parse(rs.getDate("best_before").toString()),
						getCategoryByName(rs.getString("name")), rs.getInt("status_ID"), rs.getInt("budget_ID"));
				products.add(product);
			}
			return products;
		}
	}
}
