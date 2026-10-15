# Sistem Pengelolaan Pengaduan Masyarakat

**Oleh Aulia Ashylla Ananda Putri Hariawan (2509116076)**

## 1. Deskripsi Singkat Program

Sistem Pengelolaan Pengaduan Masyarakat merupakan program yang digunakan untuk mencatat dan mengelola data pengaduan yang disampaikan oleh masyarakat. Program ini dapat digunakan untuk menangani berbagai jenis laporan, seperti fasilitas umum, kebersihan, keamanan, jalan, dan pelayanan. Setiap data pengaduan memiliki informasi berupa ID Pengaduan, Nama Pelapor, Jenis Pengaduan, Isi Pengaduan, Tanggal Pengaduan, Tingkat Urgensi, dan Status Pengaduan. ID pengaduan dibuat secara otomatis oleh sistem dengan format `P001`, `P002`, dan seterusnya. Jenis pengaduan dipilih melalui kategori yang telah disediakan sehingga data yang dimasukkan lebih terstruktur.

Program membedakan pengaduan menjadi dua jenis berdasarkan tingkat urgensinya, yaitu **pengaduan biasa** dan **pengaduan darurat**. Pengaduan biasa memiliki target penyelesaian standar 7 hari, sedangkan pengaduan darurat memiliki target respons awal standar 24 jam dan menyimpan kontak darurat pelapor. Pengaduan darurat juga memiliki penanganan khusus berupa pengiriman notifikasi darurat kepada kontak yang diberikan pelapor.

Pengguna dapat menjalankan beberapa fitur utama melalui menu interaktif, yaitu Tambah Pengaduan, Lihat Pengaduan, Ubah Status Pengaduan, Hapus Pengaduan, dan Keluar. Pada fitur Lihat Pengaduan, pengguna dapat memilih lima cara untuk melihat data, yaitu melihat seluruh pengaduan, berdasarkan ID, berdasarkan status, berdasarkan jenis, atau berdasarkan jenis dan status. Data selama program berjalan disimpan menggunakan `ArrayList`.

---

## 2. Tujuan Program

Program ini dibuat sebagai penerapan konsep Pemrograman Berorientasi Objek melalui sebuah sistem pengelolaan pengaduan sederhana.

Tujuan program adalah:

* Mencatat data pengaduan masyarakat secara terstruktur.
* Mengelompokkan pengaduan berdasarkan jenis dan tingkat urgensi.
* Menampilkan data pengaduan yang tersimpan.
* Mencari data pengaduan berdasarkan beberapa kriteria.
* Mengubah status pengaduan berdasarkan tahapan penanganan.
* Menghapus data pengaduan berdasarkan ID.

---

## 3. Struktur Program

Program terdiri dari beberapa class yang dikelompokkan berdasarkan fungsinya.

| Package | Class/File | Peran |
|---|---|---|
| `main` | `PengelolaanPengaduanMasyarakt` | Menjadi *entry point*, memeriksa koneksi database, memilih DAO yang digunakan, lalu menjalankan controller. |
| `controller` | `PengelolaDataPengaduan` | Mengatur alur tambah, lihat, cari, ubah status, hapus, dan pembuatan ID otomatis. Data diakses melalui objek DAO, bukan disimpan sebagai daftar utama di `ArrayList`. |
| `controller` | `ValidasiInput` | Memvalidasi input pengguna agar sesuai format dan pilihan yang tersedia. |
| `model` | `Pengaduan` | Abstract superclass yang menyimpan atribut dan perilaku umum pengaduan. Class ini juga dipetakan ke tabel melalui anotasi ORM. |
| `model` | `PengaduanBiasa` | Subclass `Pengaduan` untuk pengaduan biasa dengan target penyelesaian standar 7 hari. |
| `model` | `PengaduanDarurat` | Subclass `Pengaduan` untuk pengaduan darurat dengan kontak darurat dan target respons awal 24 jam. |
| `model` | `PenangananKhusus` | Interface untuk perilaku penanganan khusus, termasuk informasi target respons dan notifikasi darurat. |
| `view` | `PengaduanView` | Mengatur menu, judul, pilihan, informasi, pesan sukses, dan pesan kesalahan pada terminal. |
| `config` | `Koneksi` | Membuat dan menyediakan koneksi JDBC ke database MySQL. |
| `config` | `JPAUtil` | Menyediakan `EntityManager` untuk akses database menggunakan JPA/Hibernate. |
| `config` | `TestKoneksi` | Menguji koneksi JDBC ke database. |
| `config` | `TestORM` | Menguji koneksi ORM dan menjalankan query JPQL untuk menghitung data pengaduan. |
| `dao` | `PengaduanDAO` | Interface yang menetapkan operasi tambah, baca, cari, ubah, dan hapus data. |
| `dao` | `PengaduanJdbcDAO` | Mengimplementasikan akses database menggunakan JDBC, `PreparedStatement`, dan `ResultSet`. |
| `dao` | `PengaduanOrmDAO` | Memperluas `PengaduanJdbcDAO` dan mengganti beberapa operasi menjadi akses berbasis ORM. |
| `src/main/resources/META-INF` | `persistence.xml` | Mengatur persistence unit dan konfigurasi koneksi Hibernate/JPA. |
| Root project | `pom.xml` | Mengatur project Maven serta dependency MySQL Connector/J dan Hibernate. |
| `database` | `db_pengaduan.sql` | Membuat database/tabel serta mengisi dua data dummy awal. |

