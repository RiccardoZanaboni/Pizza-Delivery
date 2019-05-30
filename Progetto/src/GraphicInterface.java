import graphicElements.MenuPage;
import graphicElements.ProfilePage;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import pizzeria.Pizzeria;
import pizzeria.Services;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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

	/**
	 * 16 parametri: nome, indirizzo, 7 orari di apertura (da domenica a sabato),
	 * 7 orari di chiusura (da domenica a sabato).
	 *
	 * Gli orari partono sempre da LocalTime.MIN, che corrisponde a mezzanotte.
	 * A questo si aggiunge (con il metodo plus()) ora e minuti desiderati.
	 *
	 * ATTENZIONE: Per lasciare la pizzeria chiusa in un particolare giorno, porre openTime = closeTime.
	 * PRESTARE PARTICOLARE ATTENZIONE: assicurarsi che ogni giorno la pizzeria rimanga aperta almeno 20 minuti.
	 *
	 * Per modificare gli orari successivamente, lavorerò con il metodo Pizzeria.setDayOfTheWeek().
	 * */
	private Pizzeria wolf = new Pizzeria("Wolf Of Pizza", "Via Bolzano 10, Pavia",
			// orari di apertura, da domenica a sabato
			LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(Services.getMinutes(18,30), ChronoUnit.MINUTES),
			// orari di chiusura, da domenica a sabato
			LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(Services.getMinutes(0,0), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(Services.getMinutes(23,59), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES),
			LocalTime.MIN.plus(Services.getMinutes(23,30), ChronoUnit.MINUTES)
	);
	private Stage window;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage){

		window = primaryStage;

		Image image = new Image("graphicElements/images/wolf_pizza.png");
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(600);
		imageView.setFitWidth(800);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(imageView);

		Scene scene0 = new Scene(stackPane, 880, 600);
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
			MenuPage menuPage = new MenuPage();
			menuPage.display(window, wolf);
			ProfilePage profilePage = new ProfilePage();
			profilePage.display(window, wolf);
		});

	}
}