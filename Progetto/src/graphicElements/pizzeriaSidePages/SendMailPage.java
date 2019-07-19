package graphicElements.pizzeriaSidePages;

import database.Database;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pizzeria.Pizzeria;
import pizzeria.services.sendMail.SendJavaMail;

public class SendMailPage {
    public void display(Stage window, Pizzeria pizzeria) {
        window.setTitle("Wolf of Pizza - Invia Email");

        TextField mail = new TextField();
        HBox usernameBox = createHboxField("E-mail destinatario:  ","e-Mail",mail);

        TextField oggettoF = new TextField();
        HBox oggettoBox = createHboxField("Oggetto e-mail:  ", "oggetto", oggettoF);

        TextArea textArea = new TextArea();
        HBox textBox = createHboxArea(textArea);

        Label insertErrorLabel = new Label("");
        insertErrorLabel.setId("errorLabel");

        Button invioButton = createInvioButton(insertErrorLabel,mail,oggettoF,textArea);
        Button backButton = createBackButton(window,pizzeria);

        HBox buttonBox = new HBox(50);
        buttonBox.getChildren().addAll(backButton, invioButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(20);
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

    private Button createInvioButton(Label insertErrorLabel, TextField mail, TextField oggettoF, TextArea textArea) {
        Button invioButton = new Button("Invia");
        invioButton.setMinSize(100, 30);
        invioButton.setOnAction(e-> {
            insertErrorLabel.setText("");
            String mailAddress =  mail.getText();
            SendJavaMail newMail = new SendJavaMail();
            if(Database.checkMail(mailAddress)) {
                insertErrorLabel.setTextFill(Color.DARKRED);
                insertErrorLabel.setText("E-mail inviata");
                newMail.sendMail(mail.getText(),oggettoF.getText(),textArea.getText());
            } else {
                insertErrorLabel.setTextFill(Color.DARKRED);
                insertErrorLabel.setText("Indirizzo non corretto");
            }
        });
        return invioButton;
    }

    private Button createBackButton(Stage window, Pizzeria pizzeria){
        Button backButton = new Button("← Torna indietro");
        backButton.setMinSize(100, 30);
        backButton.setOnAction(e -> {
            PizzeriaHomePage pizzeriaHomePage = new PizzeriaHomePage();
            pizzeriaHomePage.display(pizzeria, window);
        });
        return backButton;
    }

    private HBox createHboxField(String textLabel,String text, TextField textField){
        Label label = new Label(textLabel);
        textField.setMinWidth(250);
        textField.setPromptText(text);
        HBox box = new HBox(50);
        box.getChildren().addAll(label, textField);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private HBox createHboxArea(TextArea textArea){
        Label label = new Label("Testo e-mail:  ");
        textArea.setMinWidth(250);
        textArea.setPrefSize(300,100);
        textArea.setPromptText("testo");
        HBox box = new HBox(50);
        box.getChildren().addAll(label, textArea);
        box.setAlignment(Pos.CENTER);
        return box;
    }


}
