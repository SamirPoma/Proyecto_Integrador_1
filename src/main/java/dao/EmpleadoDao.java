package dao;

import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import modelo.*;

public class EmpleadoDao {

    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection cn = null;

    public int Registrar(Empleado c) {
        int res = 0;
        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("insert into Empleado(idEmpreado,DNI,nombres,celular,password) values(?,?,?,?,?)");
            ps.setInt(1, c.getIdEmpleado());
            ps.setString(2, c.getDni());
            ps.setString(3, c.getNombres());
            ps.setString(4, c.getCelular());
            ps.setString(5, c.getPassword());
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

    public int Actualizar(Empleado c) {
        int res = 0;
        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("update Empleado set DNI=?,nombres=?,celular=?,password=? where idEmpreado = ?");
            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombres());
            ps.setString(3, c.getCelular());
            ps.setString(4, c.getPassword());
            ps.setInt(5, c.getIdEmpleado());
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
            ps = cn.prepareStatement("delete from Empleado where idEmpreado = ?");
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

    public Empleado BuscarPorId(int id) {
        Empleado c = null;

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idEmpreado,DNI,nombres,celular,password from Empleado where idEmpreado = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                c = new Empleado();
                c.setIdEmpleado(rs.getInt(1));
                c.setDni(rs.getString(2));
                c.setNombres(rs.getString(3));
                c.setCelular(rs.getString(4));
                c.setPassword(rs.getString(5));

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

    public Empleado BuscarPorDNI(String dni, int id) {
        Empleado c = null;

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idEmpreado,DNI,nombres,celular,password from Empleado"
                    + "  where DNI = ? and (idEmpreado != ? or ?=0)");
            ps.setString(1, dni);
            ps.setInt(2, id);
            ps.setInt(3, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                c = new Empleado();
                c.setIdEmpleado(rs.getInt(1));
                c.setDni(rs.getString(2));
                c.setNombres(rs.getString(3));
                c.setCelular(rs.getString(4));
                c.setPassword(rs.getString(5));
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

    public ArrayList<Empleado> Listado() {
        ArrayList<Empleado> lista = new ArrayList<>();

        try {
            cn = Conexion.conectar();
            ps = cn.prepareStatement("select idEmpreado,DNI,nombres,celular,password from Empleado");
            rs = ps.executeQuery();

            while (rs.next()) {
                Empleado c = new Empleado();
                c.setIdEmpleado(rs.getInt(1));
                c.setDni(rs.getString(2));
                c.setNombres(rs.getString(3));
                c.setCelular(rs.getString(4));
                c.setPassword(rs.getString(5));
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
            ps = cn.prepareStatement("select IFNULL(MAX(idEmpreado) , 100) + 1 from Empleado");
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
