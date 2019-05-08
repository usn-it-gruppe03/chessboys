package klasser;

import java.util.ArrayList;

public class Trekk {

    private ArrayList<Posisjon> pos;

    public Trekk(ArrayList<Posisjon> pos) {
        this.pos = pos;
    }

    public ArrayList<Posisjon> getPos() {
        return pos;
    }

    public void setPos(ArrayList<Posisjon> pos) {
        this.pos = pos;
    }
}
