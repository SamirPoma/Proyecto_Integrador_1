package dao;

import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import modelo.Cliente;
import modelo.Producto;

public class ProductoDao {

    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection cn = null;

    public int Registrar(Producto c) {
        int res = 0;
        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("insert into Producto(idProducto,nombre,precio,stock,estadoProducto) values(?,?,?,?,?)");
            ps.setInt(1, c.getIdProducto());
            ps.setString(2, c.getNombre());
            ps.setDouble(3, c.getPrecio());
            ps.setInt(4, c.getStock());
            ps.setString(5, c.getEstadoProducto());
            res = ps.executeUpdate();

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

    public int Actualizar(Producto c) {
        int res = 0;
        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("update Producto set nombre=?,precio=?,stock=?,estadoProducto=? where idProducto = ?");
            ps.setString(1, c.getNombre());
            ps.setDouble(2, c.getPrecio());
            ps.setInt(3, c.getStock());
            ps.setString(4, c.getEstadoProducto());
            ps.setInt(5, c.getIdProducto());
            res = ps.executeUpdate();

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

    public int Eliminar(int id) {
        int res = 0;
        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("delete from Producto where idProducto = ?");
            ps.setInt(1, id);
            res = ps.executeUpdate();

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

    public Producto BuscarPorId(int id) {
        Producto c = null;

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idProducto,nombre,precio,stock,estadoProducto from Producto where idProducto = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                c = new Producto();
                c.setIdProducto(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setPrecio(rs.getDouble(3));
                c.setStock(rs.getInt(4));
                c.setEstadoProducto(rs.getString(5));
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

    public Producto BuscarPorNombre(String nombre, int id) {
        Producto c = null;

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idProducto,nombre,precio,stock,estadoProducto from Producto"
                    + "  where nombre = ? and (idProducto != ? or ?=0)");
            ps.setString(1, nombre);
            ps.setInt(2, id);
            ps.setInt(3, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                c = new Producto();
                c.setIdProducto(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setPrecio(rs.getDouble(3));
                c.setStock(rs.getInt(4));
                c.setEstadoProducto(rs.getString(5));
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

    public ArrayList<Producto> Listado() {
        ArrayList<Producto> lista = new ArrayList<>();

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idProducto,nombre,precio,stock,estadoProducto from Producto");
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto c = new Producto();
                c.setIdProducto(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setPrecio(rs.getDouble(3));
                c.setStock(rs.getInt(4));
                c.setEstadoProducto(rs.getString(5));
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

    public int MaximoId() {
        int res = 0;

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select IFNULL(MAX(idProducto) , 100) + 1 from Producto");
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
    
    public ArrayList<Producto> ListadoActivos() {
        ArrayList<Producto> lista = new ArrayList<>();

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idProducto,nombre,precio,stock,estadoProducto"
                    + "  from Producto"
                    + " where estadoProducto ='A' and  stock >0 order by nombre asc ");
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto c = new Producto();
                c.setIdProducto(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setPrecio(rs.getDouble(3));
                c.setStock(rs.getInt(4));
                c.setEstadoProducto(rs.getString(5));
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
}
