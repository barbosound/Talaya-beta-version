package com.example.pau.talaya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    ArrayList <String> usuaris = new ArrayList<>();
    ArrayList <String> contrasenya = new ArrayList<>();

    String contra1;
    String contra2;
    String contra3;
    String contra4;
    String contra5;

    EditText txtUsr;
    EditText txtContra;

    public static UsuariActiu usuariActiu;
    private AsyncHttpClient clientUsuari;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button resgistre = (Button)findViewById(R.id.Registra);
        Button login = (Button)findViewById(R.id.Entra);

         txtUsr = (EditText)findViewById(R.id.editText);
         txtContra = (EditText)findViewById(R.id.editText4);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#4C9141"));


        resgistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intencio = new Intent(view.getContext(),form_login.class);

                startActivity(intencio);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contra = txtContra.getText().toString();
                String user = txtUsr.getText().toString();

                consultaApiLogin(view,user,contra);

            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();

//        txtContra.setText("");
//        txtUsr.setText("");

    }

    /* Retorna un hash a partir de un tipo y un texto */
    public static String getHash(String txt) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("SHA1");
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void consultaApiLogin(final View view, String correu, String contrasenya){

        final String url = "http://talaiaapi.azurewebsites.net/api/usuari/?correu="+correu+"&pass="+contrasenya;

        clientUsuari = new AsyncHttpClient();
        clientUsuari.setMaxRetriesAndTimeout(0,5000);

        clientUsuari.get(LoginActivity.this, url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

                progress = ProgressDialog.show(view.getContext(),"",
                        "Comprovant...", true);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                JSONArray jsonArray = null;
                JSONObject usuari = null;
                String str = new String(responseBody);

                try {

                    if (str.equals("null")){

                        txtUsr.setError("Usuari o contrasenya incorrectes");
                        txtUsr.requestFocus();

                        progress.dismiss();

                    }else{

                        usuari = new JSONObject(str);

                        usuariActiu = new UsuariActiu(usuari.getInt("IdUsuari"),usuari.getString("Nom"),usuari.getString("Cognom"),usuari.getString("Correu"));

                        progress.dismiss();

                        Intent intencio = new Intent(view.getContext(),home.class);

                        startActivity(intencio);

                    }

                }catch (JSONException e) {

                    e.printStackTrace();
                    progress.dismiss();

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
