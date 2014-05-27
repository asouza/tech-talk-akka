package actor;

import akka.actor.*;
import akka.routing.RoundRobinRouter;
import akka.routing.Router;
import akka.routing.SmallestMailboxRouter;

import java.util.ArrayList;

public class CheckoutSupervisorActor extends UntypedActor {

	private ActorRef checkout = getContext().actorOf(
            Props.create(CheckoutActor.class).withRouter(new SmallestMailboxRouter(3)));


	@Override
	public void preStart() throws Exception {
		System.out.println("Iniciando supervisor");
        context().watch(checkout);
	}

	@Override
	public void onReceive(Object message) throws Exception {
        checkout.tell(message, this.self());
	}

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return Supervisor.supervisorStrategy();
	}

}
