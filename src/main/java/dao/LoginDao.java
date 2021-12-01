package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Cliente;
import modelo.Empleado;

public class LoginDao {

    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection cn = null;

    public Empleado IniciarSesion(Empleado obj) {
        Empleado e = null;

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idEmpreado , dni , nombres , celular , password from empleado where idEmpreado=? and password=?");
            ps.setInt(1, obj.getIdEmpleado());
            ps.setString(2, obj.getPassword());
            rs = ps.executeQuery();

            if (rs.next()) {
                e = new Empleado();
                e.setIdEmpleado(rs.getInt(1));
                e.setDni(rs.getString(2));
                e.setNombres(rs.getString(3));
                e.setCelular(rs.getString(4));
                e.setPassword(rs.getString(5));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (rs != null) {
                    rs.close();
                }

            } catch (Exception ex) {
            }
        }

        return e;
    }
}
