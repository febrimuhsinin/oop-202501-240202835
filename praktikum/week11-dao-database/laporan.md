# Laporan Praktikum Minggu 11
Topik: Data Access Object (DAO) dan CRUD Database dengan JDBC

## Identitas
- Nama  : [Nama Mahasiswa]
- NIM   : [NIM Mahasiswa]
- Kelas : [Kelas]

---

## Tujuan
1. Menjelaskan konsep Data Access Object (DAO) dalam pengembangan aplikasi berbasis objek.

2. Menghubungkan aplikasi Java dengan basis data PostgreSQL menggunakan JDBC.

3. Mengimplementasikan operasi CRUD (Create, Read, Update, Delete) secara lengkap pada tabel produk.

4. Mengintegrasikan pola desain DAO dengan kelas aplikasi sesuai prinsip separation of concerns.

---

## Dasar Teori
1. Data Access Object (DAO): Pola desain yang memisahkan logika akses data dari logika bisnis aplikasi untuk mengurangi ketergantungan (tight coupling).

2. JDBC (Java Database Connectivity): API standar Java untuk menghubungkan aplikasi dengan database relasional melalui komponen seperti DriverManager, Connection, PreparedStatement, dan ResultSet.

3. PostgreSQL: Sistem manajemen basis data relasional (RDBMS) yang digunakan untuk menyimpan data permanen sistem Agri-POS.

4. PreparedStatement: Digunakan untuk mengeksekusi query SQL secara efisien dan aman dari serangan SQL Injection melalui penggunaan parameter.

---

## Langkah Praktikum
1. Konfigurasi Database: Membuat database agripos dan tabel products melalui pgAdmin 4 dengan kolom kode, nama, harga, dan stok.

2. Update Maven: Menambahkan dependensi driver PostgreSQL pada berkas pom.xml agar aplikasi Java dapat berkomunikasi dengan basis data.

3. Pembuatan Model: Menyusun kelas Product di paket model untuk merepresentasikan data produk.

4. Definisi Interface: Membuat ProductDAO sebagai kontrak untuk operasi CRUD.

5. Implementasi JDBC: Membuat kelas ProductDAOImpl yang berisi logika SQL menggunakan JDBC.

6. Integrasi & Testing: Membuat kelas MainDAOTest untuk menguji alur penambahan, pencarian, pembaruan, dan penghapusan data secara berurutan.

---

## Kode Program
1. ProductDAO.java
```java
package main.java.com.upb.agripos.dao;

import java.util.List;
import main.java.com.upb.agripos.model.Product;

public interface ProductDAO {
    void insert(Product product) throws Exception;
    Product findByCode(String code) throws Exception;
    List<Product> findAll() throws Exception;
    void update(Product product) throws Exception;
    void delete(String code) throws Exception;
}
```
2. ProductDAOImpl.java
```java
package main.java.com.upb.agripos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.java.com.upb.agripos.model.Product;

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
3. Product.java
```java
package main.java.com.upb.agripos.model;

public class Product {
    private String code;
    private String name;
    private double price;
    private int stock;

    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getters and Setters
    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
}
```
4. MainDAOTest.java
```java
package main.java.com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;
import main.java.com.upb.agripos.dao.*;
import main.java.com.upb.agripos.model.Product;
import java.util.List;

public class MainDAOTest {
    public static void main(String[] args) {
        System.out.println("Agri-POS - Integrasi Database (Week 11)");
        System.out.println("Nama: Febri Muhsinin | NIM: 240202835");
        System.out.println("----------------------------------------");

        String url = "jdbc:postgresql://localhost:5432/agripos";
        String user = "postgres";
        String pass = "1234"; // Ganti dengan password database Anda

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            ProductDAO dao = new ProductDAOImpl(conn);

            // 1. CREATE
            System.out.println("[CREATE] Menambah Benih Padi...");
            dao.insert(new Product("BNH-01", "Benih Padi Ciherang", 45000, 50));

            // 2. READ (Single)
            Product p = dao.findByCode("BNH-01");
            System.out.println("[READ] Data ditemukan: " + p.getName());

            // 3. UPDATE
            System.out.println("[UPDATE] Mengubah harga...");
            p.setPrice(48000);
            dao.update(p);

            // 4. READ (All)
            List<Product> products = dao.findAll();
            System.out.println("[READ ALL] Total produk: " + products.size());

            // 5. DELETE
            System.out.println("[DELETE] Menghapus data percobaan...");
            dao.delete("BNH-01");

            System.out.println("\nOperasi CRUD Berhasil Diselesaikan.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

---

## Hasil Eksekusi
![Screenshot hasil](/praktikum/week11-dao-database/screenshots/crud_result.png)

---

## Analisis
1. Alur Kerja: Kode berjalan dengan memisahkan perintah SQL ke dalam kelas implementasi DAO. Saat MainDAOTest dijalankan, ia membuka koneksi ke PostgreSQL via JDBC, kemudian memanggil metode DAO untuk berinteraksi dengan tabel products.

2. Peningkatan Desain: Pendekatan ini lebih maju dibanding minggu sebelumnya karena data kini bersifat permanen (tersimpan di database), tidak hilang saat aplikasi dimatikan.

3. Kendala & Solusi: Terdapat kendala awal di mana PostgreSQL tidak terdeteksi. Solusinya adalah melakukan registrasi server pada pgAdmin dengan mengisi tab Connection (Host: localhost, User: postgres) dan menambahkan dependensi driver PostgreSQL di berkas pom.xml.
---

## Kesimpulan
Penerapan pola DAO dan JDBC membuat aplikasi Agri-POS lebih modular dan profesional. Pemisahan logika akses data memudahkan pemeliharaan kode dan memungkinkan penggantian jenis basis data di masa depan tanpa harus mengubah logika bisnis utama aplikasi.

---

## Quiz
1. Apa keuntungan utama menggunakan pola DAO? Memisahkan logika akses data dari logika bisnis sehingga kode lebih terstruktur dan mudah dipelihara.

2. Mengapa PreparedStatement lebih disarankan daripada Statement biasa? Karena lebih aman terhadap serangan SQL Injection dan lebih efisien dalam eksekusi query berulang.

3. Apa peran Driver Manager dalam JDBC? Bertugas mengelola sekumpulan driver JDBC dan membantu membangun koneksi antara aplikasi Java dengan basis data melalui URL tertentu.
