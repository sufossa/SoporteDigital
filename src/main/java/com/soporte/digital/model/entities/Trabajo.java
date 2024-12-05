package com.soporte.digital.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trabajo {

    private int idTrabajo;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private String descripcion;
    private int idSolicitud;
    private Usuario usuario;
}
