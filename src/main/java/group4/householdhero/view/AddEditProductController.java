package group4.householdhero.view;

import java.time.LocalDate;

import group4.householdhero.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddEditProductController {
	private View view;
	private boolean editing;
	private Product product;
	
	@FXML private TextField nameField;
	@FXML private TextField categoryField;
	@FXML private TextField priceField;
	@FXML private DatePicker bestBeforeField;
	
	@FXML private Button saveButton;
	@FXML private Button deleteButton;
	@FXML private Button wasteButton;
	
	@FXML
	private void save() {
		String name = nameField.getText();
		String category = categoryField.getText();
		double price = Double.parseDouble(priceField.getText());
		LocalDate bestBefore = bestBeforeField.getValue();
		int budgetId = view.getCurrentBudget();
		
		if (editing) {
			view.updateProduct(product.getId(), name, price, bestBefore, category, product.getBudgetId(), 1);
		} else {
			view.createProduct(0, name, price, bestBefore, category, budgetId, 1);
		}
	}
	
	@FXML
	private void delete() {
		view.deleteProduct(product);
	}
	
	@FXML
	private void moveToWaste() {
		view.changeProductStatus(product, "waste");
	}
	
	protected void initialize(boolean editing, Product product) {
		view = App.getView();
		this.editing = editing;
		this.product = product;
		
		if (editing) {
			deleteButton.setVisible(true);
			wasteButton.setVisible(true);
			
			nameField.setText(product.getName());
			categoryField.setText(product.getCategory());
			priceField.setText(Double.toString(product.getPrice()));
			bestBeforeField.setValue(product.getBestBefore());
		} else {
			deleteButton.setVisible(false);
			wasteButton.setVisible(false);
		}
	}
}
