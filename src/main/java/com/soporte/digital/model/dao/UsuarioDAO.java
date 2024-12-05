package com.soporte.digital.model.dao;

import com.soporte.digital.model.dto.UsuarioDTO;
import com.soporte.digital.model.entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Usuario> ListarTodos() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "select * from tb_rol r "
                + " inner join tb_usuario u on u.tb_rol_id=r.tb_rol_id "
                + " where tb_cliente_id IS NULL";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("tb_rol_id"));
                rol.setTipo(rs.getString("tb_rol_nom"));

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("tb_usuario_id"));
                usuario.setCorreo(rs.getString("tb_usuario_corele"));
                usuario.setPassword(rs.getString("tb_usuario_con"));
                usuario.setEstado(rs.getBoolean("tb_usuario_act") ? 1 : 0);
                usuario.setApePaterno(rs.getString("tb_usuario_apepat"));
                usuario.setApeMaterno(rs.getString("tb_usuario_apemat"));
                usuario.setNombres(rs.getString("tb_usuario_nom"));
                usuario.setTipoDoc(rs.getString("tb_usuario_tipdocide"));
                usuario.setNumeroDoc(rs.getString("tb_usuario_numdocide"));
                usuario.setTipoUsuario(rs.getString("tb_usuario_tip"));
                usuario.setRol(rol);

                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Usuario> ListarColaboradores() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "select * from tb_rol r "
                + " inner join tb_usuario u on u.tb_rol_id=r.tb_rol_id "
                + " where tb_usuario_tip = 'E' "
                + " order by tb_usuario_apepat ASC ,tb_usuario_apemat ASC ";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("tb_rol_id"));
                rol.setTipo(rs.getString("tb_rol_nom"));

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("tb_usuario_id"));
                usuario.setCorreo(rs.getString("tb_usuario_corele"));
                usuario.setPassword(rs.getString("tb_usuario_con"));
                usuario.setEstado(rs.getBoolean("tb_usuario_act") ? 1 : 0);
                usuario.setApePaterno(rs.getString("tb_usuario_apepat"));
                usuario.setApeMaterno(rs.getString("tb_usuario_apemat"));
                usuario.setNombres(rs.getString("tb_usuario_nom"));
                usuario.setTipoDoc(rs.getString("tb_usuario_tipdocide"));
                usuario.setNumeroDoc(rs.getString("tb_usuario_numdocide"));
                usuario.setTipoUsuario(rs.getString("tb_usuario_tip"));
                usuario.setRol(rol);

                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    public List<Usuario> ListarColaboradoresActivos() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "select * from tb_rol r "
                + " inner join tb_usuario u on u.tb_rol_id=r.tb_rol_id "
                + " where tb_usuario_tip = 'E' AND tb_usuario_act = true"
                + " order by tb_usuario_apepat ASC ,tb_usuario_apemat ASC ";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("tb_rol_id"));
                rol.setTipo(rs.getString("tb_rol_nom"));

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("tb_usuario_id"));
                usuario.setCorreo(rs.getString("tb_usuario_corele"));
                usuario.setPassword(rs.getString("tb_usuario_con"));
                usuario.setEstado(rs.getBoolean("tb_usuario_act") ? 1 : 0);
                usuario.setApePaterno(rs.getString("tb_usuario_apepat"));
                usuario.setApeMaterno(rs.getString("tb_usuario_apemat"));
                usuario.setNombres(rs.getString("tb_usuario_nom"));
                usuario.setTipoDoc(rs.getString("tb_usuario_tipdocide"));
                usuario.setNumeroDoc(rs.getString("tb_usuario_numdocide"));
                usuario.setTipoUsuario(rs.getString("tb_usuario_tip"));
                usuario.setRol(rol);

                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    public Usuario BuscarPorId(int id) {
        Usuario usuario = null;

        String sql = "select * from tb_rol r "
                + " inner join tb_usuario u on u.tb_rol_id=r.tb_rol_id "
                + " where tb_usuario_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt("tb_rol_id"));
                    rol.setTipo(rs.getString("tb_rol_nom"));

                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("tb_usuario_id"));
                    usuario.setCorreo(rs.getString("tb_usuario_corele"));
                    usuario.setPassword(rs.getString("tb_usuario_con"));
                    usuario.setEstado(rs.getBoolean("tb_usuario_act") ? 1 : 0);
                    usuario.setApePaterno(rs.getString("tb_usuario_apepat"));
                    usuario.setApeMaterno(rs.getString("tb_usuario_apemat"));
                    usuario.setNombres(rs.getString("tb_usuario_nom"));
                    usuario.setTipoDoc(rs.getString("tb_usuario_tipdocide"));
                    usuario.setNumeroDoc(rs.getString("tb_usuario_numdocide"));
                    usuario.setTipoUsuario(rs.getString("tb_usuario_tip"));
                    usuario.setRol(rol);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    public String Guardar(UsuarioDTO dto) {
        String result;
        String sql = "{CALL sp_insertar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setString(1, dto.getTipoDoc());
            ps.setString(2, dto.getNumeroDoc());
            ps.setString(3, dto.getApePaterno());
            ps.setString(4, dto.getApeMaterno());
            ps.setString(5, dto.getNombres());
            ps.setString(6, dto.getCorreo());
            ps.setString(7, dto.getPassword());
            ps.setBoolean(8, dto.getEstado() == 1);
            ps.setString(9, dto.getTipoUsuario());
            ps.setObject(10, dto.getIdRol(), java.sql.Types.INTEGER);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getString(1);
            } else {
                result = "No se pudo guardar el usuario.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    public String Editar(UsuarioDTO dto) {
        String result;
        String sql = "{CALL sp_editar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}";

        try (PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setInt(1, dto.getIdUsuario());
            ps.setString(2, dto.getTipoDoc());
            ps.setString(3, dto.getNumeroDoc());
            ps.setString(4, dto.getApePaterno());
            ps.setString(5, dto.getApeMaterno());
            ps.setString(6, dto.getNombres());
            ps.setString(7, dto.getCorreo());
            ps.setString(8, dto.getPassword());
            ps.setBoolean(9, dto.getEstado() == 1);
            ps.setString(10, dto.getTipoUsuario());
            ps.setObject(11, dto.getIdRol(), java.sql.Types.INTEGER);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rs.getString(1);
                } else {
                    result = "No se pudo editar el usuario.";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    public String Eliminar(int id) throws Exception {
        String result;
        String sql = "{CALL sp_eliminar_usuario(?)}";

        try (PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getString(1);
            } else {
                result = "No se pudo eliminar al usuario.";
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new Exception("No se puede eliminar al usuario por que cuenta con otros registros.");
        } catch (SQLException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    public List<Usuario> ListarColaboradoresPorSolicitud(int idSolicitud) {
        List<Usuario> lista = new ArrayList<>();

        String sql = "select * from tb_asignacion a "
                + " inner join tb_usuario u ON u.tb_usuario_id = a.tb_usuario_id\n"
                + "inner join tb_rol r on u.tb_rol_id=r.tb_rol_id \n"
                + "where tb_solicitud_id = ? \n"
                + "order by tb_usuario_apepat ASC , tb_usuario_apemat ASC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSolicitud);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt("tb_rol_id"));
                    rol.setTipo(rs.getString("tb_rol_nom"));

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("tb_usuario_id"));
                    usuario.setCorreo(rs.getString("tb_usuario_corele"));
                    usuario.setPassword(rs.getString("tb_usuario_con"));
                    usuario.setEstado(rs.getBoolean("tb_usuario_act") ? 1 : 0);
                    usuario.setApePaterno(rs.getString("tb_usuario_apepat"));
                    usuario.setApeMaterno(rs.getString("tb_usuario_apemat"));
                    usuario.setNombres(rs.getString("tb_usuario_nom"));
                    usuario.setTipoDoc(rs.getString("tb_usuario_tipdocide"));
                    usuario.setNumeroDoc(rs.getString("tb_usuario_numdocide"));
                    usuario.setTipoUsuario(rs.getString("tb_usuario_tip"));
                    usuario.setEsCoordinador(rs.getBoolean("tb_asignacion_coo"));
                    usuario.setRol(rol);

                    lista.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
      public Usuario BuscarPorIdSolicitud(int idSolicitud) {
        Usuario usuario = null;

        String sql = "select * from tb_usuario u\n" +
                    "inner join tb_rol r on u.tb_rol_id=r.tb_rol_id \n" +
                    "where tb_usuario_id IN (select tb_usuario_id from tb_solicitud where tb_solicitud_id = ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSolicitud);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt("tb_rol_id"));
                    rol.setTipo(rs.getString("tb_rol_nom"));

                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("tb_usuario_id"));
                    usuario.setCorreo(rs.getString("tb_usuario_corele"));
                    usuario.setPassword(rs.getString("tb_usuario_con"));
                    usuario.setEstado(rs.getBoolean("tb_usuario_act") ? 1 : 0);
                    usuario.setApePaterno(rs.getString("tb_usuario_apepat"));
                    usuario.setApeMaterno(rs.getString("tb_usuario_apemat"));
                    usuario.setNombres(rs.getString("tb_usuario_nom"));
                    usuario.setTipoDoc(rs.getString("tb_usuario_tipdocide"));
                    usuario.setNumeroDoc(rs.getString("tb_usuario_numdocide"));
                    usuario.setTipoUsuario(rs.getString("tb_usuario_tip"));
                    usuario.setRol(rol);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
