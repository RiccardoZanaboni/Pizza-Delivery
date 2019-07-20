package pizzeria.services.sendMail;

import database.CustomerDB;
import javafx.scene.paint.Color;
import pizzeria.Customer;
import pizzeria.services.TextColorServices;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * Consente di inviare una e-mail di testo dall'indirizzo della pizzeria, autenticata
 * tramite password in SMTPAuthenticator, ad un destinatario alla volta.
 *
 * ATTENZIONE: Per poter inviare e-mail, è necessario estrarre il contenuto di "ExternalLibraries.zip"
 * (tra i file del progetto) in una cartella locale;
 * nel contenuto vi sono i due file: activation.jar, mail.jar.
 * A questo punto, su Intellij, cliccare in alto File/Project_Structure/Libraries/+/
 * [qui seleziono il percorso dei due file, separatamente].
 * */

public class SendJavaMail {
	private Properties props = setProperties();
	private Authenticator auth = new SMTPAuthenticator("pizzeria.wolf@gmail.com", "password.01");

	/** @return le proprietà richieste per l'utilizzo del servizio. */
	private Properties setProperties(){
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		return props;
	}

	/** Invia una e-mail come richiesto.
	 * @return true se l'invio è avvenuto con successo. */
	public boolean sendMail(String dest, String subject, String txt) {
		try {
			Session session = Session.getInstance(props,auth);
			session.setDebug(true);

			MimeMessage message = new MimeMessage(session);
			String from = "pizzeria.wolf@gmail.com";
			message.setFrom(new InternetAddress(from));

			InternetAddress to = new InternetAddress(dest);
			message.setRecipient(Message.RecipientType.TO, to);

			message.setSubject(subject);
			message.setSentDate(new Date());

			MimeBodyPart messagePart = new MimeBodyPart();
			messagePart.setText(txt);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messagePart);
			//multipart.addBodyPart(attachmentPart);	/* per allegati */

			message.setContent(multipart);
			Transport.send(message);

			System.out.println(TextColorServices.colorSystemOut("\t>> Il messaggio è stato inviato!", Color.YELLOW,true,false));
			return true;

		} catch (MessagingException e) {
			return false;
		}
	}

	/** Intestazione per l'invio di una mail di benvenuto, inviata in automatico
	 * quando si registra un nuovo account */
	public boolean welcomeMail(String newUser, String newPsw, String dest) {
		String subject = "Welcome to WolfPizza!";
		String messageBody = "Carissimo/a " + newUser + ",\n\n" +
				"Abbiamo il piacere di darti il benvenuto nella famiglia WolfPizza!\n" +
				"Hai creato con successo un nuovo account, tramite il quale avrai accesso a tutti i nostri servizi.\n" +
				"User: " + newUser + "\n" +
				"Password: " + newPsw + "\n\nAccedi ed ordina con noi!";
		return sendMail(dest,subject,messageBody);
	}

	/** Intestazione per l'invio di una mail di recupero dei dati, inviata in automatico quando richiesto */
	public void recoverPassword(String dest) {
		String user = CustomerDB.getInfoCustomerFromMailAddress(dest,1);
		String psw = CustomerDB.getCustomerFromUsername(user,2);
		String nome = CustomerDB.getCustomerFromUsername(user,4);
		String subject = "Recupero Password";
		StringBuilder txt = new StringBuilder();
		txt.append("Carissimo/a ");
		if(nome == null)
			txt.append("utente");
		else
			txt.append(nome.toUpperCase());
		txt.append(",\n\nEcco i tuoi dati:\n");
		txt.append("User: ").append(user).append("\n");
		txt.append("Password: ").append(psw).append("\n\nAccedi ed ordina con noi!");
		String messageBody = txt.toString();
		sendMail(dest,subject,messageBody);
	}

	/** Intestazione per l'invio di una mail di avviso di cambio di indirizzo e-mail,
	 * inviata in automatico quando richiesto */
	public void changeMailAddress(Customer customer, String newMail) {
		String user = customer.getUsername();
		String nome = CustomerDB.getCustomerFromUsername(user,4);
		String subject = "E-mail Address Changed";
		StringBuilder txt = new StringBuilder();
		txt.append("Carissimo/a ");
		if(nome == null)
			txt.append("utente");
		else
			txt.append(nome.toUpperCase());
		txt.append(",\n\nTi confermiamo che questa è la tua nuova e-mail associata.");
		txt.append("\n\nAccedi ed ordina con noi!");
		String messageBody = txt.toString();
		sendMail(newMail,subject,messageBody);
	}
}
