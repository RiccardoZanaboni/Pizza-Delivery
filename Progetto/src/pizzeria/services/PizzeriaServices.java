package pizzeria.services;

import database.CustomerDB;
import database.Database;
import enums.AccountPossibilities;
import enums.LoginPossibilities;
import graphicAlerts.GenericAlert;
import javafx.scene.paint.Color;
import pizzeria.Customer;
import pizzeria.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Fornisce molteplici servizi, grafici o funzionali, alla pizzeria, in modo da supportare
 * il buon funzionamento del programma (e tuttavia non direttamente imputabili
 * a nessuna delle classi fino ad ora definite).
 * */
@SuppressWarnings("deprecation")
public class PizzeriaServices {

	/** Restituisce, come Stringa, il contenuto del file di testo History.txt
	 * Funzionamento leggermente differente se la richiesta è fatta tramite interfaccia
	 * testuale o grafica. */
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
						history.append(TextColorServices.colorSystemOut(line + "\n", Color.YELLOW, true, true));
				} else history.append(line).append("\n");
			}
		} catch (FileNotFoundException fnfe){
			String err = "Spiacenti: sezione History non disponibile.";
			if(isGraphicRequest) GenericAlert.display(err);
			else System.out.println(TextColorServices.colorSystemOut(err, Color.RED,false,false));
		}
		return history.toString();
	}

	/** Effettua un ordinamento degli ordini, in ordine cronologico. */
	public static HashMap<String, Order> sortOrders(HashMap<String, Order> orders) { // FIXME: 20/07/2019 
		Set<Map.Entry<String, Order>> entries = orders.entrySet();
		Comparator<Map.Entry<String, Order>> valueComparator = (o1, o2) -> {
			Order v1 = o1.getValue();
			Order v2 = o2.getValue();
			return v1.compareTo(v2);
		};
		/* Converte il Set in List, per potere usare la Sort() */
		List<Map.Entry<String, Order>> listOfEntries = new ArrayList<>(entries);
		listOfEntries.sort(valueComparator);
		/* Copia gli elementi della List in una Map */
		LinkedHashMap<String, Order> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
		for(Map.Entry<String, Order> entry : listOfEntries){
			sortedByValue.put(entry.getKey(), entry.getValue());
		}
		orders = sortedByValue;
		return orders;
	}

	/** Calcola l'ordine più recente effettuato dal cliente. */
	public static Order CustomerLastOrder(Customer customer, HashMap<String,Order> orderP) { // FIXME: 20/07/2019
		Order last = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = sdf.parse("1970-01-01 00:00:00");
			for (Order order : orderP.values()) {
				if (order.getCustomer().getUsername().equals(customer.getUsername())) {
					if (order.getTime().getTime() > date.getTime()) {
						last = order;
						date = order.getTime();
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return last;
	}

	/** Controlla il corretto inserimento dei dati per la creazione di un nuovo account.
	 * @return AccountPossibilities: dà un riscontro sulla possibilità di creare l'account. */
	public static AccountPossibilities canCreateAccount(String mailAddress, String newUser, String newPsw, String confPsw) {
		if(newPsw.equals(confPsw)){
			if(newUser.length() > 2 && newPsw.length() > 2) {
				/* se si registra correttamente, va bene */
				try {
					if (CustomerDB.getCustomer(newUser.toUpperCase(),newPsw) || Database.checkMail(mailAddress))
						return AccountPossibilities.EXISTING;
					else
						return AccountPossibilities.OK;
				} catch (SQLException e) {
					return AccountPossibilities.OK;
				}
			} else
				/* password troppo breve */
				return AccountPossibilities.SHORT;
		} else {
			/* se la password non viene confermata correttamente */
			return AccountPossibilities.DIFFERENT;
		}
	}

	/** Controlla il corretto inserimento dei dati per effettuare il login.
	 * @return LoginPossibilities: dà un riscontro sulla possibilità di effettuare il login. */
	public static LoginPossibilities checkLogin(String userP, String pswP, String user, String psw) {
		try {
			if (user.equals(userP) && psw.equals(pswP)) {
				/* se è la pizzeria, allora accede come tale */
				return LoginPossibilities.PIZZERIA;
			} else if (CustomerDB.getCustomer(user, psw)) {
				/* se è un utente identificato, accede come tale */
				return LoginPossibilities.OK;
			}
		} catch (SQLException sqle) {
			String err = TextColorServices.colorSystemOut("\nErrore critico per SQL.\nIl programma viene terminato.\n",Color.RED,false,false);
			Database.criticalError(err);
		}
		/* se la combinazione utente-password è errata */
		return LoginPossibilities.NO;
	}
}
