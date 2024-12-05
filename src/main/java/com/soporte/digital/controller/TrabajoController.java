package com.soporte.digital.controller;

import com.google.gson.Gson;
import com.soporte.digital.model.dto.TrabajoDTO;
import com.soporte.digital.model.dto.UsuarioDTO;
import com.soporte.digital.model.service.TrabajoFacade;
import com.soporte.digital.model.service.UsuarioFacade;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "TrabajoController", urlPatterns = {"/trabajo"})
public class TrabajoController extends HttpServlet {

    private UsuarioFacade usuarioFacade;
    private TrabajoFacade trabajoFacade;

    @Override
    public void init(ServletConfig config) throws ServletException {
        trabajoFacade = new TrabajoFacade();
        usuarioFacade = new UsuarioFacade();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String accion = request.getParameter("accion");

        switch (accion) {
            case "guardar":
                guardar(request, response);
                break;
            case "terminar":
                terminar(request, response);
                break;
            case "listarPorAtender":
                listarPorAtender(request, response);
                break;
            case "listarPorAtenderJson":
                listarPorAtenderJson(request, response);
                break;
        }
    }

    protected void listarPorAtenderJson(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HashMap<String, Object> data = new HashMap<>();

        int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));

        List<TrabajoDTO> lista = trabajoFacade.ListarTrabajoPorSolicitud(idSolicitud);

        out.print(new Gson().toJson(lista));
    }

    private void terminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HashMap<String, Object> data = new HashMap<>();
        try {
            UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
            TrabajoDTO dto = new TrabajoDTO();
            dto.setIdSolicitud(Integer.parseInt(request.getParameter("solicitudId")));
            dto.setIdTrabajo(Integer.parseInt(request.getParameter("trabajoId")));
            dto.setUsuario(usuario);

            String result = trabajoFacade.Terminar(dto);

            data.put("msg", result);
        } catch (Exception ex) {
            data.put("msg", ex.getMessage());
        }

        out.print(new Gson().toJson(data));
    }

    protected void listarPorAtender(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud"));

        List<TrabajoDTO> lista = trabajoFacade.ListarTrabajoPorSolicitud(idSolicitud);
        request.setAttribute("trabajos", lista);
        request.getRequestDispatcher("template/vistaTrabajoPorAtender.jsp").forward(request, response);
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HashMap<String, Object> data = new HashMap<>();
        try {
            UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
            TrabajoDTO dto = new TrabajoDTO();
            dto.setIdSolicitud(Integer.parseInt(request.getParameter("solicitudId")));
            dto.setUsuario(usuario);
            dto.setDescripcion(request.getParameter("descripcion"));

            String result = trabajoFacade.Guardar(dto);

            data.put("msg", result);
        } catch (Exception ex) {
            data.put("msg", ex.getMessage());
        }

        out.print(new Gson().toJson(data));
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
