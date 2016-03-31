package com.example.pegawai.adapter;

/**
 * Created by subki on 11/27/2015.
 */
public class EmpMod {
    private String nik;
    private String nama;
    private String tempat_lahir;
    private String tanggal_lahir;
    private String jenkel;
    private String agama;
    private String status;
    private String alamat;
    private String hp;
    private String pendidikan;
    private String tgl_masuk;
    private String jabatan;
    private String profile;

    public void setNik(String nik) {
        this.nik = nik;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setTempat_lahir(String tempat_lahir) {        this.tempat_lahir = tempat_lahir;    }
    public void setTanggal_lahir(String tanggal_lahir) {        this.tanggal_lahir = tanggal_lahir;    }
    public void setJenkel(String jenkel) {        this.jenkel = jenkel;    }
    public void setAgama(String agama) {        this.agama = agama;    }
    public void setStatus(String status) {        this.status = status;    }
    public void setAlamat(String alamat) {       this.alamat = alamat;    }
    public void setHp(String hp) {        this.hp = hp;    }
    public void setPendidikan(String pendidikan) {        this.pendidikan = pendidikan;    }
    public void setTgl_masuk(String tgl_masuk) {        this.tgl_masuk = tgl_masuk;    }
    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
    public void setProfile(String profile) {this.profile = profile;}

    public String getNik() {
        return nik;
    }
    public String getNama() {
        return nama;
    }
    public String getTempat_lahir() {        return tempat_lahir;    }
    public String getTanggal_lahir() {return tanggal_lahir;}
    public String getJenkel() {        return jenkel;    }
    public String getAgama() {        return agama;    }
    public String getStatus() {        return status;    }
    public String getAlamat() {        return alamat;    }
    public String getHp() {        return hp;    }
    public String getPendidikan() {        return pendidikan;    }
    public String getTgl_masuk() {        return tgl_masuk;    }
    public String getJabatan() {return jabatan;}
    public String getProfile() {return profile;}
}
