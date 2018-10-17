package ca.ulaval.glo4003.service.stock.max;

import ca.ulaval.glo4003.domain.clock.Clock;
import ca.ulaval.glo4003.domain.stock.HistoricalStockValue;
import ca.ulaval.glo4003.domain.stock.NoStockValueFitsCriteriaException;
import ca.ulaval.glo4003.domain.stock.Stock;
import ca.ulaval.glo4003.service.InternalErrorException;
import java.time.LocalDate;
import javax.annotation.Resource;
import javax.inject.Inject;

@Resource
public class StockMaxValueRetriever {
  private final Clock clock;

  @Inject
  public StockMaxValueRetriever(Clock clock) {
    this.clock = clock;
  }

  public HistoricalStockValue getStockMaxValue(Stock stock, StockMaxValueSinceRange range) throws NoStockValueFitsCriteriaException {
    LocalDate currentDate = clock.getCurrentTime().toLocalDate();
    LocalDate from = null;
    LocalDate to = null;
    switch (range) {
      case CURRENT_MONTH:
        from = currentDate.withDayOfMonth(1);
        to = currentDate;
        break;
      case LAST_FIVE_DAYS:
        from = currentDate.minusDays(5);
        to = currentDate;
        break;
      case LAST_MONTH:
        LocalDate lastMonth = currentDate.minusMonths(1);
        from = lastMonth.withDayOfMonth(1);
        to = lastMonth.withDayOfMonth(lastMonth.lengthOfMonth());
        break;
      case LAST_YEAR:
        from = currentDate.minusYears(1);
        to = currentDate;
        break;
      case LAST_FIVE_YEARS:
        from = currentDate.minusYears(5);
        to = currentDate;
        break;
      case LAST_TEN_YEARS:
        from = currentDate.minusYears(10);
        to = currentDate;
        break;
      case ALL_TIME:
        from = LocalDate.MIN;
        to = currentDate;
        break;
      default:
        throw new InternalErrorException("Unhandled case of StockMaxValueSinceRange.");
    }

    return stock.getValueHistory().getMaxValue(from, to);
  }
}
