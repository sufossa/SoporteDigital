package com.soporte.digital.model.service;

import com.soporte.digital.config.ConexionDB;
import com.soporte.digital.model.dao.*;
import com.soporte.digital.model.dto.ClienteDTO;
import com.soporte.digital.model.dto.UsuarioDTO;
import com.soporte.digital.model.entities.Cliente;
import com.soporte.digital.model.entities.Usuario;
import com.soporte.digital.util.ResourcesUtils;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ClienteFacade {

    private ClienteDAO clienteDAO;

    public ClienteFacade() {
        Connection connection = ConexionDB.getInstance().getConnection();
        clienteDAO = new ClienteDAO(connection);
    }

    public String Registrar(UsuarioDTO dto) {
        return clienteDAO.Registrar(dto);
    }

    public String Editar(UsuarioDTO dto) {
        return clienteDAO.Editar(dto);
    }

    public String Eliminar(int id) throws Exception {
        return clienteDAO.Eliminar(id);
    }

    public List<UsuarioDTO> ListarTodos() {
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        List<Usuario> usuarios = clienteDAO.ListarTodos();

        for (Usuario usuario : usuarios) {
            ClienteDTO cliDto = new ClienteDTO();
            UsuarioDTO usuDto = new UsuarioDTO();

            Cliente cliente = usuario.getCliente();
            cliDto.setDireccion(cliente.getDireccion());
            cliDto.setRazonSocial(cliente.getRazonSocial());
            cliDto.setIdCliente(cliente.getIdCliente());
            cliDto.setNumeroDoc(cliente.getNumeroDoc());
            cliDto.setTipoDoc(cliente.getTipoDoc());
            cliDto.setTelefono(cliente.getTelefono());

            usuDto.setIdUsuario(usuario.getIdUsuario());
            usuDto.setTipoDoc(usuario.getTipoDoc());
            usuDto.setNumeroDoc(usuario.getNumeroDoc());
            usuDto.setApePaterno(usuario.getApePaterno());
            usuDto.setApeMaterno(usuario.getApeMaterno());
            usuDto.setNombres(usuario.getNombres());
            usuDto.setCorreo(usuario.getCorreo());
            usuDto.setPassword(usuario.getPassword());
            usuDto.setEstado(usuario.getEstado());
            usuDto.setTipoUsuario(usuario.getTipoUsuario());
            usuDto.setCliente(cliDto);

            if (usuario.getRol() != null) {
                usuDto.setIdRol(usuario.getRol().getIdRol());
                usuDto.setNombreRol(usuario.getRol().getTipo());
            }
            usuDto.CargarNombreTipoDoc(ResourcesUtils.listTipoDocumentosCliente());
            usuDto.CargarNombreTipoUsuario(ResourcesUtils.listTipoUsuario());

            usuariosDTO.add(usuDto);
        }

        return usuariosDTO;
    }

    public UsuarioDTO BuscarPorId(int idUsuario) {
        Usuario usuario = clienteDAO.BuscarPorId(idUsuario);

        if (usuario != null) {
            ClienteDTO cliDto = new ClienteDTO();
            UsuarioDTO usuDto = new UsuarioDTO();

            Cliente cliente = usuario.getCliente();
            cliDto.setDireccion(cliente.getDireccion());
            cliDto.setRazonSocial(cliente.getRazonSocial());
            cliDto.setIdCliente(cliente.getIdCliente());
            cliDto.setNumeroDoc(cliente.getNumeroDoc());
            cliDto.setTipoDoc(cliente.getTipoDoc());
            cliDto.setTelefono(cliente.getTelefono());

            usuDto.setIdUsuario(usuario.getIdUsuario());
            usuDto.setTipoDoc(usuario.getTipoDoc());
            usuDto.setNumeroDoc(usuario.getNumeroDoc());
            usuDto.setApePaterno(usuario.getApePaterno());
            usuDto.setApeMaterno(usuario.getApeMaterno());
            usuDto.setNombres(usuario.getNombres());
            usuDto.setCorreo(usuario.getCorreo());
            usuDto.setPassword(usuario.getPassword());
            usuDto.setEstado(usuario.getEstado());
            usuDto.setTipoUsuario(usuario.getTipoUsuario());
            usuDto.setCliente(cliDto);
            
            if (usuario.getRol() != null) {
                usuDto.setIdRol(usuario.getRol().getIdRol());
                usuDto.setNombreRol(usuario.getRol().getTipo());
            }
            usuDto.CargarNombreTipoDoc(ResourcesUtils.listTipoDocumentosCliente());
            usuDto.CargarNombreTipoUsuario(ResourcesUtils.listTipoUsuario());

            
            return usuDto;
        }
        return null;
    }
}
