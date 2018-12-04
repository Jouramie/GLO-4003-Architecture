package ca.ulaval.glo4003.domain.transaction;

import ca.ulaval.glo4003.domain.Component;
import ca.ulaval.glo4003.domain.clock.Clock;
import ca.ulaval.glo4003.domain.money.MoneyAmount;
import ca.ulaval.glo4003.domain.stock.Stock;
import ca.ulaval.glo4003.domain.stock.StockCollection;
import ca.ulaval.glo4003.domain.stock.StockNotFoundException;
import ca.ulaval.glo4003.domain.stock.StockRepository;
import ca.ulaval.glo4003.domain.stock.query.StockQuery;
import ca.ulaval.glo4003.domain.stock.query.StockQueryBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@Component
public class TransactionFactory {
  private final Clock clock;
  private final StockRepository stockRepository;

  @Inject
  public TransactionFactory(Clock clock, StockRepository stockRepository) {
    this.clock = clock;
    this.stockRepository = stockRepository;
  }

  public Transaction createPurchase(StockCollection stocks) throws StockNotFoundException {
    List<TransactionItem> transactionItems = buildTransactionItems(stocks);
    return new Transaction(clock.getCurrentTime(), transactionItems, TransactionType.PURCHASE);
  }

  private List<TransactionItem> buildTransactionItems(StockCollection items) throws StockNotFoundException {
    List<TransactionItem> transactionItems = new ArrayList<>();
    for (String title : items.getTitles()) {
      int quantity = items.getQuantity(title);
      transactionItems.add(buildTransactionItem(title, quantity));
    }
    return transactionItems;
  }

  private TransactionItem buildTransactionItem(String title, int quantity) throws StockNotFoundException {
    Stock stock = getStock(title);
    MoneyAmount amount = stock.getValue().getLatestValue();
    return new TransactionItem(title, quantity, amount);
  }

  private Stock getStock(String title) throws StockNotFoundException {
    StockQuery stockQuery = new StockQueryBuilder().withTitle(title).build();
    List<Stock> stocks = stockRepository.find(stockQuery);
    if (stocks.isEmpty()) {
      throw new StockNotFoundException(title);
    }
    return stocks.get(0);
  }
}
