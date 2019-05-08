package klasser;

import java.io.Serializable;
import java.util.ArrayList;

public class Parti implements Serializable {

    private Spiller spillerHvit, spillerSort;
    private String dato, tid, fil;
    private ArrayList<Trekk> trekkListe;

    public Parti(Spiller spillerHvit, Spiller spillerSort, String dato, String tid) {
        this.spillerHvit = spillerHvit;
        this.spillerSort = spillerSort;
        this.dato = dato;
        this.tid = tid;
    }

    public Spiller getSpillerHvit() {
        return spillerHvit;
    }

    public void setSpillerHvit(Spiller spillerHvit) {
        this.spillerHvit = spillerHvit;
    }

    public Spiller getSpillerSort() {
        return spillerSort;
    }

    public void setSpillerSort(Spiller spillerSort) {
        this.spillerSort = spillerSort;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String fulltNavnHvit() {
        String fulltNavn = this.spillerHvit.getFornavn() + " " + this.getSpillerHvit().getEtternavn();
        return fulltNavn;
    }
    public String fulltNavnSort() {
        String fulltNavn = this.spillerSort.getFornavn() + " " + this.getSpillerSort().getEtternavn();
        return fulltNavn;

    }

    public String getFil() {
        String filSti = this.fulltNavnHvit()+this.fulltNavnSort()+".dat";
        return filSti;
    }

    public ArrayList<Trekk> getTrekkListe() {
        return trekkListe;
    }

    public void setTrekkListe(ArrayList<Trekk> trekkListe) {
        this.trekkListe = trekkListe;
    }

    public void setTrekk(Trekk t) {
        this.trekkListe.add(t);
    }

    @Override
    public String toString() {
        return this.fulltNavnHvit() +" vs " + this.fulltNavnSort() + " KL: " + this.getTid() + " Dato: " + this.getDato();
    }
}
