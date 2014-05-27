package actor

import akka.actor.OneForOneStrategy
import scala.concurrent.duration._
import akka.actor.SupervisorStrategy._
import exceptions.PaymentFailureException
import akka.actor.OneForOneStrategy
import akka.actor.ActorContext
import akka.actor.ActorRef
import akka.actor.ChildRestartStats
import akka.actor.ChildRestartStats
import play.libs.Akka
import akka.actor.Props
import mailer.HtmlMail

object Supervisor {
  
  private val mailerActor = Akka.system().actorOf(
			Props.create(classOf[MailerActor]))

  val supervisorStrategy =
    new OurSupervisor(
      OneForOneStrategy.apply(maxNrOfRetries = 5, withinTimeRange = 1 minute) {
      case _: PaymentFailureException => {
        Restart
      }
      case _: NullPointerException => Stop
      case _: Exception => Escalate
    })

  class OurSupervisor(val strategy: OneForOneStrategy) extends OneForOneStrategy(strategy.maxNrOfRetries, strategy.withinTimeRange, false)(strategy.decider) {
	  
	  override def handleFailure(context: ActorContext, child: ActorRef, cause: Throwable, stats: ChildRestartStats, children: Iterable[ChildRestartStats]): Boolean = {
			  if(stats.maxNrOfRetriesCount == 5){
				  mailerActor.tell(new HtmlMail(s"Ator do checkout morreu por conta de ${cause.getMessage()}"), null)
			  }
			  super.handleFailure(context, child, cause, stats, children)
	  }
  }
}
