package codes.aliahmad.phonebook.data;

import codes.aliahmad.phonebook.model.PhoneBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlDataService implements DataService
{
  private static final String JDBC_URL = "jdbc:mysql://localhost:3306/linxa";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "root";

  private static MySqlDataService instance;
  private static Connection connection;

  private MySqlDataService()
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
      ResultSet resultSet = preparedStatement.executeQuery();

      List<PhoneBook> phoneBooks = new ArrayList<>();
      while (resultSet.next())
      {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String street = resultSet.getString("street");
        String city = resultSet.getString("city");
        String country = resultSet.getString("country");
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        Date lastUpdatedAt = resultSet.getTimestamp("lastUpdatedAt");

        phoneBooks.add(new PhoneBook(id, firstName, lastName, street, city, country, phone, email, lastUpdatedAt));
      }

      System.out.println(phoneBooks);
      return phoneBooks;

    }
    catch (SQLException e)
    {
      System.out.println("Error executing query: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public PhoneBook findById(Integer id)
  {
    try
    {
      String query = "SELECT * FROM phonebook Where id=?";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();

      PhoneBook phoneBook = null;

      if (resultSet.next())
      {
        int identifier = resultSet.getInt("id");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String street = resultSet.getString("street");
        String city = resultSet.getString("city");
        String country = resultSet.getString("country");
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        Date lastUpdatedAt = resultSet.getTimestamp("lastUpdatedAt");

        phoneBook = new PhoneBook(identifier, firstName, lastName, street, city, country, phone, email, lastUpdatedAt);
      }

      System.out.println(phoneBook);
      return phoneBook;
    }
    catch (SQLException e)
    {
      System.out.println("Error executing query: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public PhoneBook create(PhoneBook phoneBook)
  {
    try
    {
      String query = "INSERT INTO phonebook (firstName, lastName, street, city, country, phone, email, lastUpdatedAt) " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      // Set parameters for the INSERT query
      preparedStatement.setString(1, phoneBook.getFirstName());
      preparedStatement.setString(2, phoneBook.getLastName());
      preparedStatement.setString(3, phoneBook.getStreet());
      preparedStatement.setString(4, phoneBook.getCity());
      preparedStatement.setString(5, phoneBook.getCountry());
      preparedStatement.setString(6, phoneBook.getPhone());
      preparedStatement.setString(7, phoneBook.getEmail());
      preparedStatement.setTimestamp(8, new Timestamp(phoneBook.getLastUpdatedAt().getTime()));

      int affectedRows = preparedStatement.executeUpdate();

      if (affectedRows > 0)
      {
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next())
        {
          int generatedId = generatedKeys.getInt(1);
          phoneBook.setId(generatedId);
        }
        return phoneBook;
      }
      else
      {
        return null;
      }
    }
    catch (SQLException e)
    {
      System.out.println("Error executing query: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public PhoneBook delete(Integer id)
  {
    try
    {
      PhoneBook phoneBook = findById(id);

      String query = "DELETE FROM phonebook WHERE id=?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);

      int affectedRows = preparedStatement.executeUpdate();

      if (affectedRows > 0)
      {
        return phoneBook;
      }
      else
      {
        return null;
      }
    }
    catch (SQLException e)
    {
      System.out.println("Error executing query: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public PhoneBook update(PhoneBook phoneBook)
  {
    try
    {
      String query = "UPDATE phonebook SET firstName=?, lastName=?, street=?, city=?, country=?, phone=?, email=?, lastUpdatedAt=? WHERE id=?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);

      preparedStatement.setString(1, phoneBook.getFirstName());
      preparedStatement.setString(2, phoneBook.getLastName());
      preparedStatement.setString(3, phoneBook.getStreet());
      preparedStatement.setString(4, phoneBook.getCity());
      preparedStatement.setString(5, phoneBook.getCountry());
      preparedStatement.setString(6, phoneBook.getPhone());
      preparedStatement.setString(7, phoneBook.getEmail());
      preparedStatement.setTimestamp(8, new Timestamp(phoneBook.getLastUpdatedAt().getTime()));
      preparedStatement.setInt(9, phoneBook.getId());

      int affectedRows = preparedStatement.executeUpdate();

      if (affectedRows > 0)
      {
        return phoneBook;
      }
      else
      {
        return null;
      }
    }
    catch (SQLException e)
    {
      System.out.println("Error executing query: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean existsByPhoneNumber(String phoneNumber)
  {
    try
    {
      String query = "SELECT COUNT(*) FROM phonebook WHERE phone=?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, phoneNumber);

      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next())
      {
        int count = resultSet.getInt(1);
        return count > 0;
      }

      return false;
    }
    catch (SQLException e)
    {
      System.out.println("Error executing query: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id)
  {
    try
    {
      String query = "SELECT COUNT(*) FROM phonebook WHERE phone=? AND id<>?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, phoneNumber);
      preparedStatement.setInt(2, id);

      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next())
      {
        int count = resultSet.getInt(1);
        return count > 0;
      }

      return false;
    }
    catch (SQLException e)
    {
      System.out.println("Error executing query: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }


  public static MySqlDataService getInstance()
  {
    if (instance == null)
    {
      instance = new MySqlDataService();
    }
    return instance;
  }
}
