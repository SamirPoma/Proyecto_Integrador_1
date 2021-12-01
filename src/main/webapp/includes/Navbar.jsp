<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String URL = request.getRequestURL().toString();
%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">Sistema Web</a>
    </div>

    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
    </button>

    <ul class="nav navbar-right navbar-top-links">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i><c:out value = "${sessionScope.usuario.nombres}"/> &nbsp;&nbsp; <b class="caret"></b>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li class="divider"></li>
                <li><a href="ControlLogin?accion=logout"><i class="fa fa-sign-out fa-fw"></i> Cerrar Sesión</a>
                </li>
            </ul>
        </li>
    </ul>
    <!-- /.navbar-top-links -->

    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li>
                    <a href="ControlCliente?accion=inicio" class=<%= URL.contains("PagClientes") ? "active" : ""%>><i class="fa fa-user-circle fa-fw"></i>Clientes</a>
                </li>
                <li>
                    <a href="ControlEmpleados?accion=inicio" class=<%= URL.contains("PagEmpleados") ? "active" : ""%>><i class="fa fa-user fa-fw"></i>Empleados</a>
                </li>
                <li>
                    <a href="ControlProducto?accion=inicio"  class=<%= URL.contains("PagProductos") ? "active" : ""%>><i class="fa fa-product-hunt"></i> Producto</a>
                </li>
           
      
                <li>
                    <a href="#"><i class="fa fa-shopping-cart"></i> Ventas<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="ControlVenta?accion=nueva">Nueva Venta</a>
                        </li>
                        <li>
                            <a href="ControlVenta?accion=listado">Listado Ventas</a>
                        </li>
                    </ul>
              
                </li>
     
            </ul>
        </div>
    </div>
</nav>