package klasser;

import javafx.scene.layout.Pane;


/**
 * Klasse: Sjakkbrett
 * */
public class Sjakkbrett {


    /**
     * Klasseattributter
     * */
    public static final int ANTALL_RUTER = 64;
    public static final int ANTALL_RUTER_BREDDE = 8;
    public static final int ANTALL_RUTER_HØYDE = 8;




    /**
     * Get Brikkebredde.
     * */
    public static double getBrikkeBredde(Pane pane){
        return pane.getWidth() / ANTALL_RUTER_BREDDE;
    }




    /**
     * Get Brikkehøyde.
     * */
    public static double getBrikkeHøyde(Pane pane){
        return pane.getHeight() / ANTALL_RUTER_HØYDE;
    }

}
