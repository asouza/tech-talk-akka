package actor;

import java.math.BigDecimal;
import java.util.Random;

import mailer.HtmlMail;
import models.Payment;
import play.api.templates.Html;
import play.libs.Akka;
import scala.Option;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import exceptions.PaymentFailureException;

public class CheckoutActor extends UntypedActor{
	
	private static ActorRef mailerActor = Akka.system().actorOf(
			Props.create(MailerActor.class));
	private int checkouts;
	private Random random = new Random();

	@Override
	public void onReceive(Object message) throws Exception {
        System.out.println("recebendo mensagem: " + message);
        Thread.sleep(3000);
		Payment payment = (Payment) message;
		
		if(payment.getCardNumber().equals("123456")){
			throw new NullPointerException("deveria parar o ator");
		}
		
		if(payment.getUserMail().equals("escalate@gmail.com")){
			throw new IllegalArgumentException("nao tratamos isso");
		}
		
		BigDecimal randomAmount = new BigDecimal(random.nextInt(30));
		System.out.println("Valor random => "+randomAmount);
		
		if(payment.getAmount().compareTo(randomAmount) > 0){
			throw new PaymentFailureException("Valor máximo excedido! "+randomAmount);
		}
		
		System.out.println("Pagamento processado "+payment);
		this.checkouts++;
		
		Html template = views.html.mail.render(payment);
		mailerActor.tell(new HtmlMail(template.body()), this.self());
		
	}
	
	@Override
	public void preRestart(Throwable reason, Option<Object> message)
			throws Exception {
		if(message.isDefined()){
			self().forward(message.get(),context());
		}
	}
	
	@Override
	public void postStop() throws Exception {
		System.out.println("Fui morto...");
		super.postStop();
	}
	
	
}
