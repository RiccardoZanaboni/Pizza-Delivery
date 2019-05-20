import elementiGrafici.MenuPage;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import pizzeria.Pizzeria;
import java.util.Date;

// TODO MIGLIORARE GRAFICA CON COLORI E IMMAGINI

public class GraphicInterface extends Application {
	private Pizzeria wolf = new Pizzeria("Wolf Of Pizza","Via Bolzano 10, Pavia", new Date(2019,0,1,19,0),new Date(2019,0,31,23,0,0));
	private Stage window;
	private Scene scene0, scene3;

	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage) throws Exception {

		window = primaryStage;

		Image image = new Image("elementiGrafici/wolf_pizza.png");
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(600);
		imageView.setFitWidth(600);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(imageView);

		scene0 = new Scene(stackPane, 600, 600);
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