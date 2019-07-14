package graphicElements.customerSidePages.newOrder;

import graphicAlerts.GenericAlert;
import graphicElements.customerSidePages.HomePage;
import graphicElements.elements.ButtonAddPizza;
import graphicElements.elements.ButtonModPizza;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import pizzeria.Customer;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.services.SettleStringsServices;

import java.util.ArrayList;

public class OrderPage1 {
    private Button confirmButton;
    private static Button backButton;

    /** Lo Stage ospita la prima pagina della richiesta di un nuovo ordine, quella di scelta dal menu.
     * E' possibile selezionare di volta in volta una pizza dal menu, eventualmente richiedendo modifiche
     * attraverso l'apposito bottone.
     * In alto viene visualizzato un contatore delle pizze ordinate, che funziona da bottone
     * per accedere al carrello.
     * Viene visualizzato un messaggio di errore se viene richiesto un numero complessivo di pizze
     * maggiore del massimo consentito dalla pizzeria.
     * Attraverso i bottoni in fondo pagina è possibile spostarsi alla OrderPage2 (Avanti) o annullare
     * l'ordine, tornando alla HomePage (Indietro).
     * */
    public void display(Stage window, Order order, Pizzeria pizzeria, Customer customer) {
        window.setTitle("Wolf of Pizza - Nuovo Ordine");
        order.setCustomer(customer);

        /* Dichiarazione dei vari vettori di etichette e bottoni, uno per ogni pizza */
        ArrayList<Label> nomiLabels = new ArrayList<>();
        ArrayList<Label> ingrLabels = new ArrayList<>();
        ArrayList<Label> prezziLabels = new ArrayList<>();
        ArrayList<ButtonAddPizza> addButtons = new ArrayList<>();
        ArrayList<ButtonModPizza> modButtons = new ArrayList<>();
        ArrayList<VBox> vBoxBottoni = new ArrayList<>();
        ArrayList<VBox> vBoxNomeDescr = new ArrayList<>();
        ArrayList<HBox> hBoxPrezzoBottoni = new ArrayList<>();

        Image image = new Image("/graphicElements/images/shopping_cart.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        /* Definizione del bottone per accedere al Carrello */
        Button shoppingCartButton = new Button();
        shoppingCartButton.setGraphic(imageView);
        shoppingCartButton.setOnAction(e->{
            ShoppingCartPage shoppingCart = new ShoppingCartPage();
            shoppingCart.display(order, shoppingCartButton);
        });
        shoppingCartButton.setText(order.getNumPizze() + "");

        HBox shoppingCartBox = new HBox(order.getNumTemporaryPizze());
        shoppingCartBox.setId("shoppingCart");
        shoppingCartBox.getChildren().add(shoppingCartButton);

        HBox hBoxIntestazione = new HBox();
        Label labelOrdine = new Label("Totale pizze ordinate: ");
        hBoxIntestazione.getChildren().addAll(labelOrdine, shoppingCartBox);
        hBoxIntestazione.setAlignment(Pos.CENTER);
        hBoxIntestazione.setId("hboxIntestazione");

        /* Bottoni per lasciare la pagina (Avanti/Indietro). */
        confirmButton = createConfirmButton(customer, window, order, pizzeria);
        confirmButton.setPrefHeight(35);
        backButton = createBackButton(pizzeria, window, customer);
        backButton.setPrefHeight(35);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(backButton, confirmButton);
        buttonBox.setMinSize(600, 60);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setId("buttonBox");

        /* Metodi esterni, per non appesantire */
        fillLabelsAndButtons(shoppingCartButton, pizzeria, order, nomiLabels, ingrLabels, prezziLabels, addButtons, modButtons);
        fillVBoxesNomeAndIngr(pizzeria, vBoxNomeDescr, nomiLabels, ingrLabels);
        fillVBoxesButtons(pizzeria, vBoxBottoni, addButtons, modButtons);
        fillHBoxesPrezzoAndBottoni(pizzeria, hBoxPrezzoBottoni, prezziLabels, vBoxBottoni);

        GridPane gridPane;
        gridPane = setGridPaneContraints(pizzeria, vBoxNomeDescr, hBoxPrezzoBottoni);
        gridPane.getColumnConstraints().add(new ColumnConstraints(40));
        gridPane.getColumnConstraints().add(new ColumnConstraints(520));
        gridPane.getColumnConstraints().add(new ColumnConstraints(250));
        gridPane.setId("grid");

        /* Posizionamento del gridPane, con tutte le pizze, all'interno di uno ScrollPane */
        ScrollPane scroll = new ScrollPane(gridPane);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.prefWidthProperty().bind(window.widthProperty());
        scroll.prefHeightProperty().bind(window.heightProperty());
        scroll.setPadding(new Insets(10, 1, 5, 10));

        HBox hBox = new HBox();
        hBox.getChildren().add(scroll);
        hBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox();
        layout.setId("layout");
        layout.getChildren().addAll(hBoxIntestazione, hBox, buttonBox);
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());

        layout.setOnKeyPressed(ke -> {
            if(ke.getCode()== KeyCode.ENTER)
                confirmButton.fire();
            if(ke.getCode()==KeyCode.SHIFT)
                shoppingCartButton.fire();
        });

        /* Inserimento del layout nella Scena e quindi nello Stage. */
        Scene scene2 = new Scene(layout, 800, 600);
        scene2.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/orderPage1.css").toExternalForm());
        window.setScene(scene2);
        window.show();
    }

