package group4.householdhero.model;

import java.time.LocalDate;

import javafx.scene.control.Button;

public class Product {
	private int id;
	private String name;
	private double price;
	private LocalDate bestBefore;
	private int categoryId;
	private int budgetId;
	private int statusId;
	
	private Model model;
	private Button editButton;
	
	public Product(int id, String name, double price, LocalDate bestBefore, int categoryId, int budgetId, int statusId, Model model) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.bestBefore = bestBefore;
		this.categoryId = categoryId;
		this.budgetId = budgetId;
		this.statusId = statusId;
		
		this.model = model;
		editButton = new Button("Edit");
		editButton.setOnAction(e -> {
			editProduct();
		});
	}
	
	public void editProduct() {
		model.editProduct(getId());
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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
}
