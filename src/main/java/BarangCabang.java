package com.example.barcodegcnew;

import java.io.Serializable;


public class BarangCabang implements Serializable {

private String idno="";
private String kodecab="";
private String cabang="";
private String alamat="";
private String groupho="";
private Boolean switch1=true;
private String noref="";
private String tgl_rec="";
private String barcode="";
private String typetran="";
private String kondisi="";
private Double qtygc;
private String remarks="";
private String item_code="";
private String item_desc="";
private String tujuan="";
private String jumbarcode="";
    private String noinv="";

    public String getNoinv() {
        return noinv;
    }

    public void setNoinv(String noinv) {
        this.noinv = noinv;
    }

    public String getJumbarcode() {
        return jumbarcode;
    }

    public void setJumbarcode(String jumbarcode) {
        this.jumbarcode = jumbarcode;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    private String user_name="";

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTypetran() {
        return typetran;
    }

    public void setTypetran(String typetran) {
        this.typetran = typetran;
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

    public Boolean getSwitch()
    {
        return switch1;
    }

    public void setSwitch(Boolean switch1)
    {
        this.switch1=switch1;
    }

public String getIdno()
{
return idno;
}

public void setIdno(String idno)
{
    this.idno=idno;
}


public String getKodecab()
{
return kodecab;
}
public void setKodecab(String kodecab)
{
    this.kodecab=kodecab;
}



public String getCabang()
{
    return cabang;
}
    public void setcabang(String cabang)
    {
        this.cabang=cabang;
    }



    public String getAlamat()
    {
        return alamat;
    }
    public void setAlamat(String alamat)
    {
        this.alamat=alamat;
    }

    public String getGroupho()
    {
        return groupho;
    }
    public void setGroupho(String groupho)
    {
        this.groupho=groupho;
    }



}
