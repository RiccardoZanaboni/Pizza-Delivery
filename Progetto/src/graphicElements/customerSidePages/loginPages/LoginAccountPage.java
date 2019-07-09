package graphicElements.customerSidePages.loginPages;

import graphicElements.customerSidePages.newOrder.MenuPage;
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

import java.sql.SQLException;

public class LoginAccountPage {
	public void display(Stage window, Pizzeria pizzeria) {

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
			try {
				String user = nameInput.getText();
				String psw = passwordInput.getText();
				switch(pizzeria.checkLogin(user.toUpperCase(),psw.toUpperCase())){
					case "OK":
						MenuPage menuPage = new MenuPage();
						Customer customer = new Customer(user.toUpperCase(), psw.toUpperCase());
						customer.setLoggedIn(true);     //TODO: da mettere anche in Textual
						menuPage.display(window, pizzeria, customer);
						break;
					case "P":
						PizzeriaHomePage pizzeriaHomePage = new PizzeriaHomePage();
						Stage window1 = new Stage();
						pizzeriaHomePage.display(pizzeria, window1);
						break;
					default:
						insertErrorLabel.setTextFill(Color.DARKRED);
						insertErrorLabel.setText("Username o password errati");
						break;
				}
			} catch (SQLException ignored) {}
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

		Scene scene = new Scene(layout, 880, 600);
		window.setScene(scene);
		scene.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/loginPageStyle.css").toExternalForm());
		window.show();
	}
}