# Laporan Praktikum Minggu 12
Topik: GUI Dasar Daftar Produk Agri-POS Menggunakan JavaFX

## Identitas
- Nama  : [Febri Muhsinin]
- NIM   : [240202835]
- Kelas : [3IKRA]

---

## Tujuan
1. Memahami konsep event-driven programming dalam pengembangan aplikasi desktop.

2. Membangun antarmuka grafis (GUI) sederhana menggunakan library JavaFX.

3. Membuat form input data produk yang terdiri dari TextField dan Button.

4. Menampilkan daftar produk pada GUI menggunakan ListView.

5. Mengintegrasikan tampilan GUI dengan modul backend (DAO & Service) yang telah dibuat sebelumnya.

---

## Dasar Teori
1. Event-Driven Programming: Paradigma pemrograman di mana alur eksekusi program ditentukan oleh kejadian (events) seperti klik tombol, input keyboard, atau pesan dari sistem lainnya.

2. JavaFX: Platform perangkat lunak untuk membuat dan mengirimkan aplikasi desktop serta aplikasi internet kaya (Rich Internet Applications) yang dapat berjalan di berbagai perangkat.

3. MVC (Model-View-Controller): Pola arsitektur yang memisahkan aplikasi menjadi tiga komponen utama: Model (data), View (tampilan), dan Controller (logika penghubung) untuk memudahkan pemeliharaan kode.

4. Dependency Inversion Principle (DIP): Prinsip desain di mana modul tingkat tinggi (View) tidak boleh bergantung langsung pada modul tingkat rendah (DAO), melainkan harus melalui abstraksi atau layer perantara (Service).

---

## Langkah Praktikum
1. Persiapan Project: Menambahkan dependensi JavaFX pada pom.xml dan memastikan struktur direktori sesuai standar Maven.

2. Pembuatan View: Membuat kelas ProductFormView.java yang mewarisi layout JavaFX (seperti GridPane atau VBox) dan menyusun komponen UI (Label, TextField, Button, ListView).

3. Pembuatan Controller: Membuat kelas ProductController.java sebagai penghubung antara View dan Service.

4. Integrasi Backend: Menghubungkan Controller dengan ProductService (Singleton) yang telah dibuat pada pertemuan sebelumnya untuk menangani logika bisnis dan akses data.

5. Implementasi Event Handler: Menambahkan aksi pada tombol "Tambah Produk" menggunakan Lambda Expression untuk menangkap input user dan menyimpannya.

6. Testing: Menjalankan aplikasi melalui AppJavaFX.java dan memastikan data tersimpan ke database PostgreSQL serta muncul di ListView.

---

