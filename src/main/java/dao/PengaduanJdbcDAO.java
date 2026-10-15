/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import model.Pengaduan;
import model.PengaduanBiasaBaru;
import model.PengaduanDaruratBaru;
/**
 *
 * @author ASUS
 */
public class PengaduanJdbcDAO implements PengaduanDAO {

    //CREATE
    @Override
    public void tambahPengaduan(Pengaduan pengaduan) {
        String sql = "INSERT INTO pengaduan (id_pengaduan, nama_pelapor, jenis_pengaduan, isi_pengaduan, "
                + "tanggal_pengaduan, status, tingkat_urgensi, target_selesai_hari, kontak_darurat, batas_respon_jam) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pengaduan.getIdPengaduan());
            pstmt.setString(2, pengaduan.getNamaPelapor());
            pstmt.setString(3, pengaduan.getJenisPengaduan());
            pstmt.setString(4, pengaduan.getIsiPengaduan());
            pstmt.setString(5, pengaduan.getTanggalPengaduan());
            pstmt.setString(6, pengaduan.getStatus());
            pstmt.setString(7, pengaduan.getTingkatUrgensi());

            // Kolom 8-10 hanya diisi sesuai jenis pengaduan, sisanya NULL
            if (pengaduan instanceof PengaduanBiasaBaru) {
                PengaduanBiasaBaru biasa = (PengaduanBiasaBaru) pengaduan;
                pstmt.setInt(8, biasa.gettargetSelesaiHari());
                pstmt.setNull(9, Types.VARCHAR);
                pstmt.setNull(10, Types.INTEGER);
            } else if (pengaduan instanceof PengaduanDaruratBaru) {
                PengaduanDaruratBaru darurat = (PengaduanDaruratBaru) pengaduan;
                pstmt.setNull(8, Types.INTEGER);
                pstmt.setString(9, darurat.getkontakDarurat());
                pstmt.setInt(10, darurat.getbatasResponJam());
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menambah pengaduan - " + e.getMessage());
        }
    }

    //READ (all)
    @Override
    public ArrayList<Pengaduan> getAllPengaduan() {
        ArrayList<Pengaduan> daftar = new ArrayList<>();
        String sql = "SELECT * FROM pengaduan ORDER BY id_pengaduan";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                daftar.add(petakanBaris(rs));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data pengaduan - " + e.getMessage());
        }
        return daftar;
    }

    //READ (by ID)
    @Override
    public Pengaduan cariPengaduanById(String idPengaduan) {
        String sql = "SELECT * FROM pengaduan WHERE id_pengaduan = ?";
        Pengaduan pengaduan = null;
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idPengaduan);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    pengaduan = petakanBaris(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari pengaduan - " + e.getMessage());
        }
        return pengaduan;
    }

    //READ (by status)
    @Override
    public ArrayList<Pengaduan> cariPengaduanByStatus(String status) {
        ArrayList<Pengaduan> hasilFilter = new ArrayList<>();
        String sql = "SELECT * FROM pengaduan WHERE status = ? ORDER BY id_pengaduan";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hasilFilter.add(petakanBaris(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari pengaduan berdasarkan status - " + e.getMessage());
        }
        return hasilFilter;
    }

    //READ (by jenis)
    @Override
    public ArrayList<Pengaduan> cariPengaduanByJenis(String jenisPengaduan) {
        ArrayList<Pengaduan> hasilFilter = new ArrayList<>();
        String sql = "SELECT * FROM pengaduan WHERE jenis_pengaduan = ? ORDER BY id_pengaduan";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jenisPengaduan);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hasilFilter.add(petakanBaris(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari pengaduan berdasarkan jenis - " + e.getMessage());
        }
        return hasilFilter;
    }

    //READ (by jenis dan status)
    @Override
    public ArrayList<Pengaduan> cariPengaduanByJenisDanStatus(String jenisPengaduan, String status) {
        ArrayList<Pengaduan> hasilFilter = new ArrayList<>();
        String sql = "SELECT * FROM pengaduan WHERE jenis_pengaduan = ? AND status = ? ORDER BY id_pengaduan";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jenisPengaduan);
            pstmt.setString(2, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hasilFilter.add(petakanBaris(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari pengaduan berdasarkan jenis dan status - " + e.getMessage());
        }
        return hasilFilter;
    }

    //UPDATE
    @Override
    public boolean ubahStatusPengaduan(String idPengaduan, String statusBaru) {
        String sql = "UPDATE pengaduan SET status = ? WHERE id_pengaduan = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, statusBaru);
            pstmt.setString(2, idPengaduan);

            int barisBerubah = pstmt.executeUpdate();
            return barisBerubah > 0;
        } catch (SQLException e) {
            System.out.println("Gagal mengubah status pengaduan - " + e.getMessage());
            return false;
        }
    }

    //DELETE
    @Override
    public boolean hapusPengaduan(String idPengaduan) {
        String sql = "DELETE FROM pengaduan WHERE id_pengaduan = ?";
        Connection conn = Koneksi.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idPengaduan);

            int barisBerubah = pstmt.executeUpdate();
            return barisBerubah > 0;
        } catch (SQLException e) {
            System.out.println("Gagal menghapus pengaduan - " + e.getMessage());
            return false;
        }
    }

    // Mengubah satu baris ResultSet menjadi objek Pengaduan.
    // Kolom tingkat_urgensi menentukan subclass yang dibuat.
    private Pengaduan petakanBaris(ResultSet rs) throws SQLException {
        Pengaduan pengaduan;

        if (rs.getString("tingkat_urgensi").equals("DARURAT")) {
            PengaduanDaruratBaru darurat = new PengaduanDaruratBaru(
                    rs.getString("id_pengaduan"),
                    rs.getString("nama_pelapor"),
                    rs.getString("jenis_pengaduan"),
                    rs.getString("isi_pengaduan"),
                    rs.getString("tanggal_pengaduan"),
                    rs.getString("kontak_darurat"));
            darurat.setbatasResponJam(rs.getInt("batas_respon_jam"));
            pengaduan = darurat;
        } else {
            PengaduanBiasaBaru biasa = new PengaduanBiasaBaru(
                    rs.getString("id_pengaduan"),
                    rs.getString("nama_pelapor"),
                    rs.getString("jenis_pengaduan"),
                    rs.getString("isi_pengaduan"),
                    rs.getString("tanggal_pengaduan"));
            biasa.settargetSelesai(rs.getInt("target_selesai_hari"));
            pengaduan = biasa;
        }

        // Constructor selalu mengisi status awal, jadi status dari database dipasang di sini
        pengaduan.setStatus(rs.getString("status"));
        return pengaduan;
    }
}
