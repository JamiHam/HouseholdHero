package group4.householdhero.view;

import java.io.IOException;
import java.time.LocalDate;

import group4.householdhero.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FridgeController {
	View view;
	
	@FXML private TableView<Product> fridgeTable;
	@FXML private TableColumn<Product, String> nameColumn;
	@FXML private TableColumn<Product, Integer> categoryColumn;
	@FXML private TableColumn<Product, Double> priceColumn;
	@FXML private TableColumn<Product, LocalDate> bestBeforeColumn;
	@FXML private TableColumn<Product, Button> editColumn;
	
	@FXML
	private void initialize() throws IOException {
		view = App.getView();
		initializeColumns();
		updateFridgeContents();
	}
	
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    private void initializeColumns() {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("categoryId"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		bestBeforeColumn.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("bestBefore"));
		editColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("editButton"));
    }
    
    private void updateFridgeContents() {
    	fridgeTable.setItems(FXCollections.observableArrayList(view.getProductsInFridge()));
    }
    
    static void editProduct(int id) {
    	System.out.println("Editing test " + id);
    }
}
