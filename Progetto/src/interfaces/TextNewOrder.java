package interfaces;

import exceptions.RestartOrderExc;
import javafx.scene.paint.Color;
import pizzeria.Customer;
import pizzeria.Order;
import pizzeria.Pizzeria;
import services.TextualColorServices;
import services.TextualServices;

import java.sql.SQLException;
import java.util.Date;

public class TextNewOrder {

	/** Effettua tutte le operazioni necessarie ad effettuare un nuovo ordine.
	 * Utilizzo la sigla-chiave "OK" una volta terminata la scelta delle pizze, per continuare.
	 * Utilizzo ovunque la sigla-chiave "F" per l'annullamento dell'ordine: si torna all'inizio.
	 * */
	public void makeOrderText(Customer customer, Pizzeria pizzeria, TextualInterface textualInterface) throws SQLException {
		pizzeria.updatePizzeriaToday();
		System.out.println(TextualServices.printMenu(pizzeria));
		Order order = pizzeria.initializeNewOrder();
		order.setCustomer(customer);
		int num;
		String nomePizza;
		int tot = 0;
		boolean isPrimaRichiesta = true;
		try {
			do {
				nomePizza = textualInterface.whichPizza(isPrimaRichiesta);
				if (nomePizza.equals("OK")) {
					break;      // smette di chiedere pizze
				}
				isPrimaRichiesta = false;
				num = textualInterface.howManySpecificPizza(order, nomePizza);
				if (order.getNumPizze() == 16) {
					tot += num;
					break;      // hai chiesto esattamente 16 pizze in totale: smette di chiedere pizze
				}
				tot += num;
			} while (true);

			Date orario;
			orario = textualInterface.orderTime(order, tot);
			System.out.println(orario);
			if (textualInterface.insertNameAndAddress(order)) {
				System.out.println(TextualServices.recapOrder(order));
				textualInterface.askConfirm(order, orario);
			}
		} catch (RestartOrderExc e) {
			String annullato = TextualColorServices.colorSystemOut("L'ordine è stato annullato.", Color.ORANGE,true,false);
			System.out.println("\t>> " + annullato);
			System.out.println(TextualColorServices.getLine());
			textualInterface.whatDoYouWant(customer);
		}
	}



}
