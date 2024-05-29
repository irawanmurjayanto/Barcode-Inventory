package com.example.barcodegcnew;

import java.io.Serializable;

public class Barang implements Serializable{

    private int id=0;

    private String kode="";

    private String nama="";

    private double harga=0 ;

    private double harga_beli=0 ;



    public Barang(int idno, String kode, String nama, double harga, double harga_beli) {
        this.id = idno;
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.harga_beli = harga_beli;
    }



    public int getId() {

        return id;

    }

    public void setId(int id) {

        this.id = id;

    }



    public String getKode() {

        return kode;

    }

    public void setKode(String kode) {

        this.kode = kode;

    }



    public String getNama() {

        return nama;

    }

    public void setNama(String nama) {

        this.nama = nama;

    }



    public double getHarga() {

        return harga;

    }

    public void setHarga(double harga) {

        this.harga = harga;

    }

    public double getHarga_beli() {

        return harga_beli;

    }

    public void setHarga_beli(double harga_beli) {

        this.harga_beli = harga_beli;

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
