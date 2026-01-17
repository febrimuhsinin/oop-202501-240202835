# Laporan Praktikum Minggu 10
Topik: Design Pattern (Singleton, MVC) dan Unit Testing menggunakan JUnit

## Identitas
- Nama  : Febri Muhsinin
- NIM   : 240202835
- Kelas : 3IKRA

---

## Tujuan
1. Memahami dan mengimplementasikan Singleton Pattern untuk menjamin satu instance global pada layanan tertentu.

2. Menerapkan pola arsitektur Model–View–Controller (MVC) untuk memisahkan logika bisnis, tampilan, dan kontroler pada sistem Agri-POS.

3. Membuat dan menjalankan pengujian otomatis menggunakan JUnit untuk memastikan kualitas perangkat lunak.

4. Mengelola dependensi proyek menggunakan Maven dengan berkas pom.xml.

---

## Dasar Teori
1. Singleton Pattern: Pola desain yang menjamin suatu kelas hanya memiliki satu instance dan menyediakan titik akses global. Cirinya adalah memiliki private constructor, atribut static instance, dan metode static getInstance().

2. MVC (Model-View-Controller): Pemisahan tanggung jawab aplikasi menjadi tiga bagian:

   - Model: Mengelola data dan logika bisnis (seperti kelas Product).

   - View: Mengelola tampilan atau output (seperti ConsoleView).

   - Controller: Penghubung antara Model dan View (seperti ProductController).

3. Unit Testing (JUnit): Pengujian pada level terkecil dari kode program untuk memastikan fungsionalitas berjalan sesuai harapan sebelum masuk ke tahap produksi.

4. Maven: Tool manajemen proyek yang menggunakan berkas pom.xml untuk mengatur dependensi library seperti JUnit Jupiter.

---

## Langkah Praktikum
1. Konfigurasi Maven: Menambahkan dependensi junit-jupiter-api dan junit-jupiter-engine pada berkas pom.xml agar fitur testing dapat digunakan.

2. Implementasi Singleton: Membuat kelas DatabaseConnection di paket config dengan konstruktor privat.

3. Penyusunan Struktur MVC:

   - Membuat kelas Product (Model) di paket model.

   - Membuat kelas ConsoleView (View) di paket view.

   - Membuat kelas ProductController (Controller) di paket controller.

4. Pembuatan Unit Test: Menyusun kelas ProductTest di folder pengujian untuk memverifikasi metode pada Model.

5. Debugging: Memperbaiki kesalahan deklarasi paket yang muncul di VS Code akibat konfigurasi direktori sumber yang tidak standar di Maven.

---

## Kode Program
1. DatabaseConnection.java
```java
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
```
2. ProductController.java
```java
package main.java.com.upb.agripos.controller;

import main.java.com.upb.agripos.model.Product;
import main.java.com.upb.agripos.view.ConsoleView;

public class ProductController {
    private final Product model;
    private final ConsoleView view;

    public ProductController(Product model, ConsoleView view) {
        this.model = model;
        this.view = view;
    }

    public void showProduct() {
        view.showMessage("Produk Pertanian: " + model.getCode() + " - " + model.getName());
    }
}
```
3. Product.java
```java
package main.java.com.upb.agripos.model;

public class Product {
    private final String code;
    private final String name;

    public Product(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
}
```
4. ConsoleView.java
```java
package main.java.com.upb.agripos.view;

public class ConsoleView {
    public void showMessage(String message) {
        System.out.println("[View Output]: " + message);
    }
}
```
5. AppMVC.java
```java
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
```
6. ProductTest.java
```java
package test.java.com.upb.agripos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.java.com.upb.agripos.model.Product;

public class ProductTest {
    @Test
    public void testProductName() {
        Product p = new Product("PPK-01", "Pupuk NPK");
        // Memastikan nama produk sesuai
        assertEquals("Pupuk NPK", p.getName());
    }

    @Test
    public void testProductCode() {
        Product p = new Product("ALT-01", "Cangkul Baja");
        assertEquals("ALT-01", p.getCode());
    }
}
```

---

## Hasil Eksekusi
ProductTest.java
![Screenshot hasil](/praktikum/week10-pattern-testing/screenshots/junit_result.png)

AppMVC.java
![Screenshot hasil](/praktikum/week10-pattern-testing/screenshots/Screenshot%202026-01-17%20162041.png)
---

## Analisis
(
- Evaluasi Singleton & MVC: Penerapan Singleton pada koneksi database memastikan efisiensi sumber daya. Sementara itu, pola MVC memudahkan pemeliharaan kode karena logika data dipisahkan dari cara menampilkannya.

- Analisis Error VS Code: Pada tangkapan layar image_2c07ee.jpg, terlihat error "The declared package... does not match the expected package". Hal ini disebabkan oleh konfigurasi pom.xml yang memaksa <sourceDirectory>src</sourceDirectory>. Karena file berada di src/test/java/..., Maven mengharapkan nama paket diawali dengan test.java.... Solusinya adalah mengikuti struktur standar Maven (src/main/java) atau menyesuaikan deklarasi paket sesuai hierarki folder dari root src.

- Unit Testing: Penggunaan @Test memungkinkan verifikasi otomatis. Jika nama produk tidak sesuai, assertEquals akan memberikan sinyal kegagalan, yang meningkatkan keandalan aplikasi. 
)
---

## Kesimpulan
(Praktikum Minggu 10 berhasil mengintegrasikan pola desain Singleton dan MVC ke dalam sistem Agri-POS. Penggunaan Maven mempermudah pengelolaan library JUnit, meskipun diperlukan ketelitian dalam mengatur struktur folder agar tidak terjadi kesalahan deklarasi paket pada IDE.)

---

## Quiz
1. Mengapa constructor pada Singleton harus bersifat private? 
Agar kelas tersebut tidak dapat di-instansiasi menggunakan kata kunci new dari luar kelas, sehingga jumlah instance tetap terbatas hanya satu.

2. Jelaskan manfaat pemisahan Model, View, dan Controller. 
Memudahkan pemeliharaan kode (maintenance), memungkinkan pengembangan tampilan (View) tanpa mengganggu logika bisnis (Model), dan mempermudah pengujian secara terpisah.

3. Apa peran unit testing dalam menjaga kualitas perangkat lunak? 
Mendeteksi bug sejak dini, memastikan setiap unit kecil kode berjalan sesuai spesifikasi, dan memberikan rasa aman saat melakukan perubahan kode (refactoring).

4. Apa risiko jika Singleton tidak diimplementasikan dengan benar? 
Terjadi duplikasi instance yang menyebabkan pemborosan memori dan potensi inkonsistensi data saat akses global dilakukan secara bersamaan.
