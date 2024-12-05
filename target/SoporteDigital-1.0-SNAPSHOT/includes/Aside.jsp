<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="sidebar sidebar-offcanvas" id="sidebar">
    <ul class="nav">
        <c:if  test="${sessionScope.tipoUsuario != 'C'}">
            <li class="nav-item">
                <a class="nav-link" data-toggle="collapse" href="#gestion" aria-expanded="false" aria-controls="ui-basic">
                    <i class="mdi mdi-circle-outline menu-icon"></i>
                    <span class="menu-title">Gestión</span>
                    <i class="menu-arrow"></i>
                </a>
                <div class="collapse" id="gestion">
                    <ul class="nav flex-column sub-menu">
                        <li class="nav-item"> <a class="nav-link" href="usuario?accion=listar">Usuarios</a></li>
                            <c:if  test="${sessionScope.tipoUsuario == 'S'}">
                            <li class="nav-item"> <a class="nav-link" href="cliente?accion=listar">Clientes</a></li>
                            </c:if>

                    </ul>
                </div>
            </li>

            <li class="nav-item">
                <a class="nav-link" data-toggle="collapse" href="#solicitud1" aria-expanded="false" aria-controls="auth">
                    <i class="mdi mdi-file-document menu-icon"></i>
                    <span class="menu-title">Solicitud</span>
                    <i class="menu-arrow"></i>
                </a>
                <div class="collapse" id="solicitud1">
                    <c:if  test="${sessionScope.tipoUsuario == 'S'}">
                        <ul class="nav flex-column sub-menu">
                            <li class="nav-item"> <a class="nav-link" href="solicitud?accion=asignar"> Asignar Solicitud Pend.</a></li>
                            <li class="nav-item"> <a class="nav-link" href="solicitud?accion=no_finalizadas"> Solicitud No Finalizadas</a></li>
                            <li class="nav-item"> <a class="nav-link" href="solicitud?accion=finalizadas"> Solicitud Finalizadas</a></li>
                        </ul>
                    </c:if>

                    <c:if  test="${sessionScope.tipoUsuario == 'E'}">
                        <ul class="nav flex-column sub-menu">
                            <li class="nav-item"> <a class="nav-link" href="solicitud?accion=por_atender"> Mis Solicitud por atender.</a></li>
                            <li class="nav-item"> <a class="nav-link" href="solicitud?accion=finalizadas"> Solicitud Finalizadas</a></li>
                        </ul>
                    </c:if>
                </div> 
            </li>

            <c:if  test="${sessionScope.tipoUsuario == 'S'}">
                <li class="nav-item">
                    <a class="nav-link" data-toggle="collapse" href="#reporte" aria-expanded="false" aria-controls="auth">
                        <i class="mdi mdi-chart-pie menu-icon"></i>
                        <span class="menu-title">Reporte</span>
                        <i class="menu-arrow"></i>
                    </a>
                    <div class="collapse" id="reporte">
                        <ul class="nav flex-column sub-menu">
                            <li class="nav-item"> <a class="nav-link" href="solicitud?accion=estadistica"> Estadistica solicitud.</a></li>
                        </ul>
                    </div> 
                </li>
            </c:if>
        </c:if>

        <c:if  test="${sessionScope.tipoUsuario == 'C'}">
            <li class="nav-item">
                <a class="nav-link" data-toggle="collapse" href="#solicitud2" aria-expanded="false" aria-controls="auth">
                    <i class="mdi mdi-file-document menu-icon"></i>
                    <span class="menu-title">Solicitud</span>
                    <i class="menu-arrow"></i>
                </a>
                <div class="collapse" id="solicitud2">
                    <ul class="nav flex-column sub-menu">
                        <li class="nav-item"> <a class="nav-link" href="solicitud?accion=nuevo"> Nueva Solicitud </a></li>
                        <li class="nav-item"> <a class="nav-link" href="solicitud?accion=listar"> Mis solicitudes </a></li>
                    </ul>
                </div> 
            </li>
        </c:if>


    </ul>
</nav>