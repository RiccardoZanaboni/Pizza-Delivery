package graphicElements.customerSidePages;

import graphicElements.elements.GraphicRecap;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pizzeria.*;

import java.util.ArrayList;

public class LastOrderPage{

    /** Lo Stage ospita la pagina di visualizzazione dell'ultimo ordine effettuato dal cliente.
     * Restituisce un avviso nel caso il cliente non abbia mai effettuato ordini.
     * La costruzione del Recap avviene tramite GraphicRecap.addEverythingtToGridPane(). */
    public void display(Stage window, Order order, Pizzeria pizzeria, Customer customer) {
        window.setTitle("Wolf of Pizza - Ultimo Ordine");

        /* Costruzione del GridPane */
        ArrayList<Label> nomiLabels = new ArrayList<>();
        GridPane gridPane = GraphicRecap.setGridPane(order,nomiLabels);
        VBox recapBox = GraphicRecap.setIntro(order);

        /* Definizione del bottone per tornare alla HomePage */
        Button backButton = new Button("← Torna indietro");
        backButton.setPrefHeight(35);
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage();
            homePage.display(window, pizzeria, customer);
        });
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(backButton);

        /* Intestazione */
        Label yourOrder = new Label("Il tuo ultimo ordine:\t\t" + order.getOrderCode());
        HBox titleBox = GraphicRecap.setIntestation(yourOrder);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        //scrollPane.setMinSize(600, 400);
        buttonBox.setMinSize(600, 60);

        VBox layout = new VBox();
        layout.getChildren().addAll(titleBox, recapBox, scrollPane,buttonBox);

        /* Inserimento del layout nella Scene e quindi nello Stage */
        Scene scene4 = new Scene(layout,800,600);
        layout.setId("layout");
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());
        scene4.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/orderPage3.css").toExternalForm());
        window.setScene(scene4);
    }
}
