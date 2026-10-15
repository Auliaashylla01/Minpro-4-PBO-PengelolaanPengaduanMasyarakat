/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
/**
 * Class ini merupakan subclass dari class pengaduan sebagai superclass nya. 
 * Atribut tambahan yang digunakan untuk class ini adalah batasResponJam sebagai target waktu respon awal
 * dan kontakDarurat sebagai nomor yang bisa dihubungi petugas terkait informasi lebih lanjut terkait situasi 
*/ 
//Anotasi ORM: baris tabel dengan tingkat_urgensi = 'DARURAT' dibaca sebagai objek PengaduanDarurat
@Entity
@DiscriminatorValue("DARURAT")
public class PengaduanDaruratBaru extends Pengaduan implements PenangananKhusus{
    @Column(name = "kontak_darurat")
    //kontak darurat dari pelapor untuk kemudian dihubungi oleh petugas
    private String kontakDarurat;
    @Column(name = "batas_respon_jam")
    private int batasResponJam;

    //Constructor kosong, dibutuhkan ORM
    protected PengaduanDaruratBaru() {
    }
    
    //constructor yang digunakan untuk mengisi data umum dari class pengaduan 
    public PengaduanDaruratBaru (String idPengaduan, String namaPelapor,String jenisPengaduan, 
            String isiPengaduan,String tanggalPengaduan, String kontakDarurat){
            super(idPengaduan, namaPelapor, jenisPengaduan, 
                    isiPengaduan, tanggalPengaduan);
            //batas waktu respon jam sebagai default dengan standar 24 jam untuk respons awal
            this.batasResponJam = 24;
            this.kontakDarurat = kontakDarurat;
    }
    //Penerapan Getter Pada Sistem digunakan untuk mengambil atau membaca nilai atribut
    public String getkontakDarurat(){
        return kontakDarurat;
    }
    public int getbatasResponJam (){
        return batasResponJam;
    }
    public void setkontakDarurat(String kontakDarurat){
        this.kontakDarurat = kontakDarurat;
    }
    //setter dan validasi nilai jam bersifat positif
    public void setbatasResponJam(int batasResponJam){
        if (batasResponJam > 0){
            this.batasResponJam = batasResponJam;
        }  
    }
     //override method dari superclass untuk nilai tingkat urgensi darurat
    @Override
    public String getTingkatUrgensi (){
        return "DARURAT";
    }
    //override method dari superclass untuk detail pengaduan umum, tingkat urgensi, 
    //kontak darurat dan batas respon jam nya.
    @Override
    public String getDetailPengaduan(){
        return super.getDetailPengaduan() + "\n" +
                "Kontak Darurat  : " + kontakDarurat + "\n" +
                "Batas Respon Jam : " + batasResponJam + " Jam";
    }
    
    // Implementasi interface PenangananKhusus
    @Override
    public int getTargetWaktuResponJam() {
        return batasResponJam;
    }

    // Implementasi interface PenangananKhusus
    @Override
    public void kirimNotifikasiDarurat() {
        System.out.println("\n[NOTIFIKASI URGENT] Mengirim pesan darurat ke: " + kontakDarurat);
        System.out.println("Target respons awal petugas: " + getTargetWaktuResponJam() + " jam");
    }
}

