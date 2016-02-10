package com.orengo.miquel.proyectojedi;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicPlayer extends Fragment {

    ImageView bPlay,bPause;
    TextView tV;
    PlayerService mService;
    boolean bound = false;
    private OnFragmentInteractionListener mListener;
    ImageView imagenCancion;
    boolean playing = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.music_player, container, false);
        mService = new PlayerService();
        bPlay = (ImageView) rootView.findViewById(R.id.iv_play);
        imagenCancion = (ImageView) rootView.findViewById(R.id.iv_album);
        imagenCancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!playing){
                    //set image
                    Drawable d = getResources().getDrawable(R.drawable.album_playing);
                    imagenCancion.setImageDrawable(d);
                    mService.play();
                    playing = true;
                }
                else{
                    Drawable d = getResources().getDrawable(R.drawable.default_mp3);
                    imagenCancion.setImageDrawable(d);
                    mService.pause();
                    playing = false;
                }
            }
        });
        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.play();
                playing = true;
                Drawable d = getResources().getDrawable(R.drawable.album_playing);
                imagenCancion.setImageDrawable(d);
            }
        });
        bPause = (ImageView) rootView.findViewById(R.id.iv_stop);
        bPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
                playing = false;
                Drawable d = getResources().getDrawable(R.drawable.default_mp3);
                imagenCancion.setImageDrawable(d);
            }
        });
        tV = (TextView) rootView.findViewById(R.id.tv_tittle);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
