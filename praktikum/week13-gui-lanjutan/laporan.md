# Laporan Praktikum Minggu 13
Topik: GUI Lanjutan JavaFX (TableView dan Lambda Expression)

## Identitas
- Nama  : Febri Muhsinin
- NIM   : 240202835
- Kelas : 3IKRA

---

## Tujuan
1. Mengganti tampilan daftar produk dari ListView sederhana menjadi TableView yang lebih terstruktur (baris dan kolom).

2. Mengimplementasikan Lambda Expression untuk menyederhanakan penulisan event handler tombol.

3. Mengintegrasikan koleksi objek (ObservableList) dengan komponen GUI.

4. Menambahkan fitur Hapus Produk yang terhubung langsung ke database PostgreSQL melalui lapisan Service dan DAO.
---

## Dasar Teori
1. TableView: Komponen JavaFX yang dirancang untuk memvisualisasikan data dalam format tabel tak terbatas. Berbeda dengan ListView yang hanya menampilkan string tunggal, TableView dapat menampilkan berbagai properti objek dalam kolom terpisah.

2. ObservableList: Koleksi khusus di JavaFX yang memungkinkan antarmuka grafis (UI) memperbarui diri secara otomatis ketika terjadi perubahan data (penambahan atau penghapusan item).

3. Lambda Expression: Fitur Java (sejak versi 8) yang memungkinkan penulisan fungsi anonim secara ringkas. Dalam JavaFX, ini sangat berguna untuk meringkas penulisan kode aksi tombol (setOnAction).

---

## Langkah Praktikum
1. Update Backend: Menambahkan metode delete() pada interface ProductDAO, implementasi ProductDAOImpl, dan ProductService untuk menangani penghapusan data dari database.

2. Update Controller: Menambahkan metode loadProducts() yang mengembalikan ObservableList agar kompatibel dengan TableView.

3. Pembuatan View Baru: Membuat kelas ProductTableView.java untuk menggantikan ProductFormView.java.

4. Konfigurasi Kolom: Mengatur TableColumn agar sesuai dengan atribut kelas Product (Kode, Nama, Harga, Stok).

5. Implementasi Event Handling: Menambahkan logika pada tombol "Hapus" untuk mengambil item yang dipilih di tabel dan menghapusnya dari database.

---

