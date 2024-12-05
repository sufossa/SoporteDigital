package com.soporte.digital.controller;

import com.google.gson.Gson;
import com.soporte.digital.model.dto.AsignacionDTO;
import com.soporte.digital.model.dto.SolicitudDTO;
import com.soporte.digital.model.dto.UsuarioDTO;
import com.soporte.digital.model.service.AsignacionFacade;
import com.soporte.digital.model.service.SolicitudFacade;
import com.soporte.digital.model.service.TipoSolicitudFacade;
import com.soporte.digital.model.service.UsuarioFacade;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "SolicitudController", urlPatterns = {"/solicitud"})
public class SolicitudController extends HttpServlet {

    private UsuarioFacade usuarioFacade;
    private TipoSolicitudFacade tipoSolicitudFacade;
    private SolicitudFacade solicitudFacade;
    private AsignacionFacade asignacionFacade;

    @Override
    public void init(ServletConfig config) throws ServletException {
        tipoSolicitudFacade = new TipoSolicitudFacade();
        solicitudFacade = new SolicitudFacade();
        usuarioFacade = new UsuarioFacade();
        asignacionFacade = new AsignacionFacade();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        if (request.getSession().getAttribute("usuario") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String accion = request.getParameter("accion");

        switch (accion) {
            case "nuevo":
                nuevo(request, response);
                break;
            case "guardar":
                guardar(request, response);
                break;
            case "listar":
                listar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            case "asignar":
                asignar(request, response);
                break;
            case "guardarAsignacion":
                guardarAsignacion(request, response);
                break;
            case "guardarAsignacionJSON":
                guardarAsignacionJSON(request, response);
                break;
            case "no_finalizadas":
                listarNoFinalizadas(request, response);
                break;
            case "listColaborador":
                listJSONColaborador(request, response);
                break;
            case "asignarCoordinador":
                asignarCoordinador(request, response);
                break;
            case "por_atender":
                SolicitudesPorAtender(request, response);
                break;
            case "por_atender_vista":
                SolicitudesPorAtenderVista(request, response);
                break;
            case "finalizarSolicitud":
                FinalizarSolicitud(request, response);
                break;
            case "finalizadas":
                listarFinalizadas(request, response);
                break;
            case "estadistica":
                estadistica(request, response);
                break;
            case "obtener_reporte_estadistica":
                obtenerReporteEstadistica(request, response);
                break;
        }
    }

    private void obtenerReporteEstadistica(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HashMap<String, Object> data = new HashMap<>();
        try {
            int annio = Integer.parseInt(request.getParameter("annio"));
            int mes = Integer.parseInt(request.getParameter("mes"));

            data.put("cantidad_estados", solicitudFacade.ListarCantidadSolicitudPorEstado(annio, mes));
            data.put("cantidad_tipos", solicitudFacade.ListarCantidadSolicitudPorTipo(annio, mes));
            data.put("msg", "OK");
        } catch (Exception ex) {
            data.put("msg", ex.getMessage());
        }

        out.print(new Gson().toJson(data));
    }

    protected void estadistica(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.getRequestDispatcher("pagEstadistica.jsp").forward(request, response);
    }

    protected void listarFinalizadas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        int idUsuario = usuario.getTipoUsuario().equals("S") ? 0 : usuario.getIdUsuario();
        List<SolicitudDTO> lista = solicitudFacade.ListarMisSolicitudesFinalizadas(idUsuario);

        request.setAttribute("solicitudes", lista);
        request.getRequestDispatcher("pagSolicitudFinalizadas.jsp").forward(request, response);
    }

    private void FinalizarSolicitud(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HashMap<String, Object> data = new HashMap<>();
        try {
            UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
            SolicitudDTO dto = new SolicitudDTO();
            dto.setIdSolicitud(Integer.parseInt(request.getParameter("solicitudId")));
            dto.setUsuario(usuario);

            String result = solicitudFacade.FinalizarSolicitud(dto);

            data.put("msg", result);
        } catch (Exception ex) {
            data.put("msg", ex.getMessage());
        }

        out.print(new Gson().toJson(data));
    }

    protected void SolicitudesPorAtenderVista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");

