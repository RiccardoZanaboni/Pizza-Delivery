package graphicElements.customerSidePages.loginPages;

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
import pizzeria.pizzeriaSendMail.SendJavaMail;

public class RecoverPswPage {
	public void display(Stage window, Pizzeria pizzeria) {
		window.setTitle("Wolf of Pizza - Recupero Password");

		Label label = new Label(" Il tuo indirizzo e-mail:  ");
		TextField mailInput = new TextField();
		mailInput.setMinWidth(250);
		mailInput.setPromptText("Your e-Mail Address");
		label.setId("nomiLabel");
		HBox usernameBox = new HBox(50);
		usernameBox.getChildren().addAll(label, mailInput);
		usernameBox.setAlignment(Pos.CENTER);

		Label insertErrorLabel = new Label("");
		insertErrorLabel.setId("errorLabel");

		Button recoverPswButton = new Button("Recupera Password");
		recoverPswButton.setMinSize(100, 50);
		recoverPswButton.setOnAction(e-> {
			insertErrorLabel.setText("");		// fixme: non funziona (dovrebbe togliere la scritta "indirizzo non corretto", mentre sta inviando la mail).
			String mailAddress =  mailInput.getText();
			SendJavaMail newMail = new SendJavaMail();
			if(Database.checkMail(mailAddress)) {
				insertErrorLabel.setTextFill(Color.DARKRED);
				insertErrorLabel.setText("E-mail di recupero inviata");
				newMail.recoverPassword(mailAddress);
			} else {
				insertErrorLabel.setTextFill(Color.DARKRED);
				insertErrorLabel.setText("Indirizzo non corretto");
			}
		});

		Button backButton = new Button("← Torna indietro");
		backButton.setId("backButton");
		backButton.setOnAction(e -> {
			LoginAccountPage loginAccountPage = new LoginAccountPage();
			loginAccountPage.display(window, pizzeria);
		});

		HBox buttonBox = new HBox(50);
		VBox vBox = new VBox(20);
		buttonBox.getChildren().addAll(backButton, recoverPswButton);
		buttonBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(insertErrorLabel, buttonBox);
		vBox.setAlignment(Pos.CENTER);

		VBox layout = new VBox(20);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(usernameBox, vBox);

		layout.setOnKeyPressed(ke -> {
			if(ke.getCode()== KeyCode.ENTER) {
				recoverPswButton.fire();
			}
		});

		layout.getStyleClass().add("layout");

		Scene scene = new Scene(layout, 880, 600);
		window.setScene(scene);
		scene.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/loginPageStyle.css").toExternalForm());
		window.show();
	}

}
