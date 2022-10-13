package group4.householdhero.controller;

import java.sql.SQLException;
import java.time.LocalDate;

import group4.householdhero.model.Budget;
import group4.householdhero.view.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	
	@FXML
	private void save() throws SQLException {
		if(validateInputs()) {
			LocalDate startDate = startDatePicker.getValue();
			LocalDate endDate = endDatePicker.getValue();
			double plannedBudget = Double.parseDouble(plannedBudgetTextField.getText());
			
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
			showError(true);
		}
	}
	
	@FXML
	private void deleteBudget() throws SQLException {
		//controller.deleteBudget();
	}
	
	private void closeWindow() {
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
	}
	
	private void setErrorMessage(String message) {
		errorLabel.setText(message);
	}
	
	private void showError(boolean error) {
		errorLabel.setVisible(error);
	}
	
	private boolean validateInputs() {
		boolean validity = true;
		
		try {
			Double.parseDouble(plannedBudgetTextField.getText());
		} catch (NumberFormatException e) {
			validity = false;
			setErrorMessage("Planned budget must be a number");
		}
		
		if (endDatePicker.getValue() == null) {
			validity = false;
			setErrorMessage("Please select an end date for your budget");
		}
		
		if (startDatePicker.getValue() == null) {
			validity = false;
			setErrorMessage("Please select a start date for your budget");
		}
		
		return validity;
	}
	
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