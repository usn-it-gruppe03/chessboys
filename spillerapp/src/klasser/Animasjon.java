package klasser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


/**
 * Klasse Animasjon
 * */
public class Animasjon {


    /**
     * Objektattributter:
     * */
    private int antallTrekk, indeks;
    private ComboBox<Integer> hastigheter;
    private int hastighet;
    private AnchorPane sjakkbrett;
    private ObservableList<Trekk> trekk;
    private ListView<Trekk> trekkListe;
    private boolean play;
    private Timeline timeline;
    private KeyFrame keyFrame;


    /**
     * Konstruktør.
     * */
    public Animasjon(AnchorPane sjakkbrett, ListView<Trekk> trekkListe, ComboBox<Integer> hastighet){
        this.trekkListe = trekkListe;
        this.indeks = 0;
        this.sjakkbrett = sjakkbrett;
        this.hastigheter = hastighet;
    }


    /**
     * Initier animasjon.
     * */
    public void initier(){
        this.hastigheter.getSelectionModel().select(0);
        this.hastighet = this.hastigheter.getSelectionModel().getSelectedItem();
        this.antallTrekk = trekkListe.getItems().size();
        this.trekk = trekkListe.getItems();
        this.konfigurerTimeline();
    }



    /**
     * Konfigurer
     * */
    private void konfigurerTimeline(){
        this.keyFrame = new KeyFrame(Duration.seconds(this.hastighet), this::tidsløkke);
        this.timeline = new Timeline(this.keyFrame);
        this.timeline.setCycleCount(antallTrekk);
    }


    /**
     * Tidsløkke
     * */
    private void tidsløkke(ActionEvent e){
        if (this.indeks < this.antallTrekk){

            System.out.println("Trekk " + (this.indeks+1) + " av " + this.antallTrekk);

            this.keyFrame.getTime().subtract(Duration.seconds(1));
            System.out.println("Hastighet: " + this.keyFrame.getTime().toSeconds());
            this.utførTrekk();

            System.out.println("");

            this.indeks++;

        }
    }


    /**
     * Spill / Pause
     * */
    public void spillPause(){
        if (this.play){
            this.play = false;
            this.timeline.pause();
            System.out.println("Paused");
        } else {
            this.play = true;
            this.timeline.play();
            System.out.println("Playing");
        }
    }


    /**
     * Utfør trekk
     * */
    public void utførTrekk(){

        this.trekkListe.getSelectionModel().select(this.indeks);

        Trekk trekk = this.trekkListe.getItems().get(this.indeks);
        BrikkeType brikkeType = trekk.getBrikkeType();
        Posisjon fra = trekk.getFraTrekk();
        Posisjon til = trekk.getTilTrekk();

        Sjakkbrett.trekk(this.sjakkbrett, brikkeType, fra, til);

    }


    /**
     * Forrige trekk
     * */
    public void forrigeTrekk(){

        if (this.indeks - 1 >= 0){

            this.indeks--;
            this.timeline.stop();
            this.play = false;

            resetTilIndeks(this.indeks);

        }

    }


    /**
     * Neste trekk
     * */
    public void nesteTrekk(){

        if (this.indeks + 1 <= this.antallTrekk){

            this.indeks++;
            this.timeline.stop();
            this.play = false;

            resetTilIndeks(this.indeks);

        }

    }


    /**
     * Velg trekk
     * */
    public void velgTrekk(){

        this.indeks = this.trekkListe.getSelectionModel().getSelectedIndex() + 1;

        resetTilIndeks(this.indeks);

        this.timeline.stop();
        this.play = false;

    }


    /**
     * Set hastighet
     * */
    public void setHastighet(){
        this.timeline.stop();
        this.play = false;
        int hastighet = this.hastigheter.getSelectionModel().getSelectedItem();
        this.keyFrame = new KeyFrame(Duration.seconds(hastighet), this::tidsløkke);
        this.timeline = new Timeline(this.keyFrame);
        this.timeline.setCycleCount(antallTrekk);
        this.timeline.play();
        this.play = true;
    }


    public void resetTilIndeks(int indeks){

        Sjakkbrett.resettBrett(this.sjakkbrett);
        Sjakkbrett.populerSjakkBrikker(this.sjakkbrett);

        for (int i=0; i<indeks; i++){

            if (i<this.antallTrekk){

                this.trekkListe.getSelectionModel().select(i);

                Trekk trekk = this.trekkListe.getItems().get(i);
                BrikkeType brikkeType = trekk.getBrikkeType();
                Posisjon fra = trekk.getFraTrekk();
                Posisjon til = trekk.getTilTrekk();

                Sjakkbrett.trekk(this.sjakkbrett,brikkeType,fra,til);

            }

        }

    }

}
