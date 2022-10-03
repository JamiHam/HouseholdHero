package group4.householdhero.view;

import java.sql.SQLException;
import java.time.LocalDate;

import group4.householdhero.model.Budget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEditBudgetController {
	private View view;
	private boolean editing;
	private Budget budget;
	
	@FXML private DatePicker startDatePicker;
	@FXML private DatePicker endDatePicker;
	@FXML private TextField plannedBudgetTextField;
	@FXML private Label errorLabel;
	
	@FXML private Button saveButton;
	@FXML private Button deleteButton;
	
	/**
	 * Saves the budget
	 * @throws SQLException 
	 */
	@FXML
	private void save() throws SQLException {
		if(validateInputs()) {
			LocalDate startDate = startDatePicker.getValue();
			LocalDate endDate = endDatePicker.getValue();
			double plannedBudget = Double.parseDouble(plannedBudgetTextField.getText());
			
			if (editing) {
				//view.updateBudget(budget.getId(), plannedBudget, budget.getSpentBudget(), startDate, endDate);
			} else {
				view.createBudget(0, plannedBudget, 0, startDate, endDate);
			}
			
			closeWindow();
		}
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
	 * Returns true if user inputs are valid and false if not
	 * @return
	 */
	private boolean validateInputs() {
		boolean validity = true;
		if (startDatePicker.getValue() == null) validity = false;
		if (endDatePicker.getValue() == null) validity = false;
		try {
			Double.parseDouble(plannedBudgetTextField.getText());
		} catch (NumberFormatException e) {
			validity = false;
		}
		
		return validity;
	}
	
	protected void initialize(boolean editing, Budget budget) {
		this.view = App.getView();
		this.budget = budget;
		this.editing = editing;

		if (editing) {
			deleteButton.setVisible(true);
		} else {
			deleteButton.setVisible(false);
		}
	}
}
