# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik: [Inheritance (Kategori Produk)]

## Identitas
- Nama  : [Febri Muhsinin]
- NIM   : [240202835]
- Kelas : [3ikra]

---

## Tujuan
(
- Mahasiswa mampu menjelaskan konsep inheritance (pewarisan class) dalam pemrograman berorientasi objek.

- Mahasiswa mampu membuat superclass dan subclass untuk produk pertanian.

- Mahasiswa mampu mendemonstrasikan hierarki class menggunakan contoh kode program.

- Mahasiswa mampu menggunakan super untuk memanggil konstruktor dan method parent class.

- Mahasiswa mampu menjelaskan perbedaan penggunaan inheritance dibanding class tunggal.)

---

## Dasar Teori
(
1. Inheritance adalah mekanisme dalam OOP di mana suatu class dapat mewarisi atribut dan method dari class lain.

2. Superclass adalah class induk yang berisi atribut dan perilaku umum.

3. Subclass adalah class turunan yang mewarisi properti dari superclass dan dapat menambahkan atribut atau method baru.

4. Keyword super digunakan untuk mengakses konstruktor atau method dari superclass.

5. Inheritance membantu menciptakan kode yang reusable, efisien, dan mudah dikembangkan.)

---

## Langkah Praktikum
(
1. Membuat class Produk sebagai superclass yang menyimpan atribut umum seperti kode, nama, harga, dan stok.

2. Membuat tiga subclass:

   - Benih.java dengan atribut tambahan varietas.

   - Pupuk.java dengan atribut tambahan jenis.

   - AlatPertanian.java dengan atribut tambahan material.

3. Membuat MainInheritance.java untuk menampilkan data dari tiap subclass.

4. Menambahkan class CreditBy untuk menampilkan identitas pembuat program.

5. Menjalankan program dan mengambil screenshot hasil eksekusi.

6. Melakukan commit dengan pesan:

   week3-inheritance
)

---

## Kode Program
(Tuliskan kode utama yang dibuat, contoh:  
1. Produk.java
```java
package com.upb.agripos.model;

/**
 * Superclass untuk semua jenis produk di Agri-POS.
 * Berisi atribut dan method umum yang dimiliki oleh setiap produk.
 */
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

    // Getters
    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }

    // Setters
    public void setKode(String kode) { this.kode = kode; }
    public void setNama(String nama) { this.nama = nama; }
    public void setHarga(double harga) { this.harga = harga; }
    public void setStok(int stok) { this.stok = stok; }

    /**
     * Method untuk menampilkan deskripsi dasar produk.
     * Method ini akan di-override oleh subclass untuk informasi yang lebih spesifik.
     */
    public String deskripsi() {
        return "Kode: " + this.kode +
               ", Nama: " + this.nama +
               ", Harga: Rp" + String.format("%,.2f", this.harga) +
               ", Stok: " + this.stok;
    }
}
```
2. Benih.java
```java
package com.upb.agripos.model;

public class Benih extends Produk {
    private String varietas;

    public Benih(String kode, String nama, double harga, int stok, String varietas) {
        // Memanggil konstruktor superclass (Produk)
        super(kode, nama, harga, stok);
        this.varietas = varietas;
    }

    public String getVarietas() { return varietas; }
    public void setVarietas(String varietas) { this.varietas = varietas; }

    // Override method deskripsi untuk menambahkan info varietas
    @Override
    public String deskripsi() {
        return super.deskripsi() + ", Varietas: " + this.varietas;
    }
}
```
3. Pupuk.java
```java
package com.upb.agripos.model;

public class Pupuk extends Produk {
    private String jenis;

    public Pupuk(String kode, String nama, double harga, int stok, String jenis) {
        // Memanggil konstruktor superclass (Produk)
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    // Override method deskripsi untuk menambahkan info jenis
    @Override
    public String deskripsi() {
        return super.deskripsi() + ", Jenis: " + this.jenis;
    }
}
```
4. AlatPertanian.java
```java
package com.upb.agripos.model;

public class AlatPertanian extends Produk {
    private String material;

    public AlatPertanian(String kode, String nama, double harga, int stok, String material) {
        // Memanggil konstruktor superclass (Produk)
        super(kode, nama, harga, stok);
        this.material = material;
    }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    // Override method deskripsi untuk menambahkan info material
    @Override
    public String deskripsi() {
        return super.deskripsi() + ", Material: " + this.material;
    }
}
```
5. CreditBy.java
```java
package com.upb.agripos.util;

public class CreditBy {
    public static void print(String nim, String nama) {
        System.out.println("\n=================================");
        System.out.println("Tugas ini dikerjakan oleh:");
        System.out.println("NIM  : " + nim);
        System.out.println("Nama : " + nama);
        System.out.println("=================================");
    }
}
```
6. MainInheritance.java
```java
package com.upb.agripos;

import com.upb.agripos.model.*;
import com.upb.agripos.util.CreditBy;

public class MainInheritance {
    public static void main(String[] args) {
        // Instansiasi objek dari tiap subclass
        Benih benihPadi = new Benih("BNH-001", "Benih Padi IR64", 25000, 100, "IR64");
        Pupuk pupukUrea = new Pupuk("PPK-101", "Pupuk Urea Subsidi", 350000, 40, "Urea");
        AlatPertanian cangkul = new AlatPertanian("ALT-501", "Cangkul Baja", 90000, 15, "Baja");

        System.out.println("===== INFORMASI PRODUK AGRI-POS =====");
        
        // Menampilkan data dasar (Tugas 3)
        System.out.println("\n--- Data Dasar Produk ---");
        System.out.println("Benih: " + benihPadi.getNama() + " | Varietas: " + benihPadi.getVarietas());
        System.out.println("Pupuk: " + pupukUrea.getNama() + " | Jenis: " + pupukUrea.getJenis());
        System.out.println("Alat: " + cangkul.getNama() + " | Material: " + cangkul.getMaterial());

        // Menampilkan data lengkap (Latihan Mandiri)
        System.out.println("\n--- Deskripsi Lengkap Produk (Latihan Mandiri) ---");
        System.out.println("Deskripsi Benih : " + benihPadi.deskripsi());
        System.out.println("Deskripsi Pupuk : " + pupukUrea.deskripsi());
        System.out.println("Deskripsi Alat  : " + cangkul.deskripsi());

        // Menampilkan credit (Tugas 4)
        CreditBy.print("240202835", "Febri Muhsinin");
    }
}
```
)
---

