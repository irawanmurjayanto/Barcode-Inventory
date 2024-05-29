package com.example.barcodegcnew;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class ADB_Trans extends SQLiteOpenHelper{

    //InnerClass, untuk mengatur artibut seperti Nama Tabel, nama-nama kolom dan Query
    static abstract class MyColumns_trans1 implements BaseColumns{
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "trans1";
        static final String idno = "idno";
        static final String no = "notrans";
        static final String tgl_rec = "tgl_rec";

    }



    static abstract class MyColumns_bedul implements BaseColumns {
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "bedul";
        static final String idno = "idno";
        static final String typetrans = "typetrans";
        static final String notrans_link = "notrans_link";
        static final String item_code = "item_code";
        static final String item_desc = "item_desc";
        static final String tgl_doc = "tgl_doc";
        static final String qty= "qty";
        static final String Harga_beli = "harga_beli";
        static final String Harga_jual = "harga_jual";
        static final String user_name = "user_name";
        static final String disc_val = "disc_val";
        static final String remarks = "remarks";
        static final String tgl_rec = "tgl_rec";
    }



    static abstract class MyColumns_trans1sub implements BaseColumns {
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "trans1sub";
        static final String idno = "idno";
        static final String typetrans = "typetrans";
        static final String notrans_link = "notrans_link";
        static final String item_code = "item_code";
        static final String item_desc = "item_desc";
        static final String tgl_doc = "tgl_doc";
        static final String qty= "qty";
        static final String Harga_beli = "harga_beli";
        static final String Harga_jual = "harga_jual";
        static final String user_name = "user_name";
        static final String disc_val = "disc_val";
        static final String remarks = "remarks";
        static final String tgl_rec = "tgl_rec";
    }


    static abstract class MyColumns_trans1subtemp implements BaseColumns {
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "trans1subtemp";
        static final String idno = "idno";
        static final String item_code = "item_code";
        static final String item_desc = "item_desc";
        static final String AWALqty = "AWALqty";
        static final String INqty = "INqty";
        static final String TRFqty = "TRFqty";
        static final String POSqty = "POSqty";
        static final String user_name = "user_name";
        static final String typetrans = "typetrans";
        static final String STKqty = "STKqty";
        static final String ADJqty = "ADJqty";
        static final String tgl_rec = "tgl_rec";
    }

    static abstract class MyColumns_trans1subtempawal implements BaseColumns {
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "trans1subtempawal";
        static final String idno = "idno";
        static final String item_code = "item_code";
        static final String item_desc = "item_desc";
        static final String AWALqty = "AWALqty";
        static final String INqty = "INqty";
        static final String TRFqty = "TRFqty";
        static final String POSqty = "POSqty";
        static final String user_name = "user_name";
        static final String ADJqty = "ADJqty";

        static final String tgl_rec = "tgl_rec";
    }

    static abstract class MyColumns_tbstockopname implements BaseColumns {
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "tbstockopname";
        static final String idno = "idno";
        static final String item_code = "item_code";
        static final String item_desc = "item_desc";
        static final String akhirqty = "akhirqty";
        static final String opnameqty = "opnameqty";
        static final String user_name = "user_name";
        static final String tgl_rec = "tgl_rec";
    }




    private static final String NamaDatabase = "mastertrans.db";
    private static final int VersiDatabase =8;

    //Query yang digunakan untuk membuat Tabel
    private static final String SQL_CREATE_ENTRIES_trans1 = "CREATE TABLE "+ MyColumns_trans1.NamaTabel+
            "("+ MyColumns_trans1.idno+" INTEGER PRIMARY KEY, "+ MyColumns_trans1.no+" INTEGER NOT NULL, "+ MyColumns_trans1.tgl_rec+" DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M', 'NOW','localtime')))";


    private static final String SQL_CREATE_ENTRIES_bedul = "CREATE TABLE "+ MyColumns_bedul.NamaTabel+
            "("+ MyColumns_bedul.idno+" INTEGER PRIMARY KEY, "+ MyColumns_bedul.item_code+" TEXT NOT NULL, "+ MyColumns_bedul.item_desc+" TEXT NOT NULL, "+ MyColumns_bedul.Harga_jual+" DOUBLE NOT NULL, "+ MyColumns_bedul.tgl_rec+" DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M', 'NOW','localtime')))";



    private static final String SQL_CREATE_ENTRIES_trans1sub = "CREATE TABLE "+ MyColumns_trans1sub.NamaTabel+
            "("+ MyColumns_trans1sub.idno+" INTEGER PRIMARY KEY, "+ MyColumns_trans1sub.typetrans+" TEXT NOT NULL, "+ MyColumns_trans1sub.notrans_link+
            " TEXT NOT NULL, "+ MyColumns_trans1sub.item_code+" TEXT NOT NULL,"+ MyColumns_trans1sub.item_desc+" TEXT NOT NULL,"+ MyColumns_trans1sub.tgl_doc+" datetime default current_timestamp,"+ MyColumns_trans1sub.qty+" DOUBLE ,"+ MyColumns_trans1sub.Harga_beli+" DOUBLE ,"+ MyColumns_trans1sub.Harga_jual+" DOUBLE ,"+ MyColumns_trans1sub.disc_val+" DOUBLE ,"+ MyColumns_trans1sub.remarks+" TEXT ,"+ MyColumns_trans1sub.user_name+" TEXT ,"+ MyColumns_trans1sub.tgl_rec+" DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M', 'NOW','localtime')))";


    private static final String SQL_CREATE_ENTRIES_trans1subtemp = "CREATE TABLE "+ MyColumns_trans1subtemp.NamaTabel+
            "("+ MyColumns_trans1subtemp.idno+" INTEGER PRIMARY KEY, "+ MyColumns_trans1subtemp.item_code+" TEXT , "+ MyColumns_trans1subtemp.item_desc+
            " TEXT , "+ MyColumns_trans1subtemp.AWALqty+" DOUBLE ,"+ MyColumns_trans1subtemp.INqty+" DOUBLE ,"+ MyColumns_trans1subtemp.TRFqty+" DOUBLE ,"+ MyColumns_trans1subtemp.POSqty+" DOUBLE ,"+ MyColumns_trans1subtemp.ADJqty+" DOUBLE ,"+ MyColumns_trans1subtemp.user_name+" TEXT ,"+ MyColumns_trans1subtemp.typetrans+" TEXT ,"+ MyColumns_trans1subtemp.STKqty+" DOUBLE ,"+ MyColumns_trans1subtemp.tgl_rec+" DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M', 'NOW','localtime')))";


    private static final String SQL_CREATE_ENTRIES_trans1subtempawal = "CREATE TABLE "+ MyColumns_trans1subtempawal.NamaTabel+
            "("+ MyColumns_trans1subtempawal.idno+" INTEGER PRIMARY KEY, "+ MyColumns_trans1subtempawal.item_code+" TEXT , "+ MyColumns_trans1subtempawal.item_desc+
            " TEXT , "+ MyColumns_trans1subtempawal.AWALqty+" DOUBLE ,"+ MyColumns_trans1subtempawal.INqty+" DOUBLE ,"+ MyColumns_trans1subtempawal.TRFqty+" DOUBLE ,"+ MyColumns_trans1subtempawal.POSqty+" DOUBLE ,"+ MyColumns_trans1subtempawal.ADJqty+" DOUBLE ,"+ MyColumns_trans1subtempawal.user_name+" TEXT ,"+ MyColumns_trans1subtempawal.tgl_rec+" DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M', 'NOW','localtime')))";


    private static final String SQL_CREATE_ENTRIES_tbstockopname = "CREATE TABLE "+ MyColumns_tbstockopname.NamaTabel+
            "("+ MyColumns_tbstockopname.idno+" INTEGER PRIMARY KEY, "+ MyColumns_tbstockopname.item_code+" TEXT , "+ MyColumns_tbstockopname.item_desc+
            " TEXT , "+ MyColumns_tbstockopname.akhirqty+" DOUBLE ,"+ MyColumns_tbstockopname.opnameqty+" DOUBLE ,"+ MyColumns_tbstockopname.user_name+" TEXT ,"+ MyColumns_tbstockopname.tgl_rec+" DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M', 'NOW','localtime')))";




    //Query yang digunakan untuk mengupgrade Tabel
    private static final String SQL_DELETE_ENTRIES_bedul = "DROP TABLE IF EXISTS "+ MyColumns_bedul.NamaTabel;
    private static final String SQL_DELETE_ENTRIES_trans1 = "DROP TABLE IF EXISTS "+ MyColumns_trans1.NamaTabel;
    private static final String SQL_DELETE_ENTRIES_trans1sub = "DROP TABLE IF EXISTS "+ MyColumns_trans1sub.NamaTabel;
    private static final String SQL_DELETE_ENTRIES_trans1subtemp = "DROP TABLE IF EXISTS "+ MyColumns_trans1subtemp.NamaTabel;
    private static final String SQL_DELETE_ENTRIES_trans1subtempawal = "DROP TABLE IF EXISTS "+ MyColumns_trans1subtempawal.NamaTabel;
    private static final String SQL_DELETE_ENTRIES_stockopname = "DROP TABLE IF EXISTS "+ MyColumns_tbstockopname.NamaTabel;

    public List<String> getSemuaItemBedul(){
        List<String> userList2 = new ArrayList<>();
        String selectQuery = "SELECT harga_jual FROM " + ADB_Trans.MyColumns_trans1sub.NamaTabel;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
               // BarangTranPos2 userin = new BarangTranPos2(cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6));
                userList2.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return userList2;
    }


    public List<String> getSemuaItemTest(){
        List<String> userList = new ArrayList<>();
        String selectQuery = "SELECT hak FROM " + ADB_User.MyColumnsHak.NamaTabel;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                // BarangMasterUser userin = new BarangMasterUser(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                userList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return userList;
    }


    public List<Barang> getSemuaItem(){
        List<Barang> userList2 = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + ADB_Trans.MyColumns_trans1sub.NamaTabel;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Barang userin = new Barang(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getDouble(4));
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


    ADB_Trans(Context context) {
        super(context, NamaDatabase, null, VersiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_bedul);
        db.execSQL(SQL_CREATE_ENTRIES_trans1);
        db.execSQL(SQL_CREATE_ENTRIES_trans1sub);
        db.execSQL(SQL_CREATE_ENTRIES_trans1subtemp);
        db.execSQL(SQL_CREATE_ENTRIES_trans1subtempawal);
        db.execSQL(SQL_CREATE_ENTRIES_tbstockopname);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES_bedul);
        db.execSQL(SQL_DELETE_ENTRIES_trans1);
        db.execSQL(SQL_DELETE_ENTRIES_trans1sub);
        db.execSQL(SQL_DELETE_ENTRIES_trans1subtemp);
        db.execSQL(SQL_DELETE_ENTRIES_trans1subtempawal);
        db.execSQL(SQL_DELETE_ENTRIES_stockopname);
        onCreate(db);
        //  dataAwaluser(db);
        // dataAwaluserHak(db);
    }


}
