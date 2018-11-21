package ca.ulaval.glo4003.domain.stock;

import ca.ulaval.glo4003.domain.market.MarketId;
import ca.ulaval.glo4003.domain.money.Currency;
import ca.ulaval.glo4003.domain.money.MoneyAmount;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Stock {
  private final String title;
  private final String name;
  private final String category;
  private final MarketId marketId;
  private final StockHistory valueHistory;

  public Stock(String title, String name, String category, MarketId marketId,
               StockHistory valueHistory) {
    this.title = title;
    this.name = name;
    this.category = category;
    this.marketId = marketId;
    this.valueHistory = valueHistory;
  }

  public String getTitle() {
    return title;
  }

  public String getName() {
    return name;
  }

  public String getCategory() {
    return category;
  }

  public MarketId getMarketId() {
    return marketId;
  }

  public StockHistory getValueHistory() {
    return valueHistory;
  }

  public Currency getCurrency() {
    return getValue().getLatestValue().getCurrency();
  }

  public synchronized void updateValue(BigDecimal variation) {
    getValue().updateValue(variation);
  }

  public synchronized StockValue getValue() {
    return valueHistory.getLatestValue().value;
  }

  public synchronized StockValue getValueOnDay(LocalDate date) throws NoStockValueFitsCriteriaException {
    return getValueHistory().getValueOnDay(date);
  }

  public synchronized void open() {
    MoneyAmount startValue = getValue().getLatestValue();
    StockValue newStockValue = new StockValue(startValue);

    valueHistory.addNextValue(newStockValue);
  }

  public synchronized void close() {
    getValue().close();
  }
}
