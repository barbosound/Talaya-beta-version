package com.example.pau.talaya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by alber on 20/04/2017.
 */

public class DataBaseManager {

    private OpenHelper database;
    private SQLiteDatabase dbR, dbW;

    private Cursor cursor;


    public DataBaseManager(Context ctx){

        database   = new OpenHelper(ctx);

        dbR = database.getReadableDatabase();
        dbW = database.getWritableDatabase();

    }

    public void borrartot (int id, String nom){

        ContentValues vciutat = new ContentValues();


        //vciutat.put(database.CN_ID, id);
        //vciutat.put(database.CN_CIUTAT, nom);


//        dbW.insert(database.TABLE_CIUTATS, null, vciutat);
    }
    public void crearconversa (String perfil1, String perfil2, String idconversa ){

        ContentValues vciutat = new ContentValues();

        vciutat.put(database.C_Perfil, perfil1);
        vciutat.put(database.C_Perfil2, perfil2);
        vciutat.put(database.C_IDConversa, idconversa);

        dbW.insert(database.TABLE_Conversa, null, vciutat);
    }

    public Cursor getConversesperid(String idenvia, String idrep){


        String sql = "SELECT * FROM "+ database.TABLE_Conversa + " Where " + database.C_Perfil + " = ' " + idenvia + "' and " + database.C_Perfil2 + " = " + idrep;
        Cursor cursor = dbR.rawQuery(sql ,null);

        return cursor;

    }
    public int getCount(String idenvia, String idrep) {


        Cursor cursor = dbR.rawQuery("select * from " + database.TABLE_Conversa +
                " where " + idenvia + " = " + database.C_Perfil + " and " + idrep + " = " + database.C_Perfil2, null);

        int i = cursor.getCount();


        return i;
    }

    public Cursor getConverses(){


        Cursor cursor = dbR.rawQuery("SELECT * FROM "+ database.TABLE_Conversa,null);
        return cursor;

    }

    public Cursor getMissatges(int id1, int id2){

        Cursor cursor = dbR.rawQuery("SELECT * FROM "+ database.TABLE_Missatges +" Where " + database.M_Perfil + " = '" + id1 + "'collate NOCASE or " + database.M_Perfil + " = '" + id2 + "'collate NOCASE"  ,null);

        return cursor;
    }
    public Cursor getusuari(){

        Cursor usuari = dbR.rawQuery("SELECT"+ database.U_IDchat+" FROM "+ database.TABLE_Usuari, null);

        return usuari;
    }
    public void insertarmissatge (String data, String missatge, int idenvia, int idrep){

        ContentValues vciutat = new ContentValues();

        vciutat.put(database.M_Perfil,idenvia);
        vciutat.put(database.M_Perfil2, idrep);
        vciutat.put(database.M_Missatge, missatge);
        vciutat.put(database.M_Data, data);


        dbW.insert(database.TABLE_Missatges, null, vciutat);
    }
    public void insertarusuari ( int id,String correu, String nom,String contra,String imatge, int tipus, String token){

        ContentValues vciutat = new ContentValues();

        vciutat.put(database.U_Correu,correu);
        vciutat.put(database.U_Nom, nom);

        vciutat.put(database.U_Contrasenya, contra);
        vciutat.put(database.U_Imatgeperfil, imatge);
        vciutat.put(database.U_Tipuscompte, tipus);
        vciutat.put(database.U_IDchat, id);
        vciutat.put(database.U_Token, token);


        dbW.insert(database.TABLE_Usuari, null, vciutat);
    }
}
