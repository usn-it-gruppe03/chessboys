package klasser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Animasjon {

    private int antallTrekk, indeks;
    private ComboBox<Integer> hastigheter;
    private int hastighet;
    private AnchorPane sjakkbrett;
    private ObservableList<Trekk> trekk;
    private ListView<Trekk> trekkListe;
    private boolean play;
    private Timeline timeline;
    private KeyFrame keyFrame;

    public Animasjon(AnchorPane sjakkbrett, ListView<Trekk> trekkListe, ComboBox<Integer> hastighet){
        this.trekkListe = trekkListe;
        this.indeks = 0;
        this.sjakkbrett = sjakkbrett;
        this.hastigheter = hastighet;
    }

    public void initier(){
        this.hastigheter.getSelectionModel().select(0);
        this.hastighet = this.hastigheter.getSelectionModel().getSelectedItem();
        this.antallTrekk = trekkListe.getItems().size();
        this.trekk = trekkListe.getItems();
        this.konfigurer();
    }

    private void konfigurer(){
        this.keyFrame = new KeyFrame(Duration.seconds(this.hastighet), this::tidsløkke);
        this.timeline = new Timeline(this.keyFrame);
        this.timeline.setCycleCount(antallTrekk);
    }

    private void tidsløkke(ActionEvent e){
        System.out.println("Trekk " + this.indeks + " av " + this.antallTrekk);

        this.keyFrame.getTime().subtract(Duration.seconds(1));
        System.out.println("Hastighet: " + this.keyFrame.getTime().toSeconds());
        this.utførTrekk();

        this.indeks++;
    }

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

    public void utførTrekk(){

        this.trekkListe.getSelectionModel().select(this.indeks);

        Trekk trekk = this.trekk.get(this.indeks);
        BrikkeType brikkeType = trekk.getBrikkeType();
        Posisjon fra = trekk.getFraTrekk();
        Posisjon til = trekk.getTilTrekk();

        Sjakkbrett.trekk(this.sjakkbrett, brikkeType, fra, til);

    }

    public void forrigeTrekk(){

        Sjakkbrett.resettBrett(this.sjakkbrett);

    }

}
