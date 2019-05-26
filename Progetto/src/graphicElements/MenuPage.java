package graphicElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.DeliveryMan;
import pizzeria.Order;
import pizzeria.Pizzeria;


/**
 * Finestra iniziale che rappresenta la "Home", con le varie possibilità
 * di utilizzo del programma. Si attiva al "run" di GraphicInterface.
 */

public class MenuPage {
	private Scene scene1;
	private Scene scene3 = null;

	public void display(Stage window, Pizzeria pizzeria) {
		Label label1 = new Label("Benvenuto");
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(label1);
		stackPane.getStyleClass().add("stackpane");

		Image image1 = new Image("graphicElements/banner_pizza.jpg");
		ImageView imageView = new ImageView(image1);
		imageView.setFitHeight(150);
		imageView.setFitWidth(880);
		imageView.autosize();

		StackPane spazioPane = new StackPane();
		spazioPane.setMinSize(800, 150);
		spazioPane.getChildren().add(imageView);
		spazioPane.setAlignment(Pos.CENTER);
		//spazioPane.setId("pane");

		// makeNewOrder - login - register - myAccount (bloccato se non loggato)

		Button makeOrderButton = new Button("Nuovo Ordine");
        makeOrderButton.prefWidthProperty().bind(window.widthProperty());
        makeOrderButton.prefHeightProperty().bind(window.heightProperty());
		makeOrderButton.setOnAction(e -> {
			pizzeria.OpenPizzeria(8);
			pizzeria.AddDeliveryMan(new DeliveryMan("Musi", pizzeria));
			Order order = pizzeria.initializeNewOrder();
			OrderPage1 orderPage1 = new OrderPage1();
			orderPage1.display(window, scene1, order, pizzeria);
		});


		Button chiSiamoButton = new Button("Chi siamo");
		chiSiamoButton.prefWidthProperty().bind(window.widthProperty());
        chiSiamoButton.prefHeightProperty().bind(window.heightProperty());
        //chiSiamoButton.setShape(new Rectangle(10,10));

        Button recapOrdiniButton = new Button("Riepilogo ordini");
        recapOrdiniButton.prefWidthProperty().bind(window.widthProperty());
        recapOrdiniButton.prefHeightProperty().bind(window.heightProperty());

        Button altroButton = new Button("Altro");
		altroButton.prefWidthProperty().bind(window.widthProperty());
        altroButton.prefHeightProperty().bind(window.heightProperty());

		GridPane gridPane = new GridPane();
		gridPane.getChildren().addAll(makeOrderButton, chiSiamoButton, recapOrdiniButton, altroButton);
		GridPane.setConstraints(makeOrderButton, 1, 1);
		GridPane.setConstraints(chiSiamoButton, 2, 1);
		GridPane.setConstraints(recapOrdiniButton, 1, 2);
		GridPane.setConstraints(altroButton, 2, 2);

		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		VBox layout = new VBox();
		//layout.getChildren().addAll(stackPane, spazioPane, makeOrderButton, chiSiamoButton, recapOrdiniButton, altroButton);
		layout.getChildren().addAll(stackPane, imageView, gridPane);
		layout.getStyleClass().add("layout");
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());

		scene1 = new Scene(layout);
		scene1.getStylesheets().addAll(this.getClass().getResource("menuStyle.css").toExternalForm());
		//window.setResizable(false);
		window.setScene(scene1);
		window.setTitle("Wolf of Pizza");
		window.getIcons().add(new Image("graphicElements/wolf_pizza.png"));
		window.show();
	}
}
