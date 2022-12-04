package householdhero;


import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import group4.householdhero.model.Budget;
import group4.householdhero.model.DataAccessObject;
import group4.householdhero.model.Model;
import group4.householdhero.model.Product;

@TestInstance(Lifecycle.PER_CLASS)
public class DAOTest {
	private Model model = mock(Model.class);
	private DataAccessObject dao = new DataAccessObject(model, "HouseholdHeroTest");
	private Budget budget = mock(Budget.class);
	private Product product = mock(Product.class);
	private Connection connection;
	
	private int id = 1;
	private String name = "Maito";
	private double price = 3.50;
	private LocalDate bestBefore = LocalDate.parse("2017-01-13");
	private String category = "dairy";
	private int budgetId = 1;
	private int statusId = 1;
	
	@BeforeAll
	public void initialize() {
		when(model.createProduct(id, name, price, bestBefore, category, budgetId, statusId)).thenReturn(product);
		
		when(budget.getId()).thenReturn(1);
		when(budget.getPlannedBudget()).thenReturn(100.0);
		when(budget.getSpentBudget()).thenReturn(25.0);
		when(budget.getStartDate()).thenReturn(LocalDate.parse("2000-01-01"));
		when(budget.getEndDate()).thenReturn(LocalDate.parse("2000-12-31"));

		when(product.getId()).thenReturn(id);
		when(product.getName()).thenReturn(name);
		when(product.getPrice()).thenReturn(price);
		when(product.getBestBefore()).thenReturn(bestBefore);
		when(product.getCategory()).thenReturn(category);
		when(product.getBudgetId()).thenReturn(budgetId);
		when(product.getStatusId()).thenReturn(statusId);
	}
	
	@Test
	public void addProductTest() throws SQLException {
		connection = dao.getConnection();
		connection.setAutoCommit(false);
		
		dao.addBudget(budget);
		boolean success = dao.addProduct(product);
		assertEquals(true, success, "Failed to add product");
		
		connection.rollback();
	}
	
	@Test
	public void getProductTest() throws SQLException {
		connection = dao.getConnection();
		connection.setAutoCommit(false);
		
		dao.addBudget(budget);
		dao.addProduct(product);
		Product testProduct = dao.getProduct(1);
		assertEquals("Maito", testProduct.getName(), "Vituiks meni");
		
		connection.rollback();
	}
	
	/*static Model modeli = mock(Model.class);
	public DAOTest(Model model) {
		super(modeli);
		// TODO Auto-generated constructor stub
	}*/

	
	/*private static DataAccessObject dao;
	private int id = 1;
	private String name = "Maito";
	private double price = 3.50;
	private LocalDate bestBefore = LocalDate.parse("2017-01-13");
	private String category = "maitotuote";
	private int budgetid = 1;
	private int statusid = 1;
	Product dummy = mock(Product.class);
	
	@BeforeAll
	public static void start() {
		Model model = mock(Model.class);
		dao = new DataAccessObject(model);
	}

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
	
	/*@Test
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
