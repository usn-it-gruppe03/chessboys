package klasser;

import java.io.Serializable;

public class Parti implements Serializable {

    private Spiller spillerHvit, spillerSort;
    private String dato, tid;

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
}
