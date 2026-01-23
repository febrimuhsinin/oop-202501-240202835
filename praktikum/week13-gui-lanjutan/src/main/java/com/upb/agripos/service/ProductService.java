package com.upb.agripos.service;

import com.upb.agripos.dao.*;
import com.upb.agripos.model.Product;
// Hapus import DatabaseConnection karena file tidak ada
// import com.upb.agripos.config.DatabaseConnection; 

import java.sql.DriverManager; // Tambahkan ini untuk koneksi manual
import java.sql.Connection;
import java.util.List;

public class ProductService {
    private static ProductService instance;
    private ProductDAO dao; // Hapus 'final' agar bisa diinisialisasi di try-catch

    private ProductService() {
        try {
            // PENGGANTI: Kita buat koneksi langsung di sini (Manual Connection)
            // Pastikan password sesuai dengan pgAdmin Anda
            String url = "jdbc:postgresql://localhost:5432/agripos";
            String user = "postgres";
            String password = "admin"; // Ganti dengan password pgAdmin Anda (misal: 1234)

            Connection conn = DriverManager.getConnection(url, user, password);
            
            // Masukkan koneksi tersebut ke DAO
            this.dao = new ProductDAOImpl(conn);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Gagal koneksi database: " + e.getMessage());
        }
    }

    public static ProductService getInstance() {
        if (instance == null) instance = new ProductService();
        return instance;
    }

    public void insert(Product p) throws Exception {
        dao.insert(p);
    }

    public List<Product> findAll() throws Exception {
        return dao.findAll();
    }

    public void delete(String code) throws Exception {
        dao.delete(code); 
    }
}