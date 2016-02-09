package com.orengo.miquel.proyectojedi;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by Miquel on 2/2/16.
 */
public class Usuario {

    private String username;
    private String password;
    private Uri fotoPerfil;
    private int intentos;

    public Usuario() {
        this.intentos = -1;
        this.fotoPerfil = Uri.parse("android.resource://com.orengo.miquel.proyectojedi/drawable/noimage");
    }

    public Usuario(String username, String password, Uri fotoPerfil, int intentos, String direccion) {
        this.username = username;
        this.password = password;
        this.fotoPerfil = fotoPerfil;
        this.intentos = intentos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Uri getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(Uri fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }



}
