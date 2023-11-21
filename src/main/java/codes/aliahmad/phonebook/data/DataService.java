package codes.aliahmad.phonebook.data;

import codes.aliahmad.phonebook.model.PhoneBook;

import java.util.List;

public interface DataService
{
  List<PhoneBook> findAll();

  PhoneBook findById(Integer id);

  PhoneBook create(PhoneBook phoneBook);

  PhoneBook delete(Integer id);

  PhoneBook update(PhoneBook phoneBook);

  boolean existsByPhoneNumber(String phoneNumber);

  PhoneBook findByPhoneNumber(String phoneNumber);
}
