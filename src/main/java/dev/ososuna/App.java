package dev.ososuna;

import java.sql.*;

public class App {
   public static void main(String[] args) {
      try {
         // Establishing connection to the source database
         String sourceDbUrl = "jdbc:mysql://localhost:3306/source_database";
         String sourceDbUsername = "root";
         String sourceDbPassword = "password";
         Connection sourceConnection = DriverManager.getConnection(sourceDbUrl, sourceDbUsername, sourceDbPassword);

         // Creating a statement to extract data from the source database
         String selectQuery = "SELECT * FROM source_table";
         Statement selectStatement = sourceConnection.createStatement();

         // Executing the select query to get the data from the source table
         ResultSet resultSet = selectStatement.executeQuery(selectQuery);

         // Establishing connection to the destination database
         String destDbUrl = "jdbc:mysql://localhost:3306/destination_database";
         String destDbUsername = "root";
         String destDbPassword = "password";
         Connection destConnection = DriverManager.getConnection(destDbUrl, destDbUsername, destDbPassword);

         // Creating a prepared statement to insert data into the destination table
         String insertQuery = "INSERT INTO destination_table (col1, col2, col3) VALUES (?, ?, ?)";
         PreparedStatement insertStatement = destConnection.prepareStatement(insertQuery);

         // Looping through the result set and inserting each row into the destination table
         while (resultSet.next()) {
            // Extracting data from the source table
            String col1 = resultSet.getString("col1");
            String col2 = resultSet.getString("col2");
            int col3 = resultSet.getInt("col3");

            // Setting the parameters for the insert statement
            insertStatement.setString(1, col1);
            insertStatement.setString(2, col2);
            insertStatement.setInt(3, col3);

            // Executing the insert statement
            insertStatement.executeUpdate();
         }

         // Closing the resources
         insertStatement.close();
         resultSet.close();
         selectStatement.close();
         sourceConnection.close();
         destConnection.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
}
