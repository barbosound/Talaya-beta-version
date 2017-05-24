package com.example.pau.talaya;

/**
 * Created by Pau on 9/5/17.
 */

public class UsuariActiu {
    private int IdUsuari;
    private String Nom, Cognom, correu;
    private int Token;

    public String getNom() {
        return Nom;
    }

    public String getCognom() {
        return Cognom;
    }

    public int getIdUsuari() {
        return IdUsuari;
    }

    public String getCorreu() {
        return correu;
    }

    public UsuariActiu(int IdUsuari, String Nom, String Cognom, String Correu){

        this.IdUsuari = IdUsuari;
        this.Nom = Nom;
        this.Cognom = Cognom;
        this.correu = Correu;

    }
}
