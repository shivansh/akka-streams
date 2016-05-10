import akka._
import akka.util._
import akka.actor._
import akka.stream._
import akka.stream.scaladsl._
import scala.concurrent._
import scala.util._

object Main extends App {
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  val printingSink = Sink.foreach(println)
  //val primeSieve: Flow[Int, Int, NotUsed] = Flow[Int].filter(isPrime(_))

  var initialSource = Source(1 to 100)

  var x = 0
  for (x <- 2 to 100) {
    initialSource = initialSource.filter{k => k == x || k % x != 0}
  }

  // Recursive approach
  /*filterFxn(k: Int, s: Source): Source = {*/
    //if (k == 100) initialSource
    //else filterFxn(k+1, s.filter(x => x == k || x % k != 0))
  /*}*/
  initialSource.to(printingSink).run()

}
