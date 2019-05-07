/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasser;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
        if(!mappeFinnes(CONST_PATH+navn)){
            this.navn = navn;
            this.fraDato = fraDato;
            this.tilDato = tilDato;
            this.sted = sted;
            this.fil = CONST_PATH+navn;
            
            System.out.println(""+fil+"/");
            
            lagMappe();
            
            //Oppretter en resultat.txt fil i hver turneringsmappe
            try{
                File resultatFil = new File(this.getFil()+this.navn+"RESULTATER.txt");
                resultatFil.createNewFile();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informasjonsmelding!");
            alert.setHeaderText("Feilmelding:");
            alert.setContentText("Turneringen du forsøker å opprette eksisterer allerede!");

            alert.showAndWait();
        }
        
        //skrive til info.txt
    }
    
    public void leggTilTspiller(Spiller s){
        spillerListe.add(s);
    }
    
    public void leggTilParti(Parti p){
        partiListe.add(p);
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
    
    
}
