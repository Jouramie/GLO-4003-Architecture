package ca.ulaval.glo4003.domain.user;

import ca.ulaval.glo4003.domain.Component;
import ca.ulaval.glo4003.domain.cart.Cart;
import ca.ulaval.glo4003.domain.portfolio.Portfolio;

@Component
public class UserFactory {
  public User createInvestor(String email, String password) {
    return new User(email, password, UserRole.INVESTOR, new Cart(), new Portfolio(), null);
  }

  public User createAdministrator(String email, String password) {
    return new User(email, password, UserRole.ADMINISTRATOR, new Cart(), new Portfolio(), null);
  }
}
