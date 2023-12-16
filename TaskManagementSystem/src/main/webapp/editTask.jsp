<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Task</title>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
    <style>
        /* Add your additional CSS styles for the edit form here */
        .edit-form {
            max-width: 400px;
            margin: 20px auto;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
        }

        .form-group input {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
        }

        .form-group button {
            background-color: #4caf50;
            color: #fff;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .form-group button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="edit-form">
    <h2>Edit Task</h2>

    <form action="edit-task" method="post">
        <!-- Hidden input for task ID -->
        <input type="hidden" name="taskId" value="<%= request.getParameter("taskId") %>">

        <div class="form-group">
            <label for="title">New Title:</label>
            <input type="text" id="title" name="title" >
        </div>

        <div class="form-group">
            <label for="description">New Description:</label>
            <textarea id="description" name="description"></textarea>
        </div>

        <div class="form-group">
            <button type="submit">Save Changes</button>
        </div>
    </form>
</div>
</body>
</html>
