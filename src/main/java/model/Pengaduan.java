/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
/**
 * Class ini digunakan untuk menyimpan atribut umum dari pengaduan masyarakat.
 * Class ini merupakan superclass dari pengaduanBiasa dan pengaduanDarurat.
 * Penerapan abstract sebagai dasar model pengaduan
 */
//Anotasi ORM: class ini dipetakan ke tabel "pengaduan".
//SINGLE_TABLE: subclass (biasa dan darurat) disimpan dalam satu tabel,
//dibedakan oleh kolom tingkat_urgensi.
@Entity
@Table(name = "pengaduan")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tingkat_urgensi", discriminatorType = DiscriminatorType.STRING)
public abstract class Pengaduan {
    //Data private yang hanya bisa diakses secara langsung dari dalam class ini
    @Id
    @Column(name = "id_pengaduan")
    private String idPengaduan;
    @Column(name = "nama_pelapor")
    private String namaPelapor;
    @Column(name = "jenis_pengaduan")
    private String jenisPengaduan;
    @Column(name = "isi_pengaduan")
    private String isiPengaduan;
    @Column(name = "tanggal_pengaduan")
    private String tanggalPengaduan;
    private String status;
    
    //Constructor kosong, dibutuhkan ORM untuk membuat objek dari data tabel
    protected Pengaduan() {
    }
    
    //Constructor dipanggil saat objek Pengaduan baru dibuat. 
    //Digunakan untuk mengisi nilai awal dari atribut pengaduan.
    public Pengaduan(String idPengaduan, String namaPelapor,
                     String jenisPengaduan, String isiPengaduan,
                     String tanggalPengaduan) {

        this.idPengaduan = idPengaduan;
        this.namaPelapor = namaPelapor;
        this.jenisPengaduan = jenisPengaduan;
        this.isiPengaduan = isiPengaduan;
        this.tanggalPengaduan = tanggalPengaduan;
        //Agar status awal selalu "Menunggi Konfirmasi Petugas" , baik dalam pengaduan darurat ataupun biasa
        this.status = "Menunggu Konfirmasi Petugas";
    }
    //Penerapan Getter Pada Sistem digunakan untuk mengambil atau membaca nilai atribut
    public String getIdPengaduan() {
        return idPengaduan;
    }
    public String getNamaPelapor() {
        return namaPelapor;
    }
    public String getJenisPengaduan() {
        return jenisPengaduan;
    }
    public String getIsiPengaduan() {
        return isiPengaduan;
    }
    public String getTanggalPengaduan() {
        return tanggalPengaduan;
    }
    public String getStatus() {
        return status;
    }
    // Penerapan Setter Pada Sistem digunakan untuk mengubah nilai atribut status
    public void setStatus(String status) {
        this.status = status;
    }
    //Digunakan untuk penerapan tingkat urgensi, 
    //sebagai bentuk penerapan abstract dan tingkat urgensi setiap subclass
    public abstract String getTingkatUrgensi();
            
    //Method yang digunakan untuk menampilkan informasi pengaduan atau method dasar di superclass
    public String getDetailPengaduan(){
        return "ID Pengaduan : " + idPengaduan + "\n"+
                "Pelapor : " + namaPelapor + "\n" +
                "Jenis Pengaduan : " + jenisPengaduan + "\n" +
                "Tingkat Urgensi : " + getTingkatUrgensi() + "\n" +
                "Isi Pengaduan : " + isiPengaduan + "\n" +
                "Tanggal : " + tanggalPengaduan + "\n" +
                "Status : " + status;
    }
}

