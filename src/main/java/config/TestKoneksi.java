/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import java.sql.Connection;
/**
 * Class untuk menguji koneksi JDBC ke database sebelum program utama dijalankan.
 */
public class TestKoneksi {
    public static void main(String[] args) {
        System.out.println("Mengecek koneksi ke database...");

        //Memanggil koneksi
        Connection conn = Koneksi.getConnection();

        //Mengecek variabel conn ada isinya atau kosong (null)
        if (conn != null) {
            System.out.println("STATUS: BERHASIL! Database siap digunakan.");
        } else {
            System.out.println("STATUS: GAGAL! Periksa kembali URL, USER, PASS, dan pastikan service MySQL sudah menyala.");
        }
    }
}
