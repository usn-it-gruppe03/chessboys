package klasser;

import javafx.scene.layout.Pane;

import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


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
    public static double getFeltBredde(Pane pane){
        return pane.getWidth() / ANTALL_RUTER_BREDDE;
    }




    /**
     * Get Brikkehøyde.
     * */
    public static double getFeltHøyde(Pane pane){
        return pane.getHeight() / ANTALL_RUTER_HØYDE;
    }




    /**
     * Populer sjakkbrett i pane
     * */
    public static void populerSjakkbrett(Pane pane){

        ArrayList<ArrayList<Posisjon>> posisjoner = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(
                        Posisjon.A8,Posisjon.B8,Posisjon.C8,Posisjon.D8,Posisjon.E8,Posisjon.F8,Posisjon.G8,Posisjon.H8
                )),
                new ArrayList<>(Arrays.asList(
                        Posisjon.A7,Posisjon.B7,Posisjon.C7,Posisjon.D7,Posisjon.E7,Posisjon.F7,Posisjon.G7,Posisjon.H7
                )),
                new ArrayList<>(Arrays.asList(
                        Posisjon.A6,Posisjon.B6,Posisjon.C6,Posisjon.D6,Posisjon.E6,Posisjon.F6,Posisjon.G6,Posisjon.H6
                )),
                new ArrayList<>(Arrays.asList(
                        Posisjon.A5,Posisjon.B5,Posisjon.C5,Posisjon.D5,Posisjon.E5,Posisjon.F5,Posisjon.G5,Posisjon.H5
                )),
                new ArrayList<>(Arrays.asList(
                        Posisjon.A4,Posisjon.B4,Posisjon.C4,Posisjon.D4,Posisjon.E4,Posisjon.F4,Posisjon.G4,Posisjon.H4
                )),
                new ArrayList<>(Arrays.asList(
                        Posisjon.A3,Posisjon.B3,Posisjon.C3,Posisjon.D3,Posisjon.E3,Posisjon.F3,Posisjon.G3,Posisjon.H3
                )),
                new ArrayList<>(Arrays.asList(
                        Posisjon.A2,Posisjon.B2,Posisjon.C2,Posisjon.D2,Posisjon.E2,Posisjon.F2,Posisjon.G2,Posisjon.H2
                )),
                new ArrayList<>(Arrays.asList(
                        Posisjon.A1,Posisjon.B1,Posisjon.C1,Posisjon.D1,Posisjon.E1,Posisjon.F1,Posisjon.G1,Posisjon.H1
                ))
        ));

        Farge startFarge = Farge.SORT, feltFarge = Farge.SORT;
        for (int i=0; i<ANTALL_RUTER_HØYDE; i++){

            for (int j=0; j<ANTALL_RUTER_BREDDE; j++){

                // * Definer korresponderende CSS-klasse til feltfargen.
                String fargeCSS = (feltFarge == Farge.SORT) ? "bg-black" : "bg-white";

                // * Kalkuler nye koordinater for hver iterasjon:
                double x = getFeltBredde(pane) * j;
                double y = getFeltHøyde(pane) * i;

                // * Instansier et nytt felt:
                Felt felt = new Felt(posisjoner.get(i).get(j),x,y,getFeltBredde(pane),getFeltHøyde(pane));
                felt.getStyleClass().add(fargeCSS);
                felt.setStyle("-fx-background-color: red");

                // * Legg til node i pane.
                pane.getChildren().add(felt);

                // * Oppdater farge. Velg motsatt:
                feltFarge = (feltFarge == Farge.SORT) ? Farge.HVIT : Farge.SORT;

            }

            // * Bytt startfarge og feltfarge for hver linje.
            startFarge = (startFarge == Farge.SORT) ? Farge.HVIT : Farge.SORT;
            feltFarge = (startFarge == Farge.SORT) ? Farge.HVIT : Farge.SORT;

        }

    }

}
