package com.soporte.digital.config;

import com.soporte.digital.model.dao.UsuarioDAO;
import com.soporte.digital.model.entities.Usuario;
import java.sql.Connection;

public class Test {

    public static void main(String[] args) {
        Connection connection = ConexionDB.getInstance().getConnection();
    }

}