### Struktur package

```text
PengelolaanPengaduanMasyarakt/
├── database/
│   └── db_pengaduan.sql
├── src/
│   └── main/
│       ├── java/
│       │   ├── config/
│       │   │   ├── Koneksi.java
│       │   │   ├── JPAUtil.java
│       │   │   ├── TestKoneksi.java
│       │   │   └── TestORM.java
│       │   ├── controller/
│       │   │   ├── PengelolaDataPengaduan.java
│       │   │   └── ValidasiInput.java
│       │   ├── dao/
│       │   │   ├── PengaduanDAO.java
│       │   │   ├── PengaduanJdbcDAO.java
│       │   │   └── PengaduanOrmDAO.java
│       │   ├── main/
│       │   │   └── PengelolaanPengaduanMasyarakt.java
│       │   ├── model/
│       │   │   ├── Pengaduan.java
│       │   │   ├── PengaduanBiasa.java
│       │   │   ├── PengaduanDarurat.java
│       │   │   └── PenangananKhusus.java
│       │   └── view/
│       │       └── PengaduanView.java
│       └── resources/
│           └── META-INF/
│               └── persistence.xml
├── pom.xml

```

---

## 4. Menu Program

Menu utama yang tersedia pada sistem adalah:

<img width="280" height="119" alt="image" src="https://github.com/user-attachments/assets/f8c819d5-7c75-4229-99bd-28b323aa2432" />

*Gambar 1: Tampilan menu utama Sistem Pengelolaan Pengaduan Masyarakat sebagai pusat navigasi seluruh fitur program.*

Menu tersebut digunakan sebagai pusat navigasi program. Setelah pengguna menyelesaikan suatu proses, program akan kembali ke menu utama selama pengguna belum memilih menu Keluar.

---

# 5. Alur Program

## 5.1 Alur Sistem

Secara umum, sistem dimulai dengan menyediakan data awal pada `ArrayList`, kemudian pengguna dapat memilih fitur yang tersedia melalui menu utama. Alur sistem tersebut menunjukkan bahwa setiap fitur bekerja melalui menu utama dan setelah proses selesai pengguna kembali ke menu. Program akan berhenti ketika pilihan menu bernilai `5`.

```text
                        ┌──────────────┐
                        │    MULAI     │
                        └──────┬───────┘
                               │
                               ▼
                    Periksa koneksi ke 
                      database MySQL
                               │
                               ▼
                       Koneksi berhasil?
                               │
             ┌─────────────────┴─────────────────┐
             │ (Tidak)                           │ (Ya)
             ▼                                   ▼
    Tampilkan pesan gagal                 Buat View, DAO, 
                                           dan Controller
             │                                   │
             ▼                                   ▼
          Selesai                     Tampilkan menu utama
                                                 │
                                                 ▼
                                        Input Pilihan Menu
                                                 │
                                                 ▼
                                          [ Pilihan Menu? ]
                                                 │
             ┌───────────────┬───────────┬───────┴─────┬──────────────┐
             │               │           │             │              │
             ▼               ▼           ▼             ▼              ▼
            [1]             [2]         [3]           [4]            [5]
          Tambah           Lihat        Ubah         Hapus         Keluar
         pengaduan       pengaduan     status      pengaduan
             │               │           │             │              │
             ▼               ▼           ▼             ▼              ▼
          Simpan          Baca via     Perbarui       Hapus        Selesai
         via ORM         JDBC / ORM    via ORM       via ORM
             │               │           │             │              
             └───────────────┴─────┬─────┴─────────────┘              
                                   │                                  
                                   ▼                                  
                       Kembali ke menu utama
```

