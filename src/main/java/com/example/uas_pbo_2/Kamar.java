package com.example.uas_pbo_2;

public class Kamar {
    private String kode_kamar;
    private int no_kamar;
    private String jenis_kamar;
    private int harga_jual;

    public Kamar(){

    }

    public Kamar(String kode_kamar, int no_kamar, String jenis_kamar, int harga_jual) {
        this.kode_kamar = kode_kamar;
        this.no_kamar = no_kamar;
        this.jenis_kamar = jenis_kamar;
        this.harga_jual = harga_jual;
    }

    public String getKode_kamar() {
        return kode_kamar;
    }

    public void setKode_kamar(String kode_kamar) {
        this.kode_kamar = kode_kamar;
    }

    public String getNo_kamar() {
        return String.valueOf(Integer.parseInt(String.valueOf(no_kamar)));
    }

    public void setNo_kamar(int no_kamar) {
        this.no_kamar = no_kamar;
    }

    public String getJenis_kamar() {
        return jenis_kamar;
    }

    public void setJenis_kamar(String jenis_kamar) {
        this.jenis_kamar = jenis_kamar;
    }

    public String getHarga_jual() {
        return String.valueOf(Integer.parseInt(String.valueOf(harga_jual)));
    }

    public void setHarga_jual(int harga_jual) {
        this.harga_jual = harga_jual;
    }
}
