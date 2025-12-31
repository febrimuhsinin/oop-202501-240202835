package com.upb.agripos;

public class MainCart {
    public static void main(String[] args) {
        // Menggunakan identitas Febri Muhsinin - 240202835
        System.out.println("Hello, I am Febri Muhsinin-240202835 (Week7)");

        Product p1 = new Product("P01", "Beras Rojolele", 50000);
        Product p2 = new Product("P02", "Pupuk NPK 1kg", 30000);

        // Uji coba ArrayList
        System.out.println("\n[Testing ArrayList Implementation]");
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(p1);
        cart.addProduct(p2);
        cart.printCart();
        cart.removeProduct(p1);
        cart.printCart();

        // Uji coba Map (Quantity)
        System.out.println("\n[Testing Map Implementation]");
        ShoppingCartMap mapCart = new ShoppingCartMap();
        mapCart.addProduct(p1);
        mapCart.addProduct(p1); // Tambah item yang sama
        mapCart.addProduct(p2);
        mapCart.printCart();
    }
}