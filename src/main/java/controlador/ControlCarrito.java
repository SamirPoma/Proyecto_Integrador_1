package controlador;

import dao.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.*;

public class ControlCarrito extends HttpServlet {

    ProductoDao prodDao = new ProductoDao();
    VentaDao ventaDao = new VentaDao();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";

        if (accion.equals("agregar")) {
            AgregarCarrito(request, response);
        } else if (accion.equals("listar")) {
            ListarCarrito(request, response);
        } else if (accion.equals("eliminar")) {
            EliminarCarrito(request, response);
        } else if (accion.equals("procesarCompra")) {
            ProcesarCompra(request, response);
        }
    }

    protected void ProcesarCompra(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        List<Compra> lista = ObtenerListaCarrito(request);
        try {

            Empleado e = (Empleado) sesion.getAttribute("usuario");

            Ventas v = new Ventas();
            v.setIdCliente(Integer.parseInt(request.getParameter("cliente")));
            v.setFecha(request.getParameter("fecha"));
            v.setMonto(TotalCarrito(lista));
            v.setNumSerie(ventaDao.GenerarSerie());
            v.setIdVentas(ventaDao.MaximoId());
            v.setIdEmpleado(e.getIdEmpleado());
            v.setEstadoVenta("A");

            int res = ventaDao.RegistroCompra(lista, v);

            if (res > 0) {
                lista.removeAll(lista);
                sesion.setAttribute("carrito", lista);
            }

            out.print(res);
        } catch (Exception ex) {
            out.print(ex.toString());
        }

    }

    protected void EliminarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int indice = Integer.parseInt(request.getParameter("indice"));
        HttpSession sesion = request.getSession();
        List<Compra> lista = (ArrayList) sesion.getAttribute("carrito");
        lista.remove(indice);

        sesion.setAttribute("carrito", lista);

        out.print(lista.size());
    }

    protected void ListarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession sesion = request.getSession();
        List<Compra> lista = ObtenerListaCarrito(request);

        sesion.setAttribute("carrito", lista);
        request.setAttribute("total", TotalCarrito(lista));

        String pagina = "listado/PagListadoCarrito.jsp";
        request.getRequestDispatcher(pagina).forward(request, response);

    }

    protected void AgregarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        List<Compra> lista = ObtenerListaCarrito(request);

        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        int codigoProducto = Integer.parseInt(request.getParameter("producto"));

        Producto p = prodDao.BuscarPorId(codigoProducto);
        Compra c = null;

        int posicion = BuscarProducto(lista, codigoProducto);

        if (posicion == -1) {
            c = new Compra();

            if (c.getStock() < c.getCantidad()) {
                c.setCantidad(c.getStock());
            }

            c.setIdProducto(codigoProducto);
            c.setNombre(p.getNombre());
            c.setPrecio(p.getPrecio());
            c.setStock(p.getStock());
            c.setCantidad(cantidad);
            lista.add(c);
        } else {
            c = lista.get(posicion);
            c.setCantidad(c.getCantidad() + cantidad);

            if (c.getStock() < c.getCantidad()) {
                c.setCantidad(c.getStock());
            }

            lista.set(posicion, c);
        }

        sesion.setAttribute("carrito", lista);
        out.print(lista.size());
    }

    public List<Compra> ObtenerListaCarrito(HttpServletRequest request) {
        List<Compra> lista;

        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("carrito") == null) {
            lista = new ArrayList<>();
        } else {
            lista = (ArrayList<Compra>) sesion.getAttribute("carrito");
        }

        return lista;
    }

    public int BuscarProducto(List<Compra> lista, int codigo) {
        for (int i = 0; i < lista.size(); i++) {
            Compra c = lista.get(i);

            if (c.getIdProducto() == codigo) {
                return i;
            }
        }

        return -1;
    }

    public double TotalCarrito(List<Compra> lista) {
        double total = 0;
        for (int i = 0; i < lista.size(); i++) {
            Compra c = lista.get(i);

            total += c.Total();
        }

        return total;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
