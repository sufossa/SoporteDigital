<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Gestión de Clientes</title>
        <jsp:include page="includes/Css.jsp" />
    </head>

    <body>
        <div class="container-scroller">
            <jsp:include page="includes/Navegacion.jsp" />

            <div class="container-fluid page-body-wrapper">
                <jsp:include page="includes/Aside.jsp" />

                <div class="main-panel">        
                    <div class="content-wrapper">
                        <div class="row">
                            <div class="col-md-12 grid-margin stretch-card">
                                <div class="container mt-3">
                                    <div class="card">
                                        <div class="card-body">
                                            <h4>${usuario.idUsuario == 0 ? "Nuevo" : "Editar"} Cliente</h4>
                                            <hr />

                                            <form action="cliente" method="post">
                                                <jsp:include page="includes/Mensaje.jsp" />

                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <div class="form-group">
                                                            <label>Tipo de Documento: <span style="color: red;">(*)</span></label>
                                                            <select class="form-control form-control-lg" id="tipo_doc_iden" name="tipo_doc_iden" required="">
                                                                <option value="">:::Seleccione:::</option>
                                                                <c:forEach var="item" items="${tipoDocumentos}">
                                                                    <option value="${item.id}" ${usuario.cliente.tipoDoc == item.id ? "selected" : ""}>${item.descripcion}</option>
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
                                                                   id="ape_paterno" name="ape_paterno" placeholder="Apellido Paterno" required="" maxlength="50">
                                                        </div>
                                                    </div>

                                                    <div class="col-sm-6">
                                                        <div class="form-group">
                                                            <label>Apellido Materno: <span style="color: red;">(*)</span></label>
                                                            <input value="${usuario.apeMaterno}" type="text" class="form-control form-control-lg"
                                                                   id="ape_materno" name="ape_materno" placeholder="Apellido Materno" required="" maxlength="50">
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
                                                            <input value="${usuario.cliente.telefono}" type="text" 
                                                                   class="form-control form-control-lg" id="telefono" 
                                                                   name="telefono" placeholder="Teléfono" maxlength="9">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <div class="form-group">
                                                            <label>Correo: <span style="color: red;">(*)</span></label>
                                                            <input value="${usuario.correo}" type="email" class="form-control form-control-lg" id="correo" name="correo" 
                                                                   placeholder="Correo" required="" maxlength="50">
                                                        </div>
                                                    </div>

                                                    <div class="col-sm-6">
                                                        <div class="form-group">
                                                            <label>Contraseña: <span style="color: red;">(*)</span></label>
                                                            <input value="${usuario.password}" type="password" class="form-control form-control-lg" id="password" 
                                                                   name="password" placeholder="Contraseña" required="" maxlength="6">
                                                        </div>
                                                    </div>
                                                </div>


                                                <div class="mb-3">
                                                    <input type="hidden" name="id" value="${usuario.idUsuario}">
                                                    <input type="hidden" name="accion" value="guardar">
                                                    <button class="btn btn-primary btn-sm">
                                                        <i class="fa fa-save"></i> Guardar
                                                    </button>
                                                    <a href="cliente?accion=listar" 
                                                       class="btn btn-dark btn-sm">
                                                        <i class="fa fa-arrow-left"></i> Volver atrás
                                                    </a>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <jsp:include page="includes/Js.jsp" />
    </body>
</html>
