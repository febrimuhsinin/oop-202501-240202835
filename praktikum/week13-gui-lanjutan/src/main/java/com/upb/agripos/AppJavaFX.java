package com.upb.agripos;

import com.upb.agripos.view.ProductTableView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Menggunakan View Week 13 (TableView)
        ProductTableView root = new ProductTableView();
        
        Scene scene = new Scene(root, 600, 600); // Ukuran window diperbesar untuk tabel
        
        primaryStage.setTitle("Agri-POS - Week 13 (TableView & Lambda)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}