package ca.ulaval.glo4003.ws.domain.user;

public interface UserRepository {
  void save(User user);

  User find(String username);
}
