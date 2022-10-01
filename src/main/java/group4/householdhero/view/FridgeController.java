package group4.householdhero.view;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import group4.householdhero.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FridgeController {
	private View view;
	
	@FXML private TableView<Product> fridgeTable;
	@FXML private TableColumn<Product, String> nameColumn;
	@FXML private TableColumn<Product, Integer> categoryColumn;
	@FXML private TableColumn<Product, Double> priceColumn;
	@FXML private TableColumn<Product, LocalDate> bestBeforeColumn;
	@FXML private TableColumn<Product, Button> editColumn;
	
	@FXML
	private void initialize() throws IOException, SQLException {
		view = App.getView();
		initializeColumns();
		updateFridgeContents();
		checkCurrentBudget();
	}
	
    @FXML
    private void switchToWaste() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    private void addProduct() throws IOException {
    	FXMLLoader fxmlLoader = App.openWindow("secondary");
    	App.initializeAddEditProductController(fxmlLoader, false, null);
    }
    
    static void editProduct(Product product) throws IOException {
    	FXMLLoader fxmlLoader = App.openWindow("secondary");
    	App.initializeAddEditProductController(fxmlLoader, true, product);
    }
    
    private void checkCurrentBudget() throws IOException {
    	if (view.getCurrentBudget() == null) {
    		App.openWindow("secondary");
    	}
    }
    
    private void initializeColumns() {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("category"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		bestBeforeColumn.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("bestBefore"));
		editColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("editButton"));
    }
    
    private void updateFridgeContents() throws SQLException {
    	fridgeTable.setItems(FXCollections.observableArrayList(view.getProductsInFridge()));
    }
}
