package ca.ulaval.glo4003.service.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;

import ca.ulaval.glo4003.domain.stock.Stock;
import ca.ulaval.glo4003.domain.stock.StockRepository;
import ca.ulaval.glo4003.domain.stock.query.StockQuery;
import ca.ulaval.glo4003.domain.stock.query.StockQueryBuilder;
import ca.ulaval.glo4003.util.TestStockBuilder;
import com.google.common.collect.Lists;
import java.util.Arrays;
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

  @Mock
  private StockRepository stockRepository;
  @Mock
  private StockAssembler stockAssembler;

  private StockService stockService;

  @Before
  public void setup() {
    stockService = new StockService(stockRepository, stockAssembler);
  }

  @Test
  public void whenGetStockByTitle_thenStockIsGotFromRepository() {
    given(stockRepository.find(any())).willReturn(Arrays.asList(new TestStockBuilder().build()));

    stockService.getStockByTitle(SOME_TITLE);

    StockQuery someStockQuery = new StockQueryBuilder().withTitle(SOME_TITLE).build();
    verify(stockRepository).find(someStockQuery);
  }

  @Test
  public void whenGetStockByTitle_thenWeHaveCorrespondingDto() {
    Stock givenStock = new TestStockBuilder().build();
    StockDto expectedDto = new TestStockBuilder().buildDto();
    given(stockRepository.find(any())).willReturn(Arrays.asList(givenStock));
    given(stockAssembler.toDto(givenStock)).willReturn(expectedDto);

    StockDto resultingDto = stockService.getStockByTitle(SOME_TITLE);

    assertThat(resultingDto).isEqualTo(expectedDto);
  }

  @Test
  public void givenStockNotFound_whenGettingStockByTitle_thenStockDoesNotExistExceptionIsThrown() {
    String wrongTitle = "wrong";
    willReturn(Arrays.asList()).given(stockRepository).find(any());

    ThrowableAssert.ThrowingCallable getStockByTitle =
        () -> stockService.getStockByTitle(wrongTitle);

    assertThatThrownBy(getStockByTitle).isInstanceOf(StockDoesNotExistException.class);
  }

  @Test
  public void givenNameAndCategoryIsNull_whenQueryStocks_thenQueryAllStocks() {
    stockService.queryStocks(null, null);

    verify(stockRepository).find(new StockQueryBuilder().build());
  }

  @Test
  public void whenQueryStocks_thenStockIsGotFromRepository() {
    stockService.queryStocks(SOME_NAME, SOME_CATEGORY);

    StockQuery someStockQuery = new StockQueryBuilder().withName(SOME_NAME).withCategory(SOME_CATEGORY).build();
    verify(stockRepository).find(someStockQuery);
  }

  @Test
  public void whenQueryStocks_thenWeHaveCorrespondingDto() {
    List<Stock> givenStocks = Collections.singletonList(new TestStockBuilder().build());
    List<StockDto> expectedDtos = Collections.singletonList(new TestStockBuilder().buildDto());
    given(stockRepository.find(any())).willReturn(givenStocks);
    given(stockAssembler.toDtoList(givenStocks)).willReturn(expectedDtos);

    List<StockDto> resultingDtos = stockService.queryStocks(SOME_NAME, SOME_CATEGORY);

    assertThat(resultingDtos).isSameAs(expectedDtos);
  }

  @Test
  public void whenGettingCategories_thenReturnCategories() {
    List<String> expectedCategories = Lists.newArrayList("technology", "banking", "media");
    given(stockRepository.findAllCategories()).willReturn(expectedCategories);

    List<String> resultingCategories = stockService.getCategories();

    assertThat(resultingCategories).isSameAs(expectedCategories);
  }
}
