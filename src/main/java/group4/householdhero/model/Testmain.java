package group4.householdhero.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

public class Testmain extends Application{

    public static void main(String[] args) throws SQLException {
        Model model = new Model();
        DataAccessObject dao = new DataAccessObject(model);
        

        //Product product = model.createProduct(0, "maito", 0.99, LocalDate.parse("2022-10-22"), "maitotuote", 1, 1);
        try {
        	dao.checkBestBefore();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub

    }
}