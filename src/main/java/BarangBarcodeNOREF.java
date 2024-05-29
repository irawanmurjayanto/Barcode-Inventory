package com.example.barcodegcnew;

import java.io.Serializable;

public class BarangBarcodeNOREF implements Serializable{



    private String noref="";
    private String lokoven="";
    private String namaoven="";
    private String namaoven2="";
    private String typetran="";
    private String tagtran="";


    private String tgl_rec="";
    private String barcode="";

    private String kondisi="";
    private Double qtygc;
    private String remarks="";
    private String item_code="";
    private String item_desc="";
    private String user_name="";

    private int idno;

    public int getIdno() {
        return idno;
    }

    public void setIdno(int idno) {
        this.idno = idno;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTgl_rec() {
        return tgl_rec;
    }

    public void setTgl_rec(String tgl_rec) {
        this.tgl_rec = tgl_rec;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public Double getQtygc() {
        return qtygc;
    }

    public void setQtygc(Double qtygc) {
        this.qtygc = qtygc;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
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
