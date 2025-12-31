# Laporan Praktikum Minggu 6 (sesuaikan minggu ke berapa?)
Topik: [Desain Arsitektur Sistem dengan UML dan Prinsip SOLID]

## Identitas
- Nama  : [Febri Muhsinin]
- NIM   : [240202835]
- Kelas : [3IKRA]

---

## Tujuan
1. Mahasiswa mampu mengidentifikasi kebutuhan sistem ke dalam diagram UML.

2. Mahasiswa mampu menggambar UML Class Diagram dengan relasi antar class yang tepat.

3. Mahasiswa mampu menjelaskan prinsip desain OOP (SOLID).

4. Mahasiswa mampu menerapkan minimal dua prinsip SOLID dalam kode program.

---

## Dasar Teori
1. UML (Unified Modeling Language) digunakan untuk mendokumentasikan desain sistem, mencakup Use Case, Activity, Sequence, dan Class Diagram.

2. Prinsip SOLID (Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion) adalah standar desain untuk menciptakan kode yang maintainable.

3. Open/Closed Principle menekankan bahwa entitas perangkat lunak harus terbuka untuk ekstensi tetapi tertutup untuk modifikasi.

4. Dependency Inversion Principle mengarahkan agar modul tingkat tinggi tidak bergantung pada modul tingkat rendah, melainkan keduanya bergantung pada abstraksi.

---

## Langkah Praktikum
1. Pemetaan Kebutuhan: Mengidentifikasi aktor (Kasir, Admin) dan daftar use case berdasarkan kebutuhan fungsional sistem Agri-POS.

2. Perancangan Alur: Membuat Activity Diagram untuk proses Checkout dan Sequence Diagram untuk interaksi pembayaran.

3. Desain Struktur: Menyusun Class Diagram yang mengintegrasikan kode Produk (Minggu 2-4) dan sistem Pembayaran (Minggu 5).

4. Analisis SOLID: Memetakan keterhubungan antar kelas dengan prinsip desain SOLID agar sistem mudah diperluas di masa depan.
---


## Sistem Agri-POS
1. Use Case Diagram
![Screenshot hasil](/oop-202501-240202835/praktikum/week6-uml-solid/screenshots/uml_usecase.jpeg)

Diagram ini mendefinisikan siapa saja aktor yang berinteraksi dengan sistem dan apa saja fungsionalitas yang mereka miliki.

1. Aktor: Terdiri dari Kasir dan Admin.

2. Fungsi Kasir: Melakukan Transaksi Penjualan (Checkout) dan Login.

3. Fungsi Admin: Melakukan Kelola Produk (CRUD), Lihat Laporan Penjualan, dan Login.

4. Relasi Include: Use case "Transaksi Penjualan" secara otomatis mencakup (include) proses Pilih Metode Pembayaran dan Cetak Struk.

2. Activity Diagram (Proses Checkout)
![Screenshot hasil](/oop-202501-240202835/praktikum/week6-uml-solid/screenshots/uml_activity.jpeg)

Diagram ini menggambarkan alur kerja langkah-demi-langkah dari proses utama penjualan.

- Awal Proses: Kasir memasukkan data produk dan jumlahnya.

- Validasi Stok: Sistem melakukan pengecekan stok; jika stok habis, muncul Peringatan Stok Habis. Jika tersedia, sistem menghitung total belanja.

- Logika Pembayaran:

   - Untuk metode E-Wallet/Transfer, sistem melakukan Proses Validasi (OTP/PIN).

   - Untuk metode Tunai, sistem melakukan Cek Kecukupan Uang.

- Penyelesaian: Jika pembayaran berhasil, sistem akan Update Stok, Generate Invoice, dan Cetak Struk. Jika gagal, sistem menampilkan pesan gagal.

3. Sequence Diagram (Proses Pembayaran)
![Screenshot hasil](/oop-202501-240202835/praktikum/week6-uml-solid/screenshots/uml_sequence.jpeg)

Diagram ini menunjukkan interaksi antar objek secara kronologis untuk menjalankan fungsi pembayaran.

- Interaksi Objek: Melibatkan objek Kasir, PaymentService, p:Pembayaran, Validatable, dan Receiptable.

