package codes.aliahmad.phonebook.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhoneBook
{
  private String firstName;
  private String lastName;
  private String street;
  private String city;
  private String country;
  private String phone;
  private String email;
}
