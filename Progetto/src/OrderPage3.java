import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class OrderPage3 {

  public void display(Stage window, Order order, Pizzeria pizzeria, int tot) {
    Label label = new Label("Il tuo ordine");

    Label labelProducts = new Label("Prodotti");
    TextField textProducts = new TextField();
    textProducts.setText("Pizze ordinate");
    VBox vBox1 = new VBox();
    vBox1.getChildren().addAll(labelProducts, textProducts);

    Label labelTot = new Label("Totale");
    TextField textTot = new TextField();
    textTot.setText("Totale ordine");
    VBox vBox2 = new VBox();
    vBox2.getChildren().addAll(labelTot, textTot);

    HBox hBox = new HBox();
    hBox.getChildren().addAll(vBox1, vBox2);

    Scene scene4;
    scene4 = new Scene(hBox, 430, 530);
    window.setScene(scene4);

  }
}
