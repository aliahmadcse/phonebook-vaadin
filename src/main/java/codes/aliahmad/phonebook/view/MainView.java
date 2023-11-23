package codes.aliahmad.phonebook.view;

import codes.aliahmad.phonebook.model.PhoneBook;
import codes.aliahmad.phonebook.provider.PhoneBookDataProvider;
import codes.aliahmad.phonebook.util.ValidationUtil;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.data.binder.Binder;

import java.util.Arrays;
import java.util.List;

@Route("")
public class MainView extends Div
{
  private final Crud<PhoneBook> crud;

  private Grid<PhoneBook> grid;

  private final PhoneBookDataProvider dataProvider;

  private PhoneBook editingPhoneBook;

  private final String FIRST_NAME = "firstName";
  private final String LAST_NAME = "lastName";
  private final String EMAIL = "email";

  public MainView()
  {
    dataProvider = new PhoneBookDataProvider();
    crud = new Crud<>(PhoneBook.class, createEditor());

    setupGrid();
    setupDataProvider();

    add(crud);
  }

  private CrudEditor<PhoneBook> createEditor()
  {
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField street = new TextField("Street");
    TextField city = new TextField("City");
    TextField country = new TextField("Country");
    TextField phone = new TextField("Phone");
    EmailField email = new EmailField("Email");

    FormLayout form = new FormLayout(firstName, lastName, street, city, country, phone,
            email);

    Binder<PhoneBook> binder = new Binder<>(PhoneBook.class);
    binder.forField(firstName).asRequired().bind(PhoneBook::getFirstName,
            PhoneBook::setFirstName);
    binder.forField(lastName).asRequired().bind(PhoneBook::getLastName,
            PhoneBook::setLastName);
    binder.forField(street).asRequired().bind(PhoneBook::getStreet,
            PhoneBook::setStreet);
    binder.forField(city).asRequired().bind(PhoneBook::getCity,
            PhoneBook::setCity);
    binder.forField(country).asRequired().bind(PhoneBook::getCountry,
            PhoneBook::setCountry);

    binder.forField(phone).asRequired()
            .withValidator(phoneNumber -> ValidationUtil.isPhoneNumberValid(phoneNumber, editingPhoneBook), "Phone Number already exist")
            .bind(PhoneBook::getPhone, PhoneBook::setPhone);

    binder.forField(email).asRequired().bind(PhoneBook::getEmail,
            PhoneBook::setEmail);

    return new BinderCrudEditor<>(binder, form);
  }

  private void setupGrid()
  {
    grid = crud.getGrid();

    // Open editor on double click
    grid.addItemDoubleClickListener(event -> {
      PhoneBook phoneBookClone = event.getItem().clone();
      editingPhoneBook = phoneBookClone;

      PhoneBook existingPhoneBook = dataProvider.findById(editingPhoneBook.getId());
      ValidationUtil.validateDataEditing(editingPhoneBook, existingPhoneBook);

      crud.edit(phoneBookClone, Crud.EditMode.EXISTING_ITEM);
    });


    List<String> visibleColumns = Arrays.asList(FIRST_NAME, LAST_NAME,
            EMAIL);

    grid.getColumns().forEach(column -> {
      String key = column.getKey();
      if (!visibleColumns.contains(key))
      {
        grid.removeColumn(column);
      }
    });

    grid.setColumnOrder(grid.getColumnByKey(FIRST_NAME),
            grid.getColumnByKey(LAST_NAME), grid.getColumnByKey(EMAIL));
  }

  private void setupDataProvider()
  {
    crud.setDataProvider(dataProvider);

    crud.addDeleteListener(
            deleteEvent -> dataProvider.delete(deleteEvent.getItem()));

    crud.addSaveListener(saveEvent -> {
      dataProvider.persist(saveEvent.getItem());
    });
  }

  private void refreshGrid()
  {
    grid.getDataProvider().refreshAll();
  }

}
