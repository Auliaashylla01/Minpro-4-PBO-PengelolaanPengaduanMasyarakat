/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.util.ArrayList;
import java.util.Scanner;
//import model pengaduan untuk mengelola data dari superclass dan sub class
import model.Pengaduan;
import model.PengaduanBiasaBaru;
import model.PengaduanDaruratBaru;
import model.PenangananKhusus;
import view.PengaduanView;
import dao.PengaduanDAO;
/**
 * Class PengelolaDataPengaduan adalah sebagai Controller.
 * Class PengelolaPengaduan berfungsi untuk mengelola daftar atau data objek Pengaduan.
 * @author ASUS
 */
public class PengelolaDataPengaduan {
    //DAO digunakan untuk mengakses data pengaduan di database (menggantikan ArrayList)
    private PengaduanDAO dao;
    private PengaduanView view;
    private Scanner scanner;


        //Constructor menerima View dan DAO yang akan dipakai Controller
    public PengelolaDataPengaduan(PengaduanView view, PengaduanDAO dao) {
        this.view = view;
        this.dao = dao;
        this.scanner = new Scanner(System.in);
    }
    // Method untuk memulai program. Menu ditampilkan berulang
    // sampai pengguna memilih menu Keluar.
    public void mulai() {
        int menu;

        do {
            view.tampilkanHeader();
            view.tampilkanMenu();

            menu = ValidasiInput.inputMenu(scanner);

            // Menjalankan fitur sesuai menu yang dipilih
            switch (menu) {
                case 1 -> prosesTambahPengaduan();
                case 2 -> prosesLihatPengaduan();
                case 3 -> prosesUbahStatus();
                case 4 -> prosesHapusPengaduan();
                case 5 -> view.tampilkanSalam();
            }

            // Jeda agar output tidak langsung hilang
            if (menu != 5) {
                view.tampilkanPesanLanjut();
                scanner.nextLine();
            }
        } while (menu != 5);

        scanner.close();
    }
    
    // Memproses menu tambah pengaduan
    private void prosesTambahPengaduan() {
        view.tampilkanJudul("TAMBAH PENGADUAN");
        String id = generateId();
        view.tampilkanIdOtomatis(id);

        String nama = ValidasiInput.inputNamaPelapor(scanner, "Masukkan Nama Pelapor: ");

        view.tampilkanPilihanJenisPengaduan();
        String jenis = ValidasiInput.inputJenisPengaduan(scanner);

        String isi = ValidasiInput.inputTidakKosong(scanner, "Masukkan Isi Pengaduan: ");
        String tanggal = ValidasiInput.inputTanggal(scanner, "Masukkan Tanggal (dd/mm/yyyy): ");

        // Tingkat urgensi menentukan objek subclass yang dibuat
        view.tampilkanPilihanTingkatUrgensi();
        int urgensi = ValidasiInput.inputPilihan(scanner, "Pilihan (1/2): ", 1, 2);

                if (urgensi == 1) {
            tambahPengaduanBiasa(id, nama, jenis, isi, tanggal);
        } else {
            String kontak = ValidasiInput.inputKontakDarurat(scanner, "Masukkan Kontak Darurat (contoh: 081234567890) : ");
            tambahPengaduanDarurat(id, nama, jenis, isi, tanggal, kontak);
        }

        // Data dibaca ulang dari database untuk memastikan pengaduan benar-benar tersimpan
        Pengaduan pengaduanBaru = cariPengaduan(id);

        if (pengaduanBaru == null) {
            view.tampilkanError("Pengaduan gagal disimpan ke database.");
            return;
        }

        view.tampilkanSukses("Pengaduan (" + pengaduanBaru.getTingkatUrgensi() + ") berhasil ditambahkan ke dalam sistem!");
        view.tampilkanPesan("Status awal   : Menunggu Konfirmasi Petugas");

        // Pengaduan yang menerapkan interface PenangananKhusus (hanya darurat)
        // langsung mengirim notifikasi. instanceof mengecek apakah objek
        // memenuhi kontrak interface tersebut.
        if (pengaduanBaru instanceof PenangananKhusus) {
            ((PenangananKhusus) pengaduanBaru).kirimNotifikasiDarurat();
        }
    }

