package ca.ulaval.glo4003.ws.application.user;

import ca.ulaval.glo4003.ws.api.authentication.UserCreationDto;
import ca.ulaval.glo4003.ws.api.authentication.UserDto;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserFactory;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import ca.ulaval.glo4003.ws.infrastructure.injection.Component;
import javax.inject.Inject;

@Component
public class UserCreationService {

  private final UserFactory userFactory;
  private final UserRepository userRepository;
  private final UserAssembler userAssembler;

  @Inject
  public UserCreationService(UserFactory userFactory, UserRepository userRepository, UserAssembler userAssembler) {
    this.userFactory = userFactory;
    this.userRepository = userRepository;
    this.userAssembler = userAssembler;
  }

  public UserDto createUser(UserCreationDto creationRequest) {
    User user = userFactory.create(creationRequest.username, creationRequest.password, creationRequest.role);
    userRepository.save(user);
    return userAssembler.toDto(user);
  }
}
