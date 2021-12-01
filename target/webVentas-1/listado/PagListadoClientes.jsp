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
                    <th>DNI</th>
                    <th>Nombres</th>
                    <th>Celular</th>
                    <th>Correo</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="dato" items="${lista}">
                    <tr >
                        <td>${dato.idCliente}</td>
                        <td>${dato.dni}</td>
                        <td>${dato.nombres}</td>
                        <td>${dato.celular}</td>
                        <td>${dato.correo}</td>
                        <td>
                            <a href="javascript:void(0)" onclick="CargarDatos(${dato.idCliente})"   class="btn btn-info"><i class="fa fa-edit"></i></a>
                            <a href="javascript:void(0)" onclick="Confirmar(${dato.idCliente})"   class="btn btn-danger"><i class="fa fa-trash"></i></a>

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
