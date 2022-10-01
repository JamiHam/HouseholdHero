package group4.householdhero.model;

import java.sql.SQLException;

public class Testmain {

    static Model model;
    static DataAccessObject dao;

    public Testmain(Model model) {
        this.model = model;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    model = new Model();
    dao = new DataAccessObject(model);

        try {
            dao.getProductsInFridge();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e);
        }
    }
}
