package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


/**
 * Controller: Spiller App
 * */
public class Controller {

    // * TAB: Finn parti
    @FXML private Tab tab_fp;
    @FXML private ListView<?> fp_liste_parti;
    @FXML private ComboBox<?> fp_kombo_turnering;
    @FXML private TextField fp_tekstfelt_spiller2;
    @FXML private TextField fp_tekstfelt_spiller1;
    @FXML private Button fp_knapp_søk_parti;
    @FXML private Button fp_knapp_velg_parti;

    // * TAB: Se parti
    @FXML private Tab tab_sp;
    @FXML private ListView<?> sp_liste_trekk;
    @FXML private Pane sp_sjakkbrett;
    @FXML private Button sp_knapp_velg_trekk;

    // * TAB: Poengtabell
    @FXML private Tab tab_pt;
    @FXML private ListView<?> pt_poengtabell;
    @FXML private ChoiceBox<?> pt_kombo_turnering;

}
