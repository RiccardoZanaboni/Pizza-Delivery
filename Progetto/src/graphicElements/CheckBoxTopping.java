package graphicElements;

import javafx.scene.control.CheckBox;
import pizzeria.Toppings;
import pizzeria.Pizza;

class CheckBoxTopping extends CheckBox {

    private Toppings ingr;
    private boolean isPresent;

    /**
     * CheckBox utile, nell'interfaccia grafica, per modificare la pizza in questione
     * aggiungendo o rimuovendo l'ingrediente scelto. Setta automaticamente
     * gli ingredienti già compresi nella versione della pizza presente nel menu.
     */

    CheckBoxTopping(Toppings ingr, Pizza nuovaPizza) {
        this.ingr = ingr;

        if(nuovaPizza.getToppings().containsKey(ingr.name())) {
            this.isPresent = true;
            setSelected(true);
        } else {
            this.isPresent = false;
            setSelected(false);
        }
    }

    void setPresent(boolean present) {
        this.isPresent = present;
    }

    boolean isPresent() {
        return isPresent;
    }

    Toppings getIngr() {
        return ingr;
    }
}