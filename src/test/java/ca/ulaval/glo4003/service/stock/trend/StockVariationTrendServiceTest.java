package ca.ulaval.glo4003.service.stock.trend;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.domain.clock.Clock;
import ca.ulaval.glo4003.domain.stock.Stock;
import ca.ulaval.glo4003.domain.stock.StockNotFoundException;
import ca.ulaval.glo4003.domain.stock.StockRepository;
import ca.ulaval.glo4003.service.time.HistoricalDateService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StockVariationTrendServiceTest {

  public static final String STOCK_TITLE = "RBS.l";
  private final Clock clock = new Clock(LocalDateTime.of(1984, Month.JANUARY, 24, 10, 0), Duration.ZERO);
  @Mock
  private StockRepository stockRepositoryMock;
  @Mock
  private StockVariationCalculator stockVariationCalculatorMock;
  private HistoricalDateService historicalDateService;
  private StockVariationTrendService stockVariationTrendService;

  @Before
  public void initialize() {
    historicalDateService = new HistoricalDateService(clock);
    stockVariationTrendService = new StockVariationTrendService(stockRepositoryMock, historicalDateService, stockVariationCalculatorMock);
  }

  @Test
  public void whenGettingStockVariationSummary_thenFetchStockAndSendsToCalculator() throws StockNotFoundException {
    given(stockRepositoryMock.findByTitle(STOCK_TITLE)).willReturn(mock(Stock.class));

    stockVariationTrendService.getStockVariationSummary(STOCK_TITLE);

    verify(stockRepositoryMock).findByTitle(STOCK_TITLE);
    verify(stockVariationCalculatorMock).getStockVariationTrendSinceDate(any(), eq(historicalDateService.getFiveDaysAgo()));
    verify(stockVariationCalculatorMock).getStockVariationTrendSinceDate(any(), eq(historicalDateService.getThirtyDaysAgo()));
    verify(stockVariationCalculatorMock).getStockVariationTrendSinceDate(any(), eq(historicalDateService.getOneYearAgo()));
  }
}
