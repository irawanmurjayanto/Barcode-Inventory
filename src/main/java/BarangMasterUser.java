package com.example.barcodegcnew;


import java.io.Serializable;

public class BarangMasterUser implements Serializable {

    private int idno=0;

    private int idoven=0;

    private String ovenname="";

    public int getIdno() {
        return idno;
    }

    public void setIdno(int idno) {
        this.idno = idno;
    }

    public int getIdoven() {
        return idoven;
    }

    public void setIdoven(int idoven) {
        this.idoven = idoven;
    }

    public String getOvenname() {
        return ovenname;
    }

    public void setOvenname(String ovenname) {
        this.ovenname = ovenname;
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



