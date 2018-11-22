package ca.ulaval.glo4003.ws.api.market;

import ca.ulaval.glo4003.domain.Component;
import ca.ulaval.glo4003.service.market.MarketStatusDto;
import ca.ulaval.glo4003.ws.api.market.dto.MarketStatusResponseDto;


@Component
public class ApiMarketStatusAssembler {

  public MarketStatusResponseDto toDto(MarketStatusDto marketStatus) {
    return new MarketStatusResponseDto(
        marketStatus.marketId.getValue(),
        getStatusString(marketStatus)
    );
  }

  private String getStatusString(MarketStatusDto marketStatus) {
    return marketStatus.isHalted ? "HALTED" : "TRADING";
  }
}
