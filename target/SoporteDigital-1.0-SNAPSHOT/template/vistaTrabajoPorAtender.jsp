<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="table-responsive mt-2">
    <table class="table table-bordered table-striped mt-2 data_tabla">
        <thead class="bg-primary text-white">
            <tr>
                <th>ID Trab.</th>
                <th>Usuario Reg.</th>
                <th>Descripción</th>
                <th>Fecha Inicio</th>
                <th>Fecha termino</th>
                <th>Acción</th>
            </tr>
        </thead>
        <tbody >
            <c:forEach items="${trabajos}" var="item">
                <tr class="trabajo" data-fecha-hora-fin="${item.fechaHoraFin}"  data-id="${item.idTrabajo}" >
                    <td>${item.idTrabajo}</td>
                    <td>${item.usuario.nombreCompleto()}</td>
                    <td>${item.descripcion}</td>
                    <td>${item.fechaHoraInicio}</td>
                    <td>${item.fechaHoraFin == null ? "-": item.fechaHoraFin}</td>
                    <td>
                        <button onclick="fnConfTerminarTrabajo(${item.idTrabajo},${item.idSolicitud})" 
                                type="button" ${item.fechaHoraFin != null || item.usuario.idUsuario != sessionScope.usuario.idUsuario? 
                                                "disabled": ""}
                                class="btn btn-danger btn-sm" 
                                title="Terminar Tarea">
                            <i class="fa fa-stop"></i> 
                        </button>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${trabajos.size() == 0}">
                <tr class="text-center">
                    <td colspan="9">No hay trabajos registrados</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>

<script>
    $(document).ready(function () {
        function fnActualizarEstadoBotonFinalizarSolicitud() {
            var trabajosSinFechaFin = 0;
            var cantTrabajos = 0;

            $(".trabajo").each(function () {
                var fechaHoraFin = $(this).data("fecha-hora-fin");

                if (fechaHoraFin === null || fechaHoraFin === "") {
                    trabajosSinFechaFin++;
                }
                cantTrabajos++;
            });

            if (trabajosSinFechaFin === 0 && cantTrabajos > 0) {
                $(".btnTerminar").prop("disabled", false);  // Habilitar el botón
            } else {
                $(".btnTerminar").prop("disabled", true);  // Deshabilitar el botón
            }
        }

        fnActualizarEstadoBotonFinalizarSolicitud();

    });
</script>