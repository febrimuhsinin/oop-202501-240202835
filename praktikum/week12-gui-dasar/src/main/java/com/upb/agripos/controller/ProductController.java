package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;

public class ProductController {
    // Menggunakan Singleton ProductService sesuai desain Bab 10 & 12
    private final ProductService service = ProductService.getInstance();

    /**
     * Metode untuk memproses penambahan produk dari GUI
     * Merealisasikan alur UC-01 dan AD-01 dari Bab 6
     */
    public void add(String code, String name, String priceStr, String stockStr) throws Exception {
        // 1. Validasi Input Dasar (Contoh implementasi Activity Diagram)
        if (code.isEmpty() || name.isEmpty()) {
            throw new Exception("Kode dan Nama Produk tidak boleh kosong!");
        }

        try {
            // 2. Konversi tipe data dari String ke numerik
            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);

            // 3. Instansiasi Model Product
            Product product = new Product(code, name, price, stock);

            // 4. Kirim ke Service (DIP: View tidak memanggil DAO langsung)
            service.insert(product);
            
        } catch (NumberFormatException e) {
            throw new Exception("Harga dan Stok harus berupa angka yang valid!");
        }
    }
}