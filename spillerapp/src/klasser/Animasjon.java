package klasser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class Animasjon {

    private int antallTrekk, indeks;
    private int sekundHastighet;
    private AnchorPane sjakkbrett;
    private ObservableList<Trekk> trekk;
    private boolean play;
    private Timeline timeline;
    private KeyFrame keyFrame;

    public Animasjon(){
        this.antallTrekk = 10;
        this.indeks = 0;
        this.sekundHastighet = 1;
        this.config();
    }

    private void config(){

        /*EventHandler<ActionEvent> handler = time -> {

        };

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(FPS), handler));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();*/

        this.keyFrame = new KeyFrame(Duration.seconds(this.sekundHastighet), this::handler);
        this.timeline = new Timeline(this.keyFrame);
        this.timeline.setCycleCount(antallTrekk);

    }

    private void handler(ActionEvent e){
        System.out.println("Trekk " + this.indeks + " av " + this.antallTrekk);
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

    public void forrigeTrekk(){

    }

}
