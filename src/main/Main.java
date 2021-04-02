package main;

import java.sql.ResultSet;
import sql.Conexion;

/** @author alfreding0 */
public class Main {
    public static void main(String[] args) {
        Conexion con = new Conexion();
        
        String comando = "INSERT INTO usuario (nombre, clave) VALUES ('alf', 'XD')";
        con.ejecutarComando(comando);
//        String comando = "UPDATE usuario SET nombre = 'alf-01' WHERE id = 1; ";
//        con.ejecutarComando(comando);
//        String comando = "DELETE FROM usuario WHERE id = 2; ";
//        con.ejecutarComando(comando);

        String consulta = "SELECT * FROM usuario ORDER BY id DESC";
        ResultSet rs =  con.ejecutarConsulta(consulta);
        con.mostrarDatosPorConsola(rs);
    }

}
/** @author alfreding0 */