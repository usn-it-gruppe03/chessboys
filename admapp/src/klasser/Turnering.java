/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasser;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andersbaero
 */
public class Turnering implements Serializable{
    private String navn;
    private String fraDato;
    private String tilDato;
    private String sted;
    private String fil;
    private final String CONST_PATH = "admapp/src/turneringer/";
    private ArrayList<Spiller> spillerListe = new ArrayList<>();
    private ArrayList<Parti> partiListe = new ArrayList<>();

    public Turnering(String navn, String fraDato, String tilDato, String sted){
        this.navn = navn;
        this.fraDato = fraDato;
        this.tilDato = tilDato;
        this.sted = sted;



        

    }

    public void setSpillerArray(ArrayList<Spiller> spillerListe) {
        this.spillerListe = spillerListe;
    }
    
    public void leggTilSpiller(Spiller s){
        spillerListe.add(s);
    }

    public ArrayList<Spiller> hentSpillerArray(){
        return spillerListe;
    }
    
    public void leggTilParti(Parti p){
        partiListe.add(p);
    }

    public ArrayList<Parti> hentParti(){
        return partiListe;
    }
    
    public void lagMappe(){    
        new File(this.fil).mkdirs();
    }
    
    public boolean mappeFinnes(String innFil){
        if(new File(innFil).isDirectory()){
            return true;
        }else{
            return false;
        }
    }

    public String getNavn() {
        return navn;
    }

    public String getFraDato() {
        return fraDato;
    }

    public String getTilDato() {
        return tilDato;
    }

    public String getSted() {
        return sted;
    }

    public String getFil() {
        return fil;
    }

    public void setFil(String fil){
        this.fil = fil;
    }

    @Override
    public String toString(){
        return this.navn+"%"+this.fraDato+"%"+this.tilDato+"%"+this.sted;
    }
    
    
}
