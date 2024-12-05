package com.soporte.digital.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ReporteDTO {
 
    private int cantidad;
    private String descripcion;

    public ReporteDTO(String descripcion, int cantidad) {
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

}
