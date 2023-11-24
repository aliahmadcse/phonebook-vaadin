package codes.aliahmad.phonebook.util;

import codes.aliahmad.phonebook.data.DataService;
import codes.aliahmad.phonebook.data.InMemoryDataService;
import codes.aliahmad.phonebook.model.PhoneBook;
import com.vaadin.flow.component.notification.Notification;

public class ValidationUtil
{
  public static void validateDataEditing(PhoneBook editingPhoneBook, PhoneBook existingPhoneBook)
  {

    if (!existingPhoneBook.getLastUpdatedAt().equals(editingPhoneBook.getLastUpdatedAt()))
    {
      Notification.show("This data was already updated by another user", 5000, Notification.Position.TOP_CENTER);
    }

  }

  public static boolean validateDataDeletion(PhoneBook existingPhoneBook)
  {
    if (existingPhoneBook == null)
    {
      Notification.show("This record was deleted by some other user.",
              5000, Notification.Position.TOP_CENTER);
      return false;
    }
    return true;
  }

  public static boolean isPhoneNumberValid(String phoneNumber, PhoneBook editingPhoneBook, DataService dataService)
  {
    if (editingPhoneBook == null)
    {
      return !dataService.existsByPhoneNumber(phoneNumber);
    }
    return !dataService.existsByPhoneNumberAndIdNot(phoneNumber, editingPhoneBook.getId());
  }
}
