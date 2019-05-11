package elementiGrafici;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.Order;
import pizzeria.Pizzeria;


public class OrderPage3 {

  public void display(Stage window, Order order, Pizzeria pizzeria, int tot, Scene scene3) {

      //FIXME MIGLIORARE LA PAGINA

      GridPane gridPane = new GridPane();
      gridPane.setPadding(new Insets(10, 10, 10, 10));
      gridPane.setVgap(10);
      gridPane.setHgap(30);
      VBox layout = new VBox();
      HBox hbox1=new HBox(10 );
      HBox hBox2=new HBox(10);
      HBox hBox3=new HBox(10);

      Label label = new Label("Il tuo ordine: SIG."+order.getCustomer().getUsername()+"\tINDIRIZZO: "+order.getIndirizzo()+"\tOrario "+order.getOrario());
      hbox1.getChildren().addAll(label);

      Label labelProducts = new Label("Prodotti");
      Label label1=new Label(order.recap());
      VBox vBox=new VBox();

      vBox.getChildren().addAll(labelProducts,label1);
      hBox2.getChildren().addAll(vBox);

      Label labelTot = new Label("Totale");
      Label label2=new Label(""+order.getTotaleCosto());
      hBox3.getChildren().addAll(labelTot,label2);

      GridPane.setConstraints(hbox1,0,1);
      GridPane.setConstraints(hBox2,0,2);
      GridPane.setConstraints(hBox3,1,3);


      Button indietroButton = new Button("Torna indietro ←");
      indietroButton.setOnAction(e -> window.setScene(scene3));
      Button closeButton = new Button("Fine ☓");
      closeButton.setOnAction(e-> {window.close();});

      HBox buttonBox = new HBox(10);
      buttonBox.getChildren().addAll(indietroButton, closeButton);
      buttonBox.setAlignment(Pos.CENTER);
      buttonBox.setMinSize(600, 50);

      gridPane.setMinSize(750, 500);
      buttonBox.setMinSize(750, 100);
      gridPane.getChildren().addAll(hbox1,hBox2,hBox3);
      layout.getChildren().addAll(gridPane,buttonBox);
      Scene scene4;
      scene4 = new Scene(layout, 750, 600);
      window.setScene(scene4);

  }
}
