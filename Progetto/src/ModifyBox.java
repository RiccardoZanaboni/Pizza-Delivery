import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.HashMap;

public class ModifyBox{
    Button confirmButton;
  static boolean answer=false;

    public static void setAnswer() {
        ModifyBox.answer= !ModifyBox.answer;
    }

  public static boolean display(Order order, Pizzeria pizzeria, Label pizza) {



    Pizza pizzaMenu = new Pizza(
            pizzeria.getMenu().get(pizza.getText().toUpperCase()).getNomeCamel(),
            pizzeria.getMenu().get(pizza.getText().toUpperCase()).getIngredienti(),
            pizzeria.getMenu().get(pizza.getText().toUpperCase()).getPrezzo());

    //Pizza nuovaPizza = new Pizza(pizzaMenu.getNomeCamel(), pizzaMenu.getIngredienti(), pizzaMenu.getPrezzo());
    HashMap<String, Ingredienti> ingr = new HashMap<>(pizzaMenu.getIngredienti());
    Pizza nuovaPizza = new Pizza(pizzaMenu.getNomeMaiusc(), ingr, pizzaMenu.getPrezzo());


    Stage window = new Stage();

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10, 10, 10, 10));
    gridPane.setVgap(8);
    gridPane.setHgap(10);

    // Meglio fare una funzione unica per gli add anzichè uno per uno

      Label alici = new Label("Alici");
      Button addAlici = new Button("Add");
      addAlici.setOnAction(e-> {
      Ingredienti ingrediente = Ingredienti.valueOf("ALICI");
      nuovaPizza.addIngredienti(ingrediente);
      nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
      System.out.println(nuovaPizza.getIngredienti());
      System.out.println(ingr.get("ALICI").toString());
    });
      Button removeAlici = new Button("Remove");
      HBox hBox1 = new HBox();
      hBox1.getChildren().addAll(addAlici, removeAlici);
      gridPane.getChildren().addAll(alici, hBox1);
      GridPane.setConstraints(alici, 1,1);
      GridPane.setConstraints(hBox1, 5,1);

      Label basilico = new Label("Basilico");
      Button addBasilico = new Button("Add");
      addBasilico.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("BASILICO");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
        });
      Button removeBasilico = new Button("Remove");
      HBox hBox2 = new HBox();
      hBox2.getChildren().addAll(addBasilico, removeBasilico);
      //pizzeria.getMenu().get(pizza.getText()).setPrezzo(pizzeria.getMenu().get(pizza.getText()).getPrezzo() + 0.50);
      gridPane.getChildren().addAll(basilico, hBox2);
      GridPane.setConstraints(basilico, 1,2);
      GridPane.setConstraints(hBox2, 5,2);

      Label capperi = new Label("CAPPERI");
      Button addCapperi = new Button("Add");
      addCapperi.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("CAPPERI");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("CAPPERI").toString());
      });
      Button removeCapperi = new Button("Remove");
      HBox hBox3 = new HBox();
      hBox3.getChildren().addAll(addCapperi, removeCapperi);
      gridPane.getChildren().addAll(capperi, hBox3);
      GridPane.setConstraints(capperi, 1,3);
      GridPane.setConstraints(hBox3, 5,3);

      Label carciofi = new Label("CARCIOFI");
      Button addCarciofi = new Button("Add");
      addCarciofi.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("CARCIOFI");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("CARCIOFI").toString());
      });
      Button removeCarciofi = new Button("Remove");
      HBox hBox4 = new HBox();
      hBox4.getChildren().addAll(addCarciofi, removeCarciofi);
      gridPane.getChildren().addAll(carciofi, hBox4);
      GridPane.setConstraints(carciofi, 1,4);
      GridPane.setConstraints(hBox4, 5,4);


      Label cotto = new Label("COTTO");
      Button addCotto = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("COTTO");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("COTTO").toString());
      });
      Button removeCotto = new Button("Remove");
      HBox hBox5 = new HBox();
      hBox5.getChildren().addAll(addCotto, removeCotto);
      gridPane.getChildren().addAll(cotto, hBox5);
      GridPane.setConstraints(cotto, 1,5);
      GridPane.setConstraints(hBox5, 5,5);


      Label crudo = new Label("CRUDO");
      Button addCrudo = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("CRUDO");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("CRUDO").toString());
      });
      Button removeCrudo = new Button("Remove");
      HBox hBox6 = new HBox();
      hBox6.getChildren().addAll(addCrudo, removeCrudo);
      gridPane.getChildren().addAll(crudo, hBox6);
      GridPane.setConstraints(crudo, 1,6);
      GridPane.setConstraints(hBox6, 5,6);

      Label funghi= new Label("FUNGHI");
      Button addFunghi = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("FUNGHI");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("FUNGHI").toString());
      });
      Button removeFunghi= new Button("Remove");
      HBox hBox7 = new HBox();
      hBox7.getChildren().addAll(addFunghi, removeFunghi);
      gridPane.getChildren().addAll(funghi, hBox7);
      GridPane.setConstraints(funghi, 1,7);
      GridPane.setConstraints(hBox7, 5,7);

      Label gorgonzola= new Label("GORGONZOLA");
      Button addGorgonzola = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("GORGONZOLA");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("GORGONZOLA").toString());
      });
      Button removeGorgonzola= new Button("Remove");
      HBox hBox8 = new HBox();
      hBox8.getChildren().addAll(addGorgonzola, removeGorgonzola);
      gridPane.getChildren().addAll(gorgonzola, hBox8);
      GridPane.setConstraints(gorgonzola, 1,8);
      GridPane.setConstraints(hBox8, 5,8);

      Label mozzarella= new Label("MOZZARELLA");
      Button addMozzarella = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("MOZZARELLA");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("MOZZARELLA").toString());
      });
      Button removeMozzarella = new Button("Remove");
      HBox hBox9 = new HBox();
      hBox9.getChildren().addAll(addMozzarella, removeMozzarella);
      gridPane.getChildren().addAll(mozzarella, hBox9);
      GridPane.setConstraints(mozzarella, 1,9);
      GridPane.setConstraints(hBox9, 5,9);

      Label mozzarellaDiBufala = new Label("MOZZARELLA_DI_BUFALA");
      Button addMozzarellaDiBufala = new Button("Add");
      addCarciofi.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("MOZZARELLA_DI_BUFALA");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("MOZZARELLA_DI_BUFALA").toString());
      });
      Button removeMozzarellaDiBufala= new Button("Remove");
      HBox hBox10 = new HBox();
      hBox10.getChildren().addAll(addMozzarellaDiBufala, removeMozzarellaDiBufala);
      gridPane.getChildren().addAll(mozzarellaDiBufala, hBox10);
      GridPane.setConstraints(mozzarellaDiBufala, 1,10);
      GridPane.setConstraints(hBox10, 5,10);

      Label oliveNere = new Label("OLIVE_NERE");
      Button addOliveNere = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("OLIVE_NERE");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("OLIVE_NERE").toString());
      });
      Button removeOliveNere = new Button("Remove");
      HBox hBox11 = new HBox();
      hBox11.getChildren().addAll(addOliveNere, removeOliveNere);
      gridPane.getChildren().addAll(oliveNere, hBox11);
      GridPane.setConstraints(oliveNere, 1,11);
      GridPane.setConstraints(hBox11, 5,11);

      Label origano = new Label("ORIGANO");
      Button addOrigano = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("ORIGANO");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("ORIGANO").toString());
      });
      Button removeOrigano = new Button("Remove");
      HBox hBox12 = new HBox();
      hBox12.getChildren().addAll(addOrigano, removeOrigano);
      gridPane.getChildren().addAll(origano, hBox12);
      GridPane.setConstraints(origano, 1,12);
      GridPane.setConstraints(hBox12, 5,12);

      Label panna= new Label("PANNA");
      Button addPanna = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("PANNA");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("PANNA").toString());
      });
      Button removePanna= new Button("Remove");
      HBox hBox13 = new HBox();
      hBox13.getChildren().addAll(addPanna, removePanna);
      gridPane.getChildren().addAll(panna, hBox13);
      GridPane.setConstraints(panna, 1,13);
      GridPane.setConstraints(hBox13, 5,13);

      Label patatine= new Label("PATATINE");
      Button addPatatine = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("PATATINE");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("PATATINE").toString());
      });
      Button removePatatine= new Button("Remove");
      HBox hBox14 = new HBox();
      hBox14.getChildren().addAll(addPatatine, removePatatine);
      gridPane.getChildren().addAll(patatine, hBox14);
      GridPane.setConstraints(patatine, 1,14);
      GridPane.setConstraints(hBox14, 5,14);

      Label peperoni= new Label("PEPERONI");
      Button addPeperoni = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("PEPERONI");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("PEPERONI").toString());
      });
      Button removePeperoni = new Button("Remove");
      HBox hBox15 = new HBox();
      hBox15.getChildren().addAll(addPeperoni, removePeperoni);
      gridPane.getChildren().addAll(peperoni, hBox15);
      GridPane.setConstraints(peperoni, 1,15);
      GridPane.setConstraints(hBox15, 5,15);

      // GALANTERIA, GEMME_DELL_INFINITO ONNIPOTENZA
      //  WURSTEL

      Label pomodorini = new Label("POMODORINI");
      Button addPomodorini = new Button("Add");
      addCapperi.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("POMODORINI");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("POMODORINI").toString());
      });
      Button removePomodorini = new Button("Remove");
      HBox hBox16 = new HBox();
      hBox16.getChildren().addAll(addPomodorini, removePomodorini);
      gridPane.getChildren().addAll(pomodorini, hBox16);
      GridPane.setConstraints(pomodorini, 1,16);
      GridPane.setConstraints(hBox16, 5,16);

      Label pomodoro = new Label("POMODORO");
      Button addPomodoro = new Button("Add");
      addCarciofi.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("POMODORO");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("POMODORO").toString());
      });
      Button removePomodoro = new Button("Remove");
      HBox hBox17 = new HBox();
      hBox17.getChildren().addAll(addPomodoro, removePomodoro);
      gridPane.getChildren().addAll(pomodoro, hBox17);
      GridPane.setConstraints(pomodoro, 1,17);
      GridPane.setConstraints(hBox17, 5,17);


      Label rucola = new Label("RUCOLA");
      Button addRucola = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("RUCOLA");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("RUCOLA").toString());
      });
      Button removeRucola = new Button("Remove");
      HBox hBox18 = new HBox();
      hBox18.getChildren().addAll(addRucola, removeRucola);
      gridPane.getChildren().addAll(rucola, hBox18);
      GridPane.setConstraints(rucola, 1,18);
      GridPane.setConstraints(hBox18, 5,18);


      Label salamePiccante = new Label("SALAME_PICCANTE");
      Button addSalamePiccante = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("SALAME_PICCANTE");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("SALAME_PICCANTE").toString());
      });
      Button removeSalamePiccante = new Button("Remove");
      HBox hBox19 = new HBox();
      hBox19.getChildren().addAll(addSalamePiccante, removeSalamePiccante);
      gridPane.getChildren().addAll(salamePiccante, hBox19);
      GridPane.setConstraints(salamePiccante, 1,19);
      GridPane.setConstraints(hBox19, 5,19);

      Label salsiccia= new Label("SALSICCIA");
      Button addSalsiccia = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("SALSICCIA");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("SALSICCIA").toString());
      });
      Button removeSalsiccia = new Button("Remove");
      HBox hBox20 = new HBox();
      hBox20.getChildren().addAll(addSalsiccia, removeSalsiccia);
      gridPane.getChildren().addAll(salsiccia, hBox20);
      GridPane.setConstraints(salsiccia, 1,20);
      GridPane.setConstraints(hBox20, 5,20);

      Label speck= new Label("SPECK");
      Button addSpeck = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("SPECK");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("SPECK").toString());
      });
      Button removeSpeck= new Button("Remove");
      HBox hBox21 = new HBox();
      hBox21.getChildren().addAll(addSpeck, removeSpeck);
      gridPane.getChildren().addAll(speck, hBox21);
      GridPane.setConstraints(speck, 1,21);
      GridPane.setConstraints(hBox21, 5,21);

      Label wurstel= new Label("WURSTEL");
      Button addWurstel = new Button("Add");
      addCotto.setOnAction(e-> {
          Ingredienti ingrediente = Ingredienti.valueOf("WURSTEL");
          nuovaPizza.addIngredienti(ingrediente);
          nuovaPizza.setPrezzo(nuovaPizza.getPrezzo() + 0.50);
          System.out.println(nuovaPizza.getIngredienti());
          System.out.println(ingr.get("WURSTEL").toString());
      });
      Button removeWurstel = new Button("Remove");
      HBox hBox22 = new HBox();
      hBox22.getChildren().addAll(addWurstel, removeWurstel);
      gridPane.getChildren().addAll(wurstel, hBox22);
      GridPane.setConstraints(wurstel, 1,22);
      GridPane.setConstraints(hBox22, 5,22);



    Button confirmButton = new Button("Conferma le modifiche");
    confirmButton.setOnAction(e -> {
      order.addPizza(nuovaPizza, 1);
      answer=true;
      window.close();
    });


    ScrollPane scrollPane = new ScrollPane(gridPane);
    VBox layout = new VBox();
    layout.getChildren().addAll(scrollPane, confirmButton);
    layout.setAlignment(Pos.CENTER);

    // Impedisce di fare azioni sulle altre finestre
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Modifica la pizza");
    window.setMinWidth(400);
    window.setMaxWidth(400);
    window.setMaxHeight(300);
    // Mostra la finestra e attende di essere chiusa
    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
    return answer;
  }


}
