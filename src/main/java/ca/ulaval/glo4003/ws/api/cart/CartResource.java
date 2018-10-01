package ca.ulaval.glo4003.ws.api.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/cart")
public interface CartResource {
  @GET
  @Operation(
      summary = "Get the content of the cart.",
      description = "Return the every stocks in the cart, with their details and quantity.",
      responses = {
          @ApiResponse(
              responseCode = "200", description = "cart content",
              content = @Content(array = @ArraySchema(schema = @Schema(implementation = CartStockResponse.class)))
          ),
          @ApiResponse(responseCode = "401", description = "not logged in")
      }
  )
  List<CartStockResponse> getCartContent();

  @POST
  @Operation(
      summary = "Add a stock to the cart.",
      description = "Add a stock to the cart. Return the every stocks in the cart, with their details and quantity.",
      requestBody = @RequestBody(
          content = @Content(schema = @Schema(implementation = CartStockRequest.class))
      ),
      responses = {
          @ApiResponse(
              responseCode = "200", description = "cart content",
              content = @Content(array = @ArraySchema(schema = @Schema(implementation = CartStockResponse.class)))
          ),
          @ApiResponse(responseCode = "400", description = "stock does not exist or quantity is invalid"),
          @ApiResponse(responseCode = "401", description = "not logged in")
      }
  )
  List<CartStockResponse> addStockToCart(CartStockRequest cartStockRequest);

  @PATCH
  @Operation(
      summary = "Update a stock in the cart.",
      description = "Update the quantity of a stock. Return the every stocks in the cart, with their details and quantity.",
      requestBody = @RequestBody(
          content = @Content(schema = @Schema(implementation = CartStockRequest.class))
      ),
      responses = {
          @ApiResponse(
              responseCode = "200", description = "cart content",
              content = @Content(array = @ArraySchema(schema = @Schema(implementation = CartStockResponse.class)))
          ),
          @ApiResponse(responseCode = "400", description = "stock does not exist or quantity is invalid"),
          @ApiResponse(responseCode = "401", description = "not logged in")
      }
  )
  List<CartStockResponse> updateStockInCart(CartStockRequest cartStockRequest);

  @DELETE
  @Operation(
      summary = "Delete a stock in the cart.",
      description = "Remove a stock from the cart. Return the every stocks in the cart, with their details and quantity.",
      requestBody = @RequestBody(
          content = @Content(schema = @Schema(implementation = CartStockRequest.class))
      ),
      responses = {
          @ApiResponse(
              responseCode = "200", description = "cart content",
              content = @Content(array = @ArraySchema(schema = @Schema(implementation = CartStockResponse.class)))
          ),
          @ApiResponse(responseCode = "401", description = "not logged in")
      }
  )
  List<CartStockResponse> deleteStockInCart(CartStockRequest cartStockRequest);

  @DELETE
  @Operation(
      summary = "Empty the cart.",
      description = "Remove all stock from the cart.",
      responses = {
          @ApiResponse(responseCode = "204", description = "cart emptied"),
          @ApiResponse(responseCode = "401", description = "not logged in")
      }
  )
  void emptyCard();

  @POST
  @Path("/checkout")
  @Operation(
      summary = "Check out the cart.",
      description = "Check out the current content of the cart. Return the checked out stocks, with their details and quantity.",
      responses = {
          @ApiResponse(
              responseCode = "200", description = "cart content",
              content = @Content(array = @ArraySchema(schema = @Schema(implementation = CartStockResponse.class)))
          ),
          @ApiResponse(responseCode = "401", description = "not logged in")
      }
  )
  List<CartStockResponse> checkoutCart();
}
