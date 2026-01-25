package com.upb.agripos;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {
    @Test
    void testCalculateTotal() {
        CartService cart = new CartService();
        Product p1 = new Product("A", "Apel", 10000, 10);
        
        cart.addToCart(p1, 2); // 20.000
        
        assertEquals(20000.0, cart.calculateTotal(), "Total belanja harus sesuai");
    }
}