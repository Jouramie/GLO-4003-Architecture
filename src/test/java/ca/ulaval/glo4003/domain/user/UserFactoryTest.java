package ca.ulaval.glo4003.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import ca.ulaval.glo4003.domain.cart.Cart;
import ca.ulaval.glo4003.domain.portfolio.Portfolio;
import org.junit.Test;

public class UserFactoryTest {
  private static final String PASSWORD = "password";
  private static final String EMAIL = "email";

  private final UserFactory factory = new UserFactory();

  @Test
  public void whenCreatingInvestor_thenReturnInvestorUser() {
    User expectedUser = new User(EMAIL, PASSWORD, UserRole.INVESTOR, new Cart(), new Portfolio(), null);

    User createdUser = factory.createInvestor(EMAIL, PASSWORD);

    assertThat(createdUser).isEqualToComparingOnlyGivenFields(expectedUser, "email", "password", "role");
  }

  @Test
  public void whenCreatingAdministrator_thenReturnAdministratorUser() {
    User expectedUser = new User(EMAIL, PASSWORD, UserRole.ADMINISTRATOR, new Cart(), new Portfolio(), null);

    User createdUser = factory.createAdministrator(EMAIL, PASSWORD);

    assertThat(createdUser).isEqualToComparingOnlyGivenFields(expectedUser, "email", "password", "role");
  }
}
