package householdhero;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

	
	@BeforeEach
	public void getConnection() throws SQLException {
		connection = dao.getConnection();
		connection.setAutoCommit(false);
	}
	
	@AfterEach
	public void rollback() throws SQLException {
		connection.rollback();
	}
	
	@Test
	@DisplayName("Adding products")
	public void addProductTest() throws SQLException {
		dao.addBudget(budget);
		boolean success = dao.addProduct(product);
		assertEquals(true, success, "Failed to add product");
	}
	
	@Test
	@DisplayName("Get product")
	public void getProductTest() throws SQLException {
		dao.addBudget(budget);
		dao.addProduct(product);
		Product testProduct = dao.getProduct(1);
		assertEquals("Maito", testProduct.getName(), "Failed to get products");
	}
	
	@Test
	@DisplayName("Delete product")
	public void deleteProductTest() throws SQLException {
		dao.addBudget(budget);
		dao.addProduct(product);
		dao.deleteProduct(product);
		assertNull(dao.getProduct(1), "Failed to update product");
	}	
	
	@Test
	@DisplayName("Update product")
	public void updateProductTest() throws SQLException {
		
	}
}
