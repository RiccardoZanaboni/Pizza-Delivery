package graphicElements.pizzeriaSidePages;

import database.Database;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pizzeria.Pizzeria;
import pizzeria.services.sendMail.SendJavaMail;

public class PizzeriaMandaMail {
    public void display(Stage window, Pizzeria pizzeria) {
        window.setTitle("Wolf of Pizza - Invia Email");

        Label dest = new Label("E-mail destinatario:  ");
        TextField mail = new TextField();
        mail.setMinWidth(250);
        mail.setPromptText("e-Mail");
        HBox usernameBox = new HBox(50);
        usernameBox.getChildren().addAll(dest, mail);
        usernameBox.setAlignment(Pos.CENTER);

        Label oggetto = new Label("Oggetto e-mail :  ");
        TextField oggettoF = new TextField();
        oggettoF.setMinWidth(250);
        oggettoF.setPromptText("oggetto");
        HBox oggettoBox = new HBox(50);
        oggettoBox.getChildren().addAll(oggetto, oggettoF);
        oggettoBox.setAlignment(Pos.CENTER);

        Label text = new Label("Testo e-mail:  ");
        TextField textF = new TextField();
        textF.setPromptText("testo");
        textF.setPrefSize(300,40);
        HBox textBox = new HBox(50);
        textBox.getChildren().addAll(text, textF);
        textBox.setAlignment(Pos.CENTER);

        Label insertErrorLabel = new Label("");
        insertErrorLabel.setId("errorLabel");

        Button invioButton = new Button("Invia");
        invioButton.setMinSize(100, 30);
        invioButton.setOnAction(e-> {
            insertErrorLabel.setText("");
            String mailAddress =  mail.getText();
            SendJavaMail newMail = new SendJavaMail();
            if(Database.checkMail(mailAddress)) {
                insertErrorLabel.setTextFill(Color.DARKRED);
                insertErrorLabel.setText("E-mail inviata");
                newMail.sendMail(mail.getText(),oggettoF.getText(),textF.getText());
            } else {
                insertErrorLabel.setTextFill(Color.DARKRED);
                insertErrorLabel.setText("Indirizzo non corretto");
            }
        });

        Button backButton = new Button("← Torna indietro");
        backButton.setMinSize(100, 30);
        backButton.setOnAction(e -> {
            PizzeriaHomePage pizzeriaHomePage = new PizzeriaHomePage();
            pizzeriaHomePage.display(pizzeria, window);
        });

        HBox buttonBox = new HBox(50);
        VBox vBox = new VBox(20);
        buttonBox.getChildren().addAll(backButton, invioButton);
        buttonBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(insertErrorLabel, buttonBox);
        vBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(usernameBox,oggettoBox,textBox, vBox);

        layout.setOnKeyPressed(ke -> {
            if(ke.getCode()== KeyCode.ENTER) {
                invioButton.fire();
            }
        });

        Scene scene = new Scene(layout, 800, 600);
        window.setScene(scene);
        window.show();
    }

}
