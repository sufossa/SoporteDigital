package com.soporte.digital.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionDTO {

    private String fechaHoraAsignacion;
    private String fechaHoraInicioAtencion;
    private String fechaHoraTerminoAtencion;
    private boolean esCoordinador;
    private int idSolicitud;
    private int idUsuario;
}
