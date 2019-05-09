import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertNumPizzeMax {
  public static void display() {
    Stage window = new Stage();
    Label printError = new Label("Numero di pizze massimo raggiunto");

    printError.autosize();

    StackPane layout = new StackPane();
    layout.getChildren().add(printError);


    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Modifica la pizza");
    //window.setMinWidth(250);
    Scene scene = new Scene(layout, 430, 150);
    window.setScene(scene);
    window.show();
  }
}
