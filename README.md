# Sistem Pengelolaan Pengaduan Masyarakat

**Oleh Aulia Ashylla Ananda Putri Hariawan (2509116076)**

## 1. Deskripsi Singkat Program

Sistem Pengelolaan Pengaduan Masyarakat merupakan program yang digunakan untuk mencatat dan mengelola data pengaduan yang disampaikan oleh masyarakat. Program ini dapat digunakan untuk menangani berbagai jenis laporan, seperti fasilitas umum, kebersihan, keamanan, jalan, dan pelayanan. Setiap data pengaduan memiliki informasi berupa ID Pengaduan, Nama Pelapor, Jenis Pengaduan, Isi Pengaduan, Tanggal Pengaduan, Tingkat Urgensi, dan Status Pengaduan. ID pengaduan dibuat secara otomatis oleh sistem dengan format `P001`, `P002`, dan seterusnya. Jenis pengaduan dipilih melalui kategori yang telah disediakan sehingga data yang dimasukkan lebih terstruktur.

Program membedakan pengaduan menjadi dua jenis berdasarkan tingkat urgensinya, yaitu **pengaduan biasa** dan **pengaduan darurat**. Pengaduan biasa memiliki target penyelesaian standar 7 hari, sedangkan pengaduan darurat memiliki target respons awal standar 24 jam dan menyimpan kontak darurat pelapor. Pengaduan darurat juga memiliki penanganan khusus berupa pengiriman notifikasi darurat kepada kontak yang diberikan pelapor.

Pengguna dapat menjalankan beberapa fitur utama melalui menu interaktif, yaitu Tambah Pengaduan, Lihat Pengaduan, Ubah Status Pengaduan, Hapus Pengaduan, dan Keluar. Pada fitur Lihat Pengaduan, pengguna dapat memilih lima cara untuk melihat data, yaitu melihat seluruh pengaduan, berdasarkan ID, berdasarkan status, berdasarkan jenis, atau berdasarkan jenis dan status.

Pada pengembangan ini, data pengaduan tidak lagi disimpan di `ArrayList`, tetapi di **database MySQL** (`db_pengaduan`) menggunakan **JDBC (PreparedStatement) dan DAO**, sehingga data tetap tersimpan walaupun program ditutup. Data awal (dummy) diisi melalui file **`sql/db_pengaduan.sql`**, bukan melalui kode. Sebagai nilai tambah, program juga menerapkan **framework ORM (Hibernate/JPA)**.

### Tujuan Program

Program ini dibuat sebagai penerapan konsep Pemrograman Berorientasi Objek melalui sebuah sistem pengelolaan pengaduan sederhana.

Tujuan program adalah:

* Mencatat data pengaduan masyarakat secara terstruktur.
* Mengelompokkan pengaduan berdasarkan jenis dan tingkat urgensi.
* Menampilkan data pengaduan yang tersimpan.
* Mencari data pengaduan berdasarkan beberapa kriteria.
* Mengubah status pengaduan berdasarkan tahapan penanganan.
* Menghapus data pengaduan berdasarkan ID.
---

## 2. Penjelasan Struktur Package

Program terdiri dari beberapa class yang dikelompokkan berdasarkan fungsinya.