## Hasil Eksekusi
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](/praktikum/week3-inheritance/screenshots/Screenshot%202025-10-22%20133810.png)
)
---

## Analisis
(
- Program ini menggunakan pewarisan class (extends) agar subclass (Benih, Pupuk, dan AlatPertanian) dapat memiliki atribut dan method dari class induk (Produk).

- Dengan inheritance, kode menjadi lebih efisien dan tidak terjadi duplikasi karena semua atribut dasar produk disimpan di Produk.java.

- Setiap subclass hanya menambahkan atribut unik, misalnya varietas untuk benih, jenis untuk pupuk, dan material untuk alat pertanian.

- Pendekatan ini berbeda dari minggu sebelumnya (class tunggal), karena sekarang hierarki antar class lebih jelas dan modular.

- Kendala yang dihadapi: error pada saat pemanggilan konstruktor super, namun dapat diatasi dengan menyesuaikan parameter konstruktor superclass.  
)
---

## Kesimpulan
(
- Inheritance mempermudah pengelolaan struktur program dengan membagi atribut umum ke dalam superclass.

- Subclass dapat memperluas fungsionalitas tanpa mengulang kode yang sama.

- Dengan pendekatan ini, program lebih terorganisir, mudah dibaca, dan mudah dikembangkan.)

---

## Quiz
(
1. Apa keuntungan menggunakan inheritance dibanding membuat class terpisah tanpa hubungan?
   Jawaban: Inheritance menghindari duplikasi kode dan memungkinkan penggunaan kembali atribut serta method dari superclass.

2. Bagaimana cara subclass memanggil konstruktor superclass?
   Jawaban: Dengan menggunakan keyword super() di dalam konstruktor subclass.

3. Berikan contoh kasus di POS pertanian selain Benih, Pupuk, dan Alat Pertanian yang bisa dijadikan subclass.
   Jawaban: Contoh lainnya adalah ObatTanaman (dengan atribut bahanAktif) atau Pestisida (dengan atribut jenisHama).
)
