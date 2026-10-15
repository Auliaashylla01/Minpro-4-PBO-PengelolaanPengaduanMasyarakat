/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class Koneksi berfungsi untuk membuka dan menyimpan koneksi ke database MySQL.
 * Koneksi dibuat satu kali saja (singleton) lalu dipakai berulang oleh DAO.
 */
public class Koneksi {
    private static final String URL = "jdbc:mysql://localhost:3306/db_pengaduan";
    private static final String USER = "root";
    private static final String PASS = "";

    //variabel statis untuk menyimpan koneksi
    private static Connection conn;

    public static Connection getConnection() {
        try {
            //memeriksa koneksi belum ada atau sudah terputus
            if (conn == null || conn.isClosed()) {
                // Jika belum ada, membuat koneksi baru
                conn = DriverManager.getConnection(URL, USER, PASS);
            }
        } catch (SQLException e) {
            System.out.println("Koneksi GAGAL: " + e.getMessage());
        }

        //Kembalikan koneksi yang sudah aman
        return conn;
    }
}