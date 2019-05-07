package klasser;

import java.io.Serializable;

public class Spiller implements Serializable {

    private String fornavn, etternavn;
    private int poeng;

    public Spiller(String fornavn, String etternavn, int poeng) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.poeng = poeng;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public int getPoeng() {
        return poeng;
    }

    public void setPoeng(int poeng) {
        this.poeng = poeng;
    }
}

