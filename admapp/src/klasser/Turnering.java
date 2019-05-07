/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasser;

import java.io.File;
import java.io.Serializable;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author andersbaero
 */
public class Turnering implements Serializable{
    private final String navn;
    private final String fraDato;
    private final String tilDato;
    private final String sted;
    private final String fil;

    public Turnering(String navn, String fraDato, String tilDato, String sted){
        if(!mappeFinnes("/turneringer"+navn)){
            this.navn = navn;
            this.fraDato = fraDato;
            this.tilDato = tilDato;
            this.sted = sted;
            this.fil = "/turneringer/"+navn;
            lagMappe();
        }else{
            showMessageDialog(null, "Turnering eksisterer allerede!");
            throw new IllegalArgumentException("Turnering eksiterer allerede!");
        }
    }
    
    
    public void lagMappe(){    
        new File(this.fil).mkdirs();
        
    }
    
    public boolean mappeFinnes(String innFil){
        if(new File(innFil).exists()){
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
