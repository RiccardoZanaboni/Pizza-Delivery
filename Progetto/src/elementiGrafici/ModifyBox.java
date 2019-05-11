package elementiGrafici;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import pizzeria.Ingredienti;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;

import java.util.HashMap;

public class ModifyBox{
	static boolean answer=false;

	public static void setAnswer() {
        ModifyBox.answer= !ModifyBox.answer;
    }

    public static boolean display(Order order, Pizzeria pizzeria, Label pizza) {

		Pizza pizzaMenu = new Pizza(
				pizzeria.getMenu().get(pizza.getText().toUpperCase()).getNomeCamel(),
				pizzeria.getMenu().get(pizza.getText().toUpperCase()).getIngredienti(),
				pizzeria.getMenu().get(pizza.getText().toUpperCase()).getPrezzo());

		HashMap<String, Ingredienti> ingr = new HashMap<>(pizzaMenu.getIngredienti());
		Pizza nuovaPizza = new Pizza(pizzaMenu.getNomeMaiusc(), ingr, pizzaMenu.getPrezzo());

		Stage window = new Stage();

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);

		Label alici = new Label(Ingredienti.ALICI.name());
		ButtonAddIngr addAlici = new ButtonAddIngr(Ingredienti.ALICI, nuovaPizza);
		ButtonRmvIngr removeAlici = new ButtonRmvIngr(Ingredienti.ALICI, nuovaPizza);
		HBox hBox1 = new HBox(4);
		hBox1.getChildren().addAll(addAlici, removeAlici);
		gridPane.getChildren().addAll(alici, hBox1);
		GridPane.setConstraints(alici, 1,1);
		GridPane.setConstraints(hBox1, 5,1);

		Label basilico = new Label(Ingredienti.BASILICO.name());
		ButtonAddIngr addBasilico = new ButtonAddIngr(Ingredienti.BASILICO, nuovaPizza);
		ButtonRmvIngr removeBasilico = new ButtonRmvIngr(Ingredienti.BASILICO, nuovaPizza);
		HBox hBox2 = new HBox(4);
		hBox2.getChildren().addAll(addBasilico, removeBasilico);
		gridPane.getChildren().addAll(basilico, hBox2);
		GridPane.setConstraints(basilico, 1,2);
		GridPane.setConstraints(hBox2, 5,2);

		Label capperi = new Label(Ingredienti.CAPPERI.name());
		ButtonAddIngr addCapperi = new ButtonAddIngr(Ingredienti.CAPPERI, nuovaPizza);
		ButtonRmvIngr removeCapperi = new ButtonRmvIngr(Ingredienti.CAPPERI, nuovaPizza);
		HBox hBox3 = new HBox(4);
		hBox3.getChildren().addAll(addCapperi, removeCapperi);
		gridPane.getChildren().addAll(capperi, hBox3);
		GridPane.setConstraints(capperi, 1,3);
		GridPane.setConstraints(hBox3, 5,3);

		Label carciofi = new Label(Ingredienti.CARCIOFI.name());
		ButtonAddIngr addCarciofi = new ButtonAddIngr(Ingredienti.CARCIOFI, nuovaPizza);
		ButtonRmvIngr removeCarciofi = new ButtonRmvIngr(Ingredienti.CARCIOFI, nuovaPizza);
		HBox hBox4 = new HBox(4);
		hBox4.getChildren().addAll(addCarciofi, removeCarciofi);
		gridPane.getChildren().addAll(carciofi, hBox4);
		GridPane.setConstraints(carciofi, 1,4);
		GridPane.setConstraints(hBox4, 5,4);

		Label cotto = new Label(Ingredienti.COTTO.name());
		ButtonAddIngr addCotto = new ButtonAddIngr(Ingredienti.COTTO, nuovaPizza);
		ButtonRmvIngr removeCotto = new ButtonRmvIngr(Ingredienti.COTTO, nuovaPizza);
		HBox hBox5 = new HBox(4);
		hBox5.getChildren().addAll(addCotto, removeCotto);
		gridPane.getChildren().addAll(cotto, hBox5);
		GridPane.setConstraints(cotto, 1,5);
		GridPane.setConstraints(hBox5, 5,5);

		Label crudo = new Label(Ingredienti.CRUDO.name());
		ButtonAddIngr addCrudo = new ButtonAddIngr(Ingredienti.CRUDO, nuovaPizza);
		ButtonRmvIngr removeCrudo = new ButtonRmvIngr(Ingredienti.CRUDO, nuovaPizza);
		HBox hBox6 = new HBox(4);
		hBox6.getChildren().addAll(addCrudo, removeCrudo);
		gridPane.getChildren().addAll(crudo, hBox6);
		GridPane.setConstraints(crudo, 1,6);
		GridPane.setConstraints(hBox6, 5,6);

