package group4.householdhero.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import group4.householdhero.model.Product;
import group4.householdhero.view.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class FridgeController {
	protected static IController controller;
	
	@FXML private VBox expiredVBox;
	
	@FXML private TableView<Product> fridgeTable;
	@FXML private TableColumn<Product, String> fridgeNameColumn;
	@FXML private TableColumn<Product, Integer> fridgeCategoryColumn;
	@FXML private TableColumn<Product, ImageView> fridgeIconColumn;
	@FXML private TableColumn<Product, Double> fridgePriceColumn;
	@FXML private TableColumn<Product, LocalDate> fridgeBestBeforeColumn;
	@FXML private TableColumn<Product, Button> fridgeEditColumn;
	@FXML private TableColumn<Product, String> fridgeExpirationColumn;
	
	/*@FXML private TableView<Product> expiredTable;
	@FXML private TableColumn<Product, String> expiredNameColumn;
	@FXML private TableColumn<Product, Integer> expiredCategoryColumn;
	@FXML private TableColumn<Product, ImageView> expiredIconColumn;
	@FXML private TableColumn<Product, Double> expiredPriceColumn;
	@FXML private TableColumn<Product, LocalDate> expiredBestBeforeColumn;
	@FXML private TableColumn<Product, Button> expiredEditColumn;*/
	
	@FXML
	private void initialize() throws IOException, SQLException {
		controller = App.getController();
		checkCurrentBudget();
		initializeColumns();
		updateFridgeContents();
	}
	
    @FXML
	protected boolean switchToBudget() throws IOException {
        return controller.showBudget();
    }
    
    @FXML
	protected boolean addProduct() throws IOException, SQLException {
    	return controller.showProductWindow(false, null);
    }
    
    public static boolean editProduct(Product product) throws IOException, SQLException {
    	return controller.showProductWindow(true, product);
    }
    
    public boolean checkCurrentBudget() throws IOException, SQLException {
    	if (controller.getBudget(LocalDate.now()) == null) {
    		controller.showBudgetWindow(false, null);
    		return false;
    	}
    	return true;
    }
    
    private void initializeColumns() {
    	fridgeNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		fridgeCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("category"));
		fridgeIconColumn.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("categoryImageView"));
		fridgePriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		fridgeBestBeforeColumn.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("bestBefore"));
		fridgeEditColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("editButton"));
		fridgeExpirationColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("expiration"));
		
		/*expiredNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		expiredCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("category"));
		expiredIconColumn.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("categoryImageView"));
		expiredPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		expiredBestBeforeColumn.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("bestBefore"));
		expiredEditColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("editButton"));*/
    }
    
    public void updateFridgeContents() throws SQLException {
    	List<Product> products = controller.getProducts("fridge");
    	checkBestBefore(products);
    	fridgeTable.setItems(FXCollections.observableArrayList(products));
    }
    
    /*private void updateExpiredContents() throws SQLException {
    	List<Product> list = controller.getProducts("expired");
    	if (list.isEmpty()) {
    		expiredVBox.setVisible(false);
    	} else {
    		expiredVBox.setVisible(true);
    		expiredTable.setItems(FXCollections.observableArrayList(list));
    	}
    }*/
    
    private List<Product> checkBestBefore(List<Product> products) throws SQLException {
    	for (Product product : products) {
    		if (product.getBestBefore().isBefore(LocalDate.now())) {
    			product.setExpiration("expired");
    		} else if (product.getBestBefore().isBefore(LocalDate.now().plusDays(1))) {
    			product.setExpiration("soon to expire");
    		} else {
    			product.setExpiration("good to go");
    		}
    	}
    	return products;
    }
}
