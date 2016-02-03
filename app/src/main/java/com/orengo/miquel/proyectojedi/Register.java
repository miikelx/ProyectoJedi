package com.orengo.miquel.proyectojedi;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    private EditText username;
    private EditText password1;
    private EditText password2;
    private Button bRegister;
    private DB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setTitle("Register");
        db = new DB(getApplicationContext());
        username = (EditText) findViewById(R.id.et_username_register);
        password1 = (EditText) findViewById(R.id.et_password_register);
        password2 = (EditText) findViewById(R.id.et_password2_register);
        bRegister = (Button) findViewById(R.id.b_register_register);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accionRegister();
            }
        });
    }

    private void accionRegister(){
        String u = username.getText().toString();
        String p1 = password1.getText().toString();
        String p2 = password2.getText().toString();
        if(!db.existe(u)){
            if(p1.equals(p2)){
                db.register(u,p1);
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
            else{
                Snackbar.make(findViewById(R.id.layoutRegister),"Los passwords no coinciden",Snackbar.LENGTH_SHORT).show();
            }
        }
        else{
            Snackbar.make(findViewById(R.id.layoutRegister), "Username en uso", Snackbar.LENGTH_SHORT).show();
        }

    }
}
