import graphicElements.MenuPage;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import pizzeria.Pizzeria;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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
	private Pizzeria wolf = new Pizzeria("Wolf Of Pizza", "Via Bolzano 10, Pavia", LocalTime.MIN.plus(60*9+30, ChronoUnit.MINUTES), LocalTime.MIN, LocalTime.MIN.plus(60*11+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*11+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*11+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*11+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*11+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*17+30, ChronoUnit.MINUTES), LocalTime.MIN, LocalTime.MIN.plus(60*21+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*21+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*21+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*22+30, ChronoUnit.MINUTES), LocalTime.MIN.plus(60*23+30, ChronoUnit.MINUTES));
	private Stage window;
	private Scene scene0, scene3;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		window = primaryStage;

		Image image = new Image("graphicElements/wolf_pizza.png");
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(600);
		imageView.setFitWidth(800);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(imageView);

		scene0 = new Scene(stackPane, 880, 600);
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

		fadeIn.setOnFinished((e) -> {
			fadeOut.play();
			MenuPage menuPage = new MenuPage();
			menuPage.display(window, wolf);
		});

	}
}