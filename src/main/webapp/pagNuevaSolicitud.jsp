<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Formulario de Solicitud</title>
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

                                            <h3>${solicitud.idSolicitud == 0 ? "Nueva" : "Editar"} Solicitud</h3>
                                            <hr />

                                            <form action="solicitud" method="post">
                                                <jsp:include page="includes/Mensaje.jsp" />
                                                <div class="row">
                                                    <div class="col-sm-6">
                                                        <div class="mb-3">
                                                            <label>Cliente <span style="color: red;">(*)</span></label>
                                                            <input value="${sessionScope.usuario.nombres}" type="text" class="form-control" required="" disabled>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <div class="mb-3">
                                                            <label>Tipo de Solicitud: <span style="color: red;">(*)</span></label>
                                                            <select name="tipoSolicitud" class="form-control" required="">
                                                                <option value="">:::Seleccione:::</option>
                                                                <c:forEach var="item" items="${tipoSolicitudes}">
                                                                    <option value="${item.id}" ${item.id == solicitud.tipoSolicitud ? "selected" : ""}>
                                                                        ${item.descripcion}
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
  

                                                <div class="mb-3">
                                                    <label>Motivo: <span style="color: red;">(*)</span></label>
                                                    <textarea name="motivo" class="form-control" required rows="5">${solicitud.motivo}</textarea>
                                                </div>
                                                <div class="mb-3">
                                                    <input type="hidden" name="idSolicitud" value="${solicitud.idSolicitud}">
                                                    <input type="hidden" name="accion" value="guardar">
                                                    <button class="btn btn-primary btn-sm">
                                                        <i class="fa fa-save"></i> Guardar
                                                    </button>
                                                    <a href="solicitud?accion=listar" class="btn btn-dark btn-sm">
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
