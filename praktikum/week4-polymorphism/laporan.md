# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik: Polymorphism (Info Produk)

## Identitas
- Nama  : [Febri Muhsinin]
- NIM   : [240202835]
- Kelas : [3IKRA]

---

## Tujuan
- Mahasiswa mampu menjelaskan konsep polymorphism dalam OOP.

- Mahasiswa mampu membedakan method overloading dan overriding.

- Mahasiswa mampu mengimplementasikan polymorphism (overriding, overloading, dynamic binding) dalam program.

- Mahasiswa mampu menganalisis contoh kasus polymorphism pada sistem nyata (Agri-POS).

---

## Dasar Teori
1. Polymorphism berarti “banyak bentuk”, yaitu kemampuan objek untuk merespons panggilan method yang sama dengan cara berbeda tergantung jenis objeknya.

2. Overloading adalah mendefinisikan beberapa method dengan nama sama tetapi parameter berbeda dalam satu kelas.

3. Overriding terjadi ketika subclass mengganti implementasi method dari superclass dengan versi miliknya sendiri.

4. Dynamic Binding menentukan method mana yang dipanggil berdasarkan tipe objek aktual saat runtime, bukan saat compile time.

5. Dalam OOP, polymorphism meningkatkan fleksibilitas dan skalabilitas kode karena program dapat bekerja dengan tipe objek yang berbeda menggunakan antarmuka yang sama.

---

## Langkah Praktikum
1. Membuat project oop-20251-240202835/praktikum/week4-polymorphism/.

2. Membuat kelas di dalam com.upb.agripos.model:

   - Produk.java (memuat method tambahStok overload dan getInfo default).

   - Benih.java, Pupuk.java, AlatPertanian.java yang meng-override method getInfo().

3. Membuat kelas CreditBy.java di package com.upb.agripos.util.

4. Membuat kelas utama MainPolymorphism.java di package com.upb.agripos untuk menampilkan hasil overriding, overloading, dan dynamic binding.

5. Menjalankan program untuk menampilkan informasi produk menggunakan array Produk[].

---

## Kode Program
1. Produk.java
```java
package main.java.com.upb.agripos.model;

public class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    // ===== Method Overloading =====
    public void tambahStok(int jumlah) {
        this.stok += jumlah;
    }

    public void tambahStok(double jumlah) {
        this.stok += (int) jumlah;
    }

    // ===== Getter Info =====
    public String getInfo() {
        return "Produk: " + nama +
               " (Kode: " + kode + "), Harga: " + harga +
               ", Stok: " + stok;
    }

    // Getter dan Setter opsional
    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }
}
```
2. Benih.java
```java
package main.java.com.upb.agripos.model;

public class Benih extends Produk {
    private String varietas;

    public Benih(String kode, String nama, double harga, int stok, String varietas) {
        super(kode, nama, harga, stok);
        this.varietas = varietas;
    }

    @Override
    public String getInfo() {
        return "Benih - " + super.getInfo() + ", Varietas: " + varietas;
    }
}
```
3. Pupuk.java
```java
package main.java.com.upb.agripos.model;

public class Pupuk extends Produk {
    private String jenis;

    public Pupuk(String kode, String nama, double harga, int stok, String jenis) {
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    @Override
    public String getInfo() {
        return "Pupuk - " + super.getInfo() + ", Jenis: " + jenis;
    }
}
```
4. AlatPertanian.java
```java
package main.java.com.upb.agripos.model;

public class AlatPertanian extends Produk {
    private String bahan;

    public AlatPertanian(String kode, String nama, double harga, int stok, String bahan) {
        super(kode, nama, harga, stok);
        this.bahan = bahan;
    }

    @Override
    public String getInfo() {
        return "Alat Pertanian - " + super.getInfo() + ", Bahan: " + bahan;
    }
}
```
5. ObatHama.java
```java
package main.java.com.upb.agripos.model;

public class ObatHama extends Produk {
    private String kandunganAktif;

    public ObatHama(String kode, String nama, double harga, int stok, String kandunganAktif) {
        super(kode, nama, harga, stok);
        this.kandunganAktif = kandunganAktif;
    }

    @Override
    public String getInfo() {
        return "Obat Hama - " + super.getInfo() + ", Kandungan Aktif: " + kandunganAktif;
    }
}
```
6. CreditBy.java
```java
package main.java.com.upb.agripos.util;

public class CreditBy {
    public static void print(String nim, String nama) {
        System.out.println("\n=== Credit By ===");
        System.out.println("NIM  : " + nim);
        System.out.println("Nama : " + nama);
    }
}
```
7. MainPolymorphism.java
```java
package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.*;
import main.java.com.upb.agripos.util.CreditBy;

public class MainPolymorphism {
    public static void main(String[] args) {

        // ===== Membuat array objek (Dynamic Binding) =====
        Produk[] daftarProduk = {
            new Benih("BNH-001", "Benih Padi IR64", 25000, 100, "IR64"),
            new Pupuk("PPK-101", "Pupuk Urea", 350000, 40, "Urea"),
            new AlatPertanian("ALT-501", "Cangkul Baja", 90000, 15, "Baja"),
            new ObatHama("OBT-888", "Anti Wereng", 50000, 25, "Klorpirifos")
        };

        System.out.println("=== Informasi Produk (Dynamic Binding) ===");
        for (Produk p : daftarProduk) {
            System.out.println(p.getInfo());
        }

        // ===== Demonstrasi Overloading =====
        System.out.println("\n=== Setelah Tambah Stok (Overloading) ===");
        daftarProduk[0].tambahStok(10);      // tambah stok dengan int
        daftarProduk[1].tambahStok(5.5);     // tambah stok dengan double

        System.out.println(daftarProduk[0].getInfo());
        System.out.println(daftarProduk[1].getInfo());

        // ===== Identitas =====
        CreditBy.print("240202835", "Febri Muhsinin");
    }
}
```

