package com.soporte.digital.model.dao;

import com.soporte.digital.model.entities.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {

    private Connection connection;

    public RolDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Rol> ListarRolesColaboradores() {
        List<Rol> roles = new ArrayList<>();

        String sql = "SELECT * FROM tb_rol "
                + " WHERE tb_rol_id != 6 order by tb_rol_nom ASC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("tb_rol_id"));
                rol.setTipo(rs.getString("tb_rol_nom"));
                roles.add(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

}
