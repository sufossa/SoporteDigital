<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Registro de Usuario</title>
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
        <div class="container-scroller">
            <div class="container-fluid page-body-wrapper full-page-wrapper">
                <div class="content-wrapper d-flex align-items-center auth px-0 fondo">
                    <div class="row w-100 mx-0">
                        <div class="col-lg-8 mx-auto">
                            <div class="auth-form-light text-left py-5 px-4 px-sm-5">
                                <div class="brand-logo text-center">
                                    <img src="assets/img/logo.png" alt=""/>
                                </div>

                                <h4>Nueva Cuenta</h4>
                                <hr />
                                <form action="auth" method="POST" class="pt-3">
                                    <jsp:include page="includes/Mensaje.jsp" />

                                    <div class="row">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Tipo de Documento: <span style="color: red;">(*)</span></label>
                                                <select class="form-control form-control-lg" id="tipo_doc_iden" name="tipo_doc_iden" required="">
                                                    <option value="">:::Seleccione:::</option>
                                                    <c:forEach var="item" items="${tipoDocumentos}">
                                                        <option value="${item.id}" ${usuario.tipoDoc == item.id ? "selected" : ""}>${item.descripcion}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Número de Documento: <span style="color: red;">(*)</span></label>
                                                <input value="${usuario.numeroDoc}" type="text" class="form-control form-control-lg"
                                                       id="numero_doc" name="numero_doc" placeholder="Número de Documento" required=""
                                                       maxlength="15" minlength="8">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Nombres: <span style="color: red;">(*)</span></label>
                                                <input value="${usuario.nombres}" type="text" class="form-control form-control-lg"
                                                       id="nombres" name="nombres" placeholder="Nombres" required="" maxlength="50">
                                            </div>
                                        </div>

                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Apellido Paterno: <span style="color: red;">(*)</span></label>
                                                <input value="${usuario.apePaterno}" type="text" class="form-control form-control-lg" 
                                                       id="ape_paterno" name="ape_paterno" placeholder="Apellido Paterno" required="" 
                                                       maxlength="50">
                                            </div>
                                        </div>

                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Apellido Materno: <span style="color: red;">(*)</span></label>
                                                <input value="${usuario.apeMaterno}" type="text" class="form-control form-control-lg"
                                                       id="ape_materno" name="ape_materno" placeholder="Apellido Materno" required=""
                                                       maxlength="50">
                                            </div>
                                        </div>

                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Razón Social: <span style="color: red;">(*)</span></label>
                                                <input value="${usuario.cliente.razonSocial}" type="text" 
                                                       class="form-control form-control-lg" id="razon_social" name="razon_social" 
                                                       placeholder="Razón Social" required="" maxlength="50">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Dirección: <span style="color: red;">(*)</span></label>
                                                <input value="${usuario.cliente.direccion}" type="text" class="form-control form-control-lg"
                                                       id="direccion" name="direccion" placeholder="Dirección" required="" maxlength="50">
                                            </div>
                                        </div>
                                            
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Teléfono: </label>
                                                <input value="${usuario.cliente.telefono}" type="text" class="form-control form-control-lg" 
                                                       d="telefono" name="telefono" placeholder="Teléfono" maxlength="9">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Correo: <span style="color: red;">(*)</span></label>
                                                <input value="${usuario.correo}" type="email" class="form-control form-control-lg"
                                                       id="correo" name="correo" placeholder="Correo" required="" maxlength="50">
                                            </div>
                                        </div>

                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>Contraseña: <span style="color: red;">(*)</span></label>
                                                <input value="${cliente.password}" type="password" class="form-control form-control-lg"
                                                       id="password" name="password" placeholder="Contraseña" required="" maxlength="6">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="mt-3">
                                        <input type="hidden" name="accion" value="register">
                                        <button type="submit" class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">Registrar</button>
                                    </div>

                                    <div class="mt-4 text-center">
                                        <p>Ya tienes una cuenta? <a href="index.jsp" class="text-primary">Iniciar sesión aquí</a></p>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <script src="assets/vendors/base/vendor.bundle.base.js"></script>
            <script src="assets/js/off-canvas.js"></script>
            <script src="assets/js/hoverable-collapse.js"></script>
            <script src="assets/js/template.js"></script>
    </body>

</html>
