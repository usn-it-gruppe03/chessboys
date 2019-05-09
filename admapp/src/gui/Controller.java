package gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import klasser.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Controller: Admin App
 * */
public class Controller implements Initializable {

    private ArrayList<Turnering> turneringer = new ArrayList<>();
    private ObservableList<Spiller> spillere;
    private ArrayList<String> liste;
    private Turnering nyTurnering;
    private Turnering aktivTurnering;
    private Parti valgtParti;

    // * TAB PANE:
    @FXML private TabPane tab_pane;

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
    @FXML private ComboBox<Turnering> p_kombo_turnering = new ComboBox<>();
    @FXML private ComboBox<Spiller> p_kombo_spiller_sort = new ComboBox<>();
    @FXML private ComboBox<Spiller> p_kombo_spiller_hvit = new ComboBox<>();
    @FXML private ListView<Parti> p_liste_parti;

    // * TAB: Rediger parti
    @FXML private Tab tab_rp;
    @FXML private TextField rp_tekstfelt_partinavn;
    @FXML private ListView<Trekk> rp_liste_trekk;
    @FXML private Button rp_knapp_legg_til_trekk;
    @FXML private ComboBox<BrikkeType> rp_tekstfelt_brikketype;
    @FXML private ComboBox<Posisjon> rp_tekstfelt_til_rute;
    @FXML private ComboBox<Posisjon> rp_tekstfelt_fra_rute;
    @FXML private ComboBox<Spiller> rp_kombo_utfall;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hentTurneringern();
        visTurneringer();
        setTurneringKomboBox();
    }


    public void lagMappe() {
        String tempNavn = t_tekstfelt_turneringsnavn.getText();
        String tempStartDato = t_tekstfelt_startdato.getText();
        String tempSluttDato = t_tekstfelt_sluttdato.getText();
        String tempSted = t_tekstfelt_sted.getText();
        String finalPath = ""+tempNavn+tempStartDato+tempSluttDato+tempSted;

        boolean eksisterer = false;

        for(Turnering t : turneringer){
            if(t.toString().equals(finalPath)){
                eksisterer = true;
            }
        }

        if(eksisterer){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasjonsmelding!");
            alert.setHeaderText("Feilmelding:");
            alert.setContentText("Turneringen du forsøker å opprette eksisterer allerede!");
            alert.showAndWait();
        }else{

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
        }



        //Tømmer lista, og oppdaterer med nye verdier
        t_liste_turnering.getItems().clear();
        lagreInformasjon();
        setTurneringKomboBox();
        visTurneringer();



    }



    public void velgTurnKnapp() {
        velgTurnering();
        tab_pane.getSelectionModel().select(tab_rt);
        rt_tekstfelt_turneringsnavn.setText(aktivTurnering.getNavn());
        visSpillere();
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

        String x = ""+tempNavn+tempFraDato+tempTilDato+tempSted;

        for(Turnering t : turneringer) {
            if (t.toString().equals(x)) {
                aktivTurnering = new Turnering(tempNavn, tempFraDato, tempTilDato, tempSted);
                aktivTurnering.setSpillerArray(t.hentSpillerArray());
                aktivTurnering.setPartiArray(t.hentParti());
                aktivTurnering.setFil("turnering/" + x);
                System.out.println(aktivTurnering.toString());
                break;
            }
        }

        rt_knapp_legg_til_deltaker.setDisable(false);
    }

    /**
     *
     *  Lagrer arrayen med serialisering i en .dat fil
     *  Bruker en ArrayList<> med turneringsobjekter
     *
     * */

    public void lagreInformasjon() {

        if(aktivTurnering != null) {
            for (Turnering t: turneringer) {
                if(t.toString().equals(aktivTurnering.toString())) {
                    t.setSpillerArray(aktivTurnering.hentSpillerArray());
                    t.setPartiArray(aktivTurnering.hentParti());
                }
            }
        }
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
        for(Turnering t: turneringer) {
            if(t.toString().equals(aktivTurnering.toString())) {
                for(Spiller spillere: t.hentSpillerArray()) {
                    rt_liste_turnering.getItems().add(spillere.getFornavn() + " " + spillere.getEtternavn()+ " | Poeng: " + spillere.getPoeng());
                }
            }
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

        for(Turnering t: turneringer) {
            if(aktivTurnering.toString().equals(t.toString())){
                t.leggTilSpiller(spiller);
            }
        }
        System.out.println(aktivTurnering.hentSpillerArray());
        lagreInformasjon();

        visSpillere();

        this.rt_tekstfelt_fornavn.setText("");
        this.rt_tekstfelt_etternavn.setText("");

    }

    /**
     *
     * Metode for å legge til spillere fra Turnerings-objektet inn i ChoiceBox
     *
     * */

    private void setTurneringKomboBox() {

        for (Turnering t: turneringer) {
            p_kombo_turnering.getItems().add(t);
        }

    }

    public void setSpillerKomboBox() {

        p_kombo_spiller_hvit.getItems().clear();
        p_kombo_spiller_sort.getItems().clear();

        for(Turnering t: turneringer) {
            if(t.toString().equals(p_kombo_turnering.getValue().toString())) {

                aktivTurnering = new Turnering(p_kombo_turnering.getValue().getNavn(), p_kombo_turnering.getValue().getFraDato(), p_kombo_turnering.getValue().getTilDato(), p_kombo_turnering.getValue().getSted());
                aktivTurnering.setSpillerArray(t.hentSpillerArray());
                aktivTurnering.setPartiArray(t.hentParti());
                aktivTurnering.setFil("turneringer/" + t.toString() + "/");
                visParti();
                for(Spiller s: t.hentSpillerArray()) {

                    p_kombo_spiller_sort.getItems().add(s);
                    p_kombo_spiller_hvit.getItems().add(s);
                }
            }
        }
    }

    public void lagParti(){

        if(p_kombo_spiller_hvit.getValue().equals(p_kombo_spiller_sort.getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Partifeil");
            alert.setHeaderText("Ops! Noe gikk galt");
            alert.setContentText("Du har satt " + p_kombo_spiller_hvit.getValue().getFornavn() + " til å spille mot seg selv!");
            alert.showAndWait();
        } else {
            if(!p_tekstfelt_dato.getText().isEmpty() && !p_tekstfelt_klokkeslett.getText().isEmpty()) {
                Parti p = new Parti(p_kombo_spiller_hvit.getValue(), p_kombo_spiller_sort.getValue(),p_tekstfelt_dato.getText(),p_tekstfelt_klokkeslett.getText());
                for(Turnering t: turneringer) {
                    if(t.toString().equals(aktivTurnering.toString())) {
                        if(t.hentParti().isEmpty()) {
                            System.out.println("array tom");
                            t.leggTilParti(p);

                        }else {
                            for (int i = 0; i<t.hentParti().size(); i++) {
                                Parti parti = t.hentParti().get(i);
                                if(parti.toString().equals(p.toString())) {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("Partifeil");
                                    alert.setHeaderText("Ops! Noe gikk galt");
                                    alert.setContentText("Partiet eksisterer allerede!");
                                    alert.showAndWait();
                                    break;
                                } else {
                                    System.out.println("Parti lagt til!");
                                    System.out.println(p.toString());
                                    t.leggTilParti(p);
                                    break;
                                }
                            }
                        }
                    }

                }
                lagreInformasjon();
                visParti();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advarsel!");
                alert.setHeaderText("Du har ikke fylt ut alle felt!");
                alert.setContentText("Alle felt i skjemaet er ikke fylt ut, vennligst fyll alle felt!");
            }

        }


    }

    private void visParti() {
        p_liste_parti.getItems().clear();

        for(Turnering t: turneringer) {
            if(t.toString().equals(aktivTurnering.toString())) {
                for(Parti p: t.hentParti()) {
                    p_liste_parti.getItems().add(p);
                }
            }
        }
    }

    public void velgPartiTab() {
        valgtParti();
        tab_pane.getSelectionModel().select(tab_rp);
        populerRedigerPartiBox();
    }

    /**
     *
     * Metode for å velge parti, slik at det kan redigeres
     *
     * */

    private void valgtParti() {

        for(Parti p: aktivTurnering.hentParti()) {

            if(p.toString().equals(p_liste_parti.getSelectionModel().getSelectedItem().toString())){
                valgtParti = new Parti(p.getSpillerHvit(), p.getSpillerSort(), p.getDato(), p.getTid());
                rp_tekstfelt_partinavn.setText(valgtParti.toString());
            }
        }

    }

    /**
     *
     * Metode for å legge til riktig informasjon i legg til trekk tab
     *
     * */

    private void populerRedigerPartiBox() {
        rp_tekstfelt_fra_rute.setCenterShape(true);
        rp_tekstfelt_fra_rute.getItems().addAll(Posisjon.values());
        rp_tekstfelt_til_rute.getItems().addAll(Posisjon.values());
        rp_tekstfelt_brikketype.getItems().addAll(BrikkeType.values());
        rp_kombo_utfall.getItems().addAll(valgtParti.getSpillerHvit(), valgtParti.getSpillerSort());
        visTrekk();
    }

    /**
     *
     * Metoode for å legge til trekk i et parti
     *
     */


    public void redigerParti() {

        System.out.println("Legg til trekk");
        rp_liste_trekk.getItems().clear();
        for(Parti p: aktivTurnering.hentParti()) {
            if (p.toString().equals(valgtParti.toString())) {
                    p.setTrekk(new Trekk(rp_tekstfelt_fra_rute.getValue(), rp_tekstfelt_til_rute.getValue(), rp_tekstfelt_brikketype.getValue()));
                    visTrekk();
                    System.out.println("Brikketype: " + rp_tekstfelt_brikketype.getValue().getClass());
                    System.out.println("Posisjon: " + rp_tekstfelt_til_rute.getValue().getClass());


            }
        }
    }

    /**
     *
     * Metode for å vise filer i ListView
     *
     * */

    private void visTrekk() {
        for(Parti p: aktivTurnering.hentParti()) {
            if(p.toString().equals(valgtParti.toString())) {
                rp_liste_trekk.getItems().addAll(p.getTrekkListe());
            }
        }
    }

    public void tomData(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Du er i ferd med å slette all data!");
        alert.setContentText("Du har valgt å slette all data, for å bekrefte trykk \"OK\" ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            File file = new File("turneringer/info.dat");
            try  {
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println(e.toString());
            }
            alert.close();
            visTurneringer();
        } else {
           alert.close();
        }


    }


}
