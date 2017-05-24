package com.example.pau.talaya;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

/**
 * Created by alber on 16/05/2017.
 */

public class AdapterConverses extends SimpleCursorAdapter {


    public AdapterConverses(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }
}
