package householdhero;


import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
	
	Model model = mock(Model.class);
	DataAccessObject dao = mock(DataAccessObject.class);
	private int id = 1;
	private String name = "Maito";
	private double price = 3.50;
	private LocalDate bestBefore = LocalDate.parse("2017-01-13");
	private String category = "maitotuote";
	private int budgetid = 1;
	private int statusid = 1;
	Product dummy = mock(Product.class);
	
	

	@BeforeEach
	public void getConnection() {
		//dao = new DataAccessObject(model);
		System.out.println("Getting connection");
		dao.connect();
		when(dummy.getId()).thenReturn(id);
		when(dummy.getName()).thenReturn(name);
		when(dummy.getPrice()).thenReturn(price);
		when(dummy.getBestBefore()).thenReturn(bestBefore);
		when(dummy.getCategory()).thenReturn(category);
		when(dummy.getBudgetId()).thenReturn(budgetid);
		when(dummy.getStatusId()).thenReturn(statusid);
		System.out.println("Adding dummy item");
		dao.addProduct(dummy);
	}
	
	@AfterEach
	public void testEndConnection() throws SQLException {
		System.out.println("Deleting dummy item");
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
		System.out.println("Getting product");
		Product dummyProduct = dao.getProduct(1);
		System.out.println(dao.getProduct(1));
		assertEquals(id, dummyProduct.getId(), "getProduct(): Products id incorrect.");
		assertEquals(name, dummyProduct.getName(), "getProduct(): Products name incorrect.");
		assertEquals(category, dummyProduct.getCategory(), "getProduct(): Products category incorrect.");
		assertEquals(price, dummyProduct.getPrice(), "getProduct(): Products price incorrect.");
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
