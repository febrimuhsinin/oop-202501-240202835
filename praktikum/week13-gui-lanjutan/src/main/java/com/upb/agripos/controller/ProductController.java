package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductController {
    private final ProductService service = ProductService.getInstance();

    public void addProduct(String code, String name, double price, int stock) throws Exception {
        Product p = new Product(code, name, price, stock);
        service.insert(p);
    }

    public void deleteProduct(String code) throws Exception {
        service.delete(code); // Logika hapus
    }

    public ObservableList<Product> loadProducts() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        try {
            list.addAll(service.findAll()); // Ambil dari DB via Service
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}