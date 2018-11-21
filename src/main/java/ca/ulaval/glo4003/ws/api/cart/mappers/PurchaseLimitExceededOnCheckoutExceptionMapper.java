package ca.ulaval.glo4003.ws.api.cart.mappers;

import ca.ulaval.glo4003.service.cart.exceptions.PurchaseLimitExceededOnCheckoutException;
import ca.ulaval.glo4003.ws.api.ErrorMapper;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@ErrorMapper
public class PurchaseLimitExceededOnCheckoutExceptionMapper
    implements ExceptionMapper<PurchaseLimitExceededOnCheckoutException> {

  @Override
  public Response toResponse(PurchaseLimitExceededOnCheckoutException e) {
    return Response.status(Response.Status.FORBIDDEN).build();
  }
}
