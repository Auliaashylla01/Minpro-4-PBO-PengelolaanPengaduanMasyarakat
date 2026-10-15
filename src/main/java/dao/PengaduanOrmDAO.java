/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import config.JPAUtil;
import jakarta.persistence.EntityManager;
import model.Pengaduan;

/**
 * DAO dengan ORM (JPA/Hibernate), dibangun di atas DAO JDBC (extends PengaduanJdbcDAO).
 * Operasi per objek (simpan, cari berdasarkan ID, ubah, hapus) dikerjakan ORM lewat EntityManager,
 * tanpa menulis SQL. Method lain (lihat semua dan pencarian/filter) tidak ditulis ulang di sini,
 * sehingga tetap memakai query SQL dengan PreparedStatement dari PengaduanJdbcDAO.
 */

public class PengaduanOrmDAO extends PengaduanJdbcDAO {
    //CREATE
    @Override
    public void tambahPengaduan(Pengaduan pengaduan) {
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();
            // persist: ORM membuat perintah INSERT sendiri dari isi objek
            em.persist(pengaduan);
            em.getTransaction().commit();
        } catch (Exception e) {
            batalkanTransaksi(em);
            System.out.println("Gagal menambah pengaduan (ORM) - " + e.getMessage());
        } finally {
            tutup(em);
        }
    }

    //READ (by ID)
    @Override
    public Pengaduan cariPengaduanById(String idPengaduan) {
        Pengaduan pengaduan = null;
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();
            // find: ORM mencari berdasarkan primary key dan langsung mengembalikan objek
            pengaduan = em.find(Pengaduan.class, idPengaduan);
        } catch (Exception e) {
            System.out.println("Gagal mencari pengaduan (ORM) - " + e.getMessage());
        } finally {
            tutup(em);
        }
        return pengaduan;
    }

    //UPDATE
    @Override
    public boolean ubahStatusPengaduan(String idPengaduan, String statusBaru) {
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();

            Pengaduan pengaduan = em.find(Pengaduan.class, idPengaduan);
            if (pengaduan == null) {
                em.getTransaction().rollback();
                return false;
            }

            // Cukup mengubah objeknya. Saat commit, ORM menyadari ada perubahan
            // lalu membuat perintah UPDATE sendiri (dirty checking).
            pengaduan.setStatus(statusBaru);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            batalkanTransaksi(em);
            System.out.println("Gagal mengubah status pengaduan (ORM) - " + e.getMessage());
            return false;
        } finally {
            tutup(em);
        }
    }

    //DELETE
    @Override
    public boolean hapusPengaduan(String idPengaduan) {
        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();

            Pengaduan pengaduan = em.find(Pengaduan.class, idPengaduan);
            if (pengaduan == null) {
                em.getTransaction().rollback();
                return false;
            }

            // remove: ORM membuat perintah DELETE sendiri
            em.remove(pengaduan);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            batalkanTransaksi(em);
            System.out.println("Gagal menghapus pengaduan (ORM) - " + e.getMessage());
            return false;
        } finally {
            tutup(em);
        }
    }

    // Membatalkan transaksi yang masih berjalan jika terjadi error
    private void batalkanTransaksi(EntityManager em) {
        if (em != null && em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    // Menutup EntityManager setelah selesai dipakai
    private void tutup(EntityManager em) {
        if (em != null) {
            em.close();
        }
    }
}
