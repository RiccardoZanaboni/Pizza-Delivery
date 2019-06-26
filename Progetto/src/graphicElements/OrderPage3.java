package graphicElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pizzeria.Customer;
import pizzeria.Order;
import pizzeria.Pizzeria;

import java.util.ArrayList;

/**
 * OrderPage3 è la pagina di ordinazione che consente di visualizzare il riepilogo
 * dei dati inseriti e delle pizze selezionate.
 *
 * Vi si accede tramite il bottone "Avanti" in OrderPage2.
 * Cliccando "Indietro", si torna a OrderPage2.
 * Cliccando "Conferma e torna alla Home", l'ordine viene inviato alla pizzeria
 * e si torna alla pagina MenuPage.
 * Cliccando "Conferma ed esci", l'ordine viene inviato alla pizzeria
 * e l'applicazione viene chiusa.
 */

public class OrderPage3 {

    public void display(Stage window, Order order, Pizzeria pizzeria, Scene scene3, Customer customer) {

        //TODO MIGLIORARE LA PAGINA @ MUSI

        VBox layout = new VBox();

        ArrayList<Label> nomiLabels = new ArrayList<>();
        ArrayList<Label> ingrLabels = new ArrayList<>();
        ArrayList<Label> prezziLabels = new ArrayList<>();
        ArrayList<Label> countPizzeLabels = new ArrayList<>();

        GridPane gridPane = addEverythingToGridPane( order, nomiLabels, countPizzeLabels, ingrLabels, prezziLabels);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(30);

        Label yourOrder = new Label("Il tuo ordine:\t\t"+ order.getOrderCode());

        HBox titleBox = new HBox();
        titleBox.getChildren().add(yourOrder);
        titleBox.setStyle("-fx-border-color:black;");
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setMinSize(600, 50);

        VBox recapBox = new VBox(20);
        Label userLabel = new Label("SIG.\t\t\t" + order.getCustomer().getUsername().toLowerCase());
        Label interphoneNameLabel = new Label("CITOFONO:\t" + order.getName());
        Label yourAddressLabel = new Label("INDIRIZZO:\t" + order.getAddress());
        Label yourOrderTimeLabel = new Label ("ORARIO:\t\t" + order.getTime());
        recapBox.getChildren().addAll(userLabel, interphoneNameLabel, yourAddressLabel, yourOrderTimeLabel);

        Button backButton = new Button("← Torna indietro");
        backButton.setId("backButton");
        backButton.setOnAction(e ->
                window.setScene(scene3)
        );

        Button newOrderButton = new Button("Conferma e torna alla Home ✔");
        newOrderButton.setId("confirmButton");
        newOrderButton.setOnAction(e -> {
        	pizzeria.addInfoOrder(order);
            MenuPage menuPage = new MenuPage();
            OrderPage1.getBackButton().fire();
            menuPage.display(window, pizzeria, customer);
        });

        Button closeButton = new Button("Conferma ed esci ☓");
        closeButton.setId("closeButton");
        closeButton.setOnAction(e-> {
        	pizzeria.addInfoOrder(order);
        	window.close();
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(backButton, newOrderButton,closeButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setMinSize(600, 30);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setMinSize(600, 400);
        buttonBox.setMinSize(600, 100);
        layout.getChildren().addAll(titleBox, recapBox, scrollPane,buttonBox);

        layout.setOnKeyPressed(ke -> {
            if(ke.getCode()== KeyCode.ENTER)
                newOrderButton.fire();
            if(ke.getCode()== KeyCode.CONTROL||ke.getCode()== KeyCode.BACK_SPACE)
                backButton.fire();
            if(ke.getCode()== KeyCode.ESCAPE)
                closeButton.fire();

        });
        Scene scene4;
        scene4 = new Scene(layout, 600, 800);
        scene4.getStylesheets().addAll(this.getClass().getResource("cssStyle/orderPage2.css").toExternalForm());
        window.setScene(scene4);
    }

    private static GridPane addEverythingToGridPane(Order order, ArrayList<Label> nomiLabels, ArrayList<Label> countPizzeLabels,  ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels) {
        HBox totalBox = new HBox();
        Label labelTot = new Label("Totale: ");
        Label label2 = new Label("" + order.getTotalPrice() + " €");
        totalBox.getChildren().addAll(labelTot,label2);
        totalBox.setAlignment(Pos.CENTER);

        GridPane gridPane;
		gridPane = order.graphRecap(nomiLabels, countPizzeLabels, ingrLabels, prezziLabels);
		gridPane.getChildren().add(totalBox);
        GridPane.setConstraints(totalBox, 1, nomiLabels.size()+2);
        return gridPane;
    }
}
