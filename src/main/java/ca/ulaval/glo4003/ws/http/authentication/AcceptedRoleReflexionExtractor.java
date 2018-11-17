package ca.ulaval.glo4003.ws.http.authentication;

import ca.ulaval.glo4003.domain.Component;
import ca.ulaval.glo4003.domain.user.UserRole;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.container.ResourceInfo;

@Component
public class AcceptedRoleReflexionExtractor {

  public List<UserRole> extractAcceptedRoles(ResourceInfo resourceInfo) {
    AuthenticationRequiredBinding binding;

    Class<?> clazz = resourceInfo.getResourceClass();
    if (clazz == null) {
      throw new RuntimeException("Could not find annotated class.");
    }

    binding = clazz.getAnnotation(AuthenticationRequiredBinding.class);
    if (binding != null) {
      return Arrays.asList(binding.acceptedRoles());
    }

    Method method = resourceInfo.getResourceMethod();
    if (method == null) {
      throw new RuntimeException("Could not find annotated method.");
    }

    binding = method.getAnnotation(AuthenticationRequiredBinding.class);
    if (binding != null) {
      return Arrays.asList(binding.acceptedRoles());
    }

    Class<?>[] interfaces = clazz.getInterfaces();
    for (Class<?> interfaceClass : interfaces) {
      binding = interfaceClass.getAnnotation(AuthenticationRequiredBinding.class);
      if (binding != null) {
        return Arrays.asList(binding.acceptedRoles());
      }

      try {
        Method interfaceMethod = interfaceClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
        binding = interfaceMethod.getAnnotation(AuthenticationRequiredBinding.class);
        if (binding != null) {
          return Arrays.asList(binding.acceptedRoles());
        }
      } catch (NoSuchMethodException e) {
        // Interface does not contain the searched method, skipped.
      }
    }

    throw new RuntimeException("AcceptedRoles could not be extracted...");
  }
}
