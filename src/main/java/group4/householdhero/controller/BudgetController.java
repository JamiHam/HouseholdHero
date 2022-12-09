package group4.householdhero.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import group4.householdhero.model.Budget;
import group4.householdhero.model.Product;
import group4.householdhero.view.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
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
	protected IController controller;
	protected Budget budget;
	protected ArrayList<Product> productList;
	protected double fridgeTotalCost;
	protected double usedTotalCost;
	protected double wasteTotalCost;
	
	@FXML private ChoiceBox<String> languageChoiceBox;
	
	@FXML private ChoiceBox<Budget> budgetChoiceBox;
	@FXML protected Label startDateLabel;
	@FXML protected Label endDateLabel;
	@FXML protected Label plannedBudgetLabel;
	@FXML protected Label spentBudgetLabel;
	@FXML protected Label remainingBudgetLabel;
	
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
		
		setLanguageChoiceBox();
		
        getBudgets();
		
		setBudgetInformation();
		
		initializeColumns();
		
		setProducts();
		
        calculateStatusPrices();
        setPieChart();

	}
	
	/**
	 * Adds language options to choice box
	 */
	protected void setLanguageChoiceBox() {
		ObservableList<String> languages = FXCollections.observableArrayList(
				App.bundle.getString("english.choice.text"),
				App.bundle.getString("gaeilge.choice.text"));
		
		if (App.getLocale().toString().equals("en_ie")) {
			languageChoiceBox.setValue("English");
		} else {
			languageChoiceBox.setValue("Gaeilge");
		}

		languageChoiceBox.setItems(languages);
	}
	
	/**
	 * Changes the selected language
	 * @throws IOException
	 */
	@FXML
	private void changeSelectedLanguage() throws IOException {
		if (languageChoiceBox.getValue() == App.bundle.getString("english.choice.text")) {
			App.setLocaleEnglish();
		} if (languageChoiceBox.getValue() == App.bundle.getString("gaeilge.choice.text")) {
			App.setLocaleGaeilge();
		}
	}
	
	/**
	 * Changes the selected budget
	 * @throws SQLException
	 */
	@FXML
	private void changeSelectedBudget() throws SQLException {
		budget = budgetChoiceBox.getValue();
		setBudgetInformation();
		setProducts();
		calculateStatusPrices();
		setPieChart();
	}
	
	/**
	 * Changes to fridge-view
	 * @return
	 * @throws IOException
	 */
	@FXML
	protected boolean switchToFridge() throws IOException {
		return controller.showFridge();
	}
	
	/**
	 * Opens the budget editing window
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@FXML
	protected boolean editBudget() throws SQLException, IOException {
		return controller.showBudgetWindow(true, budget);
	}
	
	/**
	 * Sets selected budget's information to labels
	 */
	protected void setBudgetInformation() {
		startDateLabel.setText((budget.getStartDate()).toString());
		endDateLabel.setText((budget.getEndDate()).toString());
		plannedBudgetLabel.setText(Double.toString(budget.getPlannedBudget()));
		spentBudgetLabel.setText(Double.toString(budget.getSpentBudget()));
		remainingBudgetLabel.setText(Double.toString(budget.getPlannedBudget() - budget.getSpentBudget()));
	}
	
	/**
	 * Calculates the total price of each status in the current budget
	 */
	protected void calculateStatusPrices() {
		fridgeTotalCost = 0;
		usedTotalCost = 0;
		wasteTotalCost = 0;
		
		for (Product product : productList) {
			switch (product.getStatusId()) {
			case 1:
				fridgeTotalCost += product.getPrice();
				break;
			case 2:
				usedTotalCost += product.getPrice();
				break;
			case 3:
				wasteTotalCost += product.getPrice();
				break;
			}
		}
	}
	
	/**
	 * Sets status prices to pie chart
	 */
	private void setPieChart() {
		ObservableList<PieChart.Data> pieChartData =
			FXCollections.observableArrayList(
            new PieChart.Data(App.bundle.getString("budget.distribution.fridge.text"), fridgeTotalCost),
            new PieChart.Data(App.bundle.getString("budget.distribution.used.text"), usedTotalCost),
            new PieChart.Data(App.bundle.getString("budget.distribution.waste.text"), wasteTotalCost),
			new PieChart.Data(App.bundle.getString("remaining.budget.text"), budget.getPlannedBudget() - budget.getSpentBudget()));
		
        pieChart.setData(pieChartData);
        pieChart.setLabelsVisible(false); // Hides the pie chart labels
        pieChart.setLegendSide(Side.LEFT); // Sets legend on the left side of pie chart
        pieChart.setStartAngle(90); // Sets start angle for pie chart slices
	}
	
	/**
	 * Gets all budgets from database and sets them to budget choice box
	 * @throws SQLException
	 */
	private void getBudgets() throws SQLException {
		ObservableList<Budget> budgetList = FXCollections.observableArrayList(controller.getAllBudgets());
		budgetChoiceBox.setItems(budgetList);
	}
	
	/**
	 * Gets all products in the selected budget and adds them to the product table
	 * @throws SQLException
	 */
	private void setProducts() throws SQLException {
		productList = (ArrayList<Product>) controller.getProductsByBudget(budget.getId());
		controller.localize(productList);
		productsDuringBudgetTable.setItems(FXCollections.observableArrayList(productList));
	}
	
	/**
	 * Initializes table columns
	 */
	private void initializeColumns() {
		productsNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		productsCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("localizedCategory"));
		productsIconColumn.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("categoryImageView"));
		productsPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		productsBestBeforeColumn.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("bestBefore"));
		productsStatusColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("status"));
	}
}
