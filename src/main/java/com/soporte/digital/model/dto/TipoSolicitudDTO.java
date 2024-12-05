package com.soporte.digital.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoSolicitudDTO {

    private String id;
    private String descripcion;

    public TipoSolicitudDTO(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

}