    /** Costruisce i vari Labels e Buttons per ogni pizza del menu */
    private static void fillLabelsAndButtons(Button shoppingCartButton, Pizzeria pizzeria, Order order,
                                             ArrayList<Label> nomiLabels, ArrayList<Label> ingrLabels,
                                             ArrayList<Label> prezziLabels, ArrayList<ButtonAddPizza> addButtons,
                                             ArrayList<ButtonModPizza> modButtons) {
        int i = 0;
        for (Pizza pizzaMenu : pizzeria.getMenu().values()) {
            nomiLabels.add(i, new Label(pizzaMenu.getName(true)));
            nomiLabels.get(i).setId("nomiLabel");
            ingrLabels.add(i, new Label(pizzaMenu.getDescription()));
            prezziLabels.add(i, new Label(SettleStringsServices.settlePriceDecimal(pizzaMenu.getPrice()) + " €"));
            addButtons.add(new ButtonAddPizza(shoppingCartButton, order, pizzaMenu));
            modButtons.add(new ButtonModPizza(shoppingCartButton, order, pizzeria, pizzaMenu.getName(false)));
            i++;
        }
    }

    /** Riempie i vari HBoxes di Buttons. */
    private static void fillVBoxesButtons(Pizzeria pizzeria, ArrayList<VBox> vBoxBottoni, ArrayList<ButtonAddPizza> addButtons, ArrayList<ButtonModPizza> modButtons) {
        for (int i = 0; i < pizzeria.getMenu().values().size(); i++) {
            vBoxBottoni.add(new VBox(5));
            vBoxBottoni.get(i).getChildren().addAll(addButtons.get(i), modButtons.get(i));
        }
    }

    /** Riempie i vari VBoxes di Labels. */
    private static void fillVBoxesNomeAndIngr(Pizzeria pizzeria, ArrayList<VBox> vBoxNomeDescr, ArrayList<Label> nomiLabels, ArrayList<Label> ingrLabels) {
        for (int i = 0; i < pizzeria.getMenu().values().size(); i++) {
            vBoxNomeDescr.add(new VBox(10));
            vBoxNomeDescr.get(i).getChildren().addAll(nomiLabels.get(i), ingrLabels.get(i));
        }
    }

    /** Riempie i vari HBoxes di Labels e Buttons. */
    private static void fillHBoxesPrezzoAndBottoni(Pizzeria pizzeria, ArrayList<HBox> hBoxPrezzoBottoni, ArrayList<Label> prezziLabels, ArrayList<VBox> vBoxBottoni) {
        for (int i = 0; i < pizzeria.getMenu().values().size(); i++) {
            hBoxPrezzoBottoni.add(new HBox(10));
            hBoxPrezzoBottoni.get(i).getChildren().addAll(prezziLabels.get(i), vBoxBottoni.get(i));
        }
    }

    /** Riempie il GridPane con Labels e Buttons per ogni pizza del menu. */
    private static GridPane setGridPaneContraints(Pizzeria pizzeria, ArrayList<VBox> vBoxNomeDescr, ArrayList<HBox> hBoxPrezzoBottoni) {
        int i;
        for (i = 0; i < pizzeria.getMenu().values().size(); i++) {
            GridPane.setConstraints(vBoxNomeDescr.get(i), 1, i + 1);
            GridPane.setConstraints(hBoxPrezzoBottoni.get(i), 2, i + 1);
        }
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(1);
        gridPane.setVgap(30);
        for (i = 0; i < pizzeria.getMenu().values().size(); i++) {
            gridPane.getChildren().add(hBoxPrezzoBottoni.get(i));
            gridPane.getChildren().add(vBoxNomeDescr.get(i));
        }
        return gridPane;
    }

    /** Costruisce il backButton */
    private Button createBackButton(Pizzeria pizzeria, Stage window, Customer customer) {
        backButton = new Button("← Annulla ordine");
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage();
            homePage.display(window, pizzeria, customer);
        });
        return backButton;
    }

    /** Costruisce il bottone di conferma, che consente il passaggio ad OrderPage2 */
    private Button createConfirmButton(Customer customer, Stage window, Order order, Pizzeria pizzeria) {
        OrderPage2 orderPage2 = new OrderPage2();
        confirmButton = new Button("Prosegui →");
        confirmButton.setId("confirmButton");
        confirmButton.setOnAction(e -> {
            if(order.getNumTemporaryPizze()>0)
                orderPage2.display(window, order, pizzeria, customer);
            else
                GenericAlert.display("Attenzione: aggiungere almeno una pizza all'ordine!");
        });
        return confirmButton;
    }
}
