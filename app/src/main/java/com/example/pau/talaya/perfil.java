package com.example.pau.talaya;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpDelete;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

import static com.example.pau.talaya.LoginActivity.usuariActiu;
import static com.example.pau.talaya.home.CasaList;

/**
 * Created by Pau on 23/5/17.
 */

public class perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_user);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#4C9141"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Perfil");

        TextView txtNom = (TextView)findViewById(R.id.textNom);
        TextView txtCog = (TextView)findViewById(R.id.textCognom);
        TextView txtCor = (TextView)findViewById(R.id.textCorreu);

        Button elimina = (Button)findViewById(R.id.button4);

        txtNom.setText(usuariActiu.getNom());
        txtCog.setText(usuariActiu.getCognom());
        txtCor.setText(usuariActiu.getCorreu());

        elimina.setBackgroundColor(Color.parseColor("#FF3E14"));

        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setTitle("Confirma");
                builder.setMessage("Segur que vols eliminar el compte?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        eliminaCompte();

                        Intent intencio = new Intent(perfil.this,LoginActivity.class);
                        startActivity(intencio);
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
            }
        });

    }

    public void eliminaCompte(){

        HttpClient client = new DefaultHttpClient();

        String url = "http://talaiaapi.azurewebsites.net/api/usuari/"+usuariActiu.getIdUsuari();

        HttpDelete httpdel = new HttpDelete(url);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        try {
            client.execute(httpdel);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
