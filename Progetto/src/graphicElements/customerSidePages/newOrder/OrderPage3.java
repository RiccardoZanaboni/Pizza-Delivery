package graphicElements.customerSidePages.newOrder;

import database.OrderDB;
import graphicElements.customerSidePages.HomePage;
import graphicElements.elements.GraphicRecap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pizzeria.*;

import java.util.ArrayList;

public class OrderPage3 {

	/** Lo Stage ospita la terza pagina della richiesta di un nuovo ordine, quella di riepilogo ordine.
	 * Vengono mostrate tutte le informazioni riguardo i dati selezionati e i prodotti desiderati.
	 * Se l'ordine viene confermato, esso verrà salvato nel DB con tutti i dati necessari per evaderlo,
	 * mentre il vettore (locale) delle disponibilità di forni e fattorini verrà aggiornato. Si tornerà quindi
	 * alla HomePage. In caso contrario, si può tornare alla pagina precedente per modificare i dati inseriti,
	 * oppure tornare direttamente alla HomePage annullando l'ordine.
	 * */
	public void display(Stage window, Order order, Pizzeria pizzeria, Scene scene3, Customer customer) {
		window.setTitle("Wolf of Pizza - Nuovo Ordine");

		/* Costruzione del GridPane */
		ArrayList<Label> nomiLabels = new ArrayList<>();
		GridPane gridPane = GraphicRecap.setGridPane(order,nomiLabels);
		VBox recapBox = GraphicRecap.setIntro(order);

		/* Definizione dei bottoni di fondo pagina */
		Button backButton = new Button("← Torna indietro");
		backButton.setId("backButton");
		backButton.setOnAction(e ->
			window.setScene(scene3)
		);
		backButton.setMaxHeight(35);
		backButton.setMinHeight(35);

		Button confirmButton = new Button("Conferma Ordine ✔");
		confirmButton.setId("confirmButton");
		confirmButton.setOnAction(e -> {
			OrderDB.putOrder(order);
			order.updateAvailability(pizzeria,order.getNumPizze(),order.getTime());
			HomePage homePage = new HomePage();
			homePage.display(window, pizzeria, customer);
		});
		confirmButton.setMaxHeight(35);
		confirmButton.setMinHeight(35);

		Button closeButton = new Button("Annulla Ordine ☓");
		closeButton.setMaxHeight(35);
        closeButton.setMinHeight(35);
		closeButton.setId("closeButton");
		closeButton.setOnAction(e -> {
			HomePage home = new HomePage();
			home.display(window,pizzeria,customer);
		});

		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setMinSize(600, 30);
		buttonBox.getChildren().addAll(backButton, confirmButton, closeButton);

		/* Intestazione */
		Label yourOrder = new Label("Il tuo ordine:\t\t" + order.getOrderCode());
		HBox titleBox = GraphicRecap.setIntestation(yourOrder);

		ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.prefWidthProperty().bind(window.widthProperty());
        scrollPane.prefHeightProperty().bind(window.heightProperty());
        scrollPane.setPadding(new Insets(10, 1, 5, 10));
		buttonBox.setMinSize(600, 60);

		VBox layout = new VBox();
		layout.getChildren().addAll(titleBox, recapBox, scrollPane,buttonBox);
		layout.setOnKeyPressed(ke -> {
			if(ke.getCode()== KeyCode.ENTER)
				confirmButton.fire();
		});

		/* Inserimento del layout nella Scene e quindi nello Stage */
		Scene scene4;
		scene4 = new Scene(layout,800,600);
		layout.setId("layout");
		layout.prefWidthProperty().bind(window.widthProperty());
		layout.prefHeightProperty().bind(window.heightProperty());
		scene4.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/orderPage3.css").toExternalForm());
		window.setScene(scene4);
	}
}