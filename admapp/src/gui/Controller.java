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
    @FXML private ListView<?> rt_liste_turnering;
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
        opprettSpillere();
        setKomboSpillere();

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
        visTurneringer();



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
        turneringer.addAll(Fil.hentObjekt());
        System.out.println("Turneringer hentet!");
    }

    private void visTurneringer() {

            for(Turnering turnObject: turneringer) {
                String listeInfo = turnObject.getNavn() + "_" +turnObject.getFraDato() + "_" + turnObject.getTilDato()+ "_" +turnObject.getSted();
                t_liste_turnering.getItems().addAll(listeInfo);
            }
    }

    private void opprettSpillere() {
        

    }

    private void setKomboSpillere() {

        //p_kombo_turnering.getItems().addAll(turnListe);
        p_kombo_spiller_hvit.getItems().addAll(liste);
        //p_liste_parti.getItems().addAll(turnListe);
    }

    private void lagreTurnering() {

    }

    private void leggTilSpiller() {
        String fornavn = this.rt_tekstfelt_fornavn.getText();
        String etternavn = this.rt_tekstfelt_etternavn.getText();
        int poeng = 0;
        Spiller spiller = new Spiller(fornavn, etternavn, poeng);
        nyTurnering.leggTilSpiller(spiller);

        this.rt_tekstfelt_fornavn.setText("");
        this.rt_tekstfelt_etternavn.setText("");

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
