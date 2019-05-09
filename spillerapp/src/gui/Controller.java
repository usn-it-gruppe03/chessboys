package gui;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import klasser.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


/**
 * Controller: Spiller App
 * */
public class Controller implements Initializable {

    // * Tab pane
    @FXML private TabPane tab_pane;

    // * Generelle attributter:
    private ArrayList<Turnering> turneringer = new ArrayList<>();
    private Parti valgtParti;
    private Animasjon animasjon;
    private boolean partierLastet;

    // * TAB: Finn parti
    @FXML private Tab tab_fp;
    @FXML private ComboBox<Turnering> fp_kombo_turnering;
    @FXML private ListView<Parti> fp_liste_parti;
    @FXML private TextField fp_tekstfelt_spiller2;
    @FXML private TextField fp_tekstfelt_spiller1;
    @FXML private Button fp_knapp_søk_parti;
    @FXML private Button fp_knapp_velg_parti;

    // * TAB: Se parti
    @FXML private Tab tab_sp;
    @FXML private ListView<Trekk> sp_liste_trekk;
    @FXML private AnchorPane sp_sjakkbrett;
    @FXML private Button sp_knapp_velg_trekk;
    @FXML private Button sp_knapp_forrige_trekk;
    @FXML private Button sp_knapp_spill_av_pause;
    @FXML private Button sp_knapp_neste_trekk;
    @FXML private ComboBox<Integer> sp_kombo_hastighet;

    // * TAB: Poengtabell
    @FXML private Tab tab_pt;
    @FXML private ListView<?> pt_poengtabell;
    @FXML private ChoiceBox<?> pt_kombo_turnering;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.sp_kombo_hastighet.getItems().addAll(1,2,3,4,5);
        this.sp_kombo_hastighet.getSelectionModel().select(0);

        Sjakkbrett.populerSjakkbrett(sp_sjakkbrett);
        Sjakkbrett.populerSjakkBrikker(sp_sjakkbrett);

        hentTurneringern();
        populerComboBox();

        this.animasjon = new Animasjon(this.sp_sjakkbrett, this.sp_liste_trekk, this.sp_kombo_hastighet);


        // ! TEST
        /*Brikke brikke = Sjakkbrett.hentBrikke(sp_sjakkbrett, Posisjon.A1);
        brikke.setPosisjon(sp_sjakkbrett,Posisjon.C5);*/

    }


    public void initierAnimasjon(){
        this.animasjon.initier();
    }

    public void spillAnimasjon(){
        this.animasjon.spillPause();
    }

    public void forrigeTrekk(){
        Sjakkbrett.resettBrett(this.sp_sjakkbrett);
    }




    /**
     * fp_knapp_velg_parti
     * */
    public void hentTrekk() {

        Parti parti = this.fp_liste_parti.getSelectionModel().getSelectedItem();

        if (this.partierLastet && parti != null){
            //System.out.println("Knappen funker");
            valgtParti = fp_liste_parti.getSelectionModel().getSelectedItem();
            //System.out.println(valgtParti);

            for(Trekk t: valgtParti.getTrekkListe()) {
                sp_liste_trekk.getItems().add(t);
            }

            tab_pane.getSelectionModel().select(tab_sp);

            this.initierAnimasjon();

        } else {

            Sjakkbrett.visFeil("Parti ikke valgt", "Du har ikke valgt et parti", "Du må velge et jævla parti din idiot!");

        }

    }

    /**
     *
     * */
    public void populerListView() {
        Turnering valgtTurnering;
        valgtTurnering = fp_kombo_turnering.getSelectionModel().getSelectedItem();
        valgtTurnering.hentParti();
        for (Parti p: valgtTurnering.hentParti()) {
            fp_liste_parti.getItems().add(p);
        }
        this.partierLastet = true;
        this.fp_knapp_velg_parti.setDisable(false);
    }

    /**
     *
     * */
    public void søkSpiller() {
        Turnering valgtTurnering;
        valgtTurnering = fp_kombo_turnering.getSelectionModel().getSelectedItem();
        String spiller1 = fp_tekstfelt_spiller1.getText();
        String spiller2 = fp_tekstfelt_spiller2.getText();
        fp_liste_parti.getItems().clear();
        System.out.println();

        for (Parti p: valgtTurnering.hentParti()) {
            if (p.toString().contains(spiller1) && p.toString().contains(spiller2)) {
                fp_liste_parti.getItems().add(p);
            }
        }
    }

    /**
     *
     * */
    private void hentTurneringern() {
        if(Fil.hentObjekt() != null){
            System.out.println("hent");
            turneringer.addAll(Fil.hentObjekt());
            System.out.println("Turneringer hentet!");
        }
    }

    /**
     *
     * */
    private void populerComboBox() {
        for(Turnering t: turneringer) {
            fp_kombo_turnering.getItems().addAll(t);
        }
    }

}
