package group4.householdhero.view;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import group4.householdhero.model.Budget;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class BudgetController {
	private IView view;
	
	@FXML private DatePicker startDatePicker;
	@FXML private DatePicker endDatePicker;
	@FXML private TextField plannedBudgetTextField;
	@FXML private TextField spentBudgetTextField;
	
	@FXML
	private void initialize() throws SQLException {
		view = App.getView();
		Budget budget = view.getBudget(LocalDate.now());
		
		startDatePicker.setValue(budget.getStartDate());
		endDatePicker.setValue(budget.getEndDate());
		plannedBudgetTextField.setText(Double.toString(budget.getPlannedBudget()));
		spentBudgetTextField.setText(Double.toString(budget.getSpentBudget()));
	}
	
	@FXML
	private void switchToFridge() throws IOException {
		App.showFridge();
	}
	
	
}