| Package      | Class                           | Peran                                                                                                                                                                                              |
| ------------ | ------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `main`       | `PengelolaanPengaduanMasyarakt` | Menjadi *entry point* program. Memeriksa koneksi database, membuat View, DAO, dan Controller, lalu memulai proses pengelolaan sistem melalui controller.                                           |
| `controller` | `PengelolaDataPengaduan`        | Mengelola data pengaduan melalui DAO, menjalankan proses tambah, lihat, cari, ubah status, hapus, dan menghasilkan ID otomatis.                                                                    |
| `controller` | `ValidasiInput`                 | Menangani validasi berbagai input pengguna agar data sesuai dengan aturan yang ditentukan.                                                                                                         |
| `config`     | `Koneksi`                       | Menyimpan URL, user, dan password database serta membuka koneksi JDBC ke MySQL.                                                                                                                    |
| `config`     | `TestKoneksi`                   | Program kecil untuk menguji koneksi JDBC ke database.                                                                                                                                              |
| `config`     | `JPAUtil`                       | Membuat `EntityManager` untuk ORM dari konfigurasi `persistence.xml`.                                                                                                                              |
| `config`     | `TestORM`                       | Program kecil untuk menguji konfigurasi dan mapping ORM.                                                                                                                                           |
| `dao`        | `PengaduanDAO`                  | Interface yang menjadi kontrak akses data pengaduan ke database (tambah, baca, ubah, hapus).                                                                                                       |
| `dao`        | `PengaduanJdbcDAO`              | Implementasi DAO dengan JDBC. Semua query SQL ditulis di class ini dan dijalankan dengan `PreparedStatement`.                                                                                       |
| `dao`        | `PengaduanOrmDAO`               | Implementasi DAO dengan ORM. Mewarisi `PengaduanJdbcDAO`, lalu mengganti operasi simpan, cari ID, ubah, dan hapus dengan ORM.                                                                        |
| `model`      | `Pengaduan`                     | Menjadi abstract superclass yang menyimpan atribut, method umum, serta method abstract untuk tingkat urgensi pengaduan. Dipetakan ke tabel `pengaduan` oleh ORM.                                    |
| `model`      | `PengaduanBiasa`                | Subclass dari `Pengaduan` untuk data pengaduan dengan tingkat urgensi biasa dan target penyelesaian standar 7 hari.                                                                                |
| `model`      | `PengaduanDarurat`              | Subclass dari `Pengaduan` untuk data pengaduan dengan tingkat urgensi darurat, kontak darurat, dan target respons awal 24 jam.                                                                     |
| `model`      | `PenangananKhusus`              | Interface yang menjadi kontrak bagi pengaduan yang membutuhkan penanganan khusus, seperti pengaduan darurat.                                                                                       |
| `view`       | `PengaduanView`                 | Mengatur tampilan program pada terminal, seperti menu, judul, pilihan, informasi, pesan sukses, dan pesan error.                                                                                   |

File di luar kode Java:

| File | Peran |
| ---- | ----- |
| `src/main/resources/META-INF/persistence.xml` | Konfigurasi ORM: daftar entity dan informasi koneksi database. |
| `pom.xml` | Dependency `mysql-connector-j` (driver JDBC) dan `hibernate-core` (ORM). |

### Struktur package

```text
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   ├── main/
        │   │   └── PengelolaanPengaduanMasyarakt.java
        │   │
        │   ├── controller/
        │   │   ├── PengelolaDataPengaduan.java
        │   │   └── ValidasiInput.java
        │   │
        │   ├── config/
        │   │   ├── Koneksi.java
        │   │   ├── TestKoneksi.java
        │   │   ├── JPAUtil.java
        │   │   └── TestORM.java
        │   │
        │   ├── dao/
        │   │   ├── PengaduanDAO.java
        │   │   ├── PengaduanJdbcDAO.java
        │   │   └── PengaduanOrmDAO.java
        │   │
        │   ├── model/
        │   │   ├── Pengaduan.java
        │   │   ├── PengaduanBiasa.java
        │   │   ├── PengaduanDarurat.java
        │   │   └── PenangananKhusus.java
        │   │
        │   └── view/
        │       └── PengaduanView.java
        │
        └── resources/
            └── META-INF/
                └── persistence.xml
```

<img width="239" height="398" alt="image" src="https://github.com/user-attachments/assets/8ed1118f-fe93-4272-8676-ce12a6a6e439" />          

*Gambar 1: Struktur package program dengan penambahan package `config` dan `dao`.*

---

## 3. Penjelasan Alur Program

### 3.1 Menu Program

Menu utama yang tersedia pada sistem adalah:

<img width="280" height="119" alt="image" src="https://github.com/user-attachments/assets/f8c819d5-7c75-4229-99bd-28b323aa2432" />

*Gambar 2: Tampilan menu utama Sistem Pengelolaan Pengaduan Masyarakat sebagai pusat navigasi seluruh fitur program.*

Menu tersebut digunakan sebagai pusat navigasi program. Setelah pengguna menyelesaikan suatu proses, program akan kembali ke menu utama selama pengguna belum memilih menu Keluar.

---

### 3.2 Alur Sistem

Secara umum, sistem dimulai dengan memeriksa koneksi ke database. Jika koneksi berhasil, program membuat View, DAO, dan Controller. Data awal sudah tersedia di database dari file `db_pengaduan.sql`, sehingga tidak ada data yang dimasukkan lewat kode. Pengguna kemudian dapat memilih fitur yang tersedia melalui menu utama. Alur sistem tersebut menunjukkan bahwa setiap fitur bekerja melalui menu utama dan setelah proses selesai pengguna kembali ke menu. Program akan berhenti ketika pilihan menu bernilai `5`. Jika koneksi database gagal, program menampilkan pesan `Gagal terhubung ke Database. Program dihentikan.` lalu berhenti.

