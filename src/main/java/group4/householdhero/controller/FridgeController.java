package group4.householdhero.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import group4.householdhero.model.Product;
import group4.householdhero.view.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
	/**
	 * Sets languages to choice box
	 */
	private void setLanguageChoiceBox() {
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
	 * Switches to budget-view
	 * @return
	 * @throws IOException
	 */
    @FXML
	protected boolean switchToBudget() throws IOException {
        return controller.showBudget();
    }
    
    /**
     * Opens product window in adding mode
     * @return
     * @throws IOException
     * @throws SQLException
     */
    @FXML
	protected boolean addProduct() throws IOException, SQLException {
    	return controller.showProductWindow(false, null);
    }
    
    /**
     * Opens product window in editing mode
     * @param product
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public static boolean editProduct(Product product) throws IOException, SQLException {
    	return controller.showProductWindow(true, product);
    }
    
    /**
     * Changes product status to used
     * @param product
     * @throws IOException
     * @throws SQLException
     */
    @FXML
	static void putToUsed(Product product) throws IOException, SQLException {
		controller.changeProductStatus(product, "used");
		App.showFridge();
	}
	
    /**
     * Changes product status to waste
     * @param product
     * @throws IOException
     * @throws SQLException
     */
    @FXML
	static void putToWaste(Product product) throws IOException, SQLException {
		controller.changeProductStatus(product, "waste");
		App.showFridge();
	}
    
    /**
     * Checks if there is an active budget
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public boolean checkCurrentBudget() throws IOException, SQLException {
    	if (controller.getBudget(LocalDate.now()) == null) {
    		controller.showBudgetWindow(false, null);
    		return false;
    	}
    	return true;
    }
    
    /**
     * Initializes table columns
     */
    private void initializeColumns() {
    	fridgeNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		fridgeCategoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("localizedCategory"));
		fridgeIconColumn.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("categoryImageView"));
		fridgePriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
		fridgeBestBeforeColumn.setCellValueFactory(new PropertyValueFactory<Product, LocalDate>("bestBefore"));
		//fridgeExpirationColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("expiration")); // expiration status as String
		fridgeExpirationColumn.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("statusImageView")); // expiration status as ImageView
		fridgeEditColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("editButton"));
		fridgePutToUsedColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("putToUsedButton"));
		fridgePutToWasteColumn.setCellValueFactory(new PropertyValueFactory<Product, Button>("putToWasteButton"));
    }
    
    /**
     * Gets all products in fridge from database and adds them to the table
     * @throws SQLException
     */
    public void updateFridgeContents() throws SQLException {
    	List<Product> products = controller.getProducts("fridge");
    	checkBestBefore(products);
    	controller.localize(products);
    	fridgeTable.setItems(FXCollections.observableArrayList(products));
    }
    
    /**
     * Checks product best before date and changes their expiration status accordingly
     * @param products
     * @return
     * @throws SQLException
     */
    private List<Product> checkBestBefore(List<Product> products) throws SQLException {
    	for (Product product : products) {
    		if (product.getBestBefore().isBefore(LocalDate.now())) {
    			product.setExpiration("expired");
    		} else if (product.getBestBefore().isBefore(LocalDate.now().plusDays(2))) {
    			product.setExpiration("soon to expire");
    		} else {
    			product.setExpiration("good to go");
    		}
    	}
    	return products;
    }
}
