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

	public void display(Stage window, Pizzeria pizzeria) {

		Label label1 = new Label("Wolf Of Pizza");
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(label1);
		stackPane.getStyleClass().add("stackpane");

		StackPane spazioPane = new StackPane();
		spazioPane.setMinSize(600, 150);
		spazioPane.setId("pane");

		// Definisco i bottoni presenti nella pagina

        Button makeOrderButton = new Button("Nuovo Ordine");
        makeOrderButton.prefWidthProperty().bind(window.widthProperty());
        makeOrderButton.prefHeightProperty().bind(window.heightProperty());
		Button chiSiamoButton = new Button("Chi siamo");
		chiSiamoButton.prefWidthProperty().bind(window.widthProperty());
        chiSiamoButton.prefHeightProperty().bind(window.heightProperty());
        Button recapOrdiniButton = new Button("Riepilogo ordini");
        recapOrdiniButton.prefWidthProperty().bind(window.widthProperty());
        recapOrdiniButton.prefHeightProperty().bind(window.heightProperty());
		Button altroButton = new Button("Altro");
		altroButton.prefWidthProperty().bind(window.widthProperty());
        altroButton.prefHeightProperty().bind(window.heightProperty());

        makeOrderButton.setOnAction(e -> {
			pizzeria.ApriPizzeria(8);
			pizzeria.AddFattorino(new DeliveryMan("Musi",pizzeria));
			Order order = pizzeria.inizializeNewOrder();
			OrderPage1 orderPage1 = new OrderPage1();
			orderPage1.display(window, scene1,order,pizzeria);
		});

		VBox layout = new VBox();
		layout.getChildren().addAll(stackPane, spazioPane, makeOrderButton, chiSiamoButton, recapOrdiniButton, altroButton);
		layout.getStyleClass().add("layout");

        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());

		scene1 = new Scene(layout);
		scene1.getStylesheets().addAll(this.getClass().getResource("graphicInterfaceStyle.css").toExternalForm());
		//window.setResizable(false);
		window.setScene(scene1);
		window.setTitle("Wolf of Pizza");
		window.getIcons().add(new Image("elementiGrafici/wolf_pizza.png"));
		window.show();
	}
}
