<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Login</title>
        <link rel="stylesheet" href="assets/vendors/mdi/css/materialdesignicons.min.css">
        <link rel="stylesheet" href="assets/vendors/base/vendor.bundle.base.css">
        <link rel="stylesheet" href="assets/css/style.css">
    </head>
    <style>
        .fondo {
            background: linear-gradient(90deg, #00d2ff 0%, #7f9fee 100%);
        }
    </style>
    <body>
        <div class="container-scroller ">
            <div class="container-fluid page-body-wrapper full-page-wrapper ">
                <div class="content-wrapper d-flex align-items-center auth px-0 fondo">
                    <div class="row w-100 mx-0 ">
                        <div class="col-lg-4 mx-auto">
                            <div class="auth-form-light text-left py-5 px-4 px-sm-5">
                                <div class="brand-logo text-center">
                                    <img src="assets/img/logo.png" alt=""/>
                                </div>
                                <jsp:include page="includes/Mensaje.jsp" />
                                <form class="pt-3" action="auth">
                                    <div class="form-group"> 
                                        <input  type="email" class="form-control form-control-lg" id="correo" name="correo" 
                                                placeholder="Correo" required="" value="${correo}">
                                    </div>
                                    <div class="form-group">
                                        <input value="" type="password" class="form-control form-control-lg" id="contrasena" name="contrasena" placeholder="Contraseña" required="">
                                    </div>
                                    <div class="mt-3">
                                        <input type="hidden" name="accion" value="autentificarse">
                                        <button class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn" >Iniciar Sesión</button>
                                    </div>
                                <!--
                                    <div class="mt-4 text-center">
                                        <p>No tienes una cuenta? <a href="auth?accion=new_account" class="text-primary">Regístrate aquí</a></p>
                                    </div>
                                --->
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="assets/vendors/base/vendor.bundle.base.js"></script>
        <script src="assets/js/off-canvas.js"></script>
        <script src="assets/js/hoverable-collapse.js"></script>
        <script src="assets./js/template.js"></script>
    </body>

</html>