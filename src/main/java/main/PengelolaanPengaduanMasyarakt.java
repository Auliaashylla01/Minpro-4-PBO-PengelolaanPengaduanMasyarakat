/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import controller.PengelolaDataPengaduan;
import view.PengaduanView;
import config.Koneksi;
import dao.PengaduanDAO;
import dao.PengaduanOrmDAO;
/**
 * Kelas Utama (Main Class)
 * Berfungsi sebagai titik awal (entry point) untuk menjalankan alur program 
 * sistem Pengelolaan Pengaduan Masyarakat.
 */

public class PengelolaanPengaduanMasyarakt {

    public static void main(String[] args) {
        
        // --- KODE UNTUK MENYEMBUNYIKAN LOG INFO & WARN HIBERNATE ---
        System.setProperty("java.util.logging.config.file", "");
        java.util.logging.LogManager.getLogManager().reset();
        java.util.logging.Logger rootLogger = java.util.logging.Logger.getLogger("");
        rootLogger.setLevel(java.util.logging.Level.SEVERE); // Hanya tampilkan pesan error tingkat SEVERE saja
        
        //Memastikan koneksi ke database berhasil sebelum program dijalankan
        if (Koneksi.getConnection() != null) {
            //Membuat objek View untuk menangani tampilan sistem
            PengaduanView view = new PengaduanView();
            //Membuat objek DAO untuk akses database.
            //PengaduanJdbcDAO = JDBC murni (PreparedStatement).
            //PengaduanOrmDAO  = ORM untuk simpan/cari ID/ubah/hapus, JDBC untuk lihat semua dan pencarian/filter.
            PengaduanDAO dao = new PengaduanOrmDAO();
            //Membuat objek Controller dan menghubungkannya dengan View dan DAO
            PengelolaDataPengaduan controller = new PengelolaDataPengaduan(view, dao);
            //Memanggil method mulai() pada Controller untuk menjalankan alur menu utama
            controller.mulai();
        } else {
            System.out.println("Gagal terhubung ke Database. Program dihentikan.");
        }
}
}