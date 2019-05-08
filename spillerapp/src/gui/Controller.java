package gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import klasser.Brikke;
import klasser.BrikkeType;
import klasser.Posisjon;
import klasser.Sjakkbrett;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


/**
 * Controller: Spiller App
 * */
public class Controller implements Initializable {

    // * TAB: Finn parti
    @FXML private Tab tab_fp;
    @FXML private ComboBox<?> fp_kombo_turnering;
    @FXML private ListView<?> fp_liste_parti;
    @FXML private TextField fp_tekstfelt_spiller2;
    @FXML private TextField fp_tekstfelt_spiller1;
    @FXML private Button fp_knapp_søk_parti;
    @FXML private Button fp_knapp_velg_parti;

    // * TAB: Se parti
    @FXML private Tab tab_sp;
    @FXML private ListView<?> sp_liste_trekk;
    @FXML private AnchorPane sp_sjakkbrett;
    @FXML private Button sp_knapp_velg_trekk;

    // * TAB: Poengtabell
    @FXML private Tab tab_pt;
    @FXML private ListView<?> pt_poengtabell;
    @FXML private ChoiceBox<?> pt_kombo_turnering;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Sjakkbrett.populerSjakkbrett(sp_sjakkbrett);
        Sjakkbrett.populerSjakkBrikker(sp_sjakkbrett);

        boolean test = Sjakkbrett.validerTrekk(BrikkeType.KONGE_HVIT, Posisjon.E1, Posisjon.D2);

        System.out.println(test);

        // ! TEST
        /*Brikke brikke = Sjakkbrett.hentBrikke(sp_sjakkbrett, Posisjon.A1);
        brikke.setPosisjon(sp_sjakkbrett,Posisjon.C5);*/

    }

}
