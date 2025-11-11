package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        
        if (userDAO.getUserByUsername(username) != null) {
            request.setAttribute("error", "Username already exists");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        if (userDAO.getUserByEmail(email) != null) {
            request.setAttribute("error", "Email already exists");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        
        User user = new User(username, email, password, fullName);
        boolean success = userDAO.addUser(user);
        
        if (success) {
            response.sendRedirect("login.jsp?success=Registration successful");
        } else {
            request.setAttribute("error", "Registration failed");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}