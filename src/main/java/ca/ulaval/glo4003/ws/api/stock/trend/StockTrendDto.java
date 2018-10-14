package ca.ulaval.glo4003.ws.api.stock.trend;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "StockTrendResponce",
    description = "Stock trends for a given stock title."
)
public class StockTrendDto {
  @Schema(description = "Relevant stock title")
  public final String title;
  @Schema(description = "Variation trend for the last 5 days")
  public final StockTrend last5Days;
  @Schema(description = "Variation trend for the last 30 days")
  public final StockTrend last30Days;
  @Schema(description = "Variation trend for the last year")
  public final StockTrend lastYear;

  public StockTrendDto(String title, StockTrend last5Days, StockTrend last30Days, StockTrend lastYear) {
    this.title = title;
    this.last5Days = last5Days;
    this.last30Days = last30Days;
    this.lastYear = lastYear;
  }
}
