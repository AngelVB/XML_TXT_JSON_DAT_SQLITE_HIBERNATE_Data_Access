/*
 * Clase que almacena los métodos de CUD y consultas para gestionar la base de datos SQLite.
 */
package bd.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Pablo Pardo y Ángel Valera
 */
public class DBManagerSQLite {

    private Connection conn;

    /**
     * Método para abrir la base de datos
     * @param Ruta Nombre de la tabla
     * 
     */
    public void conectar(String Ruta) throws ClassNotFoundException, SQLException {
        

        Class.forName("org.sqlite.JDBC");

        
        conn = DriverManager.getConnection("jdbc:sqlite:" + Ruta);
    }

    /**
     * Método para cerrar la conexión
     * 
     */
    public void cerrar() throws SQLException {
        conn.close();
    }

    /**
     * Función para ejecutar INSERT, UPDATE y DROP en la base de datos.
     * @param sql Sentencia sql 
     * @return boolean
     * 
     */
    public boolean ejecutarCUD(String sql) throws SQLException {
        Statement st = conn.createStatement();
        int res = st.executeUpdate(sql);
        st.close();
        return res == 1;
    }

    /**
     * Función para ejecutar SELECT en la base de datos.
     * @param sql Sentencia sql 
     * @return ResultSet
     * 
     * 
     */
    public ResultSet realizarConsulta(String sql) throws SQLException {

        ResultSet rs;
        Statement st = conn.createStatement();
        rs = st.executeQuery(sql);
        System.out.println("Query:" + sql);
        return rs;
    }
}
