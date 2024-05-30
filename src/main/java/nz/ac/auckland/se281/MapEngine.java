package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

  // Initialize the countriesMap and adjacencyMap
  private final Map<String, Country> countriesMap;
  private final Graph graph = new Graph();

  /** Constructor for the MapEngine class. */
  public MapEngine() {
    // add other code here if you want
    countriesMap = new HashMap<>();
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
      Country country = countriesMap.get(country1);

      // Add the countries to the adjacencyMap
      for (int i = 1; i < parts.length; i++) {
        String country2 = parts[i].trim();
        Country adjCountry = countriesMap.get(country2);
        // Make them adjacent to each other
        graph.addEdge(country, adjCountry);
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
    // Check if the country is not found
    if (!countriesMap.containsKey(countryName)) {
      throw new CountryNotFoundException(countryName);
    }
  }

  /** This method is invoked when the user run the command route. */
  public void showRoute() {
    // Ask the user to insert the start and end countries
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

    // Ask the user to insert the end country
    MessageCli.INSERT_DESTINATION.printMessage();
    String endCountry;

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

    List<Country> route = findRoute(startCountry, endCountry);
    MessageCli.ROUTE_INFO.printMessage(route.toString());

    Set<String> visitedContinents = new LinkedHashSet<>();
    int totalTaxes = 0;
    boolean isFirstCountry = true;
    for (Country country : route) {
      visitedContinents.add(country.getContinent());
      if (!isFirstCountry) {
        totalTaxes += country.getTaxFees();
      }
      isFirstCountry = false;
    }

    MessageCli.CONTINENT_INFO.printMessage(visitedContinents.toString());
    MessageCli.TAX_INFO.printMessage(String.valueOf(totalTaxes));
  }

  /**
   * This method is used to compute the route between two countries.
   *
   * @param start The start country.
   * @param end The end country.
   * @return The route between the two countries.
   */
  private List<Country> findRoute(String start, String end) {
    Queue<Country> queue = new LinkedList<>();
    Map<Country, Country> predecessors = new HashMap<>();
    Set<Country> visited = new HashSet<>();

    Country startCountry = countriesMap.get(start);
    Country endCountry = countriesMap.get(end);
    queue.add(startCountry);
    visited.add(startCountry);
    predecessors.put(startCountry, null); // Initialize the start with no predecessor

    while (!queue.isEmpty()) {
      Country current = queue.poll();
      Set<Country> neighbors = graph.getAdjacentNodes(current);
      for (Country neighbor : neighbors) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          predecessors.put(neighbor, current);
          queue.add(neighbor);

          if (neighbor.equals(endCountry)) {
            // Construct the route from end to start using the predecessors
            List<Country> routePath = new ArrayList<>();
            Country currentNode = endCountry; // Start backtracking from the end node

            while (currentNode != null) {
              // Add the current node to the path
              routePath.add(currentNode);
              // Move to the next node in the path towards the start
              currentNode = predecessors.get(currentNode);
            }

            Collections.reverse(routePath); // The path was constructed backwards, so reverse it
            return routePath; // Return the route from start to end
          }
        }
      }
    }

    return Collections.emptyList(); // Return an empty list if no route is found
  }
}