    // Memproses menu lihat pengaduan: semua data, atau dicari berdasarkan
    // ID, status, jenis, serta jenis dan status.
    // Memakai method cariPengaduan yang di-overload.
    private void prosesLihatPengaduan() {
        view.tampilkanJudul("LIHAT PENGADUAN");
        view.tampilkanMenuLihat();
        int pilihan = ValidasiInput.inputPilihan(scanner, "Pilih cara melihat data: ", 1, 5);

        if (pilihan == 1) {
            // Tampilkan seluruh data
        view.tampilkanDaftarPengaduan(dao.getAllPengaduan());
        } else if (pilihan == 2) {
            // Cari berdasarkan ID: hasilnya 1 objek
            String id = ValidasiInput.inputidPengaduan(scanner, "Masukkan ID Pengaduan(contoh: P001): ");
            Pengaduan p = cariPengaduan(id);

            if (p == null) {
                view.tampilkanError("ID Pengaduan tidak ditemukan! Silakan periksa kembali ID yang dimasukkan.");
            } else {
                view.tampilkanDetailPengaduan(p);
            }
        } else {
            // Cari berdasarkan status, jenis, atau jenis dan status: hasilnya berupa list
            ArrayList<Pengaduan> hasil;

            if (pilihan == 3) {
                view.tampilkanPilihanStatus();
                String status = ValidasiInput.inputStatus(scanner);
                hasil = cariPengaduanByStatus(status);
            } else if (pilihan == 4) {
                view.tampilkanPilihanJenisPengaduan();
                String jenis = ValidasiInput.inputJenisPengaduan(scanner);
                hasil = cariPengaduanByJenis(jenis);
            } else {
                view.tampilkanPilihanJenisPengaduan();
                String jenis = ValidasiInput.inputJenisPengaduan(scanner);
                view.tampilkanPilihanStatus();
                String status = ValidasiInput.inputStatus(scanner);
                hasil = cariPengaduan(jenis, status);
            }

            if (hasil.isEmpty()) {
                view.tampilkanInfo("Tidak ada pengaduan yang sesuai dengan pencarian.");
            } else {
                view.tampilkanDaftarPengaduan(hasil);
            }
        }
    }

    // Memproses menu ubah status pengaduan
    private void prosesUbahStatus() {
        view.tampilkanJudul("UBAH STATUS PENGADUAN");
        String id = ValidasiInput.inputidPengaduan(scanner, "Masukkan ID Pengaduan(contoh: P001): ");
        Pengaduan p = cariPengaduan(id);

        if (p == null) {
            view.tampilkanError("ID Pengaduan tidak ditemukan! Silakan periksa kembali ID yang dimasukkan.");
            return;
        }

        String statusBerikutnya = statusBerikutnya(p.getStatus());
        view.tampilkanStatusUbah(p, statusBerikutnya);

        // Status sudah di tahap terakhir, tidak ada yang bisa diubah
        if (statusBerikutnya == null) {
            return;
        }

        boolean lanjut = ValidasiInput.inputKonfirmasi(scanner);

        if (lanjut) {
            if (ubahStatus(id, statusBerikutnya)) {
                view.tampilkanSukses("Status pengaduan berhasil diperbarui menjadi \"" + statusBerikutnya + "\".");
            } else {
                view.tampilkanError("Status pengaduan gagal diperbarui di database.");
            } 
        }else {
            view.tampilkanInfo("Perubahan status dibatalkan."); }
    }

