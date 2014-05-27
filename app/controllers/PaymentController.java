package controllers;

import java.math.BigDecimal;

import models.Payment;
import play.data.DynamicForm;
import play.libs.Akka;
import play.mvc.Controller;
import play.mvc.Result;
import actor.CheckoutSupervisorActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class PaymentController extends Controller {

	private static DynamicForm form = new DynamicForm();
	private static ActorRef checkoutSupervisor = Akka.system().actorOf(
			Props.create(CheckoutSupervisorActor.class));

	public static Result checkoutForm() {
		return ok(views.html.index.render());
	}

	public static Result checkout() {
		DynamicForm boundForm = form.bindFromRequest();
		Payment payment = new Payment(boundForm.get("cardNumber"),
				new BigDecimal(boundForm.get("amount")),
				boundForm.get("email"));
		
		checkoutSupervisor.tell(payment, null);
		return ok("Sua compra está sendo efetuada");
	}
}
