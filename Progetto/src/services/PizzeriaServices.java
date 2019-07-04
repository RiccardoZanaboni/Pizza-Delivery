package services;

import graphicAlerts.GenericAlert;
import javafx.scene.paint.Color;
import pizzeria.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

/**
 * Fornisce molteplici servizi, grafici o funzionali, in grado di supportare
 * il buon funzionamento del programma (e tuttavia non direttamente imputabili
 * a nessuna delle classi fino ad ora definite).
 * */
@SuppressWarnings("deprecation")
public class PizzeriaServices {

	public static String getHistory(boolean isGraphicRequest) {
		StringBuilder history = new StringBuilder("\n");
		try {
			Scanner fileIn = new Scanner(new File("Progetto/src/pizzeria/history.txt"));
			while (fileIn.hasNextLine()) {
				String line = fileIn.nextLine();
				if (line.startsWith("*")) {
					line = line.substring(1);	/* toglie l'asterisco dai titoletti */
					if(isGraphicRequest)
						history.append(line.toUpperCase()).append("\n");
					else
						history.append(TextualPrintServices.colorSystemOut(line + "\n", Color.YELLOW, true, true));
				} else history.append(line).append("\n");
			}
		} catch (FileNotFoundException fnfe){
			String err = "Spiacenti: sezione History non disponibile.";
			if(isGraphicRequest) GenericAlert.display(err);
			else System.out.println(TextualPrintServices.colorSystemOut(err, Color.RED,false,false));
		}
		return history.toString();
	}

	public static HashMap<String, Order> sortOrders(HashMap<String, Order> orders) {
		Set<Map.Entry<String, Order>> entries = orders.entrySet();
		Comparator<Map.Entry<String, Order>> valueComparator = (o1, o2) -> {
			Order v1 = o1.getValue();
			Order v2 = o2.getValue();
			return v1.compareTo(v2);
		};
		List<Map.Entry<String, Order>> listOfEntries = new ArrayList<>(entries); // Sort method needs a List, so let's first convert Set to List
		Collections.sort(listOfEntries, valueComparator);// sorting HashMap by values using comparator
		// copying entries from List to Map
		LinkedHashMap<String, Order> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
		for(Map.Entry<String, Order> entry : listOfEntries){
			sortedByValue.put(entry.getKey(), entry.getValue());
		}
		orders = sortedByValue;
		return orders;
	}
}
