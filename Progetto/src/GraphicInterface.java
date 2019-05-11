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

public class GraphicInterface extends Application{
  Stage window;
  Button button;
  Scene scene1, scene2, scene3;
  TableView<Pizza> table;
  Pizzeria wolf = new Pizzeria("Wolf Of pizzeria.Pizza","Via Bolzano 10, Pavia", new Date(2019,0,1,19,0),new Date(2019,0,31,23,0,0));
  int tot=0;

  public static void main(String[] args) { launch(args); }

  @Override
  public void start(Stage primaryStage) throws Exception {
  	window=primaryStage;

    /** PRIMA PAGINA*/

    Label label1 = new Label("Wolf Of Pizza");
    StackPane stackPane = new StackPane();
    //stackPane.setMinSize(100, 100);
    stackPane.setMinSize(600, 50);
    stackPane.getChildren().add(label1);
    stackPane.getStyleClass().add("stackpane");

    StackPane spazioPane = new StackPane();
    spazioPane.setMinSize(600, 150);


    // Definisco i bottoni presenti nella pagina

    Button makeOrderButton = new Button("Nuovo Ordine");
    makeOrderButton.setMinSize(600, 100);
    Button chiSiamoButton = new Button ("Chi siamo");
    chiSiamoButton.setMinSize(600, 100);
    Button recapOrdiniButton = new Button("Riepilogo ordini");
    recapOrdiniButton.setMinSize(600, 100);
    Button altroButton = new Button("Altro");
    altroButton.setMinSize(600, 100);

    // Layout per i bottoni
    /*GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(20, 20, 20, 20));
    gridPane.setVgap(20);
    gridPane.setHgap(20);
    GridPane.setConstraints(makeOrderButton, 0, 0);
    GridPane.setConstraints(chiSiamoButton, 1, 0);
    GridPane.setConstraints(recapOrdiniButton, 0, 1);
    GridPane.setConstraints(altroButton, 1, 1);
    gridPane.getChildren().addAll(makeOrderButton, chiSiamoButton, recapOrdiniButton, altroButton);*/

    /**     FAI ORDINE        */

    makeOrderButton.setOnAction(e -> {
      wolf.ApriPizzeria(8);
      wolf.AddFattorino(new DeliveryMan("Musi", wolf));
      String name="";
      String address="";
      Order order = wolf.inizializeNewOrder();
      wolf.creaMenu();
      wolf.setIngredientiPizzeria();
      OrderPage1 orderPage1 = new OrderPage1();
      OrderPage2 orderPage2 = new OrderPage2();
      orderPage1.display(window, scene1, scene3, order, wolf);


      /*name=orderPage2.getNome();
      address = orderPage2.getIndirizzo();
      System.out.println(name + address+ tot);*/
    });

    VBox layout = new VBox();
    layout.getChildren().addAll(stackPane, spazioPane, makeOrderButton, chiSiamoButton, recapOrdiniButton, altroButton);
    layout.getStyleClass().add("layout");

    scene1 = new Scene(layout, 600, 600);
    scene1.getStylesheets().add("elementiGrafici/graphicInterfaceStyle.css");
    window.setResizable(false);
    window.setScene(scene1);
    window.setTitle("Wolf of pizzeria.Pizza");
    window.getIcons().add(new javafx.scene.image.Image("wolf_pizza.jpg"));
    window.show();

  }


}
