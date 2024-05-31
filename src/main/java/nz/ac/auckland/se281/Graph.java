package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/** This class represents a graph. */
public class Graph {

  private final Map<Country, Set<Country>> adjacencyMap;

  /** Constructor to create a graph object. */
  public Graph() {
    this.adjacencyMap = new HashMap<>();
  }

  /**
   * Add an edge to the graph.
   *
   * @param from the country from
   * @param to the country to
   */
  public void addEdge(Country from, Country to) {
    // Add the from country to the adjacencyMap if it does not exist
    adjacencyMap.putIfAbsent(from, new LinkedHashSet<>());
    adjacencyMap.get(from).add(to);
  }

  /**
   * Get the adjacent nodes of a country.
   *
   * @param country the country
   * @return the adjacent nodes of the country
   */
  public Set<Country> getAdjacentNodes(Country country) {
    // Return the adjacent nodes of the country
    return adjacencyMap.get(country);
  }
}
