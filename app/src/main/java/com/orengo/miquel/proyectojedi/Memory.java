package com.orengo.miquel.proyectojedi;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Memory extends Fragment {


    private OnFragmentInteractionListener mListener;
    private int intentos;
    private int volteadas;
    private int completadas;
    private ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16;
    private Carta c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16;
    private Carta primera,segunda;
    private boolean sec;
    private TextView tIntentos;
    private View rootView;
    private Drawable back;



    public Memory() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentos = volteadas = completadas = 0;
        sec = false;
        back = ContextCompat.getDrawable(getActivity(), R.drawable.card_back);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.memory, container, false);
        setVariables(rootView);
        inicializa();
        setListeners();
        compruebaVolteadas();
        compruebaFin();
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

    private void compruebaVolteadas(){
        if(volteadas > 1){
            resetCartas();
            volteadas = 0;
            sec = false;
            ++intentos;
            tIntentos.setText(Integer.toString(intentos));
        }
    }

    private void compruebaWin(){
        if(primera.getId() == segunda.getId()){
            ++completadas;
            primera.setOk(true);
            segunda.setOk(true);
        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private void compruebaFin(){
        if(completadas == 8){
            Snackbar.make(rootView.findViewById(R.id.layoutMemory),"WIN",Snackbar.LENGTH_LONG).show();
            DB db = new DB(getActivity());
            String naux = db.getConectado();
            Usuario aux = db.getUsuario(naux);
            if(aux.getIntentos() > intentos+1 || aux.getIntentos() == -1) db.updatePuntuacion(naux,intentos+1);
        }
    }

    private void ini(Carta c, Drawable aux, ImageView id){
        c = new Carta(back,aux,id);
    }

    private boolean compruebaOk(int[] apariciones, int random){
        if(apariciones[random-1] > 1) return false;
        apariciones[random-1] += 1;
        return true;
    }

    private Drawable getD(int id){
        Drawable d;
        switch(id){
            case 1:
                d = ContextCompat.getDrawable(getActivity(), R.drawable.c1);
                break;
            case 2:
                d = ContextCompat.getDrawable(getActivity(), R.drawable.c2);
                break;
            case 3:
                d = ContextCompat.getDrawable(getActivity(), R.drawable.c3);
                break;
            case 4:
                d = ContextCompat.getDrawable(getActivity(), R.drawable.c4);
                break;
            case 5:
                d = ContextCompat.getDrawable(getActivity(), R.drawable.c5);
                break;
            case 6:
                d = ContextCompat.getDrawable(getActivity(), R.drawable.c6);
                break;
            case 7:
                d = ContextCompat.getDrawable(getActivity(), R.drawable.c7);
                break;
            default:
                d = ContextCompat.getDrawable(getActivity(), R.drawable.c8);
                break;
        }
        return d;
    }

    private void inicializa(){
        int n1,n2,n3,n4,n5,n6,n7,n8;
        int[] apariciones = {0,0,0,0,0,0,0,0};
        int random;
        boolean o1,o2,o3,o4,o5,o6,o7,o8,o9,o10,o11,o12,o13,o14,o15,o16;
        o1 = o2 = o3 = o4 = o5 = o6 = o7 = o8 = o9 = o10 = o11 = o12 = o13 = o14 = o15 = o16 = false;
        Random r = new Random();
        random = r.nextInt(9-1)+1;
        while(!o1) {
            if(compruebaOk(apariciones,random)){
                o1 = true;
                c1 = new Carta(back,getD(random), i1);
                c1.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o2) {
            if(compruebaOk(apariciones,random)){
                o2 = true;
                c2 = new Carta(back, getD(random), i2);
                c2.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o3) {
            if(compruebaOk(apariciones,random)){
                o3 = true;
                c3 = new Carta(back, getD(random), i3);
                c3.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o4) {
            if(compruebaOk(apariciones,random)){
                o4 = true;
                c4 = new Carta(back, getD(random), i4);
                c4.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o5) {
            if(compruebaOk(apariciones,random)){
                o5 = true;
                c5 = new Carta(back, getD(random), i5);
                c5.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o6) {
            if(compruebaOk(apariciones,random)){
                o6 = true;
                c6 = new Carta(back, getD(random), i6);
                c6.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o7) {
            if(compruebaOk(apariciones,random)){
                o7 = true;
                c7 = new Carta(back, getD(random), i7);
                c7.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o8) {
            if(compruebaOk(apariciones,random)){
                o8 = true;
                c8 = new Carta(back, getD(random), i8);
                c8.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o9) {
            if(compruebaOk(apariciones,random)){
                o9 = true;
                c9 = new Carta(back, getD(random), i9);
                c9.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o10) {
            if(compruebaOk(apariciones,random)){
                o10 = true;
                c10 = new Carta(back, getD(random), i10);
                c10.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o11) {
            if(compruebaOk(apariciones,random)){
                o11 = true;
                c11 = new Carta(back, getD(random), i11);
                c11.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o12) {
            if(compruebaOk(apariciones,random)){
                o12 = true;
                c12 = new Carta(back, getD(random), i12);
                c12.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o13) {
            if(compruebaOk(apariciones,random)){
                o13 = true;
                c13 = new Carta(back, getD(random), i13);
                c13.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o14) {
            if(compruebaOk(apariciones,random)){
                o14 = true;
                c14 = new Carta(back, getD(random), i14);
                c14.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o15) {
            if(compruebaOk(apariciones,random)){
                o15 = true;
                c15 = new Carta(back, getD(random), i15);
                c15.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }
        while(!o16) {
            if(compruebaOk(apariciones,random)){
                o16 = true;
                c16 = new Carta(back, getD(random), i16);
                c16.setId(random);
            }
            random = r.nextInt(9-1)+1;
        }


    }

    private void resetCartas(){
        c1.reinicia(getActivity());
        c2.reinicia(getActivity());
        c3.reinicia(getActivity());
        c4.reinicia(getActivity());
        c5.reinicia(getActivity());
        c6.reinicia(getActivity());
        c7.reinicia(getActivity());
        c8.reinicia(getActivity());
        c9.reinicia(getActivity());
        c10.reinicia(getActivity());
        c11.reinicia(getActivity());
        c12.reinicia(getActivity());
        c13.reinicia(getActivity());
        c14.reinicia(getActivity());
        c15.reinicia(getActivity());
        c16.reinicia(getActivity());
    }

    private void setVariables(View view){
        i1 = (ImageView) view.findViewById(R.id.iv_1);
        i2 = (ImageView) view.findViewById(R.id.iv_2);
        i3 = (ImageView) view.findViewById(R.id.iv_3);
        i4 = (ImageView) view.findViewById(R.id.iv_4);
        i5 = (ImageView) view.findViewById(R.id.iv_5);
        i6 = (ImageView) view.findViewById(R.id.iv_6);
        i7 = (ImageView) view.findViewById(R.id.iv_7);
        i8 = (ImageView) view.findViewById(R.id.iv_8);
        i9 = (ImageView) view.findViewById(R.id.iv_9);
        i10 = (ImageView) view.findViewById(R.id.iv_10);
        i11 = (ImageView) view.findViewById(R.id.iv_11);
        i12 = (ImageView) view.findViewById(R.id.iv_12);
        i13 = (ImageView) view.findViewById(R.id.iv_13);
        i14 = (ImageView) view.findViewById(R.id.iv_14);
        i15 = (ImageView) view.findViewById(R.id.iv_15);
        i16 = (ImageView) view.findViewById(R.id.iv_16);
        tIntentos = (TextView) view.findViewById(R.id.tv_nintentos);
    }

    private void volteaCarta(Carta c){
        if(sec){
            segunda = c;
        }
        else{
            primera = c;
            sec = true;
        }
        ++volteadas;
    }

    private void setListeners(){
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c1.isOk() && !c1.isVolteada()) {
                    c1.voltea(getActivity());
                    volteaCarta(c1);
                    if(volteadas > 1){
                        compruebaWin();
                    }
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c2.isOk() && !c2.isVolteada()) {
                    c2.voltea(getActivity());
                    volteaCarta(c2);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c3.isOk() && !c3.isVolteada()) {
                    c3.voltea(getActivity());
                    volteaCarta(c3);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c4.isOk() && !c4.isVolteada()) {
                    c4.voltea(getActivity());
                    volteaCarta(c4);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c5.isOk() && !c5.isVolteada()) {
                    c5.voltea(getActivity());
                    volteaCarta(c5);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c6.isOk() && !c6.isVolteada()) {
                    c6.voltea(getActivity());
                    volteaCarta(c6);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c7.isOk() && !c7.isVolteada()) {
                    c7.voltea(getActivity());
                    volteaCarta(c7);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c8.isOk() && !c8.isVolteada()) {
                    c8.voltea(getActivity());
                    volteaCarta(c8);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c9.isOk() && !c9.isVolteada()) {
                    c9.voltea(getActivity());
                    volteaCarta(c9);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c10.isOk() && !c10.isVolteada()) {
                    c10.voltea(getActivity());
                    volteaCarta(c10);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c11.isOk() && !c11.isVolteada()) {
                    c11.voltea(getActivity());
                    volteaCarta(c11);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c12.isOk() && !c12.isVolteada()) {
                    c12.voltea(getActivity());
                    volteaCarta(c12);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c13.isOk() && !c13.isVolteada()) {
                    c13.voltea(getActivity());
                    volteaCarta(c13);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c14.isOk() && !c14.isVolteada()) {
                    c14.voltea(getActivity());
                    volteaCarta(c14);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c15.isOk() && !c15.isVolteada()) {
                    c15.voltea(getActivity());
                    volteaCarta(c15);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
        i16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!c16.isOk() && !c16.isVolteada()) {
                    c16.voltea(getActivity());
                    volteaCarta(c16);
                    if(volteadas > 1) compruebaWin();
                    compruebaFin();
                    compruebaVolteadas();
                }
            }
        });
    }


//    private class Carta{
//        Drawable imagen;
//        Drawable back;
//        ImageView posicion;
//        boolean volteada;
//        boolean ok;
//        int id;
//
//        public Carta(Drawable im, ImageView pos){
//            this.imagen = im;
//            this.back = ContextCompat.getDrawable(getActivity(), R.drawable.card_back);
//            this.posicion = pos;
//            this.ok = false;
//            this.volteada = false;
//        }
//
//        public Drawable getImagen() {
//            return imagen;
//        }
//
//        public void setImagen(Drawable imagen) {
//            this.imagen = imagen;
//        }
//
//        public Drawable getBack() {
//            return back;
//        }
//
//        public void setBack(Drawable back) {
//            this.back = back;
//        }
//
//        public boolean isVolteada() {
//            return volteada;
//        }
//
//        public void setVolteada(boolean volteada) {
//            this.volteada = volteada;
//        }
//
//        public boolean isOk(){
//            return this.ok;
//        }
//
//        public void setId(int id){
//            this.id = id;
//
//        }
//
//        public int getId(){
//            return this.id;
//        }
//
//        public void setOk(boolean ok){
//            this.ok = ok;
//        }
//
//        public void voltea(){
//            if(!volteada){
//                final Drawable newImage = imagen;
//                final ImageView containerToFlip = posicion;
//                final Context context = getActivity();
//
//                ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(
//                        context, R.animator.flip);
//                anim.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        ObjectAnimator anim2 = (ObjectAnimator) AnimatorInflater.loadAnimator(
//                                context, R.animator.flip_end);
//                        containerToFlip.setImageDrawable(newImage);
//                        anim2.setTarget(containerToFlip);
//                        anim2.setDuration(100);
//                        anim2.start();
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                anim.setTarget(containerToFlip);
//                anim.setDuration(100);
//                anim.start();
//                volteada = true;
//            }
//        }
//
//        public void reinicia(){
//            final Drawable newImage = back;
//            final ImageView containerToFlip = posicion;
//            final Context context = getActivity();
//            if(!this.isOk() && this.volteada) {
//                ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(
//                        context, R.animator.flip);
//                anim.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        ObjectAnimator anim2 = (ObjectAnimator) AnimatorInflater.loadAnimator(
//                                context, R.animator.flip_end);
//                        containerToFlip.setImageDrawable(newImage);
//                        anim2.setTarget(containerToFlip);
//                        anim2.setDuration(100);
//                        anim2.start();
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                anim.setTarget(containerToFlip);
//                anim.setDuration(100);
//                anim.start();
//                volteada = false;
//            }
//        }
//
//
//    }
}
