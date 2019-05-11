package avvisiGrafici;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertNumeroPizzeMin {
    public static void display(String s) {
        Stage window = new Stage();
        Label printError = new Label("Non ci sono "+s+" da rimuovere");

        printError.autosize();

        StackPane layout = new StackPane();
        layout.getChildren().add(printError);


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Rimuovi la pizza");
        //window.setMinWidth(250);
        Scene scene = new Scene(layout, 430, 150);
        window.setScene(scene);
        window.show();
    }
}
