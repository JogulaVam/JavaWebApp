package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/StudentDashboardServlet")
public class StudentDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ResultSet student = (ResultSet) session.getAttribute("student");
        int studentId = 0;
		try {
			studentId = student.getInt("student_id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_performance_analysis", "root", "yourpassword");

            // Fetch marks
            String marksQuery = "SELECT c.course_name, m.marks FROM Marks m JOIN Enrollments e ON m.enrollment_id = e.enrollment_id JOIN Courses c ON e.course_id = c.course_id WHERE e.student_id = ?";
            PreparedStatement marksStatement = connection.prepareStatement(marksQuery);
            marksStatement.setInt(1, studentId);
            ResultSet marksResultSet = marksStatement.executeQuery();
            request.setAttribute("marks", marksResultSet);

            // Fetch grades
            String gradesQuery = "SELECT c.course_name, g.grade FROM Grades g JOIN Enrollments e ON g.enrollment_id = e.enrollment_id JOIN Courses c ON e.course_id = c.course_id WHERE e.student_id = ?";
            PreparedStatement gradesStatement = connection.prepareStatement(gradesQuery);
            gradesStatement.setInt(1, studentId);
            ResultSet gradesResultSet = gradesStatement.executeQuery();
            request.setAttribute("grades", gradesResultSet);

            // Fetch attendance
            String attendanceQuery = "SELECT date, status FROM Attendance WHERE enrollment_id IN (SELECT enrollment_id FROM Enrollments WHERE student_id = ?)";
            PreparedStatement attendanceStatement = connection.prepareStatement(attendanceQuery);
            attendanceStatement.setInt(1, studentId);
            ResultSet attendanceResultSet = attendanceStatement.executeQuery();
            request.setAttribute("attendances", attendanceResultSet);

            // Fetch events
            String eventsQuery = "SELECT e.event_name, e.event_date FROM Events e JOIN Student_Events se ON e.event_id = se.event_id WHERE se.student_id = ?";
            PreparedStatement eventsStatement = connection.prepareStatement(eventsQuery);
            eventsStatement.setInt(1, studentId);
            ResultSet eventsResultSet = eventsStatement.executeQuery();
            request.setAttribute("events", eventsResultSet);

            // Fetch achievements
            String achievementsQuery = "SELECT achievement, date FROM Achievements WHERE student_id = ?";
            PreparedStatement achievementsStatement = connection.prepareStatement(achievementsQuery);
            achievementsStatement.setInt(1, studentId);
            ResultSet achievementsResultSet = achievementsStatement.executeQuery();
            request.setAttribute("achievements", achievementsResultSet);

            connection.close();
            request.getRequestDispatcher("studentDashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

