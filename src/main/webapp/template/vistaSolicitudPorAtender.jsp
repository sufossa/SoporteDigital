<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <button onclick="fnEmpezarTrabajo(${item.idSolicitud}, '${item.usuario.nombreCompleto()}', '${item.motivo}', '${item.nombreTipoSolicitud}', '${item.nombreEstado}')" 
                            type="button" 
                            class="btn btn-success btn-sm" 
                            title="Empezar Tarea">
                        <i class="fa fa-play"></i> Empezar
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