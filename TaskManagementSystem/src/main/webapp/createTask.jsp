<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create com.tms.model.Task</title>
    <link rel="stylesheet" type="text/css" href="createTask.css">
</head>
<body>
<div id="create-task-container">
    <h2>Create com.tms.model.Task</h2>

    <%-- Display error message if task creation fails --%>
    <c:if test="${param.error == 'true'}">
        <p style="color: red;">com.tms.model.Task creation failed. Please try again.</p>
    </c:if>

    <form action="create-task" method="post">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required><br>

        <label for="description">Description:</label>
        <textarea id="description" name="description" rows="4" cols="50"></textarea><br>

        <label for="dueDate">Due Date:</label>
        <input type="text" id="dueDate" name="dueDate" placeholder="YYYY-MM-DD" required><br>

        <label for="priority">Priority:</label>
        <select id="priority" name="priority" required>
            <option value="High">High</option>
            <option value="Medium">Medium</option>
            <option value="Low">Low</option>
        </select><br>

        <button type="submit">Create com.tms.model.Task</button>
    </form>
</div>
</body>
</html>
