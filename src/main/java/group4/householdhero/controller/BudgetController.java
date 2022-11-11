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
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	@FXML private ChoiceBox<Budget> budgetChoiceBox;
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
	@FXML private TableColumn<Product, Integer> productsStatusColumn;
	@FXML private TableColumn<Product, Button> productsEditColumn;
	
	@FXML
	private void initialize() throws SQLException {
		controller = App.getController();
		budget = controller.getBudget(LocalDate.now());
		
		// Choice Box
		// Type arguments for Choice Box: String or LocalDate
        getBudgets();
		
		setBudgetInformation();
		
		initializeColumns();
		
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
	private void changeSelectedBudget() throws SQLException {
		budget = budgetChoiceBox.getValue();
		setBudgetInformation();
		setProducts();
	}
	
	@FXML
	private void switchToFridge() throws IOException {
		controller.showFridge();
	}
	
	@FXML
	private void editBudget() throws SQLException, IOException {
		controller.showBudgetWindow(true, budget);
	}
	
	private void setBudgetInformation() {
		startDateLabel.setText((budget.getStartDate()).toString());
		endDateLabel.setText((budget.getEndDate()).toString());
		plannedBudgetLabel.setText(Double.toString(budget.getPlannedBudget()));
		spentBudgetLabel.setText(Double.toString(budget.getSpentBudget()));
		remainingBudgetLabel.setText(Double.toString(budget.getPlannedBudget() - budget.getSpentBudget()));
	}
	
	private void setPieChart() {
		
	}
	
	private void getBudgets() throws SQLException {
		budgetChoiceBox.getItems().addAll(controller.getAllBudgets());
	}
	
	private void setProducts() throws SQLException {
		productsDuringBudgetTable.setItems(FXCollections.observableArrayList(controller.getProductsByBudget(budget.getId())));
	}
	
	private void initializeColumns() {
		productsNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		productsCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("category"));
		productsIconColumn.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("categoryImageView"));
		productsPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		productsBestBeforeColumn.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("bestBefore"));
		productsStatusColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("statusId"));
		//productsEditColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("editButton"));
	}
}
