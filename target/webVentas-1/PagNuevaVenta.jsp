
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>

        <title>Nueva Venta</title>

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

                            </div>
                            <div class="panel-body">
                                <form id="frmProcesar">
                                    <div class="form-group row">
                                        <div class="col-sm-8">
                                            <h2>Boleta de Venta</h2>
                                        </div>
                                        <div class="col-sm-2">
                                            <div class="form-group">
                                                <label >Serie</label>
                                                <label class="form-control" id="serie"><c:out value="${serie}" /></label>
                                            </div>

                                        </div>
                                        <div class="col-sm-2">
                                            <div class="form-group">
                                                <label >Fecha</label>
                                                <label class="form-control" id="fechaLabel"></label>
                                                <input type="hidden"  name="fecha" id="fecha" />
                                            </div>
                                        </div>
                                    </div>
                                    <hr>

                                    <div class="form-group row">
                                        <label  class="col-sm-2 col-form-label">Cliente</label>
                                        <div class="col-sm-6">
                                            <select class="form-control" name="cliente" id="cliente">
                                                <option value="0">::: Seleccione :::</option>
                                                <c:forEach var="dato" items="${clientes}">
                                                    <option value="${dato.idCliente}">${dato.nombres}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label  class="col-sm-2 col-form-label">Producto</label>
                                        <div class="col-sm-6">
                                            <select class="form-control" name="producto" id="producto" onchange="CargarProducto()">
                                                <option value="0">::: Seleccione :::</option>
                                                <c:forEach var="dato" items="${productos}">
                                                    <option value="${dato.idProducto}">${dato.nombre}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label  class="col-sm-2 col-form-label">Stock</label>
                                        <div class="col-sm-6">
                                            <label class="form-control" id="stock" ></label>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label  class="col-sm-2 col-form-label">Cantidad</label>
                                        <div class="col-sm-6">
                                            <input type="number" name="cantidad" id="cantidad" min="1" value="1"  class="form-control">
                                        </div>
                                    </div>
                                    <input type="hidden" name="accion" id="accion" value="procesarCompra">
                                    <button type="button" onclick="AgregarCarrito()" class="btn btn-primary" id="btnAgregarProducto" ><i class="fa fa-plus"></i> Agregar Productos</button>

                                    <button type="button" onclick="ProcesarCompra()"  class="btn btn-success" id="btnCompra"><i class="fa fa-shopping-bag"></i> Procesar Compra</button>

                                </form>

                                <br><br>
                                <div id="listado"></div>
                            </div>

                        </div>

                    </div>
                </div>

            </div>
        </div>

        <jsp:include page="includes/ResourceJs.jsp" />

    </body>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#fecha").val(ObtenerFecha());
            $("#fechaLabel").html(ObtenerFecha());
            Listar();
            CargarProducto();
            VisualizarBoton(${sessionScope.carrito.size()>0} ? true : false);
        });

        function ProcesarCompra() {
            var cliente = parseInt($("#cliente").val());

            if (cliente === 0) {
                bootbox.alert("Por favor seleccione un cliente.");
                return;
            }

            $.ajax({
                type: "GET",
                url: "ControlCarrito",
                data: $("#frmProcesar").serialize(),
                success: function (data) {
                    if (parseInt(data) > 0) {
                        Listar();
                        GenerarSerie();
                        bootbox.alert("Venta realizada correctamente.");
                    } else {
                        bootbox.alert("La venta no se ha podido procesar.");
                    }
                },
                error: function (xhr) {
                    bootbox.alert("No se ha podido procesar información.");
                }
            });
        }

        function CargarProducto() {
            var id = parseInt($("#producto").val());

            if (id === 0) {
                $("#stock").html("Sin datos");
                $("#cantidad").val("Sin datos");
                Habilitar(true);
            } else {
                $("#cantidad").val("1");
                Habilitar(false);

                $.ajax({
                    type: "GET",
                    url: "ControlProducto",
                    dataType: 'json',
                    data: {
                        accion: "buscar",
                        id: id
                    },
                    success: function (data) {
                        $("#stock").html(data.stock);
                    },
                    error: function (xhr) {
                        bootbox.alert("No se ha podido procesar información.");
                    }
                });
            }
        }

        function Habilitar(estado) {
            $("#cantidad").prop("disabled", estado);
            $("#stock").prop("disabled", estado);
            $("#btnAgregarProducto").prop("disabled", estado);
        }

        function VisualizarBoton(estado) {

            if (estado) {
                $("#btnCompra").show();
            } else {
                $("#btnCompra").hide();
            }
        }

        function AgregarCarrito() {
            var id = parseInt($("#producto").val());
            var cantidad = parseInt($("#cantidad").val());
            var stock = parseInt($("#stock").html());

            if (cantidad > stock) {
                bootbox.alert("Stock insuficiente.");
                return;
            }

            $.ajax({
                type: "GET",
                url: "ControlCarrito",
                data: {
                    accion: "agregar",
                    producto: id,
                    cantidad: cantidad
                },
                success: function (data) {
                    VisualizarBoton(parseInt(data) > 0 ? true : false);

                    Listar();
                    bootbox.alert("Producto agregado.");
                },
                error: function (xhr) {
                    bootbox.alert("No se ha podido procesar información.");
                }
            });
        }

        function Listar() {
            $.ajax({
                type: "GET",
                url: "ControlCarrito",
                data: {
                    accion: "listar"
                },
                success: function (data) {
                    $("#listado").html(data);
                },
                error: function (xhr) {
                    bootbox.alert("No se ha podido procesar información.");
                }
            });
        }

        function EliminarCarrito(indice) {

            $.ajax({
                type: "GET",
                url: "ControlCarrito",
                data: {
                    accion: "eliminar",
                    indice: indice
                },
                success: function (data) {
                    VisualizarBoton(parseInt(data) > 0 ? true : false);
                    Listar();
                },
                error: function (xhr) {
                    bootbox.alert("No se ha podido eliminar producto del carrito.");
                }
            });
        }

        function ObtenerFecha() {
            var d = new Date,
                    dformat = [d.getFullYear(),
                        padLeft(d.getMonth() + 1), 
                        padLeft(d.getDate())
                    ].join('-');
            return dformat;
        }

        function padLeft(n) {
            return ("00" + n).slice(-2);
        }
        
           function GenerarSerie() {
            $.ajax({
                type: "GET",
                url: "ControlVenta",
                data: {
                    accion: "generarSerie"
                },
                success: function (data) {
                    $("#serie").html(data);
                },
                error: function (xhr) {
                    bootbox.alert("No se ha podido procesar información.");
                }
            });
        }

    </script>
</html>