		Label funghi= new Label(Ingredienti.FUNGHI.name());
		ButtonAddIngr addFunghi = new ButtonAddIngr(Ingredienti.FUNGHI, nuovaPizza);
		ButtonRmvIngr removeFunghi= new ButtonRmvIngr(Ingredienti.FUNGHI, nuovaPizza);
		HBox hBox7 = new HBox(4);
		hBox7.getChildren().addAll(addFunghi, removeFunghi);
		gridPane.getChildren().addAll(funghi, hBox7);
		GridPane.setConstraints(funghi, 1,7);
		GridPane.setConstraints(hBox7, 5,7);

		Label gorgonzola = new Label(Ingredienti.GORGONZOLA.name());
		ButtonAddIngr addGorgonzola = new ButtonAddIngr(Ingredienti.GORGONZOLA, nuovaPizza);
		ButtonRmvIngr removeGorgonzola= new ButtonRmvIngr(Ingredienti.GORGONZOLA, nuovaPizza);
		HBox hBox8 = new HBox(4);
		hBox8.getChildren().addAll(addGorgonzola, removeGorgonzola);
		gridPane.getChildren().addAll(gorgonzola, hBox8);
		GridPane.setConstraints(gorgonzola, 1,8);
		GridPane.setConstraints(hBox8, 5,8);

		Label mozzarella= new Label(Ingredienti.MOZZARELLA.name());
		ButtonAddIngr addMozzarella = new ButtonAddIngr(Ingredienti.MOZZARELLA, nuovaPizza);
		ButtonRmvIngr removeMozzarella = new ButtonRmvIngr(Ingredienti.MOZZARELLA, nuovaPizza);
		HBox hBox9 = new HBox(4);
		hBox9.getChildren().addAll(addMozzarella, removeMozzarella);
		gridPane.getChildren().addAll(mozzarella, hBox9);
		GridPane.setConstraints(mozzarella, 1,9);
		GridPane.setConstraints(hBox9, 5,9);

		Label mozzarellaDiBufala = new Label(Ingredienti.MOZZARELLA_DI_BUFALA.name());
		ButtonAddIngr addMozzarellaDiBufala = new ButtonAddIngr(Ingredienti.MOZZARELLA_DI_BUFALA, nuovaPizza);
		ButtonRmvIngr removeMozzarellaDiBufala= new ButtonRmvIngr(Ingredienti.MOZZARELLA_DI_BUFALA, nuovaPizza);
		HBox hBox10 = new HBox(4);
		hBox10.getChildren().addAll(addMozzarellaDiBufala, removeMozzarellaDiBufala);
		gridPane.getChildren().addAll(mozzarellaDiBufala, hBox10);
		GridPane.setConstraints(mozzarellaDiBufala, 1,10);
		GridPane.setConstraints(hBox10, 5,10);

		Label oliveNere = new Label(Ingredienti.OLIVE_NERE.name());
		ButtonAddIngr addOliveNere = new ButtonAddIngr(Ingredienti.OLIVE_NERE, nuovaPizza);
		ButtonRmvIngr removeOliveNere = new ButtonRmvIngr(Ingredienti.OLIVE_NERE, nuovaPizza);
		HBox hBox11 = new HBox(4);
		hBox11.getChildren().addAll(addOliveNere, removeOliveNere);
		gridPane.getChildren().addAll(oliveNere, hBox11);
		GridPane.setConstraints(oliveNere, 1,11);
		GridPane.setConstraints(hBox11, 5,11);

		Label origano = new Label(Ingredienti.ORIGANO.name());
		ButtonAddIngr addOrigano = new ButtonAddIngr(Ingredienti.ORIGANO, nuovaPizza);
		ButtonRmvIngr removeOrigano = new ButtonRmvIngr(Ingredienti.ORIGANO, nuovaPizza);
		HBox hBox12 = new HBox(4);
		hBox12.getChildren().addAll(addOrigano, removeOrigano);
		gridPane.getChildren().addAll(origano, hBox12);
		GridPane.setConstraints(origano, 1,12);
		GridPane.setConstraints(hBox12, 5,12);

		Label panna= new Label(Ingredienti.PANNA.name());
		ButtonAddIngr addPanna = new ButtonAddIngr(Ingredienti.PANNA, nuovaPizza);
		ButtonRmvIngr removePanna= new ButtonRmvIngr(Ingredienti.PANNA, nuovaPizza);
		HBox hBox13 = new HBox(4);
		hBox13.getChildren().addAll(addPanna, removePanna);
		gridPane.getChildren().addAll(panna, hBox13);
		GridPane.setConstraints(panna, 1,13);
		GridPane.setConstraints(hBox13, 5,13);

