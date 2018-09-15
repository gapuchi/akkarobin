import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.Unmarshal
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future

trait MyJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val quoteFormat = jsonFormat15(Quote)
  implicit val quoteListFormat = jsonFormat1(QuoteList)
}

object Quotes extends ActorValues with MyJsonProtocol {
  def getQuote(symbol: String): Future[Quote] = {
    Http().singleRequest(HttpRequest(uri = s"https://api.robinhood.com/quotes/$symbol/"))
      .flatMap(Unmarshal(_).to[Quote])
  }

  def getQuotes(symbols: Seq[String]): Future[List[Quote]] = {
    val param = symbols.mkString(",")
    Http().singleRequest(HttpRequest(uri = s"https://api.robinhood.com/quotes/?symbols=$param"))
      .flatMap(Unmarshal(_).to[QuoteList])
      .map(_.results)
  }
}

case class QuoteList(results: List[Quote])
case class Quote(ask_price: String,
                 ask_size: Long,
                 bid_price: String,
                 bid_size: Long,
                 last_trade_price: String,
                 last_extended_hours_trade_price: String,
                 previous_close: String,
                 adjusted_previous_close: String,
                 previous_close_date: String,
                 symbol: String,
                 trading_halted: Boolean,
                 has_traded: Boolean,
                 last_trade_price_source: String,
                 updated_at: String,
                 instrument: String)
