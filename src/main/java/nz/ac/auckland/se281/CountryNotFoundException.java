package nz.ac.auckland.se281;

/** This exception is thrown when the country is not found. */
public class CountryNotFoundException extends Exception {
  public CountryNotFoundException(String countryName) {
    super(countryName);
  }
}
