package elementiGrafici;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;

import java.util.ArrayList;

public class OrderPage1 {

    public static Scene scene2;
    public static int tot;
    private int countModifiche = 0;
    private static Button avantiButton;
    private static Button indietroButton;

    public void display(Stage window, Scene scene1, Scene scene3, Order order, Pizzeria pizzeria) {

        //FIXME SISTEMARE DISTANZA TRA BOTTONI ADD, REMOVE, MODIFICA DI UNA PIZZA E QUELLA SUCCESSIVA

        GridPane gridPane;
        Label countModificheLabel = new Label();
        countModificheLabel.setText("" + countModifiche);

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

        riempiLabelsAndButtons(pizzeria, order, nomiLabels, ingrLabels, prezziLabels, countPizzeLabels, addButtons, modButtons, rmvButtons, countModificheLabel);
        riempiVBoxBottoni(pizzeria, vBoxBottoni, addButtons, modButtons, rmvButtons);
        riempiVBoxNomeAndIngr(pizzeria, vBoxNomeDescr, nomiLabels, ingrLabels);
        riempiHBoxPrezzoAndBottoni(pizzeria, hBoxPrezzoBottoni, prezziLabels, vBoxBottoni);

        //TODO AGGIUNGERE BOTTONE PER POTER TOGLIERE UNA PIZZA MODIFICATA

        Label modifiche = new Label();
        modifiche.setText("Pizze Modificate");
        HBox hBoxMod = new HBox();
        hBoxMod.getChildren().addAll(countModificheLabel, modifiche);
        countModificheLabel.setText("" + order.getCountPizzeModificate());

        OrderPage2 orderPage2 = new OrderPage2();

        avantiButton = creaAvantiButton(window, orderPage2, scene2, order, pizzeria, tot);
        indietroButton = creaIndietroButton(pizzeria, order, window, scene1, rmvButtons);

        HBox hBoxIntestazione = new HBox();
        Label labelOrdine = new Label("Ordine");
        hBoxIntestazione.getChildren().add(labelOrdine);
        hBoxIntestazione.setMinSize(600, 50);
        hBoxIntestazione.setAlignment(Pos.CENTER);

        HBox hBoxAvantiIndietro = new HBox(10);
        hBoxAvantiIndietro.getChildren().addAll(indietroButton, avantiButton);
        hBoxAvantiIndietro.setMinSize(600, 50);
        hBoxAvantiIndietro.setAlignment(Pos.CENTER);

        setGridPaneContraints(pizzeria, countPizzeLabels, vBoxNomeDescr, hBoxPrezzoBottoni, countModificheLabel, hBoxMod);
        gridPane = addEverythingToGridPane(pizzeria, countPizzeLabels, hBoxPrezzoBottoni, hBoxMod, vBoxNomeDescr);

        // Metto il gridPane con tutte le pizze all'interno di uno ScrollPane
        ScrollPane scrollPane1 = new ScrollPane(gridPane);
        scrollPane1.fitToHeightProperty();
        scrollPane1.fitToWidthProperty();
        VBox layout = new VBox();
        layout.getChildren().addAll(hBoxIntestazione, scrollPane1, hBoxAvantiIndietro);

        scene2 = new Scene(layout, 900, 700);
        window.setScene(scene2);
    }

    private static void riempiLabelsAndButtons(Pizzeria pizzeria, Order order, ArrayList<Label> nomiLabels, ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels, ArrayList<Label> countPizzeLabels, ArrayList<ButtonAddPizza> addButtons, ArrayList<ButtonModPizza> modButtons, ArrayList<ButtonRmvPizza> rmvButtons, Label countModificheLabel) {
        int i = 0;
        for (Pizza pizzaMenu : pizzeria.getMenu().values()) {
            nomiLabels.add(i, new Label(pizzaMenu.getNomeCamel()));
            ingrLabels.add(i, new Label(pizzaMenu.getDescrizione()));
            prezziLabels.add(i, new Label(pizzaMenu.getPrezzo() + " €"));
            countPizzeLabels.add(i, new Label());
            countPizzeLabels.get(i).setText("" + pizzeria.getMenu().get(pizzaMenu.getNomeMaiusc()).getCount());
            addButtons.add(new ButtonAddPizza(order, pizzeria, countPizzeLabels.get(i), pizzaMenu.getNomeMaiusc()));
            modButtons.add(new ButtonModPizza(order, pizzeria, pizzaMenu.getNomeMaiusc(), countModificheLabel));
            rmvButtons.add(new ButtonRmvPizza(order, pizzeria, countPizzeLabels.get(i), pizzaMenu.getNomeMaiusc()));
            i++;
        }
    }

