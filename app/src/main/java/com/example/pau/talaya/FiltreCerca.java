package com.example.pau.talaya;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.example.pau.talaya.LoginActivity.usuariActiu;
import static com.example.pau.talaya.home.FavoritsList;

/**
 * Created by Pau on 26/4/17.
 */

public class FiltreCerca extends AppCompatActivity implements MultiSpinner.OnItemClickListener {

    private Calendar dateCalendar = Calendar.getInstance();

    private DatePickerDialog CalendarPicker;


    private EditText txtnom;
    private EditText txtcom;
    private EditText txtcap;

    private EditText txtnumHabitacions;
    private EditText txtpreuMin;
    private EditText txtpoblacio;


    private String nom,comarca,numHabitacions,preuMin,poblacio;
    private int cap;
    private RatingBar stars;

    private MultiSpinner.MultiSpinnerListener listener;

    private List<String> items;
    public boolean[] seleccio;

    public static boolean Bfiltre = false;
    public static ArrayList<Casa> CasaFiltre = new ArrayList<Casa>();

    private Bundle b = new Bundle();

    private int billar = 0, campfut = 0, campten = 0, internet = 0, piscina = 0, projector = 0,sala = 0,tenistaula = 0;

    private ProgressDialog progress;
    private ArrayList<String> idFavorits;
    private Casa ObjCasa;

