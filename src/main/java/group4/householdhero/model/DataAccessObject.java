package group4.householdhero.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject {
	
	DBInfo db = new DBInfo();
	private Connection conn;
	private Model model;
	
	
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
 		try (PreparedStatement stmt = conn.prepareStatement("select * from product")) {
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				//create product metodi
				Product product = model.createProduct(rs.getInt("product_ID"), rs.getString("name"), rs.getDouble("price"), LocalDate.parse(rs.getDate("best_before").toString()), rs.getInt("category_ID"), rs.getInt("status_ID"), rs.getInt("budget_ID"));
				products.add(product);
			}
			return products;
		}
	}
}
