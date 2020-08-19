/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ds.kontrolnaya.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ds.kontrolnaya.db.ApachePOIExcelRead;
import ds.kontrolnaya.db.Driver;


public class NewServlet extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }

    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Enter doGet");

        String action = request.getParameter("action");
        Driver db = new Driver();
        RequestDispatcher dispatcher;
        switch (action == null ? "getInfo" : action) {
            case "getInfo":
                //ApachePOIExcelRead.readExcel();
                request.setAttribute("data", db.getInfo());
                dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
                break;
            default:
               // ApachePOIExcelRead.readExcel();
                request.setAttribute("data", db.getInfo());
                dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
