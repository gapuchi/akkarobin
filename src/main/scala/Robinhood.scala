import scala.util.{Failure, Success}

object Robinhood extends ActorValues {
  def main(args: Array[String]): Unit = {
    val symbols = Seq("AMZN", "GOOG")

    Quotes.getQuotes(symbols) onComplete {
      case Success(map) => println(map.map(_.symbol))
      case Failure(msg) => println(s"Error: $msg")
    }
  }
}
