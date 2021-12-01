<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link href="./css/bootstrap.min.css" rel="stylesheet">
        <link href="./css/metisMenu.min.css" rel="stylesheet">
        <link href="./css/startmin.css" rel="stylesheet">
        <link href="./css/font-awesome.min.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="login-panel panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Login</h3>
                        </div>
                        <div class="panel-body">
                            <form method="post" action="ControlLogin">
                                <c:if test = "${mensaje_error !=null}">
                                    <div class="alert alert-danger mt-3">
                                        <strong>Mensaje! </strong><c:out value = "${mensaje_error}"/>
                                    </div>
                                </c:if>

                                <c:if test = "${mensaje_success !=null}">
                                    <div class="alert alert-success mt-3">
                                        <strong>Mensaje! </strong><c:out value = "${mensaje_success}"/>
                                    </div>
                                </c:if>


                                <fieldset>
                                    <div class="form-group">
                                        <label>Codigo Empleado</label>
                                        <input class="form-control" placeholder="Codigo" name="id" type="number" autofocus min="1" >
                                    </div>
                                    <div class="form-group">
                                        <label>Contraseña</label>
                                        <input class="form-control" placeholder="Contraseña" name="pass" type="password" >
                                    </div>
                                    <input type="hidden" name="accion" value="login">
                                    <button type="submit" class="btn btn-lg btn-success btn-block">Iniciar Sesión</button>

                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="./js/jquery.min.js"></script>
        <script src="./js/bootstrap.min.js"></script>
    </body>
</html>
