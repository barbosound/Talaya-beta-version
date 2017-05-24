package com.example.pau.talaya;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.pau.talaya.R;

import java.util.ArrayList;

import static com.example.pau.talaya.home.CasaList;

/**
 * Created by Pau on 26/4/17.
 */

public class AdapterCasa extends ArrayAdapter<Casa> {

    private ArrayList<Casa> CasaList = new ArrayList<>();

    public AdapterCasa(Context context, int layoutResourceId, ArrayList<Casa> CasaList) {
        super(context, layoutResourceId, CasaList);

        this.CasaList = CasaList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Float avg;
        String preu;

        LayoutInflater inflater = LayoutInflater.from(getContext());

        view = inflater.inflate(R.layout.casa_row,null);

        RatingBar avgRating = (RatingBar)view.findViewById(R.id.avgRating);

        LayerDrawable stars = (LayerDrawable) avgRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#57a639" +
                ""), PorterDuff.Mode.SRC_ATOP);

        TextView txtid = (TextView)view.findViewById(R.id.textID);
        TextView txtnom = (TextView)view.findViewById(R.id.textNom);
        TextView txtdesc = (TextView)view.findViewById(R.id.textCap);
        TextView txtcom = (TextView)view.findViewById(R.id.textComarca);
        TextView txtpreu = (TextView)view.findViewById(R.id.textPreu);

        txtid.setText(String.valueOf(CasaList.get(position).getIdCasa()));
        txtnom.setText(CasaList.get(position).getNom());
        txtdesc.setText(String.valueOf(CasaList.get(position).getCapacitat()));
        txtcom.setText("("+CasaList.get(position).getComarca()+")");
        preu = String.valueOf(CasaList.get(position).getPreuBasic() +" €");
        txtpreu.setText(preu);
        avg = Float.parseFloat(String.valueOf(CasaList.get(position).getMitjana()));

        avgRating.setEnabled(false);

        avgRating.setRating(avg);

        return view;

    }

}
