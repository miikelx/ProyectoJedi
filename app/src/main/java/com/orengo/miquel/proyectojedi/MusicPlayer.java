package com.orengo.miquel.proyectojedi;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MusicPlayer extends AppCompatActivity {

    ImageView bPlay,bPause;
    TextView tV;
    PlayerService mService;
    boolean bound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        //mConnection.onServiceConnected();
        bPlay = (ImageView) findViewById(R.id.iv_play);
        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.play();
            }
        });
        bPause = (ImageView) findViewById(R.id.iv_stop);
        bPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });
        tV = (TextView) findViewById(R.id.tv_tittle);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            mService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    private void pause(){
        mService.pause();
    }

}
