package group4.householdhero.view;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBudgetController {
	private View view;
	
	@FXML DatePicker startDateField;
	@FXML DatePicker endDateField;
	@FXML TextField plannedBudgetField;
	@FXML Button saveButton;
	
	@FXML
	private void save() {
		LocalDate startDate = startDateField.getValue();
		LocalDate endDate = endDateField.getValue();
		double plannedBudget = Double.parseDouble(plannedBudgetField.getText());
		
		view.createBudget(0, plannedBudget, 0, startDate, endDate);
		
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	protected void initialize() {
		view = App.getView();
	}
}
