package pizzeria.pizzeriaSendMail;

import javafx.scene.paint.Color;
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
	private final String host = "smtp.gmail.com";
	private final String PORT = "465";
	private final String from = "pizzeria.wolf@gmail.com";
	private final String psw = "password.01";
	private String[] to = {
			"fecchio.andrea@gmail.com"
			//,"ele.repossi@gmail.com"
			//,"martina.frigoli01@universitadipavia.it"
			,"riccardo.crescenti01@universitadipavia.it"
			//,"fabio.rossanigo01@universitadipavia.it"
			//,"francesco.musitano02@universitadipavia.it"
			//,"fabio.rossanigo01@universitadipavia.it"
			//,"riccardo.zanaboni02@universitadipavia.it"
			,"matteo.gobbo12@gmail.com"
			};
	private String subject = "INVITO";
	private String bodyText = "La presente per invitarti all'inaugurazione della pizzeria \"Wolf Pizza\" oggi, 15 giugno, alle ore 17, nel nuovo locale di Pavia, in via Bolzano, 10.\n\nTi aspettiamo!";

	public SendJavaMail() {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
			props.put("mail.smtp.port", PORT);
			props.put("mail.smtp.socketFactory.port", PORT);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");

			Authenticator auth = new SMTPAuthenticator(from,psw);

			Session session = Session.getInstance(props,auth);
			session.setDebug(true);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));

			InternetAddress[] addTo = new InternetAddress[to.length];
			for (int i = 0; i < addTo.length; i++) {
				addTo[i] = new InternetAddress(to[i]);
			}
			message.setRecipients(Message.RecipientType.TO, addTo);

			message.setSubject(subject);
			message.setSentDate(new Date());

			MimeBodyPart messagePart = new MimeBodyPart();
			messagePart.setText(bodyText);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messagePart);
			//multipart.addBodyPart(attachmentPart);		// per allegati

			message.setContent(multipart);

			Transport.send(message);

			String confirm = Services.colorSystemOut("\n\t>> Il messaggio e-mail è stato inviato!\n\n", Color.YELLOW,true,false);
			System.out.println(confirm);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
