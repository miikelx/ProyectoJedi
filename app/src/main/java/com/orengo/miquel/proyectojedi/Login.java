package com.orengo.miquel.proyectojedi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText nom;
    EditText pass;
    Button bLogin;
    Button bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nom = (EditText) findViewById(R.id.et_username);
        pass = (EditText) findViewById(R.id.et_password);
        bLogin = (Button) findViewById(R.id.b_login);
        bRegister = (Button) findViewById(R.id.b_register);
    }
}
