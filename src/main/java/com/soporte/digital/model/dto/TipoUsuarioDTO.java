package com.soporte.digital.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TipoUsuarioDTO {

    private String id;
    private String descripcion;

    public TipoUsuarioDTO(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

}
