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
		checkCurrentBudget();
		initializeColumns();
		updateTables();
	}
	
	protected void updateTables() throws SQLException {
		checkBestBefore();
		updateFridgeContents();
		updateExpiredContents();
	}
	
	/**
	 * Switches to waste view
	 * @throws IOException
	 */
    @FXML
    private void switchToBudget() throws IOException {
        App.showBudget();
    }
    
    /**
     * Opens AddEditProductGUI with editing mode off
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    private void addProduct() throws IOException, SQLException {
    	App.showProductWindow(false, null);
    }
    
    /**
     * Opens AddEditProductGUI with editing mode on
     * @param product
     * @throws IOException
     * @throws SQLException 
     */
    protected static void editProduct(Product product) throws IOException, SQLException {
    	App.showProductWindow(true, product);
    }
    
    /**
     * Checks for an active budget and opens AddEditBudgetGUI if one doesn't exist
     * @throws IOException
     * @throws SQLException 
     */
    private void checkCurrentBudget() throws IOException, SQLException {
    	if (view.getBudget(LocalDate.now()) == null) {
    		App.showBudgetWindow(false, null);
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
    	fridgeTable.setItems(FXCollections.observableArrayList(view.getProducts("fridge")));
    }
    
    /**
     * Gets all expired items from the database and sets them to expiredTable
     * @throws SQLException
     */
    private void updateExpiredContents() throws SQLException {
    	List<Product> list = view.getProducts("expired");
    	if (list.isEmpty()) {
    		expiredVBox.setVisible(false);
    	} else {
    		expiredVBox.setVisible(true);
    		expiredTable.setItems(FXCollections.observableArrayList(list));
    	}
    }
    
    private void checkBestBefore() throws SQLException {
    	view.checkBestBefore();
    }
}
