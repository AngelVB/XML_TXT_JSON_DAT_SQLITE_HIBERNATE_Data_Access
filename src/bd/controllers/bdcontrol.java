/*
 * Clase en la que definimos todos los métodos de manejar las tablas
 */
package bd.controllers;

import bd.models.DBManagerSQLite;
import bd.models.basedatos;
import bd.models.campo;
import bd.models.tabla;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pablo Pardo y Ángel Valera
 */
public class bdcontrol {

    private final String query = "";
    private final DBManagerSQLite bd;
    private String ruta = "";

    /**
     *
     * Constructor encargado de crear una nueva conexión a la base de datos.
     */
    public bdcontrol() {
        bd = new DBManagerSQLite();
    }

    /**
     *Función que conecta con la base de datos y devuelve un listado de tablas.
     * @param Pb Base de datos
     * @return ArrayList(String) Lista de tablas de la base de datos.
     */
    public ArrayList<String> conectar(basedatos Pb) {
        try {
            if (!Pb.nombre.equals("")) {
                ruta = Pb.nombre;
                bd.conectar(ruta);
                ArrayList<String> tablas;
                tablas = listadotablas();
                return tablas;
            } else {
                return null;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("¡ERROR CREANDO BASE DE DATOS!");
            Logger.getLogger(bdcontrol.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     *Función que consulta el nombre de las tablas de una base de datos.
     * @return ArrayList(String) Lista de tablas de la base de datos.
     */
    public ArrayList<String> listadotablas() {
        try {
            ArrayList<String> tablas;
            ResultSet listadotablas = bd.realizarConsulta("SELECT name FROM sqlite_master WHERE type = 'table'");
            tablas = new ArrayList();
            while (listadotablas.next()) //go through each row that your query returns
            {
                String itemCode = listadotablas.getString("name"); //get the element in column "item_code"
                tablas.add(itemCode); //add each item to the model
            }
            System.out.println("Tablas:" + tablas.toString());
            return tablas;
        } catch (SQLException ex) {
            Logger.getLogger(bdcontrol.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

     /**
     * Función que devuelve el listado de campos de una tabla.
     * @param nombreTabla
     * @return ArrayList(campo)
     */
    public ArrayList<campo> listadocampos(String nombreTabla) {
        try {
            ArrayList<campo> campos = new ArrayList();
            ResultSet listadocampos = bd.realizarConsulta("PRAGMA table_info(" + nombreTabla + ");");
            while (listadocampos.next()) //go through each row that your query returns
            {
                String nombre = listadocampos.getString("name");
                String tipo = listadocampos.getString("type"); //get the element in column "item_code"
                int pk = listadocampos.getInt("pk"); //get the element in column "item_code"
                int notnull = listadocampos.getInt("notnull"); //get the element in column "item_code"                    
                campo c = new campo(nombre, tipo, pk, 0, notnull);
                campos.add(c); //add each item to the model
            }
            System.out.println("Campos:" + campos.toString());
            return campos;
        } catch (SQLException ex) {
            Logger.getLogger(bdcontrol.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Función para hacer selects Le paso una tabla, y la palabra clave para
     * buscar y me devuelve un Modelo de tabla para nuestro jTable.
     *
     * @param tabla
     * @param datoSelect, tabla
     * @param pModel
     * @return DefaultTableModel
     * @throws java.sql.SQLException
     */
    public DefaultTableModel select(tabla tabla, String datoSelect, DefaultTableModel pModel) throws SQLException{
       
        DefaultTableModel aModel = (DefaultTableModel) pModel;
        aModel.setColumnCount(0);
         //Obtener los campos de la tabla
        String query="";
        
        for(campo c: tabla.getCampos()){
          query="select * from "+tabla.getNombre()+" where "+c.getNombre()+" like '%"+datoSelect+"%' union "+query;
        }
        System.out.println(query);
        //le quito el ultimo union con un substring
        String s = query.substring(0, query.length() - 7);
        System.out.println(s);
        //llamar a la funcion con ese query
        ResultSet rs = bd.realizarConsulta(s);
        
        java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            int colNo = rsmd.getColumnCount();
            for (int i = 1; i <= colNo; i++) {
                aModel.addColumn(rsmd.getColumnName(i));
            }
            while (rs.next()) {
                Object[] objects = new Object[colNo];
                for (int i = 0; i < colNo; i++) {
                    objects[i] = rs.getObject(i + 1);
                }
                aModel.addRow(objects);
            }
            return aModel; 
    }
    
     /**
     * Método para hacer update a los datos de una tabla.
     *
     * @param tabla
     * @param campos, tabla
     * @param datos
     * @param id
     * @throws java.sql.SQLException
     */
    public void updateDatos(String tabla, ArrayList<campo> campos, ArrayList<String> datos, int id) throws SQLException{
        String query="";
        String listaCampos = "";
        String lc="";
        String valor="";
        for(int i=1;i<campos.size();i++){
            if(1!=campos.get(i).getPrimary()){
                //si es texto, tengo que añadir comilla
                valor=datos.get(i);
                if("TEXT".equals(campos.get(i).getTipo())){
                    valor="'"+valor+"'";
                }
                listaCampos= listaCampos+","+campos.get(i).getNombre()+" = "+valor;
                lc=listaCampos.substring(1, listaCampos.length());
            }            
        }
        query="update "+tabla+" set "+lc + " where id = "+id;
        System.out.println("update "+tabla+" set "+lc + " where id = "+id);
        bd.ejecutarCUD(query);
    }
    
    /**
     * Método para borrar un registro de una tabla.
     *
     * @param tabla
     * @param idBorrar
     * @throws java.sql.SQLException
     */
    public void borraDatos(String tabla, int idBorrar) throws SQLException{
        String query = "";
        query="DELETE FROM "+tabla+" WHERE ID = "+idBorrar;
        System.out.println(query);
        bd.ejecutarCUD(query);
    
    }
    
     /**
     * Método para hacer insert en una tabla.
     *
     * @param tabla
     * @param campos, tabla
     * @param datos
     * @throws java.sql.SQLException
     */
    public void insertFila(String tabla, ArrayList<campo> campos, ArrayList<String> datos) throws SQLException{  
        String query="";
        String listaCampos = "";
        for(int i=1;i<campos.size();i++){
            listaCampos= listaCampos+","+campos.get(i).getNombre();
        }
        String listaValores="";        
        //Recorrer listas y montar string
        for(int i=1;i<campos.size();i++){
            //empiezo a comprobar: 
            //si es texto, tengo que añadir comilla
            if("TEXT".equals(campos.get(i).getTipo())){
                listaValores=listaValores+"'"+datos.get(i)+"',";
            }
            else{
                //es numerico, no pongo comilla
                listaValores=listaValores+datos.get(i)+",";
            }           
        }
        System.out.println("valores: "+listaValores);
        //lista de campos sin la primera coma y con parentesis, para montar el string
        String listaCamposFinal="("+listaCampos.substring(1)+")";
        String listaValoresFinal="("+listaValores.substring(0,listaValores.length()-1)+");";
        query="INSERT INTO "+tabla+" "+listaCamposFinal+" VALUES "+listaValoresFinal;
        //query=query.substring(query.length() - 1);
        System.out.println(query);
        bd.ejecutarCUD(query);
    }
    
    /**
     * Método para renombrar un una tabla.
     *
     * @param nombreTabla
     * @param nuevonombre
     * @return String
     */
    public String renameTabla(String nombreTabla,String nuevonombre) {
        try {
            String Tablatemp = nuevonombre;
            String sql = "ALTER TABLE " + nombreTabla + " RENAME TO " + Tablatemp + ";";
            System.out.println(sql);
            bd.ejecutarCUD(sql);
            return Tablatemp;
        } catch (SQLException ex) {
            Logger.getLogger(bdcontrol.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Función que devuelve todos los datos de una tabla y devuelve un Modelo de tabla para nuestro jTable.
     *
     * @param tabla
     * @param pModel
     * @return DefaultTableModel
     */
    public DefaultTableModel datostabla(String tabla, DefaultTableModel pModel) {
        try {
            DefaultTableModel aModel = (DefaultTableModel) pModel;
            aModel.setColumnCount(0);
            ResultSet datos = bd.realizarConsulta("SELECT * FROM " + tabla);
            java.sql.ResultSetMetaData rsmd = datos.getMetaData();
            int colNo = rsmd.getColumnCount();
            for (int i = 1; i <= colNo; i++) {
                aModel.addColumn(rsmd.getColumnName(i));
            }
            while (datos.next()) {
                Object[] objects = new Object[colNo];
                // tanks to umit ozkan for the bug fix!
                for (int i = 0; i < colNo; i++) {
                    objects[i] = datos.getObject(i + 1);
                }
                aModel.addRow(objects);
            }
            return aModel;
        } catch (SQLException ex) {
            Logger.getLogger(bdcontrol.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Método para insertar los datos de la tabla vieja en una tabla modificada USANDO INSERT SELECT (MODIFICAR).
     *
     * @param tablaVieja
     * @param tablaNueva
     * @throws java.sql.SQLException
     */    
    public void insertSelect(tabla tablaVieja, tabla tablaNueva) throws SQLException {
        //recupero datos de la tabla vieja
        ResultSet datos = bd.realizarConsulta("select * from " + tablaVieja.getNombre() + ";");
        if (datos.next()) {
            //Preparo el principio de la sentencia.
            String sql = "INSERT into " + tablaNueva.getNombre() + " (";
            String campos = "";
            //Meto los campos de la tabla nueva en el string campos
            List<campo> camposnuevos = tablaNueva.getCampos();
            int i = 1;
            for (campo c : camposnuevos) {
                campos = campos + c.getNombre();
                if (camposnuevos.size() != i) {
                    campos = campos + ",";
                    //campos = campos + ") VALUES (";
                } else {
                    campos = campos + ")";
                }
                i++;
            }
            System.out.println("Numero:" + i);

            campos = campos + " VALUES ";
            String valores = "";
            //meto los valores de la tabla antigua en valores.
            ResultSetMetaData rsmd = datos.getMetaData();
            int nCol = rsmd.getColumnCount();
            //Con este while recorro las filas del resultset
            System.out.println("Num Cols tabla antigua: "+nCol);
            do {
                //Con el for recorro las columnas
                for (int j = 0; j < i - 1; j++) {
                    if (j == 0) {
                        valores = valores + "(";
                    } else {
                        valores = valores + ",";
                    }
                    //Saco el campo de la tabla nueva que corresponde al campo que estoy recorriendo en este momento
                    campo c = camposnuevos.get(j);
                    //Con este if compruebo si la tabla vieja no tiene datos y el campo es no nulo, si es así meto un 0, si no, copio el valor de la tabla antigua.
                    if (j >= nCol) {
                         System.out.println("Hola:");
                        valores = valores + "0";
                    } else {
                        if ((datos.getString(j + 1) == null) && (c.getNotNull() == 1)) {
                            valores = valores + "0";
                        } else {
                            valores = valores + "'"+datos.getString(j + 1)+"'";
                        }
                    }
                }
                valores = valores + "),";
            } while (datos.next());
            datos.close();
            //borro la última coma
            valores = valores.substring(0, valores.length() - 1);
            //monto la sentencia entera.
            sql = sql + campos + valores + ";";
            System.out.println(sql);
            bd.ejecutarCUD(sql);
        }
    }

    /**
     * Método para eliminar una tabla.
     *
     * @param tabla
     * @throws java.sql.SQLException
     */  
    public void DropTabla(String tabla) throws SQLException {
        bd.ejecutarCUD("Drop table " + tabla);
    }

    /**
     * Método para crear una tabla.
     *
     * @param Ptabla
     */ 
    public ArrayList<String> nuevatabla(tabla Ptabla) {
        if (!Ptabla.nombre.equals("")) {
            try {
                bd.ejecutarCUD(Ptabla.crearTabla());
                ArrayList<String> tablas;
                tablas = listadotablas();
                return tablas;
            } catch (SQLException ex) {
                System.out.println("¡ERROR CREANDO TABLA!");
                Logger.getLogger(bdcontrol.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }       
        }
        return null;
    }

    /**
     * Método para modificar una tabla.
     *
     * @param Ptabla
     */
    public void modificarTabla(tabla Ptabla) {
        if (!Ptabla.nombre.equals("")) {
            try {
                bd.ejecutarCUD(Ptabla.crearTabla());
            } catch (SQLException ex) {
                System.out.println("¡ERROR CREANDO TABLA!");
                Logger.getLogger(bdcontrol.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}