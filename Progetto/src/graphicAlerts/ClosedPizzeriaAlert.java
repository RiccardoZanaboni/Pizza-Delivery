package graphicAlerts;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

/**
 * Risolve, stampando un messaggio in una nuova finestra, la possibilità che l'utente
 * desideri richiedere un nuovo ordine oltre l'orario di chiusura della pizzeria.
 */

public class ClosedPizzeriaAlert {
	public static void display(boolean b) {
		Stage window = new Stage();
		Label printError;
		if(b)
			printError = new Label("Spiacenti: la pizzeria al momento è in chiusura. Torna a trovarci domani!");
		else
			printError = new Label("Spiacenti: la pizzeria al momento è chiusa. Torna a trovarci domani!");
		printError.autosize();

		StackPane layout = new StackPane();
		layout.getChildren().add(printError);


		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Impossibile effettuare nuovo ordine");
		Scene scene = new Scene(layout, 630, 150);
		window.setScene(scene);
		window.show();
	}

}
