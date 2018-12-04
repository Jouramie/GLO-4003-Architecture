package ca.ulaval.glo4003.service.portfolio;

import ca.ulaval.glo4003.domain.Component;
import ca.ulaval.glo4003.domain.clock.Clock;
import ca.ulaval.glo4003.domain.portfolio.HistoricalPortfolio;
import ca.ulaval.glo4003.domain.portfolio.Portfolio;
import ca.ulaval.glo4003.domain.stock.NoStockValueFitsCriteriaException;
import ca.ulaval.glo4003.domain.stock.StockNotFoundException;
import ca.ulaval.glo4003.domain.stock.StockRepository;
import ca.ulaval.glo4003.domain.user.CurrentUserSession;
import ca.ulaval.glo4003.domain.user.Investor;
import ca.ulaval.glo4003.service.portfolio.dto.PortfolioDto;
import ca.ulaval.glo4003.service.portfolio.dto.PortfolioReportDto;
import java.time.LocalDate;
import java.util.TreeSet;
import javax.inject.Inject;

@Component
public class PortfolioService {
  private final CurrentUserSession currentUserSession;
  private final PortfolioAssembler portfolioAssembler;
  private final PortfolioReportAssembler portfolioReportAssembler;
  private final Clock clock;
  private final StockRepository stockRepository;

  @Inject
  public PortfolioService(CurrentUserSession currentUserSession,
                          PortfolioAssembler portfolioAssembler,
                          PortfolioReportAssembler portfolioReportAssembler,
                          Clock clock,
                          StockRepository stockRepository) {
    this.currentUserSession = currentUserSession;
    this.portfolioAssembler = portfolioAssembler;
    this.portfolioReportAssembler = portfolioReportAssembler;
    this.clock = clock;
    this.stockRepository = stockRepository;
  }

  public PortfolioDto getPortfolio() throws InvalidPortfolioException {
    Portfolio portfolio = currentUserSession.getCurrentUser(Investor.class).getPortfolio();
    return portfolioAssembler.toDto(portfolio);
  }

  public PortfolioReportDto getPortfolioReport(LocalDate from) {
    try {
      Investor investor = currentUserSession.getCurrentUser(Investor.class);
      Portfolio portfolio = investor.getPortfolio();
      TreeSet<HistoricalPortfolio> portfolios = portfolio.getHistory(from, clock.getCurrentTime().toLocalDate());
      String mostIncreasingStockTitle = portfolio.getMostIncreasingStockTitle(from, stockRepository);
      String mostDecreasingStockTitle = portfolio.getMostDecreasingStockTitle(from, stockRepository);
      return portfolioReportAssembler.toDto(portfolios, mostIncreasingStockTitle, mostDecreasingStockTitle);
    } catch (StockNotFoundException | NoStockValueFitsCriteriaException e) {
      throw new InvalidPortfolioException();
    }
  }
}
