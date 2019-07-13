package graphicElements.elements;

import graphicAlerts.GenericAlert;
import graphicElements.customerSidePages.newOrder.ModifyBox;
import javafx.scene.control.Button;
import pizzeria.Order;
import pizzeria.Pizzeria;

public class ButtonModPizza extends Button {

    /**
     * Bottone utile, nell'interfaccia grafica, per aggiungere all'ordine
     * un'istanza della pizza desiderata, opportunamente modificata con
     * aggiunta o rimozione di ingredienti, tramite apposito box di modifica.
     */
    public ButtonModPizza(Button shoppingCartButton, Order order, Pizzeria pizzeria, String pizza){
        this.setId("modPizza");
        this.setText("Modifica");
        this.setOnAction(e-> {
            if(order.getNumPizze() < 16) {
                ModifyBox box = new ModifyBox();
                if (box.display(order, pizzeria,pizza)) {
                    ModifyBox.setAnswer();
                    order.setNumTemporaryPizze(true);
                    shoppingCartButton.setText(order.getNumTemporaryPizze()+"");
                }
            } else
                GenericAlert.display("Attenzione: numero massimo di pizze raggiunto!");
        });


    }
}