		Label patatine= new Label(Ingredienti.PATATINE.name());
		ButtonAddIngr addPatatine = new ButtonAddIngr(Ingredienti.PATATINE, nuovaPizza);
		ButtonRmvIngr removePatatine= new ButtonRmvIngr(Ingredienti.PATATINE, nuovaPizza);
		HBox hBox14 = new HBox(4);
		hBox14.getChildren().addAll(addPatatine, removePatatine);
		gridPane.getChildren().addAll(patatine, hBox14);
		GridPane.setConstraints(patatine, 1,14);
		GridPane.setConstraints(hBox14, 5,14);

		Label peperoni= new Label(Ingredienti.PEPERONI.name());
		ButtonAddIngr addPeperoni = new ButtonAddIngr(Ingredienti.PEPERONI, nuovaPizza);
		ButtonRmvIngr removePeperoni = new ButtonRmvIngr(Ingredienti.PEPERONI, nuovaPizza);
		HBox hBox15 = new HBox(4);
		hBox15.getChildren().addAll(addPeperoni, removePeperoni);
		gridPane.getChildren().addAll(peperoni, hBox15);
		GridPane.setConstraints(peperoni, 1,15);
		GridPane.setConstraints(hBox15, 5,15);

		Label pomodorini = new Label(Ingredienti.POMODORINI.name());
		ButtonAddIngr addPomodorini = new ButtonAddIngr(Ingredienti.POMODORINI, nuovaPizza);
		ButtonRmvIngr removePomodorini = new ButtonRmvIngr(Ingredienti.POMODORINI, nuovaPizza);
		HBox hBox16 = new HBox(4);
		hBox16.getChildren().addAll(addPomodorini, removePomodorini);
		gridPane.getChildren().addAll(pomodorini, hBox16);
		GridPane.setConstraints(pomodorini, 1,16);
		GridPane.setConstraints(hBox16, 5,16);

		Label pomodoro = new Label(Ingredienti.POMODORO.name());
		ButtonAddIngr addPomodoro = new ButtonAddIngr(Ingredienti.POMODORO, nuovaPizza);
		ButtonRmvIngr removePomodoro = new ButtonRmvIngr(Ingredienti.POMODORO, nuovaPizza);
		HBox hBox17 = new HBox(4);
		hBox17.getChildren().addAll(addPomodoro, removePomodoro);
		gridPane.getChildren().addAll(pomodoro, hBox17);
		GridPane.setConstraints(pomodoro, 1,17);
		GridPane.setConstraints(hBox17, 5,17);

		Label rucola = new Label(Ingredienti.RUCOLA.name());
		ButtonAddIngr addRucola = new ButtonAddIngr(Ingredienti.RUCOLA, nuovaPizza);
		ButtonRmvIngr removeRucola = new ButtonRmvIngr(Ingredienti.RUCOLA, nuovaPizza);
		HBox hBox18 = new HBox(4);
		hBox18.getChildren().addAll(addRucola, removeRucola);
		gridPane.getChildren().addAll(rucola, hBox18);
		GridPane.setConstraints(rucola, 1,18);
		GridPane.setConstraints(hBox18, 5,18);

		Label salamePiccante = new Label(Ingredienti.SALAME_PICCANTE.name());
		ButtonAddIngr addSalamePiccante = new ButtonAddIngr(Ingredienti.SALAME_PICCANTE, nuovaPizza);
		ButtonRmvIngr removeSalamePiccante = new ButtonRmvIngr(Ingredienti.SALAME_PICCANTE, nuovaPizza);
		HBox hBox19 = new HBox(4);
		hBox19.getChildren().addAll(addSalamePiccante, removeSalamePiccante);
		gridPane.getChildren().addAll(salamePiccante, hBox19);
		GridPane.setConstraints(salamePiccante, 1,19);
		GridPane.setConstraints(hBox19, 5,19);

		Label salsiccia = new Label(Ingredienti.SALSICCIA.name());
		ButtonAddIngr addSalsiccia = new ButtonAddIngr(Ingredienti.SALSICCIA, nuovaPizza);
		ButtonRmvIngr removeSalsiccia = new ButtonRmvIngr(Ingredienti.SALSICCIA, nuovaPizza);
		HBox hBox20 = new HBox(4);
		hBox20.getChildren().addAll(addSalsiccia, removeSalsiccia);
		gridPane.getChildren().addAll(salsiccia, hBox20);
		GridPane.setConstraints(salsiccia, 1,20);
		GridPane.setConstraints(hBox20, 5,20);

