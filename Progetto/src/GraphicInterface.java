import elementiGrafici.MenuPage;
import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import pizzeria.Pizzeria;

import java.util.Date;

// TODO MIGLIORARE GRAFICA CON COLORI E IMMAGINI

public class GraphicInterface extends Application {
	Pizzeria wolf = new Pizzeria("Wolf Of Pizza","Via Bolzano 10, Pavia", new Date(2019,0,1,19,0),new Date(2019,0,31,23,0,0));
	Stage window;
	Scene scene3;

	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;

		MenuPage menuPage = new MenuPage();
		menuPage.display(window, scene3, wolf);
	}
}