```text
                          ┌──────────────┐
                          │    MULAI     │
                          └──────┬───────┘
                                 │
                                 ▼
                        Cek Koneksi Database
                       (gagal → pesan, selesai)
                                 │
                                 ▼
               Inisialisasi View, DAO, dan Controller
                                 │
           ┌─────────────────────┴─────────────────────┐
           │                                           │
           ▼                                           │
┌────────────────────┐                                 │
│   Tampilkan Menu   │◄────────────────────────────┐   │
└─────────┬──────────┘                             │   │
          │                                        │   │
          ▼                                        │   │
┌────────────────────┐                             │   │
│   Input Pilihan    │                             │   │
│        Menu        │                             │   │
└─────────┬──────────┘                             │   │
          │                                        │   │
          ▼                                        │   │
  ┌───────────────┐                                │   │
  │ Pilihan Menu? │                                │   │
  └───────┬───────┘                                │   │
          │                                        │   │
  ┌───────┼─────────────┬──────────────┬───────────┤   │
  │       │             │              │           │   │
  ▼       ▼             ▼              ▼           ▼   │
 [1]     [2]           [3]            [4]         [5]  │
Tambah  Lihat          Ubah          Hapus       Keluar│
  │       │             │              │           │   │
  ▼       ▼             ▼              ▼           ▼   │
Proses  Proses        Proses        Proses      Selesai│
  │       │             │              │               │
  └───────┴──────┬──────┴──────────────┘               │
                 │                                     │
                 ▼                                     │
          Kembali ke Menu ─────────────────────────────┘
```

---

### 3.3 Alur Tambah Pengaduan

Fitur **Tambah Pengaduan** digunakan untuk membuat data pengaduan baru.

Urutan prosesnya adalah:

```text
Pilih Menu Tambah Pengaduan
                         │
                         ▼
                Generate ID Otomatis
                         │
                         ▼
                Input Nama Pelapor
                         │
                         ▼
              Pilih Jenis Pengaduan
                         │
                         ▼
                Input Isi Pengaduan
                         │
                         ▼
              Input Tanggal Pengaduan
                         │
                         ▼
              Pilih Tingkat Urgensi?
                  ┌──────┴──────┐
                  │             │
                  ▼             ▼
                Biasa        Darurat
                  │             │
                  ▼             ▼
             Buat Object   Input Kontak
            PengaduanBiasa   Darurat
                  │             │
                  │             ▼
                  │        Buat Object
                  │      PengaduanDarurat
                  │             │
                  └──────┬──────┘
                         │
                         ▼
              Simpan ke Database (DAO)
                         │
                         ▼
          Baca ulang data dari Database
                         │
                         ▼
                Tampilkan Berhasil
                         │
                         ▼
                  Kembali ke Menu
```

ID tidak dimasukkan secara manual oleh pengguna. Sistem menghasilkan ID berdasarkan ID yang belum digunakan di database, misalnya `P001`, `P002`, dan seterusnya.

Jenis pengaduan juga tidak dimasukkan sebagai teks bebas. Pengguna memilih salah satu dari lima kategori yang disediakan, yaitu:

```text
1. Fasilitas Umum
2. Kebersihan
3. Keamanan
4. Jalan
5. Pelayanan
```

Pada tahap berikutnya pengguna memilih tingkat urgensi:

```text
1. Biasa
2. Darurat
```

Pilihan tersebut menentukan object subclass yang dibuat. Pengaduan biasa akan menghasilkan object `PengaduanBiasa`, sedangkan pengaduan darurat akan menghasilkan object `PengaduanDarurat`.

Object tersebut kemudian disimpan ke database melalui DAO. Setelah itu, data dibaca ulang dari database untuk memastikan pengaduan benar-benar tersimpan. Jika data tidak ditemukan, program menampilkan pesan `Pengaduan gagal disimpan ke database.`

Pada pengaduan darurat, pengguna juga diminta memasukkan kontak darurat. Setelah data berhasil ditambahkan, sistem akan memeriksa apakah object tersebut menerapkan interface `PenangananKhusus`. Jika memenuhi interface tersebut, sistem akan menjalankan method `kirimNotifikasiDarurat()` untuk memberikan notifikasi bahwa pengaduan membutuhkan respons khusus.

### Bukti output proses tambah pengaduan biasa

<img width="334" height="298" alt="image" src="https://github.com/user-attachments/assets/dfbd73e3-e3bb-42e5-a5b6-16b4828c6dbb" />

*Gambar 3: Proses penambahan pengaduan biasa, mulai dari ID otomatis, pengisian data pengaduan, pemilihan jenis dan urgensi, hingga data berhasil disimpan.*

### Bukti output proses tambah pengaduan darurat

<img width="350" height="380" alt="image" src="https://github.com/user-attachments/assets/f90ce3f7-ff67-4651-9faf-64b95233e9c9" />

