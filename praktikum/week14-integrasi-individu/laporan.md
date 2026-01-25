# Laporan Praktikum Minggu 14
Topik: Integrasi Individu (OOP + Database + GUI)

## Identitas
- Nama  : Febri Muhsinin
- NIM   : 240202835
- Kelas : 3IKRA

## 1. Ringkasan Aplikasi

**Agri-POS** adalah aplikasi Point of Sales (Kasir) sederhana untuk toko pertanian. Aplikasi ini dibangun menggunakan JavaFX dengan arsitektur MVC (Model-View-Controller) yang memisahkan logika tampilan, bisnis, dan akses data.

**Fitur Utama:**

1. **Manajemen Produk (CRUD Database):** Pengguna dapat melihat, menambah, dan menghapus data produk yang tersimpan permanen di database PostgreSQL.
2. **Keranjang Belanja (Collections):** Pengguna dapat memilih produk dari tabel inventory dan memasukkannya ke keranjang belanja sementara (in-memory).
3. **Kalkulasi Total:** Total harga belanja dihitung secara otomatis menggunakan logika bisnis di layer Service.

## 2. Keterangan Integrasi Bab 1–13

Aplikasi ini menggabungkan materi dari seluruh semester:

* **Bab 1-5 (OOP):** Penggunaan Class `Product`, `CartItem`, Encapsulation (getter/setter).
* **Bab 6 (UML & SOLID):** Struktur kode mengikuti prinsip *Single Responsibility* (DAO hanya urus DB, View hanya urus UI).
* **Bab 7 (Collections):** Penggunaan `List<CartItem>` dan `ObservableList` untuk menampung data keranjang.
* **Bab 9 (Exception Handling):** Validasi input (misal: harga negatif) dan penanganan error koneksi database menggunakan `try-catch`.
* **Bab 10 (Pattern & Testing):** Penerapan **Singleton Pattern** pada `ProductService` dan **Unit Testing** pada `CartServiceTest`.
* **Bab 11 (JDBC & DAO):** Akses data PostgreSQL melalui `JdbcProductDAO`.
* **Bab 12-13 (GUI JavaFX):** Tampilan interaktif menggunakan `TableView`, `ListView`, dan Lambda Expressions.

## 3. Implementasi Traceability (Bab 6 → Kode)

Berikut adalah pemetaan desain sistem terhadap kode yang telah diimplementasikan:

| Fitur / Use Case | Komponen GUI (View) | Controller / Service | DAO / Data Access | Dampak Sistem |
| --- | --- | --- | --- | --- |
| **Tambah Produk** | Tombol `Tambah ke DB` | `PosController.addProductToDb()` → `ProductService.addProduct()` | `JdbcProductDAO.insert()` | Data tersimpan permanen di tabel `products` PostgreSQL. |
| **Hapus Produk** | Tombol `Hapus dari DB` | `PosController.deleteProduct()` → `ProductService.deleteProduct()` | `JdbcProductDAO.delete()` | Baris data hilang dari database dan TableView diperbarui. |
| **Lihat Daftar** | `TableView<Product>` | `PosController.loadProducts()` → `ProductService.getAllProducts()` | `JdbcProductDAO.findAll()` | Data dari database ditampilkan dalam bentuk tabel (Kode, Nama, Harga, Stok). |
| **Tambah ke Keranjang** | Tombol `Masuk Keranjang >>` | `PosController.addToCart()` → `CartService.addToCart()` | - (Memory `List`) | Objek `CartItem` ditambahkan ke koleksi, List keranjang terupdate. |


## 4. Tabel Traceability Bab 6 → Implementasi

| Artefak Bab 6 | Referensi (ID/Nama) | Handler / Trigger (GUI) | Controller / Service Layer | DAO / Data Access | Dampak pada Sistem / DB |
| --- | --- | --- | --- | --- | --- |
| **Use Case** | UC-01 Tambah Produk | Tombol `Tambah ke DB` (`btnAdd`) | `PosController.addProductToDb()` → `ProductService.addProduct()` | `JdbcProductDAO.insert()` | Data produk baru tersimpan permanen di tabel PostgreSQL. |
| **Use Case** | UC-02 Hapus Produk | Tombol `Hapus dari DB` (`btnDel`) | `PosController.deleteProduct()` → `ProductService.deleteProduct()` | `JdbcProductDAO.delete()` | Data produk terhapus dari PostgreSQL dan hilang dari TableView. |
| **Use Case** | UC-03 Lihat Daftar | Inisialisasi Aplikasi / `refreshData()` | `PosController.loadProducts()` → `ProductService.getAllProducts()` | `JdbcProductDAO.findAll()` | Data diambil (`SELECT *`) dari DB dan ditampilkan di `TableView`. |
| **Activity Diagram** | AD-01 Alur Keranjang | Tombol `Masuk Keranjang >>` (`btnToCart`) | `PosController.addToCart()` → `CartService.addToCart()` | - (Memory) | Objek `CartItem` masuk ke `List` sementara, total harga dihitung ulang. |
| **Sequence Diagram** | SD-01 Simpan Data | Event Klik Tombol Simpan | View memanggil Controller → Service memvalidasi → DAO eksekusi SQL | `PreparedStatement` | Alur eksekusi kode sesuai dengan urutan pesan pada diagram. |
| **Class Diagram** | Relasi Agregasi (Cart) | Kelas `Cart` & `CartItem` | `CartService` mengelola `List<CartItem>` | - | Implementasi sesuai struktur kelas: 1 Keranjang memiliki banyak Item. |


## 5. Hasil Eksekusi & Screenshot

**A. Tampilan Utama Aplikasi (`app_main.png`)**
![Screenshot hasil](/praktikum/week14-integrasi-individu/screenshots/app_main.png)


**B. Hasil Unit Test (`junit_result.png`)**
![Screenshot hasil](/praktikum/week14-integrasi-individu/screenshots/junit_result.png)

---

## 6. Kendala dan Solusi

Selama pengerjaan Week 14, terdapat beberapa kendala teknis:

1. **Kendala:** `Error: JavaFX runtime components are missing` saat menjalankan aplikasi via tombol Run.
* **Solusi:** Membuat kelas bantu `Launcher.java` yang memanggil `AppJavaFX.main()` untuk memanipulasi cara *classloader* memuat library JavaFX.


2. **Kendala:** `NullPointerException: "this.dao is null"` saat menambah data.
* **Solusi:** Ditemukan bahwa password database salah sehingga koneksi gagal saat inisialisasi. Solusinya adalah menyesuaikan password di `ProductService` dan menambahkan penanganan error (try-catch) agar aplikasi memberikan pesan jelas jika database gagal terkoneksi.


3. **Kendala:** Kolom "Stok" tidak muncul di TableView.
* **Solusi:** Menambahkan objek `TableColumn` untuk stok pada metode `setupTable()` di file `PosView.java`.



## 7. Kesimpulan

Praktikum Week 14 berhasil mengintegrasikan seluruh komponen perangkat lunak menjadi satu kesatuan. Aplikasi Agri-POS kini dapat berjalan secara *end-to-end*, mulai dari antarmuka pengguna (GUI), logika bisnis (Service), hingga penyimpanan data persisten (Database). Penerapan *Unit Testing* juga memberikan jaminan bahwa logika perhitungan keranjang berjalan dengan akurat.