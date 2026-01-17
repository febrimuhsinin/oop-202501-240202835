package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.Product;
import main.java.com.upb.agripos.view.ConsoleView;
import main.java.com.upb.agripos.controller.ProductController;
import main.java.com.upb.agripos.config.DatabaseConnection;

public class AppMVC {
    public static void main(String[] args) {
        System.out.println("Hello, I am Febri Muhsinin-240202835 (Week10)");
        System.out.println("============================================");

        // Uji Singleton
        DatabaseConnection db = DatabaseConnection.getInstance();
        db.executeQuery("SELECT * FROM produk");

        // Uji MVC
        Product model = new Product("BNH-01", "Benih Jagung Hibrida");
        ConsoleView view = new ConsoleView();
        ProductController controller = new ProductController(model, view);
        
        controller.showProduct();
    }
}