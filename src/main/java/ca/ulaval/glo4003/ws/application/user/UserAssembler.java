package ca.ulaval.glo4003.ws.application.user;

import ca.ulaval.glo4003.ws.api.authentication.UserDto;
import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.infrastructure.injection.Component;

@Component
class UserAssembler {

  UserDto toDto(User user) {
    return new UserDto(user.getUsername(), user.getRole());
  }
}
