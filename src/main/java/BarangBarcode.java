package com.example.barcodegcnew;

import java.io.Serializable;

public class BarangBarcode implements Serializable{

    private int id=0;

    private String barcode="";

    private String tglrec="";

    private String tagtran="";


    public BarangBarcode(int id, String barcode, String tglrec, String tagtran) {
        this.id = id;
        this.barcode = barcode;
        this.tglrec = tglrec;
        this.tagtran = tagtran;
    }

    public String getTagtran() {
        return tagtran;
    }

    public void setTagtran(String tagtran) {
        this.tagtran = tagtran;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTglrec() {
        return tglrec;
    }

    public void setTglrec(String tglrec) {
        this.tglrec = tglrec;
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
