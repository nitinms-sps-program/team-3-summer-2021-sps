package /*main.java.*/main.java.com.google.sps.servlets;

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

    String textValue1 = request.getParameter("email-input");
    String textValue2 = request.getParameter("company-input");
    String textValue3 = request.getParameter("review-input");
    
    try {
        //creates instance of connection pool
        DataSource connectionPool = DatabaseConnection.initializeDatabase();
        try (Connection conn = connectionPool.getConnection()) {
            //prepares satement to write to the table
            String stmt = String.format("INSERT INTO reviews (email, company, review) values (?,?,?);");
            try (PreparedStatement insertStmt = conn.prepareStatement(stmt)) {
                //inserting values from the servlet into the table/database
                insertStmt.setQueryTimeout(10);
                insertStmt.setString(1, textValue1);
                insertStmt.setString(2, textValue2);
                insertStmt.setString(3, textValue3);
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
    System.out.println("You submitted: " + textValue1);
    System.out.println("You submitted: " + textValue2);
    System.out.println("You submitted: " + textValue3);

    // Write the value to the response so the user can see it.
    response.getWriter().println("You submitted: " + textValue1);
    response.getWriter().println("You submitted: " + textValue2);
    response.getWriter().println("You submitted: " + textValue3);

  }
}