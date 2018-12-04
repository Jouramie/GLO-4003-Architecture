package ca.ulaval.glo4003.ws.api.stock.max;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import ca.ulaval.glo4003.service.stock.max.StockMaxValueService;
import ca.ulaval.glo4003.service.stock.max.dto.StockMaxValueSummary;
import ca.ulaval.glo4003.ws.api.stock.assemblers.StockMaxResponseDtoAssembler;
import ca.ulaval.glo4003.ws.api.stock.dtos.StockMaxResponseDto;
import ca.ulaval.glo4003.ws.api.stock.resources.StockMaxResourceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StockMaxResourceTest {
  private static final String SOME_TITLE = "title";

  @Mock
  private StockMaxValueService stockMaxValueService;
  @Mock
  private StockMaxResponseDtoAssembler stockMaxResponseDtoAssembler;
  @Mock
  private StockMaxValueSummary expectedSummary;
  @Mock
  private StockMaxResponseDto expectedMaxResponseDto;

  private StockMaxResourceImpl stockMaxResource;

  @Before
  public void setupStockMaxResourceImpl() {
    stockMaxResource = new StockMaxResourceImpl(stockMaxValueService, stockMaxResponseDtoAssembler);
  }

  @Test
  public void whenGetStockMaxValue_thenReturningDto() {
    given(stockMaxValueService.getStockMaxValue(SOME_TITLE)).willReturn(expectedSummary);
    given(stockMaxResponseDtoAssembler.toDto(SOME_TITLE, expectedSummary)).willReturn(expectedMaxResponseDto);

    StockMaxResponseDto resultingDto = stockMaxResource.getStockMaxValue(SOME_TITLE);

    assertThat(resultingDto).isEqualTo(expectedMaxResponseDto);
  }
}
