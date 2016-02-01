package com.orengo.miquel.proyectojedi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    private EditText username;
    private EditText password1;
    private EditText password2;
    private Button bRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.et_username_register);
        password1 = (EditText) findViewById(R.id.et_password_register);
        password2 = (EditText) findViewById(R.id.et_password2_register);
        bRegister = (Button) findViewById(R.id.b_register_register);
    }
}
