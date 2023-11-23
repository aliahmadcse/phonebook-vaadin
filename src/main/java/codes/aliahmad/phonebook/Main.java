package codes.aliahmad.phonebook;

import codes.aliahmad.phonebook.data.DataService;
import codes.aliahmad.phonebook.data.MySqlDataService;
import codes.aliahmad.phonebook.model.PhoneBook;

public class Main
{
  public static void main(String[] args)
  {
    DataService dataService = MySqlDataService.getInstance();

    PhoneBook phoneBook = new PhoneBook(null,"Ali", "Ahmad", "abc", "abc", "abc", "1233", "ali@gmail.com",null);

    dataService.create(phoneBook);
  }

}
