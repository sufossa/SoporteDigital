package com.soporte.digital.controller;

import com.soporte.digital.model.dto.ClienteDTO;
import com.soporte.digital.model.dto.UsuarioDTO;
import com.soporte.digital.model.service.AuthFacade;
import com.soporte.digital.model.service.ClienteFacade;
import com.soporte.digital.model.service.TipoDocumentoFacade;
import com.soporte.digital.util.ConstantesApp;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AuthController", urlPatterns = {"/auth"})
public class AuthController extends HttpServlet {

    private AuthFacade authFacade;
    private TipoDocumentoFacade tipoDocumentoFacade;
    private ClienteFacade clienteFacade;

    @Override
    public void init(ServletConfig config) throws ServletException {
        authFacade = new AuthFacade();
        tipoDocumentoFacade = new TipoDocumentoFacade();
        clienteFacade = new ClienteFacade();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "login":
                Login(request, response);
                break;
            case "autentificarse":
                Autentificarse(request, response);
                break;
            case "logout":
                Logout(request, response);
                break;
            case "new_account":
                NewAccount(request, response);
                break;
            case "register":
                Register(request, response);
                break;
        }

    }

    protected void Register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDTO UsuDto = new UsuarioDTO();
        ClienteDTO cliDto = new ClienteDTO();

        try {
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

            String msg = clienteFacade.Registrar(UsuDto);

            if (msg.equals("OK")) {
                request.getSession().setAttribute("success", "En hora buena! Su cuenta se ha creado de forma correcta.");
                response.sendRedirect("auth?accion=new_account");
                return;
            } else {
                request.getSession().setAttribute("error", msg);
            }

        } catch (Exception ex) {
            request.getSession().setAttribute("error", "Lo sentimos! No se pudo registrar cuenta: " + ex.getMessage());
        }
        request.setAttribute("usuario", UsuDto);
        request.setAttribute("tipoDocumentos", tipoDocumentoFacade.ListarTipoDocumentosCliente());
        request.getRequestDispatcher("registro.jsp").forward(request, response);
    }

    protected void NewAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("cliente", new UsuarioDTO());
        request.setAttribute("tipoDocumentos", tipoDocumentoFacade.ListarTipoDocumentosCliente());
        request.getRequestDispatcher("registro.jsp").forward(request, response);
    }

    protected void Autentificarse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        UsuarioDTO obj = authFacade.Login(correo, contrasena);

        if (obj != null) {
            if (obj.getEstado() == 1) {
                request.getSession().setAttribute("usuario", obj);
                request.getSession().setAttribute("tipoUsuario", obj.getTipoUsuario());

                if (obj.getIdRol() != ConstantesApp.ROL_CLIENTE) {
                    if (obj.getTipoUsuario().equalsIgnoreCase("S")) {
                        response.sendRedirect("solicitud?accion=asignar");
                    } else {
                        response.sendRedirect("solicitud?accion=por_atender");
                    }

                } else {
                    response.sendRedirect("solicitud?accion=listar");
                }

                return;
            } else {
                request.getSession().setAttribute("error", "Lo sentimos! Su cuenta se encuentra inactiva. Por favor comuniquese con el administrador.");
            }
        } else {
            request.getSession().setAttribute("error", "Correo y/o contraseña incorrecto");
        }

        request.setAttribute("correo", correo);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void Login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void Logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getSession().removeAttribute("usuario");
        response.sendRedirect("index.jsp");
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
