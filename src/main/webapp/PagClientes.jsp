<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Clientes</title>

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
                                Clientes
                            </div>
                            <div class="panel-body">
                                <a href="" class="btn btn-primary"  data-toggle="modal" data-target="#modalfrmGuardar"><i class="fa fa-plus"></i>Nuevo</a>
                                <br><br>
                                <div id="listado"></div>
                            </div>

                        </div>

                    </div>
                </div>

            </div>
        </div>
        <div class="modal fade top-space-0" id="modalfrmGuardar" tabindex="-1" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content cursor-pointer" data-toggle="modal" data-target="#modal-1">
                    <div class="modal-header bg-primary">
                        <h4 class="modal-title">Nuevo Cliente</h4>
                    </div>
                    <form id="frmGuardar" method="post">
                        <div class="modal-body">
                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">DNI <span style="color: red;">*</span></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="dni" name="dni" maxlength="8">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Nombres <span style="color: red;">*</span></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="nombres" name="nombres" maxlength="40">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Celular <span style="color: red;">*</span></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="celular" name="celular" maxlength="9">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Correo electrónico<span style="color: red;">*</span></label>
                                <div class="col-sm-9">
                                    <input type="email" class="form-control" id="correo" name="correo" maxlength="30">
                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <input type="hidden" name="accion" id="accion" value="guardar">
                            <button type="submit" class="btn btn-primary">Guardar</button>
                            <a href="#" class="btn btn-default" data-dismiss="modal">Cerrar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <div class="modal fade top-space-0" id="modalfrmEditar" tabindex="-1" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content cursor-pointer" data-toggle="modal" data-target="#modal-1">
                    <div class="modal-header bg-primary">
                        <h4 class="modal-title">Editar Cliente</h4>
                    </div>
                    <form id="frmEditar" method="post">
                        <div class="modal-body">
                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">DNI <span style="color: red;">*</span></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="dniM" name="dni" maxlength="8">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Nombres <span style="color: red;">*</span></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="nombresM" name="nombres" maxlength="40">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Celular <span style="color: red;">*</span></label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="celularM" name="celular" maxlength="9">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-sm-3 col-form-label">Correo electrónico<span style="color: red;">*</span></label>
                                <div class="col-sm-9">
                                    <input type="email" class="form-control" id="correoM" name="correo" maxlength="30">
                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <input type="hidden" name="id_cliente" id="id_cliente">
                            <input type="hidden" name="accion" id="accion" value="editar">
                            <button type="submit" class="btn btn-primary">Guardar</button>
                            <a href="#" class="btn btn-default" data-dismiss="modal">Cerrar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="includes/ResourceJs.jsp" />

    </body>

    <script type="text/javascript">
        $(document).ready(function () {
            Listar();
        });


        $(document).ready(function () {
            $("form").each(function () {
                var formulario = ($(this));
                $(formulario).validate({
                    rules: {
                        dni: {
                            required: true
                        },
                        nombres: {
                            required: true
                        },
                        celular: {
                            required: true
                        },
                        correo: {
                            required: true
                        }
                    },
                    messages: {
                        dni: {
                            required: "El campo es requerido."
                        },
                        nombres: {
                            required: "El campo es requerido."
                        },
                        celular: {
                            required: "El campo es requerido."
                        },
                        correo: {
                            required: "El campo es requerido."
                        }
                    }, submitHandler: function (form) {
                        Guardar(form.id);
                    }
                });
            });
        });

        function Guardar(form) {
            $.ajax({
                type: "post",
                url: "ControlCliente",
                data: $("#" + form).serialize(),
                success: function (data) {
                    if (data === "OK") {
                        bootbox.alert("Datos guardados correctamente.!");
                        $("#modal" + form).modal("hide");
                        $("#" + form)[0].reset();
                        Listar();
                    } else {
                        bootbox.alert(data);
                    }
                },
                error: function (xhr, status) {
                    bootbox.alert("No se ha podido procesar información.");
                }
            });
        }

        function Listar() {
            $.ajax({
                type: "GET",
                url: "ControlCliente",
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

        function CargarDatos(id) {
            $.ajax({
                type: "GET",
                url: "ControlCliente",
                dataType: 'json',
                data: {
                    accion: "buscar",
                    id: id
                },
                success: function (data) {
                    $("#id_cliente").val(data.idCliente);
                    $("#celularM").val(data.celular);
                    $("#dniM").val(data.dni);
                    $("#nombresM").val(data.nombres);
                    $("#correoM").val(data.correo);
                    $("#modalfrmEditar").modal("show");
                },
                error: function (xhr) {
                    bootbox.alert("No se ha podido encontrar datos.");
                }
            });
        }

        function Confirmar(id) {
            bootbox.confirm({
                message: "¿Está seguro que desea eliminar el cliente con id " + id + "?",
                buttons: {
                    confirm: {
                        label: 'Eliminar',
                        className: 'btn-primary'
                    },
                    cancel: {
                        label: 'Cancelar',
                        className: 'btn-default'
                    }
                },
                callback: function (data) {
                    if (data) {
                        Eliminar(id);
                    }
                }
            });
        }

        function Eliminar(id) {
            $.ajax({
                type: "get",
                url: "ControlCliente",
                data: {
                    accion:"eliminar",
                    id: id
                },
                success: function (data) {
                    if (parseInt(data) > 0) {
                        Listar();
                           bootbox.alert("Registro eliminado correctamente.");
                    } else {
                        bootbox.alert("No se ha podido eliminar registro por una dependencia de llave foránea.");
                    }
                },
                error: function (xhr, status, errorThrown) {
                    bootbox.alert("No se ha podido procesar información.");
                }
            });
        }
    </script>
</html>
