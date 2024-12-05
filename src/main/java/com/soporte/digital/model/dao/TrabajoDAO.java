package com.soporte.digital.model.dao;

import com.soporte.digital.model.dto.*;
import com.soporte.digital.model.entities.*;
import java.sql.*;
import java.util.*;

public class TrabajoDAO {

    private Connection connection;

    public TrabajoDAO(Connection connection) {
        this.connection = connection;
    }

    public String Guardar(TrabajoDTO dto) {
        String result;
        String sql = "{CALL sp_insertar_trabajo(?, ?, ?)}";

        try (PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setString(1, dto.getDescripcion());
            ps.setInt(2, dto.getUsuario().getIdUsuario());
            ps.setInt(3, dto.getIdSolicitud());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getString(1);
            } else {
                result = "No se pudo registrar trabajo.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    public String Terminar(TrabajoDTO dto) {
        String result;
        String sql = "{CALL sp_terminar_trabajo(?, ?, ?)}";

        try (PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setInt(1, dto.getIdTrabajo());
            ps.setInt(2, dto.getUsuario().getIdUsuario());
            ps.setInt(3, dto.getIdSolicitud());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getString(1);
            } else {
                result = "No se pudo terminar trabajo.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    public List<Trabajo> ListarTrabajoPorSolicitud(int idSolicitud) {
        List<Trabajo> lista = new ArrayList<>();

        String sql = "select * from tb_trabajo t "
                + " inner join tb_usuario u ON u.tb_usuario_id=t.tb_usuario_id"
                + " where t.tb_solicitud_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSolicitud);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario objusu = new Usuario();
                    Trabajo objTrab = new Trabajo();
                    objTrab.setIdTrabajo(rs.getInt("tb_trabajo_id"));
                    objTrab.setDescripcion(rs.getString("tb_trabajo_des"));
                    objTrab.setFechaHoraInicio(rs.getString("tb_trabajo_fechorini"));
                    objTrab.setFechaHoraFin(rs.getString("tb_trabajo_fechorter"));
                    objTrab.setIdSolicitud(rs.getInt("tb_solicitud_id"));
                    objusu.setIdUsuario(rs.getInt("tb_usuario_id"));
                    objusu.setApePaterno(rs.getString("tb_usuario_apepat"));
                    objusu.setApeMaterno(rs.getString("tb_usuario_apemat"));
                    objusu.setNombres(rs.getString("tb_usuario_nom"));
                    objusu.setIdUsuario(rs.getInt("tb_usuario_id"));
                    objTrab.setUsuario(objusu);
                    lista.add(objTrab);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
