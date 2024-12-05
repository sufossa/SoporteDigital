package com.soporte.digital.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static ConexionDB instancia;
    private Connection connection;

    private ConexionDB() {
        try {
            String url = "jdbc:postgresql://localhost:5432/bdSoporteDigital";
            String usuario = "postgres";
            String contrasena = "231003";

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, usuario, contrasena);

            System.out.println("Conexion establecida!");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ConexionDB getInstance() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }
    public Connection getConnection() {
        return connection;
    }
}
