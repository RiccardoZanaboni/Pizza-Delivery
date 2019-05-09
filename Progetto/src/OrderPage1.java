import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

import java.awt.event.ActionEvent;

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
    addMargherita.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(margherita.getText().toUpperCase()), 1);
      tot++;
    } else
      AlertBox.display();
  });
    modMargherita.setOnAction(e-> {
      if(ModifyBox.display(order, pizzeria, margherita))
        tot++;
    });
    VBox vBox1 = new VBox();
    vBox1.getChildren().addAll(addMargherita, modMargherita);
    HBox hBox1 = new HBox();
    hBox1.getChildren().addAll(margherita, margheritaIngre, vBox1);
    hBox1.setSpacing(10);

    Label italia = new Label(pizzeria.getMenu().get("ITALIA").getNomeCamel());
    Label italiaIngre = new Label(pizzeria.getMenu().get("ITALIA").getDescrizione());
    Button addItalia = new Button("Aggiungi al carrello");
    Button modItalia = new Button(("Modifica"));
    addItalia.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(italia.getText().toUpperCase()), 1);
      tot++;
    } else
      AlertBox.display();
    });
    modItalia.setOnAction(e->{
      if(ModifyBox.display(order, pizzeria, italia))
        tot++;
    });
    VBox vBox2 = new VBox();
    vBox2.getChildren().addAll(addItalia, modItalia);
    HBox hBox2 = new HBox();
    hBox2.getChildren().addAll(italia, italiaIngre, vBox2);
    hBox2.setSpacing(10);

    Label marinara= new Label(pizzeria.getMenu().get("MARINARA").getNomeCamel());
    Label marinaraIngre = new Label(pizzeria.getMenu().get("MARINARA").getDescrizione());
    Button addMarinara = new Button("Aggiungi al carrello");
    Button modMarinara = new Button(("Modifica"));
    addMarinara.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(marinara.getText().toUpperCase()), 1);
      tot++;
    } else
      AlertBox.display();
    });
    modMarinara.setOnAction(e->{
      if(ModifyBox.display(order, pizzeria, marinara))
        tot++;
    });
    VBox vBox3 = new VBox();
    vBox3.getChildren().addAll(addMarinara, modMarinara);
    HBox hBox3 = new HBox();
    hBox3.getChildren().addAll(marinara, marinaraIngre, vBox3);
    hBox3.setSpacing(10);

    Label patatine= new Label(pizzeria.getMenu().get("PATATINE").getNomeCamel());
    Label patatineIngre = new Label(pizzeria.getMenu().get("PATATINE").getDescrizione());
    Button addPatatine = new Button("Aggiungi al carrello");
    Button modPatatine = new Button(("Modifica"));
    addPatatine.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(patatine.getText().toUpperCase()), 1);
      tot++;
    } else
      AlertBox.display();
    });
    modPatatine.setOnAction(e->{
      if(ModifyBox.display(order, pizzeria, patatine))
        tot++;
    });
    VBox vBox4 = new VBox();
    vBox4.getChildren().addAll(addPatatine, modPatatine);
    HBox hBox4 = new HBox();
    hBox4.getChildren().addAll(patatine, patatineIngre, vBox4);
    hBox4.setSpacing(10);

    Label wurstel= new Label(pizzeria.getMenu().get("WURSTEL").getNomeCamel());
    Label wurstelIngre = new Label(pizzeria.getMenu().get("WURSTEL").getDescrizione());
    Button addWurstel = new Button("Aggiungi al carrello");
    Button modWurstel = new Button(("Modifica"));
    addWurstel.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(wurstel.getText().toUpperCase()), 1);
      tot++;
    } else
      AlertBox.display();
    });
    modWurstel.setOnAction(e->{
      if(ModifyBox.display(order, pizzeria, wurstel))
        tot++;
    });
    VBox vBox5 = new VBox();
    vBox5.getChildren().addAll(addWurstel, modWurstel);
    HBox hBox5 = new HBox();
    hBox5.getChildren().addAll(wurstel, wurstelIngre, vBox5);
    hBox5.setSpacing(10);

    Label capricciosa= new Label(pizzeria.getMenu().get("CAPRICCIOSA").getNomeCamel());
    Label capricciosaIngre = new Label(pizzeria.getMenu().get("CAPRICCIOSA").getDescrizione());
    Button addCapricciosa = new Button("Aggiungi al carrello");
    Button modCapricciosa = new Button(("Modifica"));
    addCapricciosa.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(capricciosa.getText().toUpperCase()), 1);
      tot++;
    } else
      AlertBox.display();
    });
    modCapricciosa.setOnAction(e->{
      if(ModifyBox.display(order, pizzeria, capricciosa))
        tot++;
    });
    VBox vBox6= new VBox();
    vBox6.getChildren().addAll(addCapricciosa, modCapricciosa);
    HBox hBox6 = new HBox();
    hBox6.getChildren().addAll(capricciosa, capricciosaIngre, vBox6);
    hBox6.setSpacing(10);

    Label napoli = new Label(pizzeria.getMenu().get("NAPOLI").getNomeCamel());
    Label napoliIngre = new Label(pizzeria.getMenu().get("NAPOLI").getDescrizione());
    Button addNapoli = new Button("Aggiungi al carrello");
    Button modNapoli = new Button(("Modifica"));
    addNapoli.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(napoli.getText().toUpperCase()), 1);
      tot++;
    } else
      AlertBox.display();
    });
    modNapoli.setOnAction(e->{
      if(ModifyBox.display(order, pizzeria, napoli))
        tot++;
    });
    VBox vBox7= new VBox();
    vBox7.getChildren().addAll(addNapoli, modNapoli);
    HBox hBox7 = new HBox();
    hBox7.getChildren().addAll(napoli, napoliIngre, vBox7);
    hBox7.setSpacing(10);

    Label cotto = new Label(pizzeria.getMenu().get("COTTO").getNomeCamel());
    Label cottoIngre = new Label(pizzeria.getMenu().get("COTTO").getDescrizione());
    Button addCotto = new Button("Aggiungi al carrello");
    Button modCotto = new Button(("Modifica"));
    addCotto.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(cotto.getText().toUpperCase()), 1);
      tot++;
    } else
      AlertBox.display();
    });
    modCotto.setOnAction(e->{
      if(ModifyBox.display(order, pizzeria, cotto))
        tot++;
    });
    VBox vBox8= new VBox();
    vBox8.getChildren().addAll(addCotto, modCotto);
    HBox hBox8 = new HBox();
    hBox8.getChildren().addAll(cotto, cottoIngre, vBox8);
    hBox8.setSpacing(10);

    Label cottoFunghi = new Label(pizzeria.getMenu().get("COTTO E FUNGHI").getNomeCamel());
    Label cottoFunghiIngre = new Label(pizzeria.getMenu().get("COTTO E FUNGHI").getDescrizione());
    Button addCottoFunghi = new Button("Aggiungi al carrello");
    Button modCottoFunghi = new Button(("Modifica"));
    addCottoFunghi.setOnAction(e->{if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(cottoFunghi.getText().toUpperCase()), 1);
      tot++;
    } else
      AlertBox.display();
    });
    modCottoFunghi.setOnAction(e->{
      if(ModifyBox.display(order, pizzeria, cottoFunghi))
        tot++;
    });
    VBox vBox9= new VBox();
    vBox9.getChildren().addAll(addCottoFunghi, modCottoFunghi);
    HBox hBox9 = new HBox();
    hBox9.getChildren().addAll(cottoFunghi, cottoFunghiIngre, vBox9);
    hBox9.setSpacing(10);

    Label americana = new Label(pizzeria.getMenu().get("AMERICANA").getNomeCamel());
    Label americanaIngre = new Label(pizzeria.getMenu().get("AMERICANA").getDescrizione());
    Button addAmericana = new Button("Aggiungi al carrello");
    Button modAmericana = new Button(("Modifica"));
    addAmericana.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(americana.getText().toUpperCase()), 1);
      tot++;
    } else
      AlertBox.display();
    });
    modAmericana.setOnAction(e->{
      if(ModifyBox.display(order, pizzeria, cottoFunghi))
        tot++;
    });
    VBox vBox10= new VBox();
    vBox10.getChildren().addAll(addAmericana, modAmericana);
    HBox hBox10 = new HBox();
    hBox10.getChildren().addAll(americana, americanaIngre, vBox10);
    hBox10.setSpacing(10);


    // Definisco bottone per tornare indietro

    Button indietroButton = new Button("Torna indietro");
    indietroButton.setOnAction(e -> window.setScene(scene1));

    // Definisco bottone per andare avanti

    OrderPage2 orderPage2 = new OrderPage2();
    avantiButton = new Button("Prosegui");
    avantiButton.setOnAction(e -> {
      System.out.println("Sono state ordinate in tutto "+tot+" pizze.");
      System.out.println(order.getPizzeordinate());
      orderPage2.display(window, scene1, order, pizzeria, tot);
    });


    HBox hBoxButton = new HBox();
    hBoxButton.getChildren().addAll(indietroButton, avantiButton);
    hBoxButton.setAlignment(Pos.BASELINE_CENTER);

    GridPane.setConstraints(hBox1, 0,1);
    GridPane.setConstraints(hBox2, 0,2);
    GridPane.setConstraints(hBox3, 0,3);
    GridPane.setConstraints(hBox4, 0,4);
    GridPane.setConstraints(hBox5, 0,5);
    GridPane.setConstraints(hBox6, 0,6);
    GridPane.setConstraints(hBox7, 0,7);
    GridPane.setConstraints(hBox8, 0,8);
    GridPane.setConstraints(hBox9, 0,9);
    GridPane.setConstraints(hBox10, 0,10);

    GridPane.setConstraints(hBoxButton, 0,11);

    gridPane.getChildren().addAll(
            hBox1, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7, hBox8, hBox9, hBox10, hBoxButton
    );

    // Metto il gridPane con tutte le pizze all'interno di uno ScrollPane
    javafx.scene.control.ScrollPane scrollPane1 = new javafx.scene.control.ScrollPane(gridPane);
    scrollPane1.fitToHeightProperty();
    scrollPane1.fitToWidthProperty();


    Scene scene2;
    scene2 = new Scene(scrollPane1, 430, 530);
    window.setScene(scene2);

  }

  /*public void addPizzaToOrder (Order order, Pizzeria pizzeria, Label label) {
    if (tot<4) {
      order.addPizza(pizzeria.getMenu().get(label.getText().toUpperCase()), 1);
      tot++;
      System.out.println(pizzeria.getMenu().get(label.getText().toUpperCase()));
    } else
      AlertBox.display();
  }*/

}






/*hBoxM.getChildren().addAll(indietroButton, avantiButton);
    BorderPane borderPane = new BorderPane();
    borderPane.getChildren().addAll(scrollPane1, hBoxM);
    borderPane.setTop(scrollPane1);
    borderPane.setBottom(hBoxM);
VBox vBox = new VBox();
vBox.getChildren().addAll(scrollPane1, hBoxM);*/