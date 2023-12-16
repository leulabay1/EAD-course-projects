<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.tms.model.Task" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task List</title>
    <link rel="stylesheet" type="text/css" href="taskList.css">
    <!-- Include your CSS file for task cards here -->
</head>
<body>
<div id="task-list-container">
    <h2>Task List</h2>

    <!-- Filter/Search Form -->
    <form action="search" method="get" class="filter-form">
        <label for="searchTitle">Search by Title:</label>
        <input type="text" id="searchTitle" name="searchTitle">
        <label for="searchDueDate">Search by Due Date:</label>
        <input type="text" id="searchDueDate" name="searchDueDate" placeholder="YYYY-MM-DD">
        <button type="submit">Filter</button>
    </form>

    <!-- Task Cards -->
    <%
        List<Task> taskList = (List<Task>) request.getAttribute("taskList");
        if (taskList != null) {
            for (Task task : taskList) {
    %>
    <div class="task-card">
        <h3 class="<%= task.getCompleted() ? "completed-task" : "" %>"><%= task.getTitle() %></h3>
        <p><%= task.getDescription() %></p>
        <p>Due Date: <%= task.getDueDate() %></p>
        <p>Priority: <%= task.getPriority() %></p>

        <!-- Task Actions -->
        <div class="task-actions">
            <form action="complete-task" method="get" style="display: inline;">
                <input type="hidden" name="taskId" value="<%= task.getId() %>">
                <button class="button" type="submit">Complete</button>
            </form>

            <form action="editTask.jsp" method="get" style="display: inline;">
                <input type="hidden" name="taskId" value="<%= task.getId() %>">
                <button class="button" type="submit">Edit</button>
            </form>

            <form action="delete-task" method="get" style="display: inline;">
                <input type="hidden" name="taskId" value="<%= task.getId() %>">
                <button class="button" type="submit">Delete</button>
            </form>
        </div>
    </div>
    <%
            }
        }
    %>
</div>
</body>
</html>
