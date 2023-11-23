package codes.aliahmad.phonebook.util;

import codes.aliahmad.phonebook.data.DataService;
import codes.aliahmad.phonebook.data.InMemoryDataService;
import codes.aliahmad.phonebook.model.PhoneBook;
import com.vaadin.flow.component.notification.Notification;

public class ValidationUtil
{
  private final static DataService dataService = InMemoryDataService.getInstance();

  public static void validateDataEditing(PhoneBook editingPhoneBook, PhoneBook existingPhoneBook)
  {

    if (existingPhoneBook == null)
    {
      Notification.show("This record has been already deleted by some other user. Saving this will result in a new record",
              5000, Notification.Position.TOP_CENTER);
      return;
    }
    if (!existingPhoneBook.getLastUpdatedAt().equals(editingPhoneBook.getLastUpdatedAt()))
    {
      Notification.show("This data was already updated by another user", 5000, Notification.Position.TOP_CENTER);
    }

  }

  public static boolean isPhoneNumberValid(String phoneNumber, PhoneBook editingPhoneBook)
  {
    if (editingPhoneBook == null)
    {
      return !dataService.existsByPhoneNumber(phoneNumber);
    }
    return !dataService.existsByPhoneNumberAndIdNot(phoneNumber, editingPhoneBook.getId());
  }
}
