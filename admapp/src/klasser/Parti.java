package klasser;

import java.io.Serializable;

public class Parti implements Serializable {

    private Spiller spillerHvit, spillerSort;
    private String dato, tid, fil;

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

    @Override
    public String toString() {
        return this.getSpillerHvit().getFornavn() + " " + this.getSpillerHvit().getEtternavn() +" vs " + this.getSpillerSort().getFornavn() + " " + this.getSpillerSort().getEtternavn();
    }
}
