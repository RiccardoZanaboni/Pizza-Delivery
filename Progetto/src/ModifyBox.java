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


  public static boolean display(Order order, Pizzeria pizzeria, Label pizza) {



    HashMap<String, Ingredienti> ingr = new HashMap<>(pizzeria.getIngredientiPizzeria());
    Pizza pizzaMenu = new Pizza(
            pizzeria.getMenu().get(pizza.getText().toUpperCase()).getNomeCamel(),
            pizzeria.getMenu().get(pizza.getText().toUpperCase()).getIngredienti(),
            pizzeria.getMenu().get(pizza.getText().toUpperCase()).getPrezzo());

    Stage window = new Stage();

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10, 10, 10, 10));
    gridPane.setVgap(8);
    gridPane.setHgap(10);

    // Meglio fare una funzione unica per gli add anzichè uno per uno
    Label alici = new Label("Alici");
    Button addAlici = new Button("Add");
    addAlici.setOnAction(e-> {
      pizzaMenu.addIngredienti(ingr.get("ALICI"));
      System.out.println(pizzaMenu.getIngredienti());
    });
    Button removeAlici = new Button("Remove");
    HBox hBox1 = new HBox();
    hBox1.getChildren().addAll(addAlici, removeAlici);
    //pizzeria.getMenu().get(pizza.getText()).setPrezzo(pizzeria.getMenu().get(pizza.getText()).getPrezzo() + 0.50);
    gridPane.getChildren().addAll(alici, hBox1);
    GridPane.setConstraints(alici, 1,1);
    GridPane.setConstraints(hBox1, 5,1);

    Label basilico = new Label("Basilico");
    Button addBasilico = new Button("Add");
    addBasilico.setOnAction(e-> {
      pizzaMenu.addIngredienti(ingr.get("BASILICO"));
      System.out.println(pizzaMenu.getIngredienti());
    });
    Button removeBasilico = new Button("Remove");
    HBox hBox2 = new HBox();
    hBox2.getChildren().addAll(addBasilico, removeBasilico);
    //pizzeria.getMenu().get(pizza.getText()).setPrezzo(pizzeria.getMenu().get(pizza.getText()).getPrezzo() + 0.50);
    gridPane.getChildren().addAll(basilico, hBox2);
    GridPane.setConstraints(basilico, 1,2);
    GridPane.setConstraints(hBox2, 5,2);



    Button confirmButton = new Button("Conferma le modifiche");
    confirmButton.setOnAction(e -> {
      order.addPizza(pizzeria.getMenu().get(pizza.getText().toUpperCase()), 1);
      System.out.println();
      answer=true;
      //tot++;
      window.close();
    });

    VBox layout = new VBox(10);
    layout.getChildren().addAll(gridPane, confirmButton);
    layout.setAlignment(Pos.CENTER);

    // Impedisce di fare azioni sulle altre finestre
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Modifica la pizza");
    window.setMinWidth(250);
    // Mostra la finestra e attende di essere chiusa
    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();

    return answer;
  }


}
