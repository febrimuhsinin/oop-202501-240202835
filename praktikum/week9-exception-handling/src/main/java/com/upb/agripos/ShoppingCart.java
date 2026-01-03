package main.java.com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product p, int qty) throws InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException("Gagal Tambah: Quantity (" + qty + ") harus lebih dari 0.");
        }
        items.put(p, items.getOrDefault(p, 0) + qty);
    }

    public void removeProduct(Product p) throws ProductNotFoundException {
        if (!items.containsKey(p)) {
            throw new ProductNotFoundException("Gagal Hapus: Produk '" + p.getName() + "' tidak ditemukan dalam keranjang.");
        }
        items.remove(p);
    }

    public void checkout() throws InsufficientStockException {
        // Validasi stok untuk semua item di keranjang
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int qty = entry.getValue();
            if (product.getStock() < qty) {
                throw new InsufficientStockException(
                    "Gagal Checkout: Stok '" + product.getName() + "' tidak mencukupi. (Tersedia: " + 
                    product.getStock() + ", Diminta: " + qty + ")"
                );
            }
        }
        
        // Jika semua stok aman, lakukan pengurangan stok
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().reduceStock(entry.getValue());
        }
        System.out.println("Checkout Berhasil! Stok gudang telah diperbarui.");
    }
}
