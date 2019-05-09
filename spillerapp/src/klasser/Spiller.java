package klasser;

import java.io.Serializable;

public class Spiller implements Serializable, Comparable<Spiller> {

    private String fornavn, etternavn;
    private double poeng;

    public Spiller(String fornavn, String etternavn, double poeng) {
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

    public double getPoeng() {
        return poeng;
    }

    public void setPoeng(double poeng) {
        this.poeng += poeng;
    }

    @Override
    public String toString() {
        return this.getFornavn() + " " + this.getEtternavn();
    }


    @Override
    public int compareTo(Spiller s) {
        if(this.poeng == s.poeng) {
            return 0;
        } else if(this.poeng>s.poeng) {
            return -1;
        } else {
            return 1;
        }
    }
}

