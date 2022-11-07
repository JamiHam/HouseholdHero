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
import group4.householdhero.model.Model;
import group4.householdhero.model.Product;

public class DAOTest {
	
	private Model model = new Model();
	DataAccessObject dao = new DataAccessObject(model);
	
	

	@BeforeEach
	public void testConnection() {
		dao.connect();
		Product dummy = new Product(1, "Maito", 3.50, 2017-01-13, "maitotuote", 1, 1, model);
	}
	
	@AfterEach
	public void testEndConnection() {
		dao.finalize();
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
	
	
}