    // Memproses menu hapus pengaduan
    private void prosesHapusPengaduan() {
        view.tampilkanJudul("HAPUS PENGADUAN");
        String id = ValidasiInput.inputidPengaduan(scanner, "Masukkan ID Pengaduan(contoh: P001): ");
        Pengaduan p = cariPengaduan(id);

        if (p == null) {
            view.tampilkanError("ID Pengaduan tidak ditemukan! Silakan periksa kembali ID Anda.");
            return;
        }

        view.tampilkanKonfirmasiHapus(p);
        boolean konfirmasi = ValidasiInput.inputKonfirmasi(scanner);

        if (konfirmasi) {
            if (hapusPengaduan(id)) {
                view.tampilkanSukses("Data pengaduan berhasil dihapus dari sistem.");
            } else {
                view.tampilkanError("Data pengaduan gagal dihapus dari database.");
            }
        }else {
            view.tampilkanInfo("Proses penghapusan data pengaduan dibatalkan."); }
    }
    
   //Mengecek apakah suatu ID Pengaduan sudah ada di database.
    public boolean idSudahAda(String idPengaduan) {
        return dao.cariPengaduanById(idPengaduan) != null; // true jika ID sudah dipakai
    }
    
    //Membuat ID Pengaduan otomatis dengan format P001, P002, dan seterusnya.
    public String generateId() {

    int nomor = 1;
    String idBaru;

    do {
        idBaru = String.format("P%03d", nomor); // Format 3 digit angka (P001)
        nomor++;
    } while (idSudahAda(idBaru));

    return idBaru;
}

    //Menambahkan data Pengaduan biasa baru ke dalam ArrayList.
    public void tambahPengaduanBiasa(String id, String nama, String jenis,
                String isi, String tanggal) {
        Pengaduan p = new PengaduanBiasaBaru(id, nama, jenis, isi, tanggal);
        dao.tambahPengaduan(p);
    }
    //Menambahkan data Pengaduan darurat baru ke dalam ArrayList.
    public void tambahPengaduanDarurat(String id, String nama, String jenis, String isi,
                String tanggal, String kontakDarurat) {
        Pengaduan p = new PengaduanDaruratBaru(id, nama, jenis, isi, tanggal, kontakDarurat);
        dao.tambahPengaduan(p);
    }
    
    // Overloading 1: cari berdasarkan ID, hasilnya 1 objek Pengaduan
    public Pengaduan cariPengaduan(String idPengaduan) {
        // Hasilnya null jika pengaduan tidak ditemukan
        return dao.cariPengaduanById(idPengaduan);
    }

    // Overloading 2: cari berdasarkan jenis dan status sekaligus,
    // hasilnya berupa ArrayList karena bisa lebih dari satu data
    public ArrayList<Pengaduan> cariPengaduan(String jenisPengaduan, String status) {
        return dao.cariPengaduanByJenisDanStatus(jenisPengaduan, status);
    }

    public ArrayList<Pengaduan> cariPengaduanByStatus(String status) {
        return dao.cariPengaduanByStatus(status);
    }

    public ArrayList<Pengaduan> cariPengaduanByJenis(String jenisPengaduan) {
        return dao.cariPengaduanByJenis(jenisPengaduan);
    }
    
    public String statusBerikutnya(String statusSaatIni) {
        if (statusSaatIni.equals("Menunggu Konfirmasi Petugas")) {
            return "Sedang Diproses";
        }

        if (statusSaatIni.equals("Sedang Diproses")) {
            return "Selesai Ditindaklanjuti";
        }

        // Sudah di tahap terakhir
        return null;
    }
    // Memperbarui status pengaduan berdasarkan ID
    public boolean ubahStatus(String idPengaduan, String statusBaru) {
        return dao.ubahStatusPengaduan(idPengaduan, statusBaru);
    }

    // Menghapus pengaduan dari database berdasarkan ID
    public boolean hapusPengaduan(String idPengaduan) {
        return dao.hapusPengaduan(idPengaduan);
    }
}
