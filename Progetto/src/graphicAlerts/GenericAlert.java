package graphicAlerts;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Risolve, stampando un messaggio in una nuova finestra, la possibilità che l'utente
 * esegua una generica operazione non consentita.
 */

public class GenericAlert {
    public static void display(String err) {
        Stage window = new Stage();
        Label printError = new Label(err);

        printError.autosize();

        StackPane layout = new StackPane();
        layout.getChildren().add(printError);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ATTENZIONE");
        Scene scene = new Scene(layout, 430, 150);
        window.setScene(scene);
        window.show();
    }
}
