package gui;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import klasser.Fil;
import klasser.Parti;
import klasser.Turnering;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * Controller: Admin App
 * */
public class Controller implements Initializable {

    private ArrayList<Turnering> turneringer;
    private ArrayList<Parti> partier;

    // * TAB: Turnering
    @FXML private Tab tab_t;
    @FXML private TextField t_tekstfelt_startdato;
    @FXML private TextField t_tekstfelt_sted;
    @FXML private ListView<String> t_liste_turnering;
    @FXML private Button t_knapp_velg_turnering;
    @FXML private Button t_knapp_lag_turnering;
    @FXML private TextField t_tekstfelt_sluttdato;
    @FXML private TextField t_tekstfelt_turneringsnavn;

    // * TAB: Rediger turnering
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
    @FXML private ChoiceBox<?> p_kombo_spiller_sort;
    @FXML private Button p_knapp_lag_parti;
    @FXML private TextField p_tekstfelt_klokkeslett;
    @FXML private ChoiceBox<?> p_kombo_turnering;
    @FXML private ChoiceBox<?> p_kombo_spiller_hvit;
    @FXML private ListView<?> p_liste_parti;

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

        visTurneringer();

    }
    
    
    public void opprettTurnering(){
        String tempNavn = t_tekstfelt_turneringsnavn.getText();
        String tempStartDato = t_tekstfelt_startdato.getText();
        String tempSluttDato = t_tekstfelt_sluttdato.getText();
        String tempSted = t_tekstfelt_sted.getText();
                
        Turnering nyTurn = new Turnering(
            tempNavn, 
            tempStartDato, 
            tempSluttDato, 
            tempSted);
        
        //Tømmer feltene for informasjon
        t_tekstfelt_turneringsnavn.clear(); 
        t_tekstfelt_startdato.clear(); 
        t_tekstfelt_sluttdato.clear(); 
        t_tekstfelt_sted.clear();
        
        //Tømmer lista, og oppdaterer med nye verdier
        t_liste_turnering.getItems().clear();
        visTurneringer();
        
    }

    //Populer listView t_liste_turnering
    public void visTurneringer() {
        Fil test = new Fil();
        ArrayList<String> liste = new ArrayList<>();
        String [] lol = test.søkTurneringer();
        for (int i = 0; i<lol.length; i++){
            liste.add(lol[i]);
        }
        t_liste_turnering.getItems().addAll(liste);
    }
}
