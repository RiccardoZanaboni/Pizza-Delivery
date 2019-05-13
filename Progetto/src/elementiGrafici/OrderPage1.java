/*package elementiGrafici;

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
    private static int countModifiche = 0;
    private static Button avantiButton;
    private static Button indietroButton;

    public static void display(Stage window, Scene scene1, Scene scene3, Order order, Pizzeria pizzeria) {

        //FIXME SISTEMARE DISTANZA TRA BOTTONI ADD, REMOVE, MODIFICA DI UNA PIZZA E QUELLA SUCCESSIVA

        GridPane gridPane;
        Label countModificheLabel = new Label();
        countModificheLabel.setText("" + countModifiche);

        // Inserisco tutte le pizze

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        // questo blocco andrebbe a sostituire da qui fino al prossimo TODO.

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

        riempiLabelsAndButtons(pizzeria,order,nomiLabels,ingrLabels,prezziLabels,countPizzeLabels,addButtons,modButtons,rmvButtons,countModificheLabel);
        riempiVBoxBottoni(pizzeria,vBoxBottoni,addButtons,modButtons,rmvButtons);
        riempiVBoxNomeAndIngr(pizzeria,vBoxNomeDescr,nomiLabels,ingrLabels);
        riempiHBoxPrezzoAndBottoni(pizzeria,hBoxPrezzoBottoni,prezziLabels,vBoxBottoni);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////


        //TODO AGGIUNGERE BOTTONE PER POTER TOGLIERE UNA PIZZA MODIFICATA

        Label modifiche = new Label();
        modifiche.setText("Pizze Modificate");
        HBox hBoxMod = new HBox();
        hBoxMod.getChildren().addAll(countModificheLabel, modifiche);

        OrderPage2 orderPage2 = new OrderPage2();

        avantiButton = creaAvantiButton(window,orderPage2,scene2,order,pizzeria,tot);
        indietroButton = creaIndietroButton(pizzeria,order,window,scene1,rmvButtons);

        HBox hBoxIntestazione = new HBox();
        Label labelOrdine = new Label("Ordine");
        hBoxIntestazione.getChildren().add(labelOrdine);
        hBoxIntestazione.setMinSize(600, 50);
        hBoxIntestazione.setAlignment(Pos.CENTER);

        HBox hBoxAvantiIndietro = new HBox(10);
        hBoxAvantiIndietro.getChildren().addAll(indietroButton, avantiButton);
        hBoxAvantiIndietro.setMinSize(600, 50);
        hBoxAvantiIndietro.setAlignment(Pos.CENTER);

        //GridPane.setConstraints(hBoxIntestazione, 1, 0);

        setGridPaneContraints(pizzeria,countPizzeLabels,vBoxNomeDescr,hBoxPrezzoBottoni,countModificheLabel,hBoxMod);
        gridPane = addEverythingToGridPane(pizzeria,countPizzeLabels, hBoxPrezzoBottoni, hBoxMod, vBoxNomeDescr);

        // Metto il gridPane con tutte le pizze all'interno di uno ScrollPane
        ScrollPane scrollPane1 = new ScrollPane(gridPane);
        scrollPane1.fitToHeightProperty();
        scrollPane1.fitToWidthProperty();
        VBox layout = new VBox();
        layout.getChildren().addAll(hBoxIntestazione, scrollPane1, hBoxAvantiIndietro);

        scene2 = new Scene(layout, 600, 600);
        window.setScene(scene2);
    }

    private static void riempiLabelsAndButtons(Pizzeria pizzeria, Order order, ArrayList<Label> nomiLabels, ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels, ArrayList<Label> countPizzeLabels, ArrayList<ButtonAddPizza> addButtons, ArrayList<ButtonModPizza> modButtons, ArrayList<ButtonRmvPizza> rmvButtons, Label countModificheLabel) {
        int i=0;
        for (Pizza pizzaMenu: pizzeria.getMenu().values()) {
            nomiLabels.add(i, new Label(pizzaMenu.getNomeCamel()));
            ingrLabels.add(i, new Label(pizzaMenu.getDescrizione()));
            prezziLabels.add(i, new Label(pizzaMenu.getPrezzo() + " €"));
            countPizzeLabels.add(i, new Label());
            countPizzeLabels.get(i).setText("" + pizzeria.getMenu().get(pizzaMenu.getNomeMaiusc()).getCount());
            addButtons.add(new ButtonAddPizza(order, pizzeria, countPizzeLabels.get(i), pizzaMenu.getNomeMaiusc()));
            modButtons.add(new ButtonModPizza(order, pizzeria, pizzaMenu.getNomeMaiusc(),countModificheLabel));
            rmvButtons.add(new ButtonRmvPizza(order, pizzeria, countPizzeLabels.get(i), pizzaMenu.getNomeMaiusc()));
            i++;
        }
    }

    private static void riempiVBoxBottoni(Pizzeria pizzeria, ArrayList<VBox> vBoxBottoni, ArrayList<ButtonAddPizza> addButtons, ArrayList<ButtonModPizza> modButtons, ArrayList<ButtonRmvPizza> rmvButtons) {
        int i=0;
        for (Pizza pizzaMenu: pizzeria.getMenu().values()) {
            vBoxBottoni.add(new VBox(5));
            vBoxBottoni.get(i).getChildren().addAll(addButtons.get(i),modButtons.get(i),rmvButtons.get(i));
            i++;
        }
    }

    private static void riempiVBoxNomeAndIngr(Pizzeria pizzeria, ArrayList<VBox> vBoxNomeDescr, ArrayList<Label> nomiLabels, ArrayList<Label> ingrLabels) {
        int i=0;
        for (Pizza pizzaMenu: pizzeria.getMenu().values()) {
            vBoxNomeDescr.add(new VBox(10));
            vBoxNomeDescr.get(i).getChildren().addAll(nomiLabels.get(i), ingrLabels.get(i));
            i++;
        }
    }

    private static void riempiHBoxPrezzoAndBottoni(Pizzeria pizzeria, ArrayList<HBox> hBoxPrezzoBottoni, ArrayList<Label> prezziLabels, ArrayList<VBox> vBoxBottoni) {
        int i=0;
        for (Pizza pizzaMenu: pizzeria.getMenu().values()) {
            hBoxPrezzoBottoni.add(new HBox(10));
            hBoxPrezzoBottoni.get(i).getChildren().addAll(prezziLabels.get(i), vBoxBottoni.get(i));
            i++;
        }
    }

    private static GridPane addEverythingToGridPane(Pizzeria pizzeria, ArrayList<Label> countPizzeLabels, ArrayList<HBox> hBoxPrezzoBottoni, HBox hBoxMod, ArrayList<VBox> vBoxNomeDescr) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(30);
        gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        int i=0;
        gridPane.getChildren().add(hBoxMod);
        for (Pizza pizzaMenu: pizzeria.getMenu().values()) {
            gridPane.getChildren().add(countPizzeLabels.get(i));
            i++;
        }
        i=0;
        for (Pizza pizzaMenu: pizzeria.getMenu().values()) {
            gridPane.getChildren().add(hBoxPrezzoBottoni.get(i));
            i++;
        }
        i=0;
        for (Pizza pizzaMenu: pizzeria.getMenu().values()) {
            gridPane.getChildren().add(vBoxNomeDescr.get(i));
            i++;
        }
        return gridPane;
    }

    private static void setGridPaneContraints(Pizzeria pizzeria, ArrayList<Label> countPizzeLabels, ArrayList<VBox> vBoxNomeDescr, ArrayList<HBox> hBoxPrezzoBottoni, Label countModificheLabel, HBox hBoxMod) {
        int i=0;
        for (Pizza pizzaMenu: pizzeria.getMenu().values()) {
            GridPane.setConstraints(countPizzeLabels.get(i),0,i+1);
            GridPane.setConstraints(vBoxNomeDescr.get(i),1,i+1);
            GridPane.setConstraints(hBoxPrezzoBottoni.get(i),2,i+1);
            i++;
        }
        GridPane.setConstraints(countModificheLabel,0,i+1);
        GridPane.setConstraints(hBoxMod, 1, i+1);
    }

    public static Button creaIndietroButton(Pizzeria pizzeria, Order order, Stage window, Scene scene1, ArrayList<ButtonRmvPizza> rmvButtons) {
        indietroButton = new Button("Torna indietro ←");
        indietroButton.setOnAction(e -> {
            int i=0;
            for (Pizza pizzaMenu: pizzeria.getMenu().values()) {
                rimuoviPizze(rmvButtons.get(i),pizzeria,order,pizzaMenu.getNomeMaiusc());
                i++;
            }
            window.setScene(scene1);
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

    public static void rimuoviPizze(ButtonRmvPizza buttonRmvPizza, Pizzeria pizzeria, Order order, String pizza){
        while(order.searchPizza(pizzeria.getMenu().get(pizza))) {
            buttonRmvPizza.fire();
        }
    }
    */


