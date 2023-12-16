package com.tms.model;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

import java.io.IOException;

@WebServlet("/complete-task")
public class CompleteTaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

        String taskIdParam = req.getParameter("taskId");

        if (taskIdParam != null && !taskIdParam.isEmpty()) {
            try {
                // Convert task ID to integer
                int taskId = Integer.parseInt(taskIdParam);

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String sql = "UPDATE task SET completed = true WHERE id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setInt(1, taskId);
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            // Redirect back to the task list page after completion
                            res.sendRedirect("task-servlet");
                            return;
                        } else {
                            // Handle task completion failure (e.g., task not found)
                            res.getWriter().println("Failed to complete the task.");
                        }
                    }
                }
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        }

        // Handle invalid or missing task ID parameter
        res.getWriter().println("Invalid task ID");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{



    }
}
