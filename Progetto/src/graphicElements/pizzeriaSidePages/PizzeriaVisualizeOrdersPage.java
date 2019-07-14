package graphicElements.pizzeriaSidePages;

import graphicElements.elements.GraphicRecap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.Order;
import pizzeria.Pizzeria;
import pizzeria.services.TextColorServices;
import pizzeria.services.TimeServices;

import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("deprecation")
public class PizzeriaVisualizeOrdersPage {

	/** Lo Stage mostra, dal lato Pizzeria, tutti gli ordini che devono essere evasi oggi.
	 * La grafica è minima, contando sul fatto che la Pizzeria desidera avere solo le informazioni
	 * strettamente necessarie, senza eccessi.
	 * Il bottone di aggiornamento pagina permette alla Pizzeria, in ogni momento, di assicurarsi
	 * che non siano stati effettuati nuovi ordini da quando ha accesso a questa pagina. */

	public static void display(Pizzeria pizzeria, Stage window) {
		window.setTitle("Wolf of Pizza - Elenco Ordini");
		ArrayList<Label> nameLabels = new ArrayList<>();
		VBox layout = new VBox();

		/* Aggiunge tutti gli ordini di oggi al layout */
		for (String code : pizzeria.getOrders().keySet()) {
			if (pizzeria.getOrders().get(code).getTime().getDate() == (new Date().getDate())) {
				VBox vBox = addEverythingToGridPane(pizzeria.getOrders().get(code), nameLabels);
				vBox.setPadding(new Insets(10, 10, 10, 10));
				layout.getChildren().add(vBox);
			}
		}

		/* Bottoni di fondo pagina */
		Button backButton = new Button("← Torna indietro");
		backButton.setOnAction(e -> {
			PizzeriaHomePage pizzeriaHomePage = new PizzeriaHomePage();
			pizzeriaHomePage.display(pizzeria, window);
		});
		backButton.setMinHeight(35);

		Button refreshButton = new Button("Aggiorna pagina ");
		refreshButton.setOnAction(e ->
				display(pizzeria,window)
		);
		refreshButton.setMinHeight(35);

		HBox buttonsBox = new HBox(10);
        buttonsBox.setMinSize(600, 60);
		buttonsBox.getChildren().addAll(backButton,refreshButton);
		buttonsBox.setAlignment(Pos.CENTER);

		layout.getChildren().add(buttonsBox);
		layout.setMinHeight(540);
		ScrollPane scrollPane = new ScrollPane(layout);
		Scene scene = new Scene(scrollPane, 880, 600);
		window.setScene(scene);
		window.show();
	}

	/** Aggiunge tutte le informazioni necessarie al GridPane */
	private static VBox addEverythingToGridPane(Order order, ArrayList<Label> nomiLabels) {
		Label orderLabel = new Label(order.getOrderCode() + "\t");
		Label timeLabel = new Label(TimeServices.dateTimeStamp(order.getTime()) + "\t");
		Label infoLabel = new Label(order.getName() + "\t" + order.getAddress());
		Label totalLabel = new Label("\tTOTALE:  " + order.getTotalPrice() + " €");
		HBox infoBox = new HBox();
		infoBox.getChildren().addAll(timeLabel,orderLabel,infoLabel,totalLabel);
		HBox spazioBox = new HBox();
		spazioBox.getChildren().add(new Label(TextColorServices.getLine()));

		GridPane gridPane;
		gridPane = GraphicRecap.graphicRecap(nomiLabels, order);
		gridPane.getColumnConstraints().add(new ColumnConstraints(50));
		gridPane.getColumnConstraints().add(new ColumnConstraints(100));
		gridPane.getColumnConstraints().add(new ColumnConstraints(160));
		gridPane.getColumnConstraints().add(new ColumnConstraints(500));

		gridPane.setHgap(10);
		gridPane.setVgap(25);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(infoBox,gridPane,spazioBox);
		return vBox;
	}
}