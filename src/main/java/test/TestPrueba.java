package test;

import dao.ClienteDao;
import dao.LoginDao;
import dao.ProductoDao;
import dao.VentaDao;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import modelo.Compra;
import modelo.Empleado;
import modelo.Ventas;

public class TestPrueba {

    public static void main(String[] args) {

        LoginDao obj = new LoginDao();
        Empleado e = new Empleado();
        e.setIdEmpleado(101);
        e.setPassword("123456");
        Empleado emp = obj.IniciarSesion(e);
        System.out.println(emp);

    }

}
