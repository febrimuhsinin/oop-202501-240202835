package com.upb.agripos;

import com.upb.agripos.view.PosView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    @Override
    public void start(Stage stage) {
        System.out.println("Hello World, I am Febri Muhsinin-[NIM]"); // Ganti NIM
        
        PosView root = new PosView();
        Scene scene = new Scene(root, 800, 600);
        
        stage.setTitle("Agri-POS Integrated System (Week 14)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
