<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Solicitudes por atender</title>
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
                                        <h4 class="card-title">Solicitudes por atender</h4>
                                        <hr />

                                        <jsp:include page="includes/Mensaje.jsp" />

                                        <div id="resultadoSolicitudes"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalTrabajo"  tabindex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true" data-backdrop="static">
            <div class="modal-dialog modal-lg" role="document"  data-backdrop="static">
                <div class="modal-content">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title" id="modalTareaLabel">::: Trabajos Solicitud :::</h5>
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
                                <div class="col-sm-10">
                                    <div class="form-group">
                                        <label>Descripción: <span style="color: red;">(*)</span></label>
                                        <textarea id="descripcion" name="descripcion" class="form-control" rows="3" placeholder="Ingrese la descripción de la tarea"></textarea>
                                    </div>
                                </div>
                                <div class="col-sm-2 mt-5">
                                    <button onclick="fnGuardarTrabajo()" type="button" class="btn btn-success btn-sm">
                                        <i class="fa fa-save"></i> Guardar
                                    </button>
                                </div>
                            </div>
                        </form>

                        <div id="resultadoTrabajos"></div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">
                            <i class="fas fa-times"></i> Cancelar
                        </button>

                        <button type="button" class="btn btn-primary btn-sm btnTerminar" id="btnFinalizarTarea" 
                                onclick="fnConfFinalizarSolicitud()">
                            <i class="fas fa-check"></i> Finalizar Solicitud
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="includes/Js.jsp" />
    </body>
    <script>

        function fnEmpezarTrabajo(idSolicitud, cliente, motivo, tipoSolicitud, estado) {
            $("#lblIDSolicitud").html(idSolicitud);
            $("#lblCliente").html(cliente);
            $("#lblMotivo").html(motivo);
            $("#lblTipoSolicitud").html(tipoSolicitud);
            $("#lblEstado").html(estado);
            $('#modalTrabajo').modal('show');

            fnListarTrabajos(idSolicitud);
        }

        function fnConfFinalizarSolicitud() {
            const solicitudId = $("#lblIDSolicitud").html();
            Swal.fire({
                title: '¿Está seguro?',
                text: '¿Desea seguro que desea finalizar la atención de la solicitud con Id ' + solicitudId + '?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#28a745',
                cancelButtonColor: '#dc3545',
                confirmButtonText: 'Sí, Confirmar!',
                cancelButtonText: 'Cancelar',
                background: '#f8f9fa'
            }).then((result) => {
                if (result.isConfirmed) {
                    fnFinalizarSolicitud(solicitudId);
                }
            });
        }

        function fnFinalizarSolicitud(idSolicitud) {
            $(".btnTerminar").prop("disabled", true);

            const _params = {
                solicitudId: idSolicitud,
                accion: "finalizarSolicitud"
            };

            axios
                    .get("solicitud", {params: _params})
                    .then((response) => {
                        response = response.data;

                        if (response.msg === "OK") {
                            fnAlert("success", "Solicitud finalizado correctamente.");
                            $("#modalTrabajo").modal("hide");
                            fnListarSolicitudes();
                        } else {
                            fnAlert("error", response.msg);
                        }
                        $(".btnTerminar").prop("disabled", false);
                    })
                    .catch((error) => {
                        console.error(error);
                        fnAlert("error", "Error en el sistema. Por favor, intente más tarde.");
                        $(".btnTerminar").prop("disabled", false);
                    });

        }

        function fnGuardarTrabajo() {
            const solicitudId = $("#lblIDSolicitud").html();
            const descripcion = $("#descripcion").val();

            if (!descripcion) {
                fnAlert("info", "Por favor, ingrese una descripción");
                return;
            }

            const _params = {
                solicitudId: solicitudId,
                descripcion: descripcion,
                accion: "guardar"
            };

            axios
                    .get("trabajo", {params: _params})
                    .then((response) => {
                        response = response.data;

                        if (response.msg === "OK") {
                            fnAlert("success", "Trabajo registrado correctamente.");
                            $("#descripcion").val("");
                            fnListarSolicitudes();
                            fnListarTrabajos(solicitudId);
                        } else {
                            fnAlert("error", response.msg);
                        }
                    })
                    .catch((error) => {
                        console.error(error);
                        fnAlert("error", "Error en el sistema. Por favor, intente más tarde.");
                    });
        }

        function fnConfTerminarTrabajo(idTrabajo, idSolicitud) {
            Swal.fire({
                title: '¿Está seguro?',
                text: '¿Desea seguro que desea terminar la tarea con Id ' + idTrabajo + '?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#28a745',
                cancelButtonColor: '#dc3545',
                confirmButtonText: 'Sí, Confirmar!',
                cancelButtonText: 'Cancelar',
                background: '#f8f9fa'
            }).then((result) => {
                if (result.isConfirmed) {
                    fnTerminarTrabajo(idTrabajo, idSolicitud);
                }
            });
        }

        function fnTerminarTrabajo(idTrabajo, idSolicitud) {
            const _params = {
                solicitudId: idSolicitud,
                trabajoId: idTrabajo,
                accion: "terminar"
            };

            axios
                    .get("trabajo", {params: _params})
                    .then((response) => {
                        response = response.data;

                        if (response.msg === "OK") {
                            fnAlert("success", "Trabajo terminado correctamente.");
                            fnListarTrabajos(idSolicitud);
                        } else {
                            fnAlert("error", response.msg);
                        }
                    })
                    .catch((error) => {
                        console.error(error);
                        fnAlert("error", "Error en el sistema. Por favor, intente más tarde.");
                    });
        }

        function fnListarTrabajos(idSolicitud) {
            $("#resultadoTrabajos").html("cargando...");
            $("#resultadoTrabajos").load("trabajo?accion=listarPorAtender&idSolicitud=" + idSolicitud);
        }

        function fnListarSolicitudes() {
            $("#resultadoSolicitudes").html("cargando...");
            $("#resultadoSolicitudes").load("solicitud?accion=por_atender_vista");
        }

        function fnInit() {
            fnListarSolicitudes();
        }

        fnInit();
    </script>
</html>
