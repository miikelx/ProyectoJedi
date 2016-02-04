package com.orengo.miquel.proyectojedi;


import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PerfilUsuario extends Fragment {

    private ImageView fotoPerfil;
    private TextView nombre;
    private TextView mejorPuntuacion;
    private TextView direccion;
    private Button addDireccion;
    private DB db;
    private OnFragmentInteractionListener mListener;
    private Uri path;
    private String username;

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
        View rootView = inflater.inflate(R.layout.perfil_usuario, container, false);
        fotoPerfil = (ImageView) rootView.findViewById(R.id.iv_fotoperfil);
        nombre = (TextView) rootView.findViewById(R.id.tv_username);
        mejorPuntuacion = (TextView) rootView.findViewById(R.id.tv_puntuacion);
        direccion = (TextView) rootView.findViewById(R.id.tv_direccion);
        addDireccion = (Button) rootView.findViewById(R.id.b_afegir_direccio);
        db = new DB(getActivity());
        Usuario u = db.getUsuario(db.getConectado());
        username = u.getUsername();
        nombre.setText(u.getUsername());
        if(u.getIntentos() != -1) mejorPuntuacion.setText(Integer.toString(u.getIntentos()));
        else mejorPuntuacion.setText("No ha jugado ninguna partida.");
        if(!u.getDireccion().equals("")) direccion.setText(u.getDireccion());
        else direccion.setText("No se ha añadido direccon");
        if(!u.getFotoPerfil().equals("")) fotoPerfil.setImageURI(u.getFotoPerfil());
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getImageAsContent = new Intent(Intent.ACTION_GET_CONTENT, null);
                getImageAsContent.setType("image/*");
                startActivityForResult(getImageAsContent, 1);
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
}
