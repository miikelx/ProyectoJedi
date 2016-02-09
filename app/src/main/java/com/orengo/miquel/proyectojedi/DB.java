package com.orengo.miquel.proyectojedi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

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
    public static final String USERS_TABLE_CREATE = "CREATE TABLE " + USERS_TABLE + " (username TEXT PRIMARY KEY UNIQUE, password TEXT, imagen TEXT," +
            " puntuacion INT);";


    public static final String USER_LOGGED = "Logged";

    public static final String USER_LOGGED_CREATE = "CREATE TABLE "+USER_LOGGED+" (username TEXT PRIMARY KEY);";

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
        String SQL = "DELETE FROM "+USER_LOGGED+";";
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
        Usuario aux = new Usuario();
        String uri,dir;
        int punt = aux.getIntentos();
        uri = aux.getFotoPerfil().toString();
        String SQL = "INSERT INTO "+USERS_TABLE+" (username,password,imagen,puntuacion) VALUES ('"+u+"','"+p1+"','"+uri+"',"+punt+")";
        db.execSQL(SQL);
    }

    public String getConectado(){
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "SELECT * FROM "+USER_LOGGED+";";
        String res = "";
        Cursor c = db.rawQuery(SQL, null);
        if(c.moveToFirst()){
            res = c.getString(0);
        }
        return res;
    }

    public Usuario getUsuario(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "SELECT * FROM "+USERS_TABLE+" WHERE username='"+username+"'";
        Cursor c = db.rawQuery(SQL,null);
        Usuario nuevo = new Usuario();
        if(c.moveToFirst()){
            nuevo.setUsername(c.getString(0));
            nuevo.setPassword(c.getString(1));
            nuevo.setFotoPerfil(Uri.parse(c.getString(2)));
            nuevo.setIntentos(c.getInt(3));
        }
        return nuevo;
    }

    ArrayList<Usuario> getUsers(){
        ArrayList<Usuario> ret = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "SELECT * FROM "+USERS_TABLE+";";
        Cursor c = db.rawQuery(SQL,null);
        if (c.moveToFirst()) {
            do {
                Usuario aux = new Usuario();
                aux.setUsername(c.getString(0));
                aux.setPassword(c.getString(1));
                aux.setFotoPerfil(Uri.parse(c.getString(2)));
                aux.setIntentos(c.getInt(3));
                ret.add(aux);
            } while (c.moveToNext());
        }
        return ret;
    }

    public void updateImage(String username, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "UPDATE "+USERS_TABLE+" SET imagen='"+image+"' WHERE username='"+username+"'";
        db.execSQL(SQL);
    }


    public void updatePuntuacion(String username, int puntuacion){
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "UPDATE "+USERS_TABLE+" SET puntuacion="+puntuacion+" WHERE username='"+username+"'";
        db.execSQL(SQL);
    }

    public void reiniciaRanking(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        int aux = -1;
        String SQL = "UPDATE "+USERS_TABLE+" SET puntuacion="+aux+" WHERE username='"+username+"'";
        db.execSQL(SQL);
    }
}
