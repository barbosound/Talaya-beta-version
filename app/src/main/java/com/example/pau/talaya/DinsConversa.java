package com.example.pau.talaya;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

import static com.example.pau.talaya.DescCasa.indexCasa;
import static com.example.pau.talaya.LoginActivity.usuariActiu;
import static com.example.pau.talaya.home.CasaList;


public class DinsConversa extends AppCompatActivity {
    public Cursor cursor,comprovar;
    public SimpleCursorAdapter adapterdreta;
    static AdapterChat ad;
    private AsyncHttpClient clientMissatge;
    private DataBaseManager d;
    private TextView txtMissatge;
    private String[] from;
    private int[] to;
    static int usuarirep;
   static int idperfilaenviar;
    static String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dins_conversa);
        final Context context = this;
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#4C9141"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_flecha_back));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Missatges");

        final Bundle dades = this.getIntent().getExtras();


        TextView missatgedreta = (TextView) findViewById(R.id.msg_text_message);
        TextView horamissatge = (TextView) findViewById(R.id.msg_text_time_message);

        txtMissatge = (TextView) findViewById(R.id.missatge);


        final ImageButton enviarmissatge = (ImageButton) findViewById(R.id.enviarmissatge);
        d = new DataBaseManager(this);
        from = new String[]{OpenHelper.M_Missatge, OpenHelper.M_Data,OpenHelper.M_Perfil, OpenHelper.M_Missatge, OpenHelper.M_Data};
        to = new int[]{R.id.msg_text_message, R.id.msg_text_time_message, R.id.perfilmissatge,R.id.msg_text_time_messageesquerra, R.id.msg_text_messageesquerra};

        cursor = d.getMissatges(usuariActiu.getIdUsuari(),idperfilaenviar);
        mostraListdreta(cursor, from, to);

        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);

        Bundle datos = this.getIntent().getExtras();

        int intrep =datos.getInt("idrep");
        final String id =  String.valueOf(usuariActiu.getIdUsuari());

        ///////////////////////////////////
        ///CALLBACK
        //////////////////////////////////
       final String nom= CasaList.get(indexCasa).getNom();
        Timer autoUpdate= new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {


                        getMissatge(idperfilaenviar,usuariActiu.getIdUsuari());
                        cursor = d.getMissatges(usuariActiu.getIdUsuari(),usuarirep);
                        mostraListdreta(cursor, from, to);

                    }
                });
            }
        }, 0, 5000);

        enviarmissatge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!txtMissatge.getText().toString().equals("")){
                    if(d.getCount(id,idd)==0){
                        d = new DataBaseManager(context);
                        d.crearconversa(id,idd,CasaList.get(indexCasa).getNom());
                        conversespost(id,idd,nom);
                    }
                    String date = (String) DateFormat.format("yyyy-MM-dd kk:mm:ss", new Date());
                    String miss = txtMissatge.getText().toString();

                    d.insertarmissatge(date, miss, usuariActiu.getIdUsuari(), idperfilaenviar);

                    txtMissatge.setText("");

                    apiPost(miss,date);

                    getMissatge(idperfilaenviar,usuariActiu.getIdUsuari());
                    cursor = d.getMissatges(usuariActiu.getIdUsuari(),usuarirep);
                    mostraListdreta(cursor, from, to);

                }

            }
        });
    }

    public void mostraListdreta(Cursor cursor, String[] from, int[] to) {

        ListView llista = (ListView)findViewById(R.id.listConversa);

        adapterdreta = new AdapterChat(this, R.layout.bombolladreta, cursor, from, to, 1);
        llista.setAdapter(adapterdreta);

        llista.setSelection(llista.getAdapter().getCount()-1);


    }

    private void apiPost(String missatge, String data) {

        HttpResponse response, response2;
        HttpClient client = new DefaultHttpClient();
        HttpClient client2 = new DefaultHttpClient();
        String url = "http://talaiaapi.azurewebsites.net/api/missatge";
        String url2 = "http://talaiaapi.azurewebsites.net/api/conversa";
        HttpPost post = new HttpPost(url);
        HttpPost post2 = new HttpPost(url2);

        JSONObject miss = new JSONObject();
        JSONObject conversa = new JSONObject();


        try {
            miss.put("Text",missatge);
            miss.put("Data",data);

            ///////////////////////////////////////////////////////////////
            ///AQUI!!
            ///////////////////////////////

            miss.put("FKUsuariEnvia",usuariActiu.getIdUsuari());
            miss.put("FKUsuariRep",idperfilaenviar);
            //conversa.put("FKUsuari1",usuariActiu.getIdUsuari());
            //conversa.put("FKUsuari2",idperfilaenviar);
            //conversa.put("NomConversa", )

        } catch (JSONException e) {
            e.printStackTrace();
        }


        String strUser = miss.toString();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        post.setEntity(new StringEntity(strUser, "UTF-8"));
        post.setHeader("Content-Type", "application/json");
        //post2.setEntity(new StringEntity(strUser, "UTF-8"));
        //post2.setHeader("Content-Type", "application/json");

        try {
            response = client.execute(post);
            String sresponse = response.getEntity().toString();

            //response2 = client2.execute(post2);
            //String sresponse2 = response2.getEntity().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void conversespost(String id1, String id2, String nomcasa) {

        HttpResponse response;
        HttpClient client = new DefaultHttpClient();
        String url = "http://talaiaapi.azurewebsites.net/api/conversa";
        HttpPost post = new HttpPost(url);

        JSONObject conversa = new JSONObject();


        try {
            conversa.put("FKUsuari1",id1);
            conversa.put("FKUsuari2",id2);
            conversa.put("NomConversa",nomcasa);


            ///////////////////////////////////////////////////////////////
            ///AQUI!!
            ///////////////////////////////


        } catch (JSONException e) {
            e.printStackTrace();
        }


        String strUser = conversa.toString();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        post.setEntity(new StringEntity(strUser, "UTF-8"));
        post.setHeader("Content-Type", "application/json");

        try {
            response = client.execute(post);
            String sresponse = response.getEntity().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
//    public abstract class AlwaysAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
//        @Override
//        public boolean getUseSynchronousMode() {
//            return false;
//        }
//    }
    private void getMissatge(int id, int id2){

        String url = " http://talaiaapi.azurewebsites.net/api/missatge/?envia="+ id +"&rep="+id2;

        clientMissatge = new AsyncHttpClient();
        clientMissatge.setMaxRetriesAndTimeout(0,10000);

        clientMissatge.get(DinsConversa.this, url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONArray jsonArray = null;
                JSONObject missatge = null;
                String str = new String(responseBody);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                String dateString,miss;

                String oldFormat = "MM/dd/yyyy HH:mm:ss";
                String newFormat = "dd/MM/yyyy";

                SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
                SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);


                try {

                    jsonArray = new JSONArray(str);

                    for (int i = 0; i < jsonArray.length();i++){

                        missatge = jsonArray.getJSONObject(i);

                        if (missatge.getInt("FKUsuariRep") == usuariActiu.getIdUsuari()){

                            miss = missatge.getString("Text");
                            dateString = (String) missatge.get("Data");

                            dateString = sdf2.format(sdf1.parse(dateString));


                            d.insertarmissatge(miss,dateString,usuarirep,usuariActiu.getIdUsuari());
                            cursor = d.getMissatges(usuariActiu.getIdUsuari(),usuarirep);

                            mostraListdreta(cursor, from, to);

                        }



                    }

                }catch (JSONException e) {

                    e.printStackTrace();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


            }
        });


    }
    static void idsconversa(int idrep){

        idperfilaenviar=idrep;
        idd= String.valueOf(idperfilaenviar);


    }
}
