package ca.ulaval.glo4003.ws.api.cart;

import java.util.List;

public class CartResourceImpl implements CartResource {
  @Override
  public List<CartStockResponse> getCartContent() {
    return null;
  }

  @Override
  public List<CartStockResponse> addStockToCart(String title,
                                                CartStockRequest cartStockRequest) {
    return null;
  }

  @Override
  public List<CartStockResponse> updateStockInCart(String title,
                                                   CartStockRequest cartStockRequest) {
    return null;
  }

  @Override
  public List<CartStockResponse> deleteStockInCart(String title,
                                                   CartStockRequest cartStockRequest) {
    return null;
  }

  @Override
  public void emptyCard() {
  }

  @Override
  public List<CartStockResponse> checkoutCart() {
    return null;
  }
}
