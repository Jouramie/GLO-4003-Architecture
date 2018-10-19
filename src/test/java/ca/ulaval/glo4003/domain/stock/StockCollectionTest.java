package ca.ulaval.glo4003.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;

public class StockCollectionTest {
  private final String INCLUDED_STOCK_TITLE = "MSFT";
  private final int INCLUDED_STOCK_QUANTITY = 3;
  private final String MISSING_STOCK_TITLE = "RBS.1";
  private final int MISSING_STOCK_QUANTITY = 7;

  private StockCollection stockCollection;

  @Before
  public void setupStockCollection() {
    stockCollection = new StockCollection();
    stockCollection = stockCollection.add(INCLUDED_STOCK_TITLE, INCLUDED_STOCK_QUANTITY);
  }

  @Test
  public void givenStockInCollection_whenCheckIfContains_thenReturnTrue() {
    assertThat(stockCollection.contains(INCLUDED_STOCK_TITLE)).isTrue();
  }

  @Test
  public void givenStockNotInCollection_whenCheckIfContains_thenReturnFalse() {
    assertThat(stockCollection.contains(MISSING_STOCK_TITLE)).isFalse();
  }

  @Test
  public void whenAddStock_thenStockQuantityCanBeRetrieved() {
    stockCollection = stockCollection.add(MISSING_STOCK_TITLE, MISSING_STOCK_QUANTITY);

    assertThat(stockCollection.getQuantity(MISSING_STOCK_TITLE)).isEqualTo(MISSING_STOCK_QUANTITY);
  }

  @Test
  public void givenStockAlreadyInCollection_whenAddStock_thenNumberOfStockIsIncreasedByQuantity() {
    stockCollection = stockCollection.add(INCLUDED_STOCK_TITLE, 1);

    assertThat(stockCollection.getQuantity(INCLUDED_STOCK_TITLE)).isEqualTo(INCLUDED_STOCK_QUANTITY + 1);
  }

  @Test
  public void givenStockNotInCollection_whenAddZeroStock_thenStockIsNotAddedToCollection() {
    stockCollection = stockCollection.add(MISSING_STOCK_TITLE, 0);

    assertThat(stockCollection.getStocks()).doesNotContain(MISSING_STOCK_TITLE);
  }

  @Test
  public void whenAddNegativeQuantityOfStocks_thenIllegalArgumentExceptionIsThrown() {
    assertThatThrownBy(() -> stockCollection.add(INCLUDED_STOCK_TITLE, -1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void givenStockInCollection_whenGetQuantity_thenQuantityOfTitleReturned() {
    assertThat(stockCollection.getQuantity(INCLUDED_STOCK_TITLE)).isEqualTo(INCLUDED_STOCK_QUANTITY);
  }

  @Test
  public void givenStockNotInCollection_whenGetQuantity_thenQuantityIsZero() {
    assertThat(stockCollection.getQuantity(MISSING_STOCK_TITLE)).isEqualTo(0);
  }

  @Test
  public void givenStockInCollection_whenUpdate_thenQuantityIsChanged() {
    int someQuantity = 25;
    stockCollection = stockCollection.update(INCLUDED_STOCK_TITLE, someQuantity);

    assertThat(stockCollection.getQuantity(INCLUDED_STOCK_TITLE)).isEqualTo(someQuantity);
  }

  @Test
  public void givenStockInCollection_whenUpdateWithQuantityOfZero_thenStockIsRemovedFromCollection() {
    stockCollection = stockCollection.update(INCLUDED_STOCK_TITLE, 0);

    assertThat(stockCollection.getStocks()).doesNotContain(INCLUDED_STOCK_TITLE);
  }

  @Test
  public void givenStockNotInCollection_whenUpdate_thenIllegalArgumentExceptionIsThrown() {
    assertThatThrownBy(() -> stockCollection.update(MISSING_STOCK_TITLE, 3))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void whenUpdateNegativeQuantityOfStocks_thenIllegalArgumentExceptionIsThrown() {
    assertThatThrownBy(() -> stockCollection.update(INCLUDED_STOCK_TITLE, -1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void givenStocksInCollection_whenGetStocks_thenReturnAllStocks() {
    stockCollection = stockCollection.add(MISSING_STOCK_TITLE, MISSING_STOCK_QUANTITY);

    assertThat(stockCollection.getStocks()).contains(INCLUDED_STOCK_TITLE, MISSING_STOCK_TITLE);
  }

  @Test
  public void givenStockInCollection_whenRemoveStock_thenStockQuantityIsDecreased() {
    stockCollection = stockCollection.remove(INCLUDED_STOCK_TITLE, 1);

    assertThat(stockCollection.getQuantity(INCLUDED_STOCK_TITLE)).isEqualTo(INCLUDED_STOCK_QUANTITY - 1);
  }

  @Test
  public void givenStockInCollection_whenRemoveRemainingStocks_thenStockIsRemovedFromCollection() {
    stockCollection = stockCollection.remove(INCLUDED_STOCK_TITLE, INCLUDED_STOCK_QUANTITY);

    assertThat(stockCollection.getStocks()).doesNotContain(INCLUDED_STOCK_TITLE);
  }

  @Test
  public void whenRemoveNegativeQuantityOfStocks_thenIllegalArgumentExceptionIsThrown() {
    assertThatThrownBy(() -> stockCollection.remove(INCLUDED_STOCK_TITLE, -1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void givenStockInCollection_whenRemoveMoreThanRemaining_thenRuntimeExceptionIsThrown() {
    assertThatThrownBy(() -> stockCollection.remove(INCLUDED_STOCK_TITLE, INCLUDED_STOCK_QUANTITY + 1))
        .isInstanceOf(RuntimeException.class);
  }

  @Test
  public void givenStockNotInCollection_whenRemoveStock_thenIllegalArgumentExceptionIsThrown() {
    assertThatThrownBy(() -> stockCollection.remove(MISSING_STOCK_TITLE, 1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void givenStockInCollection_whenRemoveAll_thenQuantityIsZero() {
    stockCollection = stockCollection.removeAll(INCLUDED_STOCK_TITLE);

    assertThat(stockCollection.getQuantity(INCLUDED_STOCK_TITLE)).isEqualTo(0);
  }

  @Test
  public void givenCollectionIsEmpty_whenCheckIfEmpty_thenReturnTrue() {
    stockCollection = stockCollection.removeAll(INCLUDED_STOCK_TITLE);

    assertThat(stockCollection.isEmpty()).isTrue();
  }

  @Test
  public void givenCollectionIsNotEmpty_whenCheckIfEmpty_thenReturnFalse() {
    assertThat(stockCollection.isEmpty()).isFalse();
  }

  @Test
  public void givenCollectionIsNotEmpty_whenEmpty_thenCollectionIsEmpty() {
    stockCollection = stockCollection.empty();

    assertThat(stockCollection.isEmpty()).isTrue();
  }
}
