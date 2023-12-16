package com.tms.model;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

        String searchTitle = req.getParameter("searchTitle");
        String searchDueDate = req.getParameter("searchDueDate");

        // Perform the search in the database
        List<Task> searchResults = performSearch(searchTitle, searchDueDate);

        // Set the search results as a request attribute
        res.sendRedirect("task-servlet");

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{



    }

    private List<Task> performSearch(String title, String dueDate) {
        List<Task> searchResults = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM task WHERE title LIKE ? AND due_date LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + title + "%");
                preparedStatement.setString(2, "%" + dueDate + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Create Task objects and add them to the search results
                        Task task = new Task();
                        task.setId(resultSet.getInt("id"));
                        task.setTitle(resultSet.getString("title"));
                        task.setDescription(resultSet.getString("description"));
                        task.setDueDate(resultSet.getDate("due_date"));
                        task.setPriority(Task.Priority.valueOf(resultSet.getString("priority")));
                        task.setCompleted(resultSet.getBoolean("completed"));
                        searchResults.add(task);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

}
