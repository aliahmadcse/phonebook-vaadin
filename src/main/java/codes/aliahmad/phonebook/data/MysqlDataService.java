package codes.aliahmad.phonebook.data;

import codes.aliahmad.phonebook.model.PhoneBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlDataService implements DataService
{
  private static final String JDBC_URL = "jdbc:mysql://localhost:3306/linxa";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "root";

  private static MysqlDataService instance;
  private static Connection connection;

  private MysqlDataService()
  {
    try
    {
      connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
    catch (SQLException e)
    {
      System.out.println("Error connecting to database: " + e.getMessage());
    }
  }


  @Override
  public List<PhoneBook> findAll()
  {
    try
    {
      String query = "SELECT * FROM phonebook";
      PreparedStatement preparedStatement = connection.prepareStatement(query);

      // Execute the query
      ResultSet resultSet = preparedStatement.executeQuery();

      // Process the result set
      while (resultSet.next())
      {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("firstName");
        String phoneNumber = resultSet.getString("lastName");

        System.out.println("ID: " + id + ", Name: " + name + ", Phone Number: " + phoneNumber);
      }

      return new ArrayList<>();

    } catch (SQLException e) {
      System.out.println("Error executing query: " + e.getMessage());
      return null;
    }
  }

  @Override
  public PhoneBook findById(Integer id)
  {
    return null;
  }

  @Override
  public PhoneBook create(PhoneBook phoneBook)
  {
    return null;
  }

  @Override
  public PhoneBook delete(Integer id)
  {
    return null;
  }

  @Override
  public PhoneBook update(PhoneBook phoneBook)
  {
    return null;
  }

  @Override
  public boolean existsByPhoneNumber(String phoneNumber)
  {
    return false;
  }

  @Override
  public PhoneBook findByPhoneNumber(String phoneNumber)
  {
    return null;
  }

  @Override
  public boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id)
  {
    return false;
  }

  public static MysqlDataService getInstance()
  {
    if (instance == null)
    {
      instance = new MysqlDataService();
    }
    return instance;
  }
}
