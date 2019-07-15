package graphicElements.customerSidePages;

import database.CustomerDB;
import graphicAlerts.GenericAlert;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.Customer;
import pizzeria.Pizzeria;

public class YourProfilePage {

    /** Lo Stage mostra una pagina in cui è possibile aggiornare i propri dati presenti
     * all'interno del DB. Il DB mostra in automatico quelli già presenti (dove disponibili).
     * Premendo il pulsante di conferma, i dati vengono aggiornati e salvati. */
    public void display(Stage window, Pizzeria pizzeria, Customer customer) {
        window.setTitle("Wolf of Pizza - I tuoi Dati");

        /* Campo Nome */
        Label nameLabel = new Label("Nome:\t ");
        TextField nameInput = new TextField();
        nameInput.setPromptText("Il tuo nome");
        String name = CustomerDB.getCustomerFromUsername(customer.getUsername(), 4);
        nameInput.setText(name);
        HBox nameBox = new HBox(50);
        nameBox.getChildren().addAll(nameLabel, nameInput);
        nameBox.setAlignment(Pos.CENTER);

        /* Campo Cognome */
        Label surnameLabel = new Label("Cognome: ");
        TextField surnameInput = new TextField();
        surnameInput.setPromptText("Il tuo cognome");
        String surname = CustomerDB.getCustomerFromUsername(customer.getUsername(), 5);
        surnameInput.setText(surname);
        HBox surnameBox = new HBox(50);
        surnameBox.getChildren().addAll(surnameLabel, surnameInput);
        surnameBox.setAlignment(Pos.CENTER);

        /* Campo Indirizzo */
        Label addressLabel = new Label("Indirizzo: ");
        TextField addressInput = new TextField();
        addressInput.setPromptText("Il tuo indirizzo");
        String address = CustomerDB.getCustomerFromUsername(customer.getUsername(), 6);
        addressInput.setText(address);
        HBox addressBox = new HBox(50);
        addressBox.getChildren().addAll(addressLabel, addressInput);
        addressBox.setAlignment(Pos.CENTER);

        /* Campo Email */
        Label emailLabel = new Label("Email: ");
        TextField emailField = new TextField();
        emailField.setPromptText("La tua email");
        emailField.setMinWidth(280);
        String email = CustomerDB.getCustomerFromUsername(customer.getUsername(), 3);
        emailField.setText(email);
        HBox emailBox = new HBox(50);
        emailBox.getChildren().addAll(emailLabel, emailField);
        emailBox.setAlignment(Pos.CENTER);

        /* Bottone per tornare alla HomePage senza salvare le modifiche */
        Button backButton = new Button("← Torna indietro");
        backButton.setMinHeight(35);
        backButton.setMaxHeight(40);
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
           HomePage homePage = new HomePage();
           homePage.display(window, pizzeria, customer);
        });

        /* Bottone per confermare le modifiche */
        Button confirmButton = new Button(" Conferma modifiche");
        confirmButton.setMinHeight(35);
        confirmButton.setMaxHeight(40);
        confirmButton.setOnAction(e-> {
            String newName = nameInput.getText();
            String newSurname = surnameInput.getText();
            String newAddress = addressInput.getText();
            String mail=emailField.getText();
            if(CustomerDB.addInfoCustomer(customer.getUsername(),newName,newSurname,newAddress, mail)) {
                HomePage homePage = new HomePage();
                homePage.display(window, pizzeria, customer);
            } else GenericAlert.display("Modifica dei dati non riuscita.");
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(backButton, confirmButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30);
        layout.getChildren().addAll(nameBox, surnameBox, addressBox, emailBox, buttonBox);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 800, 600);
        layout.getStyleClass().add("layout");
        scene.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/loginPageStyle.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }
}