- Alur Pesan:

   - Kasir memicu prosesTransaksi pada PaymentService.

   - Terdapat blok Alt (Alternatif): Jika pembayaran butuh validasi, PaymentService akan memanggil metode validasi() pada objek yang mengimplementasikan Validatable.

   - PaymentService kemudian memanggil prosesPembayaran() pada objek Pembayaran.

   - Jika sukses, sistem memanggil cetakStruk() pada objek yang mengimplementasikan Receiptable untuk ditampilkan kepada Kasir.

4. Class Diagram
![Screenshot hasil](/oop-202501-240202835/praktikum/week6-uml-solid/screenshots/uml_class.jpeg)

Diagram ini menjelaskan struktur statis, paket, dan hubungan antar kelas dalam kode program Anda.

- Struktur Paket: Dibagi menjadi tiga paket utama: kontrak, service, dan model.

- Konsep Pewarisan (Inheritance):

   - Produk adalah kelas abstrak yang diwarisi oleh Benih dan Pupuk.

   - Pembayaran adalah kelas abstrak yang diwarisi oleh EWallet dan Cash.

- Implementasi Interface:

   - EWallet mengimplementasikan interface Validatable dan Receiptable.

   - Cash hanya mengimplementasikan Receiptable.

- Prinsip SOLID:

   - Dependency Inversion: PaymentService bergantung pada abstraksi Pembayaran, bukan pada kelas konkret seperti Cash atau EWallet.

   - Interface Segregation: Interface dipisah menjadi Validatable dan Receiptable agar kelas hanya mengimplementasikan apa yang mereka butuhkan.
---

## AnalisisPenerapan Prinsip SOLID
Penerapan Prinsip SOLID
Desain arsitektur Agri-POS ini telah menerapkan prinsip SOLID sebagai berikut:

- S – Single Responsibility Principle: Setiap kelas memiliki tanggung jawab tunggal; kelas Produk mengelola data barang, sementara kelas Pembayaran mengelola logika transaksi.

- O – Open/Closed Principle: Sistem terbuka untuk pengembangan tetapi tertutup untuk modifikasi. Metode pembayaran baru dapat ditambahkan hanya dengan membuat subclass baru dari Pembayaran tanpa mengubah kode pada PaymentService.

- L – Liskov Substitution Principle: Subclass seperti Cash dan EWallet dapat menggantikan referensi superclass Pembayaran tanpa merusak fungsionalitas sistem.

- I – Interface Segregation Principle: Interface dipisahkan berdasarkan kebutuhan. Kelas Cash hanya mengimplementasikan Receiptable dan tidak dipaksa mengimplementasikan Validatable yang tidak relevan baginya.

- D – Dependency Inversion Principle: Kelas tingkat tinggi (PaymentService) bergantung pada abstraksi (Pembayaran dan Interface), bukan pada implementasi konkret.
---

## Kesimpulan
Penggunaan UML membantu dalam memvisualisasikan struktur sistem yang kompleks menjadi lebih terorganisir. Penerapan prinsip SOLID memastikan bahwa kode Agri-POS bersifat maintainable dan mudah dikembangkan di masa depan (misalnya saat penambahan modul gudang atau diskon).

---

## Quiz
1. Jelaskan perbedaan aggregation dan composition serta contohnya.

   - Aggregation: Hubungan "memiliki" yang lemah (objek bagian bisa ada tanpa objek induk). Contoh: Toko memiliki Produk; jika toko tutup, data produk tetap ada.

   - Composition: Hubungan "memiliki" yang kuat (objek bagian tidak bisa ada tanpa induk). Contoh: Transaksi memiliki DetailTransaksi; jika transaksi dihapus, detailnya ikut hilang.

2. Bagaimana prinsip Open/Closed memastikan sistem mudah dikembangkan?

   - Dengan menggunakan inheritance dan interface, pengembang cukup menambah modul baru (subclass/implementasi) tanpa menyentuh kode inti yang sudah stabil, sehingga meminimalisir risiko munculnya bug baru.

3. Mengapa Dependency Inversion Principle (DIP) meningkatkan testability?

   - Karena kelas tidak bergantung pada kelas konkret, kita dapat melakukan mocking atau mengganti objek asli dengan objek simulasi saat pengujian (unit testing) tanpa mengubah logika internal kelas tersebut.
