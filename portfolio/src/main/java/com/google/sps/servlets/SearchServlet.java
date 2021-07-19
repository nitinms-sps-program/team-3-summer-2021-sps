package /*main.java.*/com.google.sps.servlets;


import com.google.template.soy.SoyFileSet;
import com.google.template.soy.tofu.SoyTofu;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import javax.sql.DataSource;

import com.google.template.soy.tofu.SoyTofu;

//import sun.java2d.pipe.Simple;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//package example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Search servlet implementation
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // ensures that loaded class corresponds to a serialized object
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try{
    String company = request.getParameter("companyName");
    
    List<Review> reviews = selectBasedOnCompany(company);

    Review newReview = reviews.get(0);
    System.out.println(newReview.review);


    SoyFileSet sfs = SoyFileSet
        .builder()
        .add(Something.class.getResource("index.soy"))
        .build();

    // helloWorld
    SoyTofu tofu = sfs.compileToTofu();
    //System.out.println(
        //tofu.newRenderer("com.google.sps.index.index").render());

    // For convenience, create another SoyTofu object that has a
    // namespace specified, so you can pass partial template names to
    // the newRenderer() method.
    SoyTofu simpleTofu = tofu.forNamespace("com.google.sps.index");



    // helloName
    Map<String, Object> data = new HashMap<>();
    data.put("companyName", newReview.company);
    data.put("workBalance", newReview.wlb);
    data.put("salary", newReview.salary);
    data.put("review", newReview.review);
    data.put("role", newReview.role);
    data.put("rating", newReview.rating);
    
    System.out.println("-----------------");

    //System.out.println(
       // simpleTofu.newRenderer(".index").setData(data).render());

  ((ServletResponse) response).setContentType("text/html");
  response.setCharacterEncoding("utf-8");
  try {
    ((ServletResponse) response).getWriter().print(simpleTofu.newRenderer(".index").setData(data).render());
  } catch (IOException e) {
    //LOGGER.error("General IOException: {}", e.getMessage());
  }  

}catch (Exception e){
    response.sendRedirect("index.html");

}

    //Map

    /*
    {@param companyName: string} 
{@param workBalance: string}
{@param salary: string}
{@param review: string}
{@param role: string} 
{@param rating: string} 
    */

}

   public List<Review> selectBasedOnCompany(String company){
     try {
       //creates instance of connection pool
       DataSource connectionPool = DatabaseConnection.initializeDatabase();
       List<Review> bookList = new ArrayList<>();
       try (Connection conn = connectionPool.getConnection()) {
           String stmt = String.format("SELECT * FROM reviews WHERE company = \"" + company + "\";");
           try (PreparedStatement selectStmt = conn.prepareStatement(stmt)) {
               selectStmt.setQueryTimeout(10); // 10s
               ResultSet rs = selectStmt.executeQuery();
               while (rs.next()) {
                   //bookList.add(rs.getString(5));
                   Review pendingReview = new Review (rs.getString(1), rs.getString(2), rs.getString(3),
                   rs.getString(4), rs.getString(5), rs.getString(6));
                   bookList.add(pendingReview);
                   //bookList.add(rs.getString("review"));
               }
           }
           }catch(SQLException e){
                   System.out.println("failed");
           }
           System.out.println(bookList);
           //System.out.println(bookList.get(0).salary);
                   
           
           return bookList;
 
       } catch (ClassNotFoundException | SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
        return null;
 
 }


}

class Review{
   String company, salary, rating, role, review, wlb;
 
   public Review(String company, String salary, String rating,
   String role, String review, String wlb){
       this.company = company;
       this.salary = salary;
       this.rating = rating;
       this.role = role;
       this.review = review;
       this.wlb = wlb;
   }
 
}
