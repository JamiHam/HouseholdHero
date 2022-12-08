package group4.householdhero.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import group4.householdhero.model.Budget;
import group4.householdhero.view.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEditBudgetController {
	private IController controller;
	private boolean editing;
	private Budget budget;
	
	@FXML private DatePicker startDatePicker;
	@FXML private DatePicker endDatePicker;
	@FXML private TextField plannedBudgetTextField;
	@FXML private Label errorLabel;
	
	@FXML private Button saveButton;
	@FXML private Button deleteButton;
	
	/**
	 * Saves current budget to database
	 * @throws SQLException
	 */
	@FXML
	private void save() throws SQLException {
		if(validateInputs()) {
			LocalDate startDate = startDatePicker.getValue();
			LocalDate endDate = endDatePicker.getValue();
			double plannedBudget = Double.parseDouble(plannedBudgetTextField.getText());
			
			if (controller.checkBudgets(startDate, endDate)) {
				System.out.println(controller.checkBudgets(startDate, endDate));
				if (editing) {
					budget.setStartDate(startDate);
					budget.setEndDate(endDate);
					budget.setPlannedBudget(plannedBudget);
					controller.updateBudget(budget);
				} else {
					controller.createBudget(0, plannedBudget, 0, startDate, endDate);
				}
				closeWindow();
			} else {
				setErrorMessage(App.bundle.getString("budget.validate.input.date.range.overlaps.text"));
				showError(true);
			}
			
		} else {
			showError(true);
		}
	}
	
	/**
	 * Deletes current budget from database
	 * @throws SQLException
	 */
	@FXML
	private void deleteBudget() throws SQLException {
		controller.deleteBudget(budget);
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
	private void setErrorMessage(String message) {
		errorLabel.setText(message);
		errorLabel.setWrapText(true); // Wraps long error message texts
	}
	
	/**
	 * Makes error message visible
	 * @param error
	 */
	private void showError(boolean error) {
		errorLabel.setVisible(error);
	}
	
	/**
	 * Validates the user's inputs
	 * @return
	 */
	private boolean validateInputs() {
		boolean validity = true;
		
		try {
			Double.parseDouble(plannedBudgetTextField.getText());
		} catch (NumberFormatException e) {
			validity = false;
			setErrorMessage(App.bundle.getString("budget.validate.input.number.text"));
		}
		
		if (endDatePicker.getValue() == null) {
			validity = false;
			setErrorMessage(App.bundle.getString("budget.validate.input.end.date.text"));
		}
		
		if (startDatePicker.getValue() == null) {
			validity = false;
			setErrorMessage(App.bundle.getString("budget.validate.input.start.date.text"));
		}
		
		return validity;
	}
	
	/**
	 * Initializes the window in either adding or editing mode
	 * @param editing
	 * @param budget
	 */
	public void initialize(boolean editing, Budget budget) {
		this.controller = App.getController();
		this.budget = budget;
		this.editing = editing;
		
		showError(false);

		if (editing) {
			deleteButton.setVisible(true);
			startDatePicker.setValue(budget.getStartDate());
			endDatePicker.setValue(budget.getEndDate());
			plannedBudgetTextField.setText(Double.toString(budget.getPlannedBudget()));
		} else {
			deleteButton.setVisible(false);
		}
	}
}