## Kode Program
1. ProductController.java
```java
package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;

public class ProductController {
    // Menggunakan Singleton ProductService sesuai desain Bab 10 & 12
    private final ProductService service = ProductService.getInstance();

    /**
     * Metode untuk memproses penambahan produk dari GUI
     * Merealisasikan alur UC-01 dan AD-01 dari Bab 6
     */
    public void add(String code, String name, String priceStr, String stockStr) throws Exception {
        // 1. Validasi Input Dasar (Contoh implementasi Activity Diagram)
        if (code.isEmpty() || name.isEmpty()) {
            throw new Exception("Kode dan Nama Produk tidak boleh kosong!");
        }

        try {
            // 2. Konversi tipe data dari String ke numerik
            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);

            // 3. Instansiasi Model Product
            Product product = new Product(code, name, price, stock);

            // 4. Kirim ke Service (DIP: View tidak memanggil DAO langsung)
            service.insert(product);
            
        } catch (NumberFormatException e) {
            throw new Exception("Harga dan Stok harus berupa angka yang valid!");
        }
    }
}
```
2. roductDAO.java
```java
package com.upb.agripos.dao;

import java.util.List;
import com.upb.agripos.model.Product;

public interface ProductDAO {
    void insert(Product product) throws Exception;
    Product findByCode(String code) throws Exception;
    List<Product> findAll() throws Exception;
    void update(Product product) throws Exception;
    void delete(String code) throws Exception;
}
```
3. ProductDAOImpl.java
```java
package com.upb.agripos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.upb.agripos.model.Product;

public class ProductDAOImpl implements ProductDAO {
    private final Connection connection;

    public ProductDAOImpl(Connection connection) {
        this.connection = connection;
    }

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

    @Override
    public Product findByCode(String code) throws Exception {
        String sql = "SELECT * FROM products WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getString("code"), rs.getString("name"),
                        rs.getDouble("price"), rs.getInt("stock")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Statement s = connection.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(
                    rs.getString("code"), rs.getString("name"),
                    rs.getDouble("price"), rs.getInt("stock")
                ));
            }
        }
        return list;
    }

    @Override
    public void update(Product p) throws Exception {
        String sql = "UPDATE products SET name=?, price=?, stock=? WHERE code=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getCode());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String code) throws Exception {
        String sql = "DELETE FROM products WHERE code=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.executeUpdate();
        }
    }
}
```
4. class Product.java
```java
package com.upb.agripos.model;

public class Product {
    private String code, name;
    private double price;
    private int stock;

    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    // Getters
    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
}
```
5. ProductService.java
```java
package com.upb.agripos.service;

import com.upb.agripos.dao.*;
import com.upb.agripos.model.Product;
import java.sql.DriverManager;

public class ProductService {
    private static ProductService instance;
    private ProductDAO dao;

    private ProductService() {
        try {
            // Pastikan database agripos sudah dibuat di PostgreSQL
            var conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/agripos", "postgres", "1234");
            this.dao = new ProductDAOImpl(conn);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static ProductService getInstance() {
        if (instance == null) instance = new ProductService();
        return instance;
    }

    public void insert(Product p) throws Exception {
        dao.insert(p);
    }
}
```
6. ProductFormView.java
```java
package com.upb.agripos.view;

import com.upb.agripos.controller.ProductController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ProductFormView extends VBox {
    private final ProductController controller = new ProductController();
    private ListView<String> listView = new ListView<>();

    public ProductFormView() {
        this.setPadding(new Insets(20));
        this.setSpacing(10);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);

        TextField txtCode = new TextField(), txtName = new TextField(), 
                  txtPrice = new TextField(), txtStock = new TextField();
        Button btnAdd = new Button("Tambah Produk");

        grid.add(new Label("Kode:"), 0, 0); grid.add(txtCode, 1, 0);
        grid.add(new Label("Nama:"), 0, 1); grid.add(txtName, 1, 1);
        grid.add(new Label("Harga:"), 0, 2); grid.add(txtPrice, 1, 2);
        grid.add(new Label("Stok:"), 0, 3); grid.add(txtStock, 1, 3);
        grid.add(btnAdd, 1, 4);

        // Event Handling
        btnAdd.setOnAction(event -> {
            try {
                controller.add(txtCode.getText(), txtName.getText(), 
                               txtPrice.getText(), txtStock.getText());
                listView.getItems().add(txtCode.getText() + " - " + txtName.getText());
                txtCode.clear(); txtName.clear(); txtPrice.clear(); txtStock.clear();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        });

        this.getChildren().addAll(new Label("Form Input Produk"), grid, new Label("Daftar:"), listView);
    }
}
```
7. AppJavaFX.java
```java
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
```
8. Launcher.java
```java
package com.upb.agripos;

public class Launcher {
    public static void main(String[] args) {
        // Memanggil main method yang asli di AppJavaFX
        AppJavaFX.main(args);
    }
}
9. pom.xml
```java
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.upb</groupId>
    <artifactId>agripos-app</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.release>21</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <javafx.version>21</javafx.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.2</version>
        </dependency>

        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${javafx.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
            </plugin>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>com.upb.agripos.AppJavaFX</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## Hasil Eksekusi

![Screenshot hasil](/praktikum/week12-gui-dasar/screenshots/Screenshot%202026-01-23%20160158.png)

---

## Analisis

**1. Alur Kerja Program**
Program berjalan menggunakan arsitektur Event-Driven. Saat pengguna mengisi form dan menekan tombol "Tambah Produk", *event handler* akan dipicu. Data diambil dari text field, dikirim ke `ProductController`, lalu diteruskan ke `ProductService`. Service kemudian memanggil `ProductDAO` untuk menyimpan data secara permanen ke database PostgreSQL. Setelah sukses, UI diperbarui secara *real-time* dengan menambahkan item ke `ListView`.

**2. Perbedaan dengan Minggu Sebelumnya**
Pada minggu-minggu sebelumnya, interaksi dilakukan melalui konsol (CLI) yang bersifat prosedural dan teks. Minggu ini, interaksi berbasis grafis (GUI) yang lebih interaktif dan user-friendly.

**3. Analisis Traceability (Bab 6 → GUI)**
Implementasi ini telah memenuhi spesifikasi desain Bab 6 sebagai berikut:

| Artefak Bab 6 | Referensi | Handler GUI | Controller/Service | DAO | Dampak UI/DB |
| --- | --- | --- | --- | --- | --- |
| Use Case | UC-01 Tambah Produk | Tombol Tambah | `ProductController.add()` → `ProductService.insert()` | `ProductDAO.insert()` | UI list bertambah + DB insert |
| Activity | AD-01 Tambah Produk | Tombol Tambah | Validasi Input & Parsing | `ProductService.insert()` | validasi → simpan → tampil |
| Sequence | SD-01 Tambah Produk | Tombol Tambah | View→Controller→Service | DAO→DB | urutan panggilan sesuai SD |

---

## Kesimpulan
Praktikum Minggu 12 berhasil mengimplementasikan antarmuka grafis (GUI) untuk sistem Agri-POS menggunakan JavaFX. Penggunaan pola MVC dan pemisahan layer (View-Controller-Service-DAO) terbukti membuat kode lebih terstruktur, di mana logika tampilan terpisah dari logika bisnis dan akses data. Integrasi dengan database PostgreSQL berjalan lancar tanpa perlu menulis ulang query SQL di sisi tampilan.