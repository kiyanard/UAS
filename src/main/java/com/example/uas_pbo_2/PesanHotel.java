package com.example.uas_pbo_2;

public class PesanHotel {

    private String id_transaksi;
    private String nama;
    private String kode_kamar;
    private int lama_menginap;
    private String tanggal;
    private int total_harga;

    public PesanHotel(String id_transaksi, String nama, String kode_kamar, int lama_menginap, String tanggal, int total_harga) {
        this.id_transaksi = id_transaksi;
        this.nama = nama;
        this.kode_kamar = kode_kamar;
        this.lama_menginap = lama_menginap;
        this.tanggal = tanggal;
        this.total_harga = total_harga;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKode_kamar() {
        return kode_kamar;
    }

    public void setKode_kamar(String kode_kamar) {
        this.kode_kamar = kode_kamar;
    }

    public String getLama_menginap() {
        return String.valueOf(Integer.parseInt(String.valueOf(lama_menginap)));
    }

    public void setLama_menginap(int lama_menginap) {
        this.lama_menginap = lama_menginap;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTotal_harga() {
        return String.valueOf(Integer.parseInt(String.valueOf(total_harga)));
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }
}
