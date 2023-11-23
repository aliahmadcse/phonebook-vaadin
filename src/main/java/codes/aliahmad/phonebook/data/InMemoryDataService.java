package codes.aliahmad.phonebook.data;

import codes.aliahmad.phonebook.model.PhoneBook;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InMemoryDataService implements DataService
{
  private final Map<Integer, PhoneBook> phoneBookMap = new HashMap<>();

  private static InMemoryDataService instance;

  private InMemoryDataService()
  {
    initialize();
  }

  @Override
  public List<PhoneBook> findAll()
  {
    return phoneBookMap.values().stream().toList();
  }

  @Override
  public PhoneBook findById(Integer id)
  {
    return phoneBookMap.getOrDefault(id, null);
  }

  @Override
  public PhoneBook create(PhoneBook phoneBook)
  {
    return phoneBookMap.putIfAbsent(phoneBook.getId(), phoneBook);
  }

  @Override
  public PhoneBook delete(Integer id)
  {
    return phoneBookMap.remove(id);
  }

  @Override
  public PhoneBook update(PhoneBook phoneBook)
  {
    return phoneBookMap.replace(phoneBook.getId(), phoneBook);
  }

  @Override
  public boolean existsByPhoneNumber(String phoneNumber)
  {
    List<PhoneBook> phoneBooks = findAll();

    return phoneBooks.stream().anyMatch(phoneBook -> phoneBook.getPhone().equals(phoneNumber));
  }

//  @Override
//  public PhoneBook findByPhoneNumber(String phoneNumber)
//  {
//    List<PhoneBook> phoneBooks = findAll();
//
//    return phoneBooks.stream().filter(phoneBook -> phoneBook.getPhone().equals(phoneNumber))
//            .findFirst().orElse(null);
//  }

  @Override
  public boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id)
  {
    List<PhoneBook> phoneBooks = findAll();

    return phoneBooks.stream()
            .anyMatch(phoneBook -> phoneBook.getPhone().equals(phoneNumber) && !Objects.equals(phoneBook.getId(), id));
  }

  public static DataService getInstance()
  {
    if (instance == null)
    {
      instance = new InMemoryDataService();
    }

    return instance;
  }

  public void initialize()
  {
    phoneBookMap.put(1, new PhoneBook(1, "John", "Doe", "123 Main St", "Cityville", "Countryland", "09123456789", "john.doe@example.com", new Date()));
    phoneBookMap.put(2, new PhoneBook(2, "Jane", "Smith", "456 Oak St", "Townsville", "Countryland", "09234567890", "jane.smith@example.com", new Date()));
    phoneBookMap.put(3, new PhoneBook(3, "Bob", "Johnson", "789 Pine St", "Villagetown", "Countryland", "09345678901", "bob.johnson@example.com", new Date()));
    phoneBookMap.put(4, new PhoneBook(4, "Alice", "Brown", "321 Cedar St", "Cityville", "Countryland", "09456789012", "alice.brown@example.com", new Date()));
    phoneBookMap.put(5, new PhoneBook(5, "Tom", "Davis", "654 Birch St", "Townsville", "Countryland", "09556789013", "tom.davis@example.com", new Date()));
    phoneBookMap.put(6, new PhoneBook(6, "Sara", "Miller", "987 Walnut St", "Villagetown", "Countryland", "09656789014", "sara.miller@example.com", new Date()));
  }
}
