package com.upb.agripos.service;

import com.upb.agripos.model.Cart;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import java.util.List;

public class CartService {
    private Cart cart = new Cart();

    public void addToCart(Product product, int quantity) {
        if (quantity <= 0) return;
        CartItem item = new CartItem(product, quantity);
        cart.addItem(item);
    }

    public List<CartItem> getCartItems() {
        return cart.getItems();
    }

    public double calculateTotal() {
        return cart.getItems().stream().mapToDouble(CartItem::getSubtotal).sum();
    }

    public void clearCart() {
        cart.clear();
    }
}