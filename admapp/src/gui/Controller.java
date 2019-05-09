package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import klasser.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static klasser.Sjakkbrett.atomiserPosisjon;


/**
 * Controller: Admin App
 * */
public class Controller implements Initializable {

    private ArrayList<Turnering> turneringer = new ArrayList<>();
    private ArrayList<String> kvalitetsKode= new ArrayList<>();
    private Turnering nyTurnering;
    private Turnering aktivTurnering;
    private Parti valgtParti;
    private boolean partierLastet;

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
    @FXML private ComboBox<String> rp_kombo_utfall;
    @FXML private ComboBox<String> rp_kombo_kvalitetskode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hentTurneringern();
        visTurneringer();
        setTurneringKomboBox();

        t_knapp_velg_turnering.disableProperty()
                .bind(t_liste_turnering.getSelectionModel().selectedItemProperty().isNull());

    }


    public void lagMappe() {
        String tempNavn = t_tekstfelt_turneringsnavn.getText();
        String tempStartDato = t_tekstfelt_startdato.getText();
        String tempSluttDato = t_tekstfelt_sluttdato.getText();
        String tempSted = t_tekstfelt_sted.getText();
        String finalPath = ""+tempNavn+tempStartDato+tempSluttDato+tempSted;

        if(finalPath.length() > 3){
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

        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasjonsmelding!");
            alert.setHeaderText("Feilmelding:");
            alert.setContentText("Fyll ut informasjon!");
            alert.showAndWait();

        }




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
        boolean bool = (rt_tekstfelt_fornavn.getText()+rt_tekstfelt_etternavn.getText()).isEmpty();
        System.out.println(bool);
        if(!bool) {
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
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Partifeil");
            alert.setHeaderText("Ops! Noe gikk galt");
            alert.setContentText("Du må fylle ut alle elementer!");
            alert.showAndWait();

        }


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
        this.partierLastet = true;
        this.p_knapp_velg_parti.setDisable(false);
    }

    public void lagParti(){
        try {
            if(p_kombo_spiller_hvit.getValue().equals(p_kombo_spiller_sort.getValue())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Partifeil");
                alert.setHeaderText("Ops! Noe gikk galt");
                alert.setContentText("Du har satt " + p_kombo_spiller_hvit.getValue().getFornavn() + " til å spille mot seg selv!");
                alert.showAndWait();
            } else {
                if(!(p_tekstfelt_dato.getText()+p_tekstfelt_klokkeslett.getText()).isEmpty()) {
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
                    alert.showAndWait();
                }

            }

        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advarsel!");
            alert.setHeaderText("Du har ikke fylt ut alle felt!");
            alert.setContentText("Alle felt i skjemaet er ikke fylt ut, vennligst fyll alle felt!");
            alert.showAndWait();
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
        Parti partiSjekk = this.p_liste_parti.getSelectionModel().getSelectedItem();

        if (this.partierLastet && partiSjekk != null) {
            valgtParti();
            tab_pane.getSelectionModel().select(tab_rp);
            populerRedigerPartiBox();
        }else {
            Sjakkbrett.visFeil("Parti ikke valgt", "Du har ikke valgt et parti", "Du må velge et jævla parti din idiot!");
        }
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
        rp_kombo_utfall.getItems().addAll(valgtParti.getSpillerHvit().getFornavn(), valgtParti.getSpillerSort().getEtternavn(), "Remi");
        visTrekk();
        kvalitetsKode.clear();
        kvalitetsKode.addAll(Arrays.asList("??","?","?!","!?","!","!!"));
        rp_kombo_kvalitetskode.getItems().addAll(kvalitetsKode);
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
                if(sjekkLovlighet(rp_tekstfelt_brikketype.getValue() ,rp_tekstfelt_fra_rute.getValue(), rp_tekstfelt_til_rute.getValue())){
                    p.setTrekk(new Trekk(rp_tekstfelt_fra_rute.getValue(), rp_tekstfelt_til_rute.getValue(), rp_tekstfelt_brikketype.getValue(), rp_kombo_kvalitetskode.getValue()));
                    visTrekk();

                }else{
                    visTrekk();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Partifeil");
                    alert.setHeaderText("Ops! Noe gikk galt");
                    alert.setContentText("Ulovlig trekk!");
                    alert.showAndWait();


                }

            }
        }

    }
    public static boolean sjekkLovlighet(BrikkeType brikkeType, Posisjon fra, Posisjon til){

        char fra_bokstav = atomiserPosisjon(fra)[0].charAt(0);
        int fra_tall = Integer.parseInt(atomiserPosisjon(fra)[1]);

        char til_bokstav = atomiserPosisjon(til)[0].charAt(0);
        int til_tall = Integer.parseInt(atomiserPosisjon(til)[1]);

        int diffBokstav = Math.abs(fra_bokstav - til_bokstav);
        int diffTall = Math.abs(fra_tall - til_tall);

        int maksFlytt = 0;


        // ? Konge:
        if (brikkeType == BrikkeType.KONGE_HVIT || brikkeType == BrikkeType.KONGE_SORT) {

            if (diffTall <= 1)
                return (diffBokstav <= 1);

        }

        // ? Dronning:
        else if (brikkeType == BrikkeType.DRONNING_HVIT || brikkeType == BrikkeType.DRONNING_SORT) {

            if (diffBokstav == 0 || fra_tall == til_tall)
                return true;
            else return (diffBokstav == diffTall);

        }

        // ? Tårn:
        else if (brikkeType == BrikkeType.TÅRN_HVIT || brikkeType == BrikkeType.TÅRN_SORT) {

            return (diffBokstav == 0 || fra_tall == til_tall);

        }

        // ? Springer:
        else if (brikkeType == BrikkeType.SPRINGER_HVIT || brikkeType == BrikkeType.SPRINGER_SORT) {

            if ((diffBokstav + diffTall) <= 3)
                return (diffBokstav == 2 && diffTall == 1 || diffBokstav == 1 && diffTall == 2);

        }

        // ? Løper:
        else if (brikkeType == BrikkeType.LØPER_HVIT || brikkeType == BrikkeType.LØPER_SORT) {

            return (diffBokstav == diffTall);

        }

        // ? Bonde:
        else if (brikkeType == BrikkeType.BONDE_HVIT || brikkeType == BrikkeType.BONDE_SORT) {

            if (brikkeType == BrikkeType.BONDE_HVIT && fra_tall == 2 || brikkeType == BrikkeType.BONDE_SORT && fra_tall == 7)
                maksFlytt = 2;
            else maksFlytt = 1;

            return (diffBokstav == 0 && diffTall <= maksFlytt);

        }

        return false;

    }


    public void lagrePoengHandler() {
        lagrePartiTrekk();
        lagreInformasjon();
    }

    /**
     *
     *
     * Metode for å lagre partier, og da gi poeng i forhold til hvem som vant
     *
     * */
    private void lagrePartiTrekk() {
        if(rp_kombo_utfall.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advarsel!");
            alert.setHeaderText("Tomme felt");
            alert.setContentText("Du har ikke valgt resultat!");
            alert.showAndWait();
        } else {
            for(Turnering t: turneringer) {
                if(t.toString().equals(aktivTurnering.toString())) {
                    for(Parti p: t.hentParti()) {
                        if(p.toString().equals(valgtParti.toString())) {
                            if (p.getSpillerHvit().getFornavn().equals(rp_kombo_utfall.getValue())) {
                                p.getSpillerHvit().setPoeng(1);
                            } else if(rp_kombo_utfall.getValue().equals("Remi")){
                                p.getSpillerHvit().setPoeng(0.5);
                                p.getSpillerSort().setPoeng(0.5);
                            } else if(p.getSpillerSort().getFornavn().equals(rp_kombo_utfall.getValue())) {
                                p.getSpillerSort().setPoeng(1);
                            } else {
                                System.out.println("Ingen match funnet!");
                            }
                        }
                    }
                }

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
        alert.setTitle("Er du sikker?");
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
            t_liste_turnering.getItems().clear();
            visTurneringer();
        } else {
           alert.close();
        }

    }

    private void skrivTilBackuo() {

    }


}
