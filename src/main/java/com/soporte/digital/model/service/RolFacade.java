package com.soporte.digital.model.service;

import com.soporte.digital.config.ConexionDB;
import com.soporte.digital.model.dao.*;
import com.soporte.digital.model.dto.*;
import com.soporte.digital.model.entities.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class RolFacade {

    private RolDAO rolDAO;

    public RolFacade() {
        Connection connection = ConexionDB.getInstance().getConnection();
        rolDAO = new RolDAO(connection);
    }

    public List<RolDTO> ListarRolesColaboradores() {
        List<RolDTO> rolesDto = new ArrayList<>();
        List<Rol> roles = rolDAO.ListarRolesColaboradores();

        for (Rol rol : roles) {
            RolDTO dto = new RolDTO();
            dto.setId(rol.getIdRol());
            dto.setDescripcion(rol.getTipo());

            rolesDto.add(dto);
        }

        return rolesDto;
    }
}
