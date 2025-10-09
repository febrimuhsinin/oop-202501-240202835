# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik: [Tuliskan judul topik, misalnya "Class dan Object"]

## Identitas
- Nama  : [Febri Muhsinin]
- NIM   : [240202835]
- Kelas : [3ikra]

---

## Tujuan
(Mahasiswa memahami dan dapat menerapkan konsep class, object, atribut, dan method dalam pemrograman berorientasi objek (OOP).
Mahasiswa juga mampu menggunakan enkapsulasi dengan access modifier (private, public) serta mengimplementasikan class Produk untuk menampilkan data produk pertanian di console.)

---

## Dasar Teori
(
1. Class adalah blueprint atau cetak biru dari objek yang berisi atribut (data) dan method (perilaku).
2. Object merupakan hasil instansiasi dari class yang dapat digunakan untuk menyimpan dan mengelola data nyata.
3. Enkapsulasi berfungsi untuk menyembunyikan data dari akses langsung menggunakan atribut private, serta menyediakan akses melalui getter dan setter.
4. Access Modifier menentukan sejauh mana data dan method dapat diakses dari luar class.
5. Dengan pendekatan OOP, data produk pertanian dapat dibuat lebih terstruktur, reusable, dan mudah dikembangkan dalam sistem POS (Point of Sale).)

---

## Langkah Praktikum

1. Membuat struktur direktori sesuai panduan:
```java
oop-20251-<240202835>/
 └─ praktikum/week2-class-object/
     ├─ src/main/java/com/upb/agripos/model/
     │   └─ Produk.java
     ├─ src/main/java/com/upb/agripos/util/
     │   └─ CreditBy.java
     ├─ src/main/java/com/upb/agripos/
     │   └─ MainProduk.java
     ├─ screenshots/
     │   └─ hasil.png
     └─ laporan_week2.md
```
2. Membuat class Produk pada package model dengan atribut: kode, nama, harga, dan stok.
   - Semua atribut diberi modifier private.
   - Menambahkan method getter dan setter.
   - Menambahkan method tambahStok() dan kurangiStok().
3. Membuat class CreditBy di package util untuk menampilkan identitas mahasiswa.
4. Membuat MainProduk untuk menginstansiasi tiga produk pertanian dan menampilkan data di console.
5. Menjalankan program dan memastikan output sesuai.
6. Commit dengan pesan: week2-class-object.
---

## Kode Program
( 
1. Produk.java
```java
package com.upb.agripos.model;

public class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    // Constructor
    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    // Getter dan Setter
    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }

    // Method tambahan untuk stok
    public void tambahStok(int jumlah) {
        this.stok += jumlah;
    }

    public void kurangiStok(int jumlah) {
        if (this.stok >= jumlah) {
            this.stok -= jumlah;
        } else {
            System.out.println("Stok tidak mencukupi!");
        }
    }
}
```
2. CreditBy.java
```java
package com.upb.agripos.util;

public class CreditBy {
    public static void print(String nim, String nama) {
        System.out.println("\ncredit by: " + nim + " - " + nama);
    }
}
```
3. MainProduk.java
```java
package com.upb.agripos;

import com.upb.agripos.model.Produk;
import com.upb.agripos.util.CreditBy;

public class MainProduk {
    public static void main(String[] args) {
        // Instansiasi 3 produk pertanian
        Produk p1 = new Produk("BNH-001", "Benih Padi IR64", 25000, 100);
        Produk p2 = new Produk("PPK-101", "Pupuk Urea 50kg", 350000, 40);
        Produk p3 = new Produk("ALT-501", "Cangkul Baja", 90000, 15);

        // Tampilkan informasi produk
        System.out.println("Kode: " + p1.getKode() + ", Nama: " + p1.getNama() + ", Harga: " + p1.getHarga()
                + ", Stok: " + p1.getStok());
        System.out.println("Kode: " + p2.getKode() + ", Nama: " + p2.getNama() + ", Harga: " + p2.getHarga()
                + ", Stok: " + p2.getStok());
        System.out.println("Kode: " + p3.getKode() + ", Nama: " + p3.getNama() + ", Harga: " + p3.getHarga()
                + ", Stok: " + p3.getStok());

        // Tambahkan sedikit simulasi stok
        System.out.println("\nMenambah stok Benih Padi IR64 sebanyak 45...");
        p1.tambahStok(45);
        System.out.println("Stok baru: " + p1.getStok());

        System.out.println("Mengurangi stok Pupuk Urea sebanyak 10...");
        p2.kurangiStok(10);
        System.out.println("Stok baru: " + p2.getStok());

        // Tampilkan identitas mahasiswa
        CreditBy.print("240202835", "Febri Muhsinin");
    }
}
```
)
---

## Hasil Eksekusi
( 
![Screenshot hasil](screenshot/Screenshot 2025-10-09 203516.png)
)
---

## Analisis
(
1. Program ini menggunakan konsep enkapsulasi, di mana atribut Produk disembunyikan (private) dan hanya dapat diakses melalui getter/setter.
2. Dengan menggunakan object, data setiap produk dikelola secara terpisah namun melalui struktur yang sama (class).
3. Method tambahan tambahStok dan kurangiStok membantu dalam pengelolaan stok secara dinamis.
4. Dibanding minggu sebelumnya (yang mungkin masih prosedural), pendekatan OOP ini lebih terstruktur dan mudah dikembangkan karena setiap entitas memiliki tanggung jawab sendiri.
5. Kendala yang dihadapi adalah kesalahan import package pada awalnya, yang diselesaikan dengan memastikan struktur direktori sesuai dengan deklarasi package.
)
---

## Kesimpulan
(Dengan menerapkan konsep class dan object, program dapat mengelola data produk pertanian secara lebih efisien dan terstruktur.
Penggunaan enkapsulasi menjaga keamanan data, dan konsep ini menjadi dasar penting dalam pengembangan aplikasi berbasis OOP seperti sistem POS.)

---

## Quiz
(1. [Mengapa atribut sebaiknya dideklarasikan sebagai private dalam class?]  
   **Jawaban:** …  
    Karena agar data terlindungi dan tidak dapat diubah secara langsung dari luar class. Hal ini mencegah manipulasi data yang tidak diinginkan dan menjaga konsistensi program.

2. [Apa fungsi getter dan setter dalam enkapsulasi?]  
   **Jawaban:** …  
    Getter berfungsi untuk mengambil nilai atribut, sedangkan setter digunakan untuk mengubah nilai atribut dengan kontrol tertentu agar data tetap valid.

3. [Bagaimana cara class Produk mendukung pengembangan aplikasi POS yang lebih kompleks?]  
   **Jawaban:** …  
   Dengan membuat class Produk, setiap produk memiliki struktur dan perilaku yang sama sehingga mudah dikelola, ditambahkan fitur baru (seperti perhitungan diskon, laporan stok, dan integrasi database), tanpa mengubah logika utama aplikasi.
   )