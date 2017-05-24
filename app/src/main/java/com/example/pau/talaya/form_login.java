package com.example.pau.talaya;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.IOException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by Pau on 9/3/17.
 */

public class form_login extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulari_login);

        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#4C9141"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_flecha_back));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Formulari de registre");

        Button registrat = (Button)findViewById(R.id.button2);
        final EditText contra = (EditText)findViewById(R.id.editText5);
        final EditText confirm_contra = (EditText)findViewById(R.id.edit_contra);
        final EditText usuari = (EditText)findViewById(R.id.editText2);
        final EditText cognom = (EditText)findViewById(R.id.editText1);
        final EditText mail = (EditText)findViewById(R.id.editText4);

        registrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = contra.getText().toString();

                int contNumero = 0;

                for (int i = 0; i < password.length(); i++) {
                    contNumero++;
                }

              if (contNumero < 7){

                  contra.setError("Ha de contenir minim 7 caràcters!");
                  contra.requestFocus();

              }else {
                  if (!(contra.getText().toString().equals(confirm_contra.getText().toString()))){

                       contra.setError("Les contrassenyes no coincideixen!");
                       contra.requestFocus();


                  }else{
                      if(usuari.getText().toString().equals("")){

                          usuari.setError("No pot estar buit!");
                          usuari.requestFocus();

                      }else {
                          if(mail.getText().toString().equals("")) {

                              mail.setError("No pot estar buit!");
                              mail.requestFocus();

                          }else {

                              Toast.makeText(getApplicationContext(),contra.getText().toString(),
                                      Toast.LENGTH_SHORT).show();

                              new Thread(new Runnable() {
                                  public void run() {
                                      apiPost(usuari.getText().toString(),cognom.getText().toString(),contra.getText().toString(),mail.getText().toString());
                                  }
                              }).start();


                              Intent intencio = new Intent(getApplicationContext(),LoginActivity.class);
                              startActivity(intencio);
                          }
                      }
                  }
              }
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...
                finish();
            }
        });


    }

    private void apiPost(String nom, String cognom, String contra, String correu) {

        HttpResponse response;
        HttpClient client = new DefaultHttpClient();
        String url = "http://talaiaapi.azurewebsites.net/api/usuari";
        HttpPost post = new HttpPost(url);

        JSONObject user = new JSONObject();


        try {
            user.put("Nom",nom);
            user.put("Cognom",cognom);
            user.put("Contrasenya",contra);
            user.put("Correu",correu);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String strUser = user.toString();

        post.setEntity(new StringEntity(strUser, "UTF-8"));
        post.setHeader("Content-Type", "application/json");

        try {
            response = client.execute(post);
            String sresponse = response.getEntity().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//            imageView.setImageBitmap(bmp);

        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


}
