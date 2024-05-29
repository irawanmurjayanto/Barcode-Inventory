package com.example.barcodegcnew;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class ADB_User extends SQLiteOpenHelper{

    //InnerClass, untuk mengatur artibut seperti Nama Tabel, nama-nama kolom dan Query
    static abstract class MyColumns implements BaseColumns{
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "user";
        static final String idno = "idno";
        static final String user = "user";
        static final String password = "password";
        static final String hak = "hak";
        static final String tgl_rec = "tgl_rec";

    }

    static abstract class MyColumnsHak implements BaseColumns{
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "hak";
        static final String idno = "idno";
        static final String hak = "hak";
        static final String tgl_rec = "tgl_rec";

    }




    private static final String NamaDatabase = "user.db";
    private static final int VersiDatabase = 6;

    //Query yang digunakan untuk membuat Tabel
      private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ MyColumns.NamaTabel+
            "("+ MyColumns.idno+" INTEGER PRIMARY KEY, "+ MyColumns.user+" TEXT NOT NULL, "+ MyColumns.password+
            " TEXT NOT NULL, "+ MyColumns.hak+" TEXT NOT NULL,"+ MyColumns.tgl_rec+" DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M', 'NOW','localtime')))";


    private static final String SQL_CREATE_ENTRIES_Hak = "CREATE TABLE "+ MyColumnsHak.NamaTabel+
            "("+ MyColumnsHak.idno+" INTEGER PRIMARY KEY, "+ MyColumnsHak.hak+" TEXT NOT NULL,"+ MyColumnsHak.tgl_rec+" DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M', 'NOW','localtime')))";




    //Query yang digunakan untuk mengupgrade Tabel
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+ MyColumns.NamaTabel;
    private static final String SQL_DELETE_ENTRIESHak = "DROP TABLE IF EXISTS "+ MyColumnsHak.NamaTabel;



    private void dataAwaluser(SQLiteDatabase db){

        String Insertuser = "INSERT INTO " + MyColumns.NamaTabel +"("+ MyColumns.user+","+ MyColumns.password+","+ MyColumns.password+") VALUES ('a','a','admin')";
        db.execSQL(Insertuser);

        String Insertuser2 = "INSERT INTO " + MyColumns.NamaTabel +"("+ MyColumns.user+","+ MyColumns.password+","+ MyColumns.password+") VALUES ('b','b','admin')";
        db.execSQL(Insertuser2);


    }






    public List<String> getSemuaHak2(){
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






    public List<String> getSemuaUser2(){
        List<String> userList = new ArrayList<>();
        String selectQuery = "SELECT user FROM " + ADB_User.MyColumns.NamaTabel;

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


    ADB_User(Context context) {
        super(context, NamaDatabase, null, VersiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES_Hak);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIESHak);
        onCreate(db);
      //  dataAwaluser(db);
       // dataAwaluserHak(db);
    }


}
