<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Gestión de Usuario</title>
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
                                            <h4>${usuario.idUsuario == 0 ? "Nuevo" : "Editar"} Usuario</h4>
                                            <hr />

                                            <form action="usuario" method="post">
                                                <jsp:include page="includes/Mensaje.jsp" />

                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <div class="mb-3">
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
                                                        <div class="mb-3">
                                                            <label>Número de Documento: <span style="color: red;">(*)</span></label>
                                                            <input value="${usuario.numeroDoc}" type="text" class="form-control form-control-lg"
                                                                   id="numero_doc" name="numero_doc" placeholder="" required="" maxlength="15"
                                                                   minlength="8">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <div class="mb-3">
                                                            <label>Nombres: <span style="color: red;">(*)</span></label>
                                                            <input value="${usuario.nombres}" name="nombres" type="text" 
                                                                   class="form-control" required="" maxlength="50">
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-3">
                                                        <div class="mb-3">
                                                            <label>Apellido Paterno: <span style="color: red;">(*)</span></label>
                                                            <input value="${usuario.apePaterno}" name="apePaterno" type="text" 
                                                                   class="form-control" required="" maxlength="50">
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-3">
                                                        <div class="mb-3">
                                                            <label>Apellido Materno: <span style="color: red;">(*)</span></label>
                                                            <input value="${usuario.apeMaterno}" name="apeMaterno" type="text" 
                                                                   class="form-control" required="" maxlength="50">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <div class="mb-3">
                                                            <label>Correo: <span style="color: red;">(*)</span></label>
                                                            <input value="${usuario.correo}" name="correo" type="email" 
                                                                   class="form-control" required="" maxlength="50">
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <div class="mb-3">
                                                            <label>Password:</label>
                                                            <input value="${usuario.password}" name="password" type="password" 
                                                                   class="form-control" required="" maxlength="6">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <div class="mb-3">
                                                            <label>Rol: <span style="color: red;">(*)</span></label>
                                                            <select class="form-control form-control-lg" id="id_rol" name="id_rol" required="">
                                                                <option value="">:::Seleccione:::</option>
                                                                <c:forEach var="item" items="${roles}">
                                                                    <option value="${item.id}" ${usuario.idRol == item.id ? "selected": ""}
                                                                            >${item.descripcion} </option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <div class="mb-3">
                                                            <label>Estado: <span style="color: red;">(*)</span></label>
                                                            <select name="estado" class="form-control" required="">
                                                                <option>::: Seleccione :::</option>
                                                                <option value="1" ${usuario.estado == 1 ? 'selected' : ''}>Activo</option>
                                                                <option value="0" ${usuario.estado == 0 ? 'selected' : ''}>Inactivo</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="mb-3">
                                                    <input type="hidden" name="id" value="${usuario.idUsuario}">
                                                    <input type="hidden" name="accion" value="guardar">
                                                    <button class="btn btn-primary btn-sm">
                                                        <i class="fa fa-save"></i> Guardar
                                                    </button>
                                                    <a href="usuario?accion=listar" 
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
