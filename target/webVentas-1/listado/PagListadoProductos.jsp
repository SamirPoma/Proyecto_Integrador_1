<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado</title>
    </head>
    <body>

        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="dato" items="${lista}">
                    <tr >
                        <td>${dato.idProducto}</td>
                        <td>${dato.nombre}</td>
                        <td>${dato.precio}</td>
                        <td>${dato.stock}</td>
                        <c:choose>
                            <c:when test="${dato.estadoProducto=='A'}">
                                <td><span class="badge badge-success" style="background-color: #009933">Activo</span></td>
                            </c:when>
                            <c:otherwise>
                                <td><span class="badge badge-danger" style="background-color: #cc0000;">Inactivo</span></td>
                            </c:otherwise>
                        </c:choose>

                        <td>
                            <a href="javascript:void(0)" onclick="CargarDatos(${dato.idProducto})"   class="btn btn-info"><i class="fa fa-edit"></i></a>
                            <a href="javascript:void(0)" onclick="Confirmar(${dato.idProducto})"   class="btn btn-danger"><i class="fa fa-trash"></i></a>

                        </td>
                    </tr>
                </c:forEach>

            </tbody>
        </table>
    </body>
    <script>
        $(document).ready(function () {
            $('#dataTables-example').DataTable({
                responsive: true
            });
        });
    </script>
</html>
