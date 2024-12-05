package com.soporte.digital.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private int idUsuario;
    private String tipoDoc;
    private String numeroDoc;
    private String apePaterno;
    private String apeMaterno;
    private String nombres;
    private String correo;
    private String password;
    private int estado;
    private String tipoUsuario;
    private Cliente cliente;
    private Rol rol;
    private boolean esCoordinador;

    public String nombreCompleto() {
        return apePaterno + " " + apeMaterno + " , " + nombres;
    }
}
