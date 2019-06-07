package graphicElements;

import graphicAlerts.GenericAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	private String password;
	private Date time;

	public void display (Stage window, Scene scene2, Order order, Pizzeria pizzeria, int tot, Customer customer) {

		GridPane gridPane = new GridPane();

		Label username = new Label(" Nome:\t  ");
		TextField nameInput = new TextField();
		nameInput.setPromptText("Your Name");
		username.setId("nomiLabel");
		if (customer.isLoggedIn())
			nameInput.setText(customer.getUsername());
		HBox usernameBox = new HBox(50);
		usernameBox.getChildren().addAll(username, nameInput);

		/*Label password = new Label(" Password: ");
		PasswordField passwordInput = new PasswordField();
		passwordInput.setPromptText("Your Password");
		password.setId("nomiLabel");
		HBox passwordBox = new HBox(50);
		passwordBox.getChildren().addAll(password, passwordInput);*/

		Label address = new Label(" Indirizzo:   ");
        TextField addressInput = new TextField();
		addressInput.setPromptText("Your Address");
		address.setId("nomiLabel");
		HBox addressBox = new HBox(50);
		addressBox.getChildren().addAll(address, addressInput);

		Label choiceLabel = new Label(" Orario:\t  ");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll(getTime(pizzeria, tot));
		choiceLabel.setId("nomiLabel");
		HBox choiceHBox = new HBox(50);
		choiceHBox.getChildren().addAll(choiceLabel, choiceBox);

		Button confirmButton = new Button("Prosegui →");
        confirmButton.setId("confirmButton");
		confirmButton.setOnAction(e-> {
			//this.name = getInfo(nameInput);
			//this.password= getInfo(passwordInput);
			this.address = getInfo(addressInput);
			this.time = getChoice(choiceBox);
			order.setName(customer.getUsername());
			order.setAddress(this.address);
			order.setCustomer(customer);
			order.setTime(time);
			// FIXME: questo "if" va riaggiunto!!!	 if (checkInsert(this.name,this.password,this.address,this.time)) {
				OrderPage3 orderPage3 = new OrderPage3();
				orderPage3.display(window, order, pizzeria, scene3, customer);
			//}
		});

		Button backButton = new Button("← Torna indietro");
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
			this.name = getInfo(nameInput);
			this.address = getInfo(addressInput);
			OrderPage1 orderPage1 = new OrderPage1();
			orderPage1.display(window, order, pizzeria, customer);
		});

		HBox buttonBox = new HBox(10);
		buttonBox.getChildren().addAll(backButton, confirmButton);
		buttonBox.setAlignment(Pos.CENTER);

		HBox hBoxIntestazione = new HBox();
		Label label = new Label("Inserisci i tuoi dati");
		hBoxIntestazione.getChildren().add(label);
		hBoxIntestazione.setAlignment(Pos.CENTER);

		GridPane.setConstraints(usernameBox,0,0);
		//GridPane.setConstraints(passwordBox,0,1);
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
        scene3.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if(ke.getCode()== KeyCode.ENTER) {
                    confirmButton.fire();
                }
                if(ke.getCode()== KeyCode.CONTROL||ke.getCode()== KeyCode.BACK_SPACE)
                {
                    backButton.fire();
                }
            }
        });
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());
        scene3.getStylesheets().addAll(this.getClass().getResource("cssStyle/orderPage2.css").toExternalForm());
        window.setScene(scene3);
	}

	private boolean checkInsert(String name, String password, String address, Date time) {
		if(name.equals("")) {
			GenericAlert.display("Attenzione: non è stato inserito il nome!");
			return false;
		}
		else if(password.equals("")) {
			GenericAlert.display("Attenzione: non è stato inserito l'indirizzo!");
			return false;
		}
		else if(address.equals("")) {
			GenericAlert.display("Attenzione: non è stata inserita la password!");
			return false;
		}
		else if(time==null) {
			GenericAlert.display("Attenzione: non è stato inserito l'orario!");
			return false;
		}
		else return true;
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

	/** legge e restituisce l'informazione inserita */
	private String getInfo(TextField nInput) {
    	return nInput.getText();
	}


	/** aggiunge tutti gli orari disponibili alla ObservableList */
	private ObservableList<String> getTime(Pizzeria pizzeria, int tot) {
		ObservableList<String> orari = FXCollections.observableArrayList();
		orari.addAll(pizzeria.availableTimes(tot));
		return orari;
	}
}
