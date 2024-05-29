package com.example.barcodegcnew;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class ADB_Master extends SQLiteOpenHelper{

    //InnerClass, untuk mengatur artibut seperti Nama Tabel, nama-nama kolom dan Query
    static abstract class MyColumns_item implements BaseColumns{
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "tbl_barang";
        static final String idno = "idno";
        static final String kode = "kode";
        static final String nama = "nama";
        static final String harga = "harga";
        static final String harga_beli = "harga_beli";
        static final String tgl_rec = "tgl_rec";

    }



    static abstract class MyColumns_tokoprofile implements BaseColumns{
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "toko_profile";
        static final String idno = "idno";
        static final String nama_toko = "nama_toko";
        static final String alamat_toko = "alamat_toko";
        static final String no_hp_toko = "no_hp_toko";
        static final String tgl_rec = "tgl_rec";

    }

    static abstract class MyColumns_barcode implements BaseColumns{
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "tbl_barctran";
        static final String idno = "idno";
        static final String barcode = "barcode";
        static final String noref = "noref";
        static final String typetran = "typetran";
        static final String lokoven = "lokoven";
        static final String namaoven = "namaoven";
        static final String namaoven2 = "namaoven2";
        static final String temp1 = "temp1";
        static final String temp2 = "temp2";
        static final String temp3 = "temp3";
        static final String temp4 = "temp4";
        static final String temp5 = "temp5";
        static final String tagtran = "tagtran";
        static final String tgl_rec = "tgl_rec";

    }


    private static final String NamaDatabase = "master.db";
    private static final int VersiDatabase = 13;

    //Query yang digunakan untuk membuat Tabel
    private static final String SQL_CREATE_ENTRIES_item = "CREATE TABLE "+ MyColumns_item.NamaTabel+
            "("+ MyColumns_item.idno+" INTEGER PRIMARY KEY, "+ MyColumns_item.kode+" TEXT NOT NULL, "+ MyColumns_item.nama+
            " TEXT NOT NULL, "+ MyColumns_item.harga+" DOUBLE NOT NULL,"+ MyColumns_item.harga_beli+" DOUBLE NOT NULL,"+ MyColumns_item.tgl_rec+" DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M', 'NOW','localtime')))";


    private static final String SQL_CREATE_ENTRIES_barcode = "CREATE TABLE "+ MyColumns_barcode.NamaTabel+
            "("+ MyColumns_barcode.idno+" INTEGER PRIMARY KEY, "+ MyColumns_barcode.barcode+" TEXT NOT NULL, "+ MyColumns_barcode.noref+
            " TEXT NOT NULL, "+ MyColumns_barcode.typetran+" TEXT NOT NULL,"+ MyColumns_barcode.lokoven+" TEXT  NOT NULL,"
            + MyColumns_barcode.namaoven+" TEXT  NOT NULL,"+ MyColumns_barcode.namaoven2+" TEXT  NOT NULL," + MyColumns_barcode.temp1+" TEXT  NOT NULL,"
            + MyColumns_barcode.temp2+" TEXT  NOT NULL,"+ MyColumns_barcode.temp3+" TEXT  NOT NULL,"
            + MyColumns_barcode.temp4+" TEXT  NOT NULL,"+ MyColumns_barcode.temp5+" TEXT  NOT NULL,"
            + MyColumns_barcode.tagtran+" INTEGER  NOT NULL,"
            + MyColumns_item.tgl_rec+" DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M:%S', 'NOW','localtime')))";



    private static final String SQL_CREATE_ENTRIES_tokoprofile = "CREATE TABLE "+ MyColumns_tokoprofile.NamaTabel+
            "("+ MyColumns_tokoprofile.idno+" INTEGER PRIMARY KEY, "+ MyColumns_tokoprofile.nama_toko+" TEXT NOT NULL, "+ MyColumns_tokoprofile.alamat_toko+
            " TEXT NOT NULL, "+ MyColumns_tokoprofile.no_hp_toko+" TEXT NOT NULL,"+ MyColumns_tokoprofile.tgl_rec+" DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M', 'NOW','localtime')))";



    //Query yang digunakan untuk mengupgrade Tabel
    private static final String SQL_DELETE_ENTRIES_item = "DROP TABLE IF EXISTS "+ MyColumns_item.NamaTabel;
    private static final String SQL_DELETE_ENTRIES_barcode = "DROP TABLE IF EXISTS "+ MyColumns_barcode.NamaTabel;
    private static final String SQL_DELETE_ENTRIES_tokoprofile = "DROP TABLE IF EXISTS "+ MyColumns_tokoprofile.NamaTabel;


    public List<BarangBarcodeNOREFLokal> getSemuaINPRS(String typtran){
        List<BarangBarcodeNOREFLokal> userList2 = new ArrayList<>();
        String selectQuery = "SELECT noref,lokoven,namaoven,temp2,typetran,min(tagtran) as tagtran,max(tgl_rec) as tgl_rec,count(barcode) as jum FROM " +ADB_Master.MyColumns_barcode.NamaTabel+" where typetran= '"+typtran+"'"+" group by noref,lokoven,namaoven,temp2,typetran order by idno desc";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                BarangBarcodeNOREFLokal userin = new BarangBarcodeNOREFLokal(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7));
                userList2.add(userin);
            } while (cursor.moveToNext());
        }
        return userList2;
    }

    public List<BarangBarcode> getSemuaItem(String noref){
        List<BarangBarcode> userList2 = new ArrayList<>();
        String selectQuery = "SELECT idno,barcode,tgl_rec,tagtran FROM " +ADB_Master.MyColumns_barcode.NamaTabel+" where noref= '"+noref+"'"+" order by idno desc";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                BarangBarcode userin = new BarangBarcode(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                userList2.add(userin);
            } while (cursor.moveToNext());
        }
        return userList2;
    }

    public List<BarangDialog> getItemDialog(String itemcode){
        List<BarangDialog> userList2 = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + ADB_Master.MyColumns_item.NamaTabel+" where "
                + MyColumns_item.kode+" LIKE '%"+itemcode+"%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                BarangDialog userin = new BarangDialog(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3));
                userList2.add(userin);
            } while (cursor.moveToNext());
        }
        return userList2;
    }


    //updateData
    public void updateData(String nama_toko, String alamat_toko, String no_hp_toko, int idno){
        SQLiteDatabase database = getWritableDatabase();
        //query to update record
        String sql = "UPDATE toko_profile SET nama_toko=?, alamat_toko=?, no_hp_toko=? WHERE idno=?";

        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, nama_toko);
        statement.bindString(2, alamat_toko);
        statement.bindString(3, no_hp_toko);
        statement.bindDouble(5, (double)idno);
        statement.execute();
        database.close();
    }

    //deleteData
    public void deleteData(int idno){
        SQLiteDatabase database = getWritableDatabase();
        //query to delete record using id
        String sql = "DELETE FROM toko_profile WHERE idno=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)idno);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    ADB_Master(Context context) {
        super(context, NamaDatabase, null, VersiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_item);
        db.execSQL(SQL_CREATE_ENTRIES_barcode);
        db.execSQL(SQL_CREATE_ENTRIES_tokoprofile);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES_item);
        db.execSQL(SQL_DELETE_ENTRIES_barcode);
        db.execSQL(SQL_DELETE_ENTRIES_tokoprofile);
        onCreate(db);
        //  dataAwaluser(db);
        // dataAwaluserHak(db);
    }


}
