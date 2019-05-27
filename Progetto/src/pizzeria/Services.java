package pizzeria;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Services {

	public static String colorSystemOut(String text, Color color, boolean bold, boolean underlined) {
		StringBuilder cString = new StringBuilder("\033[");
		if(color == Color.WHITE) {
			cString.append("30");
		}
		else if(color == Color.RED) {
			cString.append("31");
		}
		else if(color == Color.GREEN) {
			cString.append("32");
		}
		else if(color == Color.YELLOW) {
			cString.append("33");
		}
		else if(color == Color.BLUE) {
			cString.append("34");
		}
		else if(color == Color.MAGENTA) {
			cString.append("35");
		}
		else if(color == Color.CYAN) {
			cString.append("36");
		}
		else if(color == Color.GRAY) {
			cString.append("37");
		}
		else {
			cString.append("30");
		}
		if(bold) {
			cString.append(";1");
		}
		if(underlined) {
			cString.append(";4");
		}
		cString.append(";0m").append(text).append("\033[0m");
		return cString.toString();
	}

	public static void paintMenuString() {
		String l1 = "        _______________________________________________";
		l1 = colorSystemOut(l1,Color.WHITE,true,false);
		String l2 = "        ||___________________________________________||";
		l2 = colorSystemOut(l2,Color.WHITE,true,false);
		String v = "        ||";
		v = colorSystemOut(v,Color.WHITE,true,false);
		String a = "                _____              ";
		a = colorSystemOut(a, Color.RED,true,false);
		String b = "        |\\  /| |      |\\   | |    |";
		b = colorSystemOut(b, Color.RED,true,false);
		String c = "        | \\/ | |___   | \\  | |    |";
		c = colorSystemOut(c, Color.RED,true,false);
		String d = "        |    | |      |  \\ | |    |";
		d = colorSystemOut(d, Color.RED,true,false);
		String e = "        |    | |_____ |   \\| |____|";
		e = colorSystemOut(e, Color.RED,true,false);

		System.out.println("\n"+getLine()+"\n"+l1+"\n"+v+a+v+"\n"+v+b+v+"\n"+v+c+v+"\n"+v+d+v+"\n"+v+e+v+"\n"+l2);
	}

	public static String getLine(){
		return "\n---------------------------------------------------------------------------------------------------\n";
	}

	public static int getMinutes(int ora, int minuto){
		return 60*ora + minuto;
	}

	public static int getMinutes(Date openingToday) {		// restituisce il minutaggio dalla mezzanotte
		int ora = openingToday.getHours();
		int minuto = openingToday.getMinutes();
		return getMinutes(ora, minuto);
	}

	public static int getNowMinutes() {
		Calendar cal = new GregorianCalendar();
		int nowHour = cal.get(Calendar.HOUR_OF_DAY);
		int nowMinute = cal.get(Calendar.MINUTE);
		return getMinutes(nowHour, nowMinute);
	}

	public static int calculateOpeningMinutesPizzeria(Pizzeria pizzeria){
		int openMinutes = getMinutes(pizzeria.getOpeningTime());
		int closeMinutes = getMinutes(pizzeria.getClosingTime());
		return closeMinutes - openMinutes;
	}

	public static int calculateStartIndex(Pizzeria pizzeria, int now, int tot, int restaAperta) {
		int esclusiIniziali = 0;
		int giaPassati;     // minuti attualmente già passati dall'apertura
		int tempiFissi;      // tempi da considerare per la cottura e la consegna
		if(tot <= pizzeria.getAvailablePlaces())
			tempiFissi = 15;      // 1 infornata: 5 minuti per la cottura e 10 minuti per la consegna
		else
			tempiFissi = 20;      // 2 infornate: 10 minuti per la cottura e 10 minuti per la consegna
		if(now > getMinutes(pizzeria.getOpeningTime())) {     // Se adesso la pizzeria è già aperta...
			giaPassati = now - getMinutes(pizzeria.getOpeningTime());	// ... allora calcolo da quanti minuti lo è.
			if(tempiFissi < giaPassati)
				esclusiIniziali = giaPassati;	// i tempi fissi sono già compresi
		}
		return esclusiIniziali + tempiFissi;
	}

	/** In TextInterface, gestisce eventuali errori di inserimento da tastiera (spazi/virgole) degli ingredienti. */
	public static String arrangeIngredientString(StringTokenizer st){
		String ingred = st.nextToken(",");
		if(ingred.startsWith(" "))
			ingred = ingred.substring(1);
		if(ingred.endsWith(" "))
			ingred = ingred.substring(0,ingred.length()-1);
		ingred = ingred.replace(" ","_");
		ingred = ingred.toUpperCase();
		return ingred;
	}

	/** Restituisce il nome della pizza, con tutte le iniziali maiuscole. */
	public static String getCamelName(Pizza pizza) {
		String nome = pizza.getMaiuscName();
		nome = nome.charAt(0) + nome.substring(1).toLowerCase();
		for(int i=1; i<nome.length(); i++){
			if(nome.substring(i,i+1).equals("_") || nome.substring(i,i+1).equals(" ")){
				nome = nome.replace(nome.substring(i,i+1)," ");
				nome = nome.replace(nome.substring(i+1,i+2),nome.substring(i+1,i+2).toUpperCase());
			}
		}
		return nome;
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

	/** Controlla, prima di un nuovo ordine, se sei ancora in tempo prima che la pizzeria chiuda. */
	public static String checkTimeOrder(Pizzeria pizzeria) {
		int nowMin = getNowMinutes();
		int closeMin = getMinutes(pizzeria.getClosingTime());
		if(closeMin - nowMin >= 20)
			return "OPEN";
		else {
			if (closeMin > nowMin)
				return "CLOSING";
			else
				return "CLOSED";
		}
	}
}
