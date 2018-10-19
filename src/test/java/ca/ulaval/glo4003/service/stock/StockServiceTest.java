package ca.ulaval.glo4003.service.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

import ca.ulaval.glo4003.domain.stock.HistoricalStockValue;
import ca.ulaval.glo4003.domain.stock.NoStockValueFitsCriteriaException;
import ca.ulaval.glo4003.domain.stock.Stock;
import ca.ulaval.glo4003.domain.stock.StockNotFoundException;
import ca.ulaval.glo4003.domain.stock.StockRepository;
import ca.ulaval.glo4003.service.stock.max.StockMaxResponseAssembler;
import ca.ulaval.glo4003.service.stock.max.StockMaxValueRetriever;
import ca.ulaval.glo4003.service.stock.max.StockMaxValueSinceRange;
import ca.ulaval.glo4003.util.TestStockBuilder;
import ca.ulaval.glo4003.ws.api.stock.StockDto;
import ca.ulaval.glo4003.ws.api.stock.max.StockMaxResponseDto;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {
  private static final String SOME_TITLE = TestStockBuilder.DEFAULT_TITLE;
  private static final String SOME_NAME = TestStockBuilder.DEFAULT_NAME;
  private static final String SOME_CATEGORY = TestStockBuilder.DEFAULT_CATEGORY;
  private static final StockMaxValueSinceRange SOME_RANGE = StockMaxValueSinceRange.LAST_FIVE_DAYS;

  @Mock
  private StockRepository stockRepository;
  @Mock
  private StockAssembler stockAssembler;
  @Mock
  private StockMaxValueRetriever stockMaxValueRetriever;
  @Mock
  private StockMaxResponseAssembler stockMaxResponseAssembler;
  @Mock
  private Stock givenStock;
  @Mock
  private StockDto expectedDto;
  @Mock
  private HistoricalStockValue givenMaximumStockValue;
  @Mock
  private StockMaxResponseDto expectedMaxResponseDto;


  private StockService stockService;

  @Before
  public void setup() {
    stockService = new StockService(stockRepository, stockAssembler, stockMaxValueRetriever,
        stockMaxResponseAssembler);
  }

  @Test
  public void whenGetStockByTitle_thenStockIsGotFromRepository() throws StockNotFoundException {
    stockService.getStockByTitle(SOME_TITLE);

    verify(stockRepository).getByTitle(SOME_TITLE);
  }

  @Test
  public void whenGetStockByTitle_thenWeHaveCorrespondingDto() throws StockNotFoundException {
    Stock givenStock = new TestStockBuilder().build();
    StockDto expectedDto = new TestStockBuilder().buildDto();
    given(stockRepository.getByTitle(any())).willReturn(givenStock);
    given(stockAssembler.toDto(givenStock)).willReturn(expectedDto);

    StockDto resultingDto = stockService.getStockByTitle(SOME_TITLE);

    assertThat(resultingDto).isEqualTo(expectedDto);
  }

  @Test
  public void givenStockNotFound_whenGettingStockByTitle_thenStockDoesNotExistExceptionIsThrown()
      throws StockNotFoundException {
    String wrongTitle = "wrong";
    doThrow(StockNotFoundException.class).when(stockRepository).getByTitle(any());

    ThrowableAssert.ThrowingCallable getStockByTitle =
        () -> stockService.getStockByTitle(wrongTitle);

    assertThatThrownBy(getStockByTitle).isInstanceOf(StockDoesNotExistException.class);
  }

  @Test
  public void whenQueryStocks_thenStockIsGotFromRepository() {
    stockService.queryStocks(SOME_NAME, SOME_CATEGORY);

    verify(stockRepository).queryStocks(SOME_NAME, SOME_CATEGORY);
  }

  @Test
  public void whenQueryStocks_thenWeHaveCorrespondingDto() {
    List<Stock> givenStocks = Collections.singletonList(new TestStockBuilder().build());
    List<StockDto> expectedDtos = Collections.singletonList(new TestStockBuilder().buildDto());
    given(stockRepository.queryStocks(any(), any())).willReturn(givenStocks);
    given(stockAssembler.toDtoList(givenStocks)).willReturn(expectedDtos);

    List<StockDto> resultingDtos = stockService.queryStocks(SOME_NAME, SOME_CATEGORY);

    assertThat(resultingDtos).isSameAs(expectedDtos);
  }

  @Test
  public void whenGetStockMaxValue_thenWeHaveCorrespondingDto() throws StockNotFoundException, NoStockValueFitsCriteriaException {
    given(stockRepository.getByTitle(SOME_TITLE)).willReturn(givenStock);
    given(stockMaxValueRetriever.getStockMaxValue(givenStock, SOME_RANGE)).willReturn(givenMaximumStockValue);
    given(stockMaxResponseAssembler.toDto(SOME_TITLE, givenMaximumStockValue)).willReturn(expectedMaxResponseDto);

    StockMaxResponseDto resultingDto = stockService.getStockMaxValue(SOME_TITLE, SOME_RANGE);

    assertThat(resultingDto).isEqualTo(expectedMaxResponseDto);
  }

  @Test
  public void givenStockDoesNotExist_whenGetStockMaxValue_thenStockDoesNotExistExceptionIsThrown()
      throws StockNotFoundException {
    doThrow(StockNotFoundException.class).when(stockRepository).getByTitle(any());

    assertThatThrownBy(() -> stockService.getStockMaxValue(SOME_TITLE, SOME_RANGE))
        .isInstanceOf(StockDoesNotExistException.class);
  }

  @Test
  public void whenGettingCategories_thenReturnCategories() {
    List<String> expectedCategories = Lists.newArrayList("technology", "banking", "media");
    given(stockRepository.getCategories()).willReturn(expectedCategories);

    List<String> resultingCategories = stockService.getCategories();

    assertThat(resultingCategories).isSameAs(expectedCategories);
  }
}
