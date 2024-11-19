package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BaseDeDatos {
    private Connection conexion;

    // Conectar a la base de datos
    public void conectar() throws Exception {
        String url = "jdbc:mysql://localhost:3306/orquideas";
        String usuario = "root";
        String contraseña = "123456";
        conexion = DriverManager.getConnection(url, usuario, contraseña);
        System.out.println("Conexión exitosa a la base de datos.");
    }

    // Guardar datos de una orquídea
    public void guardarOrquidea(String nombre, double humedad, double temperatura) throws Exception {
        String query = "INSERT INTO orquidea (nombre, humedad, temperatura) VALUES (?, ?, ?)";
        PreparedStatement stmt = conexion.prepareStatement(query);
        stmt.setString(1, nombre);
        stmt.setDouble(2, humedad);
        stmt.setDouble(3, temperatura);
        stmt.executeUpdate();
        System.out.println("Datos guardados correctamente.");
    }

    // Obtener registros de la tabla orquidea
    public ResultSet obtenerRegistros() throws Exception {
        String query = "SELECT * FROM orquidea";
        PreparedStatement stmt = conexion.prepareStatement(query);
        return stmt.executeQuery();
    }
}
