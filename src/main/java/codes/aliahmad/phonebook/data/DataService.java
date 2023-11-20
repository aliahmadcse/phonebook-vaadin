package codes.aliahmad.phonebook.data;

import codes.aliahmad.phonebook.model.PhoneBook;

import java.util.List;

public interface DataService
{
  List<PhoneBook> getAll();

  PhoneBook findByPhoneNumber(String phone);

  PhoneBook save(PhoneBook phoneBook);

  PhoneBook delete(String phone);

  PhoneBook edit(PhoneBook phoneBook);
}
