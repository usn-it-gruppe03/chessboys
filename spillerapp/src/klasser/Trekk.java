package klasser;

import java.io.Serializable;

public class Trekk implements Serializable{

    private Posisjon fraTrekk, tilTrekk;
    private BrikkeType brikkeType;
    private String kvalitetsKode;



    public Trekk(Posisjon fraTrekk, Posisjon tilTrekk, BrikkeType brikkeType, String kvalitetsKode) {
        this.fraTrekk = fraTrekk;
        this.tilTrekk = tilTrekk;
        this.brikkeType = brikkeType;
        this.kvalitetsKode = kvalitetsKode;
    }

    public String getKvalitetsKode() {
        return kvalitetsKode;
    }

    public void setKvalitetsKode(String kvalitetsKode) {
        this.kvalitetsKode = kvalitetsKode;
    }

    public Posisjon getFraTrekk() {
        return fraTrekk;
    }

    public void setFraTrekk(Posisjon fraTrekk) {
        this.fraTrekk = fraTrekk;
    }

    public Posisjon getTilTrekk() {
        return tilTrekk;
    }

    public void setTilTrekk(Posisjon tilTrekk) {
        this.tilTrekk = tilTrekk;
    }

    public BrikkeType getBrikkeType() {
        return this.brikkeType;
    }

    public void setBrikkeType(BrikkeType brikkeType) {
        this.brikkeType = brikkeType;
    }

    @Override
    public String toString() {
        return "" + this.getFraTrekk() + " - " + this.getTilTrekk() + " - " + this.getKvalitetsKode();
    }
}
