package avvisiGrafici;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertTopping {
    public static void display() {
        Stage window = new Stage();
        Label printError = new Label("Inserire almeno un ingrediente");

        printError.autosize();

        StackPane layout = new StackPane();
        layout.getChildren().add(printError);


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Modifica la pizza");
        Scene scene = new Scene(layout, 430, 150);
        window.setScene(scene);
        window.show();
    }
}
