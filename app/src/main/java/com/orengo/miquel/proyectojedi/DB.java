package com.orengo.miquel.proyectojedi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Miquel on 1/2/16.
 */
public class DB extends SQLiteOpenHelper {

    //Declaracion del nombre de la base de datos
    public static final int DATABASE_VERSION = 1;

    //Declaracion global de la version de la base de datos
    public static final String DATABASE_NAME = "database";

    //Declaracion del nombre de la tabla
    public static final String USERS_TABLE ="Users";

    //sentencia global de cracion de la base de datos
    public static final String USERS_TABLE_CREATE = "CREATE TABLE " + USERS_TABLE + " (username TEXT PRIMARY KEY UNIQUE, password TEXT);";


    public static final String USER_LOGGED = "Logged";

    public static final String USER_LOGGED_CREATE = "CREATE TABLE "+USER_LOGGED+" (username TEXT PRIMARY KEY UNIQUE);";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS_TABLE_CREATE);
        db.execSQL(USER_LOGGED_CREATE);
    }

    public boolean loginOk(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "SELECT * FROM " + USERS_TABLE + " WHERE username='" + username + "' AND password='" + password+"'";
        Cursor c = db.rawQuery(SQL, null);
        int numero = 0;
        if (c.moveToFirst()) {
            ++numero;
        }
        return (numero > 0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void logIn(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "INSERT INTO "+USER_LOGGED+" (username) VALUES ('"+username+"')";
        db.execSQL(SQL);
    }

    public void logOut(){
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "DELETE * FROM "+USER_LOGGED+";";
        db.execSQL(SQL);
    }

    public boolean existe(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "SELECT * FROM " + USERS_TABLE + " WHERE username='" + username+"'";
        Cursor c = db.rawQuery(SQL, null);
        int numero = 0;
        if(c.moveToFirst()){
            ++numero;
        }
        return(numero > 0);
    }

    public void register(String u, String p1) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "INSERT INTO "+USERS_TABLE+" (username,password) VALUES ('"+u+"','"+p1+"')";
        db.execSQL(SQL);
    }
}
