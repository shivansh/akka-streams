import akka._
import akka.util._
import akka.actor._
import akka.stream._
import akka.stream.scaladsl._
import scala.concurrent._
import scala.util._
import scala.concurrent.ExecutionContext.Implicits._

object Main extends App {
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  val printingSink = Sink.foreach(println)
  //val primeSieve: Flow[Int, Int, NotUsed] = Flow[Int].filter(isPrime(_))

  var initialSource = Source(1 to 100)

  // Iterative approach, too many mutations
  // var x = 0
  // for (x <- 2 to 100) {
  //   initialSource = initialSource.filter{k => k == x || k % x != 0}
  // }

  // Recursive, no mutations
  def filterFxn(k: Int, s: Source[Int, NotUsed]): Source[Int, NotUsed] = {
    if (k == 100) s
    else {
      val first: Future[Int] = {
        s.take(1).runWith(Sink.head)
      } 
      for {
        k <- first
      } yield filterFxn(k, s.filter(x => x == k || x % k != 0))
    }
  }
  val sieve = filterFxn(2, initialSource).to(printingSink).run()

}
