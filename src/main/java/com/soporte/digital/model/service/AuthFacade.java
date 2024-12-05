package com.soporte.digital.model.service;

import com.soporte.digital.config.ConexionDB;
import com.soporte.digital.model.dao.AuthDAO;
import com.soporte.digital.model.dto.UsuarioDTO;
import java.sql.Connection;

public class AuthFacade {

    private AuthDAO authDAO;

    public AuthFacade() {
        Connection connection = ConexionDB.getInstance().getConnection();
        authDAO = new AuthDAO(connection);
    }
    
     public UsuarioDTO Login(String correo, String password) {
         return authDAO.Login(correo, password);
     }

}
