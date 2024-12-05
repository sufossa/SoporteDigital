package com.soporte.digital.model.entities;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    private int idSolicitud;
    private int annio;
    private int mes;
    private int numeroCorrelativo;
    private String fechaHoraRegistro;
    private String fechaHoraAsignacion;
    private String fechaHoraInicioAtencion;
    private String fechaHoraTerminoAtencion;
    private String motivo;
    private String estado;
    private String tipoSolicitud;
    private Usuario usuario;
}
