package graphicElements;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.Services;

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

    public void display(Stage window, Scene scene1, Order order, Pizzeria pizzeria) {

		// TODO AGGIUNGERE BOTTONE PER POTER TOGLIERE UNA PIZZA MODIFICATA

		// FIXME SISTEMARE DISTANZA TRA BOTTONI ADD, REMOVE, MODIFICA DI UNA PIZZA E QUELLA SUCCESSIVA

        ArrayList<Label> nomiLabels = new ArrayList<>();
        ArrayList<Label> ingrLabels = new ArrayList<>();
        ArrayList<Label> prezziLabels = new ArrayList<>();
        ArrayList<Label> countPizzeLabels = new ArrayList<>();
        ArrayList<ButtonAddPizza> addButtons = new ArrayList<>();
        ArrayList<ButtonModPizza> modButtons = new ArrayList<>();
        ArrayList<ButtonRmvPizza> rmvButtons = new ArrayList<>();
        ArrayList<VBox> vBoxBottoni = new ArrayList<>();
        ArrayList<VBox> vBoxNomeDescr = new ArrayList<>();
        ArrayList<HBox> hBoxPrezzoBottoni = new ArrayList<>();

        Label countModificheLabel = new Label();
        int countModifiche = 0;
        countModificheLabel.setText("" + countModifiche);


        Label pizzasInCart = new Label();
        Image image = new Image("graphicElements/shopping_cart.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        HBox shoppingCartBox = new HBox(order.getNumPizzeProvvisorie());
        shoppingCartBox.setId("shoppingCart");
        pizzasInCart.setText(order.getNumPizzeProvvisorie() + "");
        Button shoppingCartButton = new Button();
        shoppingCartButton.setGraphic(imageView);
        shoppingCartButton.setText(pizzasInCart.getText()+"");
        shoppingCartButton.setOnAction(e->{
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.display(order, pizzeria, pizzasInCart);
        });
        shoppingCartBox.getChildren().addAll(shoppingCartButton);

        // metodi esterni per non appesantire
        fillLabelsAndButtons(pizzasInCart, pizzeria, order, nomiLabels, ingrLabels, prezziLabels, countPizzeLabels, addButtons, modButtons, rmvButtons, countModificheLabel);
        fillVBoxesNomeAndIngr(pizzeria, vBoxNomeDescr, nomiLabels, ingrLabels);
        fillVBoxesButtons(pizzeria, vBoxBottoni, addButtons, modButtons, rmvButtons);
        fillHBoxesPrezzoAndBottoni(pizzeria, hBoxPrezzoBottoni, prezziLabels, vBoxBottoni);

        //TODO AGGIUNGERE BOTTONE PER POTER TOGLIERE UNA PIZZA MODIFICATA

        Label modifiche = new Label();
        modifiche.setText("Pizze Modificate");
        HBox hBoxMod = new HBox();
        hBoxMod.getChildren().addAll(countModificheLabel, modifiche);
        countModificheLabel.setText("" + order.getCountModifiedPizze());

        OrderPage2 orderPage2 = new OrderPage2();

        int tot = 0;
        confirmButton = createConfirmButton(window, orderPage2, scene2, order, pizzeria, tot);
        backButton = createBackButton(pizzeria, order, window, scene1, rmvButtons);

        HBox hBoxIntestazione = new HBox();
        Label labelOrdine = new Label("Totali pizze ordinate: ");
        hBoxIntestazione.getChildren().addAll(labelOrdine, shoppingCartBox);
        hBoxIntestazione.setAlignment(Pos.CENTER);
        hBoxIntestazione.setId("hboxIntestazione");

        HBox hBoxAvantiIndietro = new HBox(10);
        hBoxAvantiIndietro.getChildren().addAll(backButton, confirmButton);
        hBoxAvantiIndietro.setAlignment(Pos.CENTER);

        GridPane gridPane;
        gridPane = setGridPaneContraints(pizzeria, countPizzeLabels, vBoxNomeDescr, hBoxPrezzoBottoni, countModificheLabel, modifiche);
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

        scene2 = new Scene(layout, 880, 600);
        scene2.getStylesheets().addAll(this.getClass().getResource("buttonsAndLabelsAndBackgroundStyle.css").toExternalForm());
        window.setScene(scene2);
        window.show();
    }

    /**
     * costruisce i vari Labels e Buttons per ogni pizza del menu.
     */
    private static void fillLabelsAndButtons(Label pizzasInCart, Pizzeria pizzeria, Order order, ArrayList<Label> nomiLabels, ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels, ArrayList<Label> countPizzeLabels, ArrayList<ButtonAddPizza> addButtons, ArrayList<ButtonModPizza> modButtons, ArrayList<ButtonRmvPizza> rmvButtons, Label countModificheLabel) {
        int i = 0;
        for (Pizza pizzaMenu : pizzeria.getMenu().values()) {
            nomiLabels.add(i, new Label(Services.getCamelName(pizzaMenu)));
            nomiLabels.get(i).setId("nomiLabel");
            ingrLabels.add(i, new Label(pizzaMenu.getDescription()));
            prezziLabels.add(i, new Label(pizzaMenu.getPrice() + " €"));
            countPizzeLabels.add(i, new Label());
            countPizzeLabels.get(i).setId("countpizzeLabel");
            countPizzeLabels.get(i).setText("" + pizzeria.getMenu().get(pizzaMenu.getMaiuscName()).getCount());
            addButtons.add(new ButtonAddPizza(pizzasInCart, order, pizzeria, countPizzeLabels.get(i), pizzaMenu.getMaiuscName()));
            modButtons.add(new ButtonModPizza(pizzasInCart, order, pizzeria, pizzaMenu.getMaiuscName(), countModificheLabel));
            //rmvButtons.add(new ButtonRmvPizza(pizzasInCart, order, pizzeria, countPizzeLabels.get(i), pizzaMenu.getMaiuscName()));
            i++;
        }
    }

    /**
     * riempie i vari HBoxes di Buttons.
     */
    private static void fillVBoxesButtons(Pizzeria pizzeria, ArrayList<VBox> vBoxBottoni, ArrayList<ButtonAddPizza> addButtons, ArrayList<ButtonModPizza> modButtons, ArrayList<ButtonRmvPizza> rmvButtons) {
        for (int i = 0; i < pizzeria.getMenu().values().size(); i++) {
            vBoxBottoni.add(new VBox(5));
            vBoxBottoni.get(i).getChildren().addAll(addButtons.get(i), modButtons.get(i));
        }
    }

    /**
     * riempie i vari VBoxes di Labels.
     */
    private static void fillVBoxesNomeAndIngr(Pizzeria pizzeria, ArrayList<VBox> vBoxNomeDescr, ArrayList<Label> nomiLabels, ArrayList<Label> ingrLabels) {
        for (int i = 0; i < pizzeria.getMenu().values().size(); i++) {
            vBoxNomeDescr.add(new VBox(10));
            vBoxNomeDescr.get(i).getChildren().addAll(nomiLabels.get(i), ingrLabels.get(i));
        }
    }

    /**
     * riempie i vari HBoxes di Labels e Buttons.
     */
    private static void fillHBoxesPrezzoAndBottoni(Pizzeria pizzeria, ArrayList<HBox> hBoxPrezzoBottoni, ArrayList<Label> prezziLabels, ArrayList<VBox> vBoxBottoni) {
        for (int i = 0; i < pizzeria.getMenu().values().size(); i++) {
            hBoxPrezzoBottoni.add(new HBox(10));
            hBoxPrezzoBottoni.get(i).getChildren().addAll(prezziLabels.get(i), vBoxBottoni.get(i));
        }
    }

    /**
     * riempie il GridPane con Labels e Buttons per ogni pizza del menu.
     */
    private static GridPane setGridPaneContraints(Pizzeria pizzeria, ArrayList<Label> countPizzeLabels, ArrayList<VBox> vBoxNomeDescr, ArrayList<HBox> hBoxPrezzoBottoni, Label countModificheLabel, Label modifiche) {
        int i;
        for (i = 0; i < pizzeria.getMenu().values().size(); i++) {
            GridPane.setConstraints(countPizzeLabels.get(i), 0, i + 1);
            GridPane.setConstraints(vBoxNomeDescr.get(i), 1, i + 1);
            GridPane.setConstraints(hBoxPrezzoBottoni.get(i), 2, i + 1);
        }
        GridPane.setConstraints(countModificheLabel, 0, i + 1);
        GridPane.setConstraints(modifiche, 1, i + 1);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(1);
        gridPane.setVgap(30);

        for (i = 0; i < pizzeria.getMenu().values().size(); i++) {
            gridPane.getChildren().add(countPizzeLabels.get(i));
        }
        gridPane.getChildren().add(countModificheLabel);
        for (i = 0; i < pizzeria.getMenu().values().size(); i++) {
            gridPane.getChildren().add(hBoxPrezzoBottoni.get(i));
        }
        gridPane.getChildren().add(modifiche);
        for (i = 0; i < pizzeria.getMenu().values().size(); i++) {
            gridPane.getChildren().add(vBoxNomeDescr.get(i));
        }
        return gridPane;
    }

    /**
     * costruisce il backButton
     */
    private Button createBackButton(Pizzeria pizzeria, Order order, Stage window, Scene scene1, ArrayList<ButtonRmvPizza> rmvButtons) {
        backButton = new Button("← Torna indietro");
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
            int i = 0;
            for (Pizza pizzaMenu : pizzeria.getMenu().values()) {
                removePizze(rmvButtons.get(i), pizzeria, order, pizzaMenu.getMaiuscName());
                i++;
            }
            MenuPage menuPage = new MenuPage();
            menuPage.display(window, pizzeria);
        });
        return backButton;
    }

    /**
     * costruisce il bottone di conferma, che consente il passaggio ad OrderPage2
     */
    private Button createConfirmButton(Stage window, OrderPage2 orderPage2, Scene scene2, Order order, Pizzeria pizzeria, int tot) {
        confirmButton = new Button("Prosegui  →");
        confirmButton.setId("confirmButton");
        confirmButton.setOnAction(e -> {
            //System.out.println("Sono state ordinate in tutto " + tot + " pizze.");
            //System.out.println(order.getOrderedPizze());
            orderPage2.display(window, scene2, order, pizzeria, tot);
        });
        return confirmButton;
    }

    /**
     * attiva buttonRmvPizza sulla pizza selezionata
     */
    private static void removePizze(ButtonRmvPizza buttonRmvPizza, Pizzeria pizzeria, Order order, String pizza) {
        while (order.searchPizza(pizzeria.getMenu().get(pizza))) {
            buttonRmvPizza.fire();
        }
    }

    /** restituisce il backButton */
    static Button getBackButton() {
        return backButton;
    }
}
