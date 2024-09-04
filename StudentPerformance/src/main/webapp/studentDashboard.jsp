<%@ page import="java.util.List, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; }
        th { background-color: #f4f4f4; }
        h1 { color: #333; }
        h2 { border-bottom: 2px solid #333; padding-bottom: 5px; }
    </style>
</head>
<body>
    <h1>Welcome, <%= request.getAttribute("studentName") %></h1>
    
    <h2>Performance Overview</h2>
    
    <h3>Grades</h3>
    <table>
        <thead>
            <tr>
                <th>Course</th>
                <th>Grade</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Map<String, Object>> grades = (List<Map<String, Object>>) request.getAttribute("grades");
                if (grades != null) {
                    for (Map<String, Object> grade : grades) {
            %>
            <tr>
                <td><%= grade.get("course_name") %></td>
                <td><%= grade.get("grade") %></td>
            </tr>
            <% 
                    }
                } else {
            %>
            <tr>
                <td colspan="2">No grades available</td>
            </tr>
            <% 
                }
            %>
        </tbody>
    </table>
    
    <h3>Attendance</h3>
    <table>
        <thead>
            <tr>
                <th>Course</th>
                <th>Date</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Map<String, Object>> attendance = (List<Map<String, Object>>) request.getAttribute("attendance");
                if (attendance != null) {
                    for (Map<String, Object> record : attendance) {
            %>
            <tr>
                <td><%= record.get("course_name") %></td>
                <td><%= record.get("date") %></td>
                <td><%= record.get("status") %></td>
            </tr>
            <% 
                    }
                } else {
            %>
            <tr>
                <td colspan="3">No attendance records available</td>
            </tr>
            <% 
                }
            %>
        </tbody>
    </table>
</body>
</html>
