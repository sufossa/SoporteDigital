package com.soporte.digital.model.dto;

import com.soporte.digital.model.entities.Usuario;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDTO {

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
    private UsuarioDTO usuario;

    private String nombreTipoSolicitud;
    private String nombreEstado;

    public void CargarNombreTipoSolicitud(List<TipoSolicitudDTO> lista) {
        for (TipoSolicitudDTO dto : lista) {
            if (dto.getId().equalsIgnoreCase(this.tipoSolicitud)) {
                this.nombreTipoSolicitud = dto.getDescripcion();
                break;
            }
        }
    }

    public void CargarNombreEstado(List<EstadoDTO> lista) {
        for (EstadoDTO dto : lista) {
            if (dto.getId().equalsIgnoreCase(this.estado)) {
                this.nombreEstado = dto.getDescripcion();
                break;
            }
        }
    }
}
