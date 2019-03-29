/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;

/**
 *
 * @author Vrtex
 */
public class NewServlet extends HttpServlet {
        String krowa = "";

        
    private boolean tryLogIn(HttpServletRequest req)
    {
        String usr = req.getParameter("user");
        String pass = req.getParameter("pass");
        
        if(usr == null || pass == null)
            return false;
        
        if(!usr.equals("admin") || !pass.equals("password"))
            return false;
        
        return true;
    }
    
    private void showGuestMsg(PrintWriter out)
    {
        out.println("<h1>Nie zalogowano</h1>");
        out.println("<form method=\"get\">");
        out.println("<input type=\"text\" name=\"user\" \\>");
        out.println("<input type=\"password\" name=\"pass\" \\>");
        out.println("<input type=\"submit\" value=\"zaloguj\" \\>");
        out.println("</form>");
    }
    
    private void showUserMsg(PrintWriter out)
    {
        out.println("<h1>Zalogowano</h1>");
        out.println("<form method=\"get\">");
        out.println("<input type=\"hidden\" name=\"akcja\" value=\"wyloguj\" \\>");
        out.println("<input type=\"submit\" value=\"wyloguj\" \\>");
        out.println("</form>");
    }
    
    @Override
    public void init(ServletConfig config)
    {
        krowa = config.getInitParameter("krowa");
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(true);
        
        String action = request.getParameter("akcja");
        if(action != null && action.equals("wyloguj"))
            session.setAttribute("zalogowany", false);
        
        Boolean loggedIn = (Boolean)session.getAttribute("zalogowany");
        
        if(loggedIn == null) loggedIn = false;
        
        if(!loggedIn)
        {
            loggedIn = tryLogIn(request);
            if(loggedIn)
                session.setAttribute("zalogowany", true);
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>HELLO</h1>");
            if(loggedIn)
                showUserMsg(out);
            else
                showGuestMsg(out);
            out.println("</body>");
            out.println("</html>");
        }
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
