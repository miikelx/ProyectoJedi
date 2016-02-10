package com.orengo.miquel.proyectojedi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Calculadora extends Fragment implements View.OnClickListener{

    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
    private Button bSum,bRes,bMult,bDiv,bPunt,bEq,bBorrar;
    private TextView text;
    private double resultat,mem;
    private boolean primer,suma,resta,multi,div,nou;
    private String bHColor = "#ffae00";
    private String bNColor = "#dfdfdf";
    private String bOPColor = "#7c7c7c";
    private String bBorrarColor = "#9F1B3C";
    private boolean error;
    private boolean decimal;
    private boolean dmodif;
    private int idNotificacion; //1-Toast, 2-Snackbar, 3-Barra Notificaciones
    private View rootView;
    private OnFragmentInteractionListener mListener;
    private MenuInflater menuInflater;

    public Calculadora(){

    }

    public Calculadora(MenuInflater inf){
        menuInflater = inf;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultat = 0;
        primer = true;
        suma = resta = multi = div = false;
        nou = true;
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.calculadora, container, false);
        setVariables();
        setListeners();
        setColors();

        //text.setText(Double.toString(resultat));
        if(savedInstanceState != null) restore(savedInstanceState);
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



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bSuma:
                resetColors();
                bSum.getBackground().setColorFilter(Color.parseColor(bHColor), PorterDuff.Mode.MULTIPLY);
                if(primer){
                    double naux = Double.parseDouble(text.getText().toString());
                    mem = naux;
                    suma = true;
                    text.setText(Double.toString(mem));
                    primer = false;
                    nou = true;
                    decimal = false;
                    dmodif = false;
                }
                else{
                    calcula();
                    suma = true;
                    nou = true;
                    decimal = false;
                    dmodif = false;
                }
                break;
            case R.id.bIgual:
                calcula();
                primer = true;
                resetColors();
                break;
            case R.id.bResta:
                resetColors();
                bRes.getBackground().setColorFilter(Color.parseColor(bHColor), PorterDuff.Mode.MULTIPLY);
                if(primer){
                    double naux = Double.parseDouble(text.getText().toString());
                    mem = naux;
                    resta = true;
                    text.setText(Double.toString(mem));
                    primer = false;
                    nou = true;
                    decimal = false;
                    dmodif = false;
                }
                else{
                    calcula();
                    resta = true;
                    nou = true;
                    decimal = false;
                    dmodif = false;
                }
                break;
            case R.id.bMult:
                resetColors();
                bMult.getBackground().setColorFilter(Color.parseColor(bHColor), PorterDuff.Mode.MULTIPLY);
                if(primer){
                    double naux = Double.parseDouble(text.getText().toString());
                    mem = naux;
                    multi = true;
                    text.setText(Double.toString(mem));
                    primer = false;
                    nou = true;
                    decimal = false;
                    dmodif = false;
                }
                else{
                    calcula();
                    multi = true;
                    nou = true;
                    decimal = false;
                    dmodif = false;
                }
                break;
            case R.id.bDiv:
                resetColors();
                bDiv.getBackground().setColorFilter(Color.parseColor(bHColor), PorterDuff.Mode.MULTIPLY);
                if(primer){
                    double naux = Double.parseDouble(text.getText().toString());
                    mem = naux;
                    div = true;
                    text.setText(Double.toString(mem));
                    primer = false;
                    nou = true;
                    decimal = false;
                    dmodif = false;
                }
                else{
                    calcula();
                    div = true;
                    nou = true;
                    decimal = false;
                    dmodif = false;
                }
                break;
            case R.id.bBorrar:
                mem = resultat = 0;
                text.setText(Double.toString(resultat));
                decimal = false;
                dmodif = false;
                resetColors();
                nou = true;
                break;
            case R.id.bPunto:
                if(decimal){
                    if(error){
                        Toast.makeText(getActivity(), "Ya hay un decimal", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    decimal = true;
                    bPunt.getBackground().setColorFilter(Color.parseColor(bHColor), PorterDuff.Mode.MULTIPLY);
                }
                break;
            default:
                String aux = text.getText().toString();
                double naux;
                Button baux = (Button) rootView.findViewById(v.getId());
                if(suma || resta || multi || div) { //Hay operacion seleccionada
                    if (aux.length() < 15) {
                        if (nou) {
                            naux = Double.parseDouble(baux.getText().toString());
                            text.setText(Double.toString(naux));
                            resultat = naux;
                            nou = false;
                            decimal = false;
                        } else {
                            if(decimal){
                                String[] S = aux.split(Pattern.quote("."));
                                if(S[1].length() == 1 && !dmodif){
                                    S[1] = baux.getText().toString();
                                    dmodif = true;
                                }
                                else S[1] = S[1] + baux.getText().toString();
                                String total = S[0] + "." + S[1];
                                text.setText(total);
                                resultat = Double.parseDouble(total);
                            }
                            else {
                                String[] S = aux.split(Pattern.quote("."));
                                String s1 = S[0];
                                String s2 = S[1];
                                String total = s1 + baux.getText().toString() + "." + s2;
                                text.setText(total);
                                resultat = Double.parseDouble(total);
                            }
                        }
                    }
                }
                else{ //No hay operacion seleccionada
                    if (aux.length() < 15) {
                        if (nou) {
                            naux = Double.parseDouble(baux.getText().toString());
                            text.setText(Double.toString(naux));
                            resultat = naux;
                            nou = false;
                            decimal = false;
                        } else {
                            if(decimal){
                                String[] S = aux.split(Pattern.quote("."));
                                if(S[1].length() == 1 && !dmodif){
                                    S[1] = baux.getText().toString();
                                    dmodif = true;
                                }
                                else S[1] = S[1] + baux.getText().toString();
                                String total = S[0] + "." + S[1];
                                text.setText(total);
                                resultat = Double.parseDouble(total);
                            }
                            else {
                                String[] S = aux.split(Pattern.quote("."));
                                String s1 = S[0];
                                String s2 = S[1];
                                String total = s1 + baux.getText().toString() + "." + s2;
                                text.setText(total);
                                resultat = Double.parseDouble(total);
                            }
                        }
                    }
                }
                break;


        }
    }

    private void setColors(){
        b0.getBackground().setColorFilter(Color.parseColor(bNColor), PorterDuff.Mode.MULTIPLY);
        b1.getBackground().setColorFilter(Color.parseColor(bNColor), PorterDuff.Mode.MULTIPLY);
        b2.getBackground().setColorFilter(Color.parseColor(bNColor), PorterDuff.Mode.MULTIPLY);
        b3.getBackground().setColorFilter(Color.parseColor(bNColor), PorterDuff.Mode.MULTIPLY);
        b4.getBackground().setColorFilter(Color.parseColor(bNColor), PorterDuff.Mode.MULTIPLY);
        b5.getBackground().setColorFilter(Color.parseColor(bNColor), PorterDuff.Mode.MULTIPLY);
        b6.getBackground().setColorFilter(Color.parseColor(bNColor), PorterDuff.Mode.MULTIPLY);
        b7.getBackground().setColorFilter(Color.parseColor(bNColor), PorterDuff.Mode.MULTIPLY);
        b8.getBackground().setColorFilter(Color.parseColor(bNColor), PorterDuff.Mode.MULTIPLY);
        b9.getBackground().setColorFilter(Color.parseColor(bNColor), PorterDuff.Mode.MULTIPLY);
        bSum.getBackground().setColorFilter(Color.parseColor(bOPColor), PorterDuff.Mode.MULTIPLY);
        bRes.getBackground().setColorFilter(Color.parseColor(bOPColor), PorterDuff.Mode.MULTIPLY);
        bMult.getBackground().setColorFilter(Color.parseColor(bOPColor), PorterDuff.Mode.MULTIPLY);
        bDiv.getBackground().setColorFilter(Color.parseColor(bOPColor), PorterDuff.Mode.MULTIPLY);
        bPunt.getBackground().setColorFilter(Color.parseColor(bNColor), PorterDuff.Mode.MULTIPLY);
        bEq.getBackground().setColorFilter(Color.parseColor(bOPColor), PorterDuff.Mode.MULTIPLY);
        bBorrar.getBackground().setColorFilter(Color.parseColor(bBorrarColor), PorterDuff.Mode.MULTIPLY);
        bSum.setTextColor(Color.parseColor("#E2E2E2"));
        bRes.setTextColor(Color.parseColor("#E2E2E2"));
        bMult.setTextColor(Color.parseColor("#E2E2E2"));
        bDiv.setTextColor(Color.parseColor("#E2E2E2"));
        bEq.setTextColor(Color.parseColor("#E2E2E2"));
        bBorrar.setTextColor(Color.parseColor("#E2E2E2"));
    }

    private void setListeners() {
        b0.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        bSum.setOnClickListener(this);
        bRes.setOnClickListener(this);
        bMult.setOnClickListener(this);
        bDiv.setOnClickListener(this);
        bPunt.setOnClickListener(this);
        bEq.setOnClickListener(this);
        bBorrar.setOnClickListener(this);
    }

    private void setVariables(){
        b0 = (Button) rootView.findViewById(R.id.b0);
        b1 = (Button) rootView.findViewById(R.id.b1);
        b2 = (Button) rootView.findViewById(R.id.b2);
        b3 = (Button) rootView.findViewById(R.id.b3);
        b4 = (Button) rootView.findViewById(R.id.b4);
        b5 = (Button) rootView.findViewById(R.id.b5);
        b6 = (Button) rootView.findViewById(R.id.b6);
        b7 = (Button) rootView.findViewById(R.id.b7);
        b8 = (Button) rootView.findViewById(R.id.b8);
        b9 = (Button) rootView.findViewById(R.id.b9);
        bSum = (Button) rootView.findViewById(R.id.bSuma);
        bRes = (Button) rootView.findViewById(R.id.bResta);
        bMult = (Button) rootView.findViewById(R.id.bMult);
        bDiv = (Button) rootView.findViewById(R.id.bDiv);
        bPunt = (Button) rootView.findViewById(R.id.bPunto);
        bEq = (Button) rootView.findViewById(R.id.bIgual);
        bBorrar = (Button) rootView.findViewById(R.id.bBorrar);
        text = (TextView) rootView.findViewById(R.id.textView);
        error = true;
        decimal = false;
        dmodif = false;
    }

    private void resetColors(){
        bSum.getBackground().setColorFilter(Color.parseColor(bOPColor), PorterDuff.Mode.MULTIPLY);
        bRes.getBackground().setColorFilter(Color.parseColor(bOPColor), PorterDuff.Mode.MULTIPLY);
        bMult.getBackground().setColorFilter(Color.parseColor(bOPColor), PorterDuff.Mode.MULTIPLY);
        bDiv.getBackground().setColorFilter(Color.parseColor(bOPColor), PorterDuff.Mode.MULTIPLY);
        bPunt.getBackground().setColorFilter(Color.parseColor(bNColor), PorterDuff.Mode.MULTIPLY);
    }

    private void calcula(){
        if(suma){
            double naux = Double.parseDouble(text.getText().toString());
            mem = naux + mem;
            text.setText(Double.toString(mem));
            suma = false;
        }
        else if(resta){
            double naux = Double.parseDouble(text.getText().toString());
            mem = mem - naux;
            text.setText(Double.toString(mem));
            resta = false;
        }
        else if(multi){
            double naux = Double.parseDouble(text.getText().toString());
            mem = naux * mem;
            text.setText(Double.toString(mem));
            multi = false;
        }
        else if(div){
            double naux = Double.parseDouble(text.getText().toString());
            if(naux != 0) {
                mem = mem / naux;
                text.setText(Double.toString(mem));
                div = false;
            }
            else{
                if(error) gestionErrores();
            }
        }
    }

    private void gestionErrores(){
        switch(idNotificacion){
            case 1:
                aToast();
                break;
            case 2:
                aSnackbar();
                break;
            case 3:
                aNotificacion();
                break;
        }
    }

    private void aToast(){
        Toast.makeText(getActivity(), "No es posible dividir por 0", Toast.LENGTH_SHORT).show();
    }

    private void aSnackbar(){
        Snackbar.make(rootView.findViewById(R.id.linearLayout), "No es posible dividir por 0", Snackbar.LENGTH_LONG)
                .show(); // Importante!!! No olvidar mostrar la Snackbar.
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu);
        inflater.inflate(R.menu.menu_calculadora, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_error:
                return true;
            case R.id.action_noerror:
                noError();
                return true;
            case R.id.action_call:
                call();
                return true;
            case R.id.action_toast:
                idNotificacion = 1;
                Toast.makeText(getActivity(),"Toast selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_snackbar:
                idNotificacion = 2;
                Toast.makeText(getActivity(),"Snackbar selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_notificacion:
                idNotificacion = 3;
                Toast.makeText(getActivity(),"Notification bar selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_internet:
                Uri uriUrl = Uri.parse("http://www.google.es");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void aNotificacion(){
        int mId = 1;
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("ERROR");
        bigTextStyle.setSummaryText("Division entre 0");
        bigTextStyle.bigText("Error, no es posible dividir entre 0, prueba realizando la division con otro valor");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
        mBuilder.setStyle(bigTextStyle);
        mBuilder.setSmallIcon(R.drawable.block32);
        Intent resultIntent = new Intent(getActivity(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager.notify(mId, mBuilder.build());
    }


    private void noError(){
        error = false;
        Toast.makeText(getActivity(), "Errores desactivados", Toast.LENGTH_SHORT).show();
    }

    private void call(){
        String aux = text.getText().toString();
        String S[] = aux.split(Pattern.quote("."));
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + S[0]));
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", text.getText().toString());
        outState.putBoolean("error", error);
        outState.putBoolean("decimal",decimal);
        outState.putBoolean("dmodif",dmodif);
        outState.putBoolean("primer",primer);
        outState.putBoolean("suma",suma);
        outState.putBoolean("resta",resta);
        outState.putBoolean("multi",multi);
        outState.putBoolean("div",div);
        outState.putBoolean("nou",nou);
        outState.putDouble("resultat", resultat);
        outState.putDouble("mem", mem);
        outState.putInt("idNotificacion",idNotificacion);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //probably orientation change
            restore(savedInstanceState);
        }
    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            restore(savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            restore(savedInstanceState);
        }
    }

    private void restore(Bundle savedInstanceState) {
        Log.e("RES", "restore: " + savedInstanceState.getString("text"));
        text.setText(savedInstanceState.getString("text"));
        error = savedInstanceState.getBoolean("error");
        decimal = savedInstanceState.getBoolean("decimal");
        dmodif = savedInstanceState.getBoolean("dmodif");
        primer = savedInstanceState.getBoolean("primer");
        suma = savedInstanceState.getBoolean("suma");
        resta = savedInstanceState.getBoolean("resta");
        multi = savedInstanceState.getBoolean("multi");
        div = savedInstanceState.getBoolean("div");
        nou = savedInstanceState.getBoolean("nou");
        resultat = savedInstanceState.getDouble("resultat");
        mem = savedInstanceState.getDouble("mem");
        idNotificacion = savedInstanceState.getInt("idNotificacion");
    }
}
