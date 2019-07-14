package interfaces;

import graphicElements.customerSidePages.loginPages.LoginAccountPage;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import pizzeria.Pizzeria;

/**
 * * @authors: Javengers, 2019
 *
 *  @author  Fecchio Andrea
 *  @author  Gobbo Matteo
 *  @author  Musitano Francesco
 *  @author  Rossanigo Fabio
 *  @author  Zanaboni Riccardo
 *
 * Avvia il programma tramite interfaccia grafica.
 */

public class GraphicInterface extends Application {

	/** Richiama il costruttore della pizzeria. */
	private Pizzeria wolf = new Pizzeria("Wolf Of Pizza", "Via della Mozzarella, Pavia");
	private Stage window;

	/** Il main() consiste semplicemente nella chiamata a start() */
	public static void main(String[] args) {
		System.out.println("Inizializzando...");
		launch(args);
	}

	/** Crea un nuovo Stage (window), in cui compare un'immagine di benvenuto in dissolvenza, dopodichè
	 * viene visualizzata la pagina iniziale di Login. */
	@Override
	public void start(Stage primaryStage){
		System.out.println("Pronto.");
		window = primaryStage;
		window.getIcons().add(new Image("/graphicElements/images/wolf_pizza.png"));
		window.setTitle("Wolf of Pizza");

		Image image = new Image("/graphicElements/images/wolf_pizza.png");
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(600);
		imageView.setFitWidth(800);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(imageView);

		Scene scene0 = new Scene(stackPane, 800, 600);
		window.setScene(scene0);
		window.show();
		FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), stackPane);
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);
		fadeIn.setCycleCount(1);

		FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), stackPane);
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		fadeOut.setCycleCount(1);

		fadeIn.play();
		fadeIn.setOnFinished(e -> {
			fadeOut.play();
			LoginAccountPage loginAccountPage = new LoginAccountPage();
			loginAccountPage.display(window, wolf);
		});
	}
}