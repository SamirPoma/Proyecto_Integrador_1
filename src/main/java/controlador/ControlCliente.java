package controlador;

import dao.ClienteDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Cliente;
import modelo.Empleado;
import com.google.gson.Gson;

public class ControlCliente extends HttpServlet {

    ClienteDao cliDao = new ClienteDao();
    Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";

        if (accion.equals("inicio")) {
            VistaPrincipal(request, response);
        } else if (accion.equals("listar")) {
            ListarTodos(request, response);
        } else if (accion.equals("guardar")) {
            GuardarDatos(request, response);
        } else if (accion.equals("buscar")) {
            BuscarDatos(request, response);
        } else if (accion.equals("editar")) {
            EditarDatos(request, response);
        } else if (accion.equals("eliminar")) {
            EliminarDatos(request, response);
        }
    }

    protected void EliminarDatos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));
        int res = cliDao.Eliminar(id);

        out.print(res);
    }

    protected void BuscarDatos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));
        Cliente c = cliDao.BuscarPorId(id);

        out.print(gson.toJson(c));
    }

    protected void EditarDatos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        String msj = "";
        int res = 0;

        Cliente c = new Cliente();
        c.setIdCliente(Integer.parseInt(request.getParameter("id_cliente")));
        c.setDni(request.getParameter("dni"));
        c.setNombres(request.getParameter("nombres"));
        c.setCelular(request.getParameter("celular"));
        c.setCorreo(request.getParameter("correo"));

        if (cliDao.BuscarPorDNI(c.getDni(), c.getIdCliente()) != null) {
            msj = "El dni " + c.getDni() + " ya se encuentra registrado.";
        } else {
            res = cliDao.Actualizar(c);
            if (res > 0) {
                msj = "OK";
            } else {
                msj = "No se ha podido editar datos.Por favor verifique que los datos sean correctos.";
            }

        }

        out.print(msj);
    }

    protected void GuardarDatos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        String msj = "";
        int res = 0;

        Cliente c = new Cliente();
        c.setIdCliente(cliDao.MaximoId());
        c.setDni(request.getParameter("dni"));
        c.setNombres(request.getParameter("nombres"));
        c.setCelular(request.getParameter("celular"));
        c.setCorreo(request.getParameter("correo"));

        if (cliDao.BuscarPorDNI(c.getDni(), 0) != null) {
            msj = "El dni " + c.getDni() + " ya se encuentra registrado.";
        } else {
            res = cliDao.Registrar(c);
        }

        if (res > 0) {
            msj = "OK";
        } else {
            msj = "No se ha podido guardar datos.Por favor verifique que los datos sean correctos.";
        }

        out.print(msj);
    }

    protected void VistaPrincipal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String pagina = "PagClientes.jsp";
        request.getRequestDispatcher(pagina).forward(request, response);
    }

    protected void ListarTodos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String pagina = "/listado/PagListadoClientes.jsp";

        ArrayList<Cliente> lista = cliDao.Listado();

        request.setAttribute("lista", lista);

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
