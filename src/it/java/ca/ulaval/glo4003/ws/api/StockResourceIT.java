package ca.ulaval.glo4003.ws.api;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;

import ca.ulaval.glo4003.ResetServerBetweenTest;
import org.junit.Rule;
import org.junit.Test;

public class StockResourceIT {
  private static final String API_STOCK_ROUTE = "/api/stocks";

  private static final String TITLE = "title";
  private static final String NAME = "name";
  private static final String MARKET = "market";
  private static final String CATEGORY = "category";
  private static final String OPEN = "open";
  private static final String CURRENT = "current";
  private static final String CLOSE = "close";

  private static final String SOME_TITLE = "RBS.l";
  private static final String SOME_NAME = "Royal Bank of Scotland";
  private static final String SOME_MARKET = "London";
  private static final String SOME_CATEGORY = "Banking";

  @Rule
  public ResetServerBetweenTest resetServerBetweenTest = new ResetServerBetweenTest();

  @Test
  public void whenGettingByTitle_thenReturnStockInformation() {
    //@formatter:off
    when()
        .get(API_STOCK_ROUTE + "/" + SOME_TITLE)
    .then()
        .statusCode(200)
        .body(TITLE, equalTo(SOME_TITLE))
        .body(NAME, equalTo(SOME_NAME))
        .body(MARKET, equalTo(SOME_MARKET))
        .body(CATEGORY, equalTo(SOME_CATEGORY))
        .body(OPEN, any(Float.class))
        .body(CURRENT, any(Float.class))
        .body(CLOSE, any(Float.class));
    //@formatter:on
  }

  @Test
  public void whenGettingByName_thenReturnStockInformation() {
    //@formatter:off
    given()
        .param(NAME, SOME_NAME)
    .when()
        .get(API_STOCK_ROUTE)
    .then()
        .statusCode(200)
        .body(TITLE, equalTo(SOME_TITLE))
        .body(NAME, equalTo(SOME_NAME))
        .body(MARKET, equalTo(SOME_MARKET))
        .body(CATEGORY, equalTo(SOME_CATEGORY))
        .body(OPEN, any(Float.class))
        .body(CURRENT, any(Float.class))
        .body(CLOSE, any(Float.class));
    //@formatter:on
  }

  @Test
  public void givenWrongValue_whenGettingByTitle_thenStockIsNotFound() {
    //@formatter:off
    when()
        .get(API_STOCK_ROUTE + "/wrong")
    .then()
        .statusCode(404);
    //@formatter:on
  }

  @Test
  public void givenWrongValue_whenGettingByName_thenStockIsNotFound() {
    //@formatter:off
    given()
        .param(NAME, "wrong")
    .when()
        .get(API_STOCK_ROUTE)
    .then()
        .statusCode(404);
    //@formatter:on
  }

  @Test
  public void givenNoValue_whenGettingByName_thenBadRequest() {
    //@formatter:off
    when()
        .get(API_STOCK_ROUTE)
    .then()
        .statusCode(400);
    //@formatter:on
  }
}
