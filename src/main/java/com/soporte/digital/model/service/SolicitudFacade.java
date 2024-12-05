package com.soporte.digital.model.service;

import com.soporte.digital.config.ConexionDB;
import com.soporte.digital.model.dao.SolicitudDAO;
import com.soporte.digital.model.dao.UsuarioDAO;
import com.soporte.digital.model.dto.ReporteDTO;
import com.soporte.digital.model.dto.SolicitudDTO;
import com.soporte.digital.model.dto.UsuarioDTO;
import com.soporte.digital.model.entities.Solicitud;
import com.soporte.digital.model.entities.Usuario;
import com.soporte.digital.util.CorreoUtils;
import com.soporte.digital.util.PlantillaCorreo;
import com.soporte.digital.util.ResourcesUtils;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SolicitudFacade {

    private SolicitudDAO solicitudDAO;
    private UsuarioDAO usuarioDAO;

    public SolicitudFacade() {
        Connection connection = ConexionDB.getInstance().getConnection();
        solicitudDAO = new SolicitudDAO(connection);
        usuarioDAO = new UsuarioDAO(connection);
    }

    public List<ReporteDTO> ListarCantidadSolicitudPorEstado(int annio, int mes) {
        List<ReporteDTO> lista = new ArrayList<>();
        lista.add(new ReporteDTO("Pendiente", solicitudDAO.CantidadDeSolicitudPorEstado(annio, mes, "P")));
        lista.add(new ReporteDTO("Asignada", solicitudDAO.CantidadDeSolicitudPorEstado(annio, mes, "A")));
        lista.add(new ReporteDTO("Proceso", solicitudDAO.CantidadDeSolicitudPorEstado(annio, mes, "R")));
        lista.add(new ReporteDTO("Atendida", solicitudDAO.CantidadDeSolicitudPorEstado(annio, mes, "T")));
        return lista;
    }

    public List<ReporteDTO> ListarCantidadSolicitudPorTipo(int annio, int mes) {
        List<ReporteDTO> lista = new ArrayList<>();
        lista.add(new ReporteDTO("Error", solicitudDAO.CantidadDeSolicitudPorTipo(annio, mes, "E")));
        lista.add(new ReporteDTO("Capacitación", solicitudDAO.CantidadDeSolicitudPorTipo(annio, mes, "C")));
        lista.add(new ReporteDTO("Requerimiento", solicitudDAO.CantidadDeSolicitudPorTipo(annio, mes, "R")));
        return lista;
    }

    public List<SolicitudDTO> ListarMisSolicitudesFinalizadas(int idUsuario) {
        List<SolicitudDTO> lista = new ArrayList<>();

        List<Solicitud> solicitudes = solicitudDAO.ListarMisSolicitudesFinalizadas(idUsuario);

        for (Solicitud solicitud : solicitudes) {
            UsuarioDTO usuDto = new UsuarioDTO();
            SolicitudDTO dto = new SolicitudDTO();
            dto.setIdSolicitud(solicitud.getIdSolicitud());
            dto.setAnnio(solicitud.getAnnio());
            dto.setMes(solicitud.getMes());
            dto.setNumeroCorrelativo(solicitud.getNumeroCorrelativo());
            dto.setFechaHoraRegistro(solicitud.getFechaHoraRegistro());
            dto.setFechaHoraAsignacion(solicitud.getFechaHoraAsignacion());
            dto.setFechaHoraInicioAtencion(solicitud.getFechaHoraInicioAtencion());
            dto.setFechaHoraTerminoAtencion(solicitud.getFechaHoraTerminoAtencion());
            dto.setMotivo(solicitud.getMotivo());
            dto.setEstado(solicitud.getEstado());
            dto.setTipoSolicitud(solicitud.getTipoSolicitud());

            dto.CargarNombreTipoSolicitud(ResourcesUtils.listTipoSolicitud());
            dto.CargarNombreEstado(ResourcesUtils.listEstadoSolicitud());

            usuDto.setApePaterno(solicitud.getUsuario().getApePaterno());
            usuDto.setApeMaterno(solicitud.getUsuario().getApeMaterno());
            usuDto.setNombres(solicitud.getUsuario().getNombres());
            dto.setUsuario(usuDto);
            lista.add(dto);
        }
        return lista;
    }

    public List<SolicitudDTO> ListarMisSolicitudesAsignadasNoFinalizadas() {
        List<SolicitudDTO> lista = new ArrayList<>();

        List<Solicitud> solicitudes = solicitudDAO.ListarMisSolicitudesAsignadasNoFinalizadas();

        for (Solicitud solicitud : solicitudes) {
            UsuarioDTO usuDto = new UsuarioDTO();
            SolicitudDTO dto = new SolicitudDTO();
            dto.setIdSolicitud(solicitud.getIdSolicitud());
            dto.setAnnio(solicitud.getAnnio());
            dto.setMes(solicitud.getMes());
            dto.setNumeroCorrelativo(solicitud.getNumeroCorrelativo());
            dto.setFechaHoraRegistro(solicitud.getFechaHoraRegistro());
            dto.setFechaHoraAsignacion(solicitud.getFechaHoraAsignacion());
            dto.setFechaHoraInicioAtencion(solicitud.getFechaHoraInicioAtencion());
            dto.setFechaHoraTerminoAtencion(solicitud.getFechaHoraTerminoAtencion());
            dto.setMotivo(solicitud.getMotivo());
            dto.setEstado(solicitud.getEstado());
            dto.setTipoSolicitud(solicitud.getTipoSolicitud());

            dto.CargarNombreTipoSolicitud(ResourcesUtils.listTipoSolicitud());
            dto.CargarNombreEstado(ResourcesUtils.listEstadoSolicitud());

            usuDto.setApePaterno(solicitud.getUsuario().getApePaterno());
            usuDto.setApeMaterno(solicitud.getUsuario().getApeMaterno());
            usuDto.setNombres(solicitud.getUsuario().getNombres());
            dto.setUsuario(usuDto);
            lista.add(dto);
        }
        return lista;
    }

    public List<SolicitudDTO> ListarMisSolicitudesPorAtenderUsuario(int idUsuario) {
        List<SolicitudDTO> lista = new ArrayList<>();

        List<Solicitud> solicitudes = solicitudDAO.ListarMisSolicitudesPorAtenderUsuario(idUsuario);

        for (Solicitud solicitud : solicitudes) {
            UsuarioDTO usuDto = new UsuarioDTO();
            SolicitudDTO dto = new SolicitudDTO();
            dto.setIdSolicitud(solicitud.getIdSolicitud());
            dto.setAnnio(solicitud.getAnnio());
            dto.setMes(solicitud.getMes());
            dto.setNumeroCorrelativo(solicitud.getNumeroCorrelativo());
            dto.setFechaHoraRegistro(solicitud.getFechaHoraRegistro());
            dto.setFechaHoraAsignacion(solicitud.getFechaHoraAsignacion());
            dto.setFechaHoraInicioAtencion(solicitud.getFechaHoraInicioAtencion());
            dto.setFechaHoraTerminoAtencion(solicitud.getFechaHoraTerminoAtencion());
            dto.setMotivo(solicitud.getMotivo());
            dto.setEstado(solicitud.getEstado());
            dto.setTipoSolicitud(solicitud.getTipoSolicitud());

            dto.CargarNombreTipoSolicitud(ResourcesUtils.listTipoSolicitud());
            dto.CargarNombreEstado(ResourcesUtils.listEstadoSolicitud());

            usuDto.setApePaterno(solicitud.getUsuario().getApePaterno());
            usuDto.setApeMaterno(solicitud.getUsuario().getApeMaterno());
            usuDto.setNombres(solicitud.getUsuario().getNombres());
            dto.setUsuario(usuDto);
            lista.add(dto);
        }
        return lista;
    }

    public List<SolicitudDTO> ListarMisSolicitudes(int idCliente, String estado) {
        List<SolicitudDTO> lista = new ArrayList<>();

        List<Solicitud> solicitudes = solicitudDAO.ListarMisSolicitudes(idCliente, estado);

        for (Solicitud solicitud : solicitudes) {
            UsuarioDTO usuDto = new UsuarioDTO();
            SolicitudDTO dto = new SolicitudDTO();
            dto.setIdSolicitud(solicitud.getIdSolicitud());
            dto.setAnnio(solicitud.getAnnio());
            dto.setMes(solicitud.getMes());
            dto.setNumeroCorrelativo(solicitud.getNumeroCorrelativo());
            dto.setFechaHoraRegistro(solicitud.getFechaHoraRegistro());
            dto.setFechaHoraAsignacion(solicitud.getFechaHoraAsignacion());
            dto.setFechaHoraInicioAtencion(solicitud.getFechaHoraInicioAtencion());
            dto.setFechaHoraTerminoAtencion(solicitud.getFechaHoraTerminoAtencion());
            dto.setMotivo(solicitud.getMotivo());
            dto.setEstado(solicitud.getEstado());
            dto.setTipoSolicitud(solicitud.getTipoSolicitud());
            dto.CargarNombreTipoSolicitud(ResourcesUtils.listTipoSolicitud());
            dto.CargarNombreEstado(ResourcesUtils.listEstadoSolicitud());

            usuDto.setApePaterno(solicitud.getUsuario().getApePaterno());
            usuDto.setApeMaterno(solicitud.getUsuario().getApeMaterno());
            usuDto.setNombres(solicitud.getUsuario().getNombres());
            dto.setUsuario(usuDto);
            lista.add(dto);
        }
        return lista;
    }

    public List<SolicitudDTO> ListarMisSolicitudesExcluyente(int idCliente, String estado) {
        List<SolicitudDTO> lista = new ArrayList<>();

        List<Solicitud> solicitudes = solicitudDAO.ListarMisSolicitudesExcluyente(idCliente, estado);

        for (Solicitud solicitud : solicitudes) {
            UsuarioDTO usuDto = new UsuarioDTO();
            SolicitudDTO dto = new SolicitudDTO();
            dto.setIdSolicitud(solicitud.getIdSolicitud());
            dto.setAnnio(solicitud.getAnnio());
            dto.setMes(solicitud.getMes());
            dto.setNumeroCorrelativo(solicitud.getNumeroCorrelativo());
            dto.setFechaHoraRegistro(solicitud.getFechaHoraRegistro());
            dto.setFechaHoraAsignacion(solicitud.getFechaHoraAsignacion());
            dto.setFechaHoraInicioAtencion(solicitud.getFechaHoraInicioAtencion());
            dto.setFechaHoraTerminoAtencion(solicitud.getFechaHoraTerminoAtencion());
            dto.setMotivo(solicitud.getMotivo());
            dto.setEstado(solicitud.getEstado());
            dto.setTipoSolicitud(solicitud.getTipoSolicitud());
            dto.CargarNombreTipoSolicitud(ResourcesUtils.listTipoSolicitud());
            dto.CargarNombreEstado(ResourcesUtils.listEstadoSolicitud());

            usuDto.setApePaterno(solicitud.getUsuario().getApePaterno());
            usuDto.setApeMaterno(solicitud.getUsuario().getApeMaterno());
            usuDto.setNombres(solicitud.getUsuario().getNombres());
            lista.add(dto);
        }
        return lista;
    }

    public List<SolicitudDTO> ListarMisSolicitudesPendientes() {
        List<SolicitudDTO> lista = new ArrayList<>();

        List<Solicitud> solicitudes = solicitudDAO.ListarMisSolicitudesPendientes();

        for (Solicitud solicitud : solicitudes) {
            UsuarioDTO usuDto = new UsuarioDTO();
            SolicitudDTO dto = new SolicitudDTO();
            dto.setIdSolicitud(solicitud.getIdSolicitud());
            dto.setAnnio(solicitud.getAnnio());
            dto.setMes(solicitud.getMes());
            dto.setNumeroCorrelativo(solicitud.getNumeroCorrelativo());
            dto.setFechaHoraRegistro(solicitud.getFechaHoraRegistro());
            dto.setFechaHoraAsignacion(solicitud.getFechaHoraAsignacion());
            dto.setFechaHoraInicioAtencion(solicitud.getFechaHoraInicioAtencion());
            dto.setFechaHoraTerminoAtencion(solicitud.getFechaHoraTerminoAtencion());
            dto.setMotivo(solicitud.getMotivo());
            dto.setEstado(solicitud.getEstado());
            dto.setTipoSolicitud(solicitud.getTipoSolicitud());

            dto.CargarNombreTipoSolicitud(ResourcesUtils.listTipoSolicitud());
            dto.CargarNombreEstado(ResourcesUtils.listEstadoSolicitud());

            usuDto.setApePaterno(solicitud.getUsuario().getApePaterno());
            usuDto.setApeMaterno(solicitud.getUsuario().getApeMaterno());
            usuDto.setNombres(solicitud.getUsuario().getNombres());
            dto.setUsuario(usuDto);
            lista.add(dto);
        }
        return lista;
    }

    public String Guardar(SolicitudDTO dto) {
        dto.setNumeroCorrelativo(solicitudDAO.ObtenerCorrelativo(dto.getAnnio(), dto.getMes()));
        return solicitudDAO.Guardar(dto);
    }

    public String Eliminar(int id) throws Exception {
        return solicitudDAO.Eliminar(id);
    }

    public String FinalizarSolicitud(SolicitudDTO dto) {
        String msg = solicitudDAO.FinalizarSolicitud(dto);

        if (msg.equals("OK")) {
            Usuario usuario = usuarioDAO.BuscarPorIdSolicitud(dto.getIdSolicitud());

            String asunto = "Finalización Solicitud";
            String cuerpo = PlantillaCorreo.FinalizarSolicitud(dto, usuario.nombreCompleto());

            CorreoUtils obj = new CorreoUtils();
            obj.EnviarCorreo(asunto, cuerpo, usuario.getCorreo());
        }

        return msg;
    }
}
