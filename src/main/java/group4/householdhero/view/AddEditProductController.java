package group4.householdhero.view;

import java.sql.SQLException;
import java.time.LocalDate;
import group4.householdhero.model.Budget;
import group4.householdhero.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEditProductController {
	private View view;
	private boolean editing;
	private Product product;
	
	@FXML private TextField nameTextField;
	@FXML private TextField priceTextField;
	@FXML private ChoiceBox<String> categoryChoiceBox;
	@FXML private DatePicker bestBeforeDatePicker;
	@FXML private Label errorLabel;
	
	@FXML private Button saveButton;
	@FXML private Button deleteButton;
	@FXML private Button wasteButton;
	
	/**
	 * Saves the product to database
	 * @throws SQLException 
	 */
	@FXML
	private void save() throws SQLException {
		if (validateInputs()) {
			String name = nameTextField.getText();
			String category = categoryChoiceBox.getValue();
			double price = Double.parseDouble(priceTextField.getText());
			LocalDate bestBefore = bestBeforeDatePicker.getValue();
			Budget budget = view.getCurrentBudget();
			
			if (editing) {
				view.updateProduct(product.getId(), name, price, bestBefore, category, product.getBudgetId(), 1);
			} else {
				view.createProduct(0, name, price, bestBefore, category, budget.getId(), 1);
			}
			
			closeWindow();
		} else {
			setError(true);
		}
	}
	
	/**
	 * Deletes the product
	 * @throws SQLException 
	 */
	@FXML
	private void delete() throws SQLException {
		view.deleteProduct(product);
		closeWindow();
	}
	
	/**
	 * Changes the product's status to "waste"
	 * @throws SQLException 
	 */
	@FXML
	private void moveToWaste() throws SQLException {
		view.changeProductStatus(product, "waste");
		closeWindow();
	}
	
	/**
	 * Closes the window
	 */
	private void closeWindow() {
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Sets visibility for errorLabel
	 * @param error
	 */
	protected void setError(boolean error) {
		//errorLabel.setVisible(error);
	}
	
	/**
	 * Gets all categories from the database and sets them to categoryChoiceBox
	 */
	private void getCategories() {
		categoryChoiceBox.getItems().addAll(view.getCategories());
	}
	
	/**
	 * Returns true if user inputs are valid and false if not
	 * @return
	 */
	private boolean validateInputs() {
		boolean validity = true;
		
		if (nameTextField.getText().isEmpty()) validity = false;
		if (categoryChoiceBox.getValue() == null) validity = false;
		if (bestBeforeDatePicker.getValue() == null) validity = false;
		
		try {
			Double.parseDouble(priceTextField.getText());
		} catch (NumberFormatException e) {
			validity = false;
		}
		
		return validity;
	}
	
	protected void initialize(boolean editing, Product product) {
		this.view = App.getView();
		this.editing = editing;
		this.product = product;
		getCategories();
		setError(false);
		
		if (editing) {
			deleteButton.setVisible(true);
			wasteButton.setVisible(true);
			nameTextField.setText(product.getName());
			categoryChoiceBox.setValue(product.getCategory());
			priceTextField.setText(Double.toString(product.getPrice()));
			bestBeforeDatePicker.setValue(product.getBestBefore());
		} else {
			deleteButton.setVisible(false);
			wasteButton.setVisible(false);
		}
	}
}
