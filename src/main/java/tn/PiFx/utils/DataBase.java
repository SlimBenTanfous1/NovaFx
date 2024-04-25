package tn.PiFx.utils;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    private final String URL ="jdbc:mysql://localhost:3306/NovaAssurancefx";
    private final String USERNAME ="root";
    private final String PWD ="";
    private Connection conx;


    public static DataBase instance;
    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    private DataBase(){
        try {
            conx = DriverManager.getConnection(URL,USERNAME,PWD);
            System.out.println("Connexion établie!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConx() {
        return conx;
    }
}
