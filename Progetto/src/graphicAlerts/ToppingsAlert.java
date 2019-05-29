package graphicAlerts;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Risolve, stampando un messaggio in una nuova finestra, la possibilità che l'utente
 * richieda una pizza, modificata dal menu, senza alcun ingrediente.
 */

public class ToppingsAlert {
    public static void display() {
        Stage window = new Stage();
        Label printError = new Label("Attenzione: inserire almeno un ingrediente!");

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
