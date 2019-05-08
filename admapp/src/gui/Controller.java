package gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import klasser.Fil;
import klasser.Parti;
import klasser.Spiller;
import klasser.Turnering;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.showMessageDialog;


/**
 * Controller: Admin App
 * */
public class Controller implements Initializable {

    private ArrayList<Parti> partier;
    private ArrayList<String> turnListe;
    private ArrayList<Turnering> turneringer = new ArrayList<>();
    private ObservableList<Spiller> spillere;
    private ArrayList<String> liste;
    private Turnering nyTurnering;
    private Turnering aktivTurnering;


    // * TAB: Turnering
    @FXML private Tab tab_t;
    @FXML private TextField t_tekstfelt_startdato;
    @FXML private TextField t_tekstfelt_sted;
    @FXML private ListView<String> t_liste_turnering;
    @FXML private Button t_knapp_velg_turnering;
    @FXML private Button t_knapp_lag_turnering;
    @FXML private TextField t_tekstfelt_sluttdato;
    @FXML private TextField t_tekstfelt_turneringsnavn;

    // * TAB: Legg til spiller
    @FXML private Tab tab_rt;
    @FXML private ListView<String> rt_liste_turnering;
    @FXML private Button rt_knapp_legg_til_deltaker;
    @FXML private TextField rt_tekstfelt_fornavn;
    @FXML private TextField rt_tekstfelt_etternavn;
    @FXML private TextField rt_tekstfelt_turneringsnavn;

    // * TAB: Parti
    @FXML private Tab tab_p;
    @FXML private Button p_knapp_velg_parti;
    @FXML private TextField p_tekstfelt_dato;
    @FXML private Button p_knapp_lag_parti;
    @FXML private TextField p_tekstfelt_klokkeslett;
    @FXML private ChoiceBox<String> p_kombo_turnering = new ChoiceBox<>();
    @FXML private ChoiceBox<String> p_kombo_spiller_sort = new ChoiceBox<>();
    @FXML private ChoiceBox<String> p_kombo_spiller_hvit = new ChoiceBox<>();
    @FXML private ListView<String> p_liste_parti;

    // * TAB: Rediger parti
    @FXML private Tab tab_rp;
    @FXML private TextField rp_tekstfelt_partinavn;
    @FXML private ListView<?> rp_liste_trekk;
    @FXML private Button rp_knapp_legg_til_trekk;
    @FXML private ChoiceBox<?> rp_tekstfelt_brikketype;
    @FXML private ChoiceBox<?> rp_tekstfelt_til_rute;
    @FXML private ChoiceBox<?> rp_tekstfelt_fra_rute;
    @FXML private ChoiceBox<?> rp_kombo_utfall;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hentTurneringern();
        visTurneringer();
        setKomboBokser();

