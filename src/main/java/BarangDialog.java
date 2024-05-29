package com.example.barcodegcnew;

import java.io.Serializable;

public class BarangDialog implements Serializable {

    private int id=0;

    private String kode="";

    private String nama="";

    private Double harga_jual=0.0;


    public BarangDialog(int idno, String kode, String nama, double harga_jual) {
        this.id = idno;
        this.kode = kode;
        this.nama = nama;
        this.harga_jual = harga_jual;
    }




    public int getID() {

        return id;

    }

    public void setID(int id) {

        this.id = id;

    }


    public void setKode(String kode) {

        this.kode = kode;

    }


    public String getKode() {

        return kode;

    }



    public String getNama() {

        return nama;

    }

    public void setNama(String nama) {

        this.nama = nama;

    }


    public Double getHarga_jual() {

        return harga_jual;

    }

    public void setHarga_jual(Double harga_jual) {

        this.harga_jual = harga_jual;

    }



/*
        @Override

        public String toString() {

            return "Barang{" +
                    "id='" + id + '\'' +
                    ", kode='" + kode + '\'' +
                    ", nama='" + nama + '\'' +
                    ", harga='" + harga + '\'' +
                    ", harga_beli='" + harga_beli + '\'' +
                    '}';

        }
*/
}



