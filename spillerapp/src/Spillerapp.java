import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Spillerapp extends Application {

    private Scene scene;
    private Parent root;

    private static final String TITLE = "Spillerapplikasjon";
    private static final String FXML_PATH = "/gui/gui.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Comment here
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.centerOnScreen();

        this.root = FXMLLoader.load(getClass().getResource(FXML_PATH));
        this.scene = new Scene(this.root);

        stage.setScene(this.scene);
        stage.show();

        Task<Integer> task = new Task<Integer>() {
            @Override protected Integer call() throws Exception {
                int iterations;
                for (iterations = 0; iterations < 100000; iterations++) {
                    if (isCancelled()) {
                        break;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Iteration " + iterations);
                }
                return iterations;
            }
        };

    }


}
