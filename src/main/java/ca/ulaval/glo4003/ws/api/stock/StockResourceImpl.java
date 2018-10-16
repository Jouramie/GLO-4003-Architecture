package ca.ulaval.glo4003.ws.api.stock;

import ca.ulaval.glo4003.service.stock.StockService;
import ca.ulaval.glo4003.service.stock.max.StockMaxValueSinceRange;
import ca.ulaval.glo4003.ws.api.stock.max.StockMaxResponseDto;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

@Resource
public class StockResourceImpl implements StockResource {

  private final StockService stockService;

  @Inject
  public StockResourceImpl(StockService stockService) {
    this.stockService = stockService;
  }

  @Override
  public StockDto getStockByTitle(String title) {
    return stockService.getStockByTitle(title);
  }

  @Override
  public List<StockDto> getStocks(String name, String category, int page, int perPage) {
    if (name == null || name.isEmpty()) {
      throw new BadRequestException("Missing 'name' query parameter");
    }
    return Collections.singletonList(stockService.getStockByName(name));
  }

  @Override
  public StockMaxResponseDto getStockMaxValue(String title, String since) {
    if (since == null) {
      throw new BadRequestException("Missing 'since' query parameter.");
    }

    try {
      StockMaxValueSinceRange sinceParameter = StockMaxValueSinceRange.valueOf(since);
      return stockService.getStockMaxValue(title, sinceParameter);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Invalid 'since' query parameter");
    }
  }
}