---

## 5.2 Alur Tambah Pengaduan

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
                Simpan ke Database
                         │
                         ▼
                Tampilkan Berhasil
                         │
                         ▼
                  Kembali ke Menu
```

ID tidak dimasukkan secara manual oleh pengguna. Sistem menghasilkan ID berdasarkan ID yang belum digunakan, misalnya `P001`, `P002`, dan seterusnya.

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

Pada pengaduan darurat, pengguna juga diminta memasukkan kontak darurat. Setelah data berhasil ditambahkan, sistem akan memeriksa apakah object tersebut menerapkan interface `PenangananKhusus`. Jika memenuhi interface tersebut, sistem akan menjalankan method `kirimNotifikasiDarurat()` untuk memberikan notifikasi bahwa pengaduan membutuhkan respons khusus.

### Bukti output proses tambah pengaduan biasa

<img width="334" height="298" alt="image" src="https://github.com/user-attachments/assets/dfbd73e3-e3bb-42e5-a5b6-16b4828c6dbb" />

*Gambar 2: Proses penambahan pengaduan biasa, mulai dari ID otomatis, pengisian data pengaduan, pemilihan jenis dan urgensi, hingga data berhasil disimpan pada database.*

### Bukti output proses tambah pengaduan darurat

<img width="350" height="380" alt="image" src="https://github.com/user-attachments/assets/f90ce3f7-ff67-4651-9faf-64b95233e9c9" />


*Gambar 3: Proses penambahan pengaduan darurat yang menghasilkan object `PengaduanDarurat`, meminta input kontak darurat pelapor, serta menjalankan notifikasi penanganan khusus.*

---

## 5.3 Alur Lihat Pengaduan

Fitur **Lihat Pengaduan** digunakan untuk menampilkan atau mencari data pengaduan yang tersimpan pada Database.

<img width="267" height="135" alt="image" src="https://github.com/user-attachments/assets/d28321b4-959c-4002-b9d6-8d52f9a59487" />            

*Gambar 4: Tampilan fitur lima cara Lihat Pengaduan*          

Pada versi program ini, pengguna dapat memilih **lima cara** untuk melihat data pengaduan.            

<img width="464" height="283" alt="image" src="https://github.com/user-attachments/assets/05dbb5a0-5465-4e98-bd92-143d5d8dfaa0" />

*Gambar 5: Tampilan data pengaduan pada fitur Lihat Pengaduan, dengan data pengaduan yang disimpan dalam database*

Data Pengaduan telah disimpam di database. Oleh karena itu, ketika program pertama kali menjalankan menu Lihat Pengaduan, data sudah langsung tersedia tanpa harus melakukan proses tambah terlebih dahulu.

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
                  Tampilkan hasil
                         ↓
                  Kembali ke Menu
```

Untuk pencarian berdasarkan ID, sistem menghasilkan satu object `Pengaduan` apabila data ditemukan. Sedangkan pencarian berdasarkan status, jenis, atau jenis dan status dapat menghasilkan lebih dari satu data sehingga hasilnya disimpan dalam Database Pengaduan.

Pencarian berdasarkan jenis dan status menggunakan method `cariPengaduan()` dengan parameter yang berbeda dari pencarian berdasarkan ID. Penerapan tersebut menjadi salah satu bentuk **method overloading** pada program.

---

## 5.4 Alur Ubah Status Pengaduan

Fitur **Ubah Status Pengaduan** menggunakan alur status bertahap agar perubahan status tidak dapat dilakukan secara acak.

<img width="377" height="242" alt="image" src="https://github.com/user-attachments/assets/bac2cd7f-d218-4dde-9450-e6c6bf2493e8" />

*Gambar 6: Proses perubahan status pengaduan berdasarkan ID dengan konfirmasi pengguna sebelum status diperbarui.*

Urutan status adalah:

```text
Menunggu Konfirmasi Petugas
             ↓
       Sedang Diproses
             ↓
   Selesai Ditindaklanjuti
```

Pengguna memasukkan ID pengaduan yang akan diubah. Sistem kemudian mencari data berdasarkan ID tersebut. Setelah data ditemukan, sistem menentukan status berikutnya berdasarkan status saat ini.

