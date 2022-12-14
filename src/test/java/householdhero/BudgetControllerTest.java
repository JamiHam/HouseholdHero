package householdhero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import group4.householdhero.controller.BudgetController;
import group4.householdhero.controller.Controller;
import group4.householdhero.model.Budget;
import group4.householdhero.model.Product;
import group4.householdhero.view.App;
import javafx.application.Platform;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

@TestInstance(Lifecycle.PER_CLASS)
public class BudgetControllerTest extends BudgetController {
	
	@BeforeAll
	public void setController() throws IOException, SQLException {
		controller = mock(Controller.class);
		when(controller.showFridge()).thenReturn(true);
		when(controller.showBudgetWindow(true, budget)).thenReturn(true);
	}
	
	@Test
	public void switchToFridgeTest() throws IOException {
		assertEquals(true, switchToFridge(), "Switching to fridge failed.");
	}
	
	@Test
	public void editBudgetTest() throws IOException, SQLException {
		assertEquals(true, editBudget(), "Opening editing window failed.");
	}
	
	@Test
	public void calculateStatusPricesTest() {
		productList = new ArrayList<Product>();
		Product product1 = mock(Product.class);
		when(product1.getPrice()).thenReturn(1.0);
		when(product1.getStatusId()).thenReturn(1);
		
		Product product2 = mock(Product.class);
		when(product2.getPrice()).thenReturn(1.5);
		when(product2.getStatusId()).thenReturn(1);
		
		Product product3 = mock(Product.class);
		when(product3.getPrice()).thenReturn(2.0);
		when(product3.getStatusId()).thenReturn(2);
		
		Product product4 = mock(Product.class);
		when(product4.getPrice()).thenReturn(2.5);
		when(product4.getStatusId()).thenReturn(2);
		
		Product product5 = mock(Product.class);
		when(product5.getPrice()).thenReturn(3.0);
		when(product5.getStatusId()).thenReturn(3);
		
		Product product6 = mock(Product.class);
		when(product6.getPrice()).thenReturn(3.5);
		when(product6.getStatusId()).thenReturn(3);
		
		
		productList.add(product1);
		productList.add(product2);
		productList.add(product3);
		productList.add(product4);
		productList.add(product5);
		productList.add(product6);
		
		calculateStatusPrices();
		
		assertEquals(2.5, fridgeTotalCost, "Fridge cost was incorrect");
		assertEquals(4.5, usedTotalCost, "Used cost was incorrect");
		assertEquals(6.5, wasteTotalCost, "Waste cost was incorrect");
	}
}
