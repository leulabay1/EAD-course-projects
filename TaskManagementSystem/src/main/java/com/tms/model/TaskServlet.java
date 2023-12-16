package com.tms.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.swing.text.StyledEditorKit;

@WebServlet("/task-servlet")
public class TaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;



    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Task> taskList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();) {
            String sql = "SELECT * FROM task";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    Date date = resultSet.getDate("due_date");
                    String priorityString = resultSet.getString("priority");
                    Task.Priority priority = Task.Priority.valueOf(priorityString);
                    Boolean completed = resultSet.getBoolean("completed");
                    Task task = new Task(id, title, description, date, priority,completed );
                    taskList.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("taskList", taskList);
        request.getRequestDispatcher("taskList.jsp").forward(request, response);
    }
}
