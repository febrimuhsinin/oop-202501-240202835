package com.upb.agripos.service;

import com.upb.agripos.dao.*;
import com.upb.agripos.model.Product;
import java.sql.DriverManager;

public class ProductService {
    private static ProductService instance;
    private ProductDAO dao;

    private ProductService() {
        try {
            // Pastikan database agripos sudah dibuat di PostgreSQL
            var conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/agripos", "postgres", "1234");
            this.dao = new ProductDAOImpl(conn);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static ProductService getInstance() {
        if (instance == null) instance = new ProductService();
        return instance;
    }

    public void insert(Product p) throws Exception {
        dao.insert(p);
    }
}