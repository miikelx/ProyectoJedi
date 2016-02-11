package com.orengo.miquel.proyectojedi;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Miquel on 9/2/16.
 */
public class Carta {
    Drawable imagen;
    Drawable back;
    ImageView posicion;
    boolean volteada;
    boolean ok;
    int id;
    boolean pause = true;
    //Context context;

    public Carta(Drawable back, Drawable im, ImageView pos) {
        //this.context = c;
        this.imagen = im;
        this.back = back;
        this.posicion = pos;
        this.ok = false;
        this.volteada = false;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    public Drawable getBack() {
        return back;
    }

    public void setBack(Drawable back) {
        this.back = back;
    }

    public boolean isVolteada() {
        return volteada;
    }

    public void setVolteada(boolean volteada) {
        this.volteada = volteada;
    }

    public boolean isOk() {
        return this.ok;
    }

    public void setId(int id) {
        this.id = id;

    }

    public int getId() {
        return this.id;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void voltea(Context c) {
        if (!volteada) {

            final Drawable newImage = imagen;
            final ImageView containerToFlip = posicion;
            final Context context = c;

            ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(
                    context, R.animator.flip);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ObjectAnimator anim2 = (ObjectAnimator) AnimatorInflater.loadAnimator(
                            context, R.animator.flip_end);
                    containerToFlip.setImageDrawable(newImage);
                    anim2.setTarget(containerToFlip);
                    anim2.setDuration(100);
                    anim2.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.setTarget(containerToFlip);
            anim.setDuration(100);
            anim.start();
            volteada = true;
        }
    }

    public void reinicia(Context c) {
        final Drawable newImage = back;
        final ImageView containerToFlip = posicion;
        final Context context = c;
        if (!this.isOk() && this.volteada) {
            ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(
                    context, R.animator.flip);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ObjectAnimator anim2 = (ObjectAnimator) AnimatorInflater.loadAnimator(
                            context, R.animator.flip_end);
                    containerToFlip.setImageDrawable(newImage);
                    anim2.setTarget(containerToFlip);
                    anim2.setDuration(100);
                    anim2.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.setTarget(containerToFlip);
            anim.setDuration(100);
            anim.start();
            volteada = false;
        }
    }
}


