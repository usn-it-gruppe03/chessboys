package klasser;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


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
        return pane.getPrefWidth() / ANTALL_RUTER_BREDDE;
    }




    /**
     * Get Brikkehøyde.
     * */
    public static double getFeltHøyde(Pane pane){
        return pane.getPrefHeight() / ANTALL_RUTER_HØYDE;
    }




    /**
     * Populer sjakkbrett i pane
     * */
    public static void populerSjakkbrett(AnchorPane pane){


        // * Posisjoner:
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


        // * Itererer gjennom rader:
        for (int i=0; i<ANTALL_RUTER_HØYDE; i++){

            // * Itererer gjennom kolonner:
            for (int j=0; j<ANTALL_RUTER_BREDDE; j++){

                // * Kontroller farge:
                boolean erHvit = (i%2) == (j%2);

                // * Kalkuler nye koordinater for hver iterasjon:
                double x = getFeltBredde(pane) * j;
                double y = getFeltHøyde(pane) * i;

                // * Instansier et nytt felt:
                Felt felt = new Felt(posisjoner.get(i).get(j),x,y,getFeltBredde(pane),getFeltHøyde(pane));

                if (erHvit){
                    felt.getStyleClass().add("bg-hvit");
                } else {
                    felt.getStyleClass().add("bg-sort");
                }

                // * Legg til node i pane.
                pane.getChildren().add(felt);

            }

        }

        System.out.println("Feltene på sjakkbrettet er distribuert på sjakkbrettet.");

    }




    /**
     * Populer sjakkbrikker.
     * */
    public static void populerSjakkBrikker(AnchorPane pane){

        Map<BrikkeType,ArrayList<Posisjon>> brikkePosisjon = new HashMap<BrikkeType,ArrayList<Posisjon>>(){{

            // * Sorte brikker:
            put(BrikkeType.TÅRN_SORT, new ArrayList<>(Arrays.asList(
                    Posisjon.A8, Posisjon.H8
            )));

            put(BrikkeType.SPRINGER_SORT, new ArrayList<>(Arrays.asList(
                    Posisjon.B8, Posisjon.G8
            )));

            put(BrikkeType.LØPER_SORT, new ArrayList<>(Arrays.asList(
                    Posisjon.C8, Posisjon.F8
            )));

            put(BrikkeType.DRONNING_SORT, new ArrayList<>(Arrays.asList(
                    Posisjon.D8
            )));

            put(BrikkeType.KONGE_SORT, new ArrayList<>(Arrays.asList(
                    Posisjon.E8
            )));

            put(BrikkeType.BONDE_SORT, new ArrayList<>(Arrays.asList(
                    Posisjon.A7,Posisjon.B7,Posisjon.C7,Posisjon.D7,Posisjon.E7,Posisjon.F7,Posisjon.G7,Posisjon.H7
            )));

            // * Hvite brikker
            put(BrikkeType.TÅRN_HVIT, new ArrayList<>(Arrays.asList(
                    Posisjon.A1, Posisjon.H1
            )));

            put(BrikkeType.SPRINGER_HVIT, new ArrayList<>(Arrays.asList(
                    Posisjon.B1, Posisjon.G1
            )));

            put(BrikkeType.LØPER_HVIT, new ArrayList<>(Arrays.asList(
                    Posisjon.C1, Posisjon.F1
            )));

            put(BrikkeType.DRONNING_HVIT, new ArrayList<>(Arrays.asList(
                    Posisjon.D1
            )));

            put(BrikkeType.KONGE_HVIT, new ArrayList<>(Arrays.asList(
                    Posisjon.E1
            )));

            put(BrikkeType.BONDE_HVIT, new ArrayList<>(Arrays.asList(
                    Posisjon.A2,Posisjon.B2,Posisjon.C2,Posisjon.D2,Posisjon.E2,Posisjon.F2,Posisjon.G2,Posisjon.H2
            )));

        }};

        for (Map.Entry<BrikkeType,ArrayList<Posisjon>> entry : brikkePosisjon.entrySet()){

            BrikkeType brikkeType = entry.getKey();
            ArrayList<Posisjon> posisjoner = entry.getValue();

            for (int i=0; i<posisjoner.size(); i++){

                Felt felt = hentFelt(pane, posisjoner.get(i));
                Brikke brikke = new Brikke(brikkeType,pane,felt.getPosisjon());
                pane.getChildren().add(brikke);

            }

        }

        System.out.println("Sjakkbrikker er distribuert på sjakkbrettet.");

    }




    /**
     * Fra String til Posisjon
     * */
    public static Posisjon tilPosisjon(String posisjon){

        for (Posisjon p : Posisjon.values()){
            if (p.toString().equals(posisjon.toUpperCase()))
                return p;
        }

        return null;

    }




    /**
     * Hent Felt
     * */
    public static Felt hentFelt(AnchorPane sjakkbrett, Posisjon posisjon){

        // Iterer gjennom sjakkbrettets noder.
        for (Node node : sjakkbrett.getChildren()){

            // ? Dersom noden er av typen Felt.
            if (node instanceof Felt){

                // Typetving noden til Felt.
                Felt felt = ((Felt) node);

                // ? Dersom feltets posisjon er lik den gitte posisjon.
                if (felt.getPosisjon() == posisjon)

                    // Returner feltet.
                    return felt;
            }
        }

        return null;

    }




    /**
     * Hent Felt
     * */
    public static Felt hentFelt(AnchorPane sjakkbrett, String posisjon){
        return hentFelt(sjakkbrett, tilPosisjon(posisjon));
    }




    /**
     * Hent brikke
     * */
    public static Brikke hentBrikke(AnchorPane sjakkbrett, Posisjon posisjon){

        // Iterer gjennom sjakkbrettets noder.
        for (Node node : sjakkbrett.getChildren()){

            // ? Dersom noden er av typen Felt.
            if (node instanceof Brikke){

                // Typetving noden til Felt.
                Brikke brikke = ((Brikke) node);

                // ? Dersom feltets posisjon er lik den gitte posisjon.
                if (brikke.getPosisjon() == posisjon)

                    // Returner feltet.
                    return brikke;
            }
        }

        return null;

    }

}
