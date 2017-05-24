package com.example.pau.talaya;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.pau.talaya.FiltreCerca.Bfiltre;
import static com.example.pau.talaya.LoginActivity.usuariActiu;

/**
 * Created by Pau on 15/3/17.
 */

public class home extends AppCompatActivity implements ListCases.OnFragmentInteractionListener,
        pendents.OnFragmentInteractionListener,
        missatges.OnFragmentInteractionListener,Reserva.OnFragmentInteractionListener,
        finalitzades.OnFragmentInteractionListener, favorits.OnFragmentInteractionListener,
        Notificacions.OnFragmentInteractionListener, QuiSom.OnFragmentInteractionListener,
        AvisLegal.OnFragmentInteractionListener{

    public static ArrayList<Casa> CasaList = new ArrayList<Casa>();
    public static ArrayList<Casa> FavoritsList = new ArrayList<Casa>();

    ListCases listCases = new ListCases();
    missatges miss = new missatges();
    Reserva reserva = new Reserva();

    FragmentManager fM = getSupportFragmentManager();

    private ArrayList<String> idReserva = new ArrayList<>();
    private ArrayList<String> preuReserva = new ArrayList<>();
    private ArrayList<String> diesReserva = new ArrayList<>();
    private ArrayList<String> DataEntrada = new ArrayList<>();
    private ArrayList<String> DataSortida = new ArrayList<>();
    private ArrayList<String> FKUsuari = new ArrayList<>();
    private ArrayList<String> FKCasa = new ArrayList<>();
    private ArrayList<String> Estat = new ArrayList<>();

    public static ArrayList<String> idFavorits = new ArrayList<>();

    public static boolean teReserves = false, teFavorits = false, teFinalitzades = false;

    public Bundle bReserva = new Bundle();
    public Bundle bFiltre = new Bundle();
    public ProgressDialog progress;

    private View view;

    private Casa ObjCasa;

    private ArrayList<String> idReservaFin = new ArrayList<>();
    private ArrayList<String> preuReservaFin = new ArrayList<>();
    private ArrayList<String> DataEntradaFin = new ArrayList<>();
    private ArrayList<String> DataSortidaFin = new ArrayList<>();
    private ArrayList<String> FKUsuariFin = new ArrayList<>();
    private ArrayList<String> FKCasaFin = new ArrayList<>();
    private ArrayList<String> EstatFin = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#4C9141"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Resultats");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        view = getWindow().getDecorView().getRootView();

        if (Bfiltre){

            consultaFiltreReserves(view);

            if(!listCases.isAdded()){
                fM.beginTransaction().replace(R.id.frame,listCases).commit();
            }else{
                fM.beginTransaction().show(listCases).commit();
            }

        }else {

            consultaFavorits(view);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:

                                getSupportActionBar().setTitle("Resultats");

                                if(!listCases.isAdded()){
                                    fM.beginTransaction().replace(R.id.frame,listCases).commit();
                                }else{
                                    fM.beginTransaction().show(listCases).commit();
                                }

                                break;
                            case R.id.missatges:

                                getSupportActionBar().setTitle("Missatges");

                                if(!miss.isAdded()){
                                    fM.beginTransaction().replace(R.id.frame,miss).commit();
                                }else{
                                    fM.beginTransaction().show(miss).commit();
                                }

                                break;
                            case R.id.Reserves:

                                getSupportActionBar().setTitle("Reserves");

                                if(!reserva.isAdded()){
                                    fM.beginTransaction().replace(R.id.frame, reserva).commit();
                                }else{
                                    fM.beginTransaction().show(reserva).commit();
                                }
                                break;
                            case R.id.surt:

                                View v = getWindow().getDecorView().getRootView();

                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                                builder.setTitle("Confirma");
                                builder.setMessage("Estas segur que vols sortir?");

                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intencio = new Intent(home.this,LoginActivity.class);
                                        startActivity(intencio);
                                        dialog.dismiss();
                                    }
                                });

                                builder.setNegativeButton("NO",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                // Do nothing
                                                dialog.dismiss();
                                            }
                                        });

                                AlertDialog alert = builder.create();
                                alert.show();


                                break;

                        }
                        return true;
                    }
                });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar,menu);

        return super.onCreateOptionsMenu(menu);
    }

    //MENU SETTINGS\\
    //carreguem els fragments al frame de home\\
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.qui_som:

                QuiSom qui = new QuiSom();

                if(!qui.isAdded())
                {fM.beginTransaction().replace(R.id.frame, qui).commit();}
                else
                {fM.beginTransaction().show(qui).commit();}

                break;

            case R.id.avis_legal:

                AvisLegal avis = new AvisLegal();

                if(!avis.isAdded())
                {fM.beginTransaction().replace(R.id.frame, avis).commit();}
                else
                {fM.beginTransaction().show(avis).commit();}

                break;

            case R.id.advanced:

                Intent intencio = new Intent(view.getContext(),FiltreCerca.class);

                startActivity(intencio);

                break;

            case R.id.refresh:

                Bfiltre = false;
                fM.beginTransaction().remove(listCases).commit();
                consultaFavorits(view);

                break;

            case R.id.perfil:

                Intent intent = new Intent(view.getContext(),perfil.class);

                startActivity(intent);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void consultaApiCasa(final View view){

        AsyncHttpClient clientCasa;

        String url = "http://talaiaapi.azurewebsites.net/api/casa";

        CasaList.clear();
        FavoritsList.clear();

        clientCasa = new AsyncHttpClient();

        clientCasa.setMaxRetriesAndTimeout(0,10000);

        clientCasa.get(home.this, url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONArray jsonArray = null;
                JSONObject casa = null;
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

                        casa = jsonArray.getJSONObject(i);

                        IdCasa = casa.getInt("IdCasa");
                        Nom = casa.getString("Nom");
                        PreuBasic = casa.optInt("PreuBasic");
                        PreuMitja = casa.optInt("PreuMitja");
                        PreuCompleta = casa.optInt("PreuCompleta");
                        Descripcio = casa.optString("Descripcio");
                        Capacitat = casa.optInt("Capacitat");
                        Habitacions = casa.optInt("Habitacions");
                        Banys = casa.optInt("Banys");
                        Piscina = casa.optInt("Piscina");
                        CampFutbol = casa.optInt("CampFutbol");
                        CampTenis = casa.optInt("CampTenis");
                        TenisTaula = casa.optInt("TenisTaula");
                        Billar = casa.optInt("Billar");
                        SalaComuna = casa.optInt("SalaComuna");
                        Projector = casa.optInt("Projector");
                        Internet = casa.optInt("Internet");
                        Comarca = casa.optString("Comarca");
                        Poblacio = casa.optString("Poblacio");
                        CarrerNum = casa.optString("CarrerNum");
                        Provincia = casa.optString("Provincia");
                        CodiPostal = casa.optInt("CodiPostal");
                        FK_Propietari = casa.optInt("FKUsuari");
                        Mitjana = casa.getDouble("Mitjana");

                        for (int x = 0; x < idFavorits.size();x++){

                            idFav = idFavorits.get(x);

                            if (idFav.equals(String.valueOf(IdCasa))){

                                favorits = true;
                                break;

                            }else{

                                favorits = false;

                            }
                        }

                        ObjCasa = new Casa(IdCasa, Nom, PreuBasic, PreuMitja, PreuCompleta, Descripcio, Capacitat, Habitacions, Banys, Piscina, CampFutbol, CampTenis, TenisTaula, Billar, SalaComuna, Projector, Internet, Comarca, Poblacio, CarrerNum, Provincia, CodiPostal, FK_Propietari, Mitjana, favorits);

                        if (ObjCasa.isFavorits()){

                            FavoritsList.add(ObjCasa);
                            teFavorits = true;
                        }

                        CasaList.add(ObjCasa);

                    }

                }catch (JSONException e) {

                    e.printStackTrace();

                }


                if(!listCases.isAdded()){
                    fM.beginTransaction().replace(R.id.frame,listCases).commit();
                }else{
                    fM.beginTransaction().show(listCases).commit();
                }

