package tn.esprit.utils;


import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.SQLException;

public class DataBase {
    private  final String URL ="jdbc:mysql://127.0.0.1:3306/ass" ;
    private   final String USER ="root" ;
    private  final String PWD ="" ;

    Connection cnx ;
    private static DataSource instance;

    public DataBase() {
        try {
            cnx = DriverManager.getConnection(URL,USER,PWD) ;
            System.out.println("connected to DD ") ;
        } catch (SQLException e) {
            System.out.println(e.getMessage()) ;
        }
    }

    public static DataSource getInstance(){
        if(instance == null)
            instance = new DataSource();
        return instance ;

    }

    public Connection getconn(){ return this.cnx ;}

}
