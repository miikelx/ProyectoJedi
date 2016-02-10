package com.orengo.miquel.proyectojedi;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class Login extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "6v319O2JILEMPJx9nBPs7BZoi";
    private static final String TWITTER_SECRET = "Tg8SHericUpec8EV6YFAnfCaJ6Wg0UhDL0RLKAImMPoQgLGMot";


    EditText nom;
    EditText pass;
    Button bLogin;
    Button bRegister;
    private DB db;
    private TwitterLoginButton loginButton;
    private String tw_username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.login);
        setTitle("Login");
        db = new DB(getApplicationContext());
        nom = (EditText) findViewById(R.id.et_username);
        pass = (EditText) findViewById(R.id.et_password);
        bLogin = (Button) findViewById(R.id.b_login);
        bRegister = (Button) findViewById(R.id.b_register);
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                tw_username = session.getUserName();
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        if(db.getConectado().equals("")) {
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
        else{
            Intent intent = new Intent(Login.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        if(tw_username != null) {
            loginButton.onActivityResult(requestCode, resultCode, data);
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            if (!db.existe(tw_username)) {
                db.register(tw_username, "twitter");
            }
            db.logIn(tw_username);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(),"Twitter login failed",Toast.LENGTH_SHORT).show();
        }

    }


    private void execRegister() {
        Intent intent = new Intent(Login.this,Register.class);
        startActivity(intent);

    }

    private void execLogin() {
        String username,password;
        username = nom.getText().toString();
        password = pass.getText().toString();
        if(db.loginOk(username, password)){
            Intent intent = new Intent(Login.this,MainActivity.class);
            startActivity(intent);
            db.logIn(username);
            finish();
        }
        else{
            Snackbar.make(findViewById(R.id.layoutLogin),"Username o password incorrecto",Snackbar.LENGTH_SHORT).show();
        }


    }


}
