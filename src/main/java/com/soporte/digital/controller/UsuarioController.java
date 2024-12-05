package com.soporte.digital.controller;

import com.soporte.digital.model.dto.UsuarioDTO;
import com.soporte.digital.model.service.UsuarioFacade;
import com.soporte.digital.model.service.RolFacade;
import com.soporte.digital.model.service.TipoDocumentoFacade;
import com.soporte.digital.util.ConstantesApp;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UsuarioController", urlPatterns = {"/usuario"})
public class UsuarioController extends HttpServlet {

    private TipoDocumentoFacade tipoDocumentoFacade;
    private UsuarioFacade usuarioFacade;
    private RolFacade rolFacade;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usuarioFacade = new UsuarioFacade();
        rolFacade = new RolFacade();
        tipoDocumentoFacade = new TipoDocumentoFacade();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "listar":
                listar(request, response);
                break;
            case "nuevo":
                nuevo(request, response);
                break;
            case "guardar":
                guardar(request, response);
                break;
            case "editar":
                editar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
        }

    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            String result = usuarioFacade.Eliminar(id);

            if (result.equals("OK")) {
                request.getSession().setAttribute("success", "Colaborador con id " + id + " eliminado!");
            } else {
                request.getSession().setAttribute("error", result);
            }
        } catch (Exception ex) {
              request.getSession().setAttribute("error", ex.getMessage());
        }
        
        response.sendRedirect("usuario?accion=listar");
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        UsuarioDTO obj = usuarioFacade.BuscarPorId(id);

        if (obj != null) {
            request.setAttribute("usuario", obj);
            request.setAttribute("roles", rolFacade.ListarRolesColaboradores());
            request.setAttribute("tipoDocumentos", tipoDocumentoFacade.ListarTipoDocumentosColaborador());
            request.getRequestDispatcher("pagNuevoColaborador.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("error", "No se encontró colaborador con ID " + id);
            response.sendRedirect("usuario?accion=listar");
        }
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDTO obj = new UsuarioDTO();
        try {

            obj.setTipoDoc(request.getParameter("tipo_doc_iden"));
            obj.setNumeroDoc(request.getParameter("numero_doc"));
            obj.setNombres(request.getParameter("nombres"));
            obj.setApePaterno(request.getParameter("apePaterno"));
            obj.setApeMaterno(request.getParameter("apeMaterno"));
            obj.setCorreo(request.getParameter("correo"));
            obj.setPassword(request.getParameter("password"));
            obj.setIdRol(Integer.parseInt(request.getParameter("id_rol")));
            obj.setEstado(Integer.parseInt(request.getParameter("estado")));
            obj.setIdUsuario(Integer.parseInt(request.getParameter("id")));
            obj.setTipoUsuario(obj.getIdRol() == ConstantesApp.ROL_ADMIN ? "S" : "E");

            String result = "";

            if (obj.getIdUsuario() == 0) {
                result = usuarioFacade.Guardar(obj);
            } else {
                result = usuarioFacade.Editar(obj);
            }

            if (result.equals("OK")) {
                request.getSession().setAttribute("success", obj.getIdUsuario() == 0 ? "Colaborador registrado." : "Colaborador actualizado!");
                response.sendRedirect("usuario?accion=listar");
                return;
            } else {
                request.getSession().setAttribute("error", result);
            }
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }

        request.setAttribute("usuario", obj);
        request.setAttribute("roles", rolFacade.ListarRolesColaboradores());
        request.setAttribute("tipoDocumentos", tipoDocumentoFacade.ListarTipoDocumentosColaborador());
        request.getRequestDispatcher("pagNuevoColaborador.jsp").forward(request, response);
    }

    private void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("usuario", new UsuarioDTO());
        request.setAttribute("roles", rolFacade.ListarRolesColaboradores());
        request.setAttribute("tipoDocumentos", tipoDocumentoFacade.ListarTipoDocumentosColaborador());
        request.getRequestDispatcher("pagNuevoColaborador.jsp").forward(request, response);
    }

    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("lista", usuarioFacade.ListarTodos());
        request.getRequestDispatcher("pagGestColaborador.jsp").forward(request, response);
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
