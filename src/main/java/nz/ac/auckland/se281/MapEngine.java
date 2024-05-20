package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {

  private Map<String, Country> countriesMap;

  public MapEngine() {
    // add other code here if you want
    countriesMap = new HashMap<>();
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures

    for (String line : countries) {
      String[] parts = line.split(",");
      String countryName = parts[0].trim();
      String continent = parts[1].trim();
      int taxFees = Integer.parseInt(parts[2].trim());
      Country country = new Country(countryName, continent, taxFees);
      countriesMap.put(countryName, country);
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    while (true) {
      String countryName = Utils.scanner.nextLine();
      try {
        validateCountryName(countryName);
        Country country = countriesMap.get(countryName);
        MessageCli.COUNTRY_INFO.printMessage(
            country.getName(), country.getContinent(), String.valueOf(country.getTaxFees()));
        break;
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(countryName);
      }
    }
  }

  private void validateCountryName(String countryName) throws CountryNotFoundException {
    if (!countriesMap.containsKey(countryName)) {
      throw new CountryNotFoundException(countryName);
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
