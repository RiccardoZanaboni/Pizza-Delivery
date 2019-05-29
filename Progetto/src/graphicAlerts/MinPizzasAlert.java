package graphicAlerts;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Risolve, stampando un messaggio in una nuova finestra, la possibilità che l'utente
 * richieda di eliminare dall'ordine pizze non presenti nell'ordine stesso.
 */

public class MinPizzasAlert {
    public static void display(String s) {
        Stage window = new Stage();
        Label printError = new Label("Attenzione: non ci sono " + s + " da rimuovere!");

        printError.autosize();

        StackPane layout = new StackPane();
        layout.getChildren().add(printError);


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ATTENZIONE");
        //window.setMinWidth(250);
        Scene scene = new Scene(layout, 430, 150);
        window.setScene(scene);
        window.show();
    }
}
