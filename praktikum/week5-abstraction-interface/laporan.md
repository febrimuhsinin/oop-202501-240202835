# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik: Abstraction (Abstract Class & Interface)

## Identitas
- Nama  : [Febri Muhsinin]
- NIM   : [240202835]
- Kelas : [3IKRA]

---

## Tujuan
- Mahasiswa mampu menjelaskan perbedaan antara abstract class dan interface.

- Mahasiswa mampu membuat abstract class dengan method abstrak dan konkrit.

- Mahasiswa mampu membuat interface dan mengimplementasikannya ke dalam kelas.

- Mahasiswa mampu menerapkan multiple inheritance via interface.

- Mahasiswa mampu mendokumentasikan kode dengan baik.

---

## Dasar Teori 
1. Abstraksi (Abstraction) adalah proses menyederhanakan sistem kompleks dengan hanya menampilkan fitur penting dan menyembunyikan detail implementasi.

2. Abstract class tidak dapat diinstansiasi dan dapat berisi method abstrak (tanpa isi) maupun konkrit (dengan isi).

3. Interface berisi kontrak method tanpa implementasi konkret. Sejak Java 8, interface juga dapat memiliki default method.

4. Multiple inheritance dapat diterapkan dengan mengimplementasikan beberapa interface dalam satu kelas.

5. Gunakan abstract class untuk perilaku dasar bersama, dan interface untuk mendefinisikan kemampuan lintas hierarki (misal: validasi, pencetakan struk).

---

## Langkah Praktikum
1. Membuat abstract class Pembayaran yang berisi field invoiceNo, total, serta method abstrak biaya() dan prosesPembayaran().

2. Membuat interface Validatable dan Receiptable.

3. Mengimplementasikan Cash (extends Pembayaran, implements Receiptable) dan EWallet (extends Pembayaran, implements Validatable, Receiptable).

4. Menambahkan kelas latihan mandiri TransferBank.

5. Membuat class MainAbstraction untuk menampilkan hasil proses pembayaran secara polimorfik.

6. Menambahkan class CreditBy untuk mencetak identitas mahasiswa.

7. Menjalankan program dan melakukan commit

---

## Kode Program
1. Pembayaran.java
```java
package main.java.com.upb.agripos.model.pembayaran;

public abstract class Pembayaran {
    protected String invoiceNo;
    protected double total;

    public Pembayaran(String invoiceNo, double total) {
        this.invoiceNo = invoiceNo;
        this.total = total;
    }

    public abstract double biaya();
    public abstract boolean prosesPembayaran();

    public double totalBayar() {
        return total + biaya();
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public double getTotal() {
        return total;
    }
}
```
2. Validatable.java
```java
package main.java.com.upb.agripos.model.kontrak;

public interface Validatable {
    boolean validasi();
}
```
3. Receiptable.java
```java
package main.java.com.upb.agripos.model.kontrak;

public interface Receiptable {
    String cetakStruk();
}
```
4. Cash.java
```java
package main.java.com.upb.agripos.model.pembayaran;

import main.java.com.upb.agripos.model.kontrak.Receiptable;

public class Cash extends Pembayaran implements Receiptable {
    private double tunai;

    public Cash(String invoiceNo, double total, double tunai) {
        super(invoiceNo, total);
        this.tunai = tunai;
    }

    @Override
    public double biaya() {
        return 0.0; // tidak ada biaya tambahan
    }

    @Override
    public boolean prosesPembayaran() {
        return tunai >= totalBayar(); // berhasil jika uang cukup
    }

    @Override
    public String cetakStruk() {
        if (prosesPembayaran()) {
            return String.format(
                "INVOICE %s | TOTAL: %.2f | CASH: %.2f | KEMBALI: %.2f | STATUS: BERHASIL",
                invoiceNo, totalBayar(), tunai, (tunai - totalBayar()));
        } else {
            return String.format(
                "INVOICE %s | TOTAL: %.2f | CASH: %.2f | STATUS: GAGAL (UANG KURANG)",
                invoiceNo, totalBayar(), tunai);
        }
    }
}
```
5. EWallet.java
```java
package main.java.com.upb.agripos.model.pembayaran;

import main.java.com.upb.agripos.model.kontrak.Validatable;
import main.java.com.upb.agripos.model.kontrak.Receiptable;

public class EWallet extends Pembayaran implements Validatable, Receiptable {
    private String akun;
    private String otp;

    public EWallet(String invoiceNo, double total, String akun, String otp) {
        super(invoiceNo, total);
        this.akun = akun;
        this.otp = otp;
    }

    @Override
    public double biaya() {
        return total * 0.015; // 1.5% biaya tambahan
    }

    @Override
    public boolean validasi() {
        return otp != null && otp.length() == 6;
    }

    @Override
    public boolean prosesPembayaran() {
        return validasi(); // berhasil jika OTP valid
    }

    @Override
    public String cetakStruk() {
        return String.format(
            "INVOICE %s | TOTAL+FEE: %.2f | E-WALLET: %s | STATUS: %s",
            invoiceNo, totalBayar(), akun, prosesPembayaran() ? "BERHASIL" : "GAGAL");
    }
}
```
6. TransferBank.java
```java
package main.java.com.upb.agripos.model.pembayaran;

import main.java.com.upb.agripos.model.kontrak.Validatable;
import main.java.com.upb.agripos.model.kontrak.Receiptable;

public class TransferBank extends Pembayaran implements Validatable, Receiptable {
    private String norek;
    private String pin;

    public TransferBank(String invoiceNo, double total, String norek, String pin) {
        super(invoiceNo, total);
        this.norek = norek;
        this.pin = pin;
    }

    @Override
    public double biaya() {
        return 3500; // biaya tetap
    }

    @Override
    public boolean validasi() {
        return pin != null && pin.length() == 6;
    }

    @Override
    public boolean prosesPembayaran() {
        return validasi();
    }

    @Override
    public String cetakStruk() {
        return String.format(
            "INVOICE %s | BANK: %s | TOTAL+FEE: %.2f | STATUS: %s",
            invoiceNo, norek, totalBayar(), prosesPembayaran() ? "BERHASIL" : "GAGAL");
    }
}
```
7. CreditBy.java
```java
package main.java.com.upb.agripos.util;

public class CreditBy {
    public static void print(String nim, String nama) {
        System.out.println("\n=======================================");
        System.out.println("Credit by: " + nim + " - " + nama);
        System.out.println("=======================================");
    }
}
```
---
8. MainAbstraction.java
```java
package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.pembayaran.*;
import main.java.com.upb.agripos.model.kontrak.*;
import main.java.com.upb.agripos.util.CreditBy;

public class MainAbstraction {
    public static void main(String[] args) {
        Pembayaran cash = new Cash("INV-001", 100000, 120000);
        Pembayaran ewallet = new EWallet("INV-002", 150000, "user@ewallet", "123456");
        Pembayaran transfer = new TransferBank("INV-003", 200000, "1234567890", "654321");

        System.out.println(((Receiptable) cash).cetakStruk());
        System.out.println(((Receiptable) ewallet).cetakStruk());
        System.out.println(((Receiptable) transfer).cetakStruk());

        CreditBy.print("240202835", "Febri Muhsinin");
    }
}
```

