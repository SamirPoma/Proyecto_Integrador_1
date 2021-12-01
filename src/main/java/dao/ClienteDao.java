package dao;

import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import modelo.Cliente;

public class ClienteDao {

    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection cn = null;

    public int Registrar(Cliente c) {
        int res = 0;
        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("insert into cliente(idCliente,DNI,nombres,celular,correo) values(?,?,?,?,?)");
            ps.setInt(1, c.getIdCliente());
            ps.setString(2, c.getDni());
            ps.setString(3, c.getNombres());
            ps.setString(4, c.getCelular());
            ps.setString(5, c.getCorreo());
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

    public int Actualizar(Cliente c) {
        int res = 0;
        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("update cliente set DNI=?,nombres=?,celular=?,correo=? where idCliente = ?");
            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombres());
            ps.setString(3, c.getCelular());
            ps.setString(4, c.getCorreo());
            ps.setInt(5, c.getIdCliente());
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
            ps = cn.prepareStatement("delete from cliente where idCliente = ?");
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

    public Cliente BuscarPorId(int id) {
        Cliente c = null;

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idCliente,DNI,nombres,celular,correo from cliente where idCliente = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                c = new Cliente();
                c.setIdCliente(rs.getInt(1));
                c.setDni(rs.getString(2));
                c.setNombres(rs.getString(3));
                c.setCelular(rs.getString(4));
                c.setCorreo(rs.getString(5));

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

    public Cliente BuscarPorDNI(String dni, int id) {
        Cliente c = null;

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idCliente,DNI,nombres,celular,correo from cliente"
                    + "  where DNI = ? and (idCliente != ? or ?=0)");
            ps.setString(1, dni);
            ps.setInt(2, id);
            ps.setInt(3, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                c = new Cliente();
                c.setIdCliente(rs.getInt(1));
                c.setDni(rs.getString(2));
                c.setNombres(rs.getString(3));
                c.setCelular(rs.getString(4));
                c.setCorreo(rs.getString(5));
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

    public ArrayList<Cliente> Listado() {
        ArrayList<Cliente> lista = new ArrayList<>();

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idCliente,DNI,nombres,celular,correo from cliente");
            rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt(1));
                c.setDni(rs.getString(2));
                c.setNombres(rs.getString(3));
                c.setCelular(rs.getString(4));
                c.setCorreo(rs.getString(5));
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
            ps = cn.prepareStatement("select IFNULL(MAX(idCliente) , 100) + 1 from cliente");
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
}
