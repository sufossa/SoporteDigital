package com.soporte.digital.model.dao;

import com.soporte.digital.model.dto.UsuarioDTO;
import com.soporte.digital.model.entities.Cliente;
import com.soporte.digital.model.entities.Rol;
import com.soporte.digital.model.entities.Usuario;
import com.soporte.digital.util.ConstantesApp;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public String Registrar(UsuarioDTO usuario) {
        String resultado = "Error al registrar";

        String sql = "{ ? = CALL sp_insertar_cliente(?, ?, ?, ?, ?, ?, ?,?,?,?,?) }";

        try (CallableStatement cs = connection.prepareCall(sql)) {
            cs.registerOutParameter(1, java.sql.Types.VARCHAR);

            cs.setString(2, usuario.getTipoDoc());
            cs.setString(3, usuario.getNumeroDoc());
            cs.setString(4, usuario.getApePaterno());
            cs.setString(5, usuario.getApeMaterno());
            cs.setString(6, usuario.getNombres());
            cs.setString(7, usuario.getCliente().getRazonSocial());
            cs.setString(8, usuario.getCliente().getDireccion());
            cs.setString(9, usuario.getCliente().getTelefono());
            cs.setString(10, usuario.getCorreo());
            cs.setString(11, usuario.getPassword());
            cs.setInt(12, ConstantesApp.ROL_CLIENTE);

            cs.execute();

            resultado = cs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = "Error: " + e.getMessage();
        }

        return resultado;
    }

    public String Editar(UsuarioDTO usuario) {
        String resultado = "Error al editar";

        String sql = "{ ? = CALL sp_editar_cliente(?, ?, ?, ?, ?, ?, ?,?,?,?,?,?) }";

        try (CallableStatement cs = connection.prepareCall(sql)) {
            cs.registerOutParameter(1, java.sql.Types.VARCHAR);
            cs.setInt(2, usuario.getIdUsuario());
            cs.setString(3, usuario.getTipoDoc());
            cs.setString(4, usuario.getNumeroDoc());
            cs.setString(5, usuario.getApePaterno());
            cs.setString(6, usuario.getApeMaterno());
            cs.setString(7, usuario.getNombres());
            cs.setString(8, usuario.getCliente().getRazonSocial());
            cs.setString(9, usuario.getCliente().getDireccion());
            cs.setString(10, usuario.getCliente().getTelefono());
            cs.setString(11, usuario.getCorreo());
            cs.setString(12, usuario.getPassword());
            cs.setInt(13, ConstantesApp.ROL_CLIENTE);

            cs.execute();

            resultado = cs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = "Error: " + e.getMessage();
        }

        return resultado;
    }

    public String Eliminar(int id) throws Exception {
        String result;
        String sql = "{CALL sp_eliminar_cliente(?)}";

        try (PreparedStatement ps = connection.prepareCall(sql)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getString(1);
            } else {
                result = "No se pudo eliminar al cliente.";
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new Exception("No se puede eliminar al cliente por que cuenta con otros registros.");
        } catch (SQLException e) {
            e.printStackTrace();
            result = e.getMessage();
        }

        return result;
    }

    public List<Usuario> ListarTodos() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "select * from tb_usuario u \n"
                + "INNER JOIN tb_cliente c ON c.tb_cliente_id = u.tb_cliente_id\n"
                + "INNER JOIN tb_rol r ON u.tb_rol_id=r.tb_rol_id ";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
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

                cliente.setIdCliente(rs.getInt("tb_cliente_id"));
                cliente.setTipoDoc(rs.getString("tb_cliente_tipdocide"));
                cliente.setRazonSocial(rs.getString("tb_cliente_razsoc"));
                cliente.setNumeroDoc(rs.getString("tb_cliente_numdocide"));
                cliente.setDireccion(rs.getString("tb_cliente_dir"));
                cliente.setTelefono(rs.getString("tb_cliente_tel"));

                usuario.setCliente(cliente);
                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Usuario BuscarPorId(int idUsuario) {
        Usuario usuario = null;

        String sql = "select * from tb_usuario u \n"
                + " INNER JOIN tb_cliente c ON c.tb_cliente_id = u.tb_cliente_id \n"
                + " INNER JOIN tb_rol r ON u.tb_rol_id=r.tb_rol_id  \n"
                + " WHERE u.tb_usuario_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Cliente cliente = new Cliente();
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

                    cliente.setIdCliente(rs.getInt("tb_cliente_id"));
                    cliente.setTipoDoc(rs.getString("tb_cliente_tipdocide"));
                    cliente.setRazonSocial(rs.getString("tb_cliente_razsoc"));
                    cliente.setNumeroDoc(rs.getString("tb_cliente_numdocide"));
                    cliente.setDireccion(rs.getString("tb_cliente_dir"));
                    cliente.setTelefono(rs.getString("tb_cliente_tel"));

                    usuario.setCliente(cliente);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

}
