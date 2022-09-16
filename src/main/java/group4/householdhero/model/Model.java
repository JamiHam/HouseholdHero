package group4.householdhero.model;

import group4.householdhero.controller.*;

public class Model {
	
	private Controller controller;
	private String testString = "Test"; //MVC testing
	
	public Model(Controller controller) {
		this.controller = controller;
	}
	
	//MVC testing
	public String getTestString() {
		return testString;
	}
}
