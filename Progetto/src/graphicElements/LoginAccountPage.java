package graphicElements;

import graphicElements.PizzeriaPages.PizzeriaHomePage;
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

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class LoginAccountPage {
	public void display(Stage window, Pizzeria pizzeria) {

		Label username = new Label(" Nome:\t  ");
		TextField nameInput = new TextField();
		nameInput.setPromptText("Your Name");
		username.setId("nomiLabel");
		HBox usernameBox = new HBox(50);
		usernameBox.getChildren().addAll(username, nameInput);
		usernameBox.setAlignment(Pos.CENTER);

		Label password = new Label(" Password: ");
		PasswordField passwordInput = new PasswordField();
		passwordInput.setPromptText("Your Password");
		password.setId("nomiLabel");
		HBox passwordBox = new HBox(50);
		passwordBox.getChildren().addAll(password, passwordInput);
		passwordBox.setAlignment(Pos.CENTER);

		Label insertErrorLabel = new Label("");
		insertErrorLabel.setId("errorLabel");

		Button loginButton = new Button("Login");
		loginButton.setMinSize(100, 50);
		loginButton.setOnAction(e->{
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

		Button backButton = createBackButton(pizzeria, window);

		HBox buttonBox = new HBox(50);
		VBox vBox = new VBox(20);
		buttonBox.getChildren().addAll(backButton,loginButton);
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
		scene.getStylesheets().addAll(this.getClass().getResource("cssStyle/loginPageStyle.css").toExternalForm());
		window.show();
	}

	private Button createBackButton(Pizzeria pizzeria, Stage window) {
		Button backButton = new Button("← Torna indietro");
		backButton.setId("backButton");
		backButton.setOnAction(e -> {
			StartPage startPage = new StartPage();
			startPage.display(window, pizzeria);
		});
		return backButton;
	}
}