		Label speck = new Label(Ingredienti.SPECK.name());
		ButtonAddIngr addSpeck = new ButtonAddIngr(Ingredienti.SPECK, nuovaPizza);
		ButtonRmvIngr removeSpeck= new ButtonRmvIngr(Ingredienti.SPECK, nuovaPizza);
		HBox hBox21 = new HBox(4);
		hBox21.getChildren().addAll(addSpeck, removeSpeck);
		gridPane.getChildren().addAll(speck, hBox21);
		GridPane.setConstraints(speck, 1,21);
		GridPane.setConstraints(hBox21, 5,21);

		Label wurstel = new Label(Ingredienti.WURSTEL.name());
		ButtonAddIngr addWurstel = new ButtonAddIngr(Ingredienti.WURSTEL, nuovaPizza);
		ButtonRmvIngr removeWurstel = new ButtonRmvIngr(Ingredienti.WURSTEL, nuovaPizza);
		HBox hBox22 = new HBox(4);
		hBox22.getChildren().addAll(addWurstel, removeWurstel);
		gridPane.getChildren().addAll(wurstel, hBox22);
		GridPane.setConstraints(wurstel, 1,22);
		GridPane.setConstraints(hBox22, 5,22);

		Label belleDonne = new Label(Ingredienti.BELLE_DONNE.name());
		ButtonAddIngr addBelleDonne = new ButtonAddIngr(Ingredienti.BELLE_DONNE, nuovaPizza);
		ButtonRmvIngr removeBelleDonne = new ButtonRmvIngr(Ingredienti.BELLE_DONNE, nuovaPizza);
		HBox hBox23 = new HBox(4);
		hBox23.getChildren().addAll(addBelleDonne, removeBelleDonne);
		gridPane.getChildren().addAll(belleDonne, hBox23);
		GridPane.setConstraints(belleDonne, 1,23);
		GridPane.setConstraints(hBox23, 5,23);

		Label galanteria = new Label(Ingredienti.GALANTERIA.name());
		ButtonAddIngr addGalanteria = new ButtonAddIngr(Ingredienti.GALANTERIA, nuovaPizza);
		ButtonRmvIngr removeGalanteria = new ButtonRmvIngr(Ingredienti.GALANTERIA, nuovaPizza);
		HBox hBox24 = new HBox(4);
		hBox24.getChildren().addAll(addGalanteria, removeGalanteria);
		gridPane.getChildren().addAll(galanteria, hBox24);
		GridPane.setConstraints(galanteria, 1,24);
		GridPane.setConstraints(hBox24, 5,24);

		Label gemme = new Label(Ingredienti.GEMME_DELL_INFINITO.name());
		ButtonAddIngr addGemme = new ButtonAddIngr(Ingredienti.GEMME_DELL_INFINITO, nuovaPizza);
		ButtonRmvIngr removeGemme = new ButtonRmvIngr(Ingredienti.GEMME_DELL_INFINITO, nuovaPizza);
		HBox hBox25 = new HBox(4);
		hBox25.getChildren().addAll(addGemme, removeGemme);
		gridPane.getChildren().addAll(gemme, hBox25);
		GridPane.setConstraints(gemme, 1,25);
		GridPane.setConstraints(hBox25, 5,25);

		Label onnip = new Label(Ingredienti.ONNIPOTENZA.name());
		ButtonAddIngr addOnnip = new ButtonAddIngr(Ingredienti.ONNIPOTENZA, nuovaPizza);
		ButtonRmvIngr removeOnnip = new ButtonRmvIngr(Ingredienti.ONNIPOTENZA, nuovaPizza);
		HBox hBox26 = new HBox(4);
		hBox26.getChildren().addAll(addOnnip, removeOnnip);
		gridPane.getChildren().addAll(onnip, hBox26);
		GridPane.setConstraints(onnip, 1,26);
		GridPane.setConstraints(hBox26, 5,26);

		Button confirmButton = new Button("Conferma le modifiche");
		confirmButton.setOnAction(e -> {
			order.addPizza(nuovaPizza, 1);
			answer = true;
			window.close();
		});

		ScrollPane scrollPane = new ScrollPane(gridPane);
		VBox layout = new VBox();
		layout.getChildren().addAll(scrollPane, confirmButton);
		layout.setAlignment(Pos.CENTER);

		// Impedisce di fare azioni sulle altre finestre
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Modifica la pizza");
		window.setMinWidth(400);
		window.setMaxWidth(400);
		window.setMaxHeight(300);
		// Mostra la finestra e attende di essere chiusa
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		return answer;
	}
}
