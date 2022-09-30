package householdhero;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import group4.householdhero.model.DataAccessObject;
import group4.householdhero.model.Product;

public class DAOTest extends DataAccessObject {
	/*
	DataAccessObject dao = new DataAccessObject();
	
	Product dummy = new Product("Maito", 3.50, );

	@BeforeEach
	public void testConnection() {
		dao.connect();
	}
	
	@AfterEach
	public void testEndConnection() {
		dao.endConnection();
	}
	
	@Test
	public void testGetProductsInFridge() {
		String sql = "select * from product";
		dao.getProductsInFridge(sql);
	}
	
	@Test
	public void testAddProduct() {
		String sql = "insert into product (name, price, best_before, category_ID, status_ID, budget_ID) values "
				+ "(dummy.getName(),"
				+ "dummy.getPrice(),"
				+ "dummy.getBestBefore(),"
				+ "dummy.getCategoryId(),"
				+ "dummy.getStatusId(),"
				+ "dummy.getBudgetId())";
		dao.addProduct(sql);
	}
	
	@Test
	public void testDeleteProduct() {
		
	}
	
	@Test
	public void testEditProduct() {
		
	}
	
	*/
}
