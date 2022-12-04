package householdhero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import group4.householdhero.controller.BudgetController;
import group4.householdhero.controller.Controller;
import group4.householdhero.model.Budget;
import group4.householdhero.model.Product;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;

@TestInstance(Lifecycle.PER_CLASS)
public class BudgetControllerTest extends BudgetController {
	JFXPanel panel;
	
	@BeforeAll
	public void setController() {
		controller = mock(Controller.class);
		panel = new JFXPanel();
	}
	
	@Test
	public void switchToFridgeTest() throws IOException {
		when(controller.showFridge()).thenReturn(true);
		
		assertEquals(true, switchToFridge(), "Switching to fridge failed.");
	}
	
	@Test
	public void editBudgetTest() throws IOException, SQLException {
		when(controller.showBudgetWindow(true, budget)).thenReturn(true);
		
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
	
	@Test
	public void setBudgetInformationTest() {
		startDateLabel = new Label();
		endDateLabel = new Label();
		plannedBudgetLabel = new Label();
		spentBudgetLabel = new Label();
		remainingBudgetLabel = new Label();
		
		budget = mock(Budget.class);
		when(budget.getStartDate()).thenReturn(LocalDate.parse("2000-01-01"));
		when(budget.getEndDate()).thenReturn(LocalDate.parse("2000-12-31"));
		when(budget.getPlannedBudget()).thenReturn(100.0);
		when(budget.getSpentBudget()).thenReturn(25.0);
		
		setBudgetInformation();
		
		assertEquals("2000-01-01", startDateLabel.getText(), "Start date was incorrect");
		assertEquals("2000-12-31", endDateLabel.getText(), "End date was incorrect");
		assertEquals("100.0", plannedBudgetLabel.getText(), "Planned budget was incorrect");
		assertEquals("25.0", spentBudgetLabel.getText(), "Spent budget was incorrect");
		assertEquals("75.0", remainingBudgetLabel.getText(), "Remaining budget was incorrect");
	}
}
