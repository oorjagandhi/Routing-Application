package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

  private Map<String, Country> countriesMap;
  private Map<String, Set<String>> adjacencyMap;

  /** Constructor for the MapEngine class. */
  public MapEngine() {
    // add other code here if you want
    countriesMap = new HashMap<>();
    adjacencyMap = new HashMap<>();
    loadMap(); // keep this mehtod invocation
  }

  /** Invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    // Parse the countries and add them to the countriesMap
    for (String line : countries) {
      String[] parts = line.split(",");
      String countryName = parts[0].trim();
      String continent = parts[1].trim();
      int taxFees = Integer.parseInt(parts[2].trim());
      Country country = new Country(countryName, continent, taxFees);
      countriesMap.put(countryName, country);
    }

    // Parse the adjacencies and add them to the adjacencyMap
    for (String line : adjacencies) {
      String[] parts = line.split(",");
      String country1 = parts[0].trim();
      adjacencyMap.putIfAbsent(country1, new HashSet<>());
      for (int i = 1; i < parts.length; i++) {
        String country2 = parts[i].trim();
        adjacencyMap.putIfAbsent(country2, new HashSet<>());
        adjacencyMap.get(country1).add(country2);
        adjacencyMap.get(country2).add(country1);
      }
    }
  }

  /** This method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    // Read the user input and validate it
    while (true) {
      String input = Utils.scanner.nextLine();
      // Capitalize the first letter of each word
      String countryName = Utils.capitalizeFirstLetterOfEachWord(input);
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

  /** This method is invoked when the user run the command info-country. */
  private void validateCountryName(String countryName) throws CountryNotFoundException {
    if (!countriesMap.containsKey(countryName)) {
      throw new CountryNotFoundException(countryName);
    }
  }

  /** This method is invoked when the user run the command route. */
  public void showRoute() {
    MessageCli.INSERT_SOURCE.printMessage();

    String startCountry;

    // Read the user input and validate it
    while (true) {
      String input = Utils.scanner.nextLine();
      // Capitalize the first letter of each word
      startCountry = Utils.capitalizeFirstLetterOfEachWord(input);
      try {
        validateCountryName(startCountry);
        break;
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(startCountry);
      }
    }

    String endCountry;
    MessageCli.INSERT_DESTINATION.printMessage();

    // Read the user input and validate it
    while (true) {
      String input2 = Utils.scanner.nextLine();
      // Capitalize the first letter of each word
      endCountry = Utils.capitalizeFirstLetterOfEachWord(input2);
      try {
        validateCountryName(endCountry);
        break;
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(endCountry);
      }
    }

    // Check if the start and end countries are the same
    if (startCountry.equals(endCountry)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }
  }

  /** This method is invoked when the user run the command route. */
}
