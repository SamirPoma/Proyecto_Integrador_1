<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Ventas</title>

        <jsp:include page="includes/ResourceCss.jsp" />
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="includes/Navbar.jsp" />
            <c:if test="${sessionScope.usuario == null}">
                <c:redirect url = "index.jsp"/>
            </c:if>
            <br>
            <div id="page-wrapper">
                <div class="container-fluid">

                    <div class="col-lg-12">   
                        <br>
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                Ventas
                            </div>
                            <div class="panel-body">
                                <br>
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>Serie</th>
                                            <th>Cliente</th>
                                            <th>Empleado</th>
                                            <th>Fecha</th>
                                            <th>Monto</th>
                                            <th>Detalle</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="dato" items="${ventas}">
                                            <tr >
                                                <td>${dato.numSerie}</td>
                                                <td>${dato.nomCliente}</td>
                                                <td>${dato.nomEmpleado}</td>
                                                <td>${dato.fecha}</td>
                                                <td>${dato.monto}</td>
                                                <td>
                                                    <a href="javascript:void(0)" onclick="CargarDatos(${dato.idVentas})"   class="btn btn-info" title="Ver detalle"><i class="fa fa-eye"></i></a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${ventas.size()==0}">
                                            <tr>
                                                <td colspan="5" class="text-center">No se encontraron datos</td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>

                        </div>

                    </div>
                </div>

            </div>
        </div>


        <div class="modal fade top-space-0" id="modalDetalle" tabindex="-1" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content cursor-pointer" data-toggle="modal" data-target="#modal-1">
                    <div class="modal-header bg-primary">
                        <h4 class="modal-title">Detalle Venta</h4>
                    </div>

                    <div class="modal-body">
                        <div id="listado"></div>

                    </div>

                    <div class="modal-footer">

                        <a href="#" class="btn btn-default" data-dismiss="modal">Cerrar</a>
                    </div>

                </div>
            </div>
        </div>
        <jsp:include page="includes/ResourceJs.jsp" />

    </body>

    <script type="text/javascript">

        function CargarDatos(id) {
            $.ajax({
                type: "GET",
                url: "ControlVenta",
                data: {
                    accion: "detalleVenta",
                    id: id
                },
                success: function (data) {
                    $("#listado").html(data);
                    $("#modalDetalle").modal("show");
                },
                error: function (xhr) {
                    bootbox.alert("No se ha podido encontrar datos.");
                }
            });
        }


    </script>
</html>
