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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pizzeria.Customer;
import pizzeria.Pizzeria;
import pizzeria.services.TextColorServices;
import pizzeria.services.sendMail.SendJavaMail;

public class YourProfilePage {

    /** Lo Stage mostra una pagina in cui è possibile aggiornare i propri dati presenti
     * all'interno del DB. Il DB mostra in automatico quelli già presenti (dove disponibili).
     * Premendo il pulsante di conferma, i dati vengono aggiornati e salvati. */
    public void display(Stage window, Pizzeria pizzeria, Customer customer) {
        window.setTitle("Wolf of Pizza - I tuoi Dati");

        /* Campo Nome */
        Label nameLabel = new Label(" Nome:\t  ");
        //nameLabel.setMaxWidth(230);
        TextField nameInput = new TextField();
        nameInput.setMinWidth(250);
        nameInput.setPromptText("Il tuo nome");
        String name = CustomerDB.getCustomerFromUsername(customer.getUsername(), 4);
        nameInput.setText(name);
        nameLabel.setId("nomiLabel");
        HBox nameBox = new HBox(50);
        nameBox.getChildren().addAll(nameLabel, nameInput);
        nameBox.setAlignment(Pos.CENTER);

        /* Campo Cognome */
        Label surnameLabel = new Label(" Cognome: ");
        //surnameLabel.setMaxWidth(230);
        TextField surnameInput = new TextField();
        surnameInput.setMinWidth(250);
        surnameInput.setPromptText("Il tuo cognome");
        String surname = CustomerDB.getCustomerFromUsername(customer.getUsername(), 5);
        surnameInput.setText(surname);
        surnameLabel.setId("nomiLabel");
        HBox surnameBox = new HBox(50);
        surnameBox.getChildren().addAll(surnameLabel, surnameInput);
        surnameBox.setAlignment(Pos.CENTER);

        /* Campo Indirizzo */
        Label addressLabel = new Label(" Indirizzo:  ");
        //addressLabel.setMaxWidth(230);
        TextField addressInput = new TextField();
        addressInput.setMinWidth(250);
        addressInput.setPromptText("Il tuo indirizzo");
        String address = CustomerDB.getCustomerFromUsername(customer.getUsername(), 6);
        addressInput.setText(address);
        addressLabel.setId("nomiLabel");
        HBox addressBox = new HBox(50);
        addressBox.getChildren().addAll(addressLabel, addressInput);
        addressBox.setAlignment(Pos.CENTER);

        /* Campo E-mail */
        Label mailLabel = new Label(" E-mail:\t ");
        //mailLabel.setMaxWidth(180);
        TextField mailField = new TextField();
        mailField.setPromptText("La tua e-mail");
        mailField.setMinWidth(250);
        String email = CustomerDB.getCustomerFromUsername(customer.getUsername(), 3);
        mailField.setText(email);
        mailLabel.setId("nomiLabel");
        HBox mailBox = new HBox(50);
        mailBox.getChildren().addAll(mailLabel, mailField);
        mailBox.setAlignment(Pos.CENTER);

        /* Bottone per tornare alla HomePage senza salvare le modifiche */
        Button backButton = new Button("← Torna indietro");
        backButton.setMinHeight(35);
        backButton.setMaxHeight(40);
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
        	String newMail = mailField.getText();
        	boolean modifiedMail = false;
			if(newMail.equals("") || newMail.equals(CustomerDB.getCustomerFromUsername(customer.getUsername(),3))) {	/* se la mail non è stata variata */
				newMail = CustomerDB.getCustomerFromUsername(customer.getUsername(),3);
			} else {
				modifiedMail = true;
			}
			if(CustomerDB.addInfoCustomer(customer.getUsername(),newName,newSurname,newAddress,newMail)) {
                if(modifiedMail) {
					SendJavaMail javaMail = new SendJavaMail();
					javaMail.changeMailAddress(customer, newMail);
				}
                HomePage homePage = new HomePage();
                homePage.display(window, pizzeria, customer);
            } else GenericAlert.display("Modifica dei dati non riuscita.");
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(backButton, confirmButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30);
        layout.getChildren().addAll(nameBox, surnameBox, addressBox, mailBox, buttonBox);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 800, 600);
        layout.getStyleClass().add("layout");
        scene.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/loginPageStyle.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }
}