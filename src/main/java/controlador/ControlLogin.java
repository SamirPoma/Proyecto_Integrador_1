package controlador;

import dao.LoginDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Empleado;

public class ControlLogin extends HttpServlet {

    LoginDao obj = new LoginDao();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion") != null ? request.getParameter("accion") : "";

        if (accion.equals("login")) {
            IniciarSesion(request, response);
        } else if (accion.equals("logout")) {
            CerrarSesion(request, response);
        } else {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }

    }

    protected void IniciarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        Empleado user = new Empleado();
        user.setIdEmpleado(Integer.parseInt(request.getParameter("id")));
        user.setPassword(request.getParameter("pass"));

        String pagina = "";

        Empleado e = obj.IniciarSesion(user);

        if (e != null) {
            sesion.setAttribute("usuario", e);
            pagina = "ControlVenta?accion=listado";
        } else {
            request.setAttribute("mensaje_error", "Usuario y/o incorrecto.");
            pagina = "/index.jsp";
        }

        request.getRequestDispatcher(pagina).forward(request, response);
    }

    protected void CerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession sesion = request.getSession();
        sesion.setAttribute("usuario", null);

        request.setAttribute("mensaje_success", "Sesión Cerrada correctamente..");

        String pagina = "/index.jsp";

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
