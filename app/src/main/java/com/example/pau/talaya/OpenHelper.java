package com.example.pau.talaya;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alber on 20/04/2017.
 */

public class OpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Talaia";
    public static final int DB_SHEME_VERSION = 1;

    //Definim constants
    public static final String TABLE_Conversa = "Conversa";
    public static final String C_ID = "_id";
    public static final String C_Perfil = "NomPerfil";
    public static final String C_Perfil2 = "NomPerfil2";
    public static final String C_IDConversa = "IDConversa";

    public static final String TABLE_Usuari = "Usuari";
    public static final String U_ID = "_id";
    public static final String U_Correu = "Correu";
    public static final String U_Nom = "Nom";
    public static final String U_Cognom = "Cognom";
    public static final String U_Contrasenya = "Contrasenya";
    public static final String U_Imatgeperfil = "Imatge";
    public static final String U_Tipuscompte = "TipusCompte";
    public static final String U_Token = "Token";
    public static final String U_IDchat = "Idchat";


    public static final String TABLE_Missatges = "Missatges";
    public static final String M_ID = "_id";
    public static final String M_Perfil = "NomPerfil";
    public static final String M_Missatge = "Missatge";
    public static final String M_Perfil2 = "NomPerfil2";
    public static final String M_Data = "Data";


    public OpenHelper(Context context) {
        super(context, DB_NAME, null, DB_SHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = " create table " + TABLE_Conversa + " ("
                + C_ID + " integer primary key autoincrement,"
                + C_Perfil2 + " text not null,"
                + C_IDConversa + " integer not null,"
                + C_Perfil + " text not null);";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE2 = " create table " + TABLE_Usuari + " ("
                + U_ID + " integer primary key autoincrement,"
                + U_Correu + " text not null,"
                + U_Nom + " text not null,"
                + U_Contrasenya + " text not null,"
                + U_Imatgeperfil + " text not null,"
                + U_Tipuscompte + " integer not null,"
                + U_IDchat + " integer not null,"
                + U_Token + " text not null);";
        db.execSQL(CREATE_TABLE2);

        String CREATE_TABLE3 = " create table " + TABLE_Missatges + " ("
                + M_ID + " integer primary key autoincrement,"
                + M_Perfil + " text not null,"
                + M_Perfil2 + " text not null,"
                + M_Data + " text not null,"
                + M_Missatge + " text not null);";
        db.execSQL(CREATE_TABLE3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String CREATE_TABLE = " create table " + TABLE_Conversa + " ("
                + C_ID + " integer primary key autoincrement,"
                + C_Perfil2 + " text not null,"
                + C_IDConversa + " integer not null,"
                + C_Perfil + " text not null);";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE2 = " create table " + TABLE_Usuari + " ("
                + U_ID + " integer primary key autoincrement,"
                + U_Correu + " text not null,"
                + U_Nom + " text not null,"
                + U_Contrasenya + " text not null,"
                + U_Imatgeperfil + " text not null,"
                + U_Tipuscompte + " integer not null,"
                + U_IDchat + " integer not null,"
                + U_Token + " text not null);";
        db.execSQL(CREATE_TABLE2);

        String CREATE_TABLE3 = " create table " + TABLE_Missatges + " ("
                + M_ID + " integer primary key autoincrement,"
                + M_Perfil + " text not null,"
                + M_Perfil2 + " text not null,"
                + M_Data + " text not null,"
                + M_Missatge + " text not null);";
        db.execSQL(CREATE_TABLE3);
    }
}
