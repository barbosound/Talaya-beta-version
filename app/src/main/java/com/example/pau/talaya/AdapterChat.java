package com.example.pau.talaya;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import static com.example.pau.talaya.LoginActivity.usuariActiu;

public class AdapterChat extends SimpleCursorAdapter {
    public Cursor cursor;
    public SimpleCursorAdapter adapterdreta, adapteresquerra;
    public OpenHelper d;
    public DataBaseManager database;
    private LinearLayout missatgedreta, missatgeesquerra;

    public AdapterChat(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Cursor cursor = (Cursor) getItem(position);
        View view = super.getView(position, convertView, parent);
        TextView perfilmissatge = (TextView) view.findViewById(R.id.perfilmissatge);
        missatgeesquerra = (LinearLayout) view.findViewById(R.id.msg_bubble_background_textesquerra);
        missatgedreta = (LinearLayout) view.findViewById(R.id.msg_bubble_background_text);
        String comparar = perfilmissatge.getText().toString();

        //Cursor usuari;
        //usuari=database.getusuari();

        if(comparar.equalsIgnoreCase(String.valueOf(usuariActiu.getIdUsuari()))){

            missatgeesquerra.setVisibility(View.INVISIBLE);
            missatgedreta.setVisibility(View.VISIBLE);
        }
        else {

            missatgeesquerra.setVisibility(View.VISIBLE);
            missatgedreta.setVisibility(view.INVISIBLE);

       }


        return view;
    }



}


