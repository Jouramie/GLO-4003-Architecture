package ca.ulaval.glo4003.ws.application.user.authentication;

import static junit.framework.TestCase.assertEquals;

import ca.ulaval.glo4003.ws.api.authentication.AuthenticationResponseDto;
import ca.ulaval.glo4003.ws.domain.user.authentication.AuthenticationToken;
import org.junit.Test;

public class AuthenticationResponseAssemblerTest {

  private static final String SECRET_TOKEN = "token";
  private static final AuthenticationToken SOME_AUTHENTICATION_TOKEN = new AuthenticationToken(SECRET_TOKEN, "username");
  private final AuthenticationResponseAssembler responseAssembler = new AuthenticationResponseAssembler();

  @Test
  public void whenAssemblingTokenToDto_thenReturnAuthenticationResponseDto() {
    AuthenticationResponseDto responseDto = responseAssembler.toDto(SOME_AUTHENTICATION_TOKEN);

    assertEquals(SECRET_TOKEN, responseDto.token);
  }
}
