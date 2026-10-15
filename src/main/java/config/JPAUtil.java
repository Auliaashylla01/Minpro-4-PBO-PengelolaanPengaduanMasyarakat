/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
/**
 * Class JPAUtil adalah pasangan Koneksi untuk ORM.
 * Koneksi dipakai oleh DAO JDBC, sedangkan JPAUtil dipakai oleh DAO ORM.
 * EntityManagerFactory dibuat satu kali saja (singleton) karena proses pembuatannya berat,
 * lalu dari factory ini dibuat EntityManager setiap kali DAO membutuhkan.
 */
public class JPAUtil {
    // Harus sama dengan nama <persistence-unit> pada META-INF/persistence.xml
    private static final String NAMA_UNIT = "pengaduanPU";

    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() {
        // Jika factory belum ada atau sudah ditutup, buat baru
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory(NAMA_UNIT);
        }
        return emf.createEntityManager();
    }
}
