import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Adminapp extends Application {

    private Scene scene;
    private Parent root;

    private static final String TITLE = "Administratorapplikasjon";
    private static final String FXML_PATH = "/gui/gui.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
                                    
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.centerOnScreen();

        this.root = FXMLLoader.load(getClass().getResource(FXML_PATH));
        this.scene = new Scene(this.root);

        stage.setScene(this.scene);
        stage.show();

    }


}
