package enqueue.MiniProject;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        String csvFilePath = "C:\\\\Users\\\\kripe\\\\IdeaProjects\\\\JavaJDBCConnection\\\\JavaJDBCConnection\\\\src\\\\enqueue\\\\MiniProject\\\\PhoneBook.csv";
        Class.forName("com.mysql.jdbc.Driver");
        String jdbcUrl = "jdbc:mysql://localhost:3306/mydatabase";
        String jdbcUsername = "root";
        String jdbcPassword = "";

        try {
            // Create the database
            try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
                 Statement statement = connection.createStatement()) {
                String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS mydatabase";
                statement.executeUpdate(createDatabaseSql);
            }

            // Use the database
            String useDatabaseSql = "USE mydatabase";
            try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
                 PreparedStatement statement = connection.prepareStatement(useDatabaseSql)) {
                statement.executeUpdate();
            }

            // Create table
            try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
                 Statement statement = connection.createStatement()) {
                String createTableSql = "CREATE TABLE IF NOT EXISTS people (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), country VARCHAR(255), mobile_no VARCHAR(255))";
                statement.executeUpdate(createTableSql);
            }

            // Read and save data from CSV
            PersonRepository personRepository = new PersonRepository(csvFilePath, jdbcUrl, jdbcUsername, jdbcPassword);
            personRepository.saveAll();

            System.out.println("Data saved successfully.");

        } catch (IOException | SQLException | CsvException e) {
            e.printStackTrace();
        }
    }
}


//CSVReader csvReader = new CSVReader(new FileReader("C:\\Users\\kripe\\IdeaProjects\\JavaJDBCConnection\\JavaJDBCConnection\\src\\enqueue\\MiniProject\\PhoneBook.csv"));

