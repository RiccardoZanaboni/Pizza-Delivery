package pizzeria.pizzeriaSendMail;

import javafx.scene.paint.Color;
import pizzeria.Database;
import pizzeria.Services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;


/**
 * Consente di inviare una e-mail di testo dall'indirizzo della pizzeria, autenticata
 * tramite password in SMTPAuthenticator, ad un elenco di indirizzi destinatari.
 *
 * Per poter inviare mail, è necessario estrarre il contenuto di "jaf-1.1" (tra i file del progetto)
 * nella vostra cartella jdk1.8.0_201/jre/lib/ext (il contenuto sono i due file activation.jar, mail.jar).
 * A questo punto, su Intellij, clicco in alto File/Project_Structure/Libraries/+/ [qui seleziono
 * il percorso dei due file, separatamente].
 * */

public class SendJavaMail {
	private Properties props = setProperties();
	private Authenticator auth = new SMTPAuthenticator("pizzeria.wolf@gmail.com", "password.01");

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
			//multipart.addBodyPart(attachmentPart);		// per allegati

			message.setContent(multipart);
			Transport.send(message);

			System.out.println(Services.colorSystemOut("\t>> Il messaggio è stato inviato!", Color.YELLOW,true,false));
			return true;

		} catch (MessagingException e) {
			return false;
		}
	}

	public boolean welcomeMail(String newUser, String newPsw, String dest) {
		String subject = "Welcome to WolfPizza!";
		StringBuilder txt = new StringBuilder();
		txt.append("Carissimo/a ").append(newUser).append(",\n\n");
		txt.append("Abbiamo il piacere di darti il benvenuto nella famiglia WolfPizza!\n");
		txt.append("Hai creato con successo un nuovo account, tramite il quale avrai accesso a tutti i nostri servizi.\n");
		txt.append("User: ").append(newUser).append("\n");
		txt.append("Password: ").append(newPsw).append("\n\nAccedi ed ordina con noi!");
		String messageBody = txt.toString();
		return sendMail(dest,subject,messageBody);
	}

	public void recoverPassword(String dest) {
		String user = Database.getInfoCustomerFromMailAddress(dest,1);
		String psw = Database.getInfoCustomerFromUsername(user,2);
		String nome = Database.getInfoCustomerFromUsername(user,4);
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
}