## Kode Program 
1. Update ProductDAO.java
```java
package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.util.List;

public interface ProductDAO {
    void insert(Product product) throws Exception;
    List<Product> findAll() throws Exception;
    void delete(String code) throws Exception; // Metode baru untuk Week 13
}
```
2. Update ProductDAOImpl.java
```java
package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    private final Connection connection;

    public ProductDAOImpl(Connection connection) {
        this.connection = connection;
    }

    // ... method insert() tetap sama seperti Week 11/12 ...

    @Override
    public List<Product> findAll() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY code ASC"; // Urutkan biar rapi
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Product(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                ));
            }
        }
        return list;
    }

    @Override
    public void delete(String code) throws Exception { // Implementasi Hapus
        String sql = "DELETE FROM products WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.executeUpdate();
        }
    }
    
    // ... method insert tetap ada di sini ...
    @Override
    public void insert(Product p) throws Exception {
        String sql = "INSERT INTO products(code, name, price, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getCode());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.executeUpdate();
        }
    }
}
```
3. Update ProductService.java
```java
package com.upb.agripos.service;

import com.upb.agripos.dao.*;
import com.upb.agripos.model.Product;
// Hapus import DatabaseConnection karena file tidak ada
// import com.upb.agripos.config.DatabaseConnection; 

import java.sql.DriverManager; // Tambahkan ini untuk koneksi manual
import java.sql.Connection;
import java.util.List;

public class ProductService {
    private static ProductService instance;
    private ProductDAO dao; // Hapus 'final' agar bisa diinisialisasi di try-catch

    private ProductService() {
        try {
            // PENGGANTI: Kita buat koneksi langsung di sini (Manual Connection)
            // Pastikan password sesuai dengan pgAdmin Anda
            String url = "jdbc:postgresql://localhost:5432/agripos";
            String user = "postgres";
            String password = "admin"; // Ganti dengan password pgAdmin Anda (misal: 1234)

            Connection conn = DriverManager.getConnection(url, user, password);
            
            // Masukkan koneksi tersebut ke DAO
            this.dao = new ProductDAOImpl(conn);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Gagal koneksi database: " + e.getMessage());
        }
    }

    public static ProductService getInstance() {
        if (instance == null) instance = new ProductService();
        return instance;
    }

    public void insert(Product p) throws Exception {
        dao.insert(p);
    }

    public List<Product> findAll() throws Exception {
        return dao.findAll();
    }

    public void delete(String code) throws Exception {
        dao.delete(code); 
    }
}
```
4. Update ProductController.java
```java
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
```
5. Update ProductTableView.java
```java
package com.upb.agripos.view;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.model.Product;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductTableView extends VBox {
    private final ProductController controller = new ProductController();
    
    // Komponen GUI
    private TableView<Product> table = new TableView<>();
    private TextField txtCode = new TextField();
    private TextField txtName = new TextField();
    private TextField txtPrice = new TextField();
    private TextField txtStock = new TextField();

    public ProductTableView() {
        this.setPadding(new Insets(20));
        this.setSpacing(10);

        // 1. Setup Form Input
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10); formGrid.setVgap(10);
        
        formGrid.add(new Label("Kode:"), 0, 0); formGrid.add(txtCode, 1, 0);
        formGrid.add(new Label("Nama:"), 0, 1); formGrid.add(txtName, 1, 1);
        formGrid.add(new Label("Harga:"), 0, 2); formGrid.add(txtPrice, 1, 2);
        formGrid.add(new Label("Stok:"), 0, 3); formGrid.add(txtStock, 1, 3);

        Button btnAdd = new Button("Tambah Produk");
        Button btnDelete = new Button("Hapus Produk");
        
        HBox buttonBox = new HBox(10, btnAdd, btnDelete);
        formGrid.add(buttonBox, 1, 4);

        // 2. Setup TableView
        setupTable();
        loadData(); // Load data awal dari DB

        // 3. Event Handling dengan Lambda Expression
        
        // Aksi Tambah
        btnAdd.setOnAction(e -> {
            try {
                controller.addProduct(
                    txtCode.getText(), txtName.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtStock.getText())
                );
                loadData(); // Reload tabel setelah insert
                clearFields();
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        // Aksi Hapus (Lambda)
        btnDelete.setOnAction(e -> {
            Product selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    controller.deleteProduct(selected.getCode());
                    loadData(); // Reload tabel setelah delete
                } catch (Exception ex) {
                    showAlert("Error", "Gagal hapus: " + ex.getMessage());
                }
            } else {
                showAlert("Peringatan", "Pilih produk di tabel untuk dihapus!");
            }
        });

        this.getChildren().addAll(new Label("Manajemen Produk Agri-POS (Week 13)"), formGrid, table);
    }

    @SuppressWarnings("unchecked")
    private void setupTable() {
        // Kolom Kode
        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));

        // Kolom Nama
        TableColumn<Product, String> colName = new TableColumn<>("Nama Produk");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setMinWidth(200);

        // Kolom Harga
        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Kolom Stok
        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        table.getColumns().addAll(colCode, colName, colPrice, colStock);
    }

    private void loadData() {
        // Mengintegrasikan Koleksi Objek dengan GUI
        table.setItems(controller.loadProducts());
    }

    private void clearFields() {
        txtCode.clear(); txtName.clear(); txtPrice.clear(); txtStock.clear();
    }

    private void showAlert(String title, String message) {
        new Alert(Alert.AlertType.INFORMATION, message).show();
    }
}
```
6. Update AppJavaFX.java
```java
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
```
---

## Hasil Eksekusi
![Screenshot hasil](/praktikum/week13-gui-lanjutan/screenshots/week%2013.png)

---

## Analisis 

Implementasi TableView dan fitur Hapus ini merealisasikan rancangan Bab 6 sebagai berikut:

| Artefak Bab 6 | Referensi | Handler GUI (Lambda) | Controller/Service | DAO | Dampak UI/DB |
| --- | --- | --- | --- | --- | --- |
| **Use Case** | UC-02 Lihat Daftar Produk | `loadData()` saat inisialisasi View | `controller.loadProducts()` → `service.findAll()` | `dao.findAll()` | Data dari PostgreSQL tampil terstruktur di `TableView`. |
| **Use Case** | UC-03 Hapus Produk | Tombol Hapus (`btnDelete`) | `controller.deleteProduct()` → `service.delete(code)` | `dao.delete(code)` | Baris data dihapus dari tabel PostgreSQL dan UI me-reload daftar. |
| **Sequence** | SD-02 Hapus Produk | Aksi Klik Tombol Hapus | View memanggil Controller, Controller memanggil Service | DAO mengeksekusi SQL DELETE | Urutan pemanggilan pesan sesuai dengan Sequence Diagram Bab 6. |
| **Class** | Hubungan View-Model | `TableView<Product>` | Menggunakan `ObservableList<Product>` | Mengembalikan `List<Product>` | View tidak memanipulasi SQL, hanya menampilkan objek Model. |

## Kesimpulan

Praktikum Minggu 13 berhasil meningkatkan kualitas antarmuka Agri-POS dari sekadar list sederhana menjadi tabel informatif.

- Interaktivitas: Pengguna kini dapat memilih baris data spesifik untuk melakukan aksi (seperti hapus).

- Efisiensi Kode: Penggunaan Lambda Expression terbukti mengurangi jumlah baris kode (boilerplate) pada event handler secara signifikan dibandingkan menggunakan Anonymous Inner Class.

- Konsistensi Data: Dengan arsitektur MVC yang diterapkan, perubahan data di GUI (Front-end) terjamin sinkronisasinya dengan Database (Back-end) melalui metode loadData() yang memanggil ulang data terbaru setelah setiap transaksi.

---
