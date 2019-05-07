package klasser;

import javafx.scene.shape.Rectangle;


/**
 * Klasse: Felt
 * */
public class Felt extends Rectangle {


    /**
     * Objektattributter
     * */
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




    /**
     * Get Delta X
     * @return double
     * */
    private double getDeltaX(){
        return this.getBoundsInParent().getMinX() - this.getBoundsInParent().getMaxX();
    }




    /**
     * Get Delta Y
     * @return double
     * */
    private double getDeltaY(){
        return this.getBoundsInParent().getMinY() - this.getBoundsInParent().getMaxY();
    }




    /**
     * Get Senter X
     * @return double
     * */
    public double getSenterX(){
        return this.getX() + (this.getDeltaX()/2);
    }




    /**
     * Set Senter X
     * @param x X-koordinat for sentrum av node.
     * */
    public void setSenterX(double x){
        this.setX( x - (this.getDeltaX()/2) );
    }



    public double getSenterY(){
        return this.getY() + (this.getDeltaY()/2);
    }




    /**
     * Set Senter Y
     * @param y Y-koordinat for sentrum av node.
     * */
    public void setSenterY(double y){
        this.setY( y - (this.getDeltaY()/2) );
    }




    /**
     * Get Posisjon
     * @return posisjon
     * */
    public Posisjon getPosisjon() {
        return posisjon;
    }

}
