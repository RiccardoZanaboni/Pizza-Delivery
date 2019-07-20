package graphicElements.customerSidePages.loginPages;

import database.CustomerDB;
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

public class RecoverPswPage {

	/** Lo Stage ospita la pagina di Recupero password.
	 * Se l'indirizzo e-mail è effettivamente presente nel DB, una mail con i dati di accesso
	 * viene inviata all'utente, il quale potrà tornare alla pagina di login e accedere.
	 * */
	public void display(Stage window, Pizzeria pizzeria) {
		window.setTitle("Wolf of Pizza - Recupero Password");

		/* Campo indirizzo e-mail */
		Label label = new Label(" Il tuo indirizzo e-mail:  ");
		TextField mailInput = new TextField();
		mailInput.setMinWidth(250);
		mailInput.setPromptText("Your e-Mail Address");
		label.setId("nomiLabel");
		HBox usernameBox = new HBox(50);
		usernameBox.getChildren().addAll(label, mailInput);
		usernameBox.setAlignment(Pos.CENTER);

		/* Etichetta per informazioni */
		Label insertErrorLabel = new Label("");
		insertErrorLabel.setId("errorLabel");

		/* Bottone di Conferma */
		Button recoverPswButton = new Button("Recupera Password");
		recoverPswButton.setMinSize(100, 30);
		recoverPswButton.setOnAction(e-> {
			insertErrorLabel.setText("");
			String mailAddress =  mailInput.getText();
			SendJavaMail newMail = new SendJavaMail();
			if(CustomerDB.checkMail(mailAddress)) {
				insertErrorLabel.setTextFill(Color.DARKRED);
				insertErrorLabel.setText("E-mail di recupero inviata");
				newMail.recoverPassword(mailAddress);
			} else {
				insertErrorLabel.setTextFill(Color.DARKRED);
				insertErrorLabel.setText("Indirizzo non corretto");
			}
		});

		/* Bottone per tornare indietro */
		Button backButton = new Button("← Torna indietro");
		backButton.setId("backButton");
		recoverPswButton.setMinSize(100, 30);
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

		/* Layout */
		VBox layout = new VBox(20);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(usernameBox, vBox);

		layout.setOnKeyPressed(ke -> {
			if(ke.getCode()== KeyCode.ENTER) {
				recoverPswButton.fire();
			}
		});
		layout.getStyleClass().add("layout");

		Scene scene = new Scene(layout, 800, 600);
		window.setScene(scene);
		scene.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/loginPageStyle.css").toExternalForm());
		window.show();
	}
}