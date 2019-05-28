package graphicElements;

import graphicAlerts.MinPizzasAlert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;

public class ButtonRmvPizza extends Button {
	public ButtonRmvPizza(Button shoppingCartButton, Order order, Pizzeria pizzeria, Pizza pizza, Label countPizza) {

		Image image1 = new Image("graphicElements/cestino.png");
		ImageView imageView = new ImageView(image1);
		imageView.setFitHeight(20);
		imageView.setFitWidth(20);
		this.setGraphic(imageView);
		setId("rmvpizza");
        getStylesheets().addAll(this.getClass().getResource("buttonsAndLabelsAndBackgroundStyle.css").toExternalForm());
		this.setShape(new Circle(100000));
		this.setOnAction(e-> {
			if (order.getOrderedPizze().contains(pizza)) {
				order.getOrderedPizze().remove(pizza);
                // FIXME: 28/05/2019 sistemare il decremento per le pizze modificate
                // FIXME: 28/05/2019 quando si elimina una pizza modificata il Count va a -1, ne puoi eliminare 
                // FIXME: 28/05/2019 solo una alla volta , uscendo dallo ShoppingCart ogni volta
				pizza.decreaseCount();
				order.decreaseNumPizzeProvvisorie();
				shoppingCartButton.setText(order.getNumPizzeProvvisorie()+"");
				countPizza.setText(""+pizza.getCount());

			} else {
				MinPizzasAlert.display(pizza.getMaiuscName());
			}
		});
	}
}
