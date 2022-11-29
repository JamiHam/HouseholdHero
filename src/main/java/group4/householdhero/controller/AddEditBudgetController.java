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
	
	@FXML private ChoiceBox<String> languageChoiceBox;
	
	@FXML private DatePicker startDatePicker;
	@FXML private DatePicker endDatePicker;
	@FXML private TextField plannedBudgetTextField;
	@FXML private Label errorLabel;
	
	@FXML private Button saveButton;
	@FXML private Button deleteButton;
	
	private void setLanguageChoiceBox() {
		languageChoiceBox.getItems().add(App.bundle.getString("english.choice.text"));
		languageChoiceBox.getItems().add(App.bundle.getString("gaeilge.choice.text"));
	}
	
	@FXML
	private void changeSelectedLanguage() throws IOException {
		if (languageChoiceBox.getValue() == App.bundle.getString("english.choice.text")) {
			App.setLocaleEnglish();
		} if (languageChoiceBox.getValue() == App.bundle.getString("gaeilge.choice.text")) {
			App.setLocaleGaeilge();
		}
	}
	
	@FXML
	private void save() throws SQLException {
		if(validateInputs()) {
			LocalDate startDate = startDatePicker.getValue();
			LocalDate endDate = endDatePicker.getValue();
			double plannedBudget = Double.parseDouble(plannedBudgetTextField.getText());
			
			if (controller.checkBudgets(startDate, endDate)) {
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
				setErrorMessage("Date range overlaps with an existing budget. Please choose another one");
				showError(true);
			}
			
		} else {
			showError(true);
		}
	}
	
	//Will be completed in OTP2
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
	
	public void initialize(boolean editing, Budget budget) {
		this.controller = App.getController();
		this.budget = budget;
		this.editing = editing;
		
		setLanguageChoiceBox();
		
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
