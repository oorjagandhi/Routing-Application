package nz.ac.auckland.se281;

/** This class represents a country */
public class Country {
  private final String name;
  private final String continent;
  private final int taxFees;

  /**
   * Constructor to create a country object.
   *
   * @param name the name of the country
   * @param continent the continent of the country
   * @param taxFees the tax fees of the country
   */
  public Country(String name, String continent, int taxFees) {
    this.name = name;
    this.continent = continent;
    this.taxFees = taxFees;
  }

  /**
   * Get the name of the country.
   *
   * @return the name of the country
   */
  public String getName() {
    return name;
  }

  /**
   * Get the continent of the country.
   *
   * @return the continent of the country
   */
  public String getContinent() {
    return continent;
  }

  /**
   * Get the tax fees of the country.
   *
   * @return the tax fees of the country
   */
  public int getTaxFees() {
    return taxFees;
  }
}
