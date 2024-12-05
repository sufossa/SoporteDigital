package com.soporte.digital.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoDocumentoDTO {
    private String id;
    private String descripcion;

    public TipoDocumentoDTO(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    
    
}