*Gambar 4: Proses penambahan pengaduan darurat yang menghasilkan object `PengaduanDarurat`, meminta input kontak darurat pelapor, serta menjalankan notifikasi penanganan khusus.*

---

### 3.4 Alur Lihat Pengaduan

Fitur **Lihat Pengaduan** digunakan untuk menampilkan atau mencari data pengaduan yang tersimpan di database.

<img width="267" height="135" alt="image" src="https://github.com/user-attachments/assets/d28321b4-959c-4002-b9d6-8d52f9a59487" />

*Gambar 5: Tampilan fitur lima cara Lihat Pengaduan*

Pada versi program ini, pengguna dapat memilih **lima cara** untuk melihat data pengaduan.

<img width="464" height="283" alt="image" src="https://github.com/user-attachments/assets/05dbb5a0-5465-4e98-bd92-143d5d8dfaa0" />

*Gambar 6: Tampilan data pengaduan pada fitur Lihat Pengaduan, termasuk data awal dari database yang langsung tersedia sejak program dijalankan.*

Data awal telah dimasukkan ke database melalui file `db_pengaduan.sql`. Oleh karena itu, ketika program pertama kali menjalankan menu Lihat Pengaduan, data sudah langsung tersedia tanpa harus melakukan proses tambah terlebih dahulu. Data tersebut dibaca dari database melalui DAO, bukan dari kode program.

```text
Pilih Menu Lihat Pengaduan
          ↓
Tampilkan 5 pilihan cara melihat data
          ↓
Pengguna memilih metode pencarian
          │
          ├── 1 → Tampilkan seluruh pengaduan
          │
          ├── 2 → Cari berdasarkan ID
          │
          ├── 3 → Cari berdasarkan Status
          │
          ├── 4 → Cari berdasarkan Jenis
          │
          └── 5 → Cari berdasarkan Jenis dan Status
                         ↓
          Controller memanggil DAO, DAO mengambil data dari Database
                         ↓
                  Tampilkan hasil
                         ↓
                  Kembali ke Menu
```

Untuk pencarian berdasarkan ID, sistem menghasilkan satu object `Pengaduan` apabila data ditemukan. Sedangkan pencarian berdasarkan status, jenis, atau jenis dan status dapat menghasilkan lebih dari satu data sehingga hasilnya berupa `ArrayList<Pengaduan>` yang dibuat dari baris hasil query.

Pencarian berdasarkan jenis dan status menggunakan method `cariPengaduan()` dengan parameter yang berbeda dari pencarian berdasarkan ID. Penerapan tersebut menjadi salah satu bentuk **method overloading** pada program.

---

### 3.5 Alur Ubah Status Pengaduan

Fitur **Ubah Status Pengaduan** menggunakan alur status bertahap agar perubahan status tidak dapat dilakukan secara acak.

<img width="377" height="242" alt="image" src="https://github.com/user-attachments/assets/bac2cd7f-d218-4dde-9450-e6c6bf2493e8" />

*Gambar 7: Proses perubahan status pengaduan berdasarkan ID dengan konfirmasi pengguna sebelum status diperbarui.*

Urutan status adalah:

```text
Menunggu Konfirmasi Petugas
             ↓
       Sedang Diproses
             ↓
   Selesai Ditindaklanjuti
```

Pengguna memasukkan ID pengaduan yang akan diubah. Sistem kemudian mencari data berdasarkan ID tersebut di database. Setelah data ditemukan, sistem menentukan status berikutnya berdasarkan status saat ini. Jika pengguna mengonfirmasi, status baru disimpan ke database.

Apabila pengaduan sudah berada pada status `Selesai Ditindaklanjuti`, status tidak dapat diubah lagi. Apabila ID tidak ditemukan, program menampilkan pesan error dan kembali ke menu.

```text
Pilih Ubah Status
          │
          ▼
  Input ID Pengaduan
          │
          ▼
Cari Data di Database berdasarkan ID
          │
          ▼
   Data ditemukan?
     ┌────┴────┐
   Tidak       Ya
     │          │
     │          ▼
     │   Tentukan Status Berikutnya
     │          │
     │          ▼
     │   Sudah tahap terakhir?
     │     ┌────┴────┐
     │    Ya       Tidak
     │     │          │
     │     │          ▼
     │     │   Konfirmasi perubahan?
     │     │     ┌──────┴──────┐
     │     │    'y'           'n'
     │     │     │             │
     │     │     ▼             ▼
     │     │ Update Status   Batal
     │     │ di Database       │
     │     │     │             │
     ▼     ▼     ▼             ▼
   Error Info  Sukses       Informasi
     │     │     │             │
     └─────┴──┬──┴─────────────┘
              │
              ▼
       Kembali ke Menu
```

