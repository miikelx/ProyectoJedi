package com.orengo.miquel.proyectojedi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Miquel on 4/2/16.
 */
public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.AdapterViewHolder>{

    ArrayList<Usuario> usuarios;

    AdapterRanking(ArrayList<Usuario> input){
        this.usuarios = input;
    }


    @Override
    public AdapterRanking.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.adapter_ranking, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRanking.AdapterViewHolder adapterViewholder, int position) {
        adapterViewholder.name.setText(usuarios.get(position).getUsername());
        String aux = Integer.toString(usuarios.get(position).getIntentos());
        adapterViewholder.punt.setText(aux);

    }

    @Override
    public int getItemCount() {
        //Debemos retornar el tamaño de todos los elementos contenidos en el viewholder
        //Por defecto es return 0 --> No se mostrará nada.
        return usuarios.size();
    }



    //Definimos una clase viewholder que funciona como adapter para
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        /*
        *  Mantener una referencia a los elementos de nuestro ListView mientras el usuario realiza
        *  scrolling en nuestra aplicación. Así que cada vez que obtenemos la vista de un item,
        *  evitamos las frecuentes llamadas a findViewById, la cuál se realizaría únicamente la primera vez y el resto
        *  llamaríamos a la referencia en el ViewHolder, ahorrándonos procesamiento.
        */

        public TextView name;
        public TextView punt;
        public View v;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.name = (TextView) itemView.findViewById(R.id.tv_username_adapter);
            this.punt = (TextView) itemView.findViewById(R.id.tv_puntuacion_adapter);
        }
    }
}
