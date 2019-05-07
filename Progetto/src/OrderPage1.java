import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class OrderPage1 {

  private static int tot;
  private static Button avantiButton;

  public static void display(Stage window, Scene scene1, Scene scene3, Order order, Pizzeria pizzeria) {


    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10, 10, 10, 10));
    gridPane.setVgap(8);
    gridPane.setHgap(10);

    // Inserisco tutte le pizze

    Label margherita = new Label(pizzeria.getMenu().get("MARGHERITA").getNomeCamel());
    Label margheritaIngre = new Label(pizzeria.getMenu().get("MARGHERITA").getDescrizione());
    Button addMargherita = new Button("Aggiungi al carrello");
    Button modMargherita = new Button(("Modifica"));
    addMargherita.setOnAction(e-> {
      order.addPizza(pizzeria.getMenu().get("MARGHERITA"), 1);
      tot++;
    });
    modMargherita.setOnAction(e->ModifyBox.display(order, pizzeria, margherita));
    VBox vBox1 = new VBox();
    vBox1.getChildren().addAll(addMargherita, modMargherita);
    HBox hBox1 = new HBox();
    hBox1.getChildren().addAll(margherita, margheritaIngre, vBox1);

    Label italia = new Label(pizzeria.getMenu().get("ITALIA").getNomeCamel());
    Label italiaIngre = new Label(pizzeria.getMenu().get("ITALIA").getDescrizione());
    Button addItalia = new Button("Aggiungi al carrello");
    Button modItalia = new Button(("Modifica"));
    addItalia.setOnAction(e-> {
      order.addPizza(pizzeria.getMenu().get("ITALIA"), 1);
      tot++;
    });
    modItalia.setOnAction(e->ModifyBox.display(order, pizzeria, italia));
    VBox vBox2 = new VBox();
    vBox2.getChildren().addAll(addItalia, modItalia);
    HBox hBox2 = new HBox();
    hBox2.getChildren().addAll(italia, italiaIngre, vBox2);

    Label marinara= new Label(pizzeria.getMenu().get("MARINARA").getNomeCamel());
    Label marinaraIngre = new Label(pizzeria.getMenu().get("MARINARA").getDescrizione());
    Button addMarinara = new Button("Aggiungi al carrello");
    Button modMarinara = new Button(("Modifica"));
    addMarinara.setOnAction(e-> {
      order.addPizza(pizzeria.getMenu().get("MARINARA"), 1);
      tot++;
    });
    modMarinara.setOnAction(e->ModifyBox.display(order, pizzeria, marinara));
    VBox vBox3 = new VBox();
    vBox3.getChildren().addAll(addMarinara, modMarinara);
    HBox hBox3 = new HBox();
    hBox3.getChildren().addAll(marinara, marinaraIngre, vBox3);

    Label patatine= new Label(pizzeria.getMenu().get("PATATINE").getNomeCamel());
    Label patatineIngre = new Label(pizzeria.getMenu().get("PATATINE").getDescrizione());
    Button addPatatine = new Button("Aggiungi al carrello");
    Button modPatatine = new Button(("Modifica"));
    addPatatine.setOnAction(e-> {
      order.addPizza(pizzeria.getMenu().get("PATATINE"), 1);
      tot++;
    });
    modPatatine.setOnAction(e->ModifyBox.display(order, pizzeria, patatine));
    VBox vBox4 = new VBox();
    vBox4.getChildren().addAll(addPatatine, modPatatine);
    HBox hBox4 = new HBox();
    hBox4.getChildren().addAll(patatine, patatineIngre, vBox4);

    Label wurstel= new Label(pizzeria.getMenu().get("WURSTEL").getNomeCamel());
    Label wurstelIngre = new Label(pizzeria.getMenu().get("WURSTEL").getDescrizione());
    Button addWurstel = new Button("Aggiungi al carrello");
    Button modWurstel = new Button(("Modifica"));
    addWurstel.setOnAction(e-> {
      order.addPizza(pizzeria.getMenu().get("WURSTEL"), 1);
      tot++;
    });
    modWurstel.setOnAction(e->ModifyBox.display(order, pizzeria, wurstel));
    VBox vBox5 = new VBox();
    vBox5.getChildren().addAll(addWurstel, modWurstel);
    HBox hBox5 = new HBox();
    hBox5.getChildren().addAll(wurstel, wurstelIngre, vBox5);

    Label alici= new Label(pizzeria.getMenu().get("CAPRICCIOSA").getNomeCamel());
    Label aliciIngre = new Label(pizzeria.getMenu().get("CAPRICCIOSA").getDescrizione());
    Button addAlici = new Button("Aggiungi al carrello");
    Button modAlici = new Button(("Modifica"));
    addAlici.setOnAction(e-> {
      order.addPizza(pizzeria.getMenu().get("CAPRICCIOSA"), 1);
      tot++;
    });
    modAlici.setOnAction(e->ModifyBox.display(order, pizzeria, alici));
    VBox vBox6= new VBox();
    vBox6.getChildren().addAll(addAlici, modAlici);
    HBox hBox6 = new HBox();
    hBox6.getChildren().addAll(alici, aliciIngre, vBox6);



    GridPane.setConstraints(hBox1, 1,1 );
    GridPane.setConstraints(hBox2, 1,2 );
    GridPane.setConstraints(hBox3, 1,3 );
    GridPane.setConstraints(hBox4, 1,4 );
    GridPane.setConstraints(hBox5, 1,5 );
    GridPane.setConstraints(hBox6, 1,6 );


    gridPane.getChildren().addAll(
            hBox1, hBox2, hBox3, hBox4, hBox5, hBox6
    );

    // Metto il gridPane con tutte le pizze all'interno di uno ScrollPane
    javafx.scene.control.ScrollPane scrollPane1 = new javafx.scene.control.ScrollPane(gridPane);
    scrollPane1.fitToHeightProperty();
    scrollPane1.fitToWidthProperty();


    HBox hBoxM = new HBox();

    // Definisco bottone per tornare indietro

    Button indietroButton = new Button("Torna indietro");
    indietroButton.setOnAction(e -> window.setScene(scene1));

    // Definisco bottone per andare avanti

    OrderPage2 orderPage2 = new OrderPage2();
    avantiButton = new Button("Prosegui");
    avantiButton.setOnAction(e -> {
      System.out.println("Sono state ordinate in tutto "+tot+" pizze.");
      orderPage2.display(window, scene1, order, pizzeria, tot);
    });

    hBoxM.getChildren().addAll(avantiButton, indietroButton);


    /*hBoxM.getChildren().addAll(indietroButton, avantiButton);
    BorderPane borderPane = new BorderPane();
    borderPane.getChildren().addAll(scrollPane1, hBoxM);
    borderPane.setTop(scrollPane1);
    borderPane.setBottom(hBoxM);*/
    VBox vBox = new VBox();
    vBox.getChildren().addAll(scrollPane1, hBoxM);

    Scene scene2;
    scene2 = new Scene(vBox, 430, 530);
    window.setScene(scene2);

  }

}
