package sql;
/** @author alfreding0*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
        this.conectar(); 
    }
    
    /**
     * Hace la conexion con el servidor de bases de datos, con las credenciales de <br>
     * usuario y contraseña; para que a partir de esa conexion o sesion lograda <br>
     * se pueda usar para ejecutar comando y consultas a la base de datos.
     */
    public void conectar(){
        try {
            Class.forName("org.postgresql.Driver");
            cnx= DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Test conexion successfull!!!");
        }catch (ClassNotFoundException | SQLException ex) { 
            JOptionPane.showMessageDialog(null, "Error en la conexion!\n" + ex.getMessage());
        }
    }
    
    /**
     * Siempre que se abra una conexion o sesión con el servidor de bases de datos <br>
     * es muy importante desconectarse de la misma después de hacer las operaciones
     * de inserts, updates, deletes o selects.
     * Hacer la desconexion optimiza todo el sistema porque libera una sesión en el
     * servidor.
     */
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
     * @param comando recibe un String que es el script o comando sql que se desea ejecutar.
     * <br>
     * @return retorna un booleano según lo que ocurra con la ejecución del comando. <br>
     * Cuando la ejecución es exitosa retorna true.
     * Si la ejecución falla retorna false.
     * Esto puede ayudar a mostrar un mensaje al usuario para darle a conocer la situación.
     */
    public boolean ejecutarComando(String comando) {
        try {
            Statement Cmd = cnx.createStatement();
            Cmd.execute(comando);
            return true;
        }catch (SQLException err) {
            System.out.println("Error al intentar ejecutar comando: " + err);
            return false;
        }
    }
    
    
    
    
    
    /**Ejecuta instruccion DML, del cual se espera una respuesta con datos, ejm: <br>
     * <br>
     * SELECT campo1, campo2 FROM mitabla;<br>
     * <br>
     * Todas estas instrucciones deben enviarse como una cadena String.<br>
     * El resultado de retorno es un objeto ResultSet.<br>
     * @param consulta recibe un String que es el script o consulta sql que se desea ejecutar.
     * @return el retorno de esta función es un objeto ResultSet que es en donde se almacenan los datos de la consulta.
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
    
    
    /**
     * Muestra una consulta cualquiera por la Salida o consola, sin necesidad <br>
     * de mostrarlo en una tabla.<br>
     * @param resultQuery recibe el resultado de la consulta que normalmente se lo guarda en un objeto de tipo ResultSet al hacer la consulta.
     */
    public void mostrarDatosPorConsola(ResultSet resultQuery){
        try {
            ResultSetMetaData md = resultQuery.getMetaData();
            int n_columns = md.getColumnCount();
            
            for (int i = 1; i <= n_columns; i++) {
                System.out.print( md.getColumnName(i) + "\t" );
            }
            System.out.println("\n-------------------------------------------------------");
            
            while(resultQuery.next()){
                for (int i = 1; i <= n_columns; i++) {
                    System.out.print( resultQuery.getObject(i) + " " );
                }
                System.out.println("");
            }
        } catch (SQLException ex) {
            System.out.println("Error en mostrar datos!!\n" + ex.getMessage());
        }
    }
}
/** @author alfreding0*/