<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Gestión de Usuarios</title>
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
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">Gestión de Usuarios</h4>
                                        <hr />


                                        <c:if  test="${sessionScope.tipoUsuario == 'S'}">
                                            <a href="usuario?accion=nuevo" class="btn btn-success btn-sm">
                                                <i class="fa fa-plus-circle"></i> Nuevo 
                                            </a>
                                        </c:if>

                                        <jsp:include page="includes/Mensaje.jsp" />

                                        <div class="table-responsive">
                                            <table class="table table-bordered table-striped mt-2 data_tabla">
                                                <thead class="bg-primary text-white">
                                                    <tr>
                                                        <th>ID Usuario</th>
                                                        <th>Tipo Doc</th>
                                                        <th>Número Doc</th>
                                                        <th>Nombres</th>
                                                        <th>Apellido Paterno</th>
                                                        <th>Apellido Materno</th>
                                                        <th>Correo</th>
                                                        <th>Estado</th>
                                                        <th>Tipo Usuario</th>
                                                        <th>Rol</th>
                                                            <c:if test="${sessionScope.tipoUsuario == 'S'}">
                                                            <th>Acción</th>
                                                            </c:if>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${lista}" var="item">
                                                        <tr>
                                                            <td>${item.idUsuario}</td>
                                                            <td>${item.nombreTipoDoc}</td>
                                                            <td>${item.numeroDoc}</td>
                                                            <td>${item.nombres}</td>
                                                            <td>${item.apePaterno}</td>
                                                            <td>${item.apeMaterno}</td>
                                                            <td>${item.correo}</td>
                                                            <td>${item.estado == 1 ? "Activo" : "Inactivo"}</td>
                                                            <td>${item.nombreTipoUsuario}</td>
                                                            <td>${item.nombreRol}</td>
                                                            <c:if test="${sessionScope.tipoUsuario == 'S'}">
                                                                <td>
                                                                    <a href="usuario?accion=editar&id=${item.idUsuario}"
                                                                       class="btn btn-primary btn-sm">
                                                                        <i class="fa fa-edit"></i>
                                                                    </a>
                                                                    <a href="usuario?accion=eliminar&id=${item.idUsuario}"
                                                                       onclick="return confirm('¿Está seguro que desea eliminar al usuario con ID ${item.id}?')"
                                                                       class="btn btn-danger btn-sm">
                                                                        <i class="fa fa-trash"></i>
                                                                    </a>
                                                                </td>
                                                            </c:if>
                                                        </tr>
                                                    </c:forEach>
                                                    <c:if test="${lista.size() == 0}">
                                                        <tr class="text-center">
                                                            <td colspan="12">No hay registros</td>
                                                        </tr>
                                                    </c:if>
                                                </tbody>
                                            </table>
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
