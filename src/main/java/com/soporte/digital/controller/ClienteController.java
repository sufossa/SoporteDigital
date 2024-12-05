package com.soporte.digital.controller;

import com.soporte.digital.model.dto.ClienteDTO;
import com.soporte.digital.model.dto.UsuarioDTO;
import com.soporte.digital.model.service.ClienteFacade;
import com.soporte.digital.model.service.RolFacade;
import com.soporte.digital.model.service.TipoDocumentoFacade;
import com.soporte.digital.model.service.UsuarioFacade;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ClienteController", urlPatterns = {"/cliente"})
public class ClienteController extends HttpServlet {

    private ClienteFacade clienteFacade;
    private TipoDocumentoFacade tipoDocumentoFacade;
    private UsuarioFacade usuarioFacade;
    private RolFacade rolFacade;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usuarioFacade = new UsuarioFacade();
        rolFacade = new RolFacade();
        tipoDocumentoFacade = new TipoDocumentoFacade();
        clienteFacade = new ClienteFacade();
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
            String result = clienteFacade.Eliminar(id);

            if (result.equals("OK")) {
                request.getSession().setAttribute("success", "Cliente con id " + id + " eliminado!");
            } else {
                request.getSession().setAttribute("error", result);
            }
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }

        response.sendRedirect("cliente?accion=listar");
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        UsuarioDTO obj = clienteFacade.BuscarPorId(id);

        if (obj != null) {
            request.setAttribute("usuario", obj);
            request.setAttribute("tipoDocumentos", tipoDocumentoFacade.ListarTipoDocumentosCliente());
            request.getRequestDispatcher("pagNuevoCliente.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("error", "No se encontró cliente con ID " + id);
            response.sendRedirect("cliente?accion=listar");
        }
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDTO UsuDto = new UsuarioDTO();
        ClienteDTO cliDto = new ClienteDTO();

        try {
            UsuDto.setIdUsuario(Integer.parseInt(request.getParameter("id")));
            UsuDto.setApePaterno(request.getParameter("ape_paterno"));
            UsuDto.setApeMaterno(request.getParameter("ape_materno"));
            UsuDto.setNombres(request.getParameter("nombres"));

            UsuDto.setCorreo(request.getParameter("correo"));
            UsuDto.setPassword(request.getParameter("password"));
            UsuDto.setTipoDoc(request.getParameter("tipo_doc_iden"));
            UsuDto.setNumeroDoc(request.getParameter("numero_doc"));
            cliDto.setTelefono(request.getParameter("telefono"));
            cliDto.setDireccion(request.getParameter("direccion"));
            cliDto.setRazonSocial(request.getParameter("razon_social"));
            UsuDto.setCliente(cliDto);

            String msg = "";

            if (UsuDto.getIdUsuario() == 0) {
                msg = clienteFacade.Registrar(UsuDto);
            } else {
                msg = clienteFacade.Editar(UsuDto);
            }

            if (msg.equals("OK")) {
                request.getSession().setAttribute("success", UsuDto.getIdUsuario() == 0 ? "Cliente registrado.!" : "Cliente actualizado.!");
                response.sendRedirect("cliente?accion=listar");
                return;
            } else {
                request.getSession().setAttribute("error", msg);
            }

        } catch (Exception ex) {
            request.getSession().setAttribute("error", "Lo sentimos! No se pudo registrar cuenta: " + ex.getMessage());
        }
        request.setAttribute("usuario", UsuDto);
        request.setAttribute("tipoDocumentos", tipoDocumentoFacade.ListarTipoDocumentosCliente());
        request.getRequestDispatcher("pagNuevoCliente.jsp").forward(request, response);
    }

    private void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("usuario", new UsuarioDTO());
        request.setAttribute("tipoDocumentos", tipoDocumentoFacade.ListarTipoDocumentosCliente());
        request.getRequestDispatcher("pagNuevoCliente.jsp").forward(request, response);
    }

    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("lista", clienteFacade.ListarTodos());
        request.getRequestDispatcher("pagGestCliente.jsp").forward(request, response);
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
