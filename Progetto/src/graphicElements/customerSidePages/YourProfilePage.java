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
     * Premendo il pulsante di conferma, i dati vengono aggiornati e salvati.
     * Particolare attenzione, in quanto dato critico, viene posta all'aggiornamento dell'indirizzo e-mail. */
    public void display(Stage window, Pizzeria pizzeria, Customer customer) {
        window.setTitle("Wolf of Pizza - I tuoi Dati");

        VBox labelVBox= new VBox(30);
        VBox fieldVBox =new VBox(49);
        labelVBox.setAlignment(Pos.CENTER_LEFT);
        labelVBox.setPadding(new Insets(0,20,0,165));
        fieldVBox.setAlignment(Pos.CENTER_RIGHT);

        TextField nameInput = createTextField(labelVBox,fieldVBox, "Nome:\t ","Il tuo nome",customer, 4);
        TextField surnameInput = createTextField(labelVBox,fieldVBox, "Cognome: ","Il tuo cognome",customer, 5);
        TextField addressInput = createTextField(labelVBox,fieldVBox, "Indirizzo: ","Il tuo indirizzo",customer, 6);
        TextField emailField = createTextField(labelVBox,fieldVBox, "E-mail: ","La tua e-mail",customer, 3);
        emailField.setMinWidth(300);

        /* Bottone per tornare alla HomePage senza salvare le modifiche */
        Button backButton = createBackButton(window, pizzeria, customer);
        /* Bottone per confermare le modifiche */
        Button confirmButton = createConfirmButton(window,pizzeria,customer,nameInput,surnameInput,addressInput,emailField);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(backButton, confirmButton);
        buttonBox.setAlignment(Pos.CENTER);

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

    /** @return il TextField per l'inserimento di un dato. */
    private TextField createTextField(VBox labelVBox, VBox fieldVBox, String nameLabel, String nameField, Customer customer, int index){
        Label label = new Label(nameLabel);
        TextField textField = new TextField();
        textField.setPromptText(nameField);
        String name = CustomerDB.getCustomerFromUsername(customer.getUsername(), index);
        textField.setText(name);
        labelVBox.getChildren().add(label);
        fieldVBox.getChildren().add(textField);
        return textField;
    }

    /** @return il bottone per tornare alla pagina di Home. */
    private Button createBackButton(Stage window, Pizzeria pizzeria, Customer customer){
        Button backButton = new Button("← Torna indietro");
        backButton.setMinHeight(35);
        backButton.setMaxHeight(40);
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage();
            homePage.display(window, pizzeria, customer);
        });
        return backButton;
    }

    /** @return il bottone per la conferma della modifica ai dati. Particolare attenzione al campo E-mail. */
    private Button createConfirmButton(Stage window, Pizzeria pizzeria, Customer customer,TextField nameInput,TextField surnameInput,TextField addressInput,TextField emailField){
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
        return confirmButton;
    }
}