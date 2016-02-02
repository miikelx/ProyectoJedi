package com.orengo.miquel.proyectojedi;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class PlayerService extends Service {

    private MediaPlayer mediaPlayer;
    private File sdCard;
    private File song;
    private final IBinder binder = new LocalBinder();



    public PlayerService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        PlayerService getService() {
            return PlayerService.this;
        }
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        sdCard = Environment.getExternalStorageDirectory();
        song = new File(sdCard.getAbsolutePath() + "/Music/tailtoddle_lo.mp3");
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        try {
//            mediaPlayer.setDataSource(song.getAbsolutePath());
//            mediaPlayer.prepare();
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    mediaPlayer.release();
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.start();
//        return super.onStartCommand(intent, flags, startId);
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();    }

    public void play(){
        try {
            mediaPlayer.setDataSource(song.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    public void pause(){

    }
}