---

### 3.6 Alur Hapus Pengaduan

Fitur **Hapus Pengaduan** digunakan untuk menghapus data berdasarkan ID.

<img width="328" height="239" alt="image" src="https://github.com/user-attachments/assets/11717486-9d2c-4af5-b98a-645641ac31d8" />

*Gambar 8: Proses penghapusan data pengaduan melalui pencarian ID dan konfirmasi pengguna sebelum data dihapus.*

Sebelum data benar-benar dihapus, sistem menampilkan ringkasan data dan meminta konfirmasi. Hal tersebut mencegah penghapusan dilakukan secara langsung tanpa persetujuan pengguna. Jika pengguna mengonfirmasi, data dihapus dari database. Apabila ID tidak ditemukan, program menampilkan pesan error dan kembali ke menu.

```text
Pilih Hapus Pengaduan
          │
          ▼
  Input ID Pengaduan
          │
          ▼
Cari Pengaduan di Database
          │
          ▼
   Data ditemukan?
     ┌────┴────┐
   Tidak       Ya
     │          │
     │          ▼
     │   Tampilkan Ringkasan Data
     │          │
     │          ▼
     │   Konfirmasi (y/n)?
     │     ┌─────┴─────┐
     │    'y'         'n'
     │     │           │
     │     ▼           ▼
     │   Hapus dari  Batal
     │   Database      │
     │     │           │
     ▼     ▼           ▼
   Error Sukses    Informasi
     │     │           │
     └─────┴─────┬─────┘
                 │
                 ▼
          Kembali ke Menu
```

---

### 3.7 Alur Keluar Program

Ketika pengguna memilih menu `5`, program menampilkan pesan penutup dan mengakhiri perulangan menu.

<img width="380" height="170" alt="image" src="https://github.com/user-attachments/assets/e3a15630-7d23-4331-92f8-e794194d1c5a" />

*Gambar 9: Tampilan ketika pengguna memilih menu Keluar dan program mengakhiri proses.*

---

## 4. Penjelasan Penerapan Encapsulation dan Inheritance

### 4.1 Encapsulation

**Encapsulation** diterapkan dengan menyembunyikan data internal object melalui atribut `private` dan menyediakan method `getter` serta `setter` sesuai kebutuhan.

Pada class `Pengaduan`, atribut utama menggunakan `private`:

<img width="455" height="98" alt="image" src="https://github.com/user-attachments/assets/cb3e1b40-8315-42c0-905c-2c54197b9f0f" />

*Gambar 10: Penerapan encapsulation pada Class Pengaduan dengan access modifier*

Atribut tersebut tidak dapat diakses secara langsung dari class lain. Akses terhadap data dilakukan melalui method yang telah disediakan.

<img width="526" height="143" alt="image" src="https://github.com/user-attachments/assets/c8f08e32-85c0-4df3-a6bc-b0eb24199b00" />

*Gambar 11: Penerapan encapsulation melalui method `getter` dan `setter` pada class `Pengaduan`.*

Atribut `idPengaduan` tidak memiliki `setter`. Hal tersebut dilakukan karena ID dibuat secara otomatis oleh sistem dan digunakan sebagai identitas pengaduan sehingga tidak diubah melalui setter.

Encapsulation juga diterapkan pada atribut tambahan di subclass, seperti `targetSelesaiHari` pada `PengaduanBiasa` serta `kontakDarurat` dan `batasResponJam` pada `PengaduanDarurat`.

---

### 4.2 Inheritance

**Inheritance** digunakan dengan membuat class `Pengaduan` sebagai superclass yang memiliki atribut dan method umum untuk seluruh jenis pengaduan.

Dua subclass mewarisi class tersebut:

```text
                    Pengaduan
                   (superclass)
                   /          \
                  /            \
                 ▼              ▼
       PengaduanBiasa     PengaduanDarurat
        (subclass)          (subclass)
```

Penerapan inheritance terlihat pada deklarasi:

<img width="380" height="41" alt="image" src="https://github.com/user-attachments/assets/c213bfa3-616b-4fd2-9800-95fa2d94c1f6" />

*Gambar 12: Penerapan inheritance pada subclass PengaduanBiasa*

dan:

<img width="470" height="52" alt="image" src="https://github.com/user-attachments/assets/413be313-43d1-40b8-849b-813449c8b29a" />

*Gambar 13: Penerapan inheritance pada subclass PengaduanDarurat*

Pada constructor subclass digunakan `super()` untuk menginisialisasi atribut yang berasal dari superclass.

Contoh:

<img width="556" height="115" alt="image" src="https://github.com/user-attachments/assets/cbf53c77-af77-4ed7-bdb3-b739e0842dae" />