        request.setAttribute("solicitudes", solicitudFacade.ListarMisSolicitudesPorAtenderUsuario(usuario.getIdUsuario()));
        request.getRequestDispatcher("template/vistaSolicitudPorAtender.jsp").forward(request, response);
    }

    protected void SolicitudesPorAtender(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        request.setAttribute("colaboradores", usuarioFacade.ListarColaboradores());

        request.getRequestDispatcher("pagSolicitudPorAtender.jsp").forward(request, response);
    }

    private void asignarCoordinador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HashMap<String, Object> data = new HashMap<>();
        try {
            AsignacionDTO dto = new AsignacionDTO();
            dto.setIdSolicitud(Integer.parseInt(request.getParameter("solicitudId")));
            dto.setIdUsuario(Integer.parseInt(request.getParameter("usuarioId")));
            dto.setEsCoordinador(true);

            String result = asignacionFacade.AsignarCoordinador(dto);

            data.put("msg", result);
        } catch (Exception ex) {
            data.put("msg", ex.getMessage());
        }

        out.print(new Gson().toJson(data));
    }

    protected void listJSONColaborador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));
        List<UsuarioDTO> lista = usuarioFacade.ListarTodosPorSolicitud(idSolicitud);
        out.print(new Gson().toJson(lista));
    }

    protected void listarNoFinalizadas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("colaboradores", usuarioFacade.ListarColaboradores());
        request.setAttribute("solicitudes", solicitudFacade.ListarMisSolicitudesAsignadasNoFinalizadas());
        request.getRequestDispatcher("pagSolicitudNoFinalizadas.jsp").forward(request, response);
    }

    private void guardarAsignacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            AsignacionDTO dto = new AsignacionDTO();
            dto.setIdSolicitud(Integer.parseInt(request.getParameter("solicitudId")));
            dto.setIdUsuario(Integer.parseInt(request.getParameter("colaboradorId")));
            dto.setEsCoordinador(false);

            String result = asignacionFacade.Guardar(dto);

            if (result.equals("OK")) {
                request.getSession().setAttribute("success", "La solicitud fue asignada satisfactoriamente!");
            } else {
                request.getSession().setAttribute("error", result);
            }
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }

        response.sendRedirect("solicitud?accion=asignar");
    }

    private void guardarAsignacionJSON(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HashMap<String, Object> data = new HashMap<>();
        try {
            AsignacionDTO dto = new AsignacionDTO();
            dto.setIdSolicitud(Integer.parseInt(request.getParameter("solicitudId")));
            dto.setIdUsuario(Integer.parseInt(request.getParameter("colaboradorId")));
            dto.setEsCoordinador(false);

            String result = asignacionFacade.Guardar(dto);

            data.put("msg", result);
        } catch (Exception ex) {
            data.put("msg", ex.getMessage());
        }

        out.print(new Gson().toJson(data));
    }

    protected void asignar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("colaboradores", usuarioFacade.ListarColaboradoresActivos());
        request.setAttribute("solicitudes_pendientes", solicitudFacade.ListarMisSolicitudesPendientes());
        request.getRequestDispatcher("pagAsignarSolicitud.jsp").forward(request, response);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            String result = solicitudFacade.Eliminar(id);

            if (result.equals("OK")) {
                request.getSession().setAttribute("success", "Solicitud con id " + id + " eliminado!");
            } else {
                request.getSession().setAttribute("error", result);
            }
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }

        response.sendRedirect("solicitud?accion=listar");
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        SolicitudDTO obj = new SolicitudDTO();
        try {
            Calendar calendar = Calendar.getInstance();

            obj.setIdSolicitud(Integer.parseInt(request.getParameter("idSolicitud")));
            obj.setMotivo(request.getParameter("motivo"));
            obj.setTipoSolicitud(request.getParameter("tipoSolicitud"));
            obj.setAnnio(calendar.get(Calendar.YEAR));
            obj.setMes(calendar.get(Calendar.MONTH) + 1);
            obj.setEstado("P");
            obj.setUsuario(usuario);

            String result = "";

            result = solicitudFacade.Guardar(obj);

            if (result.equals("OK")) {
                request.getSession().setAttribute("success", obj.getIdSolicitud() == 0 ? "Solicitud registrado." : "Solicitud actualizado!");
                response.sendRedirect("solicitud?accion=listar");
                return;
            } else {
                request.getSession().setAttribute("error", result);
            }
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }

        request.setAttribute("solicitud", obj);
        request.setAttribute("tipoSolicitudes", tipoSolicitudFacade.ListarTodos());
        request.getRequestDispatcher("pagNuevaSolicitud.jsp").forward(request, response);
    }

    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");

        int idCliente = usuario.getIdUsuario();
        request.setAttribute("solicitudes_pendientes", solicitudFacade.ListarMisSolicitudesExcluyente(idCliente, "T"));
        request.setAttribute("solicitudes_atendidas", solicitudFacade.ListarMisSolicitudes(idCliente, "T"));
        request.getRequestDispatcher("pagGestSolicitudes.jsp").forward(request, response);
    }

    private void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("solicitud", new SolicitudDTO());
        request.setAttribute("tipoSolicitudes", tipoSolicitudFacade.ListarTodos());
        request.getRequestDispatcher("pagNuevaSolicitud.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
