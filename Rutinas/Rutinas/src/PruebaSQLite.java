import java.sql.Connection;
import java.sql.DriverManager;

public class PruebaSQLite {
    public static void main(String[] args) {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:sqlite:data/ejercicios.db");
            System.out.println("Conexión exitosa a SQLite");
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }
}