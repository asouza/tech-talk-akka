package actor;

import mailer.HtmlMail;
import akka.actor.UntypedActor;

public class MailerActor extends UntypedActor{

	@Override
	public void onReceive(Object message) throws Exception {
		HtmlMail htmlMail = (HtmlMail) message;
		
		System.out.println("Enviando email "+htmlMail.getBody());
	}

}
