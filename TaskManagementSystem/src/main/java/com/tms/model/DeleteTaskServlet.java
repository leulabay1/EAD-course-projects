package com.tms.model;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

import java.io.IOException;

@WebServlet("/delete-task")
public class DeleteTaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

        String taskIdParam = req.getParameter("taskId");

        if (taskIdParam != null && !taskIdParam.isEmpty()) {
            try {
                // Convert task ID to integer
                int taskId = Integer.parseInt(taskIdParam);

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String sql = "DELETE FROM task WHERE id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setInt(1, taskId);
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            // Redirect back to the task list page after deletion
                            res.sendRedirect("task-servlet");
                            return;
                        } else {
                            // Handle task deletion failure (e.g., task not found)
                            res.getWriter().println("Failed to delete the task.");
                        }
                    }
                }
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    }
}