*Gambar 14: Penggunaan `super()` pada constructor subclass untuk memanggil constructor superclass `Pengaduan`.*

Dengan inheritance, atribut dan perilaku umum tidak perlu ditulis kembali pada masing-masing subclass. Setiap subclass hanya menambahkan atribut dan perilaku yang sesuai dengan jenis pengaduannya.

---

## 5. Penjelasan Penerapan Polymorphism dan Abstraction

### 5.1 Polymorphism

Pada program ini, polymorphism yang digunakan dalam pengembangan fitur adalah **method overloading**. Method yang memiliki nama sama dapat digunakan untuk kebutuhan pencarian yang berbeda berdasarkan parameter yang diberikan.

Penerapannya terdapat pada class `PengelolaDataPengaduan`.

### Overloading 1 — Mencari berdasarkan ID

<img width="437" height="143" alt="image" src="https://github.com/user-attachments/assets/21874cb3-cb75-4148-a7ff-f685ffdb049b" />

*Gambar 15: Penerapan Overloading pada class `PengelolaDataPengaduan`*

Method tersebut digunakan untuk mencari satu pengaduan berdasarkan ID dan menghasilkan satu object `Pengaduan`.

### Overloading 2 — Mencari berdasarkan jenis dan status

<img width="496" height="180" alt="image" src="https://github.com/user-attachments/assets/085e5645-76f4-4f3a-8756-8a1599d02e15" />

*Gambar 16: Penerapan Overloading pada class `PengelolaDataPengaduan` dengan parameter yang berbeda*

Method tersebut memiliki nama yang sama, tetapi parameter yang digunakan berbeda. Method ini digunakan untuk mencari beberapa pengaduan berdasarkan kombinasi jenis dan status.

Perbedaan parameter tersebut membuat Java dapat menentukan method `cariPengaduan()` yang akan digunakan sesuai dengan argumen yang diberikan.

Penerapan overloading digunakan pada fitur **Lihat Pengaduan**, khususnya ketika pengguna memilih pencarian berdasarkan ID atau berdasarkan jenis dan status. Setelah integrasi database, isi kedua method tersebut memanggil DAO, sedangkan nama method dan perbedaan parameternya tetap sama.

---

### 5.2 Abstraction

**Abstraction** diterapkan dengan menjadikan class `Pengaduan` sebagai `abstract class`.

Penerapannya terlihat pada kode:

<img width="202" height="17" alt="image" src="https://github.com/user-attachments/assets/8224a86d-d7b8-4b0c-b011-13cfef8005ce" />

*Gambar 17: Penerapan Abstraction pada super class`Pengaduan`*

Class `Pengaduan` tidak dibuat sebagai object secara langsung karena hanya berisi struktur dan perilaku umum yang akan digunakan oleh subclass.

Selain itu, terdapat method abstract:

<img width="431" height="54" alt="image" src="https://github.com/user-attachments/assets/dcd162d2-a067-46a0-9ebd-2d6022d06643" />

*Gambar 18: Penerapan Method Abstract pada super class `Pengaduan` dengan `getTingkatUrgensi` yang akan diturunkan ke subclass*

Method tersebut tidak memiliki implementasi di dalam superclass. Setiap subclass wajib memberikan implementasi sesuai dengan tingkat urgensinya.

Pada `PengaduanBiasa`:

<img width="407" height="72" alt="image" src="https://github.com/user-attachments/assets/36d9a266-73a9-44b4-90e0-efcbbf2cb980" />

*Gambar 19: Override pada class PengaduanBiasa*

Sedangkan pada `PengaduanDarurat`:

<img width="433" height="63" alt="image" src="https://github.com/user-attachments/assets/5f2cd635-d097-46ff-950c-2dc860430123" />

*Gambar 20: Override pada class PengaduanDarurat*

Dengan abstraction, class `Pengaduan` hanya menentukan bahwa setiap jenis pengaduan harus memiliki informasi tingkat urgensi, sedangkan detail nilai urgensinya ditentukan oleh masing-masing subclass.

---
## 6. Penjelasan Penerapan JDBC

Program terhubung ke database MySQL `db_pengaduan` menggunakan **JDBC** dengan **PreparedStatement**, dan akses databasenya dipisahkan dengan pola **DAO (Data Access Object)**. Dengan begitu data pengaduan tersimpan permanen di database dan tidak lagi di `ArrayList`.

### 6.1 Database dan data awal

