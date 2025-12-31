# Laporan Praktikum Minggu 7 
Topik: [Collections dan Implementasi Keranjang Belanja]

## Identitas
- Nama  : [Febri Muhsinin]
- NIM   : [240202835]
- Kelas : [3IKRA]

---

## Tujuan
1. Mengenal konsep collection dalam Java (List, Map, Set).

2. Mengimplementasikan ArrayList untuk menyimpan objek produk secara dinamis.

3. Menggunakan HashMap untuk menangani kuantitas produk dalam keranjang belanja.

4. Menganalisis efisiensi penggunaan struktur data dalam aplikasi POS.

---

## Dasar Teori
1. List (ArrayList): Struktur data terurut yang mengizinkan elemen duplikat. Berguna untuk mencatat urutan transaksi item per item.

2. Map (HashMap): Menyimpan data dalam pasangan key-value. Sangat efektif untuk sistem POS yang membutuhkan akumulasi jumlah (quantity) barang yang sama.

3. Set (HashSet): Struktur data yang menjamin keunikan elemen (tidak ada duplikat).

---

## Langkah Praktikum
1. Membuat direktori kerja di praktikum/week7-collections/.

2. Membuat kelas Product sebagai entitas dasar barang pertanian.

3. Mengimplementasikan ShoppingCart dengan ArrayList untuk operasi tambah dan hapus sederhana.

4. Mengimplementasikan ShoppingCartMap untuk menangani logika quantity menggunakan HashMap.

5. Menjalankan MainCart untuk memverifikasi fungsionalitas keranjang belanja.

---

## Kode Program 
A. Product.java
```java
package com.upb.agripos;

public class Product {
    private final String code;
    private final String name;
    private final double price;

    public Product(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    // Override untuk mendukung penggunaan HashMap secara akurat
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return code.equals(product.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
```
B. ShoppingCart.java (Versi ArrayList)
```java
package com.upb.agripos;

import java.util.ArrayList;

public class ShoppingCart {
    private final ArrayList<Product> items = new ArrayList<>();

    public void addProduct(Product p) { items.add(p); }
    public void removeProduct(Product p) { items.remove(p); }

    public double getTotal() {
        double sum = 0;
        for (Product p : items) {
            sum += p.getPrice();
        }
        return sum;
    }

    public void printCart() {
        System.out.println("=== Isi Keranjang (ArrayList) ===");
        for (Product p : items) {
            System.out.println("- " + p.getCode() + " " + p.getName() + " = Rp" + p.getPrice());
        }
        System.out.println("Total: Rp" + getTotal());
    }
}
```
C. ShoppingCartMap.java (Versi HashMap dengan Quantity)
```java
package com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartMap {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product p) { 
        items.put(p, items.getOrDefault(p, 0) + 1); 
    }

    public void removeProduct(Product p) {
        if (!items.containsKey(p)) return;
        int qty = items.get(p);
        if (qty > 1) items.put(p, qty - 1);
        else items.remove(p);
    }

    public double getTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void printCart() {
        System.out.println("=== Isi Keranjang (Map dengan Quantity) ===");
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            System.out.println("- " + e.getKey().getCode() + " " + e.getKey().getName() + 
                               " [" + e.getValue() + " pcs] = Rp" + (e.getKey().getPrice() * e.getValue()));
        }
        System.out.println("Total: Rp" + getTotal());
    }
}
```
D. MainCart.java
```java
package com.upb.agripos;

public class MainCart {
    public static void main(String[] args) {
        // Menggunakan identitas Febri Muhsinin - 240202835
        System.out.println("Hello, I am Febri Muhsinin-240202835 (Week7)");

        Product p1 = new Product("P01", "Beras Rojolele", 50000);
        Product p2 = new Product("P02", "Pupuk NPK 1kg", 30000);

        // Uji coba ArrayList
        System.out.println("\n[Testing ArrayList Implementation]");
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(p1);
        cart.addProduct(p2);
        cart.printCart();
        cart.removeProduct(p1);
        cart.printCart();

        // Uji coba Map (Quantity)
        System.out.println("\n[Testing Map Implementation]");
        ShoppingCartMap mapCart = new ShoppingCartMap();
        mapCart.addProduct(p1);
        mapCart.addProduct(p1); // Tambah item yang sama
        mapCart.addProduct(p2);
        mapCart.printCart();
    }
}
```
---

## Hasil Eksekusi

![Screenshot hasil](/praktikum/week7-koleksi-keranjang/screenshots/Screenshot%202025-12-31%20160556.png)

---

## Analisis
- ArrayList: Cocok digunakan ketika urutan penambahan produk sangat penting dan kita tidak keberatan jika ada objek yang sama muncul berkali-kali dalam list. Namun, untuk menghitung total dengan jumlah barang yang banyak, List akan menjadi panjang dan kurang efisien dalam representasi data.

- HashMap: Lebih efisien untuk sistem POS karena jika pengguna membeli 5 beras, sistem hanya menyimpan 1 kunci "Beras" dengan nilai kuantitas 5, alih-alih menyimpan 5 objek "Beras" yang identik. 

---

## Kesimpulan
Penggunaan Java Collections memungkinkan sistem Agri-POS mengelola data belanja secara dinamis tanpa batasan ukuran array statis. Pemilihan antara List dan Map bergantung pada kebutuhan bisnis; Map lebih unggul dalam menangani kuantitas barang secara ringkas.

---

## Quiz
1. Perbedaan List, Map, Set: List terurut dan boleh duplikat; Map berbasis pasangan kunci-nilai; Set unik dan tidak terurut.

2. ArrayList untuk Keranjang: Karena ArrayList mempertahankan urutan penambahan produk (insertion order) dan mudah diakses menggunakan perulangan (for-each).

3. Set mencegah duplikasi: Set menggunakan mekanisme hashCode() dan equals() untuk mengecek apakah objek sudah ada sebelum dimasukkan.

4. Kapan menggunakan Map: Saat kita butuh fitur kuantitas atau pencarian cepat berdasarkan kunci tertentu. Contoh: Menghitung jumlah stok per kategori produk.
