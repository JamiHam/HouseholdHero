package householdhero;


import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import group4.householdhero.model.DataAccessObject;
import group4.householdhero.model.Model;
import group4.householdhero.model.Product;

public class DAOTest {
	
	private Model model = new Model();
	//private Mock controller
	//model.setController(controller);
	DataAccessObject dao = new DataAccessObject(model);
	private int id = 1;
	private String name = "Maito";
	private double price = 3.50;
	private LocalDate bestBefore = LocalDate.parse("2017-01-13");
	private String category = "maitotuote";
	private int budgetid = 1;
	private int statusid = 1;
	Product dummy = model.createProduct(id, name, price, bestBefore, category, budgetid, statusid);
	

	@BeforeEach
	public void testConnection() {
		dao.connect();
		dao.addProduct(dummy);
	}
	
	@AfterEach
	public void testEndConnection() throws SQLException {
		dao.deleteProduct(dummy);
		dao.finalize();
	}
	
	/*@Test
	public void testGetProductsInFridge() throws SQLException {
		List<Product> products = dao.getProducts("fridge");
		
	}*/
	
	@Test
	@DisplayName("Adding products")
	public void testAdd() throws SQLException {
		dummy = dao.getProduct(1);
		System.out.println(dummy.getId());
		assertEquals(id, dummy.getId(), "getProduct(): Products id incorrect.");
		assertEquals(name, dummy.getName(), "getProduct(): Products name incorrect.");
		assertEquals(category, dummy.getCategory(), "getProduct(): Products category incorrect.");
		assertEquals(price, dummy.getPrice(), "getProduct(): Products price incorrect.");
	}
	
	/*@Test
	public void testAddProduct() {
		//dao.addProduct(sql);
	}*/
	
	/*@Test
	public void testDeleteProduct() {
		
	}
	
	@Test
	public void testEditProduct() {
		
	}*/
	
	
}
