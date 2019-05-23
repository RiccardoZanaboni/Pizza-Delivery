package elementiGrafici;

import avvisiGrafici.AlertNumPizzeMax;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pizzeria.Order;
import pizzeria.Pizzeria;

class ButtonModPizza extends Button {
    ButtonModPizza(Order order, Pizzeria pizzeria,String pizza, Label countModificheLabel){
        this.setId("modPizza");
        this.setText("Modifica");
        this.setOnAction(e-> {
            if(order.getNumPizze()<16) {
                if (ModifyBox.display(order, pizzeria,pizza)) {
                    order.increaseCountModifiedPizze();
                    countModificheLabel.setText("" + order.getCountModifiedPizze());
                    ModifyBox.setAnswer();
                }
            }else
                AlertNumPizzeMax.display();
        });


    }
}
