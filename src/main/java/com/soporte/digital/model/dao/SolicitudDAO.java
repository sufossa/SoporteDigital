package com.soporte.digital.model.dao;

import com.soporte.digital.model.dto.SolicitudDTO;
import com.soporte.digital.model.entities.Solicitud;
import com.soporte.digital.model.entities.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDAO {

    private Connection connection;

    public SolicitudDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Solicitud> ListarMisSolicitudesExcluyente(int idUsuario, String estado) {
        List<Solicitud> lista = new ArrayList<>();

        String sql = "SELECT * FROM tb_solicitud s"
                + " INNER JOIN tb_usuario t on t.tb_usuario_id = s.tb_usuario_id"
                + " WHERE t.tb_usuario_id =? AND tb_solicitud_est != ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setString(2, estado);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    Solicitud solicitud = new Solicitud();
                    solicitud.setIdSolicitud(rs.getInt("tb_solicitud_id"));
                    solicitud.setAnnio(rs.getInt("tb_solicitud_ani"));
                    solicitud.setMes(rs.getInt("tb_solicitud_mes"));
                    solicitud.setNumeroCorrelativo(rs.getInt("tb_solicitud_numcor"));
                    solicitud.setFechaHoraRegistro(rs.getString("tb_solicitud_fechor"));
                    solicitud.setFechaHoraAsignacion(rs.getString("tb_solicitud_fechorasi"));
                    solicitud.setFechaHoraInicioAtencion(rs.getString("tb_solicitud_fechoriniate"));
                    solicitud.setFechaHoraTerminoAtencion(rs.getString("tb_solicitud_fechorterate"));
                    solicitud.setMotivo(rs.getString("tb_solicitud_mot"));
                    solicitud.setEstado(rs.getString("tb_solicitud_est"));
                    solicitud.setTipoSolicitud(rs.getString("tb_solicitud_tip"));

                    usuario.setApePaterno(rs.getString("tb_usuario_apepat"));
                    usuario.setApeMaterno(rs.getString("tb_usuario_apemat"));
                    usuario.setNombres(rs.getString("tb_usuario_nom"));

                    solicitud.setUsuario(usuario);

                    lista.add(solicitud);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Solicitud> ListarMisSolicitudes(int idUsuario, String estado) {
        List<Solicitud> lista = new ArrayList<>();

        String sql = "SELECT * FROM tb_solicitud s"
                + " INNER JOIN tb_usuario t on t.tb_usuario_id = s.tb_usuario_id "
                + " WHERE s.tb_usuario_id = ? AND tb_solicitud_est = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setString(2, estado);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    Solicitud solicitud = new Solicitud();
                    solicitud.setIdSolicitud(rs.getInt("tb_solicitud_id"));
                    solicitud.setAnnio(rs.getInt("tb_solicitud_ani"));
                    solicitud.setMes(rs.getInt("tb_solicitud_mes"));
                    solicitud.setNumeroCorrelativo(rs.getInt("tb_solicitud_numcor"));
                    solicitud.setFechaHoraRegistro(rs.getString("tb_solicitud_fechor"));
                    solicitud.setFechaHoraAsignacion(rs.getString("tb_solicitud_fechorasi"));
                    solicitud.setFechaHoraInicioAtencion(rs.getString("tb_solicitud_fechoriniate"));
                    solicitud.setFechaHoraTerminoAtencion(rs.getString("tb_solicitud_fechorterate"));
                    solicitud.setMotivo(rs.getString("tb_solicitud_mot"));
                    solicitud.setEstado(rs.getString("tb_solicitud_est"));
                    solicitud.setTipoSolicitud(rs.getString("tb_solicitud_tip"));

                    usuario.setApePaterno(rs.getString("tb_usuario_apepat"));
                    usuario.setApeMaterno(rs.getString("tb_usuario_apemat"));
                    usuario.setNombres(rs.getString("tb_usuario_nom"));
                    solicitud.setUsuario(usuario);
                    lista.add(solicitud);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Solicitud> ListarMisSolicitudesPendientes() {
        List<Solicitud> lista = new ArrayList<>();

        String sql = "SELECT * FROM tb_solicitud s "
                + " INNER JOIN tb_usuario u ON u.tb_usuario_id = s.tb_usuario_id\n"
                + "WHERE tb_solicitud_est = 'P' \n"
                + "ORDER BY tb_solicitud_fechor ASC ";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    Solicitud solicitud = new Solicitud();
                    solicitud.setIdSolicitud(rs.getInt("tb_solicitud_id"));
                    solicitud.setAnnio(rs.getInt("tb_solicitud_ani"));
                    solicitud.setMes(rs.getInt("tb_solicitud_mes"));
                    solicitud.setNumeroCorrelativo(rs.getInt("tb_solicitud_numcor"));
                    solicitud.setFechaHoraRegistro(rs.getString("tb_solicitud_fechor"));
                    solicitud.setFechaHoraAsignacion(rs.getString("tb_solicitud_fechorasi"));
                    solicitud.setFechaHoraInicioAtencion(rs.getString("tb_solicitud_fechoriniate"));
                    solicitud.setFechaHoraTerminoAtencion(rs.getString("tb_solicitud_fechorterate"));
                    solicitud.setMotivo(rs.getString("tb_solicitud_mot"));
                    solicitud.setEstado(rs.getString("tb_solicitud_est"));
                    solicitud.setTipoSolicitud(rs.getString("tb_solicitud_tip"));
                    usuario.setApePaterno(rs.getString("tb_usuario_apepat"));
                    usuario.setApeMaterno(rs.getString("tb_usuario_apemat"));
                    usuario.setNombres(rs.getString("tb_usuario_nom"));

                    solicitud.setUsuario(usuario);
                    lista.add(solicitud);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Solicitud> ListarMisSolicitudesAsignadasNoFinalizadas() {
        List<Solicitud> lista = new ArrayList<>();

        String sql = "SELECT * FROM tb_solicitud s "
                + " INNER JOIN tb_usuario u ON u.tb_usuario_id = s.tb_usuario_id\n"
                + "WHERE (tb_solicitud_est not in('P')) AND tb_solicitud_fechorterate IS NULL \n"
                + "ORDER BY tb_solicitud_fechor ASC ";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    Solicitud solicitud = new Solicitud();
                    solicitud.setIdSolicitud(rs.getInt("tb_solicitud_id"));
                    solicitud.setAnnio(rs.getInt("tb_solicitud_ani"));
                    solicitud.setMes(rs.getInt("tb_solicitud_mes"));
                    solicitud.setNumeroCorrelativo(rs.getInt("tb_solicitud_numcor"));
                    solicitud.setFechaHoraRegistro(rs.getString("tb_solicitud_fechor"));
                    solicitud.setFechaHoraAsignacion(rs.getString("tb_solicitud_fechorasi"));
                    solicitud.setFechaHoraInicioAtencion(rs.getString("tb_solicitud_fechoriniate"));
                    solicitud.setFechaHoraTerminoAtencion(rs.getString("tb_solicitud_fechorterate"));
                    solicitud.setMotivo(rs.getString("tb_solicitud_mot"));
                    solicitud.setEstado(rs.getString("tb_solicitud_est"));
                    solicitud.setTipoSolicitud(rs.getString("tb_solicitud_tip"));
                    usuario.setApePaterno(rs.getString("tb_usuario_apepat"));
                    usuario.setApeMaterno(rs.getString("tb_usuario_apemat"));
                    usuario.setNombres(rs.getString("tb_usuario_nom"));

                    solicitud.setUsuario(usuario);
                    lista.add(solicitud);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Solicitud> ListarMisSolicitudesFinalizadas(int idUsuario) {
        List<Solicitud> lista = new ArrayList<>();

        String sql = "SELECT * FROM tb_solicitud s \n"
                + "INNER JOIN tb_usuario u ON u.tb_usuario_id = s.tb_usuario_id\n"
                + "WHERE tb_solicitud_est in('T') AND tb_solicitud_id IN\n"
                + "(select tb_solicitud_id from tb_asignacion where (tb_usuario_id = ?) OR ? = 0)\n"
                + "ORDER BY tb_solicitud_fechor DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    Solicitud solicitud = new Solicitud();
                    solicitud.setIdSolicitud(rs.getInt("tb_solicitud_id"));
                    solicitud.setAnnio(rs.getInt("tb_solicitud_ani"));
                    solicitud.setMes(rs.getInt("tb_solicitud_mes"));
                    solicitud.setNumeroCorrelativo(rs.getInt("tb_solicitud_numcor"));
                    solicitud.setFechaHoraRegistro(rs.getString("tb_solicitud_fechor"));
                    solicitud.setFechaHoraAsignacion(rs.getString("tb_solicitud_fechorasi"));
                    solicitud.setFechaHoraInicioAtencion(rs.getString("tb_solicitud_fechoriniate"));
                    solicitud.setFechaHoraTerminoAtencion(rs.getString("tb_solicitud_fechorterate"));
                    solicitud.setMotivo(rs.getString("tb_solicitud_mot"));
                    solicitud.setEstado(rs.getString("tb_solicitud_est"));
                    solicitud.setTipoSolicitud(rs.getString("tb_solicitud_tip"));
                    usuario.setApePaterno(rs.getString("tb_usuario_apepat"));
                    usuario.setApeMaterno(rs.getString("tb_usuario_apemat"));
                    usuario.setNombres(rs.getString("tb_usuario_nom"));

                    solicitud.setUsuario(usuario);
                    lista.add(solicitud);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Solicitud> ListarMisSolicitudesPorAtenderUsuario(int idUsuario) {
        List<Solicitud> lista = new ArrayList<>();

        String sql = "SELECT * FROM tb_solicitud s \n"
                + "INNER JOIN tb_usuario u ON u.tb_usuario_id = s.tb_usuario_id\n"
                + "INNER JOIN tb_asignacion a ON (a.tb_solicitud_id = s.tb_solicitud_id)\n"
                + "WHERE (tb_solicitud_est not in('P', 'T')) AND a.tb_usuario_id =?\n"
                + "ORDER BY tb_asignacion_fechor ASC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    Solicitud solicitud = new Solicitud();
                    solicitud.setIdSolicitud(rs.getInt("tb_solicitud_id"));
                    solicitud.setAnnio(rs.getInt("tb_solicitud_ani"));
                    solicitud.setMes(rs.getInt("tb_solicitud_mes"));
                    solicitud.setNumeroCorrelativo(rs.getInt("tb_solicitud_numcor"));
                    solicitud.setFechaHoraRegistro(rs.getString("tb_solicitud_fechor"));
                    solicitud.setFechaHoraAsignacion(rs.getString("tb_solicitud_fechorasi"));
                    solicitud.setFechaHoraInicioAtencion(rs.getString("tb_solicitud_fechoriniate"));
                    solicitud.setFechaHoraTerminoAtencion(rs.getString("tb_solicitud_fechorterate"));
                    solicitud.setMotivo(rs.getString("tb_solicitud_mot"));
                    solicitud.setEstado(rs.getString("tb_solicitud_est"));
                    solicitud.setTipoSolicitud(rs.getString("tb_solicitud_tip"));
                    usuario.setApePaterno(rs.getString("tb_usuario_apepat"));
                    usuario.setApeMaterno(rs.getString("tb_usuario_apemat"));
                    usuario.setNombres(rs.getString("tb_usuario_nom"));

                    solicitud.setUsuario(usuario);
                    lista.add(solicitud);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public String Guardar(SolicitudDTO dto) {
        String result = "";
        String sql = "INSERT INTO tb_solicitud ("
                + "tb_solicitud_ani, tb_solicitud_mes, tb_solicitud_numcor, "
                + " tb_solicitud_mot, tb_solicitud_est, tb_solicitud_tip, tb_usuario_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, dto.getAnnio());
            ps.setInt(2, dto.getMes());
            ps.setInt(3, dto.getNumeroCorrelativo());
            ps.setString(4, dto.getMotivo());
            ps.setString(5, dto.getEstado());
            ps.setString(6, dto.getTipoSolicitud());
            ps.setInt(7, dto.getUsuario().getIdUsuario());

            result = ps.executeUpdate() > 0 ? "OK" : "No se puedo registrar solicitud";
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        }
        return result;
    }

    public String Eliminar(int id) throws Exception {
        String result;
        String sql = "DELETE FROM tb_solicitud where tb_solicitud_id=?";

        try (PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setInt(1, id);
            result = ps.executeUpdate() > 0 ? "OK" : "No se pudo eliminar solicitud";
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new Exception("No se puede eliminar la solicitud por que cuenta con otros registros.");
        } catch (SQLException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    public int ObtenerCorrelativo(int annio, int mes) {
        String sql = "SELECT COALESCE(MAX(tb_solicitud_numcor), 0) + 1 "
                + "FROM tb_solicitud "
                + "WHERE tb_solicitud_ani = ? AND tb_solicitud_mes = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, annio);
            statement.setInt(2, mes);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public String FinalizarSolicitud(SolicitudDTO dto) {
        String result;
        String sql = "{CALL sp_finalizar_solicitud(?, ?)}";

        try (PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setInt(1, dto.getUsuario().getIdUsuario());
            ps.setInt(2, dto.getIdSolicitud());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getString(1);
            } else {
                result = "No se pudo finalizar solicitud.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    public int CantidadDeSolicitudPorEstado(int annio, int mes, String estado) {
        String sql = "select count(1) from tb_solicitud"
                + " where tb_solicitud_ani = ? AND tb_solicitud_mes = ? AND tb_solicitud_est = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, annio);
            ps.setInt(2, mes);
            ps.setString(3, estado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int CantidadDeSolicitudPorTipo(int annio, int mes, String estado) {
        String sql = "select count(1) from tb_solicitud"
                + " where tb_solicitud_ani = ? AND tb_solicitud_mes = ? AND tb_solicitud_tip = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, annio);
            ps.setInt(2, mes);
            ps.setString(3, estado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
