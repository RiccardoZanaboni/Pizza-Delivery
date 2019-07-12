package graphicElements.pizzeriaSidePages;

import graphicElements.customerSidePages.newOrder.OrderPage3;
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
import pizzeria.Pizzeria;
import pizzeria.services.TextColorServices;
import pizzeria.services.TimeServices;

import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("deprecation")
public class PizzeriaVisualizeOrdersPage {

	public static void display(Pizzeria pizzeria, Stage window) {
		ArrayList<Label> nameLabels = new ArrayList<>();
		ArrayList<Label> toppingLabels = new ArrayList<>();
		ArrayList<Label> priceLabels = new ArrayList<>();
		ArrayList<Label> countPizzeLabels = new ArrayList<>();
		VBox layout = new VBox();

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
		backButton.setMinHeight(35);
        hBox1.setMinSize(600, 60);
        hBox1.setAlignment(Pos.CENTER);
		Button refreshButton = new Button("Aggiorna pagina ");
		refreshButton.setOnAction(e ->
				display(pizzeria,window)
		);
		hBox1.getChildren().addAll(backButton,refreshButton);
		hBox1.setAlignment(Pos.CENTER);

		layout.setOnKeyPressed(ke -> {
			if(ke.getCode()== KeyCode.CONTROL||ke.getCode()== KeyCode.BACK_SPACE)
				backButton.fire();
		});
        ScrollPane scrollPane = new ScrollPane(layout);
		VBox vBox=new VBox();
		vBox.getChildren().addAll(scrollPane,hBox1);
		Scene scene = new Scene(vBox, 800, 600);
		/* prova */ //scene.getStylesheets().addAll(PizzeriaVisualizeOrdersPage.class.getResource("/graphicElements/cssStyle/orderPage3.css").toExternalForm());
		window.setScene(scene);
		window.show();
	}

	private static VBox addEverythingToGridPane(Order order, ArrayList<Label> nomiLabels, ArrayList<Label> countPizzeLabels, ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels) {
		Label orderLabel = new Label(order.getOrderCode() + "\t");
		Label timeLabel = new Label(TimeServices.dateTimeStamp(order.getTime()) + "\t");
		Label infoLabel = new Label(order.getName() + "\t" + order.getAddress());
		HBox infoBox = new HBox();
		infoBox.getChildren().addAll(timeLabel,orderLabel,infoLabel);
		HBox spazioBox = new HBox();
		spazioBox.getChildren().add(new Label(TextColorServices.getLine()));

		GridPane gridPane;
		gridPane = OrderPage3.graphicRecap(nomiLabels, countPizzeLabels, ingrLabels, prezziLabels, order);
		gridPane.getColumnConstraints().add(new ColumnConstraints(60));
		gridPane.getColumnConstraints().add(new ColumnConstraints(160));
		gridPane.getColumnConstraints().add(new ColumnConstraints(500));
		gridPane.getColumnConstraints().add(new ColumnConstraints(80));
		gridPane.setHgap(10);
		gridPane.setVgap(25);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(infoBox,gridPane,spazioBox);
		// gridPane.getChildren().add(hBox);
		return vBox;
	}

}