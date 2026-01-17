package main.java.com.upb.agripos.config;

public class DatabaseConnection {
    private static DatabaseConnection instance;

    // Constructor private agar tidak bisa di-instansiasi dari luar
    private DatabaseConnection() {
        System.out.println("Koneksi Database Agri-POS Berhasil Dibuat.");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void executeQuery(String query) {
        System.out.println("Menjalankan query: " + query);
    }
}