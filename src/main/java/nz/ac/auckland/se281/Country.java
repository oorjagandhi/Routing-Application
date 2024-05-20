package nz.ac.auckland.se281;

public class Country {
  private final String name;
  private final String continent;
  private final int taxFees;

  public Country(String name, String continent, int taxFees) {
    this.name = name;
    this.continent = continent;
    this.taxFees = taxFees;
  }

  public String getName() {
    return name;
  }

  public String getContinent() {
    return continent;
  }

  public int getTaxFees() {
    return taxFees;
  }
}
