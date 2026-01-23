package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.util.List;

public interface ProductDAO {
    void insert(Product product) throws Exception;
    List<Product> findAll() throws Exception;
    void delete(String code) throws Exception; // Metode baru untuk Week 13
}