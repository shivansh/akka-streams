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
  val primeSieve: Flow[Int, Int, NotUsed] = Flow[Int].filter(isPrime(_))

  // def mappingfxn(x: Int): Int= {
  //   if(isPrime(x)) x
  // }

  def isPrime(x: Int): Boolean = {
    if (x==1) false
    else if (x==2) true
    else !(2 to (x/2)).exists(n => x%n==0)
  }

  Source(1 to 100).via(primeSieve).to(printingSink).run()

}
