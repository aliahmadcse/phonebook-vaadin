package codes.aliahmad.phonebook.provider;

import codes.aliahmad.phonebook.data.DataService;
import codes.aliahmad.phonebook.data.InMemoryDataService;
import codes.aliahmad.phonebook.model.PhoneBook;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Comparator.naturalOrder;

public class PhoneBookDataProvider extends AbstractBackEndDataProvider<PhoneBook, CrudFilter>
{
  private final DataService dataService = new InMemoryDataService();

  private Consumer<Long> sizeChangeListener;

  @Override
  protected Stream<PhoneBook> fetchFromBackEnd(Query<PhoneBook, CrudFilter> query)
  {
    int offset = query.getOffset();
    int limit = query.getLimit();

    Stream<PhoneBook> stream = dataService.findAll().stream();

    if (query.getFilter().isPresent())
    {
      stream = stream.filter(predicate(query.getFilter().get()))
              .sorted(comparator(query.getFilter().get()));
    }

    return stream.skip(offset).limit(limit);
  }

  @Override
  protected int sizeInBackEnd(Query<PhoneBook, CrudFilter> query)
  {
    // For RDBMS just execute a SELECT COUNT(*) ... WHERE query
    long count = fetchFromBackEnd(query).count();

    if (sizeChangeListener != null)
    {
      sizeChangeListener.accept(count);
    }

    return (int) count;
  }

  void setSizeChangeListener(Consumer<Long> listener)
  {
    sizeChangeListener = listener;
  }

  private static Predicate<PhoneBook> predicate(CrudFilter filter)
  {
    // For RDBMS just generate a WHERE clause
    return filter.getConstraints().entrySet().stream()
            .map(constraint -> (Predicate<PhoneBook>) person -> {
              try
              {
                Object value = valueOf(constraint.getKey(), person);
                return value != null && value.toString().toLowerCase()
                        .contains(constraint.getValue().toLowerCase());
              }
              catch (Exception e)
              {
                e.printStackTrace();
                return false;
              }
            }).reduce(Predicate::and).orElse(e -> true);
  }

  private static Comparator<PhoneBook> comparator(CrudFilter filter)
  {
    // For RDBMS just generate an ORDER BY clause
    return filter.getSortOrders().entrySet().stream().map(sortClause -> {
      try
      {
        Comparator<PhoneBook> comparator = Comparator.comparing(
                person -> (Comparable) valueOf(sortClause.getKey(),
                        person));

        if (sortClause.getValue() == SortDirection.DESCENDING)
        {
          comparator = comparator.reversed();
        }

        return comparator;

      }
      catch (Exception ex)
      {
        return (Comparator<PhoneBook>) (o1, o2) -> 0;
      }
    }).reduce(Comparator::thenComparing).orElse((o1, o2) -> 0);
  }

  private static Object valueOf(String fieldName, PhoneBook person)
  {
    try
    {
      Field field = PhoneBook.class.getDeclaredField(fieldName);
      field.setAccessible(true);
      return field.get(person);
    }
    catch (Exception ex)
    {
      throw new RuntimeException(ex);
    }
  }

  public void persist(PhoneBook item)
  {
    boolean existingItem = item.getId() != null;

    if (!existingItem)
    {
      item.setId(dataService.findAll().stream().map(PhoneBook::getId).max(naturalOrder())
              .orElse(0) + 1);
      dataService.create(item);
    }
    else
    {
      dataService.update(item);
    }
  }

  Optional<PhoneBook> find(Integer id)
  {
    return dataService.findAll().stream().filter(entity -> entity.getId().equals(id))
            .findFirst();
  }

  public void delete(PhoneBook item)
  {
    dataService.delete(item.getId());
  }

  public boolean isPhoneNumberValid(String phoneNumber)
  {
    dataService.findByPhoneNumber(phoneNumber);

    return !dataService.existsByPhoneNumber(phoneNumber);
  }
}
