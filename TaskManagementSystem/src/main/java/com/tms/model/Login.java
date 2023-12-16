package com.tms.model;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/login")
public class Login extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try  {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO user (name, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                // Execute the update
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Registration successful
                    response.sendRedirect("task-servlet");
                } else {
                    // Registration failed
                    response.sendRedirect("registrationFailure.jsp");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("registrationFailed.jsp");
        }
    }

}