package com.example.pau.talaya;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

import static com.example.pau.talaya.FiltreCerca.CasaFiltre;
import static com.example.pau.talaya.home.CasaList;

/**
 * Created by Pau on 31/5/17.
 */

public class Comentaris extends AppCompatActivity {

    View view;

    final ArrayList<String> puntuacio = new ArrayList<>();
    final ArrayList<String> comentari = new ArrayList<>();
    final ArrayList<String> NomUser = new ArrayList<>();

    boolean nodata = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comentaris);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#4C9141"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Comentaris");

        view = getWindow().getDecorView().getRootView();

        Bundle b = getIntent().getExtras();

        int idCasa = b.getInt("idCasa");

        consultaValoracions(view, idCasa);

    }

    private void consultaValoracions(final View view, int id){

        NomUser.clear();
        comentari.clear();
        puntuacio.clear();

        AsyncHttpClient clientUsuari;

        final String url = "http://talaiaapi.azurewebsites.net/api/valoracio/"+id;

        clientUsuari = new AsyncHttpClient();
        clientUsuari.setMaxRetriesAndTimeout(0,5000);

        clientUsuari.get(Comentaris.this, url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONArray jsonArray = null;
                JSONObject valoracio = null;
                String str = new String(responseBody);

                try {

                    if (str.equals("null")){

                        LinearLayout Lnodata = (LinearLayout)findViewById(R.id.noData);

                        Lnodata.setVisibility(View.VISIBLE);

                    }else{

                        jsonArray = new JSONArray(str);

                        for (int i = 0; i < jsonArray.length();i++){

                            valoracio = jsonArray.getJSONObject(i);

                            puntuacio.add(String.valueOf(valoracio.getInt("Puntuacio")));
                            comentari.add(valoracio.getString("Comentari"));
                            NomUser.add(valoracio.getString("nomUsuari"));

                        }

                        ListView llista = (ListView)findViewById(R.id.listComen);

                        AdapterComentaris adapter = new AdapterComentaris(view.getContext(),R.layout.row_comentaris,puntuacio,comentari,NomUser);

                        llista.setAdapter(adapter);

                    }

                }catch (JSONException e) {

                    e.printStackTrace();


                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Snackbar.make(view, "Error de conexió", Snackbar.LENGTH_LONG)
                        .show();



            }
        });

    }
}
