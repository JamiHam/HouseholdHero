package group4.householdhero.model;

import java.sql.SQLException;

public class testmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataAccessObject dao = new DataAccessObject();
		
		try {
			dao.getProductsInFridge();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);;
		}
	}

}
