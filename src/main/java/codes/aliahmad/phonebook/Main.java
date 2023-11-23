package codes.aliahmad.phonebook;

import codes.aliahmad.phonebook.data.DataService;
import codes.aliahmad.phonebook.data.MysqlDataService;

public class Main
{
  public static void main(String[] args)
  {
    DataService dataService = MysqlDataService.getInstance();

    dataService.findAll();

  }

}
