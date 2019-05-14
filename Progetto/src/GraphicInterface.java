import elementiGrafici.MenuPage;
import elementiGrafici.OrderPage1;
import elementiGrafici.OrderPage2;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import pizzeria.DeliveryMan;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;

import java.util.Date;

// TODO MIGLIORARE GRAFICA CON COLORI E IMMAGINI

public class GraphicInterface extends Application {
	Stage window;
	Scene scene3;
	Pizzeria wolf = new Pizzeria("Wolf Of Pizza","Via Bolzano 10, Pavia", new Date(2019,0,1,19,0),new Date(2019,0,31,23,0,0));

	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;

		MenuPage menuPage = new MenuPage();
		menuPage.display(window, scene3, wolf);
	}
}