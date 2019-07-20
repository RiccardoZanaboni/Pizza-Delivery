package pizzeria.services.sendMail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/** Autenticatore per l'invio di e-mail.
 * Se il codice viene fornito all'utilizzatore, allora si rende necessaria la crittografia dei dati dell'account. */
public class SMTPAuthenticator extends Authenticator {
	protected String username;
	protected String password;

	SMTPAuthenticator(String username, String password){
		this.username = username;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.username, this.password);
	}
}
