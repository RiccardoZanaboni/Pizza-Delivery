package graphicElements;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.Customer;
import pizzeria.Pizzeria;
import pizzeria.Services;

public class WhoWeArePage {

    public void display(Stage window, Pizzeria pizzeria, Customer customer) {

        String history = Services.getHistory(true);
        TextArea textField=new TextArea();
        textField.setText(history);

        textField.setEditable(false);
        textField.prefWidthProperty().bind(window.widthProperty());
        textField.prefHeightProperty().bind(window.heightProperty());

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(textField);

        Button backButton = new Button("← Torna indietro");
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
            MenuPage menuPage = new MenuPage();
            menuPage.display(window, pizzeria, customer);
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(backButton);
        hBox.setAlignment(Pos.CENTER);

        VBox layout=new VBox();
        layout.getChildren().addAll(stackPane,hBox);
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());
        layout.setOnKeyPressed(ke -> {
            if(ke.getCode()== KeyCode.CONTROL||ke.getCode()== KeyCode.BACK_SPACE){
                backButton.fire();
            }
        });

        Scene scene5 = new Scene(layout);
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());
        scene5.getStylesheets().addAll(this.getClass().getResource("cssStyle/orderPage2.css").toExternalForm());
        //window.setResizable(false);
        window.setScene(scene5);
        window.setTitle("Wolf of Pizza");

        window.show();
    }
}
