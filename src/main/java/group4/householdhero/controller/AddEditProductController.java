package group4.householdhero.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import group4.householdhero.model.Budget;
import group4.householdhero.model.Product;
import group4.householdhero.view.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEditProductController {
	protected IController controller;
	private boolean editing;
	private Product product;
	private Budget budget;
	
	@FXML private TextField nameTextField;
	@FXML private TextField priceTextField;
	@FXML private ChoiceBox<String> categoryChoiceBox;
	@FXML private DatePicker bestBeforeDatePicker;
	@FXML private Label errorLabel;
	
	@FXML private Button saveButton;
	@FXML private Button deleteButton;
	
	/**
	 * Saves the product to database
	 * @throws SQLException
	 */
	@FXML
	private void save() throws SQLException {
		if (validateInputs()) {
			String name = nameTextField.getText();
			String category = controller.unlocalizeCategory(categoryChoiceBox.getValue());
			double price = Double.parseDouble(priceTextField.getText());
			LocalDate bestBefore = bestBeforeDatePicker.getValue();
			
			if (editing) {
				updateProduct(name, category, price, bestBefore);
			} else {
				createProduct(name, category, price, bestBefore);
			}
			closeWindow();
		} else {
			showError(true);
		}
	}
	
	/**
	 * Updates the pre-existing product in database and adjusts the budget accordingly
	 * @param name
	 * @param category
	 * @param price
	 * @param bestBefore
	 * @throws SQLException
	 */
	private void updateProduct(String name, String category, double price, LocalDate bestBefore) throws SQLException {
		budget.setSpentBudget(budget.getSpentBudget() - product.getPrice());
		budget.setSpentBudget(budget.getSpentBudget() + price);
		
		product.setName(name);
		product.setCategory(category);
		product.setPrice(price);
		product.setBestBefore(bestBefore);
		
		controller.updateProduct(product);
		controller.updateBudget(budget);
	}
	
	/**
	 * Adds a new product to database and adjusts the budget accordingly
	 * @param name
	 * @param category
	 * @param price
	 * @param bestBefore
	 * @throws SQLException
	 */
	private void createProduct(String name, String category, double price, LocalDate bestBefore) throws SQLException {
		controller.createProduct(0, name, price, bestBefore, category, budget.getId(), 1);
		budget.setSpentBudget(budget.getSpentBudget() + price);
		controller.updateBudget(budget);
	}
	
	/**
	 * Deletes the product from database
	 * @throws SQLException
	 */
	@FXML
	private void delete() throws SQLException {
		controller.deleteProduct(product);
		budget.setSpentBudget(budget.getSpentBudget() - product.getPrice());
		controller.updateBudget(budget);
		closeWindow();
	}
	
	/**
	 * Closes the editing window
	 */
	private void closeWindow() {
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Sets error message to given string
	 * @param message
	 */
	protected void setErrorMessage(String message) {
		errorLabel.setText(message);
	}
	
	/**
	 * Makes error message visible
	 * @param error
	 */
	private void showError(boolean error) {
		errorLabel.setVisible(error);
	}
	
	/**
	 * Sets all categories to choice box
	 * @throws SQLException
	 */
	private void getCategories() throws SQLException {
		List<String> categories = controller.getLocalizedCategories();
		System.out.println(categories);
		categoryChoiceBox.setItems(FXCollections.observableArrayList(categories));
	}
	
	/**
	 * Validates the user's inputs
	 * @return
	 */
	private boolean validateInputs() {
		boolean validity = true;
		
		if (bestBeforeDatePicker.getValue() == null) {
			validity = false;
			setErrorMessage(App.bundle.getString("product.validate.input.best.before.text"));
		}
		
		try {
			Double.parseDouble(priceTextField.getText());
		} catch (NumberFormatException e) {
			validity = false;
			setErrorMessage(App.bundle.getString("product.validate.input.price.text"));
		}
		
		if (categoryChoiceBox.getValue() == null) {
			validity = false;
			setErrorMessage(App.bundle.getString("product.validate.input.category.text"));
		}
		
		if (nameTextField.getText().isEmpty()) {
			validity = false;
			setErrorMessage(App.bundle.getString("product.validate.input.product.text"));
		}
		
		return validity;
	}
	
	/**
	 * Initializes the window in either adding or editing mode
	 * @param editing
	 * @param product
	 * @throws SQLException
	 */
	public void initialize(boolean editing, Product product) throws SQLException {
		this.controller = App.getController();
		this.editing = editing;
		this.product = product;
		budget = controller.getBudget(LocalDate.now());
		
		getCategories();
		showError(false);
		
		if (editing) {
			deleteButton.setVisible(true);
			nameTextField.setText(product.getName());
			categoryChoiceBox.setValue(product.getCategory());
			priceTextField.setText(Double.toString(product.getPrice()));
			bestBeforeDatePicker.setValue(product.getBestBefore());
		} else {
			deleteButton.setVisible(false);
		}
	}
}
