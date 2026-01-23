package com.upb.agripos;

import com.upb.agripos.view.ProductFormView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Menampilkan identitas mahasiswa di konsol saat aplikasi berjalan
        System.out.println("Aplikasi Agri-POS Berjalan...");
        System.out.println("Nama : Febri Muhsinin");
        System.out.println("NIM  : 240202835 (Week 12)");

        // 1. Inisialisasi Root View yang telah dibuat
        ProductFormView root = new ProductFormView();

        // 2. Membuat Scene dengan ukuran tertentu
        Scene scene = new Scene(root, 450, 550);

        // 3. Mengatur Judul Window sesuai desain Bab 6
        primaryStage.setTitle("Agri-POS - Manajemen Produk Pertanian");
        
        // 4. Menampilkan Stage utama
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Metode main standar untuk menjalankan aplikasi JavaFX
     */
    public static void main(String[] args) {
        launch(args);
    }
}