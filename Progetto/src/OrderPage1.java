import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class OrderPage1 {

  static Scene scene2;
  private static int tot;
  static int countModifiche=0;

  private static Button avantiButton;

  public static void display(Stage window, Scene scene1, Scene scene3, Order order, Pizzeria pizzeria) {

        //FIXME SISTEMARE DISTANZA TRA BOTTONI ADD, REMOVE, MODIFICA DI UNA PIZZA E QUELLA SUCCESSIVA

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10, 10, 10, 10));
    gridPane.setVgap(8);
    gridPane.setHgap(10);

    Label countModificheLabel = new Label();
    countModificheLabel.setText(""+countModifiche);

    // Inserisco tutte le pizze

    Label margherita = new Label(pizzeria.getMenu().get("MARGHERITA").getNomeCamel());
    Label margheritaIngre = new Label(pizzeria.getMenu().get("MARGHERITA").getDescrizione());
    Label margheritaPrezzo = new Label(pizzeria.getMenu().get("MARGHERITA").getPrezzo()+"€");
    Button addMargherita = new Button("Aggiungi al carrello ");
    Button modMargherita = new Button(("Modifica"));
    Button rimuoviMargherita =new Button("rimuovi dal carrello");
    Label countMargheritaLabel= new Label();
    countMargheritaLabel.setText(""+pizzeria.getMenu().get("MARGHERITA").getCount());

    addMargherita.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(margherita.getText().toUpperCase()), 1);
      tot++;
      pizzeria.getMenu().get("MARGHERITA").setCount();
      countMargheritaLabel.setText(""+pizzeria.getMenu().get("MARGHERITA").getCount());
    } else
      AlertNumPizzeMax.display();
  });
    rimuoviMargherita.setOnAction(event -> {
        if(order.searchPizza(pizzeria.getMenu().get("MARGHERITA"))){
        order.getPizzeordinate().remove(pizzeria.getMenu().get("MARGHERITA"));
        tot--;
        pizzeria.getMenu().get("MARGHERITA").resetCount();
        countMargheritaLabel.setText(""+pizzeria.getMenu().get("MARGHERITA").getCount());
    }else
        AlertNumeroPizzeMin.display("MARGHERITA");
    });
    modMargherita.setOnAction(e-> {if(tot<16) {
        if (ModifyBox.display(order, pizzeria, margherita)) {
            tot++;
            countModifiche++;
            countModificheLabel.setText("" + countModifiche);
            ModifyBox.setAnswer();
        }
    }else
        AlertNumPizzeMax.display();
    });

    HBox hBox1 = new HBox(10);
    VBox vBox1 = new VBox(5);
    VBox vBox1A = new VBox(10);
    vBox1.getChildren().addAll(addMargherita, modMargherita, rimuoviMargherita);
    vBox1A.getChildren().addAll(margherita, margheritaIngre);
    hBox1.getChildren().addAll(margheritaPrezzo, vBox1);

    Label italia = new Label(pizzeria.getMenu().get("ITALIA").getNomeCamel());
    Label italiaIngre = new Label(pizzeria.getMenu().get("ITALIA").getDescrizione());
    Label italiaPrezzo = new Label(pizzeria.getMenu().get("ITALIA").getPrezzo()+"€");
    Button addItalia = new Button("Aggiungi al carrello");
    Button modItalia = new Button(( "Modifica"));
    Label countItaliaLabel= new Label();
    countItaliaLabel.setText(""+pizzeria.getMenu().get("ITALIA").getCount());
    addItalia.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(italia.getText().toUpperCase()), 1);
      tot++;
      pizzeria.getMenu().get("ITALIA").setCount();
      countItaliaLabel.setText(""+pizzeria.getMenu().get("ITALIA").getCount());
    } else
      AlertNumPizzeMax.display();
    });
    modItalia.setOnAction(e->{if(tot<16){
      if(ModifyBox.display(order, pizzeria, italia)) {
          tot++;
          countModifiche++;
          countModificheLabel.setText("" + countModifiche);
          ModifyBox.setAnswer();
      }
    }else
        AlertNumPizzeMax.display();
    });
    Button rimuoviItalia =new Button("rimuovi dal carrello");
    rimuoviItalia.setOnAction(event -> {
        if(order.searchPizza(pizzeria.getMenu().get("ITALIA"))){
            order.getPizzeordinate().remove(pizzeria.getMenu().get("ITALIA"));
            tot--;
            pizzeria.getMenu().get("ITALIA").resetCount();
            countItaliaLabel.setText(""+pizzeria.getMenu().get("ITALIA").getCount());
        }else
            AlertNumeroPizzeMin.display("ITALIA");
    });

    HBox hBox2 = new HBox(10);
    VBox vBox2 = new VBox(5);
    VBox vBox2A = new VBox(10);
    vBox2.getChildren().addAll(addItalia, modItalia,rimuoviItalia);
    vBox2A.getChildren().addAll(italia, italiaIngre);
    hBox2.getChildren().addAll(italiaPrezzo, vBox2);

    Label marinara= new Label(pizzeria.getMenu().get("MARINARA").getNomeCamel());
    Label marinaraIngre = new Label(pizzeria.getMenu().get("MARINARA").getDescrizione());
    Label marinaraPrezzo = new Label(pizzeria.getMenu().get("MARINARA").getPrezzo()+"€");
    Button addMarinara = new Button("Aggiungi al carrello");
    Button modMarinara = new Button(("Modifica"));
    Label countMarinaraLabel=new Label();
    countMarinaraLabel.setText(""+pizzeria.getMenu().get("MARINARA").getCount());
    addMarinara.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(marinara.getText().toUpperCase()), 1);
      tot++;
      pizzeria.getMenu().get("MARINARA").setCount();
      countMarinaraLabel.setText(""+pizzeria.getMenu().get("MARINARA").getCount());
    } else
      AlertNumPizzeMax.display();
    });
    modMarinara.setOnAction(e->{if(tot<16){
      if(ModifyBox.display(order, pizzeria, marinara)) {
          tot++;
          countModifiche++;
          countModificheLabel.setText("" + countModifiche);
          ModifyBox.setAnswer();
      }
    }else
        AlertNumPizzeMax.display();
    });
    Button rimuoviMarinara =new Button("rimuovi dal carrello");
    rimuoviMarinara.setOnAction(event -> {
        if(order.searchPizza(pizzeria.getMenu().get("MARINARA"))){
            order.getPizzeordinate().remove(pizzeria.getMenu().get("MARINARA"));
            tot--;
            pizzeria.getMenu().get("MARINARA").resetCount();
            countMarinaraLabel.setText(""+pizzeria.getMenu().get("MARINARA").getCount());
        }else
            AlertNumeroPizzeMin.display("MARINARA");
    });

    HBox hBox3 = new HBox(10);
    VBox vBox3 = new VBox(5);
    VBox vBox3A = new VBox(10);
    vBox3.getChildren().addAll(addMarinara, modMarinara,rimuoviMarinara);
    vBox3A.getChildren().addAll(marinara, marinaraIngre);
    hBox3.getChildren().addAll(marinaraPrezzo, vBox3);

    Label patatine= new Label(pizzeria.getMenu().get("PATATINE").getNomeCamel());
    Label patatineIngre = new Label(pizzeria.getMenu().get("PATATINE").getDescrizione());
    Label patatinePrezzo = new Label(pizzeria.getMenu().get("PATATINE").getPrezzo()+"€");
    Button addPatatine = new Button("Aggiungi al carrello");
    Button modPatatine = new Button(("Modifica"));
    Label countPatatineLabel=new Label();
    countPatatineLabel.setText(""+pizzeria.getMenu().get("PATATINE").getCount());
    addPatatine.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(patatine.getText().toUpperCase()), 1);
      tot++;
      pizzeria.getMenu().get("PATATINE").setCount();
      countPatatineLabel.setText(""+pizzeria.getMenu().get("PATATINE").getCount());
    } else
      AlertNumPizzeMax.display();
    });
    modPatatine.setOnAction(e->{if(tot<16){
      if(ModifyBox.display(order, pizzeria, patatine)){
          tot++;
          countModifiche++;
          countModificheLabel.setText("" + countModifiche);
          ModifyBox.setAnswer();
      }
    }else
                AlertNumPizzeMax.display();
    });
    Button rimuoviPatatine =new Button("rimuovi dal carrello");
    rimuoviPatatine.setOnAction(event -> {
        if(order.searchPizza(pizzeria.getMenu().get("PATATINE"))){
            order.getPizzeordinate().remove(pizzeria.getMenu().get("PATATINE"));
            tot--;
            pizzeria.getMenu().get("PATATINE").resetCount();
            countPatatineLabel.setText(""+pizzeria.getMenu().get("PATATINE").getCount());
        }else
            AlertNumeroPizzeMin.display("PATATINE");
     });

    HBox hBox4 = new HBox(10);
    VBox vBox4 = new VBox(5);
    VBox vBox4A = new VBox(10);
    vBox4.getChildren().addAll(addPatatine, modPatatine,rimuoviPatatine);
    vBox4A.getChildren().addAll(patatine, patatineIngre);
    hBox4.getChildren().addAll(patatinePrezzo, vBox4);

    Label wurstel= new Label(pizzeria.getMenu().get("WURSTEL").getNomeCamel());
    Label wurstelIngre = new Label(pizzeria.getMenu().get("WURSTEL").getDescrizione());
    Label wurstelPrezzo = new Label(pizzeria.getMenu().get("WURSTEL").getPrezzo()+"€");
    Button addWurstel = new Button("Aggiungi al carrello");
    Button modWurstel = new Button(("Modifica"));
    Label countWurstelLabel=new Label();
    countWurstelLabel.setText(""+pizzeria.getMenu().get("WURSTEL").getCount());
    addWurstel.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(wurstel.getText().toUpperCase()), 1);
      tot++;
      pizzeria.getMenu().get("WURSTEL").setCount();
      countWurstelLabel.setText(""+pizzeria.getMenu().get("WURSTEL").getCount());
    } else
      AlertNumPizzeMax.display();
    });
    modWurstel.setOnAction(e->{if(tot<16){
      if(ModifyBox.display(order, pizzeria, wurstel)){
          tot++;
          countModifiche++;
          countModificheLabel.setText("" + countModifiche);
          ModifyBox.setAnswer();
      }
    }else
                AlertNumPizzeMax.display();
    });
    Button rimuoviWurstel =new Button("rimuovi dal carrello");
    rimuoviWurstel.setOnAction(event -> {
        if(order.searchPizza(pizzeria.getMenu().get("WURSTEL"))){
            order.getPizzeordinate().remove(pizzeria.getMenu().get("WURSTEL"));
            tot--;
            pizzeria.getMenu().get("WURSTEL").resetCount();
            countWurstelLabel.setText(""+pizzeria.getMenu().get("WURSTEL").getCount());
        }else
            AlertNumeroPizzeMin.display("WURSTEL");
    });

    HBox hBox5 = new HBox(10);
    VBox vBox5 = new VBox(5);
    VBox vBox5A = new VBox(10);
    vBox5.getChildren().addAll(addWurstel, modWurstel,rimuoviWurstel);
    vBox5A.getChildren().addAll(wurstel, wurstelIngre);
    hBox5.getChildren().addAll(wurstelPrezzo, vBox5);


    Label capricciosa= new Label(pizzeria.getMenu().get("CAPRICCIOSA").getNomeCamel());
    Label capricciosaIngre = new Label(pizzeria.getMenu().get("CAPRICCIOSA").getDescrizione());
    Label capricciosaPrezzo = new Label(pizzeria.getMenu().get("CAPRICCIOSA").getPrezzo()+"€");
    Button addCapricciosa = new Button("Aggiungi al carrello");
    Button modCapricciosa = new Button(("Modifica"));
    Label countCapricciosaLabel=new Label();
    countCapricciosaLabel.setText(""+pizzeria.getMenu().get("CAPRICCIOSA").getCount());
    addCapricciosa.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(capricciosa.getText().toUpperCase()), 1);
      tot++;
      pizzeria.getMenu().get("CAPRICCIOSA").setCount();
      countCapricciosaLabel.setText(""+pizzeria.getMenu().get("CAPRICCIOSA").getCount());
    } else
      AlertNumPizzeMax.display();
    });
    modCapricciosa.setOnAction(e->{if(tot<16){
      if(ModifyBox.display(order, pizzeria, capricciosa)) {
          tot++;
          countModifiche++;
          countModificheLabel.setText("" + countModifiche);
          ModifyBox.setAnswer();
      }
    }else
        AlertNumPizzeMax.display();
    });
    Button rimuoviCapricciosa =new Button("rimuovi dal carrello");
    rimuoviCapricciosa.setOnAction(event -> {
        if(order.searchPizza(pizzeria.getMenu().get("CAPRICCIOSA"))){
            order.getPizzeordinate().remove(pizzeria.getMenu().get("CAPRICCIOSA"));
            tot--;
            pizzeria.getMenu().get("CAPRICCIOSA").resetCount();
            countCapricciosaLabel.setText(""+pizzeria.getMenu().get("CAPRICCIOSA").getCount());
        }else
            AlertNumeroPizzeMin.display("CAPRICCIOSA");
    });

    HBox hBox6 = new HBox(10);
    VBox vBox6 = new VBox(5);
    VBox vBox6A = new VBox(10);
    vBox6.getChildren().addAll(addCapricciosa, modCapricciosa,rimuoviCapricciosa);
    vBox6A.getChildren().addAll(capricciosa, capricciosaIngre);
    hBox6.getChildren().addAll(capricciosaPrezzo, vBox6);

    Label napoli = new Label(pizzeria.getMenu().get("NAPOLI").getNomeCamel());
    Label napoliIngre = new Label(pizzeria.getMenu().get("NAPOLI").getDescrizione());
    Label napoliPrezzo = new Label(pizzeria.getMenu().get("NAPOLI").getPrezzo()+"€");
    Button addNapoli = new Button("Aggiungi al carrello");
    Button modNapoli = new Button(("Modifica"));
    Label countNapoliLabel=new Label();
    countNapoliLabel.setText(""+pizzeria.getMenu().get("NAPOLI").getCount());
    addNapoli.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(napoli.getText().toUpperCase()), 1);
      tot++;
      pizzeria.getMenu().get("NAPOLI").setCount();
      countNapoliLabel.setText(""+pizzeria.getMenu().get("NAPOLI").getCount());
    } else
      AlertNumPizzeMax.display();
    });
    modNapoli.setOnAction(e->{if(tot<16){
      if(ModifyBox.display(order, pizzeria, napoli)){
          tot++;
          countModifiche++;
          countModificheLabel.setText("" + countModifiche);
          ModifyBox.setAnswer();
      }
    }else
                AlertNumPizzeMax.display();
    });
    Button rimuoviNapoli =new Button("rimuovi dal carrello");
    rimuoviNapoli.setOnAction(event -> {
        if(order.searchPizza(pizzeria.getMenu().get("NAPOLI"))){
            order.getPizzeordinate().remove(pizzeria.getMenu().get("NAPOLI"));
            tot--;
            pizzeria.getMenu().get("NAPOLI").resetCount();
            countNapoliLabel.setText(""+pizzeria.getMenu().get("NAPOLI").getCount());
        }else
            AlertNumeroPizzeMin.display("NAPOLI");
    });

    HBox hBox7 = new HBox(10);
    VBox vBox7 = new VBox(5);
    VBox vBox7A = new VBox(10);
    vBox7.getChildren().addAll(addNapoli, modNapoli,rimuoviNapoli);
    vBox7A.getChildren().addAll(napoli, napoliIngre);
    hBox7.getChildren().addAll(napoliPrezzo, vBox7);

    Label cotto = new Label(pizzeria.getMenu().get("COTTO").getNomeCamel());
    Label cottoIngre = new Label(pizzeria.getMenu().get("COTTO").getDescrizione());
    Label cottoPrezzo = new Label(pizzeria.getMenu().get("COTTO").getPrezzo()+"€");
    Button addCotto = new Button("Aggiungi al carrello");
    Button modCotto = new Button(("Modifica"));
    Label countCottoLabel=new Label();
    countCottoLabel.setText(""+pizzeria.getMenu().get("COTTO").getCount());
    addCotto.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(cotto.getText().toUpperCase()), 1);
      tot++;
      pizzeria.getMenu().get("COTTO").setCount();
      countCottoLabel.setText(""+pizzeria.getMenu().get("COTTO").getCount());

    } else
      AlertNumPizzeMax.display();
    });
    modCotto.setOnAction(e->{if(tot<16){
      if(ModifyBox.display(order, pizzeria, cotto)) {
          tot++;
          countModifiche++;
          countModificheLabel.setText("" + countModifiche);
          ModifyBox.setAnswer();
      }
    }else
                AlertNumPizzeMax.display();
    });
    Button rimuoviCotto =new Button("rimuovi dal carrello");
    rimuoviCotto.setOnAction(event -> {
        if(order.searchPizza(pizzeria.getMenu().get("COTTO"))){
            order.getPizzeordinate().remove(pizzeria.getMenu().get("COTTO"));
            tot--;
            pizzeria.getMenu().get("COTTO").resetCount();
            countCottoLabel.setText(""+pizzeria.getMenu().get("COTTO").getCount());
        }else
            AlertNumeroPizzeMin.display("COTTO");
    });

    HBox hBox8 = new HBox(10);
    VBox vBox8 = new VBox(5);
    VBox vBox8A = new VBox(10);
    vBox8.getChildren().addAll(addCotto, modCotto,rimuoviCotto);
    vBox8A.getChildren().addAll(cotto, cottoIngre);
    hBox8.getChildren().addAll(cottoPrezzo, vBox8);


    Label cottoFunghi = new Label(pizzeria.getMenu().get("COTTO E FUNGHI").getNomeCamel());
    Label cottoFunghiIngre = new Label(pizzeria.getMenu().get("COTTO E FUNGHI").getDescrizione());
    Label cottoFunghiPrezzo = new Label(pizzeria.getMenu().get("COTTO E FUNGHI").getPrezzo()+"€");
    Button addCottoFunghi = new Button("Aggiungi al carrello");
    Button modCottoFunghi = new Button(("Modifica"));
    Label countCottoFunghiLabel=new Label();
    countCottoFunghiLabel.setText(""+pizzeria.getMenu().get("COTTO E FUNGHI").getCount());
    addCottoFunghi.setOnAction(e->{if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(cottoFunghi.getText().toUpperCase()), 1);
      tot++;
      pizzeria.getMenu().get("COTTO E FUNGHI").setCount();
      countCottoFunghiLabel.setText(""+pizzeria.getMenu().get("COTTO E FUNGHI").getCount());
    } else
      AlertNumPizzeMax.display();
    });
    modCottoFunghi.setOnAction(e->{if(tot<16){
      if(ModifyBox.display(order, pizzeria, cottoFunghi)) {
          tot++;
          countModifiche++;
          countModificheLabel.setText("" + countModifiche);
          ModifyBox.setAnswer();
      }
    }else
                AlertNumPizzeMax.display();
    });
    Button rimuoviCottoFunghi =new Button("rimuovi dal carrello");
    rimuoviCottoFunghi.setOnAction(event -> {
        if(order.searchPizza(pizzeria.getMenu().get("COTTO E FUNGHI"))){
            order.getPizzeordinate().remove(pizzeria.getMenu().get("COTTO E FUNGHI"));
            tot--;
            pizzeria.getMenu().get("COTTO E FUNGHI").resetCount();
            countCottoFunghiLabel.setText(""+pizzeria.getMenu().get("COTTO E FUNGHI").getCount());
        }else
            AlertNumeroPizzeMin.display("COTTO E FUNGHI");
    });

    HBox hBox9 = new HBox(10);
    VBox vBox9 = new VBox(5);
    VBox vBox9A = new VBox(10);
    vBox9.getChildren().addAll(addCottoFunghi, modCottoFunghi,rimuoviCottoFunghi);
    vBox9A.getChildren().addAll(cottoFunghi, cottoFunghiIngre);
    hBox9.getChildren().addAll(cottoFunghiPrezzo, vBox9);

    Label americana = new Label(pizzeria.getMenu().get("AMERICANA").getNomeCamel());
    Label americanaIngre = new Label(pizzeria.getMenu().get("AMERICANA").getDescrizione());
    Label americanaPrezzo = new Label(pizzeria.getMenu().get("AMERICANA").getPrezzo()+"€");
    Button addAmericana = new Button("Aggiungi al carrello");
    Button modAmericana = new Button(("Modifica"));
    Label countAmericanaLabel=new Label();
    countAmericanaLabel.setText(""+pizzeria.getMenu().get("AMERICANA").getCount());
    addAmericana.setOnAction(e-> {if (tot<16) {
      order.addPizza(pizzeria.getMenu().get(americana.getText().toUpperCase()), 1);
      tot++;
      pizzeria.getMenu().get("AMERICANA").setCount();
      countAmericanaLabel.setText(""+pizzeria.getMenu().get("AMERICANA").getCount());
    } else
      AlertNumPizzeMax.display();
    });
    modAmericana.setOnAction(e->{if(tot<16){
      if(ModifyBox.display(order, pizzeria, americana)) {
          tot++;
          countModifiche++;
          countModificheLabel.setText("" + countModifiche);
          ModifyBox.setAnswer();
      }
    }else
                AlertNumPizzeMax.display();
    });
    Button rimuoviAmericana =new Button("rimuovi dal carrello");
    rimuoviAmericana.setOnAction(event -> {
        if(order.searchPizza(pizzeria.getMenu().get("AMERICANA"))){
            order.getPizzeordinate().remove(pizzeria.getMenu().get("AMERICANA"));
            tot--;
            pizzeria.getMenu().get("AMERICANA").resetCount();
            countAmericanaLabel.setText(""+pizzeria.getMenu().get("AMERICANA").getCount());
        }else
            AlertNumeroPizzeMin.display("AMERICANA");
    });

    HBox hBox10 = new HBox(10);
    VBox vBox10 = new VBox(5);
    VBox vBox10A = new VBox(10);
    vBox10.getChildren().addAll(addAmericana, modAmericana,rimuoviAmericana);
    vBox10A.getChildren().addAll(americana, americanaIngre);
    hBox10.getChildren().addAll(americanaPrezzo, vBox10);



