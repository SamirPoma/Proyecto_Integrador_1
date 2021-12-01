package controlador;

import dao.ClienteDao;
import dao.ProductoDao;
import dao.VentaDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Cliente;
import modelo.Compra;
import modelo.Producto;

public class ControlVenta extends HttpServlet {

    VentaDao obj = new VentaDao();
    ClienteDao cliDao = new ClienteDao();
    ProductoDao prodDao = new ProductoDao();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";

        if (accion.equals("nueva")) {
            VistaPrincipal(request, response);
        } else if (accion.equals("generarSerie")) {
            GenerarSerie(request, response);
        } else if (accion.equals("listado")) {
            ListadoVentas(request, response);
        } else if (accion.equals("detalleVenta")) {
            ListadoDetalleVentas(request, response);
        }
    }

    protected void ListadoDetalleVentas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String pagina = "listado/PagListadoDetalle.jsp";
        int id = Integer.parseInt(request.getParameter("id"));

        request.setAttribute("cabecera", obj.BuscarPorId(id));
        request.setAttribute("ventas", obj.listadoDetalleVenta(id));
        request.getRequestDispatcher(pagina).forward(request, response);
    }

    protected void ListadoVentas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String pagina = "PagVentas.jsp";

        request.setAttribute("ventas", obj.Listado());
        request.getRequestDispatcher(pagina).forward(request, response);
    }

    protected void GenerarSerie(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.print(obj.GenerarSerie());
    }

    protected void VistaPrincipal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String pagina = "PagNuevaVenta.jsp";

        String serie = obj.GenerarSerie();

        request.setAttribute("serie", serie);
        request.setAttribute("clientes", cliDao.Listado());
        request.setAttribute("productos", prodDao.ListadoActivos());
        request.getRequestDispatcher(pagina).forward(request, response);
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
