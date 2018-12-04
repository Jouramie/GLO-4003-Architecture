package ca.ulaval.glo4003.service.cart.exceptions;

public class InvalidStockTitleException extends RuntimeException {
  public InvalidStockTitleException(String title) {
    super("Stock " + title + " does not exist.");
  }
}
