package dao;

import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Compra;
import modelo.DetalleVenta;
import modelo.Producto;
import modelo.Ventas;

public class VentaDao {

    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection cn = null;
    CallableStatement cs = null;

    public int RegistroCompra(List<Compra> lista, Ventas v) {
        int res = 0;

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("insert into Ventas(idVentas,idCliente,idEmpleado,numSerie,FechaVenas,EstadoVenta,monto)"
                    + " values(?,?,?,?,?,?,?)");
            ps.setInt(1, v.getIdVentas());
            ps.setInt(2, v.getIdCliente());
            ps.setInt(3, v.getIdEmpleado());
            ps.setString(4, v.getNumSerie());
            ps.setString(5, v.getFecha());
            ps.setString(6, v.getEstadoVenta());
            ps.setDouble(7, v.getMonto());
            res = ps.executeUpdate();

            if (res > 0) {
                cs = cn.prepareCall("{call sp_insertar_detalle(?,?,?,?)}");

                for (Compra c : lista) {
                    cs.setInt(1, v.getIdVentas());
                    cs.setInt(2, c.getIdProducto());
                    cs.setInt(3, c.getCantidad());
                    cs.setDouble(4, c.getPrecio());
                    cs.executeUpdate();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception ex) {
            }
        }

        return res;
    }

    public int MaximoId() {
        int res = 0;

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select IFNULL(MAX(idVentas) , 0) + 1 from Ventas");
            rs = ps.executeQuery();

            if (rs.next()) {
                res = rs.getInt(1);
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

        return res;
    }

    public String GenerarSerie() {
        String res = "";

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select LPAD(ifnull(max(NumSerie),0)+1, 5 , '0') from Ventas");
            rs = ps.executeQuery();

            if (rs.next()) {
                res = rs.getString(1);
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

        return res;
    }

    public ArrayList<Ventas> Listado() {
        ArrayList<Ventas> lista = new ArrayList<>();

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idVentas,numSerie,fechaVenas,monto,estadoVenta , e.nombres , c.nombres "
                    + " from ventas v inner join cliente c "
                    + " on c.idCliente = v.idCliente "
                    + " inner join Empleado e "
                    + " on e.idEmpreado = v.idEmpleado");
            rs = ps.executeQuery();

            while (rs.next()) {
                Ventas c = new Ventas();
                c.setIdVentas(rs.getInt(1));
                c.setNumSerie(rs.getString(2));
                c.setFecha(rs.getString(3));
                c.setMonto(rs.getDouble(4));
                c.setEstadoVenta(rs.getString(5));
                c.setNomEmpleado(rs.getString(6));
                c.setNomCliente(rs.getString(7));
                lista.add(c);
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

        return lista;
    }

    public ArrayList<DetalleVenta> listadoDetalleVenta(int idVenta) {
        ArrayList<DetalleVenta> lista = new ArrayList<>();

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select nombre , cantidad , precio "
                    + "from detalle_ventas d inner join producto p "
                    + "on p.idProducto = d.idProducto "
                    + "where idVentas = ?");
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();

            while (rs.next()) {
                DetalleVenta c = new DetalleVenta();
                c.setNombre(rs.getString(1));
                c.setCantidad(rs.getInt(2));
                c.setPrecio(rs.getDouble(3));
                
                lista.add(c);
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

        return lista;
    }

    public Ventas BuscarPorId(int idVenta) {
        Ventas c = null;

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idVentas,numSerie,fechaVenas,monto,estadoVenta , e.nombres , c.nombres "
                    + " from ventas v inner join cliente c "
                    + " on c.idCliente = v.idCliente "
                    + " inner join Empleado e "
                    + " on e.idEmpreado = v.idEmpleado"
                    + " where idVentas = ?");
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();

            if (rs.next()) {
                c = new Ventas();
                c.setIdVentas(rs.getInt(1));
                c.setNumSerie(rs.getString(2));
                c.setFecha(rs.getString(3));
                c.setMonto(rs.getDouble(4));
                c.setEstadoVenta(rs.getString(5));
                c.setNomEmpleado(rs.getString(6));
                c.setNomCliente(rs.getString(7));
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

        return c;
    }

}