Apabila pengaduan sudah berada pada status `Selesai Ditindaklanjuti`, status tidak dapat diubah lagi.

```text
Pilih Ubah Status
                      │
                      ▼
           ┌► Input ID Pengaduan ◄────────────────┐
           │          │                           │
           │          ▼                           │
           │ Cari Data berdasarkan ID             │
           │          │                           │
           │          ▼                           │
           │   Data ditemukan?                    │
           │     ┌────┴────┐                      │
           │   Tidak       Ya                     │
           │     │          │                     │
           │     ▼          ▼                     │
           └── Error  Cek Status Saat Ini         │
                           │                      │
                           ▼                      │
                Tentukan Status Berikutnya        │
                           │                      │
                           ▼                      │
                Konfirmasi perubahan?             │
                    ┌──────┴──────┐               │
                    │             │               │
                    ▼             ▼               │
                   'y'           'n'              │
                    │             │               │
                    ▼             ▼               │
                Ubah Status      Batal            │
                    │             │               │
                    └──────┬──────┘               │
                           │                      │
                           ▼                      │
                    Kembali ke Menu ─────────────┘
```

---

## 5.5 Alur Hapus Pengaduan

Fitur **Hapus Pengaduan** digunakan untuk menghapus data berdasarkan ID.

<img width="328" height="239" alt="image" src="https://github.com/user-attachments/assets/11717486-9d2c-4af5-b98a-645641ac31d8" />

*Gambar 7: Proses penghapusan data pengaduan melalui pencarian ID dan konfirmasi pengguna sebelum data dihapus.*

Sebelum data benar-benar dihapus, sistem menampilkan ringkasan data dan meminta konfirmasi. Hal tersebut mencegah penghapusan dilakukan secara langsung tanpa persetujuan pengguna.

```text
Pilih Hapus Pengaduan
                         │
                         ▼
           ┌► Input ID Pengaduan
           │             │
           │             ▼
           │       Cari Pengaduan
           │             │
           │             ▼
           │      Data ditemukan?
           │        ┌────┴────┐
           │      Tidak      Ya
           │        │         │
           │        ▼         ▼
           └─── Error    Tampilkan Ringkasan Data
                              │
                              ▼
                       Konfirmasi (y/n)?
                          ┌─────┴─────┐
                          │           │
                          ▼           ▼
                         'y'         'n'
                          │           │
                          ▼           ▼
                        Hapus       Batal
                          │           │
                          └─────┬─────┘
                                │
                                ▼
                         Kembali ke Menu
```

---

## 5.6 Alur Keluar Program

Ketika pengguna memilih menu `5`, program menampilkan pesan penutup dan mengakhiri perulangan menu.

<img width="380" height="170" alt="image" src="https://github.com/user-attachments/assets/e3a15630-7d23-4331-92f8-e794194d1c5a" />

*Gambar 8: Tampilan ketika pengguna memilih menu Keluar dan program mengakhiri proses.*

---

# 6. Penerapan Encapsulation dan Inheritance

## 6.1 Encapsulation

**Encapsulation** diterapkan dengan menyembunyikan data internal object melalui atribut `private` dan menyediakan method `getter` serta `setter` sesuai kebutuhan.

Pada class `Pengaduan`, atribut utama menggunakan `private`:

<img width="455" height="98" alt="image" src="https://github.com/user-attachments/assets/cb3e1b40-8315-42c0-905c-2c54197b9f0f" />

*Gambar 9: Penerapan encapsulation pada Class Pengaduan dengan access modifier*

Atribut tersebut tidak dapat diakses secara langsung dari class lain. Akses terhadap data dilakukan melalui method yang telah disediakan.


<img width="526" height="143" alt="image" src="https://github.com/user-attachments/assets/c8f08e32-85c0-4df3-a6bc-b0eb24199b00" />

*Gambar 10: Penerapan encapsulation melalui method `getter` dan `setter` pada class `Pengaduan`.*

Atribut `idPengaduan` tidak memiliki `setter`. Hal tersebut dilakukan karena ID dibuat secara otomatis oleh sistem dan digunakan sebagai identitas pengaduan sehingga tidak diubah melalui setter.

Encapsulation juga diterapkan pada atribut tambahan di subclass, seperti `targetSelesaiHari` pada `PengaduanBiasa` serta `kontakDarurat` dan `batasResponJam` pada `PengaduanDarurat`.

---

## 6.2 Inheritance

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

*Gambar 11: Penerapan inheritance pada subclass PengaduanBiasa*            

