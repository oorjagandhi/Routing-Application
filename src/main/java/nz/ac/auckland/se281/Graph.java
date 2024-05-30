package nz.ac.auckland.se281;

import java.util.*;

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
    return adjacencyMap.get(country);
  }
}
