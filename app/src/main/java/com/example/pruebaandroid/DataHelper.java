package com.example.pruebaandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {
    public DataHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE producto(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, precio INTEGER, cantidad INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS producto");
        db.execSQL("CREATE TABLE producto(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, precio INTEGER, cantidad INTEGER)");
    }
}