Database dibuat melalui file **`sql/db_pengaduan.sql`**. File ini membuat tabel `pengaduan` dan mengisi **2 data awal** (`P001` pengaduan biasa dan `P002` pengaduan darurat). Data awal **diisi lewat file `.sql`, tidak lewat kode**, sehingga method `muatData()` yang dulu berisi data dummy sudah dihapus dari controller. Saat fitur Lihat Pengaduan dijalankan, data langsung tampil dari database.

Dua jenis pengaduan disimpan dalam satu tabel. Kolom `tingkat_urgensi` (`BIASA` atau `DARURAT`) menjadi pembeda, sedangkan kolom khusus seperti `kontak_darurat` hanya terisi untuk jenis yang sesuai.

<img width="887" height="272" alt="image" src="https://github.com/user-attachments/assets/39abfb6f-2070-4cb6-b52b-f968b8eb1e1a" />

*Gambar 21: Data awal pada tabel `pengaduan` hasil menjalankan file `.sql`.*

### 6.2 Koneksi database

Class `Koneksi` (package `config`) menyimpan URL, user, dan password database, lalu membuka koneksi JDBC dengan `DriverManager.getConnection()`. Pada `Main`, koneksi diperiksa lebih dulu. Jika gagal, program berhenti dengan pesan `Gagal terhubung ke Database`. Class `TestKoneksi` digunakan untuk menguji koneksi sebelum program utama dijalankan.

<img width="499" height="418" alt="image" src="https://github.com/user-attachments/assets/ce715f0e-f983-4e68-9f62-5f43f6dcd560" />

*Gambar 22: Class `Koneksi` untuk membuka koneksi JDBC ke database.*

<img width="434" height="263" alt="image" src="https://github.com/user-attachments/assets/405008fb-a5c0-4894-8e21-aa8d59f88219" />

*Gambar 23: Hasil pengujian koneksi database pada class `TestKoneksi`.*

### 6.3 DAO dan PreparedStatement

Interface `PengaduanDAO` (package `dao`) menjadi kontrak akses data, berisi method untuk tambah, baca (semua, berdasarkan ID, status, jenis, serta jenis dan status), ubah status, dan hapus. Class `PengaduanJdbcDAO` mengimplementasikannya dengan query SQL yang dijalankan memakai `PreparedStatement`. Nilai query dimasukkan lewat tanda `?`, bukan digabung langsung ke teks SQL, sehingga lebih aman dari SQL injection. Hasil `SELECT` ditampung di `ResultSet`, lalu diubah menjadi object `PengaduanBiasa` atau `PengaduanDarurat` sesuai kolom `tingkat_urgensi`.

Controller (`PengelolaDataPengaduan`) tidak lagi memakai `ArrayList`, tetapi memanggil DAO. Nama method dan overloading `cariPengaduan()` tetap sama, hanya isinya yang kini memanggil DAO, misalnya `dao.cariPengaduanById(id)`. Controller dan View tidak berisi SQL, karena seluruh SQL berada di package `dao`.

<img width="576" height="279" alt="image" src="https://github.com/user-attachments/assets/65f38285-f12a-4da5-81c9-a49b6cdb87fc" />

*Gambar 24: Interface `PengaduanDAO` sebagai kontrak akses data pengaduan.*

<img width="722" height="415" alt="image" src="https://github.com/user-attachments/assets/4c73fc81-9290-4539-ab86-7dc064769895" />

<img width="497" height="258" alt="image" src="https://github.com/user-attachments/assets/737260ca-eed7-4b68-a4f1-9d2e66c36eb4" />

*Gambar 25 & 26: Penerapan `PreparedStatement` untuk menyimpan dan membaca data pengaduan.*

<img width="523" height="234" alt="image" src="https://github.com/user-attachments/assets/90a1a253-8fc6-4410-9850-6980aea9cafd" />            

<img width="464" height="221" alt="image" src="https://github.com/user-attachments/assets/4dbc3577-a992-4fdc-b363-dcfbdbb5ba8f" />            

*Gambar 27 & 28: Penerapan `PreparedStatement` untuk mengubah dan menghapus data pengaduan.*

<img width="541" height="381" alt="image" src="https://github.com/user-attachments/assets/823b7f13-6e2d-41f3-8d4e-eb4cefd58bcf" />

*Gambar 29: Controller memakai DAO untuk mengakses database.*

<img width="724" height="374" alt="image" src="https://github.com/user-attachments/assets/b4bf4202-cfc0-404d-8bd7-97f2ece47a09" />

*Gambar 30: Main memeriksa koneksi database lalu menghubungkan DAO ke Controller.*

<img width="346" height="348" alt="image" src="https://github.com/user-attachments/assets/5aec070f-af2b-4aba-95e9-4730ec65e633" />

