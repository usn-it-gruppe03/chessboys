package klasser;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Brikke extends ImageView {

    private static double BREDDE = 50.0, HØYDE = 50.0;

    private Brikke(){
        super();
    }

    public Brikke(Image ikon){
        this();
        this.setImage(ikon);
        this.setFitWidth(BREDDE);
        this.setFitHeight(HØYDE);
        this.setPreserveRatio(true);
    }

    public Brikke(Image ikon, double senterX, double senterY){
        this(ikon);
        this.setSenterX(senterX);
        this.setSenterY(senterY);
    }

    public Brikke(String ikonFilSti, double senterX, double senterY){
        this(new Image(ikonFilSti),senterX,senterY);
    }

    private double getSenterX(){
        return this.getX() + BREDDE/2;
    }

    private void setSenterX(double senterX){
        this.setX(senterX - BREDDE/2);
    }

    private double getSenterY(){
        return this.getY() + HØYDE/2;
    }

    private void setSenterY(double senterY){
        this.setY(senterY - HØYDE/2);
    }

}
