/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bd.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sandra
 */
public class DBManagerMySQL {
    
    private Connection conn;
    
    public void conectar() throws ClassNotFoundException, SQLException
    {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("com.mysql.jdbc.Driver");
        
        // create a database connection
         conn= DriverManager.getConnection ( "jdbc:mysql://localhost:8889/AgenciaViajes?" + "user=root&password=root");
    }
    
    public void cerrar() throws SQLException
    {
        conn.close();
    }
    
    public boolean ejecutarCUD(String sql) throws SQLException
    {
        Statement st = conn.createStatement();
        int res = st.executeUpdate(sql);
        st.close();
        return res==1;
    }
    
    public ResultSet realizarConsulta(String sql) throws SQLException
    {
       
        ResultSet rs;
        try (Statement st = conn.createStatement()) {
            rs = st.executeQuery(sql);
        }
        
        return rs;
    }
        
}
