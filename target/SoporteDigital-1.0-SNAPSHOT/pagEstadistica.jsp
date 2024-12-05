<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Estadistica de Operaciones</title>
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
                                        <h4 class="card-title">Estadistica de Operaciones</h4>
                                        <hr />

                                        <jsp:include page="includes/Mensaje.jsp" />

                                        <div class="row">
                                            <div class="col-md-12 grid-margin stretch-card">
                                                <form method="POST" action="GenerarReporte" class="form-inline">
                                                    <div class="form-group mb-3 mr-3">
                                                        <label for="anio" class="sr-only">Año</label>
                                                        <input type="number" id="anio" name="anio" class="form-control" placeholder="Año" min="1900" max="2100" required />
                                                    </div>


                                                    <div class="form-group mb-3 mr-3">
                                                        <label for="mes" class="sr-only">Mes</label>
                                                        <select id="mes" name="mes" class="form-control">
                                                            <option value="1">Enero</option>
                                                            <option value="2">Febrero</option>
                                                            <option value="3">Marzo</option>
                                                            <option value="4">Abril</option>
                                                            <option value="5">Mayo</option>
                                                            <option value="6">Junio</option>
                                                            <option value="7">Julio</option>
                                                            <option value="8">Agosto</option>
                                                            <option value="9">Septiembre</option>
                                                            <option value="10">Octubre</option>
                                                            <option value="11">Noviembre</option>
                                                            <option value="12">Diciembre</option>
                                                        </select>
                                                    </div>


                                                    <button onclick="fnGenerarReporte()" id="btnGenerar" type="button" class="btn btn-primary mb-3">
                                                        <i class="mdi mdi-chart-bar"></i> Generar Reporte
                                                    </button>
                                                </form>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-sm-6">
                                                <div id="graficoEstado">
                                                </div>
                                            </div>
                                            <div class="col-sm-6">
                                                <div id="graficoTipo">
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
        </div>

        <jsp:include page="includes/Js.jsp" />
        <script src="assets/librerias/highcharts.js"></script>
        <script src="assets/librerias/modules/exporting.js"></script>
        <script src="assets/librerias/modules/export-data.js"></script>
        <script src="assets/librerias/modules/accessibility.js"></script>
    </body>
    <script>

                                                        function fnGenerarReporte() {
                                                            const anio = document.getElementById('anio').value;
                                                            const mes = document.getElementById('mes').value;

                                                            if (!anio || !mes) {
                                                                fnAlert("info", 'Por favor, completa todos los campos.');
                                                                return;
                                                            }

                                                            const _params = {
                                                                annio: anio,
                                                                mes: mes,
                                                                accion: "obtener_reporte_estadistica"
                                                            };
                                                            var dataEstados = [];
                                                            var dataDescripcionTipo = [];
                                                            var dataCantidadTipo = [];

                                                            axios
                                                                    .get("solicitud", {params: _params})
                                                                    .then((response) => {
                                                                        response = response.data;
                                                                        console.log(response);

                                                                        for (var i = 0; i < response.cantidad_estados.length; i++) {
                                                                            let item = response.cantidad_estados[i];
                                                                            dataEstados.push({
                                                                                name: item.descripcion,
                                                                                y: item.cantidad
                                                                            });
                                                                        }

                                                                        for (var i = 0; i < response.cantidad_tipos.length; i++) {
                                                                            let item = response.cantidad_tipos[i];
                                                                            dataDescripcionTipo.push(item.descripcion);
                                                                            dataCantidadTipo.push(item.cantidad);
                                                                        }

                                                                        GraficarEstados(dataEstados);
                                                                        GraficarTipo(dataDescripcionTipo, dataCantidadTipo);
                                                                    })
                                                                    .catch((error) => {
                                                                        console.error(error);
                                                                        fnAlert("error", "Error al generar reporte.");
                                                                    });
                                                        }
                                                        function GraficarTipo(descripcion, data) {
                                                            Highcharts.chart('graficoTipo', {
                                                                title: {
                                                                    text: 'Solicitudes registradas por tipo solicitud'
                                                                },
                                                                xAxis: {
                                                                    categories: descripcion
                                                                },
                                                                yAxis: {
                                                                    title: {
                                                                        text: 'Nro de solicitudes'
                                                                    }
                                                                },
                                                                series: [{
                                                                        type: 'column',
                                                                        colorByPoint: true,
                                                                        data: data,
                                                                        showInLegend: false
                                                                    }]

                                                            });
                                                        }

                                                        function GraficarEstados(data) {
                                                            Highcharts.chart('graficoEstado', {
                                                                chart: {
                                                                    plotBackgroundColor: null,
                                                                    plotBorderWidth: null,
                                                                    plotShadow: false,
                                                                    type: 'pie'
                                                                },
                                                                title: {
                                                                    text: 'Solicitudes registradas por estado'
                                                                },
                                                                tooltip: {
                                                                    pointFormat: '{series.name}: <b>{point.y}</b>'
                                                                },
                                                                accessibility: {
                                                                    point: {
                                                                        valueSuffix: '%'
                                                                    }
                                                                },
                                                                plotOptions: {
                                                                    pie: {
                                                                        allowPointSelect: true,
                                                                        cursor: 'pointer',
                                                                        dataLabels: {
                                                                            enabled: true,
                                                                            format: '<b>{point.name}</b>: {point.percentage:.1f}% '
                                                                        }
                                                                    }
                                                                },
                                                                series: [{
                                                                        name: 'Solicitud',
                                                                        colorByPoint: true,
                                                                        data: data
                                                                    }]
                                                            });

                                                        }
    </script>
</html>
