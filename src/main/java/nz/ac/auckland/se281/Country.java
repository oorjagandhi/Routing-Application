package nz.ac.auckland.se281;

/** This class represents a country. */
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

  /**
   * Get the hash code of the country.
   *
   * @return the hash code of the country
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /**
   * Check if two countries are equal.
   *
   * @param obj the object to compare
   * @return true if the countries are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Country other = (Country) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  /**
   * Get the string representation of the country.
   *
   * @return the string representation of the country
   */
  @Override
  public String toString() {
    return name;
  }
}
