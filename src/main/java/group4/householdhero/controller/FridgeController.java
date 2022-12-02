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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FridgeController {
	protected static IController controller;
	private Product product;
	
	@FXML private VBox expiredVBox;
	
	@FXML private ChoiceBox<String> languageChoiceBox;
	@FXML private Button putToUsedButton;
	@FXML private Button putToWasteButton;
	
	@FXML private TableView<Product> fridgeTable;
	@FXML private TableColumn<Product, String> fridgeNameColumn;
	@FXML private TableColumn<Product, Integer> fridgeCategoryColumn;
	@FXML private TableColumn<Product, ImageView> fridgeIconColumn;
	@FXML private TableColumn<Product, Double> fridgePriceColumn;
	@FXML private TableColumn<Product, LocalDate> fridgeBestBeforeColumn;
	//@FXML private TableColumn<Product, String> fridgeExpirationColumn; // expiration status as String
	@FXML private TableColumn<Product, ImageView> fridgeExpirationColumn; // expiration status as ImageView
	@FXML private TableColumn<Product, Button> fridgeEditColumn;
	@FXML private TableColumn<Product, Button> fridgePutToUsedColumn;
	@FXML private TableColumn<Product, Button> fridgePutToWasteColumn;
	
	@FXML
	private void initialize() throws IOException, SQLException {
		controller = App.getController();
		checkCurrentBudget();
		
		setLanguageChoiceBox();
		initializeColumns();
		updateFridgeContents();
	}
	
	private void setLanguageChoiceBox() {
		languageChoiceBox.getItems().add(App.bundle.getString("english.choice.text"));
		languageChoiceBox.getItems().add(App.bundle.getString("gaeilge.choice.text"));
	}
	
	@FXML
	private void changeSelectedLanguage() throws IOException {
		if (languageChoiceBox.getValue() == App.bundle.getString("english.choice.text")) {
			App.setLocaleEnglish();
		} if (languageChoiceBox.getValue() == App.bundle.getString("gaeilge.choice.text")) {
			App.setLocaleGaeilge();
		}
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
    
    @FXML
	static void putToUsed(Product product) throws IOException, SQLException {
		controller.changeProductStatus(product, "used");
		App.showFridge();
	}
	
    @FXML
	static void putToWaste(Product product) throws IOException, SQLException {
		controller.changeProductStatus(product, "waste");
		App.showFridge();
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
		//fridgeExpirationColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("expiration")); // expiration status as String
		fridgeExpirationColumn.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("statusImageView")); // expiration status as ImageView
		fridgeEditColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("editButton"));
		fridgePutToUsedColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("putToUsedButton"));
		fridgePutToWasteColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("putToWasteButton"));
    }
    
    public void updateFridgeContents() throws SQLException {
    	List<Product> products = controller.getProducts("fridge");
    	checkBestBefore(products);
    	fridgeTable.setItems(FXCollections.observableArrayList(products));
    }
    
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
