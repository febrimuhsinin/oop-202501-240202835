# Laporan Praktikum Minggu 9 
Topik: [Exception Handling, Custom Exception, dan Penerapan Design Pattern]

## Identitas
- Nama  : [febri Muhsinin]
- NIM   : [240202835]
- Kelas : [3IKRA]

---

## Tujuan
1. Menjelaskan perbedaan mendasar antara error dan exception dalam pemrograman Java.

2. Mengimplementasikan blok try–catch–finally untuk menangani kondisi tidak normal pada aplikasi.

3. Membuat dan menggunakan custom exception yang relevan dengan kasus bisnis Agri-POS.

4. Mengintegrasikan penanganan eksepsi ke dalam fitur keranjang belanja dan manajemen stok.

5. Memahami konsep dasar Design Pattern Singleton dan arsitektur MVC.

---

## Dasar Teori
1. Error vs Exception: Error merujuk pada kondisi fatal yang tidak dapat ditangani (seperti OutOfMemoryError), sedangkan Exception adalah kondisi tidak normal yang dapat ditangkap dan ditangani oleh program.

2. Struktur Penanganan: Blok try digunakan untuk kode berisiko, catch untuk logika penanganan, dan finally untuk kode yang wajib dieksekusi terlepas dari munculnya kesalahan.

3. Custom Exception: Membuat kelas turunan dari Exception untuk mendefinisikan kesalahan spesifik yang lebih informatif bagi alur bisnis aplikasi.

4. Design Pattern Singleton: Pola yang menjamin sebuah kelas hanya memiliki satu instansi global untuk menghemat memori dan menjaga konsistensi data.

---

## Langkah Praktikum
1. Setup Struktur Folder: Membuat direktori praktikum/week9-exception-handling/ beserta paket com.upb.agripos.

2. Pembuatan Custom Exception: Membuat kelas InvalidQuantityException, ProductNotFoundException, dan InsufficientStockException.

3. Pembaruan Model: Menambahkan atribut stock dan metode reduceStock() pada kelas Product.

4. Implementasi Logika: Menambahkan klausa throws pada metode addProduct, removeProduct, dan checkout di kelas ShoppingCart.

5. Pengujian: Membuat kelas MainExceptionDemo untuk mensimulasikan kegagalan transaksi dan menangkapnya dengan try-catch.

---

## Kode Program

1. InvalidQuantityException.java
```java
package main.java.com.upb.agripos;

public class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String msg) { 
        super(msg); 
    }
}
```
2. ProductNotFoundException.java
```java
package main.java.com.upb.agripos;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String msg) { 
        super(msg); 
    }
}
```
3. InsufficientStockException.java
```java
package main.java.com.upb.agripos;

public class InsufficientStockException extends Exception {
    public InsufficientStockException(String msg) { 
        super(msg); 
    }
}
```
4. Product.java
```java
package main.java.com.upb.agripos;

public class Product {
    private final String code;
    private final String name;
    private final double price;
    private int stock;

    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    
    public void reduceStock(int qty) { 
        this.stock -= qty; 
    }
}
```
5. ShoppingCart.java
```java
package main.java.com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product p, int qty) throws InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException("Gagal Tambah: Quantity (" + qty + ") harus lebih dari 0.");
        }
        items.put(p, items.getOrDefault(p, 0) + qty);
    }

    public void removeProduct(Product p) throws ProductNotFoundException {
        if (!items.containsKey(p)) {
            throw new ProductNotFoundException("Gagal Hapus: Produk '" + p.getName() + "' tidak ditemukan dalam keranjang.");
        }
        items.remove(p);
    }

    public void checkout() throws InsufficientStockException {
        // Validasi stok untuk semua item di keranjang
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int qty = entry.getValue();
            if (product.getStock() < qty) {
                throw new InsufficientStockException(
                    "Gagal Checkout: Stok '" + product.getName() + "' tidak mencukupi. (Tersedia: " + 
                    product.getStock() + ", Diminta: " + qty + ")"
                );
            }
        }
        
        // Jika semua stok aman, lakukan pengurangan stok
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().reduceStock(entry.getValue());
        }
        System.out.println("Checkout Berhasil! Stok gudang telah diperbarui.");
    }
}
```
6. MainExceptionDemo.java
```java
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
```
```java
// Contoh
Produk p1 = new Produk("BNH-001", "Benih Padi", 25000, 100);
System.out.println(p1.getNama());
```

---

## Hasil Eksekusi
 
![Screenshot hasil](/praktikum/week9-exception-handling/screenshots/Screenshot%202026-01-03%20083104.png)

---

## Analisis
(
1. Mekanisme Kerja: Kode berjalan dengan melakukan validasi di setiap input. Jika input tidak valid (misalnya jumlah barang negatif), sistem akan melempar (throw) objek eksepsi ke metode pemanggil.

2. Perbedaan Pendekatan: Dibandingkan minggu ke-7 (Collections), pendekatan minggu ini jauh lebih aman karena sistem tidak akan membiarkan transaksi berjalan jika data tidak valid atau stok gudang tidak mencukupi.

3. Kendala: Kesulitan awal dalam menentukan kapan harus menggunakan throws (deklarasi) dan throw (melempar). Solusinya adalah memahami bahwa throws digunakan di signature metode untuk memperingatkan pemanggil, sementara throw digunakan di dalam blok kode untuk memicu eksepsi.
)
---

## Kesimpulan
Dengan mengimplementasikan Exception Handling dan Custom Exception, aplikasi Agri-POS menjadi lebih tangguh (robust). Program tidak akan berhenti tiba-tiba saat terjadi kesalahan data, melainkan memberikan umpan balik yang jelas kepada pengguna.

---

## Quiz
1. Jelaskan perbedaan error dan exception. 
Jawaban: Error adalah masalah serius pada sistem atau JVM yang tidak bisa diperbaiki oleh program (fatal), sedangkan Exception adalah masalah dalam logika kode yang bisa diantisipasi dan ditangani agar program tetap berjalan.

2. Apa fungsi finally dalam blok try–catch–finally? 
Jawaban: Blok finally berfungsi untuk menjalankan kode penting (seperti menutup koneksi database atau clean-up memori) yang harus tetap dieksekusi baik terjadi eksepsi maupun tidak.

3. Mengapa custom exception diperlukan? 
Jawaban: Agar pesan kesalahan lebih spesifik dan relevan dengan domain aplikasi (misalnya pesan "Stok Kurang" lebih berguna daripada pesan umum "NullPointerException").

4. Berikan contoh kasus bisnis dalam POS yang membutuhkan custom exception. 
Jawaban: Validasi kuantitas item yang tidak boleh nol, pengecekan apakah produk masih terdaftar di database, dan validasi kecukupan saldo atau stok saat checkout.
