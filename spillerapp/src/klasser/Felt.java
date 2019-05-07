package klasser;

import javafx.scene.shape.Rectangle;


/**
 * Klasse: Felt
 * */
public class Felt extends Rectangle {

    Posisjon posisjon;

    /**
     * Konstruktør
     * */
    public Felt(Posisjon posisjon, double x, double y, double bredde, double høyde){
        super();
        this.posisjon = posisjon;
        this.setX(x);
        this.setY(y);
        this.setWidth(bredde);
        this.setHeight(høyde);
    }

    public getSenterX(){

    }

    public setSenterX(double x){

    }

}