## Hasil Eksekusi
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](/praktikum/week5-abstraction-interface/screenshots/Screenshot%202025-11-06%20164021.png)
)
---

## Analisis
- Proses program:
Kelas Pembayaran menjadi blueprint untuk semua jenis pembayaran. Kelas turunan (Cash, EWallet, TransferBank) mengimplementasikan logika pembayaran sesuai jenisnya.
Interface Validatable dan Receiptable memungkinkan multiple inheritance — EWallet dan TransferBank menerapkan dua kemampuan sekaligus.

- Perbedaan minggu ini:
Minggu sebelumnya berfokus pada Polymorphism, sedangkan minggu ini menambahkan Abstraction dan Interface untuk membangun struktur kode yang lebih fleksibel dan reusable.

- Kendala:
Awalnya terjadi error karena salah import package interface (kontrak). Solusi: menyesuaikan struktur folder dan package agar sesuai path com.upb.agripos.model.kontrak.

---

## Kesimpulan
Dengan menggunakan abstract class dan interface, program menjadi lebih modular dan mudah diperluas.
Konsep abstraksi membantu memisahkan apa yang dilakukan dari bagaimana cara melakukannya, sehingga kode lebih bersih dan mudah dikelola.
Implementasi multiple inheritance melalui interface juga meningkatkan fleksibilitas desain sistem.
---

## Quiz
1. Jelaskan perbedaan konsep dan penggunaan abstract class dan interface.
Jawaban:
Abstract class dapat berisi field dan method konkrit, sementara interface hanya berisi kontrak method tanpa implementasi. Abstract class digunakan untuk hierarki dengan perilaku dasar yang sama, sedangkan interface digunakan untuk mendefinisikan kemampuan lintas hierarki.

2. Mengapa multiple inheritance lebih aman dilakukan dengan interface pada Java?
Jawaban:
Karena interface tidak membawa state (data), sehingga tidak menimbulkan konflik pewarisan (diamond problem) seperti yang dapat terjadi bila mewarisi beberapa class sekaligus.

3. Pada contoh Agri-POS, bagian mana yang paling tepat menjadi abstract class dan mana yang menjadi interface? Jelaskan alasannya.
Jawaban:
Abstract class: Pembayaran — karena memiliki field dan method dasar yang sama untuk semua jenis pembayaran.
Interface: Validatable dan Receiptable — karena merepresentasikan kemampuan tambahan (validasi OTP, cetak struk) yang bisa dimiliki oleh berbagai jenis pembayaran tanpa tergantung hierarki.
