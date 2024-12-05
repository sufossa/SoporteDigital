package com.soporte.digital.model.dao;

import com.soporte.digital.model.dto.UsuarioDTO;
import java.sql.*;

public class AuthDAO {

    private Connection connection;

    public AuthDAO(Connection connection) {
        this.connection = connection;
    }

    public UsuarioDTO Login(String correo, String password) {
        UsuarioDTO usuario = null;

        try {
            String sql = "SELECT * FROM SP_Auth_Login(?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new UsuarioDTO();
                usuario.setIdRol(rs.getInt("idRol"));
                usuario.setNombreRol(rs.getString("nombreRol"));
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setPassword(rs.getString("password"));
                usuario.setEstado(rs.getBoolean("estado") ? 1 : 0);
                usuario.setId(rs.getInt("id"));
                usuario.setNombres(rs.getString("nombre"));
                usuario.setTipoUsuario(rs.getString("tipoUsuario"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

}
