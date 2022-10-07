package group4.householdhero.view;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import group4.householdhero.model.Product;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class FridgeController {
	private View view;
	
	@FXML private VBox expiredVBox;
	
	@FXML private TableView<Product> fridgeTable;
	@FXML private TableColumn<Product, String> fridgeNameColumn;
	@FXML private TableColumn<Product, Integer> fridgeCategoryColumn;
	@FXML private TableColumn<Product, ImageView> fridgeIconColumn;
	@FXML private TableColumn<Product, Double> fridgePriceColumn;
	@FXML private TableColumn<Product, LocalDate> fridgeBestBeforeColumn;
	@FXML private TableColumn<Product, Button> fridgeEditColumn;
	
	@FXML private TableView<Product> expiredTable;
	@FXML private TableColumn<Product, String> expiredNameColumn;
	@FXML private TableColumn<Product, Integer> expiredCategoryColumn;
	@FXML private TableColumn<Product, ImageView> expiredIconColumn;
	@FXML private TableColumn<Product, Double> expiredPriceColumn;
	@FXML private TableColumn<Product, LocalDate> expiredBestBeforeColumn;
	@FXML private TableColumn<Product, Button> expiredEditColumn;
	
	@FXML
	private void initialize() throws IOException, SQLException {
		view = App.getView();
		//checkCurrentBudget();
		initializeColumns();
		updateTables();
	}
	
	protected void updateTables() throws SQLException {
		updateFridgeContents();
		updateExpiredContents();
	}
	
	/**
	 * Switches to waste view
	 * @throws IOException
	 */
    @FXML
    private void switchToWaste() throws IOException {
        App.setRoot("secondary");
    }
    
    /**
     * Opens AddEditProductGUI with editing mode off
     * @throws IOException
     */
    @FXML
    private void addProduct() throws IOException {
    	App.openProductWindow(false, null);
    }
    
    /**
     * Opens AddEditProductGUI with editing mode on
     * @param product
     * @throws IOException
     */
    protected static void editProduct(Product product) throws IOException {
    	App.openProductWindow(true, product);
    }
    
    /**
     * Checks for an active budget and opens AddEditBudgetGUI if one doesn't exist
     * @throws IOException
     * @throws SQLException 
     */
    private void checkCurrentBudget() throws IOException, SQLException {
    	if (view.getCurrentBudget() == null) {
    		App.openBudgetWindow(false, null);
    	}
    }
    
    /**
     * Initializes all columns for fridgeTable and expiredTable
     */
    private void initializeColumns() {
    	fridgeNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		fridgeCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("category"));
		fridgeIconColumn.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("categoryImageView"));
		fridgePriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		fridgeBestBeforeColumn.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("bestBefore"));
		fridgeEditColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("editButton"));
		
		expiredNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		expiredCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("category"));
		expiredIconColumn.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("categoryImageView"));
		expiredPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		expiredBestBeforeColumn.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("bestBefore"));
		expiredEditColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("editButton"));
    }
    
    /**
     * Gets all fridge items from the database and sets them to fridgeTable
     * @throws SQLException
     */
    private void updateFridgeContents() throws SQLException {
    	fridgeTable.setItems(FXCollections.observableArrayList(view.getProductsInFridge()));
    }
    
    /**
     * Gets all expired items from the database and sets them to expiredTable
     * @throws SQLException
     */
    private void updateExpiredContents() throws SQLException {
    	List<Product> list = view.getExpiredProducts();
    	expiredTable.setItems(FXCollections.observableArrayList(list));
    	if (list.isEmpty()) {
    		//expiredVBox.setVisible(false);
    	} else {
    		//expiredTable.setItems(FXCollections.observableArrayList(list));
    	}
    }
}
