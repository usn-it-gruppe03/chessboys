package klasser;

import javafx.scene.CacheHint;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;


/**
 * Klasse: Brikke
 * */
public class Brikke extends ImageView {


    /**
     * Objektattributter
     * */
    private Posisjon posisjon;
    private BrikkeType brikkeType;
    private static final double BREDDE = 50.0, HØYDE = 50.0;

    private Brikke(){
        super();
    }

    private Brikke(Image ikon){
        this();
        this.setImage(ikon);
        this.setFitWidth(BREDDE);
        this.setFitHeight(HØYDE);
        this.setPreserveRatio(true);
    }

    private Brikke(Image ikon, double senterX, double senterY){
        this(ikon);
        this.setSenterX(senterX);
        this.setSenterY(senterY);
    }

    private Brikke(String ikonFilSti, double senterX, double senterY){
        this(new Image(ikonFilSti),senterX,senterY);
    }

    private Brikke(BrikkeType brikkeType, double senterX, double senterY){
        this(brikkeType.getIkonURL(), senterX, senterY);
    }

    public Brikke(BrikkeType brikkeType, AnchorPane sjakkbrett, Posisjon posisjon){
        this(
                brikkeType,
                Sjakkbrett.hentFelt(sjakkbrett, posisjon).getSenterX(),
                Sjakkbrett.hentFelt(sjakkbrett, posisjon).getSenterY()
        );
        this.posisjon = posisjon;
    }

    public double getSenterX(){
        return (this.getX() + (HØYDE/2.0));
    }

    public void setSenterX(double senterX){
        this.setX(senterX - (BREDDE/2.0));
    }

    public double getSenterY(){
        return (this.getY() + (HØYDE/2.0));
    }

    public void setSenterY(double senterY){
        this.setY(senterY - (HØYDE/2.0));
    }

    public Posisjon getPosisjon(){
        return this.posisjon;
    }

    public void setPosisjon(AnchorPane sjakkbrett, Posisjon posisjon){
        Felt felt = Sjakkbrett.hentFelt(sjakkbrett,posisjon);
        this.setSenterX(felt.getSenterX());
        this.setSenterY(felt.getSenterY());
    }

    public BrikkeType getBrikkeType(){
        return this.brikkeType;
    }

}
