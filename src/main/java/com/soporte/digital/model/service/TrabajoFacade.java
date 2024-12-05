package com.soporte.digital.model.service;

import com.soporte.digital.config.ConexionDB;
import com.soporte.digital.model.dao.*;
import com.soporte.digital.model.dto.*;
import com.soporte.digital.model.entities.Trabajo;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TrabajoFacade {

    private TrabajoDAO trabajoDAO;

    public TrabajoFacade() {
        Connection connection = ConexionDB.getInstance().getConnection();
        trabajoDAO = new TrabajoDAO(connection);
    }

    public String Guardar(TrabajoDTO dto) {
        return trabajoDAO.Guardar(dto);
    }

    public String Terminar(TrabajoDTO dto) {
        return trabajoDAO.Terminar(dto);
    }

    public List<TrabajoDTO> ListarTrabajoPorSolicitud(int idSolicitud) {
        List<TrabajoDTO> lista = new ArrayList<>();

        List<Trabajo> trabajos = trabajoDAO.ListarTrabajoPorSolicitud(idSolicitud);

        for (Trabajo trabajo : trabajos) {
            UsuarioDTO usuDto = new UsuarioDTO();
            TrabajoDTO dto = new TrabajoDTO();
            dto.setIdSolicitud(trabajo.getIdSolicitud());
            dto.setIdTrabajo(trabajo.getIdTrabajo());
            dto.setDescripcion(trabajo.getDescripcion());
            dto.setFechaHoraInicio(trabajo.getFechaHoraInicio());
            dto.setFechaHoraFin(trabajo.getFechaHoraFin());

            usuDto.setApePaterno(trabajo.getUsuario().getApePaterno());
            usuDto.setApeMaterno(trabajo.getUsuario().getApeMaterno());
            usuDto.setNombres(trabajo.getUsuario().getNombres());
            usuDto.setIdUsuario(trabajo.getUsuario().getIdUsuario());
            dto.setUsuario(usuDto);
            lista.add(dto);
        }
        return lista;
    }
}
