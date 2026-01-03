package main.java.com.upb.agripos;

public class MainExceptionDemo {
    public static void main(String[] args) {
        // Identitas Mahasiswa
        System.out.println("Hello, I am Febri Muhsinin-240202835 (Week9)");
        System.out.println("============================================");

        ShoppingCart cart = new ShoppingCart();
        Product p1 = new Product("P01", "Pupuk Organik", 25000, 3); // Stok hanya 3

        // 1. Uji InvalidQuantityException
        System.out.println("\n[Test 1: Invalid Quantity]");
        try {
            cart.addProduct(p1, -1);
        } catch (InvalidQuantityException e) {
            System.err.println("Kesalahan: " + e.getMessage());
        }

        // 2. Uji ProductNotFoundException
        System.out.println("\n[Test 2: Product Not Found]");
        try {
            cart.removeProduct(p1);
        } catch (ProductNotFoundException e) {
            System.err.println("Kesalahan: " + e.getMessage());
        }

        // 3. Uji InsufficientStockException
        System.out.println("\n[Test 3: Insufficient Stock]");
        try {
            System.out.println("Mencoba membeli 5 " + p1.getName() + "...");
            cart.addProduct(p1, 5); 
            cart.checkout();
        } catch (Exception e) {
            System.err.println("Kesalahan: " + e.getMessage());
        } finally {
            System.out.println("Proses validasi stok selesai.");
        }
    }
}