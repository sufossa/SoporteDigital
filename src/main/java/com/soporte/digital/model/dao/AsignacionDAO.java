package com.soporte.digital.model.dao;

import com.soporte.digital.model.dto.AsignacionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AsignacionDAO {

    private Connection connection;

    public AsignacionDAO(Connection connection) {
        this.connection = connection;
    }

    public String Guardar(AsignacionDTO dto) {
        String result;
        String sql = "{CALL sp_insertar_asignacion(?, ?, ?)}";

        try (PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setInt(1, dto.getIdSolicitud());
            ps.setInt(2, dto.getIdUsuario());
            ps.setBoolean(3, dto.isEsCoordinador());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getString(1);
            } else {
                result = "No se pudo asignar la solicitud al colaborador.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }
    
     public String AsignarCoordinador(AsignacionDTO dto) {
        String result;
        String sql = "{CALL sp_asignar_coordinador(?, ?, ?)}";

        try (PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setInt(1, dto.getIdSolicitud());
            ps.setInt(2, dto.getIdUsuario());
            ps.setBoolean(3, dto.isEsCoordinador());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getString(1);
            } else {
                result = "No se pudo asignar coordinador a la solicitud.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }
}
