package ca.ulaval.glo4003.util;

import ca.ulaval.glo4003.domain.cart.Cart;
import ca.ulaval.glo4003.domain.portfolio.Portfolio;
import ca.ulaval.glo4003.domain.user.User;
import ca.ulaval.glo4003.domain.user.UserRole;

public class UserBuilder {
  public static final String DEFAULT_EMAIL = "email";
  public static final String DEFAULT_PASSWORD = "password";
  private static final UserRole DEFAULT_USER_ROLE = UserRole.INVESTOR;

  private final UserRole userRole = DEFAULT_USER_ROLE;
  private String email = DEFAULT_EMAIL;
  private String password = DEFAULT_PASSWORD;

  public UserBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  public User build() {
    return new User(email, password, userRole, new Cart(), new Portfolio(), null);
  }
}
