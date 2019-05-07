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
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Controller: Admin App
 * */
public class Controller implements Initializable {

    private ArrayList<Parti> partier;
    private ArrayList<String> turnListe;
    private ArrayList<Turnering> turneringer;
    private ObservableList<Spiller> spillere;
    private ArrayList<String> liste;
    private Turnering turnering;


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
    @FXML private ChoiceBox<?> p_kombo_spiller_sort;
    @FXML private Button p_knapp_lag_parti;
    @FXML private TextField p_tekstfelt_klokkeslett;
    @FXML private ChoiceBox<String> p_kombo_turnering = new ChoiceBox<>();
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

            Turnering nyTurnering = new Turnering(
                    tempNavn,
                    tempStartDato,
                    tempSluttDato,
                    tempSted);
            nyTurnering.setFil(finalPath);

            //Skriv til .json her fremfor system.out.
            System.out.println(nyTurnering.toString());
        }



        //Tømmer lista, og oppdaterer med nye verdier
        t_liste_turnering.getItems().clear();
        visTurneringer();



    }

    public void opprettTurnering(){
        System.out.print("lol2");
        /*
        //Fjern klammer: (?<=\[{1})(.+)(?=\]{1})
        //Hent ut tekst: [a-zæøåA-ZÆØÅ]+
        //Skill datoer: ([0-9-]){10}

        //s.split("regex")
        //s.matches("regex")

        String nyTurn = ""+t_liste_turnering.getSelectionModel().getSelectedItems();


        Pattern pattern = Pattern.compile("(?<=\\[{1})(.+)(?=\\]{1})");
        Matcher matcher = pattern.matcher(nyTurn);
        nyTurn = matcher.group();

        pattern = Pattern.compile("[a-zæøåA-ZÆØÅ]");
        matcher = pattern.matcher(nyTurn);

        nyTurn = matcher.group();


        while(matcher.find()){

            System.out.println(nyTurn);
        }







        //Turnering turn = new Turnering();

         */
    }

    //Populer listView t_liste_turnering
    private void visTurneringer() {
        Fil turneringer = new Fil();
        turnListe = new ArrayList<>();
        String [] søkListe = turneringer.søkTurneringer();
        turnListe.addAll(Arrays.asList(søkListe));
        t_liste_turnering.getItems().addAll(turnListe);
    }

    private void opprettSpillere() {
        String fileName = "admapp/src/turneringer/Bøsjakkmesterskap20190102/spillere.txt";
        String line = null;
        liste = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            while(br.ready()) {
                liste.add(br.readLine());
            }


        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }catch(IOException ei) {
            System.out.println("IO fault");
        }



    }

    private void setKomboSpillere() {

        p_kombo_turnering.getItems().addAll(turnListe);
        p_kombo_spiller_hvit.getItems().addAll(liste);
        p_liste_parti.getItems().addAll(turnListe);
    }

    private void lagreTurnering() {

    }

    private void leggTilSpiller() {
        String fornavn = this.rt_tekstfelt_fornavn.getText();
        String etternavn = this.rt_tekstfelt_etternavn.getText();
        int poeng = 0;
        Spiller spiller = new Spiller(fornavn, etternavn, poeng);
        turnering.leggTilSpiller(spiller);

        this.rt_tekstfelt_fornavn.setText("");
        this.rt_tekstfelt_etternavn.setText("");

    }

    private void lagParti(){
        String spillerHvit = p_kombo_spiller_hvit.getValue().toLowerCase();
        Spiller spillerHvitObjekt;
        for(Turnering turnering: turneringer ) {
            for(Spiller spiller: turnering.hentSpillerArray()) {
                if(spiller.getFornavn()+spiller.getEtternavn() == spillerHvit){
                    spillerHvitObjekt = new Spiller(spiller.getFornavn(), spiller.getEtternavn(), spiller.getPoeng());
                } else if(spiller.getFornavn()+spiller.getEtternavn() == )
            }
        }
        Parti parti = new Parti(spillerHvitObjekt, );
    }


}
