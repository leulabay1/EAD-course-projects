package com.tms.model;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

import java.io.IOException;

@WebServlet("/create-task")
public class CreateTaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String date = request.getParameter("dueDate");
        Date dueDate = Date.valueOf(date);
        String priority = request.getParameter("priority");

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO task (title, description, due_date, priority, completed) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setDate(3, dueDate);
                preparedStatement.setString(4, priority);
                preparedStatement.setBoolean(5, false);

                // Execute the update
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // com.tms.model.Task creation successful
                    response.sendRedirect("task-servlet");
                } else {
                    // com.tms.model.Task creation failed
                    response.sendRedirect("createTask.jsp?error=true");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("createTask.jsp?error=true");
        }

    }
}
