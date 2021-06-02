package conexion;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conector {
    static String url = "jdbc:sqlite:C:\\Users\\seryi\\nuevo.db";
    static Connection connect;

    public static boolean conectar(){
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connect = DriverManager.getConnection(url);
            if (connect!=null) {
            }
        }catch (SQLException ex) {
            System.err.println("No se ha podido conectar a la base de datos\n"+ex.getMessage());
        }
        return connect!=null;
    }
    public static void cerrar(){
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int sentenciaSql(String sSql){


        try {
            PreparedStatement st= connect.prepareStatement(sSql);
            st.execute();
            return 1;


        }catch (SQLException e){
            System.out.println("Error en sentenciaSql " + e);
            return 0;
        }
    }
    public static ResultSet consulta(String sSql){

        try {
            PreparedStatement st= connect.prepareStatement(sSql);
            ResultSet resultado=st.executeQuery();
            return resultado;

        }catch (SQLException e){
            System.out.println("Error consulta" + e);
            return null;
        }
    }
}
