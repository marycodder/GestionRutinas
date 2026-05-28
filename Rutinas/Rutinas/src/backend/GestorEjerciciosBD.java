package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GestorEjerciciosBD {
    private static final String RUTA_BD = "jdbc:sqlite:data/ejercicios.db";

    public void crearTabla() throws Exception {
        Connection conexion = DriverManager.getConnection(RUTA_BD);

        String sql = "CREATE TABLE IF NOT EXISTS ejercicios (" +
                "codigo INTEGER PRIMARY KEY, " +
                "nombre TEXT NOT NULL, " +
                "tipo TEXT NOT NULL, " +
                "intensidad TEXT NOT NULL, " +
                "tiempo INTEGER NOT NULL, " +
                "descripcion TEXT NOT NULL, " +
                "ultima_semana_usado INTEGER NOT NULL" +
                ")";

        Statement stmt = conexion.createStatement();
        stmt.execute(sql);

        stmt.close();
        conexion.close();
    }

    public void eliminarDatos() throws Exception {
        Connection conexion = DriverManager.getConnection(RUTA_BD);

        String sql = "DELETE FROM ejercicios";

        Statement stmt = conexion.createStatement();
        stmt.executeUpdate(sql);

        stmt.close();
        conexion.close();
    }

    public void insertarEjercicio(Ejercicio ejercicio) throws Exception {
        Connection conexion = DriverManager.getConnection(RUTA_BD);

        String sql = "INSERT INTO ejercicios " +
                "(codigo, nombre, tipo, intensidad, tiempo, descripcion, ultima_semana_usado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = conexion.prepareStatement(sql);

        ps.setInt(1, ejercicio.getCodigo());
        ps.setString(2, ejercicio.getNombre());
        ps.setString(3, ejercicio.getTipo());
        ps.setString(4, ejercicio.getIntensidad());
        ps.setInt(5, ejercicio.getTiempo());
        ps.setString(6, ejercicio.getDescripcion());
        ps.setInt(7, ejercicio.getUltimaSemanaUsado());

        ps.executeUpdate();

        ps.close();
        conexion.close();
    }

    public ArrayList<Ejercicio> cargarEjercicios() throws Exception {
        ArrayList<Ejercicio> ejercicios = new ArrayList<>();

        Connection conexion = DriverManager.getConnection(RUTA_BD);

        String sql = "SELECT * FROM ejercicios";

        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int codigo = rs.getInt("codigo");
            String nombre = rs.getString("nombre");
            String tipo = rs.getString("tipo");
            String intensidad = rs.getString("intensidad");
            int tiempo = rs.getInt("tiempo");
            String descripcion = rs.getString("descripcion");
            int ultimaSemana = rs.getInt("ultima_semana_usado");

            Ejercicio ejercicio = new Ejercicio(
                    codigo,
                    nombre,
                    tipo,
                    intensidad,
                    tiempo,
                    descripcion,
                    ultimaSemana
            );

            ejercicios.add(ejercicio);
        }

        rs.close();
        stmt.close();
        conexion.close();

        return ejercicios;
    }
}