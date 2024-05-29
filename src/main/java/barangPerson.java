package com.example.barcodegcnew;

import java.io.Serializable;

public class barangPerson implements Serializable {

    private int idno=0;

    private String NIK="";

    private String nama="";

    private String section1="";

    private String jabatan="";

    private String macadd="";

    private String statusemp="";

    private String item_code="";

    private String item_desc="";

    private String barcode="";

    private String remarks="";


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    private Double qty=0.0;
    private Double qtydamage=0.0;
    private Double qtytotal=0.0;

    public Double getQtydamage() {
        return qtydamage;
    }

    public void setQtydamage(Double qtydamage) {
        this.qtydamage = qtydamage;
    }

    public Double getQtytotal() {
        return qtytotal;
    }

    public void setQtytotal(Double qtytotal) {
        this.qtytotal = qtytotal;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
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

    public int getIDNO() {

        return idno;

    }

    public void setIDNO(int idno) {

        this.idno = idno;

    }


    public String getNIK() {

        return NIK;

    }


    public void setNIK(String NIK) {

        this.NIK = NIK;

    }





    public String getNama() {

        return nama;

    }

    public void setNama(String nama) {

        this.nama = nama;

    }


    public String getSection1() {

        return section1;

    }

    public void setSection1(String section1) {

        this.section1 = section1;

    }

    public String getJabatan() {

        return jabatan;

    }

    public void setJabatan(String jabatan) {

        this.jabatan = jabatan;

    }

    public String getMacadd() {

        return macadd;

    }

    public void setMacadd(String macadd) {

        this.macadd = macadd;

    }

    public String getStatusemp() {

        return statusemp;

    }

    public void setStatusemp(String statusemp) {

        this.statusemp = statusemp;

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
