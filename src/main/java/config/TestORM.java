/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import jakarta.persistence.EntityManager;

/**
 *e
 * @author ASUS
 */
public class TestORM {
    public static void main(String[] args) {
        System.out.println("Mengecek ORM ke database...");

        EntityManager em = JPAUtil.getEntityManager();

        try {
            // JPQL: menghitung jumlah baris tabel pengaduan lewat entity Pengaduan
            Long jumlah = em.createQuery("SELECT COUNT(p) FROM Pengaduan p", Long.class).getSingleResult();
            System.out.println("STATUS: BERHASIL! ORM terhubung, jumlah pengaduan di database: " + jumlah);
        } catch (Exception e) {
            System.out.println("STATUS: GAGAL! " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
