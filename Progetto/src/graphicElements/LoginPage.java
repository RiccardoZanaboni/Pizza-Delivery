package graphicElements;

import graphicElements.PizzeriaPages.PizzeriaHomePage;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pizzeria.Customer;
import pizzeria.Database;
import pizzeria.Pizzeria;

import java.sql.SQLException;

/**
 *
 * Per entrare in pizzeria usare queste credenziali
 * username = pizzeria
 * password = password
 *
 */

public class LoginPage {

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

        Button pizzeriaButton = new Button("Apri pizzeria");  // Bottone aggiunto solo per comodità
        pizzeriaButton.setOnAction(e->{
            PizzeriaHomePage pizzeriaHomePage = new PizzeriaHomePage();
            pizzeriaHomePage.display(pizzeria);
        });

        Button signUpButton = new Button("Registrati");
        signUpButton.setMinSize(100, 50);
        signUpButton.setOnAction(e->{
            try {
                if (!Database.getCustomers(nameInput.getText(), passwordInput.getText())){
                    Database.putCustomer(nameInput.getText(), passwordInput.getText());
                    MenuPage menuPage = new MenuPage();
                    Customer customer = new Customer(nameInput.getText(), passwordInput.getText());
                    customer.setLoggedIn(true);
                    menuPage.display(window, pizzeria, customer);
                    }
                else
                    System.out.println("Utente gia esistente");
            } catch (SQLException e1) {
                System.out.println("Problema");
            }
        });

        Button loginButton = new Button("Login");
        loginButton.setMinSize(100, 50);
        loginButton.setOnAction(e->{
            try {
                if (nameInput.getText().equals("pizzeria") && passwordInput.getText().equals("password")) {
                    PizzeriaHomePage pizzeriaHomePage = new PizzeriaHomePage();
                    pizzeriaHomePage.display(pizzeria);
                } else if (Database.getCustomers(nameInput.getText(), passwordInput.getText())){
                    MenuPage menuPage = new MenuPage();
                    Customer customer = new Customer(nameInput.getText(), passwordInput.getText());
                    customer.setLoggedIn(true);
                    menuPage.display(window, pizzeria, customer);
                } else {
                    insertErrorLabel.setTextFill(Color.RED);
                    insertErrorLabel.setText("Username o password errati");
                }
            } catch (SQLException e1) {}
        });

        HBox buttonBox = new HBox(50);
        VBox vBox = new VBox(20);
        buttonBox.getChildren().addAll(signUpButton, loginButton);
        buttonBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(insertErrorLabel, buttonBox);
        vBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(pizzeriaButton,usernameBox, passwordBox, vBox);

        layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if(ke.getCode()== KeyCode.DOWN) {
                    loginButton.fire();
                }if(ke.getCode()== KeyCode.UP)
                {   signUpButton.fire();
                }
            }
        });

        Scene scene = new Scene(layout, 880, 600);
        window.setScene(scene);
        window.show();
    }
}
