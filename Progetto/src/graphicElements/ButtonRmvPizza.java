package graphicElements;

import graphicAlerts.MinPizzasAlert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;

public class ButtonRmvPizza extends Button {
	public ButtonRmvPizza(Button shoppingCartButton, Order order, Pizzeria pizzeria, Pizza pizza, Label countPizza) {
		this.setShape(new Circle(1000000));
		this.setText("✘");
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
