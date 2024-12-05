package com.soporte.digital.model.service;

import com.soporte.digital.config.ConexionDB;
import com.soporte.digital.model.dao.*;
import com.soporte.digital.model.dto.*;
import com.soporte.digital.model.entities.*;
import com.soporte.digital.util.ResourcesUtils;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UsuarioFacade {

    private UsuarioDAO colaboradorDAO;

    public UsuarioFacade() {
        Connection connection = ConexionDB.getInstance().getConnection();
        colaboradorDAO = new UsuarioDAO(connection);
    }

    public List<UsuarioDTO> ListarTodos() {
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        List<Usuario> usuarios = colaboradorDAO.ListarTodos();

        for (Usuario usuario : usuarios) {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setIdUsuario(usuario.getIdUsuario());
            dto.setTipoDoc(usuario.getTipoDoc());
            dto.setNumeroDoc(usuario.getNumeroDoc());
            dto.setApePaterno(usuario.getApePaterno());
            dto.setApeMaterno(usuario.getApeMaterno());
            dto.setNombres(usuario.getNombres());
            dto.setCorreo(usuario.getCorreo());
            dto.setPassword(usuario.getPassword());
            dto.setEstado(usuario.getEstado());
            dto.setTipoUsuario(usuario.getTipoUsuario());

            if (usuario.getRol() != null) {
                dto.setIdRol(usuario.getRol().getIdRol());
                dto.setNombreRol(usuario.getRol().getTipo());
            }
            dto.CargarNombreTipoDoc(ResourcesUtils.listTipoDocumentosCliente());
            dto.CargarNombreTipoUsuario(ResourcesUtils.listTipoUsuario());
            usuariosDTO.add(dto);
        }

        return usuariosDTO;
    }

    public List<UsuarioDTO> ListarColaboradoresActivos() {
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        List<Usuario> usuarios = colaboradorDAO.ListarColaboradoresActivos();

        for (Usuario usuario : usuarios) {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setIdUsuario(usuario.getIdUsuario());
            dto.setTipoDoc(usuario.getTipoDoc());
            dto.setNumeroDoc(usuario.getNumeroDoc());
            dto.setApePaterno(usuario.getApePaterno());
            dto.setApeMaterno(usuario.getApeMaterno());
            dto.setNombres(usuario.getNombres());
            dto.setCorreo(usuario.getCorreo());
            dto.setPassword(usuario.getPassword());
            dto.setEstado(usuario.getEstado());
            dto.setTipoUsuario(usuario.getTipoUsuario());

            if (usuario.getRol() != null) {
                dto.setIdRol(usuario.getRol().getIdRol());
                dto.setNombreRol(usuario.getRol().getTipo());
            }
            dto.CargarNombreTipoDoc(ResourcesUtils.listTipoDocumentosCliente());
            dto.CargarNombreTipoUsuario(ResourcesUtils.listTipoUsuario());
            usuariosDTO.add(dto);
        }

        return usuariosDTO;
    }

    public List<UsuarioDTO> ListarColaboradores() {
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        List<Usuario> usuarios = colaboradorDAO.ListarColaboradores();

        for (Usuario usuario : usuarios) {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setIdUsuario(usuario.getIdUsuario());
            dto.setTipoDoc(usuario.getTipoDoc());
            dto.setNumeroDoc(usuario.getNumeroDoc());
            dto.setApePaterno(usuario.getApePaterno());
            dto.setApeMaterno(usuario.getApeMaterno());
            dto.setNombres(usuario.getNombres());
            dto.setCorreo(usuario.getCorreo());
            dto.setPassword(usuario.getPassword());
            dto.setEstado(usuario.getEstado());
            dto.setTipoUsuario(usuario.getTipoUsuario());

            if (usuario.getRol() != null) {
                dto.setIdRol(usuario.getRol().getIdRol());
                dto.setNombreRol(usuario.getRol().getTipo());
            }
            dto.CargarNombreTipoDoc(ResourcesUtils.listTipoDocumentosCliente());
            dto.CargarNombreTipoUsuario(ResourcesUtils.listTipoUsuario());
            usuariosDTO.add(dto);
        }

        return usuariosDTO;
    }

    public List<UsuarioDTO> ListarTodosPorSolicitud(int idSolicitud) {
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        List<Usuario> usuarios = colaboradorDAO.ListarColaboradoresPorSolicitud(idSolicitud);

        for (Usuario usuario : usuarios) {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setIdUsuario(usuario.getIdUsuario());
            dto.setTipoDoc(usuario.getTipoDoc());
            dto.setNumeroDoc(usuario.getNumeroDoc());
            dto.setApePaterno(usuario.getApePaterno());
            dto.setApeMaterno(usuario.getApeMaterno());
            dto.setNombres(usuario.getNombres());
            dto.setCorreo(usuario.getCorreo());
            dto.setPassword(usuario.getPassword());
            dto.setEstado(usuario.getEstado());
            dto.setTipoUsuario(usuario.getTipoUsuario());
            dto.setEsCoordinador(usuario.isEsCoordinador());

            if (usuario.getRol() != null) {
                dto.setIdRol(usuario.getRol().getIdRol());
                dto.setNombreRol(usuario.getRol().getTipo());
            }
            dto.CargarNombreTipoDoc(ResourcesUtils.listTipoDocumentosCliente());
            dto.CargarNombreTipoUsuario(ResourcesUtils.listTipoUsuario());
            usuariosDTO.add(dto);
        }

        return usuariosDTO;
    }

    public UsuarioDTO BuscarPorId(int id) {
        Usuario usuario = colaboradorDAO.BuscarPorId(id);

        if (usuario != null) {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setIdUsuario(usuario.getIdUsuario());
            dto.setTipoDoc(usuario.getTipoDoc());
            dto.setNumeroDoc(usuario.getNumeroDoc());
            dto.setApePaterno(usuario.getApePaterno());
            dto.setApeMaterno(usuario.getApeMaterno());
            dto.setNombres(usuario.getNombres());
            dto.setCorreo(usuario.getCorreo());
            dto.setPassword(usuario.getPassword());
            dto.setEstado(usuario.getEstado());
            dto.setTipoUsuario(usuario.getTipoUsuario());

            if (usuario.getRol() != null) {
                dto.setIdRol(usuario.getRol().getIdRol());
                dto.setNombreRol(usuario.getRol().getTipo());
            }
            return dto;
        }
        return null;
    }
    
    

    public String Guardar(UsuarioDTO dto) {
        return colaboradorDAO.Guardar(dto);
    }

    public String Editar(UsuarioDTO dto) {
        return colaboradorDAO.Editar(dto);
    }

    public String Eliminar(int id) throws Exception {
        return colaboradorDAO.Eliminar(id);
    }

}
