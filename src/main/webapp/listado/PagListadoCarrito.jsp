<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Carrito</title>
    </head>
    <body>
        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
            <thead class="bg-primary">
                <tr>
                    <th>#</th>
                    <th>Producto</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Total</th>
                    <th>Accion</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="dato" items="${sessionScope.carrito}" varStatus="loop">
                    <tr >
                        <td>${loop.index + 1}</td>
                        <td>${dato.nombre}</td>
                        <td>${dato.precio}</td>
                        <td>${dato.cantidad}</td>
                        <td>${dato.Total()}</td>
                       <!--
                        <td>
                            <fmt:formatNumber type = "number"  maxIntegerDigits = "2"  value = "${dato.Total()}" />
                        </td>
                       -->
                        <td>
                            <a href="javascript:void(0)" onclick="EliminarCarrito(${loop.index})"   class="btn btn-danger"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${sessionScope.carrito.size()==0}">
                    <tr>
                        <td colspan="6" class="text-center">No se encontraron datos</td>
                    </tr>
                </c:if>

                <c:if test="${sessionScope.carrito.size()>0}">
                    <tr>
                        <td colspan="4" class="text-right">Total</td>
                        <td colspan="2" class="text-left" style="font-weight: bold; font-size: 20px;">
                            ${total}
                          <!--
                            <fmt:formatNumber type = "number"  maxIntegerDigits = "2"  value = "${total}" />
                          -->
                        </td>
                    </tr>
                </c:if>
            </tbody>
    </body>
</html>
