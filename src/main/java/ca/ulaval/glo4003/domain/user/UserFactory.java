package ca.ulaval.glo4003.domain.user;

import ca.ulaval.glo4003.domain.stock.StockRepository;
import ca.ulaval.glo4003.infrastructure.injection.Component;
import javax.inject.Inject;

@Component
public class UserFactory {

  private final StockRepository stockRepository;

  @Inject
  public UserFactory(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  public User create(String email, String password, UserRole role) {
    return new User(email, password, role, stockRepository);
  }
}
