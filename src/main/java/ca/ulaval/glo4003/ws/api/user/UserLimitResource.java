package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.ApiUserLimitDto;
import ca.ulaval.glo4003.ws.api.user.dto.UserMoneyAmountLimitCreationDto;
import ca.ulaval.glo4003.ws.api.user.dto.UserStockLimitCreationDto;
import ca.ulaval.glo4003.ws.http.AuthenticationRequiredBinding;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{email}/limit")
@AuthenticationRequiredBinding
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UserLimitResource {

  @PUT
  @Path("/stock")
  @Operation(
      summary = "Set a stock per transaction limit to a user.",
      responses = {
          @ApiResponse(
              responseCode = "202",
              content = @Content(
                  schema = @Schema(
                      implementation = ApiUserLimitDto.class
                  )
              )
          ),
          @ApiResponse(
              responseCode = "401",
              description = "User is not logged in or not administrator."
          ),
          @ApiResponse(
              responseCode = "404",
              description = "User does not exist."
          )
      }
  )
  public ApiUserLimitDto setUserStockLimit(
      @PathParam("email") String email,
      @Valid UserStockLimitCreationDto userStockLimitCreationDto);

  @PUT
  @Path("/money_amount")
  @Operation(
      summary = "Set a money amount per transaction limit to a user.",
      responses = {
          @ApiResponse(
              responseCode = "202",
              content = @Content(
                  schema = @Schema(
                      implementation = ApiUserLimitDto.class
                  )
              )
          ),
          @ApiResponse(
              responseCode = "401",
              description = "User is not logged in or not administrator."
          ),
          @ApiResponse(
              responseCode = "404",
              description = "User does not exist."
          )
      }
  )
  public ApiUserLimitDto setUserMoneyAmountLimit(
      @PathParam("email") String email,
      @Valid UserMoneyAmountLimitCreationDto userMoneyAmountLimitCreationDto);

  @DELETE
  @Operation(
      summary = "Remove a limit from a user.",
      responses = {
          @ApiResponse(
              responseCode = "204"
          ),
          @ApiResponse(
              responseCode = "401",
              description = "User is not logged in or not administrator."
          ),
          @ApiResponse(
              responseCode = "404",
              description = "User does not exist."
          )
      }
  )
  public Response removeUserLimit(@PathParam("email") String email);
}
