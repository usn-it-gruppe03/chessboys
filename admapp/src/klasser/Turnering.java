/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasser;

/**
 *
 * @author andersbaero
 */
public class Turnering {
    private final String navn;
    private final String fraDato;
    private final String tilDato;
    private final String sted;
    private final String fil;

    public Turnering(String navn, String fraDato, String tilDato, String sted) {
        this.navn = navn;
        this.fraDato = fraDato;
        this.tilDato = tilDato;
        this.sted = sted;
        this.fil = "turneringer/"+navn;
        //Eks /turneringer/bøsjakkmesterskap
    }
    
    
    //lag mappe
    //new File("/path/directory").mkdirs();
    //sjekk om mappe allerede eksisterer

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
