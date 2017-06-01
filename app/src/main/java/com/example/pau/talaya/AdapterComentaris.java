package com.example.pau.talaya;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Pau on 31/5/17.
 */

public class AdapterComentaris extends ArrayAdapter<String> {

    ArrayList<String> puntuacio = new ArrayList<>();
    ArrayList<String> comentari = new ArrayList<>();
    ArrayList<String> NomUser = new ArrayList<>();

    public AdapterComentaris(Context context, int layoutResourceId, ArrayList<String> puntuacio,ArrayList<String> comentari,ArrayList<String> NomUser) {
        super(context, layoutResourceId, puntuacio);

        this.puntuacio = puntuacio;
        this.comentari = comentari;
        this.NomUser = NomUser;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        float rat;

        LayoutInflater inflater = LayoutInflater.from(getContext());

        view = inflater.inflate(R.layout.row_comentaris,null);

        TextView nom = (TextView)view.findViewById(R.id.txtNom);
        TextView txtcomentari = (TextView)view.findViewById(R.id.txtComen);
        RatingBar rating = (RatingBar)view.findViewById(R.id.ratingBar3);

        nom.setText(NomUser.get(position));
        txtcomentari.setText(comentari.get(position));

        rat = Float.parseFloat(puntuacio.get(position));

        rating.setEnabled(false);

        rating.setRating(rat);

        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#57a639" +
                ""), PorterDuff.Mode.SRC_ATOP);


        return view;

    }

}