package elementiGrafici;

import avvisiGrafici.AlertNumPizzeMax;
import avvisiGrafici.AlertNumeroPizzeMin;
import elementiGrafici.ModifyBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import pizzeria.Order;
import pizzeria.Pizzeria;

public class OrderPage1 {

    static Scene scene2;
    public static int tot;
    static int countModifiche=0;

    private static Button avantiButton;

    public static void display(Stage window, Scene scene1, Scene scene3, Order order, Pizzeria pizzeria) {

        //FIXME SISTEMARE DISTANZA TRA BOTTONI ADD, REMOVE, MODIFICA DI UNA PIZZA E QUELLA SUCCESSIVA

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10,10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        Label countModificheLabel = new Label();
        countModificheLabel.setText(""+countModifiche);

        // Inserisco tutte le pizze

        Label margherita = new Label(pizzeria.getMenu().get("MARGHERITA").getNomeCamel());
        Label margheritaIngre = new Label(pizzeria.getMenu().get("MARGHERITA").getDescrizione());
        Label margheritaPrezzo = new Label(pizzeria.getMenu().get("MARGHERITA").getPrezzo()+"€");
        Label countMargheritaLabel= new Label();
        ButtonAddPizza addMargherita = new ButtonAddPizza(order, pizzeria, countMargheritaLabel, pizzeria.getMenu().get("MARGHERITA").getNomeMaiusc());
        ButtonRmvPizza rimuoviMargherita =new ButtonRmvPizza(order, pizzeria, countMargheritaLabel, pizzeria.getMenu().get("MARGHERITA").getNomeMaiusc());
        ButtonModPizza buttonModMargherita=new ButtonModPizza(order,pizzeria,pizzeria.getMenu().get("MARGHERITA").getNomeMaiusc(),countModificheLabel);
        countMargheritaLabel.setText(""+pizzeria.getMenu().get("MARGHERITA").getCount());

        HBox hBox1 = new HBox(10);
        VBox vBox1 = new VBox(5);
        VBox vBox1A = new VBox(10);
        vBox1.getChildren().addAll(addMargherita, buttonModMargherita, rimuoviMargherita);
        vBox1A.getChildren().addAll(margherita, margheritaIngre);
        hBox1.getChildren().addAll(margheritaPrezzo, vBox1);



        Label italia = new Label(pizzeria.getMenu().get("ITALIA").getNomeCamel());
        Label italiaIngre = new Label(pizzeria.getMenu().get("ITALIA").getDescrizione());
        Label italiaPrezzo = new Label(pizzeria.getMenu().get("ITALIA").getPrezzo()+"€");
        Label countItaliaLabel= new Label();
        ButtonAddPizza addItalia = new ButtonAddPizza(order, pizzeria, countItaliaLabel, pizzeria.getMenu().get("ITALIA").getNomeMaiusc());
        ButtonRmvPizza rimuoviItalia =new ButtonRmvPizza(order, pizzeria, countItaliaLabel, pizzeria.getMenu().get("ITALIA").getNomeMaiusc());
        ButtonModPizza modItalia=new ButtonModPizza(order,pizzeria,pizzeria.getMenu().get("ITALIA").getNomeMaiusc(),countModificheLabel);
        countItaliaLabel.setText(""+pizzeria.getMenu().get("ITALIA").getCount());

        HBox hBox2 = new HBox(10);
        VBox vBox2 = new VBox(5);
        VBox vBox2A = new VBox(10);
        vBox2.getChildren().addAll(addItalia, modItalia,rimuoviItalia);
        vBox2A.getChildren().addAll(italia, italiaIngre);
        hBox2.getChildren().addAll(italiaPrezzo, vBox2);

        Label marinara= new Label(pizzeria.getMenu().get("MARINARA").getNomeCamel());
        Label marinaraIngre = new Label(pizzeria.getMenu().get("MARINARA").getDescrizione());
        Label marinaraPrezzo = new Label(pizzeria.getMenu().get("MARINARA").getPrezzo()+"€");
        Label countMarinaraLabel=new Label();


        ButtonAddPizza addMarinara = new ButtonAddPizza(order, pizzeria, countMarinaraLabel, pizzeria.getMenu().get("MARINARA").getNomeMaiusc());
        ButtonRmvPizza rimuoviMarinara =new ButtonRmvPizza(order, pizzeria, countMarinaraLabel, pizzeria.getMenu().get("MARINARA").getNomeMaiusc());
        ButtonModPizza modMarinara=new ButtonModPizza(order,pizzeria,pizzeria.getMenu().get("MARINARA").getNomeMaiusc(),countModificheLabel);
        countMarinaraLabel.setText(""+pizzeria.getMenu().get("MARINARA").getCount());

        HBox hBox3 = new HBox(10);
        VBox vBox3 = new VBox(5);
        VBox vBox3A = new VBox(10);
        vBox3.getChildren().addAll(addMarinara, modMarinara,rimuoviMarinara);
        vBox3A.getChildren().addAll(marinara, marinaraIngre);
        hBox3.getChildren().addAll(marinaraPrezzo, vBox3);

        Label patatine= new Label(pizzeria.getMenu().get("PATATINE").getNomeCamel());
        Label patatineIngre = new Label(pizzeria.getMenu().get("PATATINE").getDescrizione());
        Label patatinePrezzo = new Label(pizzeria.getMenu().get("PATATINE").getPrezzo()+"€");
        Label countPatatineLabel=new Label();

        ButtonAddPizza addPatatine = new ButtonAddPizza(order, pizzeria, countPatatineLabel, pizzeria.getMenu().get("PATATINE").getNomeMaiusc());
        ButtonRmvPizza rimuoviPatatine =new ButtonRmvPizza(order, pizzeria, countPatatineLabel, pizzeria.getMenu().get("PATATINE").getNomeMaiusc());
        ButtonModPizza modPatatine=new ButtonModPizza(order,pizzeria,pizzeria.getMenu().get("PATATINE").getNomeMaiusc(),countModificheLabel);
        countPatatineLabel.setText(""+pizzeria.getMenu().get("PATATINE").getCount());

        HBox hBox4 = new HBox(10);
        VBox vBox4 = new VBox(5);
        VBox vBox4A = new VBox(10);
        vBox4.getChildren().addAll(addPatatine, modPatatine,rimuoviPatatine);
        vBox4A.getChildren().addAll(patatine, patatineIngre);
        hBox4.getChildren().addAll(patatinePrezzo, vBox4);

        Label wurstel= new Label(pizzeria.getMenu().get("WURSTEL").getNomeCamel());
        Label wurstelIngre = new Label(pizzeria.getMenu().get("WURSTEL").getDescrizione());
        Label wurstelPrezzo = new Label(pizzeria.getMenu().get("WURSTEL").getPrezzo()+"€");
        Label countWurstelLabel=new Label();
        ButtonAddPizza addWurstel = new ButtonAddPizza(order, pizzeria,countWurstelLabel, pizzeria.getMenu().get("WURSTEL").getNomeMaiusc());
        ButtonRmvPizza rimuoviWurstel =new ButtonRmvPizza(order, pizzeria, countWurstelLabel, pizzeria.getMenu().get("WURSTEL").getNomeMaiusc());
        ButtonModPizza modWurstel=new ButtonModPizza(order,pizzeria,pizzeria.getMenu().get("WURSTEL").getNomeMaiusc(),countModificheLabel);
        countWurstelLabel.setText(""+pizzeria.getMenu().get("WURSTEL").getCount());

        HBox hBox5 = new HBox(10);
        VBox vBox5 = new VBox(5);
        VBox vBox5A = new VBox(10);
        vBox5.getChildren().addAll(addWurstel, modWurstel,rimuoviWurstel);
        vBox5A.getChildren().addAll(wurstel, wurstelIngre);
        hBox5.getChildren().addAll(wurstelPrezzo, vBox5);

        Label capricciosa= new Label(pizzeria.getMenu().get("CAPRICCIOSA").getNomeCamel());
        Label capricciosaIngre = new Label(pizzeria.getMenu().get("CAPRICCIOSA").getDescrizione());
        Label capricciosaPrezzo = new Label(pizzeria.getMenu().get("CAPRICCIOSA").getPrezzo()+"€");
        Label countCapricciosaLabel=new Label();
        ButtonAddPizza addCapricciosa = new ButtonAddPizza(order, pizzeria,countCapricciosaLabel, pizzeria.getMenu().get("CAPRICCIOSA").getNomeMaiusc());
        ButtonRmvPizza rimuoviCapricciosa =new ButtonRmvPizza(order, pizzeria, countCapricciosaLabel, pizzeria.getMenu().get("CAPRICCIOSA").getNomeMaiusc());
        ButtonModPizza modCapricciosa=new ButtonModPizza(order,pizzeria,pizzeria.getMenu().get("CAPRICCIOSA").getNomeMaiusc(),countModificheLabel);
        countCapricciosaLabel.setText(""+pizzeria.getMenu().get("CAPRICCIOSA").getCount());

        HBox hBox6 = new HBox(10);
        VBox vBox6 = new VBox(5);
        VBox vBox6A = new VBox(10);
        vBox6.getChildren().addAll(addCapricciosa, modCapricciosa,rimuoviCapricciosa);
        vBox6A.getChildren().addAll(capricciosa, capricciosaIngre);
        hBox6.getChildren().addAll(capricciosaPrezzo, vBox6);

        Label napoli = new Label(pizzeria.getMenu().get("NAPOLI").getNomeCamel());
        Label napoliIngre = new Label(pizzeria.getMenu().get("NAPOLI").getDescrizione());
        Label napoliPrezzo = new Label(pizzeria.getMenu().get("NAPOLI").getPrezzo()+"€");
        Label countNapoliLabel=new Label();
        ButtonAddPizza addNapoli = new ButtonAddPizza(order, pizzeria,countNapoliLabel, pizzeria.getMenu().get("NAPOLI").getNomeMaiusc());
        ButtonRmvPizza rimuoviNapoli=new ButtonRmvPizza(order, pizzeria, countNapoliLabel, pizzeria.getMenu().get("NAPOLI").getNomeMaiusc());
        ButtonModPizza modNapoli=new ButtonModPizza(order,pizzeria,pizzeria.getMenu().get("NAPOLI").getNomeMaiusc(),countModificheLabel);
        countNapoliLabel.setText(""+pizzeria.getMenu().get("NAPOLI").getCount());

        HBox hBox7 = new HBox(10);
        VBox vBox7 = new VBox(5);
        VBox vBox7A = new VBox(10);
        vBox7.getChildren().addAll(addNapoli, modNapoli,rimuoviNapoli);
        vBox7A.getChildren().addAll(napoli, napoliIngre);
        hBox7.getChildren().addAll(napoliPrezzo, vBox7);

        Label cotto = new Label(pizzeria.getMenu().get("COTTO").getNomeCamel());
        Label cottoIngre = new Label(pizzeria.getMenu().get("COTTO").getDescrizione());
        Label cottoPrezzo = new Label(pizzeria.getMenu().get("COTTO").getPrezzo()+"€");
        Label countCottoLabel=new Label();
        ButtonAddPizza addCotto = new ButtonAddPizza(order, pizzeria,countCottoLabel, pizzeria.getMenu().get("COTTO").getNomeMaiusc());
        ButtonRmvPizza rimuoviCotto=new ButtonRmvPizza(order, pizzeria, countCottoLabel, pizzeria.getMenu().get("COTTO").getNomeMaiusc());
        ButtonModPizza modCotto = new ButtonModPizza(order,pizzeria,pizzeria.getMenu().get("COTTO").getNomeMaiusc(),countModificheLabel);
        countCottoLabel.setText(""+pizzeria.getMenu().get("COTTO").getCount());


        HBox hBox8 = new HBox(10);
        VBox vBox8 = new VBox(5);
        VBox vBox8A = new VBox(10);
        vBox8.getChildren().addAll(addCotto, modCotto,rimuoviCotto);
        vBox8A.getChildren().addAll(cotto, cottoIngre);
        hBox8.getChildren().addAll(cottoPrezzo, vBox8);

        Label cottoFunghi = new Label(pizzeria.getMenu().get("COTTO E FUNGHI").getNomeCamel());
        Label cottoFunghiIngre = new Label(pizzeria.getMenu().get("COTTO E FUNGHI").getDescrizione());
        Label cottoFunghiPrezzo = new Label(pizzeria.getMenu().get("COTTO E FUNGHI").getPrezzo()+"€");
        Label countCottoFunghiLabel=new Label();
        ButtonAddPizza addCottoFunghi = new ButtonAddPizza(order, pizzeria,countCottoFunghiLabel, pizzeria.getMenu().get("COTTO E FUNGHI").getNomeMaiusc());
        ButtonRmvPizza rimuoviCottoFunghi =new ButtonRmvPizza(order, pizzeria, countCottoFunghiLabel, pizzeria.getMenu().get("COTTO E FUNGHI").getNomeMaiusc());
        ButtonModPizza modCottoFunghi = new ButtonModPizza(order,pizzeria,pizzeria.getMenu().get("COTTO E FUNGHI").getNomeMaiusc(),countModificheLabel);
        countCottoFunghiLabel.setText(""+pizzeria.getMenu().get("COTTO E FUNGHI").getCount());

        HBox hBox9 = new HBox(10);
        VBox vBox9 = new VBox(5);
        VBox vBox9A = new VBox(10);
        vBox9.getChildren().addAll(addCottoFunghi, modCottoFunghi,rimuoviCottoFunghi);
        vBox9A.getChildren().addAll(cottoFunghi, cottoFunghiIngre);
        hBox9.getChildren().addAll(cottoFunghiPrezzo, vBox9);

        Label americana = new Label(pizzeria.getMenu().get("AMERICANA").getNomeCamel());
        Label americanaIngre = new Label(pizzeria.getMenu().get("AMERICANA").getDescrizione());
        Label americanaPrezzo = new Label(pizzeria.getMenu().get("AMERICANA").getPrezzo()+"€");
        Label countAmericanaLabel=new Label();
        ButtonAddPizza addAmericana = new ButtonAddPizza(order, pizzeria,countAmericanaLabel, pizzeria.getMenu().get("AMERICANA").getNomeMaiusc());
        ButtonRmvPizza rimuoviAmericana =new ButtonRmvPizza(order, pizzeria, countAmericanaLabel, pizzeria.getMenu().get("AMERICANA").getNomeMaiusc());
        ButtonModPizza modAmericana = new ButtonModPizza(order,pizzeria,pizzeria.getMenu().get("AMERICANA").getNomeMaiusc(),countModificheLabel);
        countAmericanaLabel.setText(""+pizzeria.getMenu().get("AMERICANA").getCount());

        HBox hBox10 = new HBox(10);
        VBox vBox10 = new VBox(5);
        VBox vBox10A = new VBox(10);
        vBox10.getChildren().addAll(addAmericana, modAmericana,rimuoviAmericana);
        vBox10A.getChildren().addAll(americana, americanaIngre);
        hBox10.getChildren().addAll(americanaPrezzo, vBox10);

        //TODO AGGIUNGERE BOTTONE PER POTER TOGLIERE UNA PIZZA MODIFICATA

        Label modifiche=new Label();
        modifiche.setText("Pizze Modificate");
        HBox hBoxMod=new HBox();
        hBoxMod.getChildren().addAll(countModificheLabel,modifiche);

        // Definisco bottone per tornare indietro

        Button indietroButton = new Button("Torna indietro ←");
        indietroButton.setOnAction(e -> {
            rimuoviPizze(rimuoviAmericana,pizzeria,order,"AMERICANA");
            rimuoviPizze(rimuoviCapricciosa,pizzeria,order,"CAPRICCIOSA");
            rimuoviPizze(rimuoviCotto,pizzeria,order,"COTTO");
            rimuoviPizze(rimuoviCottoFunghi,pizzeria,order,"COTTO E FUNGHI");
            rimuoviPizze(rimuoviItalia,pizzeria,order,"ITALIA");
            rimuoviPizze(rimuoviMargherita,pizzeria,order,"MARGHERITA");
            rimuoviPizze(rimuoviMarinara,pizzeria,order,"MARINARA");
            rimuoviPizze(rimuoviNapoli,pizzeria,order,"NAPOLI");
            rimuoviPizze(rimuoviPatatine,pizzeria,order,"PATATINE");
            rimuoviPizze(rimuoviWurstel,pizzeria,order,"WURSTEL");
            window.setScene(scene1);
        });

        // Definisco bottone per andare avanti

        OrderPage2 orderPage2 = new OrderPage2();
        avantiButton = new Button("Prosegui  →");
        avantiButton.setOnAction(e -> {
            System.out.println("Sono state ordinate in tutto "+tot+" pizze.");
            System.out.println(order.getPizzeordinate());
            orderPage2.display(window, scene2, order, pizzeria, tot);
        });

        HBox hBoxIntestazione = new HBox();
        Label label = new Label("Ordine");
        hBoxIntestazione.getChildren().add(label);
        hBoxIntestazione.setMinSize(600, 50);
        hBoxIntestazione.setAlignment(Pos.CENTER);

        HBox hBoxButton = new HBox(10);
        hBoxButton.getChildren().addAll(indietroButton, avantiButton);
        hBoxButton.setMinSize(600, 50);
        hBoxButton.setAlignment(Pos.CENTER);

        //GridPane.setConstraints(hBoxIntestazione, 1, 0);

        GridPane.setConstraints(countMargheritaLabel, 0,1);
        GridPane.setConstraints(countItaliaLabel, 0,2);
        GridPane.setConstraints(countMarinaraLabel, 0,3);
        GridPane.setConstraints(countPatatineLabel, 0,4);
        GridPane.setConstraints(countWurstelLabel, 0,5);
        GridPane.setConstraints(countCapricciosaLabel, 0,6);
        GridPane.setConstraints(countNapoliLabel, 0,7);
        GridPane.setConstraints(countCottoLabel, 0,8);
        GridPane.setConstraints(countCottoFunghiLabel, 0,9);
        GridPane.setConstraints(countAmericanaLabel, 0,10);
        GridPane.setConstraints(countModificheLabel, 0,11);

        GridPane.setConstraints(vBox1A, 1,1);
        GridPane.setConstraints(vBox2A, 1,2);
        GridPane.setConstraints(vBox3A, 1,3);
        GridPane.setConstraints(vBox4A, 1,4);
        GridPane.setConstraints(vBox5A, 1,5);
        GridPane.setConstraints(vBox6A, 1,6);
        GridPane.setConstraints(vBox7A, 1,7);
        GridPane.setConstraints(vBox8A, 1,8);
        GridPane.setConstraints(vBox9A, 1,9);
        GridPane.setConstraints(vBox10A, 1,10);

        GridPane.setConstraints(hBox1, 2,1);
        GridPane.setConstraints(hBox2, 2,2);
        GridPane.setConstraints(hBox3, 2,3);
        GridPane.setConstraints(hBox4, 2,4);
        GridPane.setConstraints(hBox5, 2,5);
        GridPane.setConstraints(hBox6, 2,6);
        GridPane.setConstraints(hBox7, 2,7);
        GridPane.setConstraints(hBox8, 2,8);
        GridPane.setConstraints(hBox9, 2,9);
        GridPane.setConstraints(hBox10, 2,10);

        GridPane.setConstraints(hBoxMod,1,12);
        //GridPane.setConstraints(hBoxButton, 0,13);
        gridPane.setVgap(30);
        gridPane.getColumnConstraints().add(new ColumnConstraints(50));

        gridPane.getChildren().addAll(
                hBoxMod, countMargheritaLabel, countItaliaLabel, countMarinaraLabel, countPatatineLabel,countWurstelLabel,
                countCapricciosaLabel, countNapoliLabel, countCottoLabel, countCottoFunghiLabel, countAmericanaLabel,
                hBox1, hBox2, hBox3, hBox4, hBox5, hBox6, hBox7, hBox8, hBox9, hBox10,
                vBox1A, vBox2A, vBox3A, vBox4A ,vBox5A, vBox6A, vBox7A, vBox8A, vBox9A, vBox10A
        );

        // Metto il gridPane con tutte le pizze all'interno di uno ScrollPane
        javafx.scene.control.ScrollPane scrollPane1 = new javafx.scene.control.ScrollPane(gridPane);
        scrollPane1.fitToHeightProperty();
        scrollPane1.fitToWidthProperty();
        VBox layout = new VBox();
        layout.getChildren().addAll(hBoxIntestazione, scrollPane1, hBoxButton);


        //Scene scene2;
        scene2 = new Scene(layout, 600, 600);
        window.setScene(scene2);
    }




