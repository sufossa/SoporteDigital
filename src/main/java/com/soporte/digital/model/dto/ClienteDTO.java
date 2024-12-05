package com.soporte.digital.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private int idCliente;
    private String tipoDoc;
    private String numeroDoc;
    private String razonSocial;
    private String direccion;
    private String telefono;

}
