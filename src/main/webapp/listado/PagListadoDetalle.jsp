<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pedido</title>
    </head>
    <body>
        <div class="col-sm-6">
            <ul class="list-group">
                <li class="list-group-item">Cliente: <c:out value="${cabecera.nomCliente}" /></li>
                <li class="list-group-item">Empleado: <c:out value="${cabecera.nomEmpleado}" /></li>
                <li class="list-group-item">Fecha: <c:out value="${cabecera.fecha}" /></li>
                <li class="list-group-item">Serie: <c:out value="${cabecera.numSerie}" /></li>
            </ul>
        </div>

        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
            <thead class="bg-primary">
                <tr>
                    <th>#</th>
                    <th>Producto</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="dato" items="${ventas}" varStatus="loop">
                    <tr >
                        <td>${loop.index + 1}</td>
                        <td>${dato.nombre}</td>
                        <td>${dato.precio}</td>
                        <td>${dato.cantidad}</td>
                        <td>
                            <fmt:formatNumber type = "number"  maxIntegerDigits = "2"  value = "${dato.Total()}" />
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
    </body>
</html>