//TODO AGGIUNGERE BOTTONE PER POTER TOGLIERE UNA PIZZA MODIFICATA

    Label modifiche=new Label();
    modifiche.setText("Pizze Modificate");
    HBox hBoxMod=new HBox();
    hBoxMod.getChildren().addAll(countModificheLabel,modifiche);

    // Definisco bottone per tornare indietro

    Button indietroButton = new Button("Torna indietro ←");
    indietroButton.setOnAction(e -> window.setScene(scene1));

    // Definisco bottone per andare avanti

    OrderPage2 orderPage2 = new OrderPage2();
    avantiButton = new Button("Prosegui  →");
    avantiButton.setOnAction(e -> {
      System.out.println("Sono state ordinate in tutto "+tot+" pizze.");
      System.out.println(order.getPizzeordinate());
      orderPage2.display(window, scene2, order, pizzeria, tot);
    });

    HBox hBoxIntestazione = new HBox();
    Label label = new Label("Ordine");
    hBoxIntestazione.getChildren().add(label);
    hBoxIntestazione.setMinSize(600, 50);
    hBoxIntestazione.setAlignment(Pos.CENTER);

    HBox hBoxButton = new HBox(10);
    hBoxButton.getChildren().addAll(indietroButton, avantiButton);
    hBoxButton.setMinSize(600, 50);
    hBoxButton.setAlignment(Pos.CENTER);

    //GridPane.setConstraints(hBoxIntestazione, 1, 0);

    GridPane.setConstraints(countMargheritaLabel, 0,1);
    GridPane.setConstraints(countItaliaLabel, 0,2);
    GridPane.setConstraints(countMarinaraLabel, 0,3);
    GridPane.setConstraints(countPatatineLabel, 0,4);
    GridPane.setConstraints(countWurstelLabel, 0,5);
    GridPane.setConstraints(countCapricciosaLabel, 0,6);
    GridPane.setConstraints(countNapoliLabel, 0,7);
    GridPane.setConstraints(countCottoLabel, 0,8);
    GridPane.setConstraints(countCottoFunghiLabel, 0,9);
    GridPane.setConstraints(countAmericanaLabel, 0,10);
    GridPane.setConstraints(countModificheLabel, 0,11);

    GridPane.setConstraints(vBox1A, 1,1);
    GridPane.setConstraints(vBox2A, 1,2);
    GridPane.setConstraints(vBox3A, 1,3);
    GridPane.setConstraints(vBox4A, 1,4);
    GridPane.setConstraints(vBox5A, 1,5);
    GridPane.setConstraints(vBox6A, 1,6);
    GridPane.setConstraints(vBox7A, 1,7);
    GridPane.setConstraints(vBox8A, 1,8);
    GridPane.setConstraints(vBox9A, 1,9);
    GridPane.setConstraints(vBox10A, 1,10);

    GridPane.setConstraints(hBox1, 2,1);
    GridPane.setConstraints(hBox2, 2,2);
    GridPane.setConstraints(hBox3, 2,3);
    GridPane.setConstraints(hBox4, 2,4);
    GridPane.setConstraints(hBox5, 2,5);
    GridPane.setConstraints(hBox6, 2,6);
    GridPane.setConstraints(hBox7, 2,7);
    GridPane.setConstraints(hBox8, 2,8);
    GridPane.setConstraints(hBox9, 2,9);
    GridPane.setConstraints(hBox10, 2,10);

    GridPane.setConstraints(hBoxMod,1,12);
    //GridPane.setConstraints(hBoxButton, 0,13);

    gridPane.getColumnConstraints().add(new ColumnConstraints(50));

    gridPane.getChildren().addAll(
            hBoxMod,
            countMargheritaLabel, countItaliaLabel, countMarinaraLabel, countPatatineLabel,countWurstelLabel,
            countCapricciosaLabel, countNapoliLabel, countCottoLabel, countCottoFunghiLabel, countAmericanaLabel,
            hBox1, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7, hBox8, hBox9, hBox10,
            vBox1A, vBox2A, vBox3A, vBox4A, vBox5A, vBox6A, vBox7A, vBox8A, vBox9A, vBox10A
    );

    // Metto il gridPane con tutte le pizze all'interno di uno ScrollPane
    javafx.scene.control.ScrollPane scrollPane1 = new javafx.scene.control.ScrollPane(gridPane);
    scrollPane1.fitToHeightProperty();
    scrollPane1.fitToWidthProperty();

    VBox layout = new VBox();
    layout.getChildren().addAll(hBoxIntestazione, scrollPane1, hBoxButton);


    //Scene scene2;
    scene2 = new Scene(layout, 600, 600);
    window.setScene(scene2);

  }

  /*public void addPizzaToOrder (Order order, Pizzeria pizzeria, Label label) {
    if (tot<4) {
      order.addPizza(pizzeria.getMenu().get(label.getText().toUpperCase()), 1);
      tot++;
      System.out.println(pizzeria.getMenu().get(label.getText().toUpperCase()));
    } else
      AlertNumPizzeMax.display();
  }*/

}






/*hBoxM.getChildren().addAll(indietroButton, avantiButton);
    BorderPane borderPane = new BorderPane();
    borderPane.getChildren().addAll(scrollPane1, hBoxM);
    borderPane.setTop(scrollPane1);
    borderPane.setBottom(hBoxM);
VBox vBox = new VBox();
vBox.getChildren().addAll(scrollPane1, hBoxM);*/