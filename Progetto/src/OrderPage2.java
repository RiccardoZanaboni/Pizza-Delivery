import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OrderPage2 {
  private Scene scene3;
  private Button button;
  private String oraScelta, indirizzo, nome;


  public void display (Stage window, Scene scene1, Order order, Pizzeria pizzeria, int tot) {
    GridPane gridPane = new GridPane();

    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    //getItems returns the ObservableList object which you can add items to
    //choiceBox.getItems().addAll(getOrari(pizzeria, tot));

    //Set a default value
    //choiceBox.setValue("Apples");
    choiceBox.getItems().addAll(getOrari(pizzeria, tot));

    Label username = new Label("Username");
    GridPane.setConstraints(username, 0, 0);

    TextField nameInput = new TextField();
    nameInput.setPromptText("yourname");
    GridPane.setConstraints(nameInput, 1, 0);

    Label address = new Label("Indirizzo");
    GridPane.setConstraints(address, 0, 1);

    TextField addressInput = new TextField();
    addressInput.setPromptText("youraddress");      // questo è il modo giusto per un login
    GridPane.setConstraints(addressInput, 1, 1);

    Button loginButton = new Button("Prosegui");
    GridPane.setConstraints(loginButton, 3, 2);
    loginButton.setOnAction(e-> {
      nome = getName(nameInput);
      indirizzo = getAddress(addressInput);
      oraScelta = getChoice(choiceBox);
      order.setIndirizzo(getAddress(addressInput));
      Customer customer = new Customer(getName(nameInput));
      order.setCustomer(customer);

      OrderPage3 orderPage3 = new OrderPage3();
      orderPage3.display(window, order, pizzeria, tot);
    });

    GridPane.setConstraints(choiceBox, 0, 4);

    gridPane.getChildren().addAll(choiceBox, username, nameInput, address, addressInput, loginButton);

    scene3 = new Scene(gridPane, 430, 530);
    window.setScene(scene3);
  }

  public String getChoice(ChoiceBox<String> choiceBox) {
    String a = "L'ora scelta è:";
    String food = choiceBox.getValue();
    System.out.println(food);
    return a;
  }

  public String getAddress (TextField aInput) {
    String a = "";
    a+=aInput.getText();
    //System.out.println(a);
    return a;
  }

  public String getName (TextField nInput) {
    String a = "";
    a+=nInput.getText();
    //System.out.println(a);
    return a;
  }


  private ObservableList<String> getOrari (Pizzeria pizzeria, int tot) {
    ObservableList<String> orari = FXCollections.observableArrayList();

   // orari.addAll(pizzeria.orarioDisponibile(tot));

    orari.addAll("19:00", "19:05", "19:10", "19:15","19:20","19:25",
            "19:30","19:35","19:40","19:45","19:50","19:55"
    );

    return orari;
  }

}
