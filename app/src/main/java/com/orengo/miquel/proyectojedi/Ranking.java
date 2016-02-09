package com.orengo.miquel.proyectojedi;


import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ranking extends Fragment {


    private DB db;
    private ArrayList<Usuario> usuarios;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    private Button bReiniciar;



    TextView tv;

    private OnFragmentInteractionListener mListener;

    public Ranking() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.ranking, container, false);
        db = new DB(getActivity());
        usuarios = db.getUsers();
        bReiniciar = (Button) rootView.findViewById(R.id.b_reiniciaRanking);
        for(int i = 0; i < usuarios.size(); ++i){
            if(usuarios.get(i).getIntentos() == -1) usuarios.remove(i);
        }
        Collections.sort(usuarios,new CustomComparator());
        //findViewById del layout activity_main
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);

        //LinearLayoutManager necesita el contexto de la Activity.
        //El LayoutManager se encarga de posicionar los items dentro del recyclerview
        //Y de definir la politica de reciclaje de los items no visibles.
        mLinearLayout = new LinearLayoutManager(getActivity());
        //Asignamos el LinearLayoutManager al recycler:
        mRecyclerView.setLayoutManager(mLinearLayout);


        //El adapter se encarga de  adaptar un objeto definido en el c�digo a una vista en xml
        //seg�n la estructura definida.
        //Asignamos nuestro custom Adapter
        mRecyclerView.setAdapter(new AdapterRanking(usuarios));
        bReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.reiniciaRanking(db.getConectado());
                Snackbar.make(rootView.findViewById(R.id.layoutRanking),"ESTADISTICAS REINICIADAS",Snackbar.LENGTH_INDEFINITE).show();
            }
        });
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

    public class CustomComparator implements Comparator<Usuario> {

        @Override
        public int compare(Usuario u1, Usuario u2){
            if(u1.getIntentos() > u2.getIntentos()) return 1;
            else return 0;
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