---

## Hasil Eksekusi
![Screenshot hasil](/praktikum/week4-polymorphism/screenshots/Screenshot%202025-10-30%20130016.png)

---

## Analisis
- Program menunjukkan bahwa method getInfo() dijalankan sesuai tipe objek aktual (Benih, Pupuk, AlatPertanian) meskipun semuanya disimpan dalam array bertipe Produk. Ini adalah contoh dynamic binding.

- Overloading terlihat pada method tambahStok(int) dan tambahStok(double) yang memperlihatkan cara berbeda untuk menambah stok.

- Overriding digunakan agar setiap subclass memiliki detail informasi spesifik produk.

- Dibanding minggu sebelumnya (tentang inheritance), minggu ini menambahkan fleksibilitas pemanggilan method berdasarkan tipe objek aktual, bukan hanya pewarisan atribut.

- Kendala: Memastikan struktur direktori dan package benar agar program dapat dikompilasi tanpa error. Solusi: cek package di setiap file dan jalankan dari folder src/main/java. 
---

## Kesimpulan
- Polymorphism memungkinkan satu antarmuka digunakan untuk berbagai bentuk objek yang berbeda.

- Overloading terjadi di dalam satu kelas dengan nama method sama tapi parameter berbeda.

- Overriding digunakan untuk mengganti perilaku method superclass di subclass.

- Dengan dynamic binding, Java menentukan method mana yang dipanggil saat runtime berdasarkan tipe objek aktual, bukan tipe referensinya.

---

## Quiz
1. Apa perbedaan overloading dan overriding?
Jawaban: Overloading terjadi ketika dua atau lebih method memiliki nama yang sama tetapi parameter berbeda dalam satu kelas, sedangkan overriding terjadi ketika subclass mengganti implementasi method dari superclass dengan versi miliknya.

2. Bagaimana Java menentukan method mana yang dipanggil dalam dynamic binding?
Jawaban: Java menentukan method yang dipanggil berdasarkan tipe objek aktual pada saat runtime, bukan tipe referensi yang digunakan.

3. Berikan contoh kasus polymorphism dalam sistem POS selain produk pertanian.
Jawaban: Dalam sistem POS toko elektronik, method getInfo() pada superclass Produk bisa dioverride oleh subclass seperti Televisi, Laptop, dan Smartphone untuk menampilkan informasi spesifik (ukuran layar, RAM, kapasitas baterai, dll).
