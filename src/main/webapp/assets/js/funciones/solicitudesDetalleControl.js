function fnCargarDetalle(idSolicitud, cliente, motivo, tipoSolicitud, estado,
        fechaHoraRegistro, fechaHoraAsignacion, fechaHoraInicioAtencion, fechaHoraTerminoAtencion) {
    $("#lblIDSolicitud").html(idSolicitud);
    $("#lblCliente").html(cliente);
    $("#lblMotivo").html(motivo);
    $("#lblTipoSolicitud").html(tipoSolicitud);
    $("#lblEstado").html(estado);

    $("#lblFechaReg").html(fechaHoraRegistro);
    $("#lblAsigSolicitud").html(fechaHoraAsignacion);
    $("#lblFechaInicioAten").html(fechaHoraInicioAtencion);
    $("#lblFechaFinAten").html(fechaHoraTerminoAtencion);

    $("#detalleModal").modal("show");

    fnListarColaboradores(idSolicitud);
    fnListarTrabajos(idSolicitud);
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
                    colColaborador.textContent = item.apePaterno + " " + item.apePaterno + " , " + item.nombres;
                    row.appendChild(colColaborador);

                    const colRol = document.createElement("td");
                    colRol.textContent = item.nombreRol;
                    row.appendChild(colRol);

                    const colCoordinador = document.createElement("td");
                    colCoordinador.textContent = item.esCoordinador ? "SI" : "NO";
                    row.appendChild(colCoordinador);

                    tbody.appendChild(row);
                });

            })
            .catch((error) => {
                console.dir(error);
                fnAlert("error", "No se pudo cargar colaboradores.");
            });
}

function fnListarTrabajos(idSolicitud) {
    var _params = {
        "idSolicitud": idSolicitud,
        "accion": "listarPorAtenderJson"
    };

    axios
            .get("trabajo", {params: _params})
            .then((response) => {
                response = response.data;

                const tbody = document.getElementById("lblTrabajo");
                tbody.innerHTML = "";

                response.forEach((item, index) => {
                    const row = document.createElement("tr");

                    const colID = document.createElement("td");
                    colID.textContent = item.idTrabajo;
                    row.appendChild(colID);

                    const colColaborador = document.createElement("td");
                    colColaborador.textContent = item.usuario.apePaterno + " " + item.usuario.apePaterno + " , " + item.usuario.nombres;
                    row.appendChild(colColaborador);

                    const colDesc = document.createElement("td");
                    colDesc.textContent = item.descripcion;
                    row.appendChild(colDesc);

                    const colFechaHoraInicio = document.createElement("td");
                    colFechaHoraInicio.textContent = item.fechaHoraInicio;
                    row.appendChild(colFechaHoraInicio);

                    const colFechaHoraFin = document.createElement("td");
                    colFechaHoraFin.textContent = item.fechaHoraFin;
                    row.appendChild(colFechaHoraFin);

                    tbody.appendChild(row);
                });

            })
            .catch((error) => {
                console.dir(error);
                fnAlert("error", "No se pudo cargar trabajos.");
            });
}