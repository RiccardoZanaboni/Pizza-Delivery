package elementiGrafici;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;

import java.util.ArrayList;


public class OrderPage3 {

    public void display(Stage window, Order order, Pizzeria pizzeria, int tot, Scene scene3) {

        //TODO MIGLIORARE LA PAGINA

        GridPane gridPane ;

        VBox layout = new VBox();


        ArrayList<Label> nomiLabels = new ArrayList<>();
        ArrayList<Label> ingrLabels = new ArrayList<>();
        ArrayList<Label> prezziLabels = new ArrayList<>();
        ArrayList<Label> countPizzeLabels = new ArrayList<>();

        riempiLabels(order, nomiLabels, ingrLabels, prezziLabels, countPizzeLabels);
        gridPane = addEverythingToGridPane(pizzeria, order, nomiLabels, countPizzeLabels, ingrLabels, prezziLabels);

        Label yourOrder = new Label("Il tuo ordine "+ order.getCodice());

        HBox titleBox = new HBox();
        titleBox.getChildren().add(yourOrder);
        titleBox.setStyle("-fx-border-color:black;");
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setMinSize(600, 50);

        VBox recapBox = new VBox(20);
        Label yourNameLabel = new Label("SIG."+order.getCustomer().getUsername());
        Label yourAddressLabel = new Label("INDIRIZZO: "+order.getIndirizzo());
        Label yourOrderTimeLabel = new Label ("Orario "+order.getOrario());
        recapBox.getChildren().addAll(yourNameLabel, yourAddressLabel, yourOrderTimeLabel);

        Button indietroButton = new Button("← Torna indietro");
        indietroButton.setOnAction(e -> window.setScene(scene3));
        Button nuovoOrdine=new Button("Nuovo Ordine");
        nuovoOrdine.setOnAction(event -> {
            MenuPage menuPage = new MenuPage();
            OrderPage1.getIndietroButton().fire();
            menuPage.display(window, pizzeria);
        }
            );
        Button closeButton = new Button("Fine ☓");
        closeButton.setOnAction(e-> {window.close();});

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(indietroButton, nuovoOrdine,closeButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setMinSize(600, 30);


        ScrollPane scrollPane = new ScrollPane(gridPane);

        scrollPane.setMinSize(600, 400);
        buttonBox.setMinSize(600, 100);
        layout.getChildren().addAll(titleBox, recapBox, scrollPane,buttonBox);
        Scene scene4;
        scene4 = new Scene(layout, 600, 800);
        window.setScene(scene4);
    }

    private static void riempiLabels (Order order, ArrayList<Label> nomiLabels, ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels, ArrayList<Label> countPizzeLabels) {
        System.out.println(order.getNumeroPizze());
        System.out.println(order.getPizzeordinate());

        for (int i=0; i<order.getNumeroPizze(); i++) {
            nomiLabels.add(i, new Label(order.getPizzeordinate().get(i).getNomeCamel()));
            ingrLabels.add(i, new Label(order.getPizzeordinate().get(i).getDescrizione()));
            prezziLabels.add(i, new Label(order.getPizzeordinate().get(i).getPrezzo() + " €"));
            countPizzeLabels.add(i, new Label());
            countPizzeLabels.get(i).setText("" + order.getPizzeordinate().get(i).getCount());
        }
    }

    private static GridPane addEverythingToGridPane(Pizzeria pizzeria, Order order, ArrayList<Label> nomiLabels, ArrayList<Label> countPizzeLabels,  ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels) {
        HBox hbox2=new HBox();
        Label labelTot = new Label("Totale: ");
        Label label2=new Label(""+order.getTotaleCosto());
        hbox2.getChildren().addAll(labelTot,label2);
        hbox2.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        GridPane.setConstraints(hbox2, 1, order.getNumeroPizze()+2);
        gridPane.getChildren().addAll(hbox2);
        for (int i=0; i<order.getNumeroPizze(); i++) {
            GridPane.setConstraints(countPizzeLabels.get(i), 0, i + 1);
            GridPane.setConstraints(nomiLabels.get(i), 1, i + 1);
            GridPane.setConstraints(ingrLabels.get(i), 2, i + 1);
            GridPane.setConstraints(prezziLabels.get(i), 3, i + 1);
            gridPane.getChildren().add(nomiLabels.get(i));
            gridPane.getChildren().add(ingrLabels.get(i));
            gridPane.getChildren().add(countPizzeLabels.get(i));
            gridPane.getChildren().add(prezziLabels.get(i));
        }
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(30);
        return gridPane;
    }


}
