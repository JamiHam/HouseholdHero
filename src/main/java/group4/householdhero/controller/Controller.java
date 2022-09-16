package group4.householdhero.controller;

import group4.householdhero.view.*;
import group4.householdhero.model.*;

public class Controller {
	private Model model;
	private View view;
	
	public Controller(View view) {
		this.view = view;
		this.model = new Model(this);
	}
	
	// MVC testing
	public String getTestString() {
		return model.getTestString();
	}
}
