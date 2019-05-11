package elementiGrafici;

import avvisiGrafici.AlertNumPizzeMax;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pizzeria.Order;
import pizzeria.Pizzeria;

public class ButtonModPizza extends Button {
    public ButtonModPizza(Order order, Pizzeria pizzeria,String pizza, Label countModificheLabel){
        this.setText("Modifica");
        this.setOnAction(e-> {if(order.getNumeroPizze()<16) {
            if (ModifyBox.display(order, pizzeria,pizza)) {
                order.setCountPizzeModificate();
                countModificheLabel.setText("" + order.getCountPizzeModificate());
                ModifyBox.setAnswer();
            }
        }else
            AlertNumPizzeMax.display();
        });


    }
}
