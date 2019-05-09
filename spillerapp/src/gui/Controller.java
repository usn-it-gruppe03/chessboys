package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import klasser.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
    private boolean partierLastet, turneringValgt;

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
    @FXML private ListView<String> pt_poengtabell;
    @FXML private ChoiceBox<Turnering> pt_kombo_turnering;

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
        this.animasjon.forrigeTrekk();
    }

    public void nesteTrekk(){
        this.animasjon.nesteTrekk();
    }

    public void velgTrekk(){
        this.animasjon.velgTrekk();
    }

    public void setHastighet(){
        this.animasjon.setHastighet();
    }




    /**
     *  hentTrekk metoden sjekker om bruker henter parti fra ListView.
     *  Populerer sp_liste_trekk med Trekk objekter
     * */
    public void hentTrekk() {

        Parti parti = this.fp_liste_parti.getSelectionModel().getSelectedItem();

        if (this.partierLastet && parti != null){

            valgtParti = fp_liste_parti.getSelectionModel().getSelectedItem();

            for(Trekk t: valgtParti.getTrekkListe()) {
                sp_liste_trekk.getItems().add(t);
            }

            tab_pane.getSelectionModel().select(tab_sp);

            this.initierAnimasjon();

        } else {

            Sjakkbrett.visFeil("Parti ikke valgt", "Du har ikke valgt et parti", "Vennligst velg et parti!");

        }

        this.sp_knapp_forrige_trekk.setDisable(false);
        this.sp_knapp_spill_av_pause.setDisable(false);
        this.sp_knapp_neste_trekk.setDisable(false);
        this.sp_kombo_hastighet.setDisable(false);
        this.sp_knapp_velg_trekk.setDisable(false);

    }

    /**
     *  Populerer ListView med Parti objekter.
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
        this.fp_knapp_søk_parti.setDisable(false);
    }

    /**
     *
     * */
    public void søkSpiller() {
        Turnering valgtTurnering;
        valgtTurnering = fp_kombo_turnering.getSelectionModel().getSelectedItem();
        String spiller1 = fp_tekstfelt_spiller1.getText().toLowerCase();
        String spiller2 = fp_tekstfelt_spiller2.getText().toLowerCase();
        fp_liste_parti.getItems().clear();
        System.out.println();

        for (Parti p: valgtTurnering.hentParti()) {
            if (p.toString().toLowerCase().contains(spiller1) && p.toString().toLowerCase().contains(spiller2)) {
                fp_liste_parti.getItems().add(p);
            }
        }
    }

    /**
     *
     * */
    private void hentTurneringern() {
        if(Fil.hentObjekt() != null){
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
            pt_kombo_turnering.getItems().addAll(t);
        }
    }

    public void visResultat() {
        for (Turnering t: turneringer) {
            if(t.toString().equals(pt_kombo_turnering.getValue().toString())) {
                Collections.sort(t.hentSpillerArray());
                int index = 1;
                for (Spiller s: t.hentSpillerArray()) {
                    pt_poengtabell.getItems().add((index++) + ". "+ s.getFornavn() + " " + s.getEtternavn() + " | Poeng: " + s.getPoeng());
                }
            }
        }
    }

}
