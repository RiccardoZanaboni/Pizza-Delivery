import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;

import java.util.Date;


public class GraphicInterface extends Application{
  Stage window;
  Button button;
  Scene scene1, scene2, scene3;
  TableView<Pizza> table;
  Pizzeria wolf = new Pizzeria("Wolf Of Pizza","Via Bolzano 10, Pavia", new Date(2019,0,1,19,0),new Date(2019,0,31,23,0,0));
  int tot=0;

  public static void main(String[] args) { launch(args); }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window=primaryStage;

    /** PRIMA PAGINA*/

    Label label1 = new Label("Benvenuto");
    StackPane stackPane = new StackPane();
    stackPane.setMinSize(100, 100);
    stackPane.getChildren().add(label1);

    // Definisco i bottoni presenti nella pagina
    Button makeOrderButton = new Button("Fai l'ordine");
    makeOrderButton.setMinSize(200, 200);
    Button chiSiamoButton = new Button ("Chi siamo");
    chiSiamoButton.setMinSize(200, 200);
    Button recapOrdiniButton = new Button("Riepilogo ordini");
    recapOrdiniButton.setMinSize(200, 200);
    Button altroButton = new Button("Altro");
    altroButton.setMinSize(200, 200);

    // Layout per i bottoni
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10, 10, 10, 10));
    gridPane.setVgap(10);
    gridPane.setHgap(10);
    GridPane.setConstraints(makeOrderButton, 0, 0);
    GridPane.setConstraints(chiSiamoButton, 1, 0);
    GridPane.setConstraints(recapOrdiniButton, 0, 1);
    GridPane.setConstraints(altroButton, 1, 1);
    gridPane.getChildren().addAll(makeOrderButton, chiSiamoButton, recapOrdiniButton, altroButton);

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

    BorderPane layout = new BorderPane();
    layout.setTop(stackPane);
    layout.setCenter(gridPane);
    scene1 = new Scene(layout, 430, 530);

    window.setResizable(false);
    window.setScene(scene1);
    window.setTitle("Wolf of Pizza");
    window.getIcons().add(new javafx.scene.image.Image("wolf_pizza.jpg"));
    window.show();

  }


}
