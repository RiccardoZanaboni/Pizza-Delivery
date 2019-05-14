package elementiGrafici;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.DeliveryMan;
import pizzeria.Order;
import pizzeria.Pizzeria;

public class MenuPage {
	private Scene scene1;

	public void display(Stage window, Scene scene3, Pizzeria pizzeria) {

		Label label1 = new Label("Wolf Of Pizza");
		StackPane stackPane = new StackPane();
		stackPane.setMinSize(600, 50);
		stackPane.getChildren().add(label1);
		stackPane.getStyleClass().add("stackpane");

		StackPane spazioPane = new StackPane();
		spazioPane.setMinSize(600, 150);
		spazioPane.setId("pane");

		// Definisco i bottoni presenti nella pagina

		Button makeOrderButton = new Button("Nuovo Ordine");
		makeOrderButton.setMinSize(600, 100);
		Button chiSiamoButton = new Button("Chi siamo");
		chiSiamoButton.setMinSize(600, 100);
		Button recapOrdiniButton = new Button("Riepilogo ordini");
		recapOrdiniButton.setMinSize(600, 100);
		Button altroButton = new Button("Altro");
		altroButton.setMinSize(600, 100);

		makeOrderButton.setOnAction(e -> {
			pizzeria.ApriPizzeria(8);
			pizzeria.AddFattorino(new DeliveryMan("Musi",pizzeria));
			Order order = pizzeria.inizializeNewOrder();
			OrderPage1 orderPage1 = new OrderPage1();
			orderPage1.display(window, scene1, scene3, order, pizzeria);
		});

		VBox layout = new VBox();
		layout.getChildren().addAll(stackPane, spazioPane, makeOrderButton, chiSiamoButton, recapOrdiniButton, altroButton);
		layout.getStyleClass().add("layout");

		scene1 = new Scene(layout, 600, 600);
		scene1.getStylesheets().addAll(this.getClass().getResource("graphicInterfaceStyle.css").toExternalForm());
		window.setResizable(false);
		window.setScene(scene1);
		window.setTitle("Wolf of Pizza");
		window.getIcons().add(new Image("elementiGrafici/wolf_pizza.png"));
		window.show();
	}
}
