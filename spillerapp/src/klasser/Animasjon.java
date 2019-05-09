package klasser;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Animasjon implements Runnable {

    private int antallTrekk, indeks;
    private long hastighet;
    private AnchorPane sjakkbrett;
    private ObservableList<Trekk> trekk;
    private boolean play;

    public Animasjon(AnchorPane sjakkbrett, ObservableList<Trekk> trekk){
        this.antallTrekk = trekk.size();
        this.indeks = 0;
        this.sjakkbrett = sjakkbrett;
        this.trekk = trekk;
        this.hastighet = 5000;
    }

    @Override
    public void run() {

        while (true){

            while (play && this.indeks<antallTrekk){

                try {

                    Thread.sleep(this.hastighet);
                    this.utførTrekk();

                } catch (InterruptedException e) {

                    Sjakkbrett.visFeil(
                            "Animasjon krasjet",
                            "Ooops, sorry!",
                            "Animasjonen krasjet. Error: " + e.getMessage()
                    );

                }

            }

        }

    }

    private void utførTrekk(){

        Trekk trekk = this.trekk.get(this.indeks);
        BrikkeType brikkeType = trekk.getBrikkeType();
        Posisjon fra = trekk.getFraTrekk();
        Posisjon til = trekk.getTilTrekk();

        Sjakkbrett.trekk(this.sjakkbrett, brikkeType, fra, til);

    }

    public void play(){
        this.play = true;
    }

    public void pause(){
        this.play = false;
    }

    public boolean spiller(){
        return this.play;
    }

    public void forrigeTrekk(){
        if (this.indeks - 1 >= 0){
            this.indeks--;
            this.utførTrekk();
        }
    }

    public void nesteTrekk(){
        if (this.indeks + 1 < this.antallTrekk ){
            this.indeks++;
            this.utførTrekk();
        }
    }

    public void setHastighet(double sekunder){
        this.hastighet = Long.parseLong(String.valueOf(Duration.seconds(sekunder).toMillis()));
    }

}
