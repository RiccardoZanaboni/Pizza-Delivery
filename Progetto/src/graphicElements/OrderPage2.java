package graphicElements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import pizzeria.Customer;
import pizzeria.Order;
import pizzeria.Pizzeria;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * OrderPage2 è la pagina di ordinazione che consente di selezionare username,
 * indirizzo di consegna e orario desiderato (tra quelli disponibili).
 *
 * Vi si accede tramite il bottone "Avanti" in OrderPage1 o "Indietro" in OrderPage3.
 * Cliccando "Indietro", l'inserimento dati nella pagina viene annullato e si torna a OrderPage1.
 * Cliccando "Avanti", si salvano i dati inseriti e si accede alla pagina OrderPage3.
 */

public class OrderPage2 {
	private Scene scene3;
	private String address;
	private String name;
	private Date time;

	public void display (Stage window, Scene scene2, Order order, Pizzeria pizzeria, int tot) {

		// TODO: se torno indietro da qui, non vorrei perdere nome e indirizzo! Secondo me si :)
		// (l'ora per forza, perchè potrei cambiare numero di pizze)

		GridPane gridPane = new GridPane();

		Label username = new Label("Username:");
		TextField nameInput = new TextField();
		nameInput.setPromptText("Your name");
		username.setId("nomiLabel");
		HBox usernameBox = new HBox(50);
		usernameBox.getChildren().addAll(username, nameInput);

		Label address = new Label("Indirizzo:");
        address.setId("nomiLabel");
        TextField addressInput = new TextField();
		addressInput.setPromptText("Your address");
		HBox addressBox = new HBox(61);
		addressBox.getChildren().addAll(address, addressInput);

		Label choiceLabel = new Label("Scegli l'ora:");
        choiceLabel.setId("nomiLabel");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll(getTime(pizzeria, tot));
		HBox choiceHBox = new HBox(44);
		choiceHBox.getChildren().addAll(choiceLabel, choiceBox);

		Button confirmButton = new Button("Prosegui →");
        confirmButton.setId("confirmButton");
		confirmButton.setOnAction(e-> {
			this.name = getName(nameInput);
			this.address = getAddress(addressInput);
			this.time = getChoice(choiceBox);
			order.setAddress(getAddress(addressInput));
			Customer customer = new Customer(getName(nameInput));
			order.setCustomer(customer);
			order.setTime(time);
			OrderPage3 orderPage3 = new OrderPage3();
			orderPage3.display(window, order, pizzeria, tot, scene3);
		});

		Button backButton = new Button("← Torna indietro");
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
			this.name = getName(nameInput);
			this.address = getAddress(addressInput);
			OrderPage1 orderPage1 = new OrderPage1();
			orderPage1.display(window, scene2, order, pizzeria);
		});

		HBox buttonBox = new HBox(10);
		buttonBox.getChildren().addAll(backButton, confirmButton);
		buttonBox.setAlignment(Pos.CENTER);

		HBox hBoxIntestazione = new HBox();
		Label label = new Label("Inserisci i tuoi dati");
		hBoxIntestazione.getChildren().add(label);
		hBoxIntestazione.setAlignment(Pos.CENTER);

		GridPane.setConstraints(usernameBox,0,0);
		GridPane.setConstraints(addressBox, 0, 1);
		GridPane.setConstraints(choiceHBox, 0, 2);

		gridPane.getChildren().addAll(usernameBox, addressBox, choiceHBox);
		gridPane.setPadding(new Insets(130, 5, 20, 70));
		gridPane.setVgap(20);
		//gridPane.setHgap(150);

        HBox hBox=new HBox(40);
        gridPane.setHgap(50);
        hBox.getChildren().add(gridPane);
        hBox.setAlignment(Pos.CENTER);

		VBox layout = new VBox();
		layout.getChildren().addAll(hBoxIntestazione, hBox, buttonBox);
		layout.setId("grid");

		scene3 = new Scene(layout);
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());
        scene3.getStylesheets().addAll(this.getClass().getResource("orderPage2.css").toExternalForm());
        window.setScene(scene3);
	}

	// FIXME DA SISTEMARE getChoice , time funziona ma poco carino

	/** legge e restituisce l'orario desiderato */
	private Date getChoice(ChoiceBox<String> choiceBox) {
		Date oraScelta;
		String orario = choiceBox.getValue();
		Calendar calendar = new GregorianCalendar();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		orario = day + "/" + month + "/" + year + " " + orario;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			oraScelta = formato.parse(orario);
		} catch (ParseException e) {
			return null;
		}
		System.out.println(orario);
		return oraScelta;
	}

	/** legge e restituisce l'indirizzo inserito */
	private String getAddress (TextField aInput) {
		String a = "";
		a += aInput.getText();
		return a;
	}

	/** legge e restituisce l'username inserito */
	private String getName (TextField nInput) {
		String a = "";
		a += nInput.getText();
    	return a;
	}

	/** aggiunge tutti gli orari disponibili alla ObservableList */
	private ObservableList<String> getTime(Pizzeria pizzeria, int tot) {
		ObservableList<String> orari = FXCollections.observableArrayList();
		orari.addAll(pizzeria.availableTimes(tot));
		return orari;
	}
}
