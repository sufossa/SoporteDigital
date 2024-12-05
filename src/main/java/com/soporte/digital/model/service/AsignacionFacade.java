package com.soporte.digital.model.service;

import com.soporte.digital.config.ConexionDB;
import com.soporte.digital.model.dao.AsignacionDAO;
import com.soporte.digital.model.dto.AsignacionDTO;
import java.sql.Connection;

public class AsignacionFacade {

    private AsignacionDAO asignacionDAO;

    public AsignacionFacade() {
        Connection connection = ConexionDB.getInstance().getConnection();
        asignacionDAO = new AsignacionDAO(connection);
    }

    public String Guardar(AsignacionDTO dto) {
        return asignacionDAO.Guardar(dto);
    }

    public String AsignarCoordinador(AsignacionDTO dto) {
        return asignacionDAO.AsignarCoordinador(dto);
    }
}
