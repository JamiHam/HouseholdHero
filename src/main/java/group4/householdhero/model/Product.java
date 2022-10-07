package group4.householdhero.model;

import java.io.IOException;
import java.time.LocalDate;

import javafx.scene.control.Button;

public class Product {
	private int id;
	private String name;
	private double price;
	private LocalDate bestBefore;
	private String category;
	private int budgetId;
	private int statusId;
	
	private Model model;
	private Button editButton;
	private ImageView categoryImageView;
	
	public Product(int id, String name, double price, LocalDate bestBefore, String category, int budgetId, int statusId, Model model) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.bestBefore = bestBefore;
		this.category = category;
		this.budgetId = budgetId;
		this.statusId = statusId;
		
		this.model = model;
		
		setUpButton();
		setUpImageView();
		
		// Pohjaa kategoriaikonin asettamiselle
		/*
		categoryButton = new Button("");
		categoryButton.setOnAction(e -> {
			try {
				// Tähän täytyy hakea tuotteen kategorian ID
				int categoryId = 1;
				
				for (int i = 1; i <= 23; i++) {
					if (categoryId == i) {
						
						image = new Image(Product.class.getResourceAsStream("resources/category-icon-" + i + ".png"),
								40, 40, true, true);

						imageView.setImage(image);
					}
				}
		editButton = new Button("");
		editButton.getStyleClass().add("product-edit-button");
		editButton.setOnAction(e -> {
			try {
				editProduct();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		*/
	}
	
	public void editProduct() throws IOException {
		model.editProduct(this);
	}
	
	private void setUpButton() {
		editButton = new Button("");
		editButton.getStyleClass().add("product-edit-button");
		editButton.setOnAction(e -> {
			try {
				editProduct();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
	
	private void setUpImageView() {
		categoryImageView = new ImageView();
		String className = category.replace(" ", "-");
		categoryImageView.getStyleClass().add(className);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDate getBestBefore() {
		return bestBefore;
	}

	public void setBestBefore(LocalDate bestBefore) {
		this.bestBefore = bestBefore;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	public Button getEditButton() {
		return editButton;
	}
	
	public ImageView getCategoryImageView() {
		return categoryImageView;
	}
}
