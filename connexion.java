
package javafxapplication2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mega-PC
 */
public class connexion {
 
    static String user = "root";
    static String url = "jdbc:mysql://localhost:3306/users";
    static String pass = "";
    static String driver = "com.mysql.jdbc.Driver";
 
    public static Connection getCon() {
       java.sql.Connection con = null;
 
        try {
            Class.forName(driver);
           con = DriverManager.getConnection(
                                url,user, pass);
            System.out.println("Ok");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(connexion.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return con;
 
    }
 
}