//                fM.beginTransaction().add(R.id.frame,listCases).commit();

                consultaApiReserves(view);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Snackbar.make(view, "Error de conexió", Snackbar.LENGTH_LONG)
                        .show();
                progress.dismiss();

            }
        });

    }

    private void consultaApiReserves(final View view){

        AsyncHttpClient clientReserva;

        String url = "http://talaiaapi.azurewebsites.net/api/reserva";

        idReserva.clear();
        preuReserva.clear();
        DataEntrada.clear();
        DataSortida.clear();
        FKCasa.clear();
        Estat.clear();

        idReservaFin.clear();
        preuReservaFin.clear();
        DataEntradaFin.clear();
        DataSortidaFin.clear();
        FKUsuariFin.clear();
        FKCasaFin.clear();
        EstatFin.clear();

        clientReserva = new AsyncHttpClient();

        clientReserva.setMaxRetriesAndTimeout(0,10000);

        clientReserva.get(home.this, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONArray jsonArray = null;
                JSONObject reserva = null;
                String str = new String(responseBody);

                try {

                    jsonArray = new JSONArray(str);

                    for (int i = 0; i < jsonArray.length();i++){

                        reserva = jsonArray.getJSONObject(i);

                        if (reserva.getInt("FKUsuari")==(usuariActiu.getIdUsuari())){

                            if (reserva.getString("Estat").equals("Pendent") || reserva.getString("Estat").equals("Acceptada")){

                                idReserva.add(String.valueOf(reserva.getInt("IdReserva")));
                                preuReserva.add(String.valueOf(reserva.getInt("Preu")));
                                DataEntrada.add(String.valueOf(reserva.get("DataEntrada")));
                                DataSortida.add(String.valueOf(reserva.get("DataSortida")));
                                FKUsuari.add(String.valueOf(reserva.getInt("FKUsuari")));
                                FKCasa.add(String.valueOf(reserva.getInt("FKCasa")));
                                Estat.add(reserva.getString("Estat"));

                                teReserves = true;
                            }else {
                                if (reserva.getString("Estat").equals("Finalitzada")){

                                    idReservaFin.add(String.valueOf(reserva.getInt("IdReserva")));
                                    preuReservaFin.add(String.valueOf(reserva.getInt("Preu")));
                                    DataEntradaFin.add(String.valueOf(reserva.get("DataEntrada")));
                                    DataSortidaFin.add(String.valueOf(reserva.get("DataSortida")));
                                    FKUsuariFin.add(String.valueOf(reserva.getInt("FKUsuari")));
                                    FKCasaFin.add(String.valueOf(reserva.getInt("FKCasa")));
                                    EstatFin.add(reserva.getString("Estat"));

                                    teFinalitzades = true;
                                }
                            }

                        }

                    }

                }catch (JSONException e) {

                    e.printStackTrace();

                }

                bReserva.putStringArrayList("id",idReserva);
                bReserva.putStringArrayList("preu",preuReserva);
                bReserva.putStringArrayList("DE",DataEntrada);
                bReserva.putStringArrayList("DS",DataSortida);
                bReserva.putStringArrayList("FKUsuari",FKUsuari);
                bReserva.putStringArrayList("FKCasa",FKCasa);
                bReserva.putStringArrayList("Estat",Estat);

                bReserva.putStringArrayList("idFin",idReservaFin);
                bReserva.putStringArrayList("preuFin",preuReservaFin);
                bReserva.putStringArrayList("DEFin",DataEntradaFin);
                bReserva.putStringArrayList("DSFin",DataSortidaFin);
                bReserva.putStringArrayList("FKUsuariFin",FKUsuariFin);
                bReserva.putStringArrayList("FKCasaFin",FKCasaFin);
                bReserva.putStringArrayList("EstatFin",EstatFin);

                home.this.reserva.setArguments(bReserva);

                progress.dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Snackbar.make(view, "Error de conexió", Snackbar.LENGTH_LONG)
                        .show();

                progress.dismiss();
            }
        });

    }

    private void consultaFavorits(final View view){

        idFavorits.clear();

        AsyncHttpClient clientFavorits;

        String url = "http://talaiaapi.azurewebsites.net/api/marcador/"+usuariActiu.getIdUsuari();

        clientFavorits = new AsyncHttpClient();

        clientFavorits.setMaxRetriesAndTimeout(0,10000);

        clientFavorits.get(this, url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

                progress = ProgressDialog.show(view.getContext(), "Progrés",
                        "Obtenint dades...", true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONArray jsonArray = null;
                JSONObject favorit = null;
                String str = new String(responseBody);

                try {

                    jsonArray = new JSONArray(str);

                    for (int i = 0; i < jsonArray.length();i++){

                        favorit = jsonArray.getJSONObject(i);

                        idFavorits.add(String.valueOf(favorit.getInt("FKCasa")));

                    }

                }catch (JSONException e) {

                    e.printStackTrace();

                }

                consultaApiCasa(view);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Snackbar.make(view, "Error de conexió", Snackbar.LENGTH_LONG)
                        .show();
                progress.dismiss();

            }
        });

    }
    private void consultaFiltreReserves(final View view){

        AsyncHttpClient clientReserva;

        String url = "http://talaiaapi.azurewebsites.net/api/reserva";

        idReserva.clear();
        preuReserva.clear();
        DataEntrada.clear();
        DataSortida.clear();
        FKCasa.clear();
        Estat.clear();

        idReservaFin.clear();
        preuReservaFin.clear();
        DataEntradaFin.clear();
        DataSortidaFin.clear();
        FKUsuariFin.clear();
        FKCasaFin.clear();
        EstatFin.clear();

        clientReserva = new AsyncHttpClient();

        clientReserva.setMaxRetriesAndTimeout(0,10000);

        clientReserva.get(home.this, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONArray jsonArray = null;
                JSONObject reserva = null;
                String str = new String(responseBody);

                try {

                    jsonArray = new JSONArray(str);

                    for (int i = 0; i < jsonArray.length();i++){

                        reserva = jsonArray.getJSONObject(i);

                        if (reserva.getInt("FKUsuari")==(usuariActiu.getIdUsuari())){

                            if (reserva.getString("Estat").equals("Pendent") || reserva.getString("Estat").equals("Acceptada")){

                                idReserva.add(String.valueOf(reserva.getInt("IdReserva")));
                                preuReserva.add(String.valueOf(reserva.getInt("Preu")));
                                DataEntrada.add(String.valueOf(reserva.get("DataEntrada")));
                                DataSortida.add(String.valueOf(reserva.get("DataSortida")));
                                FKUsuari.add(String.valueOf(reserva.getInt("FKUsuari")));
                                FKCasa.add(String.valueOf(reserva.getInt("FKCasa")));
                                Estat.add(reserva.getString("Estat"));

                                teReserves = true;
                            }else {
                                if (reserva.getString("Estat").equals("Finalitzada")){

                                    idReservaFin.add(String.valueOf(reserva.getInt("IdReserva")));
                                    preuReservaFin.add(String.valueOf(reserva.getInt("Preu")));
                                    DataEntradaFin.add(String.valueOf(reserva.get("DataEntrada")));
                                    DataSortidaFin.add(String.valueOf(reserva.get("DataSortida")));
                                    FKUsuariFin.add(String.valueOf(reserva.getInt("FKUsuari")));
                                    FKCasaFin.add(String.valueOf(reserva.getInt("FKCasa")));
                                    EstatFin.add(reserva.getString("Estat"));

                                    teFinalitzades = true;
                                }
                            }

                        }

                    }

                }catch (JSONException e) {

                    e.printStackTrace();

                }

                bReserva.putStringArrayList("id",idReserva);
                bReserva.putStringArrayList("preu",preuReserva);
                bReserva.putStringArrayList("DE",DataEntrada);
                bReserva.putStringArrayList("DS",DataSortida);
                bReserva.putStringArrayList("FKUsuari",FKUsuari);
                bReserva.putStringArrayList("FKCasa",FKCasa);
                bReserva.putStringArrayList("Estat",Estat);

                bReserva.putStringArrayList("idFin",idReservaFin);
                bReserva.putStringArrayList("preuFin",preuReservaFin);
                bReserva.putStringArrayList("DEFin",DataEntradaFin);
                bReserva.putStringArrayList("DSFin",DataSortidaFin);
                bReserva.putStringArrayList("FKUsuariFin",FKUsuariFin);
                bReserva.putStringArrayList("FKCasaFin",FKCasaFin);
                bReserva.putStringArrayList("EstatFin",EstatFin);

                home.this.reserva.setArguments(bReserva);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Snackbar.make(view, "Error de conexió", Snackbar.LENGTH_LONG)
                        .show();

            }
        });

    }

}
