package graphicElements.customerSidePages.newOrder;

import com.sun.org.apache.xpath.internal.operations.Or;
import database.CustomerDB;
import graphicAlerts.GenericAlert;
import graphicElements.customerSidePages.HomePage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pizzeria.Customer;
import pizzeria.Order;
import pizzeria.Pizzeria;
import pizzeria.services.TimeServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class OrderPage2 {
	private Scene scene3;
	private String address;
	private String name;
	private Date time;

	/** Lo Stage ospita la seconda pagina della richiesta di un nuovo ordine, quella di inserimento dei dati.
	 * Se l'utente ha precedentemente inserito nel DB i propri dati personali, le informazioni riguardo
	 * al cognome e all'indirizzo risultano già presenti (ovviamente modificabili, nel caso l'utente
	 * non si trovi, per quest'ordine, nel domicilio usuale).
	 * Il cuore dell'applicazione si nasconde dietro alla scelta dei possibili orari, in quanto
	 * per la visualizzazione della lista di orari disponibili vi è uno studio degli ordini già effettuati
	 * da altri utenti, attraverso il DB, in relazione con le disponibilità di forni e fattorini
	 * da parte della pizzeria.
	 * Viene visualizzato un messaggio di errore se uno qualunque dei tre campi richiesti non venga riempito.
	 * Attraverso i bottoni in fondo pagina è possibile spostarsi alla OrderPage3 (Avanti) oppure nuovamente
	 * alla OrderPage1, per modificare le pizze richieste (Indietro).
	 * */
	public void display (Stage window, Order order, Pizzeria pizzeria, Customer customer) {
		window.setTitle("Wolf of Pizza - Nuovo Ordine");

		HBox hBoxIntestazione = new HBox();
		Label label = new Label("Inserisci i tuoi dati");
		hBoxIntestazione.getChildren().add(label);
		hBoxIntestazione.setAlignment(Pos.CENTER);
		hBoxIntestazione.setMinHeight(40);
        hBoxIntestazione.setId("hboxIntestazione");

		/* Campo Surname */
		Label surnameLabel = new Label(" Cognome:  ");
		surnameLabel.setId("nomiLabel");
		TextField surnameInput = new TextField();
		surnameInput.setPromptText("Your Surname");
		String surname;
		if (order.getName() != null)
			surname = order.getName();
		else
			surname = CustomerDB.getCustomerFromUsername(customer.getUsername(),5);
		if(surname != null)
			surnameInput.setText(surname);

		HBox usernameBox = new HBox(50);
		usernameBox.getChildren().addAll(surnameLabel, surnameInput);

		/* Campo Address */
		Label addressLabel = new Label(" Indirizzo:    ");
		addressLabel.setId("nomiLabel");
        TextField addressInput = new TextField();
		addressInput.setPromptText("Your Address");
		String address;
		if(!order.getAddress().equals(""))
			address = order.getAddress();
		else
			address = CustomerDB.getCustomerFromUsername(customer.getUsername(),6);
		if(address != null)
			addressInput.setText(address);

		HBox addressBox = new HBox(50);
		addressBox.getChildren().addAll(addressLabel, addressInput);

		/* Campo TimeChoice */
		Label timeChoiceLabel = new Label(" Orario:\t   ");
		timeChoiceLabel.setId("nomiLabel");
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll(getTime(pizzeria, order.getNumPizze(), customer, window));

		HBox choiceHBox = new HBox(50);
		choiceHBox.getChildren().addAll(timeChoiceLabel, choiceBox);

		/* Bottone di conferma */
		Button confirmButton = createConfirmButton(order,surnameInput,addressInput,choiceBox,window,customer,pizzeria);

		/* Bottone per tornare indietro */
		Button backButton = createBackButton(order, surnameInput,addressInput,window,customer,pizzeria);
		HBox buttonBox = new HBox(10);
		buttonBox.getChildren().addAll(backButton, confirmButton);
		buttonBox.setMinSize(600, 58);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setId("buttonBox");

		/* Definizione elementi del GridPane */
		GridPane gridPane = new GridPane();
		GridPane.setConstraints(usernameBox,2,0);
		GridPane.setConstraints(addressBox, 2, 1);
		GridPane.setConstraints(choiceHBox, 2, 2);
		gridPane.getChildren().addAll(usernameBox, addressBox, choiceHBox);
		gridPane.setPadding(new Insets(130, 5, 20, 200));
		gridPane.setVgap(20);
        gridPane.setId("grid");

        ScrollPane scroll = new ScrollPane(gridPane);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.prefWidthProperty().bind(window.widthProperty());
        scroll.prefHeightProperty().bind(window.heightProperty());
        scroll.setPadding(new Insets(0, 0, 0, 0));
        scroll.setId("layout");

        VBox layout = new VBox();
		layout.getChildren().addAll(hBoxIntestazione, scroll, buttonBox);
        layout.setId("layout");

		scene3 = new Scene(layout,800,600);
        scene3.setOnKeyPressed(ke -> {
            if(ke.getCode()== KeyCode.ENTER) {
                confirmButton.fire();
            }
            if(ke.getCode() == KeyCode.CONTROL || ke.getCode() == KeyCode.BACK_SPACE) {
                backButton.fire();
            }
        });
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());
        scene3.getStylesheets().add(this.getClass().getResource("/graphicElements/cssStyle/orderPage2.css").toExternalForm());
        window.setScene(scene3);
	}

	private Button createConfirmButton(Order order, TextField surnameInput, TextField addressInput, ChoiceBox<String> choiceBox, Stage window, Customer customer, Pizzeria pizzeria){
        Button button = new Button("Prosegui →");
        button.setId("confirmButton");
        button.setMinHeight(35);
        button.setOnAction(e-> {
            this.name = getInfo(surnameInput);
            this.address = getInfo(addressInput);
            this.time = getChoice(choiceBox);
            order.setName(this.name);
            order.setAddress(this.address);
            order.setTime(this.time);
            if (checkInsert(this.name,this.address,this.time)) {
                OrderPage3 orderPage3 = new OrderPage3();
                orderPage3.display(window, order, pizzeria, scene3, customer);
            }
        });
        return button;
	}

	private Button createBackButton(Order order, TextField surnameInput, TextField addressInput, Stage window,Customer customer, Pizzeria pizzeria){
        Button button = new Button("← Torna indietro");
        button.setMinHeight(35);
        button.setId("backButton");
        button.setOnAction(e -> {
            order.setName(getInfo(surnameInput));
            order.setAddress(getInfo(addressInput));
            OrderPage1 orderPage1 = new OrderPage1();
            orderPage1.display(window, order, pizzeria, customer);
        });
        return button;
    }
	/** Verifica che tutti i campi siano stati riempiti. */
	private boolean checkInsert(String name, String address, Date time) {
		if(name.equals("")) {
			GenericAlert.display("Attenzione: non è stato inserito il nome!");
			return false;
		}
		else if(address.equals("")) {
			GenericAlert.display("Attenzione: non è stato inserito l'indirizzo!");
			return false;
		}
		else if(time == null) {
			GenericAlert.display("Attenzione: non è stato inserito l'orario!");
			return false;
		}
		else return true;
	}

	/** Legge e restituisce l'orario selezionato. */
	private Date getChoice(ChoiceBox<String> choiceBox) {
		Date choiceTime;
		String orario = choiceBox.getValue();
		Calendar calendar = new GregorianCalendar();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		orario = day + "/" + month + "/" + year + " " + orario;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			choiceTime = formato.parse(orario);
		} catch (ParseException e) {
			return null;
		}
		return choiceTime;
	}

	/** Legge e restituisce l'informazione inserita */
	private String getInfo(TextField nInput) {
    	return nInput.getText();
	}

	/** Aggiunge tutti gli orari disponibili alla ObservableList */
	private ObservableList<String> getTime(Pizzeria pizzeria, int tot, Customer customer, Stage window) {
		ObservableList<String> orari = FXCollections.observableArrayList();
		try{
			//noinspection ConstantConditions
			orari.addAll(TimeServices.availableTimes(pizzeria, tot));
		} catch (NullPointerException npe){
			/* se l'ordine inizia in un orario ancora valido, ma impiega troppo tempo e diventa troppo tardi: */
			HomePage home = new HomePage();
			home.display(new Stage(),pizzeria,customer);
			GenericAlert.display("Spiacenti: si è fatto tardi,\nla pizzeria è ormai in chiusura.");
			window.close();
		}
		return orari;
	}
}
