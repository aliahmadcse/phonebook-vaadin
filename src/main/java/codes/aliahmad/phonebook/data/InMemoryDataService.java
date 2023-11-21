package codes.aliahmad.phonebook.data;

import codes.aliahmad.phonebook.model.PhoneBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDataService implements DataService
{
  private final Map<String, PhoneBook> phoneBookMap = new HashMap<>();

  public InMemoryDataService()
  {
    initialize();
  }

  @Override
  public List<PhoneBook> getAll()
  {
    return phoneBookMap.values().stream().toList();
  }

  @Override
  public PhoneBook findByPhoneNumber(String phone)
  {
    return phoneBookMap.getOrDefault(phone, null);
  }

  @Override
  public PhoneBook save(PhoneBook phoneBook)
  {
    return phoneBookMap.putIfAbsent(phoneBook.getPhone(), phoneBook);
  }

  @Override
  public PhoneBook delete(String phone)
  {
    return phoneBookMap.remove(phone);
  }

  @Override
  public PhoneBook edit(PhoneBook phoneBook)
  {
    return phoneBookMap.replace(phoneBook.getPhone(), phoneBook);
  }

  public void initialize()
  {
    phoneBookMap.put("09123456789", new PhoneBook(1, "John", "Doe", "123 Main St", "Cityville", "Countryland", "09123456789", "john.doe@example.com"));
    phoneBookMap.put("09234567890", new PhoneBook(2, "Jane", "Smith", "456 Oak St", "Townsville", "Countryland", "09234567890", "jane.smith@example.com"));
    phoneBookMap.put("09345678901", new PhoneBook(3, "Bob", "Johnson", "789 Pine St", "Villagetown", "Countryland", "09345678901", "bob.johnson@example.com"));
    phoneBookMap.put("09456789012", new PhoneBook(4, "Alice", "Brown", "321 Cedar St", "Cityville", "Countryland", "09456789012", "alice.brown@example.com"));
    phoneBookMap.put("09556789013", new PhoneBook(5, "Tom", "Davis", "654 Birch St", "Townsville", "Countryland", "09556789013", "tom.davis@example.com"));
    phoneBookMap.put("09656789014", new PhoneBook(6, "Sara", "Miller", "987 Walnut St", "Villagetown", "Countryland", "09656789014", "sara.miller@example.com"));
  }
}
