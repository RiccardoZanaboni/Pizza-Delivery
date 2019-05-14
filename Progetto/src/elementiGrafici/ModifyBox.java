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

    public static boolean display(Order order, Pizzeria pizzeria, String pizza) {

		Pizza pizzaMenu = new Pizza(
				pizzeria.getMenu().get(pizza).getNomeCamel(),
				pizzeria.getMenu().get(pizza).getIngredienti(),
				pizzeria.getMenu().get(pizza).getPrezzo());

		HashMap<String, Ingredienti> ingr = new HashMap<>(pizzaMenu.getIngredienti());
		Pizza nuovaPizza = new Pizza(pizzaMenu.getNomeMaiusc(), ingr, pizzaMenu.getPrezzo());

		Stage window = new Stage();

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.getColumnConstraints().add(new ColumnConstraints(210));
		gridPane.getColumnConstraints().add(new ColumnConstraints(70));
		gridPane.setVgap(8);
		gridPane.setHgap(10);

		Label alici = new Label(Ingredienti.ALICI.name());
		ButtonAddRmvIngr removeAlici = new ButtonAddRmvIngr(Ingredienti.ALICI, nuovaPizza);
		HBox hBox1 = new HBox(4);
		hBox1.getChildren().addAll(removeAlici);
		gridPane.getChildren().addAll(alici, hBox1);
		GridPane.setConstraints(alici, 0,1);
		GridPane.setConstraints(hBox1, 1,1);

		Label basilico = new Label(Ingredienti.BASILICO.name());
		ButtonAddRmvIngr removeBasilico = new ButtonAddRmvIngr(Ingredienti.BASILICO, nuovaPizza);
		HBox hBox2 = new HBox(4);
		hBox2.getChildren().addAll(removeBasilico);
		gridPane.getChildren().addAll(basilico, hBox2);
		GridPane.setConstraints(basilico, 0,2);
		GridPane.setConstraints(hBox2, 1,2);

		Label capperi = new Label(Ingredienti.CAPPERI.name());
		ButtonAddRmvIngr removeCapperi = new ButtonAddRmvIngr(Ingredienti.CAPPERI, nuovaPizza);
		HBox hBox3 = new HBox(4);
		hBox3.getChildren().addAll(removeCapperi);
		gridPane.getChildren().addAll(capperi, hBox3);
		GridPane.setConstraints(capperi, 0,3);
		GridPane.setConstraints(hBox3, 1,3);

		Label carciofi = new Label(Ingredienti.CARCIOFI.name());
		ButtonAddRmvIngr removeCarciofi = new ButtonAddRmvIngr(Ingredienti.CARCIOFI, nuovaPizza);
		HBox hBox4 = new HBox(4);
		hBox4.getChildren().addAll(removeCarciofi);
		gridPane.getChildren().addAll(carciofi, hBox4);
		GridPane.setConstraints(carciofi, 0,4);
		GridPane.setConstraints(hBox4, 1,4);

		Label cotto = new Label(Ingredienti.COTTO.name());
		ButtonAddRmvIngr removeCotto = new ButtonAddRmvIngr(Ingredienti.COTTO, nuovaPizza);
		HBox hBox5 = new HBox(4);
		hBox5.getChildren().addAll(removeCotto);
		gridPane.getChildren().addAll(cotto, hBox5);
		GridPane.setConstraints(cotto, 0,5);
		GridPane.setConstraints(hBox5, 1,5);

		Label crudo = new Label(Ingredienti.CRUDO.name());
		ButtonAddRmvIngr removeCrudo = new ButtonAddRmvIngr(Ingredienti.CRUDO, nuovaPizza);
		HBox hBox6 = new HBox(4);
		hBox6.getChildren().addAll(removeCrudo);
		gridPane.getChildren().addAll(crudo, hBox6);
		GridPane.setConstraints(crudo, 0,6);
		GridPane.setConstraints(hBox6, 1,6);

		Label funghi= new Label(Ingredienti.FUNGHI.name());
		ButtonAddRmvIngr removeFunghi= new ButtonAddRmvIngr(Ingredienti.FUNGHI, nuovaPizza);
		HBox hBox7 = new HBox(4);
		hBox7.getChildren().addAll(removeFunghi);
		gridPane.getChildren().addAll(funghi, hBox7);
		GridPane.setConstraints(funghi, 0,7);
		GridPane.setConstraints(hBox7, 1,7);

		Label gorgonzola = new Label(Ingredienti.GORGONZOLA.name());
		ButtonAddRmvIngr removeGorgonzola= new ButtonAddRmvIngr(Ingredienti.GORGONZOLA, nuovaPizza);
		HBox hBox8 = new HBox(4);
		hBox8.getChildren().addAll(removeGorgonzola);
		gridPane.getChildren().addAll(gorgonzola, hBox8);
		GridPane.setConstraints(gorgonzola, 0,8);
		GridPane.setConstraints(hBox8, 1,8);

		Label mozzarella= new Label(Ingredienti.MOZZARELLA.name());
		ButtonAddRmvIngr removeMozzarella = new ButtonAddRmvIngr(Ingredienti.MOZZARELLA, nuovaPizza);
		HBox hBox9 = new HBox(4);
		hBox9.getChildren().addAll(removeMozzarella);
		gridPane.getChildren().addAll(mozzarella, hBox9);
		GridPane.setConstraints(mozzarella, 0,9);
		GridPane.setConstraints(hBox9, 1,9);

		Label mozzarellaDiBufala = new Label(Ingredienti.MOZZARELLA_DI_BUFALA.name());
		ButtonAddRmvIngr removeMozzarellaDiBufala= new ButtonAddRmvIngr(Ingredienti.MOZZARELLA_DI_BUFALA, nuovaPizza);
		HBox hBox10 = new HBox(4);
		hBox10.getChildren().addAll(removeMozzarellaDiBufala);
		gridPane.getChildren().addAll(mozzarellaDiBufala, hBox10);
		GridPane.setConstraints(mozzarellaDiBufala, 0,10);
		GridPane.setConstraints(hBox10, 1,10);

		Label oliveNere = new Label(Ingredienti.OLIVE_NERE.name());
		ButtonAddRmvIngr removeOliveNere = new ButtonAddRmvIngr(Ingredienti.OLIVE_NERE, nuovaPizza);
		HBox hBox11 = new HBox(4);
		hBox11.getChildren().addAll(removeOliveNere);
		gridPane.getChildren().addAll(oliveNere, hBox11);
		GridPane.setConstraints(oliveNere, 0,11);
		GridPane.setConstraints(hBox11, 1,11);

		Label origano = new Label(Ingredienti.ORIGANO.name());
		ButtonAddRmvIngr removeOrigano = new ButtonAddRmvIngr(Ingredienti.ORIGANO, nuovaPizza);
		HBox hBox12 = new HBox(4);
		hBox12.getChildren().addAll(removeOrigano);
		gridPane.getChildren().addAll(origano, hBox12);
		GridPane.setConstraints(origano, 0,12);
		GridPane.setConstraints(hBox12, 1,12);

		Label panna= new Label(Ingredienti.PANNA.name());
		ButtonAddRmvIngr removePanna= new ButtonAddRmvIngr(Ingredienti.PANNA, nuovaPizza);
		HBox hBox13 = new HBox(4);
		hBox13.getChildren().addAll(removePanna);
		gridPane.getChildren().addAll(panna, hBox13);
		GridPane.setConstraints(panna, 0,13);
		GridPane.setConstraints(hBox13, 1,13);

		Label patatine= new Label(Ingredienti.PATATINE.name());
		ButtonAddRmvIngr removePatatine= new ButtonAddRmvIngr(Ingredienti.PATATINE, nuovaPizza);
		HBox hBox14 = new HBox(4);
		hBox14.getChildren().addAll(removePatatine);
		gridPane.getChildren().addAll(patatine, hBox14);
		GridPane.setConstraints(patatine, 0,14);
		GridPane.setConstraints(hBox14, 1,14);

		Label peperoni= new Label(Ingredienti.PEPERONI.name());
		ButtonAddRmvIngr removePeperoni = new ButtonAddRmvIngr(Ingredienti.PEPERONI, nuovaPizza);
		HBox hBox15 = new HBox(4);
		hBox15.getChildren().addAll(removePeperoni);
		gridPane.getChildren().addAll(peperoni, hBox15);
		GridPane.setConstraints(peperoni, 0,15);
		GridPane.setConstraints(hBox15, 1,15);

		Label pomodorini = new Label(Ingredienti.POMODORINI.name());
		ButtonAddRmvIngr removePomodorini = new ButtonAddRmvIngr(Ingredienti.POMODORINI, nuovaPizza);
		HBox hBox16 = new HBox(4);
		hBox16.getChildren().addAll(removePomodorini);
		gridPane.getChildren().addAll(pomodorini, hBox16);
		GridPane.setConstraints(pomodorini, 0,16);
		GridPane.setConstraints(hBox16, 1,16);

		Label pomodoro = new Label(Ingredienti.POMODORO.name());
		ButtonAddRmvIngr removePomodoro = new ButtonAddRmvIngr(Ingredienti.POMODORO, nuovaPizza);
		HBox hBox17 = new HBox(4);
		hBox17.getChildren().addAll(removePomodoro);
		gridPane.getChildren().addAll(pomodoro, hBox17);
		GridPane.setConstraints(pomodoro, 0,17);
		GridPane.setConstraints(hBox17, 1,17);

		Label rucola = new Label(Ingredienti.RUCOLA.name());
		ButtonAddRmvIngr removeRucola = new ButtonAddRmvIngr(Ingredienti.RUCOLA, nuovaPizza);
		HBox hBox18 = new HBox(4);
		hBox18.getChildren().addAll(removeRucola);
		gridPane.getChildren().addAll(rucola, hBox18);
		GridPane.setConstraints(rucola, 0,18);
		GridPane.setConstraints(hBox18, 1,18);

		Label salamePiccante = new Label(Ingredienti.SALAME_PICCANTE.name());
		ButtonAddRmvIngr removeSalamePiccante = new ButtonAddRmvIngr(Ingredienti.SALAME_PICCANTE, nuovaPizza);
		HBox hBox19 = new HBox(4);
		hBox19.getChildren().addAll(removeSalamePiccante);
		gridPane.getChildren().addAll(salamePiccante, hBox19);
		GridPane.setConstraints(salamePiccante, 0,19);
		GridPane.setConstraints(hBox19, 1,19);

		Label salsiccia = new Label(Ingredienti.SALSICCIA.name());
		ButtonAddRmvIngr removeSalsiccia = new ButtonAddRmvIngr(Ingredienti.SALSICCIA, nuovaPizza);
		HBox hBox20 = new HBox(4);
		hBox20.getChildren().addAll(removeSalsiccia);
		gridPane.getChildren().addAll(salsiccia, hBox20);
		GridPane.setConstraints(salsiccia, 0,20);
		GridPane.setConstraints(hBox20, 1,20);

		Label speck = new Label(Ingredienti.SPECK.name());
		ButtonAddRmvIngr removeSpeck= new ButtonAddRmvIngr(Ingredienti.SPECK, nuovaPizza);
		HBox hBox21 = new HBox(4);
		hBox21.getChildren().addAll(removeSpeck);
		gridPane.getChildren().addAll(speck, hBox21);
		GridPane.setConstraints(speck, 0,21);
		GridPane.setConstraints(hBox21, 1,21);

		Label wurstel = new Label(Ingredienti.WURSTEL.name());
		ButtonAddRmvIngr removeWurstel = new ButtonAddRmvIngr(Ingredienti.WURSTEL, nuovaPizza);
		HBox hBox22 = new HBox(4);
		hBox22.getChildren().addAll(removeWurstel);
		gridPane.getChildren().addAll(wurstel, hBox22);
		GridPane.setConstraints(wurstel, 0,22);
		GridPane.setConstraints(hBox22, 1,22);

		Label belleDonne = new Label(Ingredienti.BELLE_DONNE.name());
		ButtonAddRmvIngr removeBelleDonne = new ButtonAddRmvIngr(Ingredienti.BELLE_DONNE, nuovaPizza);
		HBox hBox23 = new HBox(4);
		hBox23.getChildren().addAll(removeBelleDonne);
		gridPane.getChildren().addAll(belleDonne, hBox23);
		GridPane.setConstraints(belleDonne, 0,23);
		GridPane.setConstraints(hBox23, 1,23);

		Label galanteria = new Label(Ingredienti.GALANTERIA.name());
		ButtonAddRmvIngr removeGalanteria = new ButtonAddRmvIngr(Ingredienti.GALANTERIA, nuovaPizza);
		HBox hBox24 = new HBox(4);
		hBox24.getChildren().addAll(removeGalanteria);
		gridPane.getChildren().addAll(galanteria, hBox24);
		GridPane.setConstraints(galanteria, 0,24);
		GridPane.setConstraints(hBox24, 1,24);

		Label gemme = new Label(Ingredienti.GEMME_DELL_INFINITO.name());
		ButtonAddRmvIngr removeGemme = new ButtonAddRmvIngr(Ingredienti.GEMME_DELL_INFINITO, nuovaPizza);
		HBox hBox25 = new HBox(4);
		hBox25.getChildren().addAll(removeGemme);
		gridPane.getChildren().addAll(gemme, hBox25);
		GridPane.setConstraints(gemme, 0,25);
		GridPane.setConstraints(hBox25, 1,25);

		Label onnip = new Label(Ingredienti.ONNIPOTENZA.name());
		ButtonAddRmvIngr removeOnnip = new ButtonAddRmvIngr(Ingredienti.ONNIPOTENZA, nuovaPizza);
		HBox hBox26 = new HBox(4);
		hBox26.getChildren().addAll(removeOnnip);
		gridPane.getChildren().addAll(onnip, hBox26);
		GridPane.setConstraints(onnip, 0,26);
		GridPane.setConstraints(hBox26, 1,26);

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
		window.setMinWidth(330);
		window.setMaxWidth(400);
		window.setMaxHeight(300);
		// Mostra la finestra e attende di essere chiusa
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		return answer;
	}
}
