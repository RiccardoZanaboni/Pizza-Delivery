package graphicElements.customerSidePages.loginPages;

import graphicElements.customerSidePages.HomePage;
import graphicElements.pizzeriaSidePages.PizzeriaHomePage;
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
import pizzeria.services.PizzeriaServices;

public class LoginAccountPage {
	/** Lo Stage ospita la pagina di Login.
	 * E' possibile inserire direttamente i dati per l'accesso, oppure
	 * passare alle pagine per richiedere la creazione di un nuovo account
	 * oppure per il recupero dei dati utente.
	 * */
	public void display(Stage window, Pizzeria pizzeria) {
		window.setTitle("Wolf of Pizza - Login");

		Label username = new Label(" Username:");
		TextField nameInput = new TextField();
		nameInput.setMinWidth(250);
		nameInput.setPromptText("Your Username");
		username.setId("nomiLabel");
		HBox usernameBox = new HBox(50);
		usernameBox.getChildren().addAll(username, nameInput);
		usernameBox.setAlignment(Pos.CENTER);

		Label password = new Label(" Password: ");
		PasswordField passwordInput = new PasswordField();
		passwordInput.setMinWidth(250);
		passwordInput.setPromptText("Your Password");
		password.setId("nomiLabel");
		HBox passwordBox = new HBox(50);
		passwordBox.getChildren().addAll(password, passwordInput);
		passwordBox.setAlignment(Pos.CENTER);

		Label insertErrorLabel = new Label("");
		insertErrorLabel.setId("errorLabel");

		Button loginButton = new Button("Login");
		loginButton.setMinSize(100, 50);
		loginButton.setOnAction(e-> {
				String user = nameInput.getText();
				String psw = passwordInput.getText();
				switch(PizzeriaServices.checkLogin(pizzeria, user.toUpperCase(),psw.toUpperCase())){
					case OK:
						HomePage homePage = new HomePage();
						Customer customer = new Customer(user.toUpperCase());
						homePage.display(window, pizzeria, customer);
						break;
					case PIZZERIA:
						PizzeriaHomePage pizzeriaHomePage = new PizzeriaHomePage();
						Stage stage=new Stage();
						pizzeriaHomePage.display(pizzeria, stage);
						break;
					default:
						insertErrorLabel.setTextFill(Color.DARKRED);
						insertErrorLabel.setText("Username o password errati");
						break;
				}
		});

		Button signUpButton = new Button("Nuovo Account");
		signUpButton.setMinSize(100, 50);
		signUpButton.setOnAction(e-> {
			NewAccountPage newAccountPage = new NewAccountPage();
			newAccountPage.display(window,pizzeria);
		});

		Button recoverPswButton = new Button("Recupera Password");
		recoverPswButton.setMinSize(100, 50);
		recoverPswButton.setOnAction(e-> {
			RecoverPswPage recoverPswPage = new RecoverPswPage();
			recoverPswPage.display(window, pizzeria);
		});

		HBox buttonBox = new HBox(50);
		VBox vBox = new VBox(20);
		buttonBox.getChildren().addAll(loginButton, signUpButton, recoverPswButton); //, backButton);
		buttonBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(insertErrorLabel, buttonBox);
		vBox.setAlignment(Pos.CENTER);

		VBox layout = new VBox(20);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(usernameBox, passwordBox, vBox);

		layout.setOnKeyPressed(ke -> {
			if(ke.getCode()== KeyCode.ENTER) {
				loginButton.fire();
			}
		});
		layout.getStyleClass().add("layout");

		Scene scene = new Scene(layout, 800, 600);
		window.setScene(scene);
		scene.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/loginPageStyle.css").toExternalForm());
		window.show();
	}
}