    private static void riempiVBoxBottoni(Pizzeria pizzeria, ArrayList<VBox> vBoxBottoni, ArrayList<ButtonAddPizza> addButtons, ArrayList<ButtonModPizza> modButtons, ArrayList<ButtonRmvPizza> rmvButtons) {
        for (int i=0; i< pizzeria.getMenu().values().size();i++) {
            vBoxBottoni.add(new VBox(5));
            vBoxBottoni.get(i).getChildren().addAll(addButtons.get(i), modButtons.get(i), rmvButtons.get(i));
        }
    }

    private static void riempiVBoxNomeAndIngr(Pizzeria pizzeria, ArrayList<VBox> vBoxNomeDescr, ArrayList<Label> nomiLabels, ArrayList<Label> ingrLabels) {
        for (int i=0; i<pizzeria.getMenu().values().size(); i++) {
            vBoxNomeDescr.add(new VBox(10));
            vBoxNomeDescr.get(i).getChildren().addAll(nomiLabels.get(i), ingrLabels.get(i));
        }
    }

    private static void riempiHBoxPrezzoAndBottoni(Pizzeria pizzeria, ArrayList<HBox> hBoxPrezzoBottoni, ArrayList<Label> prezziLabels, ArrayList<VBox> vBoxBottoni) {
		for (int i=0; i<pizzeria.getMenu().values().size(); i++) {
			hBoxPrezzoBottoni.add(new HBox(10));
            hBoxPrezzoBottoni.get(i).getChildren().addAll(prezziLabels.get(i), vBoxBottoni.get(i));
        }
    }

    private static GridPane addEverythingToGridPane(Pizzeria pizzeria, ArrayList<Label> countPizzeLabels, ArrayList<HBox> hBoxPrezzoBottoni, HBox hBoxMod, ArrayList<VBox> vBoxNomeDescr) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(30);
        gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        gridPane.getChildren().add(hBoxMod);
		for (int i=0; i<pizzeria.getMenu().values().size(); i++) {
			gridPane.getChildren().add(countPizzeLabels.get(i));
        }
		for (int i=0; i<pizzeria.getMenu().values().size(); i++) {
			gridPane.getChildren().add(hBoxPrezzoBottoni.get(i));
		}
		for (int i=0; i<pizzeria.getMenu().values().size(); i++) {
            gridPane.getChildren().add(vBoxNomeDescr.get(i));
        }
        return gridPane;
    }

    private static void setGridPaneContraints(Pizzeria pizzeria, ArrayList<Label> countPizzeLabels, ArrayList<VBox> vBoxNomeDescr, ArrayList<HBox> hBoxPrezzoBottoni, Label countModificheLabel, HBox hBoxMod) {
		int i=0;
    	for (i=0; i<pizzeria.getMenu().values().size(); i++) {
			GridPane.setConstraints(countPizzeLabels.get(i), 0, i + 1);
            GridPane.setConstraints(vBoxNomeDescr.get(i), 1, i + 1);
            GridPane.setConstraints(hBoxPrezzoBottoni.get(i), 2, i + 1);
        }
        GridPane.setConstraints(countModificheLabel, 0, i + 1);
        GridPane.setConstraints(hBoxMod, 1, i + 1);
    }

    public static Button creaIndietroButton(Pizzeria pizzeria, Order order, Stage window, Scene scene1, ArrayList<ButtonRmvPizza> rmvButtons) {
        indietroButton = new Button("Torna indietro ←");
        indietroButton.setOnAction(e -> {
            int i = 0;
            for (Pizza pizzaMenu : pizzeria.getMenu().values()) {
                rimuoviPizze(rmvButtons.get(i), pizzeria, order, pizzaMenu.getNomeMaiusc());
                i++;
            }
            MenuPage menuPage = new MenuPage();
            menuPage.display(window, scene1, pizzeria);
        });
        return indietroButton;
    }

    public static Button creaAvantiButton(Stage window, OrderPage2 orderPage2, Scene scene2, Order order, Pizzeria pizzeria, int tot) {
        avantiButton = new Button("Prosegui  →");
        avantiButton.setOnAction(e -> {
            System.out.println("Sono state ordinate in tutto " + tot + " pizze.");
            System.out.println(order.getPizzeordinate());
            orderPage2.display(window, scene2, order, pizzeria, tot);
        });
        return avantiButton;
    }

    public static void rimuoviPizze(ButtonRmvPizza buttonRmvPizza, Pizzeria pizzeria, Order order, String pizza) {
        while (order.searchPizza(pizzeria.getMenu().get(pizza))) {
            buttonRmvPizza.fire();
        }
    }
    //TODO AGGIUNGERE BOTTONE PER POTER TOGLIERE UNA PIZZA MODIFICATA
}