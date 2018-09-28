package ca.ulaval.glo4003.ws.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class UserFactoryTest {

  private static final UserRole ROLE = UserRole.ADMINISTRATOR;
  private static final String PASSWORD = "password";
  private static final String USERNAME = "username";

  private UserFactory factory = new UserFactory();

  @Test
  public void whenCreatingUser_thenReturnCreatedUser() {
    User expectedUser = new User(USERNAME, PASSWORD, ROLE);

    User createdUser = factory.create(USERNAME, PASSWORD, ROLE);

    assertThat(createdUser).isEqualToComparingFieldByField(expectedUser);
  }
}
