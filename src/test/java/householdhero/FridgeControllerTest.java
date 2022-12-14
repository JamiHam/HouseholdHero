package householdhero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import group4.householdhero.controller.Controller;
import group4.householdhero.controller.FridgeController;
import group4.householdhero.model.Budget;
import group4.householdhero.model.Model;
import group4.householdhero.model.Product;

@TestInstance(Lifecycle.PER_CLASS)
public class FridgeControllerTest extends FridgeController {
	
	@BeforeAll
	public void setController() {
		controller = mock(Controller.class);
	}
	
	@Test
	public void switchToBudgetTest() throws IOException {
		when(controller.showBudget()).thenReturn(true);
		
		assertEquals(true, switchToBudget(), "Switching to budget view failed.");
	}
	
	@Test
	public void addProductTest() throws IOException, SQLException {
		when(controller.showProductWindow(false, null)).thenReturn(true);
		
		assertEquals(true, addProduct(), "Opening product window failed.");
	}
	
	@Test
	public void editProductTest() throws IOException, SQLException {
		Product product = mock(Product.class);
		when(controller.showProductWindow(true, product)).thenReturn(true);
		
		assertEquals(true, editProduct(product), "Opening product window failed.");
	}
	
	@Test
	public void checkCurrentBudgetTest() throws SQLException, IOException {
		when(controller.getBudget(LocalDate.now())).thenReturn(null);
		assertEquals(false, checkCurrentBudget(), "getBudget returned null when it found a budget.");
		
		Budget budget = mock(Budget.class);
		when(controller.getBudget(LocalDate.now())).thenReturn(budget);
		assertEquals(true, checkCurrentBudget(), "getBudget returnd false when it didn't a budget.");
	}
}
