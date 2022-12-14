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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import group4.householdhero.controller.Controller;
import group4.householdhero.controller.FridgeController;
import group4.householdhero.model.Model;
import group4.householdhero.model.Product;
import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

@TestInstance(Lifecycle.PER_CLASS)
public class FridgeControllerJavaFXTest extends FridgeController {
	
	@BeforeAll
	public void initJfxRuntime() {
		Platform.startup(() -> {});
	}
	
	@BeforeAll
	public void setController() {
		controller = mock(Controller.class);
		when(controller.getLocalisedString("english.choice.text")).thenReturn("English");
		when(controller.getLocalisedString("gaeilge.choice.text")).thenReturn("Gaeilge");
		when(controller.setLocale("en_IE")).thenReturn(new Locale("en_IE"));
		when(controller.setLocale("ga_IE")).thenReturn(new Locale("ga_IE"));
	}
	
	@Test
	public void setLanguageChoiceBoxTest() {
		languageChoiceBox = new ChoiceBox<String>();
		when(controller.getLocale()).thenReturn(new Locale("en_IE"));
		setLanguageChoiceBox();
		assertEquals("English", languageChoiceBox.getValue(), "The value of languageChoiceBox is incorrect");
		
		languageChoiceBox = new ChoiceBox<String>();
		when(controller.getLocale()).thenReturn(new Locale("ga_IE"));
		setLanguageChoiceBox();
		assertEquals("Gaeilge", languageChoiceBox.getValue(), "The value of languageChoiceBox is incorrect");
		
		assertEquals(Arrays.asList("English", "Gaeilge"), languageChoiceBox.getItems(), "The items in languageChoicebox were incorrect");
	}
	
	@Test
	public void changeSelectedLanguageTest() throws IOException {
		languageChoiceBox = new ChoiceBox<String>();
		when(controller.getLocale()).thenReturn(new Locale("en_IE"));
		setLanguageChoiceBox();
		
		languageChoiceBox.setValue("English");
		assertEquals("en_ie", changeSelectedLanguage().toString(), "The selected language was incorrect");
		
		languageChoiceBox.setValue("Gaeilge");
		assertEquals("ga_ie", changeSelectedLanguage().toString(), "The selected language was incorrect");
	}
	
	@Test
	public void updateFridgeContentsTest() throws SQLException {
		fridgeTable = new TableView<Product>();
		List<Product> products = new ArrayList<Product>();
		Product product = mock(Product.class);
		when(product.getBestBefore()).thenReturn(LocalDate.parse("2022-12-15"));
		products.add(product);
		when(controller.getProducts("fridge")).thenReturn(products);
		
		updateFridgeContents();
		assertEquals(1, fridgeTable.getItems().size(), "The fridge table contents were incorrect");
	}
}
