package pizzeria.services;

import enums.OpeningPossibilities;
import javafx.scene.paint.Color;
import pizzeria.DeliveryMan;
import pizzeria.Pizzeria;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeServices {

	/** Restituisce i minuti passati dalla mezzanotte all'orario richiesto. */
	public static int getMinutes(int ora, int minuto){
		return 60*ora + minuto;
	}

	/** Restituisce i minuti passati dalla mezzanotte all'orario richiesto. */
	public static int getMinutes(Date date) {
		int ora = date.getHours();
		int minuto = date.getMinutes();
		return getMinutes(ora, minuto);
	}

	/** Restituisce i minuti passati dalla mezzanotte di oggi. */
	public static int getNowMinutes() {
		Calendar cal = new GregorianCalendar();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		int nowMinute = cal.get(Calendar.MINUTE);
		return getMinutes(nowHour, nowMinute);
	}

	/** Restituisce tutti gli orari in cui la pizzeria potrebbe garantire la consegna di "tot" pizze.
	 * la var "scarto" risponde all'eventualità che la pizzeria sia già aperta al momento attuale. */
	public static ArrayList<String> availableTimes(Pizzeria pizzeria, int tot){
		ArrayList<String> availables = new ArrayList<>();
		int now = getNowMinutes();
		int restaAperta = calculateOpeningMinutesPizzeria(pizzeria);
		int esclusiIniziali = calculateStartIndex(pizzeria, now, tot);     // primo orario da visualizzare (in minuti)

		for(int i = esclusiIniziali; i < restaAperta; i++) {    // considera i tempi minimi di preparazione e consegna
			if(i % 5 == 0) {
				if (pizzeria.getOvens()[i / 5].getAvailablePlaces() + pizzeria.getOvens()[(i / 5) - 1].getAvailablePlaces() >= tot) {
					for (DeliveryMan a : pizzeria.getDeliveryMen()) {
						if (a.getDeliveryManTimes()[i / 10].isFree()) {
							int newMinutes = getMinutes(pizzeria.getOpeningToday()) + i;   // NON POSSO PARTIRE DA TROVACASELLA MENO 1: RISCHIO ECCEZIONE
							int ora = newMinutes / 60;
							int min = newMinutes % 60;
							String nuovoOrario = timeStamp(ora,min);
							availables.add(nuovoOrario + "  ");
							break;
						}
					}
				}
			}
		}
		if(availables.size() > 0) {
			return availables;
		} else {
			return null;
		}
	}

	/** Calcola i minuti totali in cui la pizzeria rimane aperta oggi. */
	public static int calculateOpeningMinutesPizzeria(Pizzeria pizzeria){
		int openMinutes = getMinutes(pizzeria.getOpeningToday());
		int closeMinutes = getMinutes(pizzeria.getClosingToday());
		return closeMinutes - openMinutes - 10;
	}

	/** esegue i controlli necessari per determinare il primo orario disponibile da restituire. */
	public static int calculateStartIndex(Pizzeria pizzeria, int now, int tot) {
		int esclusiIniziali = 0;
		int giaPassati;      // minuti attualmente già passati dall'apertura
		int tempiFissi;      // tempi da considerare per la cottura e la consegna
		if(tot <= pizzeria.getAvailablePlaces())
			tempiFissi = 15;      // 1 infornata: 5 minuti per la cottura e 10 minuti per la consegna
		else
			tempiFissi = 20;      // 2 infornate: 10 minuti per la cottura e 10 minuti per la consegna
		if(now > getMinutes(pizzeria.getOpeningToday())) {     // Se adesso la pizzeria è già aperta...
			giaPassati = now - getMinutes(pizzeria.getOpeningToday());	// ... allora calcolo da quanti minuti lo è.
			if(tempiFissi < giaPassati)
				esclusiIniziali = giaPassati;	// i tempi fissi sono già compresi
		}
		return esclusiIniziali + tempiFissi;
	}

	/** La stringa, corrispondente ad un orario valido, viene appositamente
	 * trasformata in Date (viene considerato il giorno corrente). */
	public static Date stringToDate(String sDate1) {
		Date d;
		Calendar calendar = new GregorianCalendar();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		sDate1 = day + "/" + month + "/" + year + " " + sDate1;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			d = formato.parse(sDate1);
		} catch (ParseException e) {
			return null;
		}
		return d;
	}

	/** Controlla che la String inserita corrisponda ad un orario valido, nel formato [HH:mm]. */
	public static boolean checkValidTime(String sDate1) {
		try {
			StringTokenizer st = new StringTokenizer(sDate1, ":");
			String token = st.nextToken();
			if (token.length() != 2) {
				return false;
			}
			int ora = Integer.parseInt(token);
			token = st.nextToken();
			if (token.length() != 2)
				return false;
			int minuti = Integer.parseInt(token);
			if (ora > 23 || minuti > 59)
				return false;
		} catch (NumberFormatException | NoSuchElementException e) {
			return false;
		}
		return true;
	}

	/** Esegue i controlli necessari per scrivere l'orario in formato [HH:mm]. */
	public static String timeStamp(int ora, int min) {
		String orario = "";
		if(ora < 10)
			orario += "0";
		if (min < 10) {
			orario += (ora + ":0" + min);
		} else {
			orario += (ora + ":" + min);
		}
		return orario;
	}

	public static String dateTimeStamp(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dataStr = sdf.format(date);
		dataStr += " " + timeStamp(date.getHours(),date.getMinutes());
		return dataStr;
	}

	/** Controlla, prima di un nuovo ordine, se sei ancora in tempo prima che la pizzeria chiuda. */
	public static OpeningPossibilities checkTimeOrder(Pizzeria pizzeria) {
		int nowMin = getNowMinutes();
		int openMin = getMinutes(pizzeria.getOpeningToday());
		int closeMin = getMinutes(pizzeria.getClosingToday());
		if(closeMin <= nowMin || openMin == closeMin)
			return OpeningPossibilities.CLOSE;
		if(closeMin - nowMin >= 0)// TODO: risistemare alla fine (mettere 20)!! Ho settato a 0 per poter lavorare anche alle 23:50!!!
			return OpeningPossibilities.OPEN;
		else
			return OpeningPossibilities.CLOSING;
	}
}
