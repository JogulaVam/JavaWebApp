package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FacultyDashboardServlet")
public class FacultyDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentIdParam = request.getParameter("student_id");
        if (studentIdParam != null) {
            int studentId = Integer.parseInt(studentIdParam);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_performance_analysis", "root", "butterfly");

                // Fetch student details
                String studentQuery = "SELECT * FROM Students WHERE student_id = ?";
                PreparedStatement studentStatement = connection.prepareStatement(studentQuery);
                studentStatement.setInt(1, studentId);
                ResultSet studentResultSet = studentStatement.executeQuery();
                request.setAttribute("studentDetails", studentResultSet);

                // Fetch additional data as needed

                connection.close();
                request.getRequestDispatcher("facultyDashboard.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else {
            response.sendRedirect("facultyDashboard.jsp?error=no_id");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_performance_analysis", "root", "yourpassword");
            String updateQuery = "UPDATE Students SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE student_id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, firstName);
            updateStatement.setString(2, lastName);
            updateStatement.setString(3, email);
            updateStatement.setString(4, phone);
            updateStatement.setInt(5, studentId);
            updateStatement.executeUpdate();
            connection.close();
            response.sendRedirect("facultyDashboard.jsp?status=updated");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("facultyDashboard.jsp?error=update_failed");
        }
    }
}

