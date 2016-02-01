package com.orengo.miquel.proyectojedi;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText nom;
    EditText pass;
    Button bLogin;
    Button bRegister;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DB(getApplicationContext());
        nom = (EditText) findViewById(R.id.et_username);
        pass = (EditText) findViewById(R.id.et_password);
        bLogin = (Button) findViewById(R.id.b_login);
        bRegister = (Button) findViewById(R.id.b_register);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execLogin();
            }
        });
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                execRegister();
            }
        });
    }

    private void execRegister() {
        Intent intent = new Intent(Login.this,Register.class);

    }

    private void execLogin() {
        String username,password;
        username = nom.getText().toString();
        password = pass.getText().toString();
        if(db.loginOk(username, password)){
            Intent intent = new Intent(Login.this,MainActivity.class);
            startActivity(intent);
        }
        else{
            Snackbar.make(findViewById(R.id.layoutLogin),"Username o password incorrecto",Snackbar.LENGTH_SHORT).show();
        }


    }


}
