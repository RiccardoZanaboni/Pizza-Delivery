package graphicElements.customerSidePages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pizzeria.*;
import pizzeria.services.TimeServices;

import java.util.ArrayList;

public class LastOrderPage{

    public void display(Stage window, Order order, Pizzeria pizzeria, Customer customer) {
        window.setTitle("Wolf of Pizza - Ultimo Ordine");

        ArrayList<Label> nomiLabels = new ArrayList<>();
        ArrayList<Label> ingrLabels = new ArrayList<>();
        ArrayList<Label> prezziLabels = new ArrayList<>();
        ArrayList<Label> countPizzeLabels = new ArrayList<>();

        GridPane gridPane = addEverythingToGridPane(order, nomiLabels, countPizzeLabels, ingrLabels, prezziLabels);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(30);

        VBox recapBox = new VBox(20);
        Label userLabel = new Label("USERNAME:\t" + order.getCustomer().getUsername().toLowerCase());
        Label interphoneNameLabel = new Label("COGNOME:\t" + order.getName());
        Label yourAddressLabel = new Label("INDIRIZZO:\t" + order.getAddress());
        Label yourOrderTimeLabel = new Label ("ORARIO:\t\t" + TimeServices.dateTimeStamp(order.getTime()));
        recapBox.getChildren().addAll(userLabel, interphoneNameLabel, yourAddressLabel, yourOrderTimeLabel);
        recapBox.setId("recapBox");

        Button backButton = new Button("← Torna indietro");
        backButton.setPrefHeight(35);
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage();
            homePage.display(window, pizzeria, customer);
            }
        );

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(backButton);

        Label yourOrder = new Label("Il tuo ultimo ordine:\t\t" + order.getOrderCode());
        HBox titleBox = new HBox();
        titleBox.getChildren().add(yourOrder);
        titleBox.setId("yourOrderBox");
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setMinSize(600, 50);

        ScrollPane scrollPane = new ScrollPane(gridPane);
        //scrollPane.setMinSize(600, 400);
        buttonBox.setMinSize(600, 60);

        VBox layout = new VBox();
        layout.getChildren().addAll(titleBox, recapBox, scrollPane,buttonBox);

        Scene scene4 = new Scene(layout, 800,600);

        layout.setId("layout");
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());
        scene4.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/orderPage3.css").toExternalForm());	//TODO: ???
        window.setScene(scene4);
    }

    private static GridPane addEverythingToGridPane(Order order, ArrayList<Label> nomiLabels, ArrayList<Label> countPizzeLabels,  ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels) {
        HBox totalBox = new HBox();
        Label labelTot = new Label("Totale: ");
        Label label2 = new Label("" + order.getTotalPrice() + " €");
        totalBox.getChildren().addAll(labelTot,label2);
        totalBox.setAlignment(Pos.CENTER);

        GridPane gridPane;
        gridPane = graphicRecap(nomiLabels, countPizzeLabels, ingrLabels, prezziLabels, order);
        gridPane.getChildren().add(totalBox);
        GridPane.setConstraints(totalBox, 1, nomiLabels.size()+2);
        return gridPane;
    }

    // TODO: questo metodo è IDENTICO a quello in OrderPage3, dobbiamo trovare una soluzione.

    /** Costruisce etichette per il riepilogo della versione grafica */
    public static GridPane graphicRecap(ArrayList<Label> nomiLabels, ArrayList<Label> countPizzeLabels, ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels, Order order) {
        GridPane gridPane = new GridPane();
        Label label = new Label();
        label.setText(order.getNumTemporaryPizze() + "");
        ArrayList<Pizza> elencate = new ArrayList<>();
        int numTipo = 0;
        for (int i = 0; i < order.getNumPizze(); i++) {
            Pizza p = order.getOrderedPizze().get(i);
            int num = 0;
            boolean contains = false;
            for (Pizza pizza : elencate) {
                if (p.getName(false).equals(pizza.getName(false)) && p.getToppings().equals(pizza.getToppings())) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                elencate.add(p);
                for (int j = 0; j < order.getNumPizze(); j++) {
                    if (p.getName(false).equals(order.getOrderedPizze().get(j).getName(false)) && p.getToppings().equals(order.getOrderedPizze().get(j).getToppings()))
                        /* di quel "tipo di pizza" ce n'è una in più */
                        num++;
                }
                nomiLabels.add(numTipo, new Label(order.getOrderedPizze().get(i).getName(true)));
                ingrLabels.add(numTipo, new Label(order.getOrderedPizze().get(i).getDescription()));
                prezziLabels.add(numTipo, new Label((order.getOrderedPizze().get(i).getPrice()*num + " €")));
                countPizzeLabels.add(numTipo, new Label());
                countPizzeLabels.get(numTipo).setText("" + num);

                gridPane.getChildren().add(nomiLabels.get(numTipo));
                gridPane.getChildren().add(ingrLabels.get(numTipo));
                gridPane.getChildren().add(countPizzeLabels.get(numTipo));
                gridPane.getChildren().add(prezziLabels.get(numTipo));

                GridPane.setConstraints(countPizzeLabels.get(numTipo), 0, numTipo + 1);
                GridPane.setConstraints(nomiLabels.get(numTipo), 1, numTipo + 1);
                GridPane.setConstraints(ingrLabels.get(numTipo), 2, numTipo + 1);
                GridPane.setConstraints(prezziLabels.get(numTipo), 3, numTipo + 1);

                numTipo++;		// ho un "tipo di pizza" in piu
            }
        }
        return gridPane;
    }

}
