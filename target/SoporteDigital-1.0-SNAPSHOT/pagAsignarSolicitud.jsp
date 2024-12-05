<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Solicitudes pendientes</title>
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
                                        <h4 class="card-title">Solicitudes pendientes sin asignar</h4>
                                        <hr />

                                        <jsp:include page="includes/Mensaje.jsp" />

                                        <table class="table table-bordered table-striped mt-2">
                                            <thead class="bg-primary text-white">
                                                <tr>
                                                    <th># Solicitud</th>
                                                    <th>Tipo Solicitud</th>
                                                    <th>Cliente</th>
                                                    <th>Fecha Registro</th>
                                                    <th>Motivo</th>
                                                    <th>Acción</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${solicitudes_pendientes}" var="item">
                                                    <tr>
                                                        <td>${item.idSolicitud}</td>
                                                        <td>${item.nombreTipoSolicitud}</td>
                                                        <td>${item.usuario.nombreCompleto()}</td>
                                                        <td>${item.fechaHoraRegistro}</td>
                                                        <td>${item.motivo}</td>
                                                        <td>
                                                            <!-- Botón para abrir el modal -->
                                                            <button class="btn btn-warning btn-sm" data-toggle="modal" data-target="#asignarModal${item.idSolicitud}"
                                                                    title="Asignar Colaborador">
                                                                Asignar
                                                            </button>

                                                            <!-- Modal -->
                                                            <div class="modal fade" id="asignarModal${item.idSolicitud}" 
                                                                 tabindex="-1" role="dialog" 
                                                                 aria-labelledby="asignarModalLabel${item.idSolicitud}" 
                                                                 aria-hidden="true"  data-backdrop="static">
                                                                <div class="modal-dialog" role="document">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header bg-primary text-white">
                                                                            <h5 class="modal-title" id="asignarModalLabel${item.idSolicitud}">::: Asignar Colaborador :::</h5>
                                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                <span aria-hidden="true">&times;</span>
                                                                            </button>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <form action="solicitud" method="get">
                                                                                <div class="form-group">
                                                                                    <label>Colaborador: <span style="color: red;">(*)</span></label>
                                                                                    <select class="form-control" id="colaboradorId" name="colaboradorId" required>
                                                                                        <option value="">::: Seleccione :::</option>
                                                                                        <c:forEach items="${colaboradores}" var="itemC">
                                                                                            <option value="${itemC.idUsuario}">${itemC.apePaterno} ${itemC.apeMaterno} ${itemC.nombres} (${itemC.nombreRol})</option>
                                                                                        </c:forEach>
                                                                                    </select>
                                                                                </div>

                                                                                <div class="form-group">
                                                                                    <label>Cliente Registrado</label>
                                                                                    <p>${item.usuario.nombreCompleto()}</p>
                                                                                </div>


                                                                                <div class="row">
                                                                                    <div class="col-sm-6">
                                                                                        <div class="form-group">
                                                                                            <label>Tipo de Solicitud</label>
                                                                                            <p>${item.nombreTipoSolicitud}</p>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-sm-6">
                                                                                        <div class="form-group">
                                                                                            <label>Fecha de Registro</label>
                                                                                            <p>${item.fechaHoraRegistro}</p>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group">
                                                                                    <label>Motivo</label>
                                                                                    <p>${item.motivo}</p>
                                                                                </div>
                                                                                <input type="hidden" name="accion" value="guardarAsignacion">
                                                                                <input type="hidden" name="solicitudId" value="${item.idSolicitud}" />
                                                                                <button type="submit" class="btn btn-primary btn-sm">
                                                                                    <i class="fas fa-save"></i> Guardar
                                                                                </button>
                                                                                <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">
                                                                                    <i class="fas fa-times"></i> Cancelar
                                                                                </button>

                                                                            </form>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <c:if test="${solicitudes_pendientes.size() == 0}">
                                                    <tr class="text-center">
                                                        <td colspan="9">No hay asignaciones</td>
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
        <jsp:include page="includes/Js.jsp" />
    </body>

</html>
