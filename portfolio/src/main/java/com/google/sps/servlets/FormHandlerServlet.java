package /*main.java.*/com.google.sps.servlets;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Extract the pool from the Servlet Context, reusing the one that was created
        // in the ContextListener when the application was started
        

    // Get the value entered in the form.

    String company = request.getParameter("inputCompany");
    String role = request.getParameter("role");
    String workLife = request.getParameter("workLife");
    String starRating = request.getParameter("starRating");
    String salary = request.getParameter("salary");
    String review = request.getParameter("inputReview");

    
    try {
        //creates instance of connection pool
        DataSource connectionPool = DatabaseConnection.initializeDatabase();
        try (Connection conn = connectionPool.getConnection()) {
            //prepares satement to write to the table
            String stmt = String.format("INSERT INTO reviews (company, role, workLife, starRating, salary, review) values (?,?,?,?,?,?);");
            try (PreparedStatement insertStmt = conn.prepareStatement(stmt)) {
                //inserting values from the servlet into the table/database
                insertStmt.setQueryTimeout(10);
                insertStmt.setString(1, company);
                insertStmt.setString(2, role);
                insertStmt.setString(3, workLife);
                insertStmt.setString(4, starRating);
                insertStmt.setString(5, salary);
                insertStmt.setString(6, review);
                insertStmt.execute();
                }
            }catch(SQLException e){
                System.out.println("failed");
            }

        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    // Print the value so you can see it in the server logs.
    System.out.println("Company: " + company);
    System.out.println("Role: " + role);
    System.out.println("Work-Life Balance: " + workLife);
    System.out.println("Star Rating: " + starRating);
    System.out.println("Salary: " + salary);
    System.out.println("Review: " + review);
    
    response.sendRedirect("index.html");

    // Write the value to the response so the user can see it.
    // response.getWriter().println("<p>Company: " + company +"</p>");
    // response.getWriter().println("<p>Role: " + role +"</p>");
    // response.getWriter().println("<p>Work-Life Balance: " + workLife +"</p>");
    // response.getWriter().println("<p>Star Rating: " + starRating +"</p>");
    // response.getWriter().println("<p>Salary: " + salary +"</p>");
    // response.getWriter().println("<p>Review: " + review +"</p>");

  }
}