package codes.aliahmad.phonebook.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PhoneBook implements Cloneable
{
  private Integer id;
  private String firstName;
  private String lastName;
  private String street;
  private String city;
  private String country;
  private String phone;
  private String email;
  private Date lastUpdatedAt;


  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
    PhoneBook phoneBook = (PhoneBook) o;
    return Objects.equals(id, phoneBook.id);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id);
  }

  @Override
  public PhoneBook clone()
  {
    try
    {
      return (PhoneBook) super.clone();
    }
    catch (CloneNotSupportedException e)
    {
      throw new AssertionError();
    }
  }
}
