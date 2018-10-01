package ca.ulaval.glo4003.domain.stock;

import ca.ulaval.glo4003.domain.money.MoneyAmount;

public class StockValue {
  private MoneyAmount currentValue;
  private MoneyAmount openValue;
  private MoneyAmount closeValue;

  StockValue(MoneyAmount startValue) {
    currentValue = startValue;
    openValue = startValue;
    closeValue = startValue;
  }

  StockValue(MoneyAmount openValue, MoneyAmount closeValue) {
    currentValue = closeValue;
    this.openValue = openValue;
    this.closeValue = closeValue;
  }

  public MoneyAmount getCurrentValue() {
    return currentValue;
  }

  public MoneyAmount getOpenValue() {
    return openValue;
  }

  public MoneyAmount getCloseValue() {
    return closeValue;
  }

  boolean isClosed() {
    return closeValue != null;
  }

  public void setValue(MoneyAmount currentValue) {
    if (isClosed()) {
      openValue = currentValue;
      closeValue = null;
    }

    this.currentValue = currentValue;
  }

  public void close() {
    closeValue = currentValue;
  }
}
