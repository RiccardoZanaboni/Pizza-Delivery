package elementiGrafici;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pizzeria.Order;
import pizzeria.Pizzeria;

import java.util.ArrayList;

public class OrderPage3 {

    public void display(Stage window, Order order, Pizzeria pizzeria, int tot, Scene scene3) {

        //TODO MIGLIORARE LA PAGINA

        VBox layout = new VBox();

        ArrayList<Label> nomiLabels = new ArrayList<>();
        ArrayList<Label> ingrLabels = new ArrayList<>();
        ArrayList<Label> prezziLabels = new ArrayList<>();
        ArrayList<Label> countPizzeLabels = new ArrayList<>();

        //riempiLabels(order, nomiLabels, ingrLabels, prezziLabels, countPizzeLabels);
        GridPane gridPane = addEverythingToGridPane(order, nomiLabels, countPizzeLabels, ingrLabels, prezziLabels);

        Label yourOrder = new Label("Il tuo ordine:\t\t"+ order.getOrderCode());

        HBox titleBox = new HBox();
        titleBox.getChildren().add(yourOrder);
        titleBox.setStyle("-fx-border-color:black;");
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setMinSize(600, 50);

        VBox recapBox = new VBox(20);
        Label yourNameLabel = new Label("SIG.\t" + order.getCustomer().getUsername());
        Label yourAddressLabel = new Label("INDIRIZZO:\t" + order.getAddress());
        Label yourOrderTimeLabel = new Label ("ORARIO:\t" + order.getTime());
        recapBox.getChildren().addAll(yourNameLabel, yourAddressLabel, yourOrderTimeLabel);

        Button backButton = new Button("← Torna indietro");
        backButton.setId("backButton");

        backButton.setOnAction(e -> window.setScene(scene3));
        Button newOrderButton=new Button("Conferma e torna al Menu ✔");
        newOrderButton.setId("confirmButton");
        newOrderButton.setOnAction(event -> {
        	order.setFull();
            MenuPage menuPage = new MenuPage();
            OrderPage1.getBackButton().fire();
            menuPage.display(window, pizzeria);
        });

        Button closeButton = new Button("Conferma ed esci ☓");
        closeButton.setId("closeButton");
        closeButton.setOnAction(e-> {
        	order.setFull();
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
        Scene scene4;
        scene4 = new Scene(layout, 600, 800);
        scene4.getStylesheets().addAll(this.getClass().getResource("ButtonPizza.css").toExternalForm());
        window.setScene(scene4);
    }

    private static GridPane addEverythingToGridPane(Order order, ArrayList<Label> nomiLabels, ArrayList<Label> countPizzeLabels,  ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels) {
        HBox totalBox = new HBox();
        Label labelTot = new Label("Totale: ");
        Label label2 = new Label("" + order.getTotalPrice());
        totalBox.getChildren().addAll(labelTot,label2);
        totalBox.setAlignment(Pos.CENTER);

        GridPane gridPane;
		gridPane = order.graphRecap(nomiLabels, countPizzeLabels, ingrLabels, prezziLabels);
		gridPane.getChildren().add(totalBox);
        GridPane.setConstraints(totalBox, 1, nomiLabels.size()+2);

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(30);
        return gridPane;
    }
}
