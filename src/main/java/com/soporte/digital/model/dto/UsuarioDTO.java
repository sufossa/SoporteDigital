package com.soporte.digital.model.dto;

import com.soporte.digital.model.entities.Cliente;
import com.soporte.digital.model.entities.Rol;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private int id;
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
    private ClienteDTO cliente;
    private int idRol;
    private String nombreRol;

    private String nombreTipoDoc;
    private String nombreTipoUsuario;
    private boolean esCoordinador;

    public String nombreCompleto() {
        return apePaterno + " " + apeMaterno + " , " + nombres;
    }

    public void CargarNombreTipoDoc(List<TipoDocumentoDTO> lista) {
        for (TipoDocumentoDTO dto : lista) {
            if (tipoUsuario.equalsIgnoreCase("C")) {
                if (dto.getId().equalsIgnoreCase(this.getCliente().getTipoDoc())) {
                    this.nombreTipoDoc = dto.getDescripcion();
                    break;
                }
            } else {
                if (dto.getId().equalsIgnoreCase(this.tipoDoc)) {
                    this.nombreTipoDoc = dto.getDescripcion();
                    break;
                }
            }

        }
    }

    public void CargarNombreTipoUsuario(List<TipoUsuarioDTO> lista) {
        for (TipoUsuarioDTO dto : lista) {
            if (dto.getId().equalsIgnoreCase(this.tipoUsuario)) {
                this.nombreTipoUsuario = dto.getDescripcion();
                break;
            }
        }
    }
}
