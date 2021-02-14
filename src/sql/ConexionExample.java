package sql;
/** @author alfreding0*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public final class ConexionExample{
    
    private final String DATABASE   =   "tubasededatos";
    private final String HOST       =   "localhost";
    private final String PORT       =   "5432";
    private final String MOTOR      =   "postgresql";
    private final String USER       =   "tuusuario";
    private final String PASS       =   "tucontraseña";
    
    
    
    
    private Connection cnx          =   null;
    private final String URL        =   "jdbc:"+MOTOR+"://"+HOST+":"+PORT+"/"+DATABASE;
    
    
    
    
    
    public ConexionExample(){
        conectar(); 
    }
    
    
    public void conectar(){
        try {
            Class.forName("org.postgresql.Driver");
            cnx= DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Test conexion successfull!!!");
        }catch (ClassNotFoundException | SQLException ex) { 
            JOptionPane.showMessageDialog(null, "Error en la conexion!\n" + ex.getMessage());
        }
    }
    
    public void desconectar(){
        if(getConnection()!=null){
            try {
                cnx.close();
            } catch (SQLException ex) {
                System.out.println("Problemas al desconectar !!!\n" + ex.getMessage());
            }
        }
    }
    
    public Connection getConnection(){
        return cnx; 
    }
    
    
    
    
    
    /**Ejecuta instrucciones DML, los cuales no retornan datos, ejms: <br>
     * <br>
     * INSERT INTO mitabla (campo1, campo2) VALUES (3424, 'datocampo2'); <br>
     * UPDATE mitabla SET campo1=98345, campo2='datomodificado' WHERE id=53; <br>
     * DELETE FROM mitabla WHERE id=53; <br>
     * <br>
     * También soporta ejecución de instrucciones DDL. <br>
     * <br>
     * Todas estas instrucciones deben enviarse como una cadena String. <br>
     * @param comando
     */
    public void ejecutarComando(String comando) {
        try {
            Statement Cmd = cnx.createStatement();
            Cmd.execute(comando);
        }catch (SQLException err) {
            System.out.println("Error al intentar ejecutar comando: " + err);
        }
    }
    
    
    
    
    
    /**Ejecuta instruccion DML, del cual se espera una respuesta con datos, ejm: <br>
     * <br>
     * SELECT campo1, campo2 FROM mitabla;<br>
     * <br>
     * Todas estas instrucciones deben enviarse como una cadena String.<br>
     * El resultado de retorno es un objeto ResultSet.<br>
     * @param consulta
     * @return 
     */
    public ResultSet ejecutarConsulta(String consulta){
        ResultSet res = null;
        try {
            Statement stmt = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            res = stmt.executeQuery(consulta);  
        }catch (SQLException err) {
            System.out.println("Error al intentar ejecutar consulta: " + err);
        }
        return res;
    }
    
    
    
    
}
/** @author alfreding0*/