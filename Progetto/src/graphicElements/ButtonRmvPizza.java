package graphicElements;

import graphicAlerts.MinPizzasAlert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import pizzeria.Order;
import pizzeria.Pizza;

public class ButtonRmvPizza extends Button {

	public ButtonRmvPizza(Label nomeLabels, Label prezzoLabel, Label toppingLabel, Button shoppingCartButton, Order order, Pizza pizza, Label countPizza) {
		//  Label prezzoLabel, Label toppingLabel,
		Image image1 = new Image("graphicElements/images/cestino.png");
		ImageView imageView = new ImageView(image1);
		imageView.setFitHeight(20);
		imageView.setFitWidth(20);
		this.setGraphic(imageView);
		setId("rmvpizza");
        getStylesheets().addAll(this.getClass().getResource("cssStyle/buttonsAndLabelsAndBackgroundStyle.css").toExternalForm());
		this.setShape(new Circle(100000));
		this.setOnAction(e-> {
		    if (order.getOrderedPizze().contains(pizza)) {
                order.getOrderedPizze().remove(pizza);
                order.setNumTemporaryPizze(-1);
                shoppingCartButton.setText(order.getNumPizzeProvvisorie() + "");

                if(pizza.getName().equals("PizzaModificata"))
                {
                    int i = order.countPizzaModificata(pizza);
                    pizza.setCount(i);
                    countPizza.setText("" + pizza.getCount());
                } else {
                    pizza.decreaseCount();
                    countPizza.setText("" + pizza.getCount());
                }
		    }
            else if (order.searchModificata(pizza) ){
                if(pizza.getName().equals("PizzaModificata"))
                {
                    for(int j=0;j<order.getNumPizze();j++)
                    {
                       if(order.getOrderedPizze().get(j).getDescription().equals(pizza.getDescription())) {
                           order.getOrderedPizze().remove(j);
                           break;
                       }
                    }
                    int i=order.countPizzaModificata(pizza);
                    pizza.setCount(i);
                    countPizza.setText("" + pizza.getCount());
                    order.setNumTemporaryPizze(-1);
                    shoppingCartButton.setText(order.getNumPizzeProvvisorie() + "");
            }
        }
            if (pizza.getCount()==0) {
					nomeLabels.setText("");
					prezzoLabel.setText("");
					toppingLabel.setText("");
					countPizza.setText("");
					this.setVisible(false);
				}
			else if(pizza.getCount()<0) {
				MinPizzasAlert.display(pizza.getMaiuscName());
			}
		});
	}
}
