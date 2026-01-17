package test.java.com.upb.agripos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.java.com.upb.agripos.model.Product;

public class ProductTest {
    @Test
    public void testProductName() {
        Product p = new Product("PPK-01", "Pupuk NPK");
        // Memastikan nama produk sesuai
        assertEquals("Pupuk NPK", p.getName());
    }

    @Test
    public void testProductCode() {
        Product p = new Product("ALT-01", "Cangkul Baja");
        assertEquals("ALT-01", p.getCode());
    }
}