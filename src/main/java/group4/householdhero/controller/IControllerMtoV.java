package group4.householdhero.controller;

import java.io.IOException;
import java.sql.SQLException;

import group4.householdhero.model.Product;

public interface IControllerMtoV {
	public void editProduct(Product product) throws IOException, SQLException;
}
