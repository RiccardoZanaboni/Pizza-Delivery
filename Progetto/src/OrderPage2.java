import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class OrderPage2 {
  private Scene scene3;
  private Button button;
  private String oraScelta, indirizzo, nome;


  public void display (Stage window, Scene scene2, Order order, Pizzeria pizzeria, int tot) {
    GridPane gridPane = new GridPane();


    //choiceBox.getItems().addAll(getOrari(pizzeria, tot));

    //Set a default value
    //choiceBox.setValue("");

    Label username = new Label("Username");
    TextField nameInput = new TextField();
    nameInput.setPromptText("Your name");
    HBox usernameBox = new HBox(50);
    usernameBox.getChildren().addAll(username, nameInput);

    Label address = new Label("Indirizzo");
    TextField addressInput = new TextField();
    addressInput.setPromptText("Your address");
    HBox addressBox = new HBox(61);
    addressBox.getChildren().addAll(address, addressInput);

    Label choiceLabel = new Label("Scegli l'ora");
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    choiceBox.getItems().addAll(getOrari(pizzeria, tot));
    HBox choiceHBox = new HBox(44);
    choiceHBox.getChildren().addAll(choiceLabel, choiceBox);


    Button nextPageButton = new Button("Prosegui →");

    nextPageButton.setOnAction(e-> {
      nome = getName(nameInput);
      indirizzo = getAddress(addressInput);
      oraScelta = getChoice(choiceBox);
      order.setIndirizzo(getAddress(addressInput));
      Customer customer = new Customer(getName(nameInput));
      order.setCustomer(customer);

      OrderPage3 orderPage3 = new OrderPage3();
      orderPage3.display(window, order, pizzeria, tot);
    });

    Button goBackButton = new Button("Torna indietro ←");
    goBackButton.setOnAction(e -> window.setScene(scene2));

    HBox buttonBox = new HBox(10);
    buttonBox.getChildren().addAll(goBackButton, nextPageButton);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setMinSize(600, 50);

    HBox hBoxIntestazione = new HBox();
    Label label = new Label("Inserisci i tuoi dati");
    hBoxIntestazione.getChildren().add(label);
    hBoxIntestazione.setMinSize(600, 50);
    hBoxIntestazione.setAlignment(Pos.CENTER);

    //GridPane.setConstraints(username, 0 , 0);
    //GridPane.setConstraints(nameInput, 1, 0);
    GridPane.setConstraints(usernameBox, 0, 0);
    GridPane.setConstraints(addressBox, 0, 1);
    GridPane.setConstraints(choiceHBox, 0, 2);
    gridPane.getChildren().addAll(usernameBox, addressBox, choiceHBox);
    gridPane.setMinSize(600, 500);
    gridPane.setPadding(new Insets(150, 10, 10, 180));
    gridPane.setVgap(20);
    //gridPane.setHgap(150);


    VBox layout = new VBox();
    layout.getChildren().addAll(hBoxIntestazione, gridPane, buttonBox);

    scene3 = new Scene(layout, 600, 600);
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

    orari.addAll(pizzeria.orarioDisponibile(tot));

    /*orari.addAll("19:00", "19:05", "19:10", "19:15","19:20","19:25",
            "19:30","19:35","19:40","19:45","19:50","19:55"
    );*/

    return orari;
  }

}
