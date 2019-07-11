package graphicElements.customerSidePages;

import graphicAlerts.GenericAlert;
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
import pizzeria.services.PizzeriaServices;

import java.awt.*;
import java.net.URI;

public class WhoWeArePage {

    public void display(Stage window, Pizzeria pizzeria, Customer customer) {

        String history = PizzeriaServices.getHistory(true);
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
            HomePage homePage = new HomePage();
            homePage.display(window, pizzeria, customer);
        });

        /*Button buttonVideo= new Button("Video Presentazione!!");
        buttonVideo.setId("confirmButton");
        buttonVideo.setOnAction(event ->  {
            //TODO: non funziona!!!
            try {
                Desktop.getDesktop().browse(new URI("https://drive.google.com/open?id=1IywtXGVTaywaYirjZSVLLV3KDOI1bBx-"));
            } catch (Exception e) {
                GenericAlert.display("Spiacenti: video di presentazione al momento non disponibile.");
            }
        });
        */
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(backButton/*,buttonVideo*/);
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
        scene5.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/orderPage2.css").toExternalForm());
        //window.setResizable(false);
        window.setScene(scene5);
        window.setTitle("Wolf of Pizza");

        window.show();
    }
}
