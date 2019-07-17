package graphicElements.customerSidePages;

import database.CustomerDB;
import graphicAlerts.GenericAlert;
import javafx.geometry.Insets;
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
import pizzeria.services.sendMail.SendJavaMail;

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

        /* Campo Cognome */
        Label surnameLabel = new Label("Cognome: ");
        TextField surnameInput = new TextField();
        surnameInput.setPromptText("Il tuo cognome");
        String surname = CustomerDB.getCustomerFromUsername(customer.getUsername(), 5);
        surnameInput.setText(surname);

        /* Campo Indirizzo */
        Label addressLabel = new Label("Indirizzo: ");
        TextField addressInput = new TextField();
        addressInput.setPromptText("Il tuo indirizzo");
        String address = CustomerDB.getCustomerFromUsername(customer.getUsername(), 6);
        addressInput.setText(address);

        /* Campo Email */
        Label emailLabel = new Label("Email: ");
        TextField emailField = new TextField();
        emailField.setPromptText("La tua email");
        emailField.setMinWidth(280);
        String email = CustomerDB.getCustomerFromUsername(customer.getUsername(), 3);
        emailField.setText(email);

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
            String newMail = emailField.getText();
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

        VBox labelVBox= new VBox(30);
        labelVBox.getChildren().addAll(nameLabel,surnameLabel,addressLabel,emailLabel);
        VBox fieldVBox =new VBox(49);
        fieldVBox.getChildren().addAll(nameInput,surnameInput,addressInput,emailField);
        labelVBox.setAlignment(Pos.CENTER_LEFT);
        labelVBox.setPadding(new Insets(0,20,0,165));
        fieldVBox.setAlignment(Pos.CENTER_RIGHT);
        HBox hBox=new HBox(30);
        hBox.getChildren().addAll(labelVBox,fieldVBox);

        VBox layout = new VBox(30);
        layout.getChildren().addAll(hBox, buttonBox);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 800, 600);
        layout.getStyleClass().add("layout");
        scene.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/loginPageStyle.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }
}