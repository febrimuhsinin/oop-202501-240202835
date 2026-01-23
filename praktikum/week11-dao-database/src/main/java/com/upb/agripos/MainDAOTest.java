package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;
import com.upb.agripos.dao.*;
import com.upb.agripos.model.Product;
import java.util.List;

public class MainDAOTest {
    public static void main(String[] args) {
        System.out.println("Agri-POS - Integrasi Database (Week 11)");
        System.out.println("Nama: Febri Muhsinin | NIM: 240202835");
        System.out.println("----------------------------------------");

        String url = "jdbc:postgresql://localhost:5432/agripos";
        String user = "postgres";
        String pass = "1234"; // Ganti dengan password database Anda

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            ProductDAO dao = new ProductDAOImpl(conn);

            // 1. CREATE
            System.out.println("[CREATE] Menambah Benih Padi...");
            dao.insert(new Product("BNH-01", "Benih Padi Ciherang", 45000, 50));

            // 2. READ (Single)
            Product p = dao.findByCode("BNH-01");
            System.out.println("[READ] Data ditemukan: " + p.getName());

            // 3. UPDATE
            System.out.println("[UPDATE] Mengubah harga...");
            p.setPrice(48000);
            dao.update(p);

            // 4. READ (All)
            List<Product> products = dao.findAll();
            System.out.println("[READ ALL] Total produk: " + products.size());

            // 5. DELETE
            System.out.println("[DELETE] Menghapus data percobaan...");
            dao.delete("BNH-01");

            System.out.println("\nOperasi CRUD Berhasil Diselesaikan.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}