    private View view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtre);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#4C9141"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_flecha_back));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filtre de cerca");

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        items = Arrays.asList(getResources().getStringArray(R.array.multispinner_entries));

        view1 = getWindow().getDecorView().getRootView();

        txtnom = (EditText)findViewById(R.id.editNom);
        txtcom =(EditText)findViewById(R.id.editComarca);
        txtcap = (EditText)findViewById(R.id.editCapacitat);
        txtnumHabitacions = (EditText)findViewById(R.id.editNumHab);
        txtpoblacio = (EditText)findViewById(R.id.editPob);
        txtpreuMin = (EditText)findViewById(R.id.editPreu);


        final Button cerca = (Button)findViewById(R.id.button3);

        final RatingBar valoracio = (RatingBar)findViewById(R.id.ratingBar);

        LayerDrawable stars = (LayerDrawable) valoracio.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#57a639" +
                ""), PorterDuff.Mode.SRC_ATOP);


        MultiSpinner multiSpinner = (MultiSpinner)findViewById(R.id.multispinner);
        multiSpinner.setItems(items, "Selecciona les instal·lacions", listener);

        seleccio = multiSpinner.selected;

        //per obtenir la valoracio que han posat
        valoracio.getRating();

        cerca.setEnabled(true);

        cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                nom = txtnom.getText().toString();
                comarca = txtcom.getText().toString();
                cap = Integer.parseInt(txtcap.getText().toString());
                numHabitacions = txtnumHabitacions.getText().toString();
                poblacio = txtpoblacio.getText().toString();
                preuMin = txtpreuMin.getText().toString();

                consultaFiltre(view1,nom,cap,piscina,campfut,campten,tenistaula,billar,sala,projector,internet,comarca,poblacio);

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        switch (i){
            case 0:
                if (billar == 0){
                    billar = 1;
                }else {
                    billar = 0;
                }
                break;
            case 1:
                if (campfut == 0){
                    campfut = 1;
                }else {
                    campfut = 0;
                }
                break;
            case 2:
                if (campten == 0){
                    campten = 1;
                }else {
                    campten = 0;
                }
                break;
            case 3:
                if (internet == 0){
                    internet = 1;
                }else {
                    internet = 0;
                }
                break;
            case 4:
                if (piscina == 0){
                    piscina = 1;
                }else {
                    piscina = 0;
                }
                break;
            case 5:
                if (projector == 0){
                    projector = 1;
                }else {
                    projector = 0;
                }
                break;
            case 6:
                if (sala == 0){
                    sala = 1;
                }else {
                    sala = 0;
                }
                break;
            case 7:
                if (tenistaula == 0){
                    tenistaula = 1;
                }else {
                    tenistaula = 0;
                }
                break;
        }

    }

    private void consultaFiltre (final View view, String nom, int capacitat,int piscina, int campFut, int campTen, int TenisTaula, int billar, int salaComuna, int projector, int internet, String comarca, String poblacio){

        CasaFiltre.clear();

        AsyncHttpClient clientFiltre;

        String url = "http://talaiaapi.azurewebsites.net/api/buscar/?nom="+nom+"&capacitat="+capacitat+"&piscina="+piscina+"&campFutbol="+campFut+"&campTenis="+campTen+"&tenisTaula="+TenisTaula+"&billar="+billar+"&salaComuna="+salaComuna+"&projector="+projector+"&internet="+internet+"&comarca="+comarca+"&poblacio="+poblacio+"&carrerNum=&provincia=";

        clientFiltre = new AsyncHttpClient();

        clientFiltre.setMaxRetriesAndTimeout(0,10000);

        clientFiltre.get(this, url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

                progress = ProgressDialog.show(view.getContext(), "Progrés",
                        "Obtenint dades...", true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONArray jsonArray = null;
                JSONObject filtre = null;
                String str = new String(responseBody);

                String idFav = "";

                int IdCasa;
                String Nom;
                int PreuBasic;
                int PreuMitja;
                int PreuCompleta;
                String Descripcio;
                int Capacitat;
                int Habitacions;
                int Banys;
                int Piscina;
                int CampFutbol;
                int CampTenis;
                int TenisTaula;
                int Billar;
                int SalaComuna;
                int Projector;
                int Internet;
                String Comarca;
                String Poblacio;
                String CarrerNum;
                String Provincia;
                int CodiPostal;
                int FK_Propietari;
                double Mitjana;
                boolean favorits = false;


                try {

                    jsonArray = new JSONArray(str);

                    for (int i = 0; i < jsonArray.length();i++){

                        filtre = jsonArray.getJSONObject(i);

                        IdCasa = filtre.getInt("IdCasa");
                        Nom = filtre.getString("Nom");
                        PreuBasic = filtre.optInt("PreuBasic");
                        PreuMitja = filtre.optInt("PreuMitja");
                        PreuCompleta = filtre.optInt("PreuCompleta");
                        Descripcio = filtre.optString("Descripcio");
                        Capacitat = filtre.optInt("Capacitat");
                        Habitacions = filtre.optInt("Habitacions");
                        Banys = filtre.optInt("Banys");
                        Piscina = filtre.optInt("Piscina");
                        CampFutbol = filtre.optInt("CampFutbol");
                        CampTenis = filtre.optInt("CampTenis");
                        TenisTaula = filtre.optInt("TenisTaula");
                        Billar = filtre.optInt("Billar");
                        SalaComuna = filtre.optInt("SalaComuna");
                        Projector = filtre.optInt("Projector");
                        Internet = filtre.optInt("Internet");
                        Comarca = filtre.optString("Comarca");
                        Poblacio = filtre.optString("Poblacio");
                        CarrerNum = filtre.optString("CarrerNum");
                        Provincia = filtre.optString("Provincia");
                        CodiPostal = filtre.optInt("CodiPostal");
                        FK_Propietari = filtre.optInt("FKUsuari");
                        Mitjana = filtre.getDouble("Mitjana");

                        ObjCasa = new Casa(IdCasa, Nom, PreuBasic, PreuMitja, PreuCompleta, Descripcio, Capacitat, Habitacions, Banys, Piscina, CampFutbol, CampTenis, TenisTaula, Billar, SalaComuna, Projector, Internet, Comarca, Poblacio, CarrerNum, Provincia, CodiPostal, FK_Propietari, Mitjana, favorits);

                        CasaFiltre.add(ObjCasa);

                        Bfiltre = true;

                        b.putBoolean("filtre",Bfiltre);

                        Intent intencio = new Intent(FiltreCerca.this, home.class);

                        intencio.putExtras(b);

                        startActivity(intencio);

                    }

                }catch (JSONException e) {

                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Snackbar.make(view, "Error de conexió", Snackbar.LENGTH_LONG)
                        .show();
                progress.dismiss();

            }
        });
    }
}
