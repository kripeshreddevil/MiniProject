package enqueue.MiniProject;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonRepository {
    private final String csvFilePath;
    private final String jdbcUrl;
    private final String jdbcUsername;
    private final String jdbcPassword;

    public PersonRepository(String csvFilePath, String jdbcUrl, String jdbcUsername, String jdbcPassword) {
        this.csvFilePath = csvFilePath;
        this.jdbcUrl = jdbcUrl;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    public void saveAll() throws IOException, SQLException, CsvException {
        CSVReader csvReader = new CSVReader(new FileReader(csvFilePath));
        List<String[]> csvData = csvReader.readAll();

        if (!csvData.isEmpty()) {
            csvData.remove(0);
        }


        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement insertStatement = connection.prepareStatement(
                     "INSERT INTO people (name, country, mobile_no) SELECT ?, ?, ? FROM DUAL WHERE NOT EXISTS " +
                             "(SELECT 1 FROM people WHERE name = ? AND country = ? AND mobile_no = ?)")) {

            Set<Person> uniquePeople = new HashSet<>();

            for (String[] row : csvData) {
                Person person = new Person(row[0], row[1], row[2]);
                // Normalize the data by removing leading/trailing whitespace and converting to lowercase
                person.setName(person.getName().trim().toLowerCase());
                person.setCountry(person.getCountry().trim().toLowerCase());
                person.setMobileNo(person.getMobileNo().trim());

                if (!uniquePeople.contains(person)) {
                    uniquePeople.add(person);

                    insertStatement.setString(1, person.getName());
                    insertStatement.setString(2, person.getCountry());
                    insertStatement.setString(3, person.getMobileNo());
                    insertStatement.setString(4, person.getName());
                    insertStatement.setString(5, person.getCountry());
                    insertStatement.setString(6, person.getMobileNo());

                    insertStatement.executeUpdate();
                }
            }
        }
    }
}
