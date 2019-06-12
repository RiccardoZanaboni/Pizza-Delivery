package pizzeria.pizzeriaSendMail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator
{
	protected String username;
	protected String password;


	public SMTPAuthenticator(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(this.username, this.password);
	}
}
