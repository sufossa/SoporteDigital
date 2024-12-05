<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Solicitudes no finalizadas</title>
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
                                        <h4 class="card-title">Solicitudes no finalizadas</h4>
                                        <hr />

                                        <jsp:include page="includes/Mensaje.jsp" />

                                        <table class="table table-bordered table-striped mt-2">
                                            <thead class="bg-primary text-white">
                                                <tr>
                                                    <th># Solicitud</th>
                                                    <th>Tipo Solicitud</th>
                                                    <th>Cliente</th>
                                                    <th>Fecha Registro</th>
                                                    <th>Fecha Asignación</th>
                                                    <th>Motivo</th>
                                                    <th>Estado</th>
                                                    <th>Acción</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${solicitudes}" var="item">
                                                    <tr>
                                                        <td>${item.idSolicitud}</td>
                                                        <td>${item.nombreTipoSolicitud}</td>
                                                        <td>${item.usuario.nombreCompleto()}</td>
                                                        <td>${item.fechaHoraRegistro}</td>
                                                        <td>${item.fechaHoraAsignacion == null ? "-": item.fechaHoraAsignacion}</td>
                                                        <td>${item.motivo}</td>
                                                        <td>${item.nombreEstado}</td>
                                                        <td>
                                                            <button onclick="fnCargarColaboradores(${item.idSolicitud}, '${item.usuario.nombreCompleto()}', '${item.motivo}', '${item.nombreTipoSolicitud}', '${item.nombreEstado}')" type="button" class="btn btn-primary btn-sm" data-toggle="modal" 
                                                                    title="Asignar Colaborador">
                                                                <i class="fa fa-user"></i> 
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                <c:if test="${solicitudes.size() == 0}">
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

        <div class="modal fade" id="modalAsignarColaborador" tabindex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true" data-backdrop="static">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title">::: Asignación de Colaboradores :::</h5>
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
                                                    <strong>ID Solicitud:</strong>
                                                    <span id="lblIDSolicitud"></span>

                                                    <br /> 
                                                    <strong>Cliente:</strong>
                                                    <span  id="lblCliente"> </span>
                                                    <br /> 
                                                    <strong>Estado Solicitud:</strong>
                                                    <span  id="lblEstado"> </span>

                                                </div>
                                                <div class="col-sm-6">
                                                    <strong>Tipo Solicitud:</strong>
                                                    <span  id="lblTipoSolicitud"></span>

                                                    <br /> 
                                                    <strong>Motivo:</strong>
                                                    <span  id="lblMotivo"></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr />
                        <form action="solicitud" method="get">
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>Colaborador: <span style="color: red;">(*)</span></label>
                                        <select class="form-control" id="colaboradorId" name="colaboradorId" required>
                                            <option value="">::: Seleccione :::</option>
                                            <c:forEach items="${colaboradores}" var="item">
                                                <option value="${item.idUsuario}">${item.apePaterno} ${item.apeMaterno} ${item.nombres} (${item.nombreRol})</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6 mt-4">
                                    <button onclick="fnAsignarColaborador()" type="button" class="btn btn-success btn-sm">
                                        <i class="fa fa-user-plus"></i> Asignar
                                    </button>
                                </div>
                            </div>
                        </form>

                        <div class="table-responsive mt-2">
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
    </body>
    <script>
        function fnAsignarColaborador() {
            const colaboradorId = document.getElementById("colaboradorId").value;
            const solicitudId = document.getElementById("lblIDSolicitud").innerHTML;

            if (!colaboradorId) {
                fnAlert("info", "Por favor, seleccione un colaborador.");
                return;
            }

            const _params = {
                colaboradorId: colaboradorId,
                solicitudId: solicitudId,
                accion: "guardarAsignacionJSON"
            };

            axios
                    .get("solicitud", {params: _params})
                    .then((response) => {
                        response = response.data;

                        if (response.msg === "OK") {
                            fnAlert("success", "Colaborador asignado correctamente.");
                            fnListarColaboradores(solicitudId);
                        } else {
                            fnAlert("error", response.msg);
                        }
                    })
                    .catch((error) => {
                        console.error(error);
                        fnAlert("error", "Error en el sistema. Por favor, intente más tarde.");
                    });
        }

        function fnListarColaboradores(idSolicitud) {
            var _params = {
                "idSolicitud": idSolicitud,
                "accion": "listColaborador"
            };

            axios
                    .get("solicitud", {params: _params})
                    .then((response) => {
                        response = response.data;

                        const tbody = document.getElementById("lblColaborador");
                        tbody.innerHTML = "";

                        response.forEach((item, index) => {
                            const row = document.createElement("tr");

                            const colID = document.createElement("td");
                            colID.textContent = item.idUsuario;
                            row.appendChild(colID);

                            const colColaborador = document.createElement("td");
                            colColaborador.textContent = item.apePaterno + " " + item.apeMaterno + " , " + item.nombres;
                            row.appendChild(colColaborador);

                            const colRol = document.createElement("td");
                            colRol.textContent = item.nombreRol;
                            row.appendChild(colRol);

                            const colCoordinador = document.createElement("td");
                            const checkbox = document.createElement("input");
                            checkbox.type = "checkbox";
                            checkbox.name = "coordinador";
                            checkbox.value = item.idUsuario;
                            checkbox.checked = item.esCoordinador;
                            checkbox.onclick = function () {
                                document.querySelectorAll("input[name='coordinador']").forEach((cb) => {
                                    if (cb !== checkbox) {
                                        cb.checked = false;
                                    }
                                });

                                if (checkbox.checked) {
                                    fnAsignarCoordinador(idSolicitud, item.idUsuario);
                                }
                            };
                            colCoordinador.appendChild(checkbox);
                            row.appendChild(colCoordinador);

                            tbody.appendChild(row);
                        });

                    })
                    .catch((error) => {
                        console.dir(error);
                        fnAlert("error", "No se pudo cargar colaboradores.");
                    });
        }

        function fnAsignarCoordinador(idSolicitud, idUsuario) {
            const _params = {
                solicitudId: idSolicitud,
                usuarioId: idUsuario,
                accion: "asignarCoordinador"
            };

            axios
                    .get("solicitud", {params: _params})
                    .then((response) => {
                        response = response.data;

                        if (response.msg === "OK") {
                            fnAlert("success", "Coordinador asignado correctamente.");
                            fnListarColaboradores(idSolicitud);
                        } else {
                            fnAlert("error", response.msg);
                        }
                    })
                    .catch((error) => {
                        console.error(error);
                        fnAlert("error", "Error en el sistema. Por favor, intente más tarde.");
                    });
        }

        function fnCargarColaboradores(idSolicitud, cliente, motivo, tipoSolicitud, estado) {
            $("#lblIDSolicitud").html(idSolicitud);
            $("#lblCliente").html(cliente);
            $("#lblMotivo").html(motivo);
            $("#lblTipoSolicitud").html(tipoSolicitud);
            $("#lblEstado").html(estado);

            $("#modalAsignarColaborador").modal("show");

            fnListarColaboradores(idSolicitud);
        }
    </script>
</html>
