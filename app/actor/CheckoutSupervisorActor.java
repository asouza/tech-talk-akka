package actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;

public class CheckoutSupervisorActor extends UntypedActor {

	private ActorRef checkout = getContext().actorOf(
			Props.create(CheckoutActor.class));
	
	@Override
	public void preStart() throws Exception {
		System.out.println("Iniciando supervisor");
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
