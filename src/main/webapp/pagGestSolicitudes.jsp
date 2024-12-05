<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Gestión de Solicitudes</title>
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
                                        <h4 class="card-title">Mis Solicitudes</h4>
                                        <hr />

                                        <jsp:include page="includes/Mensaje.jsp" />

                                        <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                                            <li class="nav-item" role="presentation">
                                                <button class="nav-link active" id="pills-home-tab" data-toggle="pill" data-target="#pills-home" type="button" role="tab" aria-controls="pills-home" aria-selected="true">Pendientes</button>
                                            </li>
                                            <li class="nav-item" role="presentation">
                                                <button class="nav-link" id="pills-profile-tab" data-toggle="pill" data-target="#pills-profile" type="button" role="tab" aria-controls="pills-profile" aria-selected="false">Atendidas</button>
                                            </li>

                                        </ul>
                                        <div class="tab-content" id="pills-tabContent">
                                            <div class="tab-pane fade show active" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab">
                                                <a href="solicitud?accion=nuevo" class="btn btn-success btn-sm">
                                                    <i class="fa fa-plus-circle"></i> Nuevo 
                                                </a>

                                                <table class="table table-bordered table-striped mt-2">
                                                    <thead class="bg-primary text-white">
                                                        <tr>
                                                            <th># Solicitud</th>
                                                            <th>Tipo Solicitud</th>
                                                            <th>Fecha Registro</th>
                                                            <th>Motivo</th>
                                                            <th>Estado</th>
                                                            <th>Fecha Asignación</th>
                                                            <th>Fecha Inicio Atención</th>
                                                            <th>Acción</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${solicitudes_pendientes}" var="item">
                                                            <tr>
                                                                <td>${item.idSolicitud}</td>
                                                                <td>${item.nombreTipoSolicitud}</td>
                                                                <td>${item.fechaHoraRegistro}</td>
                                                                <td>${item.motivo}</td>
                                                                <td>${item.nombreEstado}</td>
                                                                <td>${item.fechaHoraAsignacion == null ? "-": item.fechaHoraAsignacion}</td>
                                                                <td>${item.fechaHoraInicioAtencion == null ? "-": item.fechaHoraInicioAtencion}</td>
                                                                <td>
                                                                    <c:if test="${item.estado != 'P'}">
                                                                        <button type="button" class="btn btn-danger btn-sm" disabled="">
                                                                            <i class="fa fa-trash"></i>
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${item.estado == 'P'}">
                                                                        <a href="solicitud?accion=eliminar&id=${item.idSolicitud}"
                                                                           onclick="return confirm('¿Está seguro que desea eliminar la solicitud con id ${item.idSolicitud}?')"
                                                                           class="btn btn-danger btn-sm">
                                                                            <i class="fa fa-trash"></i>
                                                                        </a>
                                                                    </c:if>

                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                        <c:if test="${solicitudes_pendientes.size() == 0}">
                                                            <tr class="text-center">
                                                                <td colspan="9">No hay registros</td>
                                                            </tr>
                                                        </c:if>
                                                    </tbody>
                                                </table>

                                            </div>
                                            <div class="tab-pane fade" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">

                                                <table class="table table-bordered table-striped mt-2">
                                                    <thead class="bg-primary text-white">
                                                        <tr>
                                                            <th># Solicitud</th>
                                                            <th>Tipo Solicitud</th>
                                                            <th>Fecha Registro</th>
                                                            <th>Motivo</th>
                                                            <th>Fecha Asignación</th>
                                                            <th>Fecha Inicio Atención</th>
                                                            <th>Fecha Fin Atención</th>
                                                            <th>Acción</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${solicitudes_atendidas}" var="item">
                                                            <tr>
                                                                <td>${item.idSolicitud}</td>
                                                                <td>${item.nombreTipoSolicitud}</td>
                                                                <td>${item.fechaHoraRegistro}</td>
                                                                <td>${item.motivo}</td>
                                                                <td>${item.fechaHoraAsignacion}</td>
                                                                <td>${item.fechaHoraInicioAtencion}</td>
                                                                <td>${item.fechaHoraTerminoAtencion}</td>
                                                                <td>
                                                                    <button type="button" class="btn btn-primary btn-sm" title="Ver detalle"
                                                                            onclick="fnCargarDetalle(${item.idSolicitud}, '${item.usuario.nombreCompleto()}', '${item.motivo}', '${item.nombreTipoSolicitud}',
                                                                                        '${item.nombreEstado}', '${item.fechaHoraRegistro}', '${item.fechaHoraAsignacion}',
                                                                                        '${item.fechaHoraInicioAtencion}', '${item.fechaHoraTerminoAtencion}')">
                                                                        <i class="fa fa-eye"></i> Ver
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                        <c:if test="${solicitudes_atendidas.size() == 0}">
                                                            <tr class="text-center">
                                                                <td colspan="8">No hay registros</td>
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
        </div>

        <div class="modal fade" id="detalleModal"  tabindex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true" data-backdrop="static">
            <div class="modal-dialog modal-lg" role="document"  data-backdrop="static">
                <div class="modal-content">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title" id="modalTareaLabel">::: Detalle Solicitud :::</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="pcoded-main-container">
                            <div class="pcoded-wrapper container-fluid mt-2">
                                <div class="main-body">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <div>
                                                        <strong>ID Solicitud:</strong>
                                                        <span id="lblIDSolicitud"></span>
                                                    </div>
                                                    <br /> 
                                                    <div>
                                                        <strong>Cliente:</strong>
                                                        <span  id="lblCliente"> </span>
                                                    </div>
                                                    <br /> 
                                                    <div>
                                                        <strong>Estado Solicitud:</strong>
                                                        <span  id="lblEstado"> </span>
                                                    </div>
                                                    <br /> 
                                                </div>
                                                <div class="col-sm-6">
                                                    <div>
                                                        <strong>Tipo Solicitud:</strong>
                                                        <span  id="lblTipoSolicitud"></span>
                                                    </div>
                                                    <br /> 
                                                    <div>
                                                        <strong>Motivo:</strong>
                                                        <span  id="lblMotivo"></span>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <div>
                                                        <strong>Fecha Registro Solicitud:</strong>
                                                        <span id="lblFechaReg"></span>
                                                    </div>
                                                    <br /> 
                                                    <div>
                                                        <strong>Fecha Asignación Solicitud</strong>
                                                        <span  id="lblAsigSolicitud"> </span>
                                                    </div>
                                                </div>

                                                <div class="col-sm-6">
                                                    <div>
                                                        <strong>Fecha Inicio Atención:</strong>
                                                        <span id="lblFechaInicioAten"></span>
                                                    </div>
                                                    <br /> 
                                                    <div>
                                                        <strong>Fecha Fin Atención:</strong>
                                                        <span  id="lblFechaFinAten"> </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr />

                        <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link active" id="pills-home-tab" data-toggle="pill" data-target="#pills-home2" type="button" role="tab" aria-controls="pills-home" aria-selected="true">Asignaciones</button>
                            </li>
                            <li class="nav-item" role="presentation">
                                <button class="nav-link" id="pills-profile-tab" data-toggle="pill" data-target="#pills-profile2" type="button" role="tab" aria-controls="pills-profile" aria-selected="false">Trabajos</button>
                            </li>

                        </ul>
                        <div class="tab-content" id="pills-tabContent">
                            <div class="tab-pane fade show active" id="pills-home2" role="tabpanel" aria-labelledby="pills-home-tab">

                                <table class="table table-bordered table-striped mt-2 data_tabla">
                                    <thead class="bg-primary text-white">
                                        <tr>
                                            <th>ID</th>
                                            <th>Colaborador</th>
                                            <th>Rol</th>
                                            <th>Coordinador</th>
                                        </tr>
                                    </thead>
                                    <tbody id="lblColaborador">
                                    </tbody>
                                </table>

                            </div>
                            <div class="tab-pane fade" id="pills-profile2" role="tabpanel" aria-labelledby="pills-profile-tab">

                                <table class="table table-bordered table-striped mt-2">
                                    <thead class="bg-primary text-white">
                                        <tr>
                                            <th>ID Trab.</th>
                                            <th>Usuario Reg</th>
                                            <th>Descripción</th>
                                            <th>Fecha Inicio</th>
                                            <th>Fecha termino</th>
                                        </tr>
                                    </thead>
                                    <tbody  id="lblTrabajo">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">
                            <i class="fas fa-times"></i> Cancelar
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="includes/Js.jsp" />
        <script src="assets/js/funciones/solicitudesDetalleControl.js" type="text/javascript"></script>
    </body>

</html>
