package graphicElements.customerSidePages.loginPages;

import database.CustomerDB;
import graphicElements.customerSidePages.HomePage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pizzeria.Customer;
import pizzeria.Pizzeria;
import pizzeria.services.sendMail.SendJavaMail;
import pizzeria.services.PizzeriaServices;

public class NewAccountPage {

	/** Lo Stage ospita la pagina di creazione di un nuovo account.
	 * Una volta inseriti i dati in modo corretto, il login viene effettuato automaticamente.
	 * In alternativa viene stampato un messaggio di errore.
	 * */
	public void display(Stage window, Pizzeria pizzeria) {
		window.setTitle("Wolf of Pizza - Nuovo Account");

		/* Campo Username */
		Label username = new Label(" Username:");
		TextField nameInput = new TextField();
		nameInput.setMinWidth(250);
		nameInput.setPromptText("Your Name");
		username.setId("nomiLabel");
		HBox usernameBox = new HBox(50);
		usernameBox.getChildren().addAll(username, nameInput);
		usernameBox.setAlignment(Pos.CENTER);

		/* Campo Password */
		Label password = new Label(" Password: ");
		PasswordField passwordInput = new PasswordField();
		passwordInput.setMinWidth(250);
		passwordInput.setPromptText("Your Password");
		password.setId("nomiLabel");
		HBox passwordBox = new HBox(50);
		passwordBox.getChildren().addAll(password, passwordInput);
		passwordBox.setAlignment(Pos.CENTER);

		/* Campo Verifica Password */
		Label password2 = new Label(" Conferma: ");
		PasswordField passwordInput2 = new PasswordField();
		passwordInput2.setMinWidth(250);
		passwordInput2.setPromptText("Confirm Password");
		password2.setId("nomiLabel");
		HBox passwordBox2 = new HBox(50);
		passwordBox2.getChildren().addAll(password2, passwordInput2);
		passwordBox2.setAlignment(Pos.CENTER);

		/* Campo indirizzo e-mail */
		Label mail = new Label(" E-mail:\t  ");
		TextField mailInput = new TextField();
		mailInput.setMinWidth(250);
		mailInput.setPromptText("Your e-Mail Address");
		mail.setId("nomiLabel");
		HBox mailBox = new HBox(50);
		mailBox.getChildren().addAll(mail, mailInput);
		mailBox.setAlignment(Pos.CENTER);

		/* Campo etichetta per informazioni */
		Label insertErrorLabel = new Label("");
		insertErrorLabel.setId("errorLabel");

		/* Bottone di Conferma */
		Button signUpButton = new Button("Registrati");
		signUpButton.setMinSize(100, 35);
		signUpButton.setOnAction(e->{
			switch(PizzeriaServices.canCreateAccount(mailInput.getText(),nameInput.getText(),passwordInput.getText(),passwordInput2.getText())){
				case OK:
					SendJavaMail newMail = new SendJavaMail();
					boolean isCorrectAddress = newMail.welcomeMail(nameInput.getText(),passwordInput.getText(),mailInput.getText());
					if(!isCorrectAddress){
						insertErrorLabel.setTextFill(Color.DARKRED);
						insertErrorLabel.setText("Indirizzo e-mail inesistente");
					} else {
						CustomerDB.putCustomer(nameInput.getText().toUpperCase(), passwordInput.getText(), mailInput.getText());
						HomePage homePage = new HomePage();
						Customer customer = new Customer(nameInput.getText().toUpperCase());
						homePage.display(window, pizzeria, customer);
					}
					break;
				case SHORT:
					insertErrorLabel.setTextFill(Color.DARKRED);
					insertErrorLabel.setText("Username o password troppo brevi");
					break;
				case EXISTING:
					insertErrorLabel.setTextFill(Color.DARKRED);
					insertErrorLabel.setText("Utente già registrato");
					break;
				case DIFFERENT:
					insertErrorLabel.setTextFill(Color.DARKRED);
					insertErrorLabel.setText("Password non coincidente");
					break;
			}
		});

		/* Bottone per tornare alla pagina di login */
		Button backButton = createBackButton(pizzeria, window);
        backButton.setMinHeight(35);

        HBox buttonBox = new HBox(50);
		VBox vBox = new VBox(20);
		buttonBox.getChildren().addAll(backButton,signUpButton);
		buttonBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(insertErrorLabel, buttonBox);
		vBox.setAlignment(Pos.CENTER);

		/* Layout */
		VBox layout = new VBox(20);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(usernameBox, passwordBox, passwordBox2, mailBox, vBox);

		layout.setOnKeyPressed(ke -> {
			if(ke.getCode()== KeyCode.ENTER) {
				signUpButton.fire();
			}
		});
		layout.getStyleClass().add("layout");

		Scene scene = new Scene(layout, 800, 600);
		window.setScene(scene);
		scene.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/loginPageStyle.css").toExternalForm());
		window.show();
	}

	/** @return il bottone per tornare alla pagina di Login. */
	private Button createBackButton(Pizzeria pizzeria, Stage window) {
		Button backButton = new Button("← Torna indietro");
		backButton.setId("backButton");
		backButton.setOnAction(e -> {
			LoginAccountPage loginAccountPage = new LoginAccountPage();
			loginAccountPage.display(window, pizzeria);
		});
		return backButton;
	}
}
