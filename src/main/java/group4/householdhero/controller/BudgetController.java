package group4.householdhero.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import group4.householdhero.model.Budget;
import group4.householdhero.model.Product;
import group4.householdhero.view.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class BudgetController {
	private IController controller;
	private Budget budget;
	
	/*
	@FXML private DatePicker startDatePicker;
	@FXML private DatePicker endDatePicker;
	@FXML private TextField plannedBudgetTextField;
	@FXML private TextField spentBudgetTextField;
	*/
	
	@FXML private ChoiceBox<String> budgetChoiceBox;
	@FXML private Label startDateLabel;
	@FXML private Label endDateLabel;
	@FXML private Label plannedBudgetLabel;
	@FXML private Label spentBudgetLabel;
	@FXML private Label remainingBudgetLabel;
	
	@FXML private PieChart pieChart;
	
	@FXML private TableView<Product> productsDuringBudgetTable;
	@FXML private TableColumn<Product, String> productsNameColumn;
	@FXML private TableColumn<Product, Integer> productsCategoryColumn;
	@FXML private TableColumn<Product, ImageView> productsIconColumn;
	@FXML private TableColumn<Product, Double> productsPriceColumn;
	@FXML private TableColumn<Product, LocalDate> productsBestBeforeColumn;
	@FXML private TableColumn<Product, String> productsStatusColumn;
	@FXML private TableColumn<Product, Button> productsEditColumn;
	
	@FXML
	private void initialize() throws SQLException {
		controller = App.getController();
		budget = controller.getBudget(LocalDate.now());
		
		// Choice Box
		// Type arguments for Choice Box: String or LocalDate
        List<String> list = new ArrayList<String>();
        list.add("Budget Period 1");
        list.add("Budget Period 2");
        list.add("Budget Period 3");
        ObservableList obList = FXCollections.observableList(list);
        budgetChoiceBox.setItems(obList);
        budgetChoiceBox.setValue("Budget Period 1");
		
		startDateLabel.setText((budget.getStartDate()).toString());
		endDateLabel.setText((budget.getEndDate()).toString());
		plannedBudgetLabel.setText(Double.toString(budget.getPlannedBudget()));
		spentBudgetLabel.setText(Double.toString(budget.getSpentBudget()));
		
		// Still missing remaining budget
		
		// Example pie chart for testing purposes
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Fridge", 20),
                new PieChart.Data("Used", 30),
                new PieChart.Data("Expired", 10),
                new PieChart.Data("Waste", 40));
        pieChart.setData(pieChartData);

	}
	
	@FXML
	private void switchToFridge() throws IOException {
		controller.showFridge();
	}
	
	@FXML
	private void editBudget() throws SQLException, IOException {
		controller.showBudgetWindow(true, budget);
	}
}
