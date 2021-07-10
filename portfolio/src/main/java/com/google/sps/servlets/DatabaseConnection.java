package com.google.sps.servlets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
  
// This class can be used to initialize the database connection
public class DatabaseConnection {
    
    public static DataSource initializeDatabase()
        throws SQLException, ClassNotFoundException
    {
        // Initialize all the information regarding
        // Database Connection
        String dbDriver = "com.mysql.jdbc.GoogleDriver";
        
        // Database name to access
       
        String dbName = "reviews";
        String dbUsername = "root";
        String dbPassword = "";


        // Note: For Java users, the Cloud SQL JDBC Socket Factory can provide authenticated connections
        // which is preferred to using the Cloud SQL Proxy with Unix sockets.
        // See https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory for details.

        // The configuration object specifies behaviors for the connection pool.
        HikariConfig config = new HikariConfig();

        // The following URL is equivalent to setting the config options below:
        // jdbc:mysql:///<DB_NAME>?cloudSqlInstance=<CLOUD_SQL_CONNECTION_NAME>&
        // socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=<DB_USER>&password=<DB_PASS>
        // See the link below for more info on building a JDBC URL for the Cloud SQL JDBC Socket Factory
        // https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory#creating-the-jdbc-url

        // Configure which instance and what database user to connect with.
        config.setJdbcUrl(String.format("jdbc:mysql:///%s", dbName));
        config.setUsername(dbUsername); // e.g. "root", "mysql"
        config.setPassword(dbPassword); // e.g. "my-password"

        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
        config.addDataSourceProperty("cloudSqlInstance", "summer21-sps-3:us-central1:internship-review-database");

        // The ipTypes argument can be used to specify a comma delimited list of preferred IP types 
        // for connecting to a Cloud SQL instance. The argument ipTypes=PRIVATE will force the 
        // SocketFactory to connect with an instance's associated private IP. 
        config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE");

        // ... Specify additional connection properties here.
        // ...

        // Initialize the connection pool using the configuration object.
        //Class.forName(dbDriver);
        //config.setDriverClassName(dbDriver);

        DataSource pool = new HikariDataSource(config);

        return pool;
    }
}