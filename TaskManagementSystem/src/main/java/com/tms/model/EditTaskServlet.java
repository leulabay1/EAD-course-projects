package com.tms.model;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

import java.io.IOException;

@WebServlet("/edit-task")
public class EditTaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{


    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("I am here");
        String taskIdParam = request.getParameter("taskId");
        System.out.println(taskIdParam);
        String newTitle = request.getParameter("title");
        String newDescription = request.getParameter("description");

        if (taskIdParam != null && !taskIdParam.isEmpty()) {
            try {
                // Convert task ID to integer
                int taskId = Integer.parseInt(taskIdParam);

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String sql = "UPDATE task SET title = ?, description = ? WHERE id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setString(1, newTitle);
                        preparedStatement.setString(2, newDescription);
                        preparedStatement.setInt(3, taskId);

                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            // Redirect back to the task list page after editing
                            response.sendRedirect("task-servlet");
                            return;
                        } else {
                            // Handle task editing failure (e.g., task not found)
                            response.getWriter().println("Failed to edit the task.");
                        }
                    }
                }
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        }

        // Handle invalid or missing task ID parameter
        response.getWriter().println("Invalid task ID");


    }
}