    public static void rimuoviPizze(ButtonRmvPizza buttonRmvPizza, Pizzeria pizzeria, Order order,String pizza ){
        while(order.searchPizza(pizzeria.getMenu().get(pizza)))
        { buttonRmvPizza.fire(); }
    }
          /*public void addPizzaToOrder (pizzeria.Order order, pizzeria.Pizzeria pizzeria, Label label) {
            if (tot<4) {
              order.addPizza(pizzeria.getMenu().get(label.getText().toUpperCase()), 1);
              tot++;
              System.out.println(pizzeria.getMenu().get(label.getText().toUpperCase()));
            } else
              avvisiGrafici.AlertNumPizzeMax.display();
          }*/

}
























          /*public void addPizzaToOrder (pizzeria.Order order, pizzeria.Pizzeria pizzeria, Label label) {
            if (tot<4) {
              order.addPizza(pizzeria.getMenu().get(label.getText().toUpperCase()), 1);
              tot++;
              System.out.println(pizzeria.getMenu().get(label.getText().toUpperCase()));
            } else
              avvisiGrafici.AlertNumPizzeMax.display();
          }*/







        /*hBoxM.getChildren().addAll(indietroButton, avantiButton);
            BorderPane borderPane = new BorderPane();
            borderPane.getChildren().addAll(scrollPane1, hBoxM);
            borderPane.setTop(scrollPane1);
            borderPane.setBottom(hBoxM);
        VBox vBox = new VBox();
vBox.getChildren().addAll(scrollPane1, hBoxM);*/