<img width="886" height="92" alt="image" src="https://github.com/user-attachments/assets/3a5c35e5-be51-467e-9f4c-1ba0d798d8f0" />

*Gambar 31 & 32: Tambah Pengaduan dan Bukti data pengaduan tersimpan di database.*

---

## 7. Penjelasan Letak Penerapan Nilai Tambah (ORM)

Nilai tambah yang diterapkan adalah **ORM (Object-Relational Mapping)** menggunakan **Hibernate** (implementasi JPA). Pada JDBC, query SQL ditulis manual. Pada ORM, hubungan antara class dan tabel dijelaskan lewat anotasi, lalu program bekerja dengan object dan ORM yang membuat perintah SQL-nya secara otomatis.

### 7.1 Letak penerapan

| Lokasi | Penerapan |
| ------ | --------- |
| `pom.xml` | Dependency `hibernate-core`. |
| `META-INF/persistence.xml` | Konfigurasi ORM (daftar entity dan koneksi database). |
| `model/Pengaduan`, `PengaduanBiasa`, `PengaduanDarurat` | Anotasi yang memetakan class ke tabel `pengaduan`. |
| `config/JPAUtil` | Membuat `EntityManager` yang dipakai DAO. |
| `dao/PengaduanOrmDAO` | DAO yang memakai ORM untuk simpan, cari ID, ubah, dan hapus. |
| `config/TestORM` | Menguji konfigurasi dan mapping ORM. |

<img width="788" height="379" alt="image" src="https://github.com/user-attachments/assets/e8d13619-1b16-4944-9824-f376aaa58273" />

*Gambar 33: Konfigurasi ORM pada `persistence.xml`.*

### 7.2 Mapping class ke tabel

Class `Pengaduan` diberi anotasi agar dipetakan ke tabel `pengaduan`. Class `PengaduanBiasa` dan `PengaduanDarurat` disimpan dalam tabel yang sama, dibedakan oleh kolom `tingkat_urgensi`.
Subclass diberi `@Entity` dan `@DiscriminatorValue("BIASA")` atau `@DiscriminatorValue("DARURAT")`, sedangkan atribut khususnya diberi `@Column`. Setiap entity juga memiliki constructor kosong yang dibutuhkan ORM.

<img width="568" height="335" alt="image" src="https://github.com/user-attachments/assets/24eb8301-0ff9-4e53-a01d-991c3548ae1e" />

*Gambar 34: Anotasi ORM pada superclass `Pengaduan`.*

<img width="590" height="137" alt="image" src="https://github.com/user-attachments/assets/1e7c248b-7db3-4d1a-beca-2fa01efeace5" />        

<img width="599" height="175" alt="image" src="https://github.com/user-attachments/assets/7c3c419e-2dda-4bde-8800-27b7dafbe6df" />        

*Gambar 35 & 36: Anotasi ORM pada subclass `PengaduanBiasa` dan `PengaduanDarurat`.*

### 7.3 DAO dengan ORM

`PengaduanOrmDAO` mewarisi `PengaduanJdbcDAO`, jadi JDBC tetap menjadi dasar. Class ini hanya mengganti empat operasi dengan ORM melalui `EntityManager`, yaitu simpan, cari berdasarkan ID, ubah status, dan hapus. Lihat semua dan pencarian/filter tetap memakai SQL dari `PengaduanJdbcDAO`. Operasi yang mengubah data dibungkus transaksi (`begin` dan `commit`, dengan `rollback` jika terjadi error). DAO yang dipakai dipilih pada `Main`, sedangkan Controller dan View tidak berubah:

<img width="504" height="246" alt="image" src="https://github.com/user-attachments/assets/15850da8-5c98-4ba1-867c-29444653c810" />

*Gambar 37: Penerapan ORM untuk membuat data pengaduan tanpa query SQL.*

<img width="616" height="53" alt="image" src="https://github.com/user-attachments/assets/f5fcd9c2-5e32-435d-a91e-fdc38768a6a9" />

*Gambar 38: Main memilih `PengaduanOrmDAO` sebagai DAO yang dipakai.*

### 7.4 Bukti penerapan ORM

<img width="615" height="340" alt="image" src="https://github.com/user-attachments/assets/6a0a1271-b42f-42bd-8641-e1362ffa5d7a" />

*Gambar 39: Hasil pengujian konfigurasi ORM dengan `TestORM`.*

<img width="719" height="194" alt="image" src="https://github.com/user-attachments/assets/bcfd3016-21a9-4791-9a6f-81cc27139d52" />

*Gambar 40: Pewarisan `PengaduanBiasa` dan `PengaduanDarurat` disimpan dalam satu tabel dengan kolom pembeda `tingkat_urgensi`.*