        //System.out.println(turneringer.get(0));

    }


    public void lagMappe() {


        String tempNavn = t_tekstfelt_turneringsnavn.getText();
        String tempStartDato = t_tekstfelt_startdato.getText();
        String tempSluttDato = t_tekstfelt_sluttdato.getText();
        String tempSted = t_tekstfelt_sted.getText();
        String finalPath = "admapp/src/turneringer/"+tempNavn+tempStartDato+tempSluttDato+tempSted+"/";


        if(new File(finalPath).isDirectory()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasjonsmelding!");
            alert.setHeaderText("Feilmelding:");
            alert.setContentText("Turneringen du forsøker å opprette eksisterer allerede!");
            alert.showAndWait();
        }else{
            //Oppretter en resultat.txt fil i hver turneringsmappe
            try{
                new File(finalPath).mkdirs();
                File resultatFil = new File(finalPath+tempNavn+"RESULTATER.txt");
                resultatFil.createNewFile();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
            //Tømmer feltene for informasjon
            t_tekstfelt_turneringsnavn.clear();
            t_tekstfelt_startdato.clear();
            t_tekstfelt_sluttdato.clear();
            t_tekstfelt_sted.clear();

            nyTurnering = new Turnering(
                    tempNavn,
                    tempStartDato,
                    tempSluttDato,
                    tempSted);
            nyTurnering.setFil(finalPath);

            turneringer.add(nyTurnering);
            System.out.println(turneringer);
        }



        //Tømmer lista, og oppdaterer med nye verdier
        t_liste_turnering.getItems().clear();
        lagreInformasjon();
        visTurneringer();



    }

        //FIKS IFFEN
    public void velgTurnering(){

        //Henter stringen av objektet som er lagret i listview.
        String innString = ""+t_liste_turnering.getSelectionModel().getSelectedItems();

        //Stringen returnerer med "[TEST]" så vi må fjerne klammene
        //ved hjelp av regex
        String regex = "(?<=\\[{1})(.+)(?=\\]{1})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(innString);

        matcher.find();
        String valgtTurnering = matcher.group();

        //Splitter den nye stringen på skilletegnet "_" for å
        //plukke ut de individuelle verdiene.
        String [] splitTab = valgtTurnering.split("_");

        String tempNavn = splitTab[0];
        String tempFraDato = splitTab[1];
        String tempTilDato = splitTab[2];
        String tempSted = splitTab[3];

        String x = ""+tempNavn+"%"+tempFraDato+"%"+tempTilDato+"%"+tempSted;


        for(Turnering t : turneringer){
            if(t.toString().equals(x)){
                aktivTurnering = new Turnering(tempNavn, tempFraDato, tempTilDato, tempSted);
                System.out.println(aktivTurnering.toString());
                break;
            }
        }







    }

    /**
     *
     *  Lagrer arrayen med serialisering i en .dat fil
     *  Bruker en ArrayList<> med turneringsobjekter
     *
     * */

    public void lagreInformasjon() {
        Fil.leggTilObjekt(turneringer);
        System.out.println("LAGRET TIL .dat");
    }

    /**
     *
     * Henter turneringer fra en .dat fil, deserialiserer informasjonen
     * og legger de til i en ArrayList<>'
     *
     * */
    private void hentTurneringern() {

        if(Fil.hentObjekt() != null){
            turneringer.addAll(Fil.hentObjekt());
            System.out.println("Turneringer hentet!");
        }

    }

    private void visTurneringer() {

            for(Turnering turnObject: turneringer) {
                String listeInfo = turnObject.getNavn() + "_" +turnObject.getFraDato() + "_" + turnObject.getTilDato()+ "_" +turnObject.getSted();
                t_liste_turnering.getItems().addAll(listeInfo);
            }
    }
    /**
     *
     * Metode for å vise spillere i ListView
     *
     * */
    private void visSpillere() {

        rt_liste_turnering.getItems().clear();
        for(Spiller spillere: aktivTurnering.hentSpillerArray()) {
            rt_liste_turnering.getItems().add(spillere.getFornavn() + " " + spillere.getFornavn() + " | Poeng: " + spillere.getPoeng());
        }

    }

    /***
     *
     * Metode for å legge til Spiller-objekter i valgt Turnerings-objekt
     *
     */

    public void leggTilSpiller() {
        String fornavn = this.rt_tekstfelt_fornavn.getText();
        String etternavn = this.rt_tekstfelt_etternavn.getText();
        int poeng = 0;
        Spiller spiller = new Spiller(fornavn, etternavn, poeng);
        aktivTurnering.leggTilSpiller(spiller);

        visSpillere();

        this.rt_tekstfelt_fornavn.setText("");
        this.rt_tekstfelt_etternavn.setText("");

    }

    /**
     * Metode for å legge til spillere fra Turnerings-objektet inn i ChoiceBox
     * */

    private void setKomboBokser() {
       // for(Turnering )
        /*for(Spiller spiller: aktivTurnering.hentSpillerArray()) {

        }*/
    }

    private void lagParti(){
        String spillerHvit = p_kombo_spiller_hvit.getValue().toLowerCase();
        String spillerSort = p_kombo_spiller_sort.getValue().toLowerCase();
        Spiller spillerHvitObjekt;
        for(Turnering turnering: turneringer ) {
            for(Spiller spiller: turnering.hentSpillerArray()) {
                if(spiller.getFornavn()+spiller.getEtternavn() == spillerHvit){
                    spillerHvitObjekt = new Spiller(spiller.getFornavn(), spiller.getEtternavn(), spiller.getPoeng());
                } else if(spiller.getFornavn()+spiller.getEtternavn() == spillerSort ){

                }
            }
        }
        //Parti parti = new Parti(spillerHvitObjekt, );
    }


}
