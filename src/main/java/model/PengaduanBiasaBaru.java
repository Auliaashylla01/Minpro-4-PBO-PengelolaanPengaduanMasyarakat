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
 * Atribut tambahan yang digunakan untuk class ini adalah targetSelesaiHari sebagai waktu atau standar waktu -
 * penanganan yang di tentukan atau default dari sistem
 * 
 */
//Anotasi ORM: baris tabel dengan tingkat_urgensi = 'BIASA' dibaca sebagai objek PengaduanBiasa
@Entity
@DiscriminatorValue("BIASA")
public class PengaduanBiasaBaru extends Pengaduan {
    @Column(name = "target_selesai_hari")
    //atribut yang digunakan sebagai target waktu penyelesaian 
    private int targetSelesaiHari;
    //Constructor kosong, dibutuhkan ORM
    protected PengaduanBiasaBaru() {
    }
    
     //constructor yang digunakan untuk mengisi data umum dari class pengaduan 
    public PengaduanBiasaBaru (String idPengaduan, String namaPelapor,
            String jenisPengaduan, String isiPengaduan,String tanggalPengaduan){
            super(idPengaduan, namaPelapor, jenisPengaduan, 
                    isiPengaduan, tanggalPengaduan);
            //set nilai default target waktu standar 7 hari sehingga pelapor tidak perlu mengisinya
            this.targetSelesaiHari = 7;
    }
    //Penerapan Getter Pada Sistem digunakan untuk mengambil atau membaca nilai atribut
    public int gettargetSelesaiHari(){
        return targetSelesaiHari;
    }
    //Setter dan validasi nilai harus bersifat positif 
    public void settargetSelesai (int targetSelesaiHari){
        if (targetSelesaiHari > 0 ){
            this.targetSelesaiHari = targetSelesaiHari;
        }         
}
    //override method dari superclass untuk nilai tingkat urgensi biasa
    @Override
    public String getTingkatUrgensi (){
        return "BIASA";
    }
    //override method dari superclass untuk detail pengaduan umum, tingkat urgensi dan targetwaktu penyelesaian nya.
    @Override
    public String getDetailPengaduan(){
        return super.getDetailPengaduan() + "\n" + "Target Selesai Hari: " + targetSelesaiHari 
                + " hari (standar penanganan, bukan batas mutlak)";
    }
    
}
