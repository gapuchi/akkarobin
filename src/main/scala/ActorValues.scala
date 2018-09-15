import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait ActorValues {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher
}
