package graphicElements;

import graphicAlerts.GenericAlert;
import javafx.scene.control.Button;
import pizzeria.Order;
import pizzeria.Pizzeria;

class ButtonModPizza extends Button {

    /**
     * Bottone utile, nell'interfaccia grafica, per aggiungere all'ordine
     * un'istanza della pizza desiderata, opportunamente modificata con
     * aggiunta o rimozione di ingredienti, tramite apposito box di modifica.
     */

    ButtonModPizza(Button shoppingCartButton, Order order, Pizzeria pizzeria, String pizza){
        this.setId("modPizza");
        this.setText("Modifica");
        this.setOnAction(e-> {
            if(order.getNumPizze()<16) {
                if (ModifyBox.display(order, pizzeria,pizza)) {
                    order.increaseCountModifiedPizze();
                    //countModificheLabel.setText("" + order.getCountModifiedPizze());
                    ModifyBox.setAnswer();
                    order.setNumTemporaryPizze(1);
                    shoppingCartButton.setText(order.getNumTemporaryPizze()+"");
                }
            }else
                GenericAlert.display("Attenzione: numero massimo di pizze raggiunto!");
        });


    }
}
