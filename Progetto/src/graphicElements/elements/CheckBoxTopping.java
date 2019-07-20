package graphicElements.elements;

import javafx.scene.control.CheckBox;
import pizzeria.Pizza;

public class CheckBoxTopping extends CheckBox {

    private String ingr;
    private boolean isPresent;

    /**
     * CheckBox utile, nell'apposito Box dell'interfaccia grafica, per modificare la pizza selezionata,
     * aggiungendo o rimuovendo l'ingrediente scelto.
     * Setta automaticamente gli ingredienti che sono già compresi nella versione della pizza presente nel menu.
     */

    public CheckBoxTopping(String ingr, Pizza nuovaPizza) {
        this.ingr = ingr;

        if(nuovaPizza.getToppings().containsKey(ingr)) {
            this.isPresent = true;
            setSelected(true);
        } else {
            this.isPresent = false;
            setSelected(false);
        }
    }

    public void setPresent(boolean present) {
        this.isPresent = present;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public String getIngr() {
        return ingr;
    }
}