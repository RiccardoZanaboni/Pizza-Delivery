package elementiGrafici;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pizzeria.Customer;
import pizzeria.Order;
import pizzeria.Pizzeria;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class OrderPage2 {
	private Scene scene3;
	private Button button;
	private String indirizzo, nome;
	private Date ora;


	public void display (Stage window, Scene scene2, Order order, Pizzeria pizzeria, int tot) {
		GridPane gridPane = new GridPane();

		Label username = new Label("Username");
		TextField nameInput = new TextField();
		nameInput.setPromptText("Your name");
		HBox usernameBox = new HBox(50);
		usernameBox.getChildren().addAll(username, nameInput);

		Label address = new Label("Indirizzo");
		TextField addressInput = new TextField();
		addressInput.setPromptText("Your address");
		HBox addressBox = new HBox(61);
		addressBox.getChildren().addAll(address, addressInput);

		Label choiceLabel = new Label("Scegli l'ora");
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll(getOrari(pizzeria, tot));
		HBox choiceHBox = new HBox(44);
		choiceHBox.getChildren().addAll(choiceLabel, choiceBox);

		Button nextPageButton = new Button("Prosegui →");
		nextPageButton.setOnAction(e-> {
			nome = getName(nameInput);
			indirizzo = getAddress(addressInput);
			ora = getChoice(choiceBox, order);
			order.setIndirizzo(getAddress(addressInput));
			Customer customer = new Customer(getName(nameInput));
			order.setCustomer(customer);
			order.setOrario(ora);
			OrderPage3 orderPage3 = new OrderPage3();
			orderPage3.display(window, order, pizzeria, tot, scene3);
		});

		Button goBackButton = new Button("Torna indietro ←");
		goBackButton.setOnAction(e -> {
			OrderPage1 orderPage1 = new OrderPage1();
			orderPage1.display(window,scene2, order, pizzeria);
			//window.setScene(scene2);
		});

		HBox buttonBox = new HBox(10);
		buttonBox.getChildren().addAll(goBackButton, nextPageButton);
		buttonBox.setAlignment(Pos.CENTER);

		HBox hBoxIntestazione = new HBox();
		Label label = new Label("Inserisci i tuoi dati");
		hBoxIntestazione.getChildren().add(label);
		hBoxIntestazione.setAlignment(Pos.CENTER);

		//GridPane.setConstraints(username, 0 , 0);
		// GridPane.setConstraints(nameInput, 1, 0);
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

		scene3 = new Scene(layout);
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());
        window.setScene(scene3);
	}

	// FIXME DA SISTEMARE getChoice , ora funziona ma poco carino

	private Date getChoice(ChoiceBox<String> choiceBox, Order order) {
		String a = "L'ora scelta è:";
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

	private String getAddress (TextField aInput) {
		String a = "";
		a += aInput.getText();
		//System.out.println(a);
		return a;
	}

	private String getName (TextField nInput) {
		String a = "";
		a += nInput.getText();
		//System.out.println(a);
    	return a;
	}

	private ObservableList<String> getOrari (Pizzeria pizzeria, int tot) {
		ObservableList<String> orari = FXCollections.observableArrayList();
		orari.addAll(pizzeria.orarioDisponibile(tot));
		return orari;
	}
}
