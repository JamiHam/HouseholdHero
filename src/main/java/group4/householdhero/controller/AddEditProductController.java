package group4.householdhero.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import group4.householdhero.model.Budget;
import group4.householdhero.model.Product;
import group4.householdhero.view.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEditProductController {
	private IController controller;
	private boolean editing;
	private Product product;
	private Budget budget;
	
	@FXML private TextField nameTextField;
	@FXML private TextField priceTextField;
	@FXML private ChoiceBox<String> categoryChoiceBox;
	@FXML private DatePicker bestBeforeDatePicker;
	@FXML private Label errorLabel;
	
	@FXML private Button saveButton;
	@FXML private Button usedButton;
	@FXML private Button deleteButton;
	@FXML private Button wasteButton;
	
	@FXML
	private void save() throws SQLException {
		if (validateInputs()) {
			String name = nameTextField.getText();
			String category = categoryChoiceBox.getValue();
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
	
	private void createProduct(String name, String category, double price, LocalDate bestBefore) throws SQLException {
		controller.createProduct(0, name, price, bestBefore, category, budget.getId(), 1);
		budget.setSpentBudget(budget.getSpentBudget() + price);
		controller.updateBudget(budget);
	}
	
	@FXML
	private void moveToUsed() throws SQLException {
		controller.changeProductStatus(product, "used");
		closeWindow();
	}
	
	@FXML
	private void delete() throws SQLException {
		controller.deleteProduct(product);
		budget.setSpentBudget(budget.getSpentBudget() - product.getPrice());
		controller.updateBudget(budget);
		closeWindow();
	}
	
	@FXML
	private void moveToWaste() throws SQLException {
		controller.changeProductStatus(product, "waste");
		closeWindow();
	}
	
	private void closeWindow() {
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
	}
	
	protected void setErrorMessage(String message) {
		errorLabel.setText(message);
	}
	
	private void showError(boolean error) {
		errorLabel.setVisible(error);
	}
	
	private void getCategories() throws SQLException {
		categoryChoiceBox.getItems().addAll(controller.getCategories());
	}
	
	private boolean validateInputs() {
		boolean validity = true;
		
		if (bestBeforeDatePicker.getValue() == null) {
			validity = false;
			setErrorMessage("Please select a best before date");
		}
		
		try {
			Double.parseDouble(priceTextField.getText());
		} catch (NumberFormatException e) {
			validity = false;
			setErrorMessage("The price must be a number");
		}
		
		if (categoryChoiceBox.getValue() == null) {
			validity = false;
			setErrorMessage("Please select a category");
		}
		
		if (nameTextField.getText().isEmpty()) {
			validity = false;
			setErrorMessage("Please enter a product name");
		}
		
		return validity;
	}
	
	public void initialize(boolean editing, Product product) throws SQLException {
		this.controller = App.getController();
		this.editing = editing;
		this.product = product;
		budget = controller.getBudget(LocalDate.now());
		
		getCategories();
		showError(false);
		
		if (editing) {
			usedButton.setVisible(true);
			deleteButton.setVisible(true);
			wasteButton.setVisible(true);
			nameTextField.setText(product.getName());
			categoryChoiceBox.setValue(product.getCategory());
			priceTextField.setText(Double.toString(product.getPrice()));
			bestBeforeDatePicker.setValue(product.getBestBefore());
		} else {
			usedButton.setVisible(false);
			deleteButton.setVisible(false);
			wasteButton.setVisible(false);
		}
	}
}