package graphicElements.customerSidePages;

import graphicAlerts.ClosedPizzeriaAlert;
import graphicAlerts.GenericAlert;
import graphicElements.customerSidePages.loginPages.LoginAccountPage;
import graphicElements.customerSidePages.newOrder.OrderPage1;
import javafx.beans.NamedArg;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.*;
import enums.OpeningPossibilities;
import pizzeria.services.PizzeriaServices;
import pizzeria.services.TimeServices;

public class HomePage {

	/**
	 * Visualizza la Scene iniziale che rappresenta la "Home", con le varie possibilità
	 * di utilizzo del programma, rappresentate dai 4 bottoni centrali.
	 * Si attiva al "run" di GraphicInterface.
	 */
	public void display(Stage window, Pizzeria pizzeria, Customer customer) {
		window.setTitle("Wolf of Pizza - Home");
		Label label1 = new Label("Benvenuto");
		Label usernameLabel = new Label("");
		usernameLabel.setText(customer.getUsername());
        HBox hBox = new HBox(20);

        ImageView imageView1 = creatImageView("/graphicElements/images/logout-128.png",20,20);

        /* Bottone per il logout */
        Button logoutButton = new Button();
        logoutButton.setGraphic(imageView1);
        logoutButton.setMinSize(100, 50);
        logoutButton.setOnAction(e->{
            LoginAccountPage loginAccountPage = new LoginAccountPage();
            loginAccountPage.display(window, pizzeria);
        });

        hBox.getChildren().addAll(label1, usernameLabel,logoutButton);
        hBox.setAlignment(Pos.CENTER);
		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(hBox);
		stackPane.getStyleClass().add("stackpane");

		ImageView imageView = creatImageView("/graphicElements/images/banner_pizza.jpg",150,880);

		StackPane spazioPane = new StackPane();
		spazioPane.setMinSize(800, 150);
		spazioPane.getChildren().add(imageView);
		spazioPane.setAlignment(Pos.CENTER);

		/* Bottone "Nuovo ordine" */
		Button makeOrderButton = creatMakeButton(pizzeria, window, customer) ;

		/* Bottone "Chi siamo" */
		Button whoWeAreButton = new Button("Chi siamo");
		whoWeAreButton.setOnAction(event -> {
		    WhoWeArePage whoWeArePage = new WhoWeArePage();
		    whoWeArePage.display(window,pizzeria, customer);
        });
		whoWeAreButton.prefWidthProperty().bind(window.widthProperty());
        whoWeAreButton.prefHeightProperty().bind(window.heightProperty());

		/* Bottone "Ultimo Ordine" */
		Button lastOrderButton = creatLastOrderButton(pizzeria, window, customer);

		/* Bottone "Il tuo profilo" */
		Button dataButton = new Button("Il tuo profilo");
        dataButton.setOnAction(e->{
            YourProfilePage yourProfilePage = new YourProfilePage();
            yourProfilePage.display(window, pizzeria, customer);
        });
		dataButton.prefWidthProperty().bind(window.widthProperty());
        dataButton.prefHeightProperty().bind(window.heightProperty());

        /* Costruisce il GridPane con gli elementi */
		GridPane gridPane = new GridPane();
		gridPane.getChildren().addAll(makeOrderButton, whoWeAreButton, lastOrderButton, dataButton);
		GridPane.setConstraints(makeOrderButton, 1, 1);
		GridPane.setConstraints(whoWeAreButton, 2, 1);
		GridPane.setConstraints(lastOrderButton, 1, 2);
		GridPane.setConstraints(dataButton, 2, 2);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		VBox layout = new VBox();
		layout.getChildren().addAll(stackPane, imageView, gridPane);
		layout.getStyleClass().add("layout");
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());

		Scene scene1 = new Scene(layout, 800, 600);
		scene1.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/menuStyle.css").toExternalForm());
		window.setScene(scene1);
		window.show();
	}


	private Button creatMakeButton(Pizzeria pizzeria, Stage window,Customer customer){
	    Button button =new Button("Nuovo ordine");
        OpeningPossibilities checkOpen = TimeServices.checkTimeOrder(pizzeria.getOpeningToday(), pizzeria.getClosingToday());
        button.setOnAction(e -> {
            pizzeria.updatePizzeriaToday();
            switch (checkOpen) {
                case OPEN:        /* pizzeria aperta e in attività */
                    Order order = pizzeria.initializeNewOrder();
                    OrderPage1 orderPage1 = new OrderPage1();
                    orderPage1.display(window, order, pizzeria, customer);
                    break;
                case CLOSING:
                    ClosedPizzeriaAlert.display(true);        // pizzeria in chiusura
                    break;
                default:
                    ClosedPizzeriaAlert.display(false);        // pizzeria già chiusa
                    break;
            }
        });
        button.prefWidthProperty().bind(window.widthProperty());
        button.prefHeightProperty().bind(window.heightProperty());
        return button;
    }

    private ImageView creatImageView ( @NamedArg("url") String url, double Height, double Width )
    {
        Image image1 = new Image(url);
        ImageView imageView = new ImageView(image1);
        imageView.setFitHeight(Height);
        imageView.setFitWidth(Width);
        imageView.autosize();
        return imageView;
    }

    private Button creatLastOrderButton(Pizzeria pizzeria, Stage window,Customer customer){
        Button button = new Button("Ultimo ordine");
        button.prefWidthProperty().bind(window.widthProperty());
        button.prefHeightProperty().bind(window.heightProperty());
        button.setOnAction(event -> {
            Order last = PizzeriaServices.CustomerLastOrder(customer,pizzeria.getOrders());
            if(last != null) {
                LastOrderPage lastOrderPage = new LastOrderPage();
                lastOrderPage.display(window, last, pizzeria, customer);
            } else GenericAlert.display(customer.getUsername() + ", non hai ancora effettuato nessun ordine!");
        });
    return button;
    }
}
