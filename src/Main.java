import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Assure-toi que Interface.fxml est sur le classpath (par ex. dans src/ et copié dans le dossier classes).
        URL fxmlUrl = getClass().getResource("/view/Interface.fxml");
        if (fxmlUrl == null) {
            throw new IllegalStateException("Interface.fxml introuvable sur le classpath. Placez-le à la racine des ressources ou ajustez le chemin.");
        }
        Parent root = FXMLLoader.load(fxmlUrl);
        Scene scene = new Scene(root);

        primaryStage.setTitle("Gestion des Salaires - OCP");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
