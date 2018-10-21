package ca.ulaval.glo4003.ws.api.cart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(
    name = "Cart stock",
    description = "Cart stock request containing the quantity of a stock."
)
public class CartStockRequest {
  @NotNull
  @Min(value = 0)
  public final Integer quantity;

  @JsonCreator
  public CartStockRequest(@JsonProperty("quantity") Integer quantity) {
    this.quantity = quantity;
  }
}
