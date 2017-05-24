package com.example.pau.talaya;

/**
 * Created by Pau on 15/5/17.
 */

public class Casa {

    public int getIdCasa() {
        return IdCasa;
    }

    public String getNom() {
        return Nom;
    }

    public int getPreuBasic() {
        return PreuBasic;
    }

    public int getPreuMitja() {
        return PreuMitja;
    }

    public int getPreuCompleta() {
        return PreuCompleta;
    }

    public String getDescripcio() {
        return Descripcio;
    }

    public int getCapacitat() {
        return Capacitat;
    }

    public int getHabitacions() {
        return Habitacions;
    }

    public int getBanys() {
        return Banys;
    }

    public int getPiscina() {
        return Piscina;
    }

    public int getCampFutbol() {
        return CampFutbol;
    }

    public int getCampTenis() {
        return CampTenis;
    }

    public int getTenisTaula() {
        return TenisTaula;
    }

    public int getBillar() {
        return Billar;
    }

    public int getSalaComuna() {
        return SalaComuna;
    }

    public int getProjector() {
        return Projector;
    }

    public int getInternet() {
        return Internet;
    }

    public String getComarca() {
        return Comarca;
    }

    public String getPoblacio() {
        return Poblacio;
    }

    public String getCarrerNum() {
        return CarrerNum;
    }

    public String getProvincia() {
        return Provincia;
    }

    public int getCodiPostal() {
        return CodiPostal;
    }

    public int getFK_Propietari() {
        return FK_Propietari;
    }

    public double getMitjana() {
        return Mitjana;
    }

    public boolean isFavorits() {
        return favorits;
    }

    private int IdCasa;
    private String Nom;
    private int PreuBasic;
    private int PreuMitja;
    private int PreuCompleta;
    private String Descripcio;
    private int Capacitat;
    private int Habitacions;
    private int Banys;
    private int Piscina;
    private int CampFutbol;
    private int CampTenis;
    private int TenisTaula;
    private int Billar;
    private int SalaComuna;
    private int Projector;
    private int Internet;
    private String Comarca;
    private String Poblacio;
    private String CarrerNum;
    private String Provincia;
    private int CodiPostal;
    private int FK_Propietari;
    private double Mitjana;
    private boolean favorits;

    public void setFavorits(boolean favorits) {
        this.favorits = favorits;
    }



    public Casa (int IdCasa,String Nom,int PreuBasic,int PreuMitja,int PreuCompleta,String Descripcio,int Capacitat,int Habitacions,int Banys,int Piscina,int CampFutbol,int CampTenis,int TenisTaula,int Billar,int SalaComuna,int Projector,int Internet,String Comarca,String Poblacio,String CarrerNum,String Provincia,int CodiPostal,int FK_Propietari,double Mitjana,boolean favorits){

        this.IdCasa = IdCasa;
        this.Nom = Nom;
        this.PreuBasic = PreuBasic;
        this.PreuMitja = PreuMitja;
        this.PreuCompleta = PreuCompleta;
        this.Descripcio = Descripcio;
        this.Capacitat = Capacitat;
        this.Habitacions = Habitacions;
        this.Banys = Banys;
        this.Piscina = Piscina;
        this.CampFutbol = CampFutbol;
        this.CampTenis = CampTenis;
        this.TenisTaula = TenisTaula;
        this.Billar = Billar;
        this.SalaComuna = SalaComuna;
        this.Projector = Projector;
        this.Internet = Internet;
        this.Comarca = Comarca;
        this.Poblacio = Poblacio;
        this.CarrerNum = CarrerNum;
        this.Provincia = Provincia;
        this.CodiPostal = CodiPostal;
        this.FK_Propietari = FK_Propietari;
        this.Mitjana = Mitjana;
        this.favorits = favorits;


    }
}