dan:

<img width="470" height="52" alt="image" src="https://github.com/user-attachments/assets/413be313-43d1-40b8-849b-813449c8b29a" />                

*Gambar 12: Penerapan inheritance pada subclass PengaduanDarurat*                        

Pada constructor subclass digunakan `super()` untuk menginisialisasi atribut yang berasal dari superclass.

Contoh:

<img width="556" height="115" alt="image" src="https://github.com/user-attachments/assets/cbf53c77-af77-4ed7-bdb3-b739e0842dae" />

*Gambar 13: Penggunaan `super()` pada constructor subclass untuk memanggil constructor superclass `Pengaduan`.*

Dengan inheritance, atribut dan perilaku umum tidak perlu ditulis kembali pada masing-masing subclass. Setiap subclass hanya menambahkan atribut dan perilaku yang sesuai dengan jenis pengaduannya.

---

# 7. Penerapan Polymorphism dan Abstraction

## 7.1 Polymorphism

Pada program ini, polymorphism yang digunakan dalam pengembangan fitur adalah **method overloading**. Method yang memiliki nama sama dapat digunakan untuk kebutuhan pencarian yang berbeda berdasarkan parameter yang diberikan.

Penerapannya terdapat pada class `PengelolaDataPengaduan`.

### Overloading 1 — Mencari berdasarkan ID

<img width="437" height="143" alt="image" src="https://github.com/user-attachments/assets/21874cb3-cb75-4148-a7ff-f685ffdb049b" />

*Gambar 14: Penerapan Overloading pada class `PengelolaDataPengaduan`*

Method tersebut digunakan untuk mencari satu pengaduan berdasarkan ID dan menghasilkan satu object `Pengaduan`.

### Overloading 2 — Mencari berdasarkan jenis dan status

<img width="496" height="180" alt="image" src="https://github.com/user-attachments/assets/085e5645-76f4-4f3a-8756-8a1599d02e15" />

*Gambar 15: Penerapan Overloading pada class `PengelolaDataPengaduan` dengan parameter yang berbeda*

Method tersebut memiliki nama yang sama, tetapi parameter yang digunakan berbeda. Method ini digunakan untuk mencari beberapa pengaduan berdasarkan kombinasi jenis dan status.

Perbedaan parameter tersebut membuat Java dapat menentukan method `cariPengaduan()` yang akan digunakan sesuai dengan argumen yang diberikan.

Penerapan overloading digunakan pada fitur **Lihat Pengaduan**, khususnya ketika pengguna memilih pencarian berdasarkan ID atau berdasarkan jenis dan status.

---

## 7.2 Abstraction

**Abstraction** diterapkan dengan menjadikan class `Pengaduan` sebagai `abstract class`.

Penerapannya terlihat pada kode:

<img width="202" height="17" alt="image" src="https://github.com/user-attachments/assets/8224a86d-d7b8-4b0c-b011-13cfef8005ce" />

*Gambar 16: Penerapan Abstraction pada super class`Pengaduan`*

Class `Pengaduan` tidak dibuat sebagai object secara langsung karena hanya berisi struktur dan perilaku umum yang akan digunakan oleh subclass.

Selain itu, terdapat method abstract:

<img width="431" height="54" alt="image" src="https://github.com/user-attachments/assets/dcd162d2-a067-46a0-9ebd-2d6022d06643" />

*Gambar 17: Penerapan Method Abstract pada super class `Pengaduan` dengan `getTingkatUrgensi` yang akan diturunkan ke subclass*

Method tersebut tidak memiliki implementasi di dalam superclass. Setiap subclass wajib memberikan implementasi sesuai dengan tingkat urgensinya.

Pada `PengaduanBiasa`:

<img width="407" height="72" alt="image" src="https://github.com/user-attachments/assets/36d9a266-73a9-44b4-90e0-efcbbf2cb980" />

*Gambar 18: Override pada class PengaduanBiasa*

Sedangkan pada `PengaduanDarurat`:

<img width="433" height="63" alt="image" src="https://github.com/user-attachments/assets/5f2cd635-d097-46ff-950c-2dc860430123" />                        

*Gambar 19: Override pada class PengaduanDarurat*

Dengan abstraction, class `Pengaduan` hanya menentukan bahwa setiap jenis pengaduan harus memiliki informasi tingkat urgensi, sedangkan detail nilai urgensinya ditentukan oleh masing-masing subclass.

---


## 8. Penjelasan Penerapan JDBC

