package graphicAlerts;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

/**
 * Risolve, stampando un messaggio in una nuova finestra, la possibilità che l'utente
 * richieda un numero di pizze complessivo superiore al massimo consentito.
 */

public class MaxPizzasAlert {
    public static void display() {
        Stage window = new Stage();
        Label printError = new Label("Attenzione: numero massimo di pizze raggiunto!");

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
