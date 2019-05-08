package klasser;

import java.io.Serializable;

public class Trekk implements Serializable{

    private Posisjon fraTrekk, tilTrekk;
    private BrikkeType brikkeType;

    public Trekk(Posisjon fraTrekk, Posisjon tilTrekk, BrikkeType brikkeType) {
        this.fraTrekk = fraTrekk;
        this.tilTrekk = tilTrekk;
        this.brikkeType = brikkeType;
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
        return brikkeType;
    }

    public void setBrikkeType(BrikkeType brikkeType) {
        this.brikkeType = brikkeType;
    }

    @Override
    public String toString() {
        return "" + this.getFraTrekk() + " - " + this.getTilTrekk();
    }
}
