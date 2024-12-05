package com.soporte.digital.model.dto;

import com.soporte.digital.model.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrabajoDTO {

    private int idTrabajo;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private String descripcion;
    private int idSolicitud;
    private UsuarioDTO usuario;
}
