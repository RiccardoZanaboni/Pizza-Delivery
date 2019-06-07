package graphicElements;

import graphicAlerts.GenericAlert;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import pizzeria.Customer;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;

import java.util.ArrayList;

/**
 * OrderPage1 è la pagina di ordinazione che consente di visualizzare il menu,
 * selezionando numero e tipo delle pizze desiderate, oltre ad eventuali modifiche.
 *
 * Vi si accede tramite il bottone "Nuovo Ordine" in MenuPage oppure "Indietro" in OrderPage2.
 * Cliccando "Indietro", l'ordine viene annullato e si torna a MenuPage.
 * Cliccando "Avanti", si salvano i dati e si accede alla pagina OrderPage2.
 */

public class OrderPage1 {

    private static Scene scene2;
    private Button confirmButton;
    private static Button backButton;

    public void display(Stage window, Order order, Pizzeria pizzeria, Customer customer) {

        ArrayList<Label> nomiLabels = new ArrayList<>();
        ArrayList<Label> ingrLabels = new ArrayList<>();
        ArrayList<Label> prezziLabels = new ArrayList<>();
        //ArrayList<Label> countPizzeLabels = new ArrayList<>();
        ArrayList<ButtonAddPizza> addButtons = new ArrayList<>();
        ArrayList<ButtonModPizza> modButtons = new ArrayList<>();
        //ArrayList<ButtonRmvPizza> rmvButtons = new ArrayList<>();
        ArrayList<VBox> vBoxBottoni = new ArrayList<>();
        ArrayList<VBox> vBoxNomeDescr = new ArrayList<>();
        ArrayList<HBox> hBoxPrezzoBottoni = new ArrayList<>();

        //Label countModificheLabel = new Label();
        //int countModifiche = 0;
        //countModificheLabel.setText("" + countModifiche);

        //Label pizzasInCart = new Label();
        Image image = new Image("graphicElements/images/shopping_cart.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        HBox shoppingCartBox = new HBox(order.getNumTemporaryPizze());
        shoppingCartBox.setId("shoppingCart");

        Button shoppingCartButton = new Button();
        shoppingCartButton.setGraphic(imageView);
        shoppingCartButton.setOnAction(e->{
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.display(order, shoppingCartButton);
        });
        shoppingCartButton.setText(order.getNumPizze() + "");
        shoppingCartBox.getChildren().addAll(shoppingCartButton);

        /* metodi esterni per non appesantire */
        //fillLabelsAndButtons(pizzasInCart, pizzeria, order, nomiLabels, ingrLabels, prezziLabels, countPizzeLabels, addButtons, modButtons, rmvButtons, countModificheLabel);
        //fillLabelsAndButtons(pizzasInCart, pizzeria, order, nomiLabels, ingrLabels, prezziLabels, countPizzeLabels, addButtons, modButtons, countModificheLabel);
        //fillLabelsAndButtons(shoppingCartButton, pizzeria, order, nomiLabels, ingrLabels, prezziLabels, addButtons, modButtons, countModificheLabel);
        fillLabelsAndButtons(shoppingCartButton, pizzeria, order, nomiLabels, ingrLabels, prezziLabels, addButtons, modButtons);
        fillVBoxesNomeAndIngr(pizzeria, vBoxNomeDescr, nomiLabels, ingrLabels);
        //fillVBoxesButtons(pizzeria, vBoxBottoni, addButtons, modButtons, rmvButtons);
        fillVBoxesButtons(pizzeria, vBoxBottoni, addButtons, modButtons);
        fillHBoxesPrezzoAndBottoni(pizzeria, hBoxPrezzoBottoni, prezziLabels, vBoxBottoni);

        //Label modifiche = new Label();
        //modifiche.setText("Pizze Modificate");
        //HBox hBoxMod = new HBox();
        //hBoxMod.getChildren().addAll(countModificheLabel, modifiche);
        //countModificheLabel.setText("" + order.getCountModifiedPizze());

        OrderPage2 orderPage2 = new OrderPage2();

        int tot = 0;
        confirmButton = createConfirmButton(customer, window, orderPage2, scene2, order, pizzeria, tot);
        backButton = createBackButton(pizzeria, order, window, customer);

        HBox hBoxIntestazione = new HBox();
        Label labelOrdine = new Label("Totali pizze ordinate: ");
        hBoxIntestazione.getChildren().addAll(labelOrdine, shoppingCartBox);
        hBoxIntestazione.setAlignment(Pos.CENTER);
        hBoxIntestazione.setId("hboxIntestazione");

        HBox hBoxAvantiIndietro = new HBox(10);
        hBoxAvantiIndietro.getChildren().addAll(backButton, confirmButton);
        hBoxAvantiIndietro.setAlignment(Pos.CENTER);

        GridPane gridPane;
        //gridPane = setGridPaneContraints(pizzeria, countPizzeLabels, vBoxNomeDescr, hBoxPrezzoBottoni, countModificheLabel, modifiche);
        gridPane = setGridPaneContraints(pizzeria, vBoxNomeDescr, hBoxPrezzoBottoni);
        gridPane.getColumnConstraints().add(new ColumnConstraints(40));
        gridPane.getColumnConstraints().add(new ColumnConstraints(520));
        gridPane.getColumnConstraints().add(new ColumnConstraints(250));
        gridPane.setId("grid");

        /* Metto il gridPane con tutte le pizze all'interno di uno ScrollPane */
        ScrollPane scroll = new ScrollPane(gridPane);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.prefWidthProperty().bind(window.widthProperty());
        scroll.prefHeightProperty().bind(window.heightProperty());
        HBox hBox = new HBox();
        scroll.setPadding(new Insets(10, 1, 5, 10));
        hBox.getChildren().add(scroll);
        hBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox();
        layout.setId("layout");
        layout.getChildren().addAll(hBoxIntestazione, hBox, hBoxAvantiIndietro);
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());

        layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if(ke.getCode()== KeyCode.ENTER) {
                    confirmButton.fire();
                }if(ke.getCode()== KeyCode.CONTROL||ke.getCode()== KeyCode.BACK_SPACE)
                   backButton.fire();
                if(ke.getCode()==KeyCode.SHIFT)
                {   shoppingCartButton.fire();
                }
            }
        });


        scene2 = new Scene(layout, 800, 600);
        scene2.getStylesheets().addAll(this.getClass().getResource("cssStyle/buttonsAndLabelsAndBackgroundStyle.css").toExternalForm());
        window.setScene(scene2);
        window.show();
    }

    /**
     * Costruisce i vari Labels e Buttons per ogni pizza del menu.
     */
    private static void fillLabelsAndButtons(Button shoppingCartButton, Pizzeria pizzeria, Order order,
                                             ArrayList<Label> nomiLabels, ArrayList<Label> ingrLabels,
                                             ArrayList<Label> prezziLabels, ArrayList<ButtonAddPizza> addButtons,
                                             ArrayList<ButtonModPizza> modButtons) {
        int i = 0;
        for (Pizza pizzaMenu : pizzeria.getMenu().values()) {
            nomiLabels.add(i, new Label(pizzaMenu.getName(true)));
            nomiLabels.get(i).setId("nomiLabel");
            ingrLabels.add(i, new Label(pizzaMenu.getDescription()));
            prezziLabels.add(i, new Label(pizzaMenu.getPrice() + " €"));
            //countPizzeLabels.add(i, new Label());
            //countPizzeLabels.get(i).setId("countpizzeLabel");
            //countPizzeLabels.get(i).setText("" + pizzeria.getMenu().get(pizzaMenu.getName()).getCount());
            //addButtons.add(new ButtonAddPizza(pizzasInCart, order, pizzeria, countPizzeLabels.get(i), pizzaMenu.getName()));
            addButtons.add(new ButtonAddPizza(shoppingCartButton, order, pizzaMenu));
            modButtons.add(new ButtonModPizza(shoppingCartButton, order, pizzeria, pizzaMenu.getName(false)));
            //modButtons.add(new ButtonModPizza(shoppingCartButton, order, pizzeria, pizzaMenu.getName(), countModificheLabel));
            //rmvButtons.add(new ButtonRmvPizza(pizzasInCart, order, pizzeria, countPizzeLabels.get(i), pizzaMenu.getName()));
            i++;
        }
    }

    /**
     * Riempie i vari HBoxes di Buttons.
     */
    private static void fillVBoxesButtons(Pizzeria pizzeria, ArrayList<VBox> vBoxBottoni, ArrayList<ButtonAddPizza> addButtons, ArrayList<ButtonModPizza> modButtons) {
        for (int i = 0; i < pizzeria.getMenu().values().size(); i++) {
            vBoxBottoni.add(new VBox(5));
            vBoxBottoni.get(i).getChildren().addAll(addButtons.get(i), modButtons.get(i));
        }
    }

    /**
     * Riempie i vari VBoxes di Labels.
     */
    private static void fillVBoxesNomeAndIngr(Pizzeria pizzeria, ArrayList<VBox> vBoxNomeDescr, ArrayList<Label> nomiLabels, ArrayList<Label> ingrLabels) {
        for (int i = 0; i < pizzeria.getMenu().values().size(); i++) {
            vBoxNomeDescr.add(new VBox(10));
            vBoxNomeDescr.get(i).getChildren().addAll(nomiLabels.get(i), ingrLabels.get(i));
        }
    }

    /**
     * Riempie i vari HBoxes di Labels e Buttons.
     */
    private static void fillHBoxesPrezzoAndBottoni(Pizzeria pizzeria, ArrayList<HBox> hBoxPrezzoBottoni, ArrayList<Label> prezziLabels, ArrayList<VBox> vBoxBottoni) {
        for (int i = 0; i < pizzeria.getMenu().values().size(); i++) {
            hBoxPrezzoBottoni.add(new HBox(10));
            hBoxPrezzoBottoni.get(i).getChildren().addAll(prezziLabels.get(i), vBoxBottoni.get(i));
        }
    }

    /**
     * Riempie il GridPane con Labels e Buttons per ogni pizza del menu.
     */
    private static GridPane setGridPaneContraints(Pizzeria pizzeria, ArrayList<VBox> vBoxNomeDescr, ArrayList<HBox> hBoxPrezzoBottoni) {
        int i;
        for (i = 0; i < pizzeria.getMenu().values().size(); i++) {
            //GridPane.setConstraints(countPizzeLabels.get(i), 0, i + 1);
            GridPane.setConstraints(vBoxNomeDescr.get(i), 1, i + 1);
            GridPane.setConstraints(hBoxPrezzoBottoni.get(i), 2, i + 1);
        }
        //GridPane.setConstraints(countModificheLabel, 0, i + 1);
        //GridPane.setConstraints(modifiche, 1, i + 1);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(1);
        gridPane.setVgap(30);

        /*for (i = 0; i < pizzeria.getMenu().values().size(); i++) {
            gridPane.getChildren().add(countPizzeLabels.get(i));
        }
        gridPane.getChildren().add(countModificheLabel);*/
        for (i = 0; i < pizzeria.getMenu().values().size(); i++) {
            gridPane.getChildren().add(hBoxPrezzoBottoni.get(i));
        }
        //gridPane.getChildren().add(modifiche);
        for (i = 0; i < pizzeria.getMenu().values().size(); i++) {
            gridPane.getChildren().add(vBoxNomeDescr.get(i));
        }
        return gridPane;
    }

    /**
     * Costruisce il backButton
     */
    private Button createBackButton(Pizzeria pizzeria, Order order, Stage window, Customer customer) {
        backButton = new Button("← Torna indietro");
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
            /*int i = 0;
            for (Pizza pizzaMenu : pizzeria.getMenu().values()) {
                removePizze(rmvButtons.get(i), pizzeria, order, pizzaMenu.getName());
                i++;
            }*/
            MenuPage menuPage = new MenuPage();
            menuPage.display(window, pizzeria, customer);
        });
        return backButton;
    }

    /**
     * Costruisce il bottone di conferma, che consente il passaggio ad OrderPage2
     */
    private Button createConfirmButton(Customer customer, Stage window, OrderPage2 orderPage2, Scene scene2, Order order, Pizzeria pizzeria, int tot) {
        confirmButton = new Button("Prosegui  →");
        confirmButton.setId("confirmButton");
        confirmButton.setOnAction(e -> {
            if(order.getNumTemporaryPizze()>0)
                //System.out.println("Sono state ordinate in tutto " + tot + " pizze.");
                //System.out.println(order.getOrderedPizze());
                orderPage2.display(window, scene2, order, pizzeria, tot, customer);
            else
                GenericAlert.display("Attenzione: aggiungere almeno una pizza all'ordine!");
        });
        return confirmButton;
    }

    /**
     * Attiva buttonRmvPizza sulla pizza selezionata
     */
    private static void removePizze(ButtonRmvPizza buttonRmvPizza, Pizzeria pizzeria, Order order, String pizza) {
        while (order.searchPizza(pizzeria.getMenu().get(pizza))) {
            buttonRmvPizza.fire();
        }
    }

    /** Restituisce il backButton */
    static Button getBackButton() {
        return backButton;
    }

    private class VK_ENTER {
    }
}
