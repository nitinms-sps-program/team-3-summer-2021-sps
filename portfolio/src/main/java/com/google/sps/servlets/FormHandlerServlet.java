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

    
    /*try {
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
        }*/

    insertIntoDatabse(company, salary, rating, role, review, wlb);

    selectBasedOnCompany("\"Amazon\"");
    //response.sendRedirect("https://google.com");


    // Print the value so you can see it in the server logs.
    System.out.println("Company: " + company);
    System.out.println("Role: " + role);
    System.out.println("Work-Life Balance: " + workLife);
    System.out.println("Star Rating: " + starRating);
    System.out.println("Salary: " + salary);
    System.out.println("Review: " + review);

    // Write the value to the response so the user can see it.
    response.getWriter().println("Company: " + company);
    response.getWriter().println("Role: " + role);
    response.getWriter().println("Work-Life Balance: " + workLife);
    response.getWriter().println("Star Rating: " + starRating);
    response.getWriter().println("Salary: " + salary);
    response.getWriter().println("Review: " + review);

  }
  public void insertIntoDatabse(String company, int salary, int rating, String role, String review, int wlb){
      try {
       //creates instance of connection pool
       DataSource connectionPool = DatabaseConnection.initializeDatabase();
       try (Connection conn = connectionPool.getConnection()) {
           //prepares satement to write to the table
           String stmt = String.format("INSERT INTO reviews (comapny, salary, rating, role, review, wlb) VALUES (?,?,?,?,?,?);");
           try (PreparedStatement insertStmt = conn.prepareStatement(stmt)) {
               //inserting values from the servlet into the table/database
               insertStmt.setQueryTimeout(10);
               insertStmt.setString(1, company);
               insertStmt.setInt(2, salary);
               insertStmt.setInt(3, rating);
               insertStmt.setString(4, role);
               insertStmt.setString(5, review);
               insertStmt.setInt(6, wlb);
               insertStmt.execute();
               }
           }catch(SQLException e){
               System.out.println("failed");
           }
 
       } catch (ClassNotFoundException | SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
 
 }
  public void selectBasedOnRole(String role){
   try {
       //creates instance of connection pool
       DataSource connectionPool = DatabaseConnection.initializeDatabase();
       List<String> bookList = new ArrayList<>();
       try (Connection conn = connectionPool.getConnection()) {
           String stmt = String.format("SELECT * FROM reviews WHERE role = " + role + ";");
           try (PreparedStatement selectStmt = conn.prepareStatement(stmt)) {
               selectStmt.setQueryTimeout(10); // 10s
               ResultSet rs = selectStmt.executeQuery();
               while (rs.next()) {
                   bookList.add(rs.getString(5));
               }
 
           }
           }catch(SQLException e){
                   System.out.println("failed");
           }
           System.out.println(bookList);
 
       } catch (ClassNotFoundException | SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
      
 
 }
 
 public void selectBasedOnCompany(String company){
     try {
       //creates instance of connection pool
       DataSource connectionPool = DatabaseConnection.initializeDatabase();
       List<String> bookList = new ArrayList<>();
       try (Connection conn = connectionPool.getConnection()) {
           String stmt = String.format("SELECT * FROM reviews WHERE comapny = " + company + ";"); 
           try (PreparedStatement selectStmt = conn.prepareStatement(stmt)) {
               selectStmt.setQueryTimeout(10); // 10s
               ResultSet rs = selectStmt.executeQuery();
               while (rs.next()) {
                   bookList.add(rs.getString(5));
                   bookList.add(rs.getString("review"));
               }
           }
           }catch(SQLException e){
                   System.out.println("failed");
           }
           System.out.println(bookList);
 
       } catch (ClassNotFoundException | SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
 
 }
 


}