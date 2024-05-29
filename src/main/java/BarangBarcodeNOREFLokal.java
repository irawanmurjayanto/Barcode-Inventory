package com.example.barcodegcnew;

import java.io.Serializable;

public class BarangBarcodeNOREFLokal implements Serializable{



    private String noref="";
    private String lokoven="";
    private String namaoven="";
    private String namaoven2="";
    private String typetran="";
    private String tagtran="";
    private String tgl_rec="";
    private int jumbarc=0;

    public BarangBarcodeNOREFLokal(String noref, String lokoven, String namaoven, String namaoven2, String typetran, String tagtran, String tgl_rec, int jumbarc) {
        this.noref = noref;
        this.lokoven = lokoven;
        this.namaoven = namaoven;
        this.namaoven2 = namaoven2;
        this.typetran = typetran;
        this.tagtran = tagtran;
        this.tgl_rec = tgl_rec;
        this.jumbarc = jumbarc;
    }

    public int getJumbarc() {
        return jumbarc;
    }

    public void setJumbarc(int jumbarc) {
        this.jumbarc = jumbarc;
    }

    public String getTgl_rec() {
        return tgl_rec;
    }

    public void setTgl_rec(String tgl_rec) {
        this.tgl_rec = tgl_rec;
    }

    public String getNoref() {
        return noref;
    }

    public void setNoref(String noref) {
        this.noref = noref;
    }

    public String getLokoven() {
        return lokoven;
    }

    public void setLokoven(String lokoven) {
        this.lokoven = lokoven;
    }

    public String getNamaoven() {
        return namaoven;
    }

    public void setNamaoven(String namaoven) {
        this.namaoven = namaoven;
    }

    public String getNamaoven2() {
        return namaoven2;
    }

    public void setNamaoven2(String namaoven2) {
        this.namaoven2 = namaoven2;
    }

    public String getTypetran() {
        return typetran;
    }

    public void setTypetran(String typetran) {
        this.typetran = typetran;
    }

    public String getTagtran() {
        return tagtran;
    }

    public void setTagtran(String tagtran) {
        this.tagtran = tagtran;
    }

    /* @Override

    public String toString() {

        return "Barang{" +
        "id='" + id + '\'' +
        ", kode='" + kode + '\'' +
        ", nama='" + nama + '\'' +
        ", harga='" + harga + '\'' +
                ", harga_beli='" + harga_beli + '\'' +
        '}';

    }*/

}
