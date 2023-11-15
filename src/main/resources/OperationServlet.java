import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/operation")
public class OperationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");

        if (operation == null || operation.isEmpty()) {
            response.getWriter().println("Please provide a valid operation parameter (addition, subtraction, multiplication, division)");
            return;
        }

        // Forward the request to the appropriate operation servlet based on the operation parameter
        switch (operation) {
            case "addition":
                request.getRequestDispatcher("/addition").forward(request, response);
                break;
            case "subtraction":
                request.getRequestDispatcher("/subtraction").forward(request, response);
                break;
            case "multiplication":
                request.getRequestDispatcher("/multiplication").forward(request, response);
                break;
            case "division":
                request.getRequestDispatcher("/division").forward(request, response);
                break;
            default:
                response.getWriter().println("Invalid operation");
        }
    }
}
