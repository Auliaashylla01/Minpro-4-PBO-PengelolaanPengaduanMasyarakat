/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;
import java.util.ArrayList;
import model.Pengaduan;
/**
 *
 * @author ASUS
 */
public interface PengaduanDAO {
    //CREATE
    void tambahPengaduan(Pengaduan pengaduan);
    //READ
    ArrayList<Pengaduan> getAllPengaduan();
    Pengaduan cariPengaduanById(String idPengaduan);
    ArrayList<Pengaduan> cariPengaduanByStatus(String status);
    ArrayList<Pengaduan> cariPengaduanByJenis(String jenisPengaduan);
    ArrayList<Pengaduan> cariPengaduanByJenisDanStatus(String jenisPengaduan, String status);
    //UPDATE
    boolean ubahStatusPengaduan(String idPengaduan, String statusBaru);
    //DELETE
    boolean hapusPengaduan(String idPengaduan);
}
