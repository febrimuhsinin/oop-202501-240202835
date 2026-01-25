package com.upb.agripos.service;

import com.upb.agripos.dao.*;
import com.upb.agripos.model.Product;
import java.sql.DriverManager;
import java.util.List;

public class ProductService {
    private static ProductService instance;
    private ProductDAO dao;

    private ProductService() {
        try {
            // --- KONFIGURASI DATABASE ---
            // Pastikan database 'agripos' sudah dibuat di pgAdmin
            String url = "jdbc:postgresql://localhost:5432/agripos";
            String user = "postgres";
            
            // PERHATIAN: Ganti "admin" dengan password pgAdmin kamu!
            String pass = "admin"; 
            
            var conn = DriverManager.getConnection(url, user, pass);
            this.dao = new JdbcProductDAO(conn);
            
        } catch (Exception e) {
            System.err.println("GAGAL KONEKSI DATABASE: " + e.getMessage());
            e.printStackTrace();
            // Jangan biarkan dao null, inisialisasi dummy jika perlu atau biarkan crash agar ketahuan
        }
    }

    public static ProductService getInstance() {
        if (instance == null) instance = new ProductService();
        return instance;
    }

    public List<Product> getAllProducts() throws Exception {
        if (dao == null) throw new Exception("Koneksi Database Gagal! Cek Password di ProductService.java");
        return dao.findAll();
    }
    
    public void addProduct(Product p) throws Exception {
        if (dao == null) throw new Exception("Koneksi Database Gagal!");
        if (p.getPrice() < 0) throw new Exception("Harga tidak boleh negatif!");
        dao.insert(p);
    }
    
    public void deleteProduct(String code) throws Exception {
        if (dao == null) throw new Exception("Koneksi Database Gagal!");
        dao.delete(code);
    }
}