package graphicElements.PizzeriaPages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.Order;
import pizzeria.OrderDB;
import pizzeria.Pizzeria;

import java.util.ArrayList;
import java.util.Date;

public class PizzeriaOrdersPage {

	public static void display(Pizzeria pizzeria, Stage window) {
		//ArrayList<Label> orderNameLabels = new ArrayList<>();
		ArrayList<Label> nameLabels = new ArrayList<>();
		ArrayList<Label> toppingLabels = new ArrayList<>();
		ArrayList<Label> priceLabels = new ArrayList<>();
		ArrayList<Label> countPizzeLabels = new ArrayList<>();
		//ArrayList<GridPane> gridPanes = new ArrayList<>();
		VBox layout = new VBox();
//FIXME se funziona get order col db

		//TODO: OrderDB.deleteOrdersNotToday();     // elimino dal DB tutti gli ordini che non sono per oggi
		for (String code:pizzeria.getOrders().keySet()) {
			if (pizzeria.getOrders().get(code).getTime().getDate() == (new Date().getDate())) {
				VBox vBox = addEverythingToGridPane(pizzeria.getOrders().get(code), nameLabels, countPizzeLabels, toppingLabels, priceLabels);
				vBox.setPadding(new Insets(10, 10, 10, 10));
				layout.getChildren().add(vBox);
			}
		}

		Button backButton = new Button("← Torna indietro");
		backButton.setOnAction(e -> {
			PizzeriaHomePage pizzeriaHomePage=new PizzeriaHomePage();
			pizzeriaHomePage.display(pizzeria, window);
		});
		HBox hBox1=new HBox(10);
		hBox1.getChildren().add(backButton);
		hBox1.setAlignment(Pos.CENTER);

		layout.setOnKeyPressed(ke -> {
			if(ke.getCode()== KeyCode.CONTROL||ke.getCode()== KeyCode.BACK_SPACE)
				backButton.fire();
		});
		layout.getChildren().add(hBox1);
		ScrollPane scrollPane = new ScrollPane(layout);
		Scene scene = new Scene(scrollPane, 880, 600);
		window.setScene(scene);
		window.show();
	}

	private static VBox addEverythingToGridPane(Order order, ArrayList<Label> nomiLabels, ArrayList<Label> countPizzeLabels,  ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels) {
		HBox hBox = new HBox();
		Label orderLabel = new Label(order.getOrderCode()+" ");
		Label timeLabel = new Label(order.getTime()+" ");
		Label label=new Label(order.getName()+" "+order.getAddress()+" ");
		hBox.getChildren().addAll( timeLabel,orderLabel,label);
		//hBox.setAlignment(Pos.CENTER);

		GridPane gridPane;
		gridPane = order.graphRecap(nomiLabels, countPizzeLabels, ingrLabels, prezziLabels);
		gridPane.getColumnConstraints().add(new ColumnConstraints(80));
		gridPane.getColumnConstraints().add(new ColumnConstraints(100));
		gridPane.getColumnConstraints().add(new ColumnConstraints(500));
		gridPane.getColumnConstraints().add(new ColumnConstraints(80));
		gridPane.setHgap(10);
		gridPane.setVgap(30);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(hBox,gridPane);
		// gridPane.getChildren().add(hBox);
		return vBox;
	}

}

