package graphicElements;

import graphicAlerts.MinPizzasAlert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
		//this.setText("✘");
		this.setShape(new Circle(100000));
		this.setOnAction(e-> {
			if (order.getOrderedPizze().contains(pizza)) {
				order.getOrderedPizze().remove(pizza);
				pizzeria.getMenu().get(pizza.getMaiuscName()).decreaseCount();
				order.decreaseNumPizzeProvvisorie();
				shoppingCartButton.setText(order.getNumPizzeProvvisorie()+"");
				countPizza.setText(""+pizzeria.getMenu().get(pizza.getMaiuscName()).getCount());

			} else {
				MinPizzasAlert.display(pizza.getMaiuscName());
			}
		});
	}
}
