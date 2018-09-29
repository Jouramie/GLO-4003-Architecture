package ca.ulaval.glo4003.ws.application.user.authentication;

import ca.ulaval.glo4003.ws.api.authentication.AuthenticationRequestDto;
import ca.ulaval.glo4003.ws.api.authentication.AuthenticationResponseDto;
import ca.ulaval.glo4003.ws.api.authentication.AuthenticationTokenDto;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import ca.ulaval.glo4003.ws.domain.user.authentication.AuthenticationToken;
import ca.ulaval.glo4003.ws.domain.user.authentication.AuthenticationTokenFactory;
import ca.ulaval.glo4003.ws.domain.user.authentication.AuthenticationTokenRepository;
import ca.ulaval.glo4003.ws.infrastructure.injection.Component;
import javax.inject.Inject;

@Component
public class AuthenticationService {

  private final UserRepository userRepository;
  private final AuthenticationTokenAssembler authenticationTokenAssembler;
  private final AuthenticationTokenFactory tokenFactory;
  private final AuthenticationTokenRepository authenticationTokenRepository;
  private final AuthenticationResponseAssembler responseAssembler;

  @Inject
  public AuthenticationService(UserRepository userRepository,
                               AuthenticationTokenAssembler authenticationTokenAssembler,
                               AuthenticationTokenFactory tokenFactory,
                               AuthenticationTokenRepository authenticationTokenRepository,
                               AuthenticationResponseAssembler responseAssembler) {
    this.userRepository = userRepository;
    this.authenticationTokenAssembler = authenticationTokenAssembler;
    this.tokenFactory = tokenFactory;
    this.authenticationTokenRepository = authenticationTokenRepository;
    this.responseAssembler = responseAssembler;
  }

  public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequest) {
    User user = userRepository.find(authenticationRequest.username);
    AuthenticationToken token =
        user.authenticateByPassword(authenticationRequest.password, tokenFactory);
    return responseAssembler.toDto(token);
  }

  public void validateAuthentication(AuthenticationTokenDto authenticationTokenDto) {
    AuthenticationToken authenticationToken =
        authenticationTokenRepository.getTokenForUser(authenticationTokenDto.username);
    AuthenticationToken requestToken = authenticationTokenAssembler.toModel(authenticationTokenDto);
    if (!authenticationToken.equals(requestToken)) {
      throw new InvalidTokenException();
    }
  }
}
