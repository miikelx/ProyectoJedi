package com.orengo.miquel.proyectojedi;


import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class PerfilUsuario extends Fragment {

    private ImageView fotoPerfil;
    private TextView nombre;
    private TextView mejorPuntuacion;
    private TextView direccion;
    private DB db;
    private OnFragmentInteractionListener mListener;
    private Uri path;
    private String username;
    private List<Address> l;
    private View rootView;
    private Button bGetLocation;

    public PerfilUsuario() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.perfil_usuario, container, false);
        fotoPerfil = (ImageView) rootView.findViewById(R.id.iv_fotoperfil);
        nombre = (TextView) rootView.findViewById(R.id.tv_username);
        mejorPuntuacion = (TextView) rootView.findViewById(R.id.tv_puntuacion);
        direccion = (TextView) rootView.findViewById(R.id.tv_direccion);
        bGetLocation = (Button) rootView.findViewById(R.id.b_afegir_direccio);
        db = new DB(getActivity());
        Usuario u = db.getUsuario(db.getConectado());
        username = u.getUsername();
        nombre.setText(u.getUsername());
        if(u.getIntentos() != -1) mejorPuntuacion.setText(Integer.toString(u.getIntentos()));
        else mejorPuntuacion.setText("No ha jugado ninguna partida.");
        //if(!u.getDireccion().equals("")) direccion.setText(u.getDireccion());
        direccion.setText("GPS Desactivado");
        if(!u.getFotoPerfil().equals("")) fotoPerfil.setImageURI(u.getFotoPerfil());
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getImageAsContent = new Intent(Intent.ACTION_GET_CONTENT, null);
                getImageAsContent.setType("image/*");
                startActivityForResult(getImageAsContent, 1);
            }
        });
        bGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });


        return rootView;
    }

    public void onActivityResult(int reqCode, int resCode, Intent data){
        if(resCode == Activity.RESULT_OK){
            if(reqCode == 1) fotoPerfil.setImageURI(data.getData());
            path = data.getData();
            db.updateImage(username,path.toString());
        }
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

    private void getLocation(){
        GPS gpsService = new GPS(getActivity());

        TextView t = (TextView) rootView.findViewById(R.id.tv_direccion);
        if(gpsService.CanGetLocation()) {
            Location location = gpsService.getLocation();
            Geocoder gc = new Geocoder(getActivity());
            if(location != null) {
                try {
                    l = gc.getFromLocation(location.getLatitude(),
                            location.getLongitude(), 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < l.size(); ++i) {
                    Log.v("LOG", l.get(i).getAddressLine(0).toString());
                    TextView text = (TextView) rootView.findViewById(R.id.tv_direccion);
                    if (i == 0) t.setText("");
                    text.setText(l.get(i).getAddressLine(0).toString());
                }
            }
            else{
                Snackbar.make(rootView.findViewById(R.id.layoutPerfil),"CAN'T GET LOCATION",Snackbar.LENGTH_SHORT).show();
            }
        }
